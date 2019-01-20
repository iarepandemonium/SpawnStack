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

import net.pandette.spawn_stack.SpawnStackProvider;
import net.pandette.spawn_stack.StackLocation;
import net.pandette.spawn_stack.listeners.CreatureListener;
import net.pandette.spawn_stack.listeners.SoulListener;
import net.pandette.spawn_stack.listeners.SpawnerListener;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = SingleModule.class)
public interface SingleComponent {

  /**
   * Gets the CreatureListener instance.
   *
   * @return CreatureListener instance
   */
  CreatureListener creatureListener();

  /**
   * Gets the SpawnerListener instance.
   *
   * @return SpawnerListener instance
   */
  SpawnerListener spawnerListener();

  /**
   * Gets the SoulListener instance.
   *
   * @return SoulListener instance
   */
  SoulListener soulListener();

  /**
   * Gets the stack location object initialized in the module.
   *
   * @return Stack Location
   */
  StackLocation stackLocation();

  /**
   * Gets the spawn stack provider that is initialized in the module
   *
   * @return Spawn stack provider.
   */
  SpawnStackProvider spawnStackProvider();

}
