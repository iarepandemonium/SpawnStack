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

package net.pandette.spawn_stack.yaml;

import net.pandette.spawn_stack.StackLocation;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

public class StackYaml implements StackLocation {

  private final Map<Location, Integer> locations = new HashMap<>();

  private final CustomYamlFile custom;

  @Inject
  public StackYaml(CustomYamlFile custom) {
    this.custom = custom;

    FileConfiguration configuration = custom.get();

    if (configuration.getConfigurationSection("locations") == null) {
      configuration.createSection("locations");
    }

    Set<String> keys = configuration.getConfigurationSection("locations").getKeys(false);
    for (String k : keys) {
      Location location = split(k);
      Integer i = configuration.getInt("locations." + k, 0);
      locations.put(location, i);
    }
  }

  @Override
  public int getSize(Location location) {
    return locations.get(location);
  }

  @Override
  public void updateLocation(Location location, int size) {
    locations.put(location, size);
    custom.get().set("locations." + gather(location), size);
    custom.save();
  }

  @Override
  public void deleteLocation(Location location) {
    locations.remove(location);
    custom.get().set("locations." + gather(location), null);
    custom.save();
  }

  @Override
  public boolean isSpawner(Location location) {
    return locations.containsKey(location);
  }

  @Override
  public List<Location> getBetweenXandZ(String world, int x, int z) {
    List<Location> locations = new ArrayList<>();

    int X = x << 4;
    int Z = z << 4;
    for (Location location : this.locations.keySet()) {
      if (isApplicableBlock(location, world, X, Z)) {
        locations.add(location);
      }
    }
    return locations;
  }

  private boolean isApplicableBlock(Location location, String world, int X, int Z) {
    return location.getWorld().getName().equals(world)
            && inbetween(location.getBlockX(), X, X + 15) && inbetween(location.getBlockZ(), Z, Z + 15);
  }

  private boolean inbetween(int num, int min, int max) {
    return num >= min && num <= max;
  }

  private Location split(String location) {
    String[] locationString = location.split(":::");
    if (locationString.length < 4) return null;

    try {
      return new Location(
              Bukkit.getWorld(locationString[0]),
              Integer.parseInt(locationString[1]),
              Integer.parseInt(locationString[2]),
              Integer.parseInt(locationString[3]));
    } catch (Exception e) {
      return null;
    }
  }

  private String gather(Location location) {
    return location.getWorld().getName() + ":::" + location.getBlockX() +
            ":::" + location.getBlockY() + ":::" + location.getBlockZ();
  }
}
