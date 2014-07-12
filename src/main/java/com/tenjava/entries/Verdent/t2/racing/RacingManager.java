/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tenjava.entries.Verdent.t2.racing;

import com.tenjava.entries.Verdent.t2.entity.Arena;
import com.tenjava.entries.Verdent.t2.entity.Checkpoint;
import com.tenjava.entries.Verdent.t2.entity.Jockey;
import com.tenjava.entries.Verdent.t2.entity.RacingLap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;

/**
 *
 * @author Verdent
 */
public class RacingManager {

    private final static RacingManager SINGLETON = new RacingManager();
    private final ArrayList<Entity> powerUps = new ArrayList<Entity>();
    private final HashMap<UUID, Horse> horses = new HashMap<UUID, Horse>();
    private final HashMap<UUID, Jockey> jockeys = new HashMap<UUID, Jockey>();

    private RacingManager() {
    }

    public static RacingManager getInstance() {
        return SINGLETON;
    }

    public synchronized void addPowerUp(Entity entity) {
        powerUps.remove(entity);
        powerUps.add(entity);
    }

    public synchronized void removePowerUp(Entity entity) {
        powerUps.remove(entity);
    }

    public synchronized void removeAllPowerUps() {
        for (Entity entity : powerUps) {
            entity.remove();
        }
    }

    public synchronized Entity getPowerUp(Location location) {
        for (Entity entity : powerUps) {
            Location loc = entity.getLocation().add(0, -1, 0);
            if (loc.getBlockX() == location.getBlockX()
                    && loc.getBlockY() == location.getBlockY()
                    && loc.getBlockZ() == location.getBlockZ()
                    && loc.getWorld().equals(location.getWorld())) {
                return entity;
            }
        }
        return null;
    }

    public Horse getHorse(UUID uuid) {
        return this.horses.get(uuid);
    }

    public Jockey getJockey(UUID uuid) {
        return this.jockeys.get(uuid);
    }

    public boolean setNextCheckpoint(UUID uuid) {
        return setNextCheckpoint(getJockey(uuid));
    }

    public boolean setNextCheckpoint(Jockey jockey) {
        Checkpoint checkpoint = jockey.getNextCheckPoint();
        UUID uuid = jockey.getPlayerUUID();
        if (!checkpoint.containsPlayer(uuid)) {
            checkpoint.addPlayer(uuid);
        }
        Arena arena = jockey.getCurrentLap().getArena();
        checkpoint = arena.getNextCheckpoint(checkpoint);
        return false;
    }

}
