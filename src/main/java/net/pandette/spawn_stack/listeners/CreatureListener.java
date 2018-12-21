/*
 * SpawnStack is a plugin that allows users to stack spawners and collect souls.
 * Copyright (C) 2018 Kimberly Boynton
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 * To request information, make an issue on the github page for this
 * plugin.
 */

package net.pandette.spawn_stack.listeners;

import net.pandette.spawn_stack.StackLocation;
import net.pandette.spawn_stack.StackerConfiguration;
import org.bukkit.Bukkit;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.SpawnerSpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class CreatureListener implements Listener {

    private final List<UUID> spawnedCreatures = new ArrayList<>();

    private final StackLocation stackLocation;
    private final StackerConfiguration configuration;
    private final JavaPlugin plugin;

    @Inject
    public CreatureListener(StackLocation stackLocation, StackerConfiguration configuration, JavaPlugin plugin) {
        this.stackLocation = stackLocation;
        this.configuration = configuration;
        this.plugin = plugin;
    }

    /**
     * This method check is a spawner from the plugin spawned a creature. It then sets the delay to a random number
     * that is lower than the max delay possible.  It also adds any spawned creature to a list to track it.
     *
     * @param event SpawnerSpawnEvent
     */
    @EventHandler(ignoreCancelled = true)
    public void onSpawn(SpawnerSpawnEvent event) {
        CreatureSpawner spawner = event.getSpawner();

        if (!stackLocation.isSpawner(spawner.getLocation())) return;

        int maxDelay = configuration.getDefaultSpawnerDelay() / stackLocation.getSize(spawner.getLocation());

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            spawner.setDelay(new Random().nextInt(maxDelay));
            spawner.update();
        }, 0L);

        spawnedCreatures.add(event.getEntity().getUniqueId());
    }

    /**
     * This kills a spawned creature that was saved to the list, tracking it by UUID, and then
     * removes it from the list before having a random chance to drop a soul item.
     *
     * @param event EntityDeathEvent
     */
    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        if (!spawnedCreatures.contains(event.getEntity().getUniqueId())) return;

        spawnedCreatures.remove(event.getEntity().getUniqueId());
        if (Math.random() > configuration.getSoulDropChance()) return;

        event.getDrops().add(configuration.getSoulItem());
    }


}
