/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tenjava.entries.Verdent.t2.entity;

import com.tenjava.entries.Verdent.t2.TenJava;
import com.tenjava.entries.Verdent.t2.racing.RacingManager;
import com.tenjava.entries.Verdent.t2.utils.EntitySpawnManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.scheduler.BukkitRunnable;

/**
 *
 * @author Verdent
 */
public class PowerUp {

    private final int INTERVAL;
    private final Location location;
    private final Arena arena;
    private Entity entity;

    public PowerUp(int INTERVAL, Arena arena, Entity entity) {
        this.INTERVAL = INTERVAL;
        this.entity = entity;
        this.location = entity.getLocation().add(0, -1, 0);;
        this.arena = arena;
    }

    public Entity getEntity() {
        return this.entity;
    }

    public void pickUp() {
        this.entity.remove();
        arena.removePowerUp(this.entity);
        new BukkitRunnable() {
            @Override
            public void run() {
                spawn();
            }
        }.runTaskLater(Bukkit.getPluginManager().getPlugin(TenJava.NAME), this.INTERVAL);
    }

    public void spawn() {
        EntitySpawnManager manager = new EntitySpawnManager();
        this.entity = manager.spawnEntity(this.location, EntityType.ENDER_CRYSTAL);
        arena.addPowerUp(this);
    }

}
