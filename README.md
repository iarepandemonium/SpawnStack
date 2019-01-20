# Spawn Stack

A plugin designed for Wysteria Skyblocks

## API


```$java

SpawnStack plugin = Bukkit.getPluginManager().getPlugin("Spawn-Stack");

SpawnStackProvider provider = plugin.getSpawnStackProvider();
StackLocation stack = plugin.getStackLocation();
```

```java
public interface StackLocation {

  /**
   * Retrieve the size of the spawner.
   *
   * @param location Location of Spawner
   * @return Size
   */
  int getSize(Location location);

  /**
   * Update the size of the spawner or add the location
   *
   * @param location Location to update or add
   * @param size     Spawner size
   */
  void updateLocation(Location location, int size);

  /**
   * Delete the location of a spawner
   *
   * @param location location to delete
   */
  void deleteLocation(Location location);

  /**
   * Check if a location has a spawner present.
   *
   * @param location Location to check
   * @return is present.
   */
  boolean isSpawner(Location location);

  /**
   * Get all spawners in the chunk that a spawner is being placed.
   *
   * @param world World to compare
   * @param x     X location
   * @param z     Z location
   * @return List of all spawners in the chunk
   */
  List<Location> getBetweenXandZ(String world, int x, int z);
}

```

```java
public interface SpawnStackProvider {

  /**
   * <p>Gets the type of the Entity from an item stack</p>
   *
   * @param stack Stack to retrieve
   * @return Stack
   */
  EntityType getType(ItemStack stack);

  /**
   * <p>Gets the item stack for a specific location</p>
   *
   * @param location location
   * @return ItemStack
   */
  ItemStack getStack(Location location);
}

```