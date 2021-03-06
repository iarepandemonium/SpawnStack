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

package net.pandette.spawn_stack.hooks;

import de.dustplanet.util.SilkUtil;

import net.pandette.spawn_stack.SpawnStackProvider;

import org.bukkit.Location;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import javax.inject.Inject;

import lombok.Value;

@Value
public class SilkSpawnerHook implements SpawnStackProvider {

  SilkUtil silkUtil;

  @Inject
  public SilkSpawnerHook() {
    silkUtil = SilkUtil.hookIntoSilkSpanwers();
  }


  @Override
  public EntityType getType(ItemStack stack) {
    short id = silkUtil.getStoredSpawnerItemEntityID(stack);
    return EntityType.fromId(id);
  }

  @Override
  public ItemStack getStack(Location location) {
    CreatureSpawner creatureSpawnerBlock = (CreatureSpawner) location.getBlock().getState();
    EntityType type = creatureSpawnerBlock.getSpawnedType();
    String name = silkUtil.getCreatureName(type.getTypeId());
    return silkUtil.newSpawnerItem(type.getTypeId(), name, 1, false);
  }


}
