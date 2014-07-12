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

/**
 *
 * @author Verdent
 */
public class JockeyListener implements Listener {

    @EventHandler
    public void onPlayerInterractEvent(PlayerInteractEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            Location location = event.getClickedBlock().getLocation().add(0, 1, 0);
            EntitySpawnManager manager = new EntitySpawnManager();
            Entity entity = manager.spawnEntity(location, EntityType.ENDER_CRYSTAL);
            RacingManager.getInstance().addPowerUp(entity);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (event.isCancelled()) {
            return;
        }
        Location loc = event.getTo();
        Player player = event.getPlayer();
        PowerUp powerUp = RacingManager.getInstance().getPowerUp(loc);
        if (powerUp != null) {
            powerUp.pickUp();
            IBoost boost = BoostManager.getInstance().getRandomBoost();
            Jockey jockey = RacingManager.getInstance().getJockey(player.getUniqueId());
            jockey.giveBoost(boost);
            FireworkManager.playStrictFirework(loc.add(0, 1, 0), FireworkEffect.Type.BALL, jockey.getColor());
            player.sendMessage("You have just picked up: " + boost.getName());
        }

    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {

    }

}
