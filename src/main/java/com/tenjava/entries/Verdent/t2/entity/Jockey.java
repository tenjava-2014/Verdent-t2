/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tenjava.entries.Verdent.t2.entity;

import java.util.UUID;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;

/**
 *
 * @author Verdent
 */
public class Jockey {

    private final Player player;
    private Horse horse;
    private Checkpoint nextCheckPoint;

    public Jockey(Player player, Horse horse) {
        this.player = player;
        this.horse = horse;
    }

    public Horse getHorse() {
        return horse;
    }

    public void setHorse(Horse horse) {
        this.horse = horse;
    }

    public Player getPlayer() {
        return player;
    }

    public Checkpoint getNextCheckPoint() {
        return nextCheckPoint;
    }

    public RacingLap getCurrentLap() {
        return nextCheckPoint.getLap();
    }

    public UUID getPlayerUUID() {
        return player.getUniqueId();
    }

}
