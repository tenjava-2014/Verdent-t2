/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tenjava.entries.Verdent.t2.racing;

import com.tenjava.entries.Verdent.t2.entity.Arena;
import com.tenjava.entries.Verdent.t2.entity.Checkpoint;
import com.tenjava.entries.Verdent.t2.entity.Jockey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 *
 * @author Verdent
 */
public class RacingManager {

    private final static RacingManager SINGLETON = new RacingManager();
    private final HashMap<String, Arena> arenas = new HashMap<String, Arena>();
    private final HashMap<UUID, String> arenaNames = new HashMap<UUID, String>();
    private final HashMap<UUID, Location> arenaLocation1 = new HashMap<UUID, Location>();
    private final HashMap<UUID, Location> arenaLocation2 = new HashMap<UUID, Location>();
    private final HashMap<UUID, String> powerEnabled = new HashMap<UUID, String>();
    private final HashMap<UUID, String> spawnEnabled = new HashMap<UUID, String>();
    private final HashMap<UUID, String> checkpointEnabled = new HashMap<UUID, String>();
    private final HashMap<UUID, Location> checkpointLocation1 = new HashMap<UUID, Location>();
    private final HashMap<UUID, Location> checkpointLocation2 = new HashMap<UUID, Location>();

    private RacingManager() {
    }

    public static RacingManager getInstance() {
        return SINGLETON;
    }

    public boolean setNextCheckpoint(Jockey jockey) {
        Checkpoint checkpoint = jockey.getCheckPoint();
        UUID uuid = jockey.getPlayerUUID();
        if (!checkpoint.containsPlayer(uuid)) {
            checkpoint.addPlayer(uuid);
        }
        Arena arena = jockey.getCurrentLap().getArena();
        checkpoint = arena.getNextCheckpoint(checkpoint);
        if (checkpoint != null) {
            jockey.setCheckPoint(checkpoint);
            return true;
        }
        return false;
    }

    public HashMap<String, Arena> getArenas() {
        return arenas;
    }

    public Arena getArena(String name) {
        return arenas.get(name);
    }

    public void registerArena(Arena arena) {
        arenas.put(arena.getName(), arena);
    }

    public Arena removeArena(String name) {
        Arena arena = arenas.remove(name);
        if (arena != null) {
            arena.reloadArena();
        }
        return arena;
    }

    public void clearAllArenas() {
        for (Arena arena : arenas.values()) {
            arena.removeAllPowerUps();
            arena.removeAllHorses();
            arena.removeAllJockeys();
        }
    }

    public void addJockey(Player player) {
        if (this.arenas.isEmpty()) {
            throw new IllegalStateException("There are no active arenas!");
        }
        Random random = new Random();
        int index = random.nextInt(this.arenas.size());
        String arenaName = (String) this.arenas.keySet().toArray()[index];
        Arena arena = arenas.get(arenaName);
        arena.addJockey(player);
    }

    public Jockey getJockey(Player player) {
        return getJockey(player.getUniqueId());
    }

    public Jockey getJockey(UUID uuid) {
        for (Arena arena : arenas.values()) {
            Jockey jockey = arena.getJockey(uuid);
            if (jockey != null) {
                return jockey;
            }
        }
        return null;
    }

    public boolean isInArena(Player player) {
        Jockey jockey = getJockey(player);
        return jockey != null;
    }

    public boolean isInArena(Location loc) {
        for (Arena arena : arenas.values()) {
            if (arena.isInArena(loc)) {
                return true;
            }
        }
        return false;
    }

    public void addArenaName(UUID uuid, String name) {
        arenaNames.put(uuid, name);
    }

    public boolean containsArenaName(UUID uuid) {
        return arenaNames.containsKey(uuid);
    }

    public String removeArenaName(UUID uuid) {
        return arenaNames.remove(uuid);
    }

    public void addArenaLocation1(UUID uuid, Location loc) {
        arenaLocation1.remove(uuid);
        arenaLocation1.put(uuid, loc);
    }

    public boolean containsArenaLocation1(UUID uuid) {
        return arenaLocation1.containsKey(uuid);
    }

    public Location removeArenaLocation1(UUID uuid) {
        return arenaLocation1.remove(uuid);
    }

    public void addArenaLocation2(UUID uuid, Location loc) {
        arenaLocation2.remove(uuid);
        arenaLocation2.put(uuid, loc);
    }

    public boolean containsArenaLocation2(UUID uuid) {
        return arenaLocation2.containsKey(uuid);
    }

    public Location removeArenaLocation2(UUID uuid) {
        return arenaLocation2.remove(uuid);
    }

    public void addPowerEnabled(UUID uuid, String name) {
        powerEnabled.remove(uuid);
        powerEnabled.put(uuid, name);
    }

    public boolean containsPowerEnabled(UUID uuid) {
        return powerEnabled.containsKey(uuid);
    }

    public String removePowerEnabled(UUID uuid) {
        return powerEnabled.remove(uuid);
    }

    public String getPowerEnabled(UUID uuid) {
        return powerEnabled.get(uuid);
    }

    public void addSpawnEnabled(UUID uuid, String name) {
        spawnEnabled.remove(uuid);
        spawnEnabled.put(uuid, name);
    }

    public boolean containsSpawnEnabled(UUID uuid) {
        return spawnEnabled.containsKey(uuid);
    }

    public String removeSpawnEnabled(UUID uuid) {
        return spawnEnabled.remove(uuid);
    }

    public String getSpawnEnabled(UUID uuid) {
        return spawnEnabled.get(uuid);
    }

    public void addCheckpointEnabled(UUID uuid, String name) {
        checkpointEnabled.remove(uuid);
        checkpointEnabled.put(uuid, name);
    }

    public boolean containsCheckpointEnabled(UUID uuid) {
        return checkpointEnabled.containsKey(uuid);
    }

    public String removeCheckpointEnabled(UUID uuid) {
        return checkpointEnabled.remove(uuid);
    }

    public String getCheckpointEnabled(UUID uuid) {
        return checkpointEnabled.get(uuid);
    }

    public void addCheckpointLocation1(UUID uuid, Location loc) {
        checkpointLocation1.remove(uuid);
        checkpointLocation1.put(uuid, loc);
    }

    public boolean containsCheckpointLocation1(UUID uuid) {
        return checkpointLocation1.containsKey(uuid);
    }

    public Location removeCheckpointLocation1(UUID uuid) {
        return checkpointLocation1.remove(uuid);
    }

    public void addCheckpointLocation2(UUID uuid, Location loc) {
        checkpointLocation2.remove(uuid);
        checkpointLocation2.put(uuid, loc);
    }

    public boolean containsCheckpointLocation2(UUID uuid) {
        return checkpointLocation2.containsKey(uuid);
    }

    public Location removeCheckpointLocation2(UUID uuid) {
        return checkpointLocation2.remove(uuid);
    }

}
