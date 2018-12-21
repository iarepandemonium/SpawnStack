package net.pandette.spawn_stack.listeners;

import net.pandette.spawn_stack.StackLocation;
import net.pandette.spawn_stack.util.VersionUtil;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
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

    @Inject
    public SpawnerListener(StackLocation stackLocation) {
        this.stackLocation = stackLocation;
    }

    @EventHandler
    public void onPlaceStack(BlockPlaceEvent event) {
        if (event.getBlockPlaced().getType() != Material.MOB_SPAWNER) return;
        Chunk chunk = event.getBlock().getChunk();

        List<Location> locationList = stackLocation
                .getBetweenXandZ(chunk.getWorld().getName(), chunk.getX(), chunk.getZ());
        Bukkit.broadcastMessage(locationList.size() + "");

        if (!locationList.isEmpty()) {
            for (Location location : locationList) {
                if (location.getBlock().getType() != Material.MOB_SPAWNER) {
                    stackLocation.deleteLocation(location);
                    continue;
                }

                event.setCancelled(true);

                CreatureSpawner creatureSpawner = (CreatureSpawner) event.getBlock().getState();
                int size = stackLocation.getSize(location);

                int delay = creatureSpawner.getDelay() * size;
                stackLocation.updateLocation(location, size + 1);
                creatureSpawner.setDelay(delay / (size + 1));
                return;
            }
        }

        stackLocation.updateLocation(event.getBlock().getLocation(), 1);
    }

    @EventHandler
    public void onBreakStack(BlockBreakEvent event) {
        Location location = event.getBlock().getLocation();
        if(event.getBlock().getType() != Material.MOB_SPAWNER || !stackLocation.isSpawner(location)) return;

        int size = stackLocation.getSize(location);

        if(size == 1) {
            stackLocation.deleteLocation(location);
        } else {
            event.setCancelled(true);
            stackLocation.updateLocation(location, size - 1);
            CreatureSpawner creatureSpawnerBlock = (CreatureSpawner) event.getBlock().getState();

            int delay = creatureSpawnerBlock.getDelay() * size;
            creatureSpawnerBlock.setDelay(delay / (size - 1));

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
