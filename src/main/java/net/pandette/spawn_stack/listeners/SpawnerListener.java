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

import net.pandette.spawn_stack.SpawnStackProvider;
import net.pandette.spawn_stack.StackLocation;
import net.pandette.spawn_stack.StackerConfiguration;

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

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SpawnerListener implements Listener {

  private final StackLocation stackLocation;
  private final StackerConfiguration configuration;
  private final SpawnStackProvider stackProvider;

  @Inject
  public SpawnerListener(StackLocation stackLocation, StackerConfiguration configuration, SpawnStackProvider stackProvider) {
    this.stackLocation = stackLocation;
    this.configuration = configuration;
    this.stackProvider = stackProvider;
  }

  /**
   * When placing a mob spawner, it updates the StackLocation tracker with the information. If one
   * already exists in the same chunk, it will stack them together.
   *
   * @param event BlockPlaceEvent
   */
  @EventHandler
  public void onPlaceStack(BlockPlaceEvent event) {
    if (event.getBlockPlaced().getType() != Material.MOB_SPAWNER) return;
    Chunk chunk = event.getBlock().getChunk();
    List<Location> locationList = stackLocation
            .getBetweenXandZ(chunk.getWorld().getName(), chunk.getX(), chunk.getZ());

    int size = 1;
    Location loc = event.getBlock().getLocation();
    Player player = event.getPlayer();


    if (!locationList.isEmpty()) {
      for (Location location : locationList) {
        if (location.getBlock().getType() != Material.MOB_SPAWNER) {
          stackLocation.deleteLocation(location);
          continue;
        }

        CreatureSpawner creatureSpawner = (CreatureSpawner) location.getBlock().getState();

        if (!creatureSpawner.getSpawnedType().equals(stackProvider.getType(event.getItemInHand())))
          continue;

        event.setCancelled(true);


        size = stackLocation.getSize(location);


        if ((configuration.getDefaultSpawnerDelay() / size) < 10) {
          String message = configuration.getMessage("too_low_delay", "&4This spawner can no longer be upgraded!");
          player.sendMessage(message);
          return;
        }

        size = size + 1;
        loc = location;
        creatureSpawner.update();
        break;
      }
    }

    ItemStack stack = event.getItemInHand().clone();
    stack.setAmount(1);
    player.getInventory().removeItem(stack);
    player.updateInventory();
    stackLocation.updateLocation(loc, size);
  }

  /**
   * Reduces or deletes a mobspawner depending on the size of the spawner.
   *
   * @param event BlockBreakEvent
   */
  @EventHandler
  public void onBreakStack(BlockBreakEvent event) {
    Location location = event.getBlock().getLocation();
    if (event.getBlock().getType() != Material.MOB_SPAWNER || !stackLocation.isSpawner(location))
      return;

    int size = stackLocation.getSize(location);

    if (size == 1) {
      stackLocation.deleteLocation(location);
    } else {
      event.setCancelled(true);
      stackLocation.updateLocation(location, size - 1);
    }

    ItemStack stack = stackProvider.getStack(event.getBlock().getLocation());
    location.getWorld().dropItemNaturally(location, stack);
  }
}
