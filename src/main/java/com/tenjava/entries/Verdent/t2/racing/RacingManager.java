/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tenjava.entries.Verdent.t2.racing;

import com.tenjava.entries.Verdent.t2.entity.Arena;
import com.tenjava.entries.Verdent.t2.entity.Checkpoint;
import com.tenjava.entries.Verdent.t2.entity.Jockey;
import com.tenjava.entries.Verdent.t2.entity.PowerUp;
import com.tenjava.entries.Verdent.t2.utils.HorseManager;
import java.util.HashMap;
import java.util.UUID;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;

/**
 *
 * @author Verdent
 */
public class RacingManager {

    private final static RacingManager SINGLETON = new RacingManager();
    private final HashMap<UUID, PowerUp> powerUps = new HashMap<UUID, PowerUp>();
    private final HashMap<UUID, Horse> horses = new HashMap<UUID, Horse>();
    private final HashMap<UUID, Jockey> jockeys = new HashMap<UUID, Jockey>();
    private final HorseManager hm = new HorseManager();

    private RacingManager() {
    }

    public static RacingManager getInstance() {
        return SINGLETON;
    }

    public synchronized void addPowerUp(Entity entity) {
        addPowerUp(entity, 160);
    }

    public synchronized void addPowerUp(Entity entity, int interval) {
        PowerUp powerUp = new PowerUp(interval, entity);
        addPowerUp(powerUp);
    }

    public synchronized void addPowerUp(PowerUp powerUp) {
        UUID uuid = powerUp.getEntity().getUniqueId();
        powerUps.remove(uuid);
        powerUps.put(uuid, powerUp);
    }

    public synchronized void removePowerUp(Entity entity) {
        powerUps.remove(entity.getUniqueId());
    }

    public synchronized void removeAllPowerUps() {
        for (PowerUp powerUp : powerUps.values()) {
            powerUp.getEntity().remove();
        }
    }

    public synchronized PowerUp getPowerUp(Location location) {
        for (PowerUp powerUp : powerUps.values()) {
            Location loc = powerUp.getEntity().getLocation().add(0, -1, 0);
            if (loc.getBlockX() <= location.getBlockX() + 1 && loc.getBlockX() >= location.getBlockX() - 1
                    && loc.getBlockY() <= location.getBlockY() + 1 && loc.getBlockY() >= location.getBlockY() - 1
                    && loc.getBlockZ() <= location.getBlockZ() + 1 && loc.getBlockZ() >= location.getBlockZ() - 1
                    && loc.getWorld().equals(location.getWorld())) {
                return powerUp;
            }
        }
        return null;
    }

    public Horse getHorse(UUID uuid) {
        return this.horses.get(uuid);
    }

    public void removeAllHorses() {
        for (Horse horse : this.horses.values()) {
            horse.remove();
        }
    }

    public Jockey getJockey(UUID uuid) {
        return this.jockeys.get(uuid);
    }

    public void addJockey(Player player) {
        Horse horse = this.hm.spawnHorse(player.getLocation(), "", true, player, false);
        this.hm.setHorseSpeed(horse);
        horse.setPassenger(player);
        Jockey jockey = new Jockey(player, Color.BLUE, horse);
        addJockey(jockey);
    }

    public void addJockey(Jockey jockey) {
        horses.put(jockey.getHorse().getUniqueId(), jockey.getHorse());
        jockeys.put(jockey.getPlayerUUID(), jockey);
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
        if (checkpoint != null) {
            jockey.setNextCheckPoint(checkpoint);
            return true;
        }
        return false;
    }

}
