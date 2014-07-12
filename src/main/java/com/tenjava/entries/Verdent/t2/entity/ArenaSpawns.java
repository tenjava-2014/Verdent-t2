/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tenjava.entries.Verdent.t2.entity;

import com.tenjava.entries.Verdent.t2.entity.Arena;
import com.tenjava.entries.Verdent.t2.racing.RacingManager;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 *
 * @author Verdent
 */
public class ArenaSpawns {

    private final Arena arena;
    private Location[] spawns;
    private final HashMap<UUID, Location> fullSpawns;

    public ArenaSpawns(Location[] spawns, Arena arena) {
        this.arena = arena;
        this.spawns = spawns;
        this.fullSpawns = new HashMap<UUID, Location>();
    }

    public int getCapacity() {
        return spawns.length;
    }

    public int getTakenPlaces() {
        return fullSpawns.size();
    }

    public synchronized void movePlayersToSpawn() {
        for (Jockey jockey : arena.getJockeys().values()) {
            movePlayerToSpawn(jockey.getPlayer());
        }
    }

    public synchronized void movePlayerToSpawn(Player player) {
        Location l = getPlayerSpawn(player.getUniqueId());
        if (l == null) {
            return;
        }
        player.teleport(l);
    }

    public synchronized String givePlayerSpawn(Player player) {
        Location loc = getFirstEmpySpawn();
        UUID uuid = player.getUniqueId();
        if (loc == null) {
            return "There are no free spawns in this arena!";
        } else if (fullSpawns.containsKey(uuid)) {
            return "You are already member of this arena!";
        } else if (RacingManager.getInstance().isInArena(player)) {
            return "You are already member of another arena!";
        }
        player.teleport(loc);
        fullSpawns.put(uuid, loc);
        return "";
    }

    private synchronized Location getFirstEmpySpawn() {
        for (Location l : spawns) {
            if (l != null
                    && !fullSpawns.containsValue(l)) {
                return l.getBlock().getLocation();
            }
        }
        return null;
    }

    private synchronized Location getPlayerSpawn(UUID uuid) {
        return fullSpawns.get(uuid);
    }

    public synchronized void removePlayerSpawn(UUID uuid) {
        fullSpawns.remove(uuid);
    }

    public synchronized void registerSpawn(Location loc) {
        Location[] s = new Location[spawns.length + 1];
        System.arraycopy(spawns, 0, s, 0, spawns.length);
        s[spawns.length] = loc;
        spawns = s;
    }

    public synchronized boolean isArenaFull() {
        return getCapacity() == getTakenPlaces();
    }

    /*@Override
     public void save(PrintWriter logger) {
     for (int i = 0; i < spawns.length; i++) {
     Location loc = spawns[i];
     int x = loc.getBlockX();
     int y = loc.getBlockY();
     int z = loc.getBlockZ();
     logger.println("\t\t<spawn id=\"" + (i + 1) + "\">");
     logger.println("\t\t\t<x>" + x + "</x>");
     logger.println("\t\t\t<y>" + y + "</y>");
     logger.println("\t\t\t<z>" + z + "</z>");
     logger.println("\t\t</spawn>");
     }
     }*/
}
