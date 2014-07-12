/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tenjava.entries.Verdent.t2.entity;

import com.tenjava.entries.Verdent.t2.racing.RacingManager;
import com.tenjava.entries.Verdent.t2.utils.HorseManager;
import java.util.HashMap;
import java.util.UUID;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;

/**
 *
 * @author Verdent
 */
public class Arena {

    private final HashMap<UUID, PowerUp> powerUps = new HashMap<UUID, PowerUp>();
    private final HashMap<UUID, Horse> horses = new HashMap<UUID, Horse>();
    private final HashMap<UUID, Jockey> jockeys = new HashMap<UUID, Jockey>();
    private final HashMap<Integer, RacingLap> laps = new HashMap<Integer, RacingLap>();
    private final HorseManager hm = new HorseManager();
    private final ArenaSpawns spawns;
    private final String name;
    private final Location location1;
    private final Location location2;

    public Arena(Location[] spawns, String name, Location location1, Location location2) {
        this.name = name;
        this.spawns = new ArenaSpawns(spawns, this);
        this.location1 = location1;
        this.location2 = location2;
    }

    public Checkpoint getNextCheckpoint(Checkpoint checkpoint) {
        RacingLap lap = checkpoint.getLap();
        if (lap.hasNextCheckpoint(checkpoint)) {
            return lap.getNextCheckpoint(checkpoint);
        }
        lap = getNextLap(lap);
        if (lap != null) {
            return lap.getNextCheckpoint(0);
        }
        return null;
    }

    public RacingLap getNextLap(RacingLap lap) {
        int lapNumber = lap.getLapNumber();
        return getNextLap(lapNumber);
    }

    public RacingLap getNextLap(int lapNumber) {
        return laps.get(lapNumber + 1);
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

    public HashMap<UUID, Jockey> getJockeys() {
        return jockeys;
    }

    public boolean addJockey(Player player) {
        Jockey jockey = new Jockey(player, Color.BLUE, player.getLocation(), null);
        return addJockey(jockey);
    }

    public boolean addJockey(Jockey jockey) {
        Player player = jockey.getPlayer();
        String msg = spawns.givePlayerSpawn(player);
        if (msg.equalsIgnoreCase("")) {
            player.sendMessage(ChatColor.RED + msg);
            return false;
        }
        Horse horse = this.hm.spawnHorse(player.getLocation(), "", true, player, false);
        this.hm.setHorseSpeed(horse);
        horse.setPassenger(player);
        jockey.setHorse(horse);
        horses.put(jockey.getHorse().getUniqueId(), jockey.getHorse());
        jockeys.put(jockey.getPlayerUUID(), jockey);
        giveFirstCheckpoint(jockey);
        return true;
    }

    public void removeJockey(Jockey jockey) {
        jockey.getPlayer().getVehicle().eject();
        jockeys.remove(jockey.getPlayerUUID());
        spawns.removePlayerSpawn(jockey.getPlayerUUID());
    }

    public void removeAllJockeys() {
        for (Jockey jockey : this.jockeys.values()) {
            jockey.exitArena();
        }
    }

    public synchronized void addPowerUp(Entity entity) {
        addPowerUp(entity, 160);
    }

    public synchronized void addPowerUp(Entity entity, int interval) {
        PowerUp powerUp = new PowerUp(interval, this, entity);
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

    public boolean setNextCheckpoint(UUID uuid) {
        return RacingManager.getInstance().setNextCheckpoint(getJockey(uuid));
    }

    public boolean isArenaFull() {
        return spawns.isArenaFull();
    }

    public String getName() {
        return this.name;
    }

    private void giveFirstCheckpoint(Jockey jockey) {
        Checkpoint checkpoint = laps.get(1).getNextCheckpoint(0);
        jockey.setCheckPoint(checkpoint);
    }

    public void reloadArena() {
        removeAllHorses();
        removeAllJockeys();
        removeAllPowerUps();
    }

    public void sendMessageToAll(String message) {
        for (Jockey jockey : jockeys.values()) {
            jockey.getPlayer().sendMessage(message);
        }
    }

    public boolean addSpawn(Location location) {
        if (spawns.getCapacity() >= 17) {
            return false;
        }
        spawns.registerSpawn(location);
        return true;
    }

}
