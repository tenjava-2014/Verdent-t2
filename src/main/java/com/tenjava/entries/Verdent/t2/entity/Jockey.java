/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tenjava.entries.Verdent.t2.entity;

import com.tenjava.entries.Verdent.t2.boosts.IBoost;
import java.util.UUID;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;

/**
 *
 * @author Verdent
 */
public class Jockey {

    private final Player player;
    private final Color color;
    private Horse horse;
    private Checkpoint nextCheckPoint;

    public Jockey(Player player, Color color, Horse horse) {
        this.player = player;
        this.color = color;
        this.horse = horse;
    }

    public Horse getHorse() {
        return horse;
    }

    public void setHorse(Horse horse) {
        this.horse = horse;
    }

    public Color getColor() {
        return color;
    }

    public Player getPlayer() {
        return player;
    }

    public Checkpoint getNextCheckPoint() {
        return nextCheckPoint;
    }

    public void setNextCheckPoint(Checkpoint checkpoint) {
        this.nextCheckPoint = checkpoint;
    }

    public RacingLap getCurrentLap() {
        return nextCheckPoint.getLap();
    }

    public UUID getPlayerUUID() {
        return player.getUniqueId();
    }

    public void giveBoost(IBoost boost) {
        boost.activate(this);
    }

    public Arena getArena() {
        return getCurrentLap().getArena();
    }

}
