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
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class StackYaml implements StackLocation {

    private static final Map<Location, Integer> LOCATIONS = new HashMap<>();

    private final CustomYamlFile custom;

    public StackYaml(CustomYamlFile custom) {
        this.custom = custom;

        FileConfiguration configuration = custom.get();

        if(configuration.getConfigurationSection("locations") == null) {
            configuration.createSection("locations");
        }

        Set<String> keys = configuration.getConfigurationSection("locations").getKeys(false);
        for(String k : keys) {

        }

    }

    @Override
    public int getSize(Location location) {
        return 0;
    }

    @Override
    public void updateLocation(Location location, int size) {

    }

    @Override
    public void deleteLocation(Location location) {

    }
}
