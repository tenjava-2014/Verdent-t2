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
import java.util.Random;
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
    private final HashMap<String, Arena> arenas = new HashMap<String, Arena>();

    private RacingManager() {
    }

    public static RacingManager getInstance() {
        return SINGLETON;
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

    public HashMap<String, Arena> getArenas() {
        return arenas;
    }

    public Arena getArena(String name) {
        return arenas.get(name);
    }

    public void clearAllArenas() {
        for (Arena arena : arenas.values()) {
            arena.removeAllPowerUps();
            arena.removeAllHorses();
        }
    }

    public void addJockey(Player player) {
        if (this.arenas.isEmpty()) {
            throw new IllegalStateException("There are no active arenas!");
        }
        Random random = new Random();
        int index = random.nextInt(this.arenas.size());
        /*System.out.println(this.boosts.size());
         System.out.println(index);
         index = index < 0 ? 0 : index;
         System.out.println(index);*/
        String arenaName = (String) this.arenas.keySet().toArray()[index];
        Arena arena = arenas.get(arenaName);
        arena.addJockey(player);
    }

    public Jockey getJockey(Player player) {
        return getJockey(player.getUniqueId());
    }

    public Jockey getJockey(UUID uuid) {
        for (Arena arena : arenas.values()) {
            Jockey jockey = getJockey(uuid);
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

}
