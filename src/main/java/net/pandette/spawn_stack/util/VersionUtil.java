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

package net.pandette.spawn_stack.util;

import org.bukkit.entity.EntityType;

public class VersionUtil {

  /**
   * This is used to convert creature type to entity type using a method that is still available in
   * 12.2, mostly because I was too lazy to recompile a 1.8 version of spigot.
   *
   * @param type Type to convert
   * @return Entity Type
   */
  public static EntityType convertCreatureType(String type) {
    return EntityType.valueOf(type.toUpperCase());
  }
}
