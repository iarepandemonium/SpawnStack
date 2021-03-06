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

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import static org.bukkit.Material.GHAST_TEAR;

public class StackerConfiguration {

  private final FileConfiguration configuration;

  @Inject
  public StackerConfiguration(FileConfiguration configuration) {
    this.configuration = configuration;
  }

  /**
   * Build the soul item that is currency for this plugin.
   *
   * @return Itemstack of soul item
   */
  public ItemStack getSoulItem() {
    Material material = GHAST_TEAR;
    try {
      material = Material.valueOf(configuration.getString("soulitem.material", "GHAST_TEAR").toUpperCase());
    } catch (EnumConstantNotPresentException ex) {
      //No need to log this.
    }

    int durability = configuration.getInt("soulitem.durability", 0);
    String title = configuration.getString("soulitem.title", "Soul");
    List<String> lore = configuration.getStringList("soulitem.lore");
    if (lore == null) {
      lore = Collections.singletonList("Soul Item");
    }

    ItemStack stack = new ItemStack(material);
    stack.setDurability((short) durability);
    ItemMeta meta = stack.getItemMeta();
    meta.setDisplayName(title);
    meta.setLore(lore);
    stack.setItemMeta(meta);
    return stack;
  }

  /**
   * Get the drop chance of the soul item.
   *
   * @return Drop Chance
   */
  public double getSoulDropChance() {
    return configuration.getDouble("soulitem.dropchance", 1.3);
  }

  /**
   * Get the database username for MYSQL
   *
   * @return username
   */
  public String getDatabaseUsername() {
    return configuration.getString("database.user", "minecraft");
  }

  /**
   * Get the database password for MYSQL
   *
   * @return password
   */
  public String getDatabasePassword() {
    return configuration.getString("database.password", "superStronkPassword");
  }

  /**
   * Get the database name for MYSQL
   *
   * @return name
   */
  public String getDatabaseName() {
    return configuration.getString("database.database", "database");
  }

  /**
   * Get the JDBC url for MYSQL
   *
   * @return Url
   */
  public String buildJDBC() {
    String jdbc = "jdbc:mysql://";
    String url = configuration.getString("database.url", "");
    String database = configuration.getString("database.database", "");
    boolean useSSL = configuration.getBoolean("database.usessl", false);
    return jdbc + url + "/" + database + "?useSSL=" + useSSL;

  }

  /**
   * Get the cost of souls per creature
   *
   * @param type Type
   * @return Cost of souls
   */
  public Integer getSoulsPerCreature(String type) {
    if (configuration.getConfigurationSection("soulcost") == null) {
      configuration.createSection("soulcost");
      return null;
    }

    if (!configuration.isSet("soulcost." + type)) return null;
    else return configuration.getInt("soulcost." + type);
  }

  /**
   * Get a message from the configuration file.
   *
   * @param messageType    The message type to get
   * @param defaultMessage A default message incase someone deleted the configuration
   * @return Message
   */
  public String getMessage(String messageType, String defaultMessage) {
    return ChatColor.translateAlternateColorCodes('&', configuration.getString("messages." + messageType, defaultMessage));
  }

  /**
   * Get the delay for the spawner, if none is created the minecraft default of 200 is selected
   *
   * @return Spawn time.
   */
  public int getDefaultSpawnerDelay() {
    return configuration.getInt("spawner_default_delay", 200);
  }

}
