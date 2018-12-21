package net.pandette.spawn_stack.listeners;

import net.pandette.spawn_stack.StackLocation;
import net.pandette.spawn_stack.StackerConfiguration;
import net.pandette.spawn_stack.util.VersionUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SoulListener implements Listener {

    private final StackLocation stackLocation;
    private final StackerConfiguration configuration;

    @Inject
    public SoulListener(StackLocation stackLocation, StackerConfiguration configuration) {
        this.stackLocation = stackLocation;
        this.configuration = configuration;
    }

    @EventHandler
    public void clickWithSoul(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        Block block = event.getClickedBlock();
        if (block.getType() != Material.MOB_SPAWNER) return;

        if (!stackLocation.isSpawner(block.getLocation())) return;

        CreatureSpawner creatureSpawner = (CreatureSpawner) block.getState();

        EntityType type = VersionUtil.convertCreatureType(creatureSpawner.getCreatureTypeName());
        Integer cost = configuration.getSoulsPerCreature(type.name());
        if (cost == null) return;

        Player player = event.getPlayer();
        if (!player.getInventory().containsAtLeast(configuration.getSoulItem(), cost)) {
            player.sendMessage(configuration.getMessage("not_enough_souls", "&4You do not have enough souls for this spawner!"));
            return;
        }


        ItemStack stack = configuration.getSoulItem();
        stack.setAmount(cost);
        player.getInventory().remove(stack);
        player.updateInventory();

        int size = stackLocation.getSize(block.getLocation());

        String message = configuration.getMessage("paid_souls", "&aYou have upgraded this spawner to level {level}!");
        player.sendMessage(message.replace("{level}", String.valueOf(size + 1)));

        int delay = creatureSpawner.getDelay() * size;
        stackLocation.updateLocation(block.getLocation(), size + 1);
        creatureSpawner.setDelay(delay / (size + 1));
    }
}
