/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tenjava.entries.Verdent.t2.entity;

import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;

/**
 *
 * @author Verdent
 */
public class Jokey {

    private final Player player;
    private Horse horse;

    public Jokey(Player player, Horse horse) {
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

}
