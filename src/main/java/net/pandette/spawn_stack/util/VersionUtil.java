package net.pandette.spawn_stack.util;

import org.bukkit.entity.EntityType;

public class VersionUtil {

    public static EntityType convertCreatureType(String type){
        return EntityType.valueOf(type.toUpperCase());
    }
}
