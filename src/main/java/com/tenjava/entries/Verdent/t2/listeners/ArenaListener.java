/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tenjava.entries.Verdent.t2.listeners;

import com.tenjava.entries.Verdent.t2.entity.Arena;
import com.tenjava.entries.Verdent.t2.racing.RacingManager;
import com.tenjava.entries.Verdent.t2.utils.EntitySpawnManager;
import java.util.UUID;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
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
        RacingManager rm = RacingManager.getInstance();
        Player p = event.getPlayer();
        Location l = event.getClickedBlock().getLocation();
        boolean clearHands = p.getItemInHand() == null || p.getItemInHand().getType() == Material.AIR;
        Action action = event.getAction();
        if (rm.containsArenaName(p.getUniqueId())) {
            event.setCancelled(arenaCreating(p, l, action, clearHands));
        } else if (rm.containsPowerEnabled(p.getUniqueId())) {
            event.setCancelled(powerUpCreating(p, l, action, clearHands));
        } else if (rm.containsSpawnEnabled(p.getUniqueId())) {
            event.setCancelled(spawnCreating(p, l, action, clearHands));
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
                player.sendMessage(ChatColor.GOLD + "Use /racing arena confirm OR /racing arena cancel");
            } else {
                player.sendMessage(ChatColor.GREEN + "Location of the first block set. Please select second one by your left mouse button.");
            }
        } else if (action == Action.LEFT_CLICK_BLOCK) {
            RacingManager.getInstance().addArenaLocation1(uuid, l);
            if (RacingManager.getInstance().containsArenaLocation2(uuid)) {
                player.sendMessage(ChatColor.GREEN + "Now you have both locations selected.");
                player.sendMessage(ChatColor.GOLD + "Use /racing arena confirm OR /racing arena cancel");
            } else {
                player.sendMessage(ChatColor.GREEN + "Location of the first block set. Please select second one by your right mouse button.");
            }
        }
        return true;
    }

    private boolean powerUpCreating(Player player, Location location, Action action, boolean clearHands) {
        if (!clearHands) {
            return false;
        }
        RacingManager rm = RacingManager.getInstance();
        if (!rm.isInArena(location)) {
            player.sendMessage(ChatColor.RED + "Power up has to be inside arena!");
            return true;
        }
        UUID uuid = player.getUniqueId();
        if (action == Action.RIGHT_CLICK_BLOCK
                || action == Action.LEFT_CLICK_BLOCK) {
            EntitySpawnManager manager = new EntitySpawnManager();
            Entity entity = manager.spawnEntity(location.add(0, 1, 0), EntityType.ENDER_CRYSTAL);
            String arenaName = RacingManager.getInstance().getPowerEnabled(uuid);
            Arena arena = RacingManager.getInstance().getArena(arenaName);
            arena.addPowerUp(entity, 100);
            player.sendMessage(ChatColor.GREEN + "New power up has been set up.");
        }
        return true;
    }

    private boolean spawnCreating(Player player, Location location, Action action, boolean clearHands) {
        if (!clearHands) {
            return false;
        }
        RacingManager rm = RacingManager.getInstance();
        if (!rm.isInArena(location)) {
            player.sendMessage(ChatColor.RED + "Spawn has to be inside arena!");
            return true;
        }
        UUID uuid = player.getUniqueId();
        if (action == Action.RIGHT_CLICK_BLOCK
                || action == Action.LEFT_CLICK_BLOCK) {
            String arenaName = RacingManager.getInstance().getSpawnEnabled(uuid);
            Arena arena = RacingManager.getInstance().getArena(arenaName);
            if (!arena.addSpawn(location)) {
                player.sendMessage(ChatColor.RED + "Full capacity of spawns has been reached!");
            } else {
                player.sendMessage(ChatColor.GREEN + "New spawn has been set up.");
            }
        }
        return true;
    }

}
