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

package net.pandette.spawn_stack.mysql;

import net.pandette.spawn_stack.StackLocation;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class StackSql implements StackLocation {

  private final MySQL mySQL;

  @Inject
  public StackSql(MySQL mySQL) {
    this.mySQL = mySQL;
    createTable();
  }

  /**
   * If the table doesn't exist in the database, this creates the table.
   */
  private void createTable() {
    try (Connection connection = mySQL.open();
         PreparedStatement ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS spawnstack " +
                 "(" +
                 "  world VARCHAR(32) NOT NULL, " +
                 "  x     INTEGER     NOT NULL, " +
                 "  y     INTEGER     NOT NULL, " +
                 "  z     INTEGER     NOT NULL, " +
                 "  size  INTEGER     NOT NULL, " +
                 "  CONSTRAINT pk_stack_location PRIMARY KEY (world, x, y, z), " +
                 "  CONSTRAINT un_stack_location UNIQUE (world, x, y, z) " +
                 ")" +
                 " ENGINE = InnoDB;")) {

      ps.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public int getSize(Location location) {
    try (Connection connection = mySQL.open();
         PreparedStatement ps = connection.prepareStatement("SELECT size FROM spawnstack WHERE" +
                 " world = ? AND x = ? AND y = ? AND z = ?")) {
      setSqlLocation(ps, location);

      ResultSet rs = ps.executeQuery();

      if (rs.next()) {
        return rs.getInt("size");
      } else return -1;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return -1;
  }

  @Override
  public void updateLocation(Location location, int size) {
    try (Connection connection = mySQL.open();
         PreparedStatement ps = connection.prepareStatement(
                 "INSERT INTO spawnstack (world, x, y, z, size) VALUES (?,?,?,?,?) " +
                         "ON DUPLICATE KEY UPDATE size = ?")) {
      setSqlLocation(ps, location);
      ps.setInt(5, size);
      ps.setInt(6, size);
      ps.execute();

    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  @Override
  public void deleteLocation(Location location) {
    try (Connection connection = mySQL.open();
         PreparedStatement ps = connection.prepareStatement(
                 "DELETE FROM spawnstack WHERE world = ? AND x = ? AND y = ? AND z = ?")) {
      setSqlLocation(ps, location);
      ps.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public boolean isSpawner(Location location) {
    try (Connection connection = mySQL.open();
         PreparedStatement ps = connection.prepareStatement("SELECT * FROM spawnstack WHERE" +
                 " world = ? AND x = ? AND y = ? AND z = ?")) {
      setSqlLocation(ps, location);

      ResultSet rs = ps.executeQuery();

      return rs.next();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return false;
  }

  @Override
  public List<Location> getBetweenXandZ(String world, int x, int z) {
    List<Location> locations = new ArrayList<>();
    try (Connection connection = mySQL.open();
         PreparedStatement ps = connection.prepareStatement("SELECT world,x,y,z FROM spawnstack WHERE" +
                 " world = ? AND " +
                 "(x BETWEEN ? AND ?) AND " +
                 "(z BETWEEN ? AND ?)")) {
      ps.setString(1, world);
      int X = x << 4;
      int Z = z << 4;
      ps.setInt(2, X);
      ps.setInt(3, X + 16);
      ps.setInt(4, Z);
      ps.setInt(5, Z + 16);
      ResultSet rs = ps.executeQuery();

      while (rs.next()) {
        locations.add(new Location(
                Bukkit.getWorld(rs.getString("world")),
                rs.getInt("x"),
                rs.getInt("y"),
                rs.getInt("z")
        ));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return locations;
  }

  /**
   * This sets a location in SQL using the 1-4 parameters to set it.
   *
   * @param ps       Prepared Statement
   * @param location Location
   * @throws SQLException Exception that can be thrown.
   */
  private void setSqlLocation(PreparedStatement ps, Location location) throws SQLException {
    ps.setString(1, location.getWorld().getName());
    ps.setInt(2, location.getBlockX());
    ps.setInt(3, location.getBlockY());
    ps.setInt(4, location.getBlockZ());
  }
}
