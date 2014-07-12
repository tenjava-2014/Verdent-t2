/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tenjava.entries.Verdent.t2.listeners;

import com.tenjava.entries.Verdent.t2.racing.RacingManager;
import java.util.UUID;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 *
 * @author User
 */
public class ArenaListener implements Listener {

    @EventHandler
    public void onPlayerInterract(PlayerInteractEvent event) {
        if (event.isCancelled()) {
            return;
        }
        Player p = event.getPlayer();
        Location l = event.getClickedBlock().getLocation();
        boolean clearHands = p.getItemInHand() == null || p.getItemInHand().getType() == Material.AIR;
        Action action = event.getAction();
        if (RacingManager.getInstance().containsArenaName(p.getUniqueId())) {
            event.setCancelled(arenaCreating(p, l, action, clearHands));
        }
    }

    private boolean arenaCreating(Player player, Location l, Action action, boolean clearHands) {
        if (!clearHands) {
            return false;
        }
        UUID uuid = player.getUniqueId();
        if (action == Action.RIGHT_CLICK_BLOCK) {
            RacingManager.getInstance().addArenaLocation2(uuid, l);
            if (RacingManager.getInstance().containsArenaLocation1(uuid)) {
                player.sendMessage(ChatColor.GREEN + "Now you have both locations selected.");
                player.sendMessage(ChatColor.GOLD + "Use /recing arena confirm OR /recing arena cancel");
            } else {
                player.sendMessage(ChatColor.GREEN + "Location of the first block set. Please select second one by your left mouse button.");
            }
        } else if (action == Action.LEFT_CLICK_BLOCK) {
            RacingManager.getInstance().addArenaLocation1(uuid, l);
            if (RacingManager.getInstance().containsArenaLocation2(uuid)) {
                player.sendMessage(ChatColor.GREEN + "Now you have both locations selected.");
                player.sendMessage(ChatColor.GOLD + "Use /recing arena confirm OR /recing arena cancel");
            } else {
                player.sendMessage(ChatColor.GREEN + "Location of the first block set. Please select second one by your right mouse button.");
            }
        }
        return true;
    }

}
