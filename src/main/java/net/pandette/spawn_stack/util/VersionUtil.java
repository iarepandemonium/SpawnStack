package net.pandette.spawn_stack.util;

import org.bukkit.entity.EntityType;

public class VersionUtil {

    /**
     * This is used to convert creature type to entity type using a method that is still available in
     * 12.2, mostly because I was too lazy to recompile a 1.8 version of spigot.
     *
     * @param type Type to convert
     * @return Entity Type
     */
    public static EntityType convertCreatureType(String type){
        return EntityType.valueOf(type.toUpperCase());
    }
}
