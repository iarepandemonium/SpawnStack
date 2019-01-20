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

package net.pandette.spawn_stack;

import org.bukkit.Location;

import java.util.List;

public interface StackLocation {

  /**
   * Retrieve the size of the spawner.
   *
   * @param location Location of Spawner
   * @return Size
   */
  int getSize(Location location);

  /**
   * Update the size of the spawner or add the location
   *
   * @param location Location to update or add
   * @param size     Spawner size
   */
  void updateLocation(Location location, int size);

  /**
   * Delete the location of a spawner
   *
   * @param location location to delete
   */
  void deleteLocation(Location location);

  /**
   * Check if a location has a spawner present.
   *
   * @param location Location to check
   * @return is present.
   */
  boolean isSpawner(Location location);

  /**
   * Get all spawners in the chunk that a spawner is being placed.
   *
   * @param world World to compare
   * @param x     X location
   * @param z     Z location
   * @return List of all spawners in the chunk
   */
  List<Location> getBetweenXandZ(String world, int x, int z);

}
