/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tenjava.entries.Verdent.t2.utils;

import java.util.Random;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

/**
 *
 * @author Verdent
 */
public class EntitySpawnManager {

    public final Random random = new Random();

    public Entity spawnEntity(Location location, EntityType type) {
        return location.getWorld().spawn(location, type.getEntityClass());
    }

    public Entity spawnEntity(Location location, Class entity) {
        return location.getWorld().spawn(location, entity);
    }

}
