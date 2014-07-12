/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tenjava.entries.Verdent.t2.listeners;

import com.tenjava.entries.Verdent.t2.boosts.IBoost;
import com.tenjava.entries.Verdent.t2.entity.Jockey;
import com.tenjava.entries.Verdent.t2.entity.PowerUp;
import com.tenjava.entries.Verdent.t2.racing.BoostManager;
import com.tenjava.entries.Verdent.t2.racing.RacingManager;
import com.tenjava.entries.Verdent.t2.utils.EntitySpawnManager;
import com.tenjava.entries.Verdent.t2.utils.FireworkManager;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;

/**
 *
 * @author Verdent
 */
public class JockeyListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (event.isCancelled()) {
            return;
        }
        Location loc = event.getTo();
        Player player = event.getPlayer();
        Jockey jockey = RacingManager.getInstance().getJockey(player);
        if (jockey != null) {
            if (!jockey.getArena().hasStarted()) {
                player.sendMessage(ChatColor.RED + "You cant move because of the game havent started yet!");
                return;
            }
            PowerUp powerUp = jockey.getArena().getPowerUp(loc);
            if (powerUp != null) {
                powerUp.pickUp();
                IBoost boost = BoostManager.getInstance().getRandomBoost();
                jockey.giveBoost(boost);
                FireworkManager.playStrictFirework(loc.add(0, 1, 0), FireworkEffect.Type.BALL, jockey.getColor());
                player.sendMessage("You have just picked up: " + boost.getName());
            }
            if (jockey.getCheckPoint() != null
                    && jockey.getCheckPoint().isInCheckpoint(loc)) {
                player.sendMessage(ChatColor.GREEN + "You have just reached checkpoin number: " + jockey.getCheckPoint().getCheckpointNumber());
                boolean next = RacingManager.getInstance().setNextCheckpoint(jockey);
                if (!next) {
                    jockey.getArena().sendMessageToAll(ChatColor.GOLD + player.getName() + " has reached finish line!!");
                    jockey.getArena().end();
                }
            }
        }
    }

    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();
        Jockey jockey = RacingManager.getInstance().getJockey(player);
        if (jockey != null) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDismount(VehicleExitEvent event) {
        if (event.getExited() instanceof Player) {
            Player player = (Player) event.getExited();
            Jockey jockey = RacingManager.getInstance().getJockey(player);
            if (jockey != null) {
                event.setCancelled(true);
            }
        }
    }

}
