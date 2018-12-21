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

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class CustomYamlFile {

    private static final String CHARACTER_ENCODING = "UTF8";

    private File configurationFile = null;
    private FileConfiguration config = null;
    private final String path;

    public CustomYamlFile(String path) {
        this.path = path;
        get();
    }


    public void save() {
        if (config == null || configurationFile == null) {
            return;
        }
        try {
            get().save(configurationFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public FileConfiguration get() {
        if(config != null) return config;

        File file = new File(path);
        try {
            config = YamlConfiguration.loadConfiguration(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (configurationFile == null) {
            configurationFile = file;
        }

        return config;
    }


}
