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

import net.pandette.spawn_stack.di.DaggerSingleComponent;
import net.pandette.spawn_stack.di.SingleComponent;
import net.pandette.spawn_stack.di.SingleModule;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import lombok.Getter;

public class SpawnStack extends JavaPlugin {

  @Getter
  private StackLocation stackLocation;

  @Getter
  private SpawnStackProvider spawnStackProvider;

  @Override
  public void onEnable() {
    if (!getDataFolder().exists()) {
      saveDefaultConfig();
    }

    SingleComponent component = DaggerSingleComponent.builder()
            .singleModule(new SingleModule(getConfig(), this)).build();

    Bukkit.getPluginManager().registerEvents(component.creatureListener(), this);
    Bukkit.getPluginManager().registerEvents(component.spawnerListener(), this);
    Bukkit.getPluginManager().registerEvents(component.soulListener(), this);

    stackLocation = component.stackLocation();
    spawnStackProvider = component.spawnStackProvider();
  }

  @Override
  public void onDisable() {

  }

}
