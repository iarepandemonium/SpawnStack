package net.pandette.spawn_stack;

import net.pandette.spawn_stack.util.VersionUtil;
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

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

import javax.inject.Inject;

public class DefaultSpawnStackProvider implements SpawnStackProvider {

  @Inject
  public DefaultSpawnStackProvider() {
  }

  @Override
  public EntityType getType(ItemStack stack) {
    BlockStateMeta bsm = (BlockStateMeta) stack.getItemMeta();
    CreatureSpawner creatureSpawner = (CreatureSpawner) bsm.getBlockState();
    return creatureSpawner.getSpawnedType();
  }

  @Override
  public ItemStack getStack(Location location) {
    CreatureSpawner creatureSpawnerBlock = (CreatureSpawner) location.getBlock().getState();

    ItemStack spawner = new ItemStack(Material.MOB_SPAWNER, 1);
    BlockStateMeta bsm = (BlockStateMeta) spawner.getItemMeta();
    CreatureSpawner creatureSpawner = (CreatureSpawner) bsm.getBlockState();

    creatureSpawner.setSpawnedType(VersionUtil.convertCreatureType(creatureSpawnerBlock.getCreatureTypeName()));
    bsm.setBlockState(creatureSpawner);
    spawner.setItemMeta(bsm);
    return spawner;
  }

}
