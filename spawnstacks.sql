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

CREATE TABLE IF NOT EXISTS spawnstack
(
  world VARCHAR(32) NOT NULL,
  x     INTEGER     NOT NULL,
  y     INTEGER     NOT NULL,
  z     INTEGER     NOT NULL,
  size  INTEGER     NOT NULL,
  CONSTRAINT pk_stack_location PRIMARY KEY (world, x, y, z),
  CONSTRAINT un_stack_location UNIQUE (world, x, y, z)
)
  ENGINE = InnoDB;