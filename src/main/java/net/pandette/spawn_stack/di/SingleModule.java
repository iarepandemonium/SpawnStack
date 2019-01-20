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

import net.pandette.spawn_stack.DefaultSpawnStackProvider;
import net.pandette.spawn_stack.SpawnStackProvider;
import net.pandette.spawn_stack.StackLocation;
import net.pandette.spawn_stack.StackerConfiguration;
import net.pandette.spawn_stack.hooks.SilkSpawnerHook;
import net.pandette.spawn_stack.listeners.CreatureListener;
import net.pandette.spawn_stack.mysql.MySQL;
import net.pandette.spawn_stack.mysql.StackSql;
import net.pandette.spawn_stack.yaml.CustomYamlFile;
import net.pandette.spawn_stack.yaml.StackYaml;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class SingleModule {


  private final FileConfiguration configuration;
  private final String path;
  private final JavaPlugin plugin;

  public SingleModule(FileConfiguration configuration, JavaPlugin plugin) {
    this.configuration = configuration;
    this.path = plugin.getDataFolder() + "/" + "spawners.yml";
    this.plugin = plugin;
  }

  @Singleton
  @Provides
  StackerConfiguration providesStackerConfiguration() {
    return new StackerConfiguration(configuration);
  }

  @Singleton
  @Provides
  StackLocation providesStackLocation(StackerConfiguration stacker) {
    if (configuration.getBoolean("database.enable", false)) {
      return new StackSql(new MySQL(stacker));
    } else {
      return new StackYaml(new CustomYamlFile(path));
    }
  }

  @Singleton
  @Provides
  CreatureListener providesCreatureListener(StackLocation location, StackerConfiguration configuration) {
    return new CreatureListener(location, configuration, plugin);
  }

  @Singleton
  @Provides
  SpawnStackProvider providesSpawnStackProvider() {
    if (configuration.getBoolean("hooks.silkspawner", false)) {
      return new SilkSpawnerHook();
    } else {
      return new DefaultSpawnStackProvider();
    }
  }

}
