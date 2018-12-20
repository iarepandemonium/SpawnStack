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

package net.pandette.spawn_stack.di;

import dagger.Module;
import dagger.Provides;
import net.pandette.spawn_stack.StackLocation;
import net.pandette.spawn_stack.StackerConfiguration;
import net.pandette.spawn_stack.mysql.MySQL;
import net.pandette.spawn_stack.mysql.StackSql;
import net.pandette.spawn_stack.yaml.CustomYamlFile;
import net.pandette.spawn_stack.yaml.StackYaml;
import org.bukkit.configuration.file.YamlConfiguration;

import javax.inject.Singleton;
import java.io.InputStream;

@Module
public class SingleModule {


    private final YamlConfiguration configuration;
    private final InputStream stream;
    private final String path;

    public SingleModule(YamlConfiguration configuration, InputStream stream, String path) {
        this.configuration = configuration;
        this.stream = stream;
        this.path = path;
    }

    @Singleton
    @Provides
    StackerConfiguration providesStackerConfiguration() {
        return new StackerConfiguration(configuration);
    }

    @Singleton
    @Provides
    StackLocation providesStackLocation(StackerConfiguration stacker) {
        if (configuration.getBoolean("database.enabled", false)) {
            return new StackSql(new MySQL(stacker));
        } else {
            return new StackYaml(new CustomYamlFile(stream, path));
        }
    }

}
