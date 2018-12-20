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
import net.pandette.spawn_stack.StackerConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

@Module
public class SingleModule {


    private YamlConfiguration configuration;

    public SingleModule(YamlConfiguration configuration){
        this.configuration = configuration;
    }

    StackerConfiguration providesStackerConfiguration(){
        return new StackerConfiguration(configuration);
    }

}
