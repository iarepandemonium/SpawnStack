package net.pandette.spawn_stack.listeners;

import net.pandette.spawn_stack.StackLocation;
import net.pandette.spawn_stack.StackerConfiguration;
import net.pandette.spawn_stack.util.VersionUtil;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class SpawnerListener implements Listener {

    private final StackLocation stackLocation;
    private final StackerConfiguration configuration;

    @Inject
    public SpawnerListener(StackLocation stackLocation, StackerConfiguration configuration) {
        this.stackLocation = stackLocation;
        this.configuration = configuration;
    }

    /**
     * When placing a mob spawner, it updates the StackLocation tracker with the information. If one already
     * exists in the same chunk, it will stack them together.
     *
     * @param event BlockPlaceEvent
     */
    @EventHandler
    public void onPlaceStack(BlockPlaceEvent event) {
        if (event.getBlockPlaced().getType() != Material.MOB_SPAWNER) return;
        Chunk chunk = event.getBlock().getChunk();

        List<Location> locationList = stackLocation
                .getBetweenXandZ(chunk.getWorld().getName(), chunk.getX(), chunk.getZ());

        if (!locationList.isEmpty()) {
            for (Location location : locationList) {
                if (location.getBlock().getType() != Material.MOB_SPAWNER) {
                    stackLocation.deleteLocation(location);
                    continue;
                }

                event.setCancelled(true);

                CreatureSpawner creatureSpawner = (CreatureSpawner) location.getBlock().getState();
                int size = stackLocation.getSize(location);

                Player player = event.getPlayer();

                if((configuration.getDefaultSpawnerDelay() / size) < 10) {
                    String message = configuration.getMessage("too_low_delay", "&4This spawner can no longer be upgraded!");
                    player.sendMessage(message);
                    return;
                }

                player.getInventory().removeItem(new ItemStack(Material.MOB_SPAWNER));
                player.updateInventory();
                stackLocation.updateLocation(location, size + 1);
                creatureSpawner.update();
                return;
            }
        }

        stackLocation.updateLocation(event.getBlock().getLocation(), 1);
    }

    /**
     * Reduces or deletes a mobspawner depending on the size of the spawner.
     *
     * @param event BlockBreakEvent
     */
    @EventHandler
    public void onBreakStack(BlockBreakEvent event) {
        Location location = event.getBlock().getLocation();
        if (event.getBlock().getType() != Material.MOB_SPAWNER || !stackLocation.isSpawner(location)) return;

        int size = stackLocation.getSize(location);

        if (size == 1) {
            stackLocation.deleteLocation(location);
        } else {
            event.setCancelled(true);
            stackLocation.updateLocation(location, size - 1);
            CreatureSpawner creatureSpawnerBlock = (CreatureSpawner) event.getBlock().getState();

            ItemStack spawner = new ItemStack(Material.MOB_SPAWNER, 1);
            BlockStateMeta bsm = (BlockStateMeta) spawner.getItemMeta();
            CreatureSpawner creatureSpawner = (CreatureSpawner) bsm.getBlockState();

            creatureSpawner.setSpawnedType(VersionUtil.convertCreatureType(creatureSpawnerBlock.getCreatureTypeName()));
            bsm.setBlockState(creatureSpawner);
            spawner.setItemMeta(bsm);
            location.getWorld().dropItemNaturally(location, spawner);
        }
    }
}
