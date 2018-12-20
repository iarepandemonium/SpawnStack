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
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.SpawnerSpawnEvent;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CreatureListener implements Listener {

    private final List<UUID> spawnedCreatures = new ArrayList<>();

    private final StackLocation stackLocation;
    private final StackerConfiguration configuration;

    @Inject
    public CreatureListener(StackLocation stackLocation, StackerConfiguration configuration) {
        this.stackLocation = stackLocation;
        this.configuration = configuration;
    }

    @EventHandler
    public void onSpawn(SpawnerSpawnEvent event) {
        if(!stackLocation.isSpawner(event.getSpawner().getLocation())) return;

        spawnedCreatures.add(event.getEntity().getUniqueId());
    }

    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        if(!spawnedCreatures.contains(event.getEntity().getUniqueId())) return;

        spawnedCreatures.remove(event.getEntity().getUniqueId());
        if(Math.random() > configuration.getSoulDropChance()) return;

        event.getDrops().add(configuration.getSoulItem());
    }


}
