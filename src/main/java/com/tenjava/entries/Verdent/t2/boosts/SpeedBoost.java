/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tenjava.entries.Verdent.t2.boosts;

import com.tenjava.entries.Verdent.t2.TenJava;
import com.tenjava.entries.Verdent.t2.entity.Jockey;
import com.tenjava.entries.Verdent.t2.utils.HorseManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Horse;
import org.bukkit.scheduler.BukkitRunnable;

/**
 *
 * @author Verdent
 */
public class SpeedBoost extends Boost {

    public SpeedBoost() {
        super("Speed Boost");
    }

    @Override
    public void activate(final Jockey jockey) {
        HorseManager hm = new HorseManager();
        Horse horse = jockey.getHorse();
        double previousValue = hm.getHorseSpeed(horse);
        hm.setHorseSpeed(horse, previousValue * 1.5);
        new BukkitRunnable() {

            @Override
            public void run() {
                deactivate(jockey);
            }
        }.runTaskLater(Bukkit.getPluginManager().getPlugin(TenJava.NAME), 40);
    }

    @Override
    public void deactivate(Jockey jockey) {
        HorseManager hm = new HorseManager();
        Horse horse = jockey.getHorse();
        if (horse != null) {
            hm.setHorseSpeed(horse);
        }
    }

}
