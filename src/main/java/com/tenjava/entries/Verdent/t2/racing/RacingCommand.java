/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tenjava.entries.Verdent.t2.racing;

import com.tenjava.entries.Verdent.t2.entity.Arena;
import com.tenjava.entries.Verdent.t2.entity.Jockey;
import com.tenjava.entries.Verdent.t2.racing.RacingManager;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

/**
 *
 * @author Verdent
 */
public class RacingCommand {

    public void onCommand(Player player, String[] args) {
        if (args.length == 0) {
            player.sendMessage(ChatColor.GOLD + "Usage:");
            player.sendMessage(ChatColor.GREEN + "/racing list - list all created arenas");
            player.sendMessage(ChatColor.GREEN + "/racing join <arenaName> - join to the specific arena");
            player.sendMessage(ChatColor.GREEN + "/racing exit - leaves current arena");
            player.sendMessage(ChatColor.GREEN + "/racing arena - tools for arena maintenance");
            player.sendMessage(ChatColor.GREEN + "Created by Verdent");
            return;
        }
        String subComm = args[0];
        if (subComm.equalsIgnoreCase("list")) {
            listSubComm(player, args);
        } else if (subComm.equalsIgnoreCase("join")) {
            joinSubComm(player, args);
        } else if (subComm.equalsIgnoreCase("exit")) {
            exitSubComm(player, args);
        } else if (subComm.equalsIgnoreCase("arena")) {
            arenaSubComm(player, args);
        } else {
            player.sendMessage(ChatColor.RED + "Your command was not correct. Type /racing for more information");
        }
    }

    private void listSubComm(Player player, String[] args) {
        if (args.length != 1) {
            player.sendMessage(ChatColor.RED + "Usage:");
            player.sendMessage(ChatColor.RED + "/racing list - list all created arenas");
            return;
        }
        Set<String> names = RacingManager.getInstance().getArenas().keySet();
        player.sendMessage(ChatColor.GOLD + "Arenas:");
        if (names.isEmpty()) {
            player.sendMessage(ChatColor.RED + "There are no arenas created!");
        } else {
            String list = "";
            for (String str : names) {
                list = list.equals("") ? str : list + ", " + str;
            }
            player.sendMessage(ChatColor.GREEN + list);
        }
    }

    private void joinSubComm(Player player, String[] args) {
        if (args.length != 2) {
            player.sendMessage(ChatColor.RED + "Usage:");
            player.sendMessage(ChatColor.RED + "/racing join <arenaName> - join to the specific arena");
            return;
        }
        String arenaName = args[1];
        Arena arena = RacingManager.getInstance().getArena(arenaName);
        if (arena == null) {
            player.sendMessage(ChatColor.RED + "There is no arena with name: " + arenaName);
            return;
        }
        if (arena.addJockey(player)) {
            arena.sendMessageToAll(ChatColor.GOLD + player.getName() + " has joined arena!");
            player.sendMessage(ChatColor.GREEN + "You have joined arena succesfully");
        }
    }

    private void exitSubComm(Player player, String[] args) {
        if (args.length != 1) {
            player.sendMessage(ChatColor.RED + "Usage:");
            player.sendMessage(ChatColor.RED + "/racing exit - leaves current arena");
            return;
        }
        Jockey jockey = RacingManager.getInstance().getJockey(player);
        if (jockey == null) {
            player.sendMessage(ChatColor.RED + "You are not member of racing arena");
            return;
        }
        Arena arena = jockey.getArena();
        jockey.exitArena();
        arena.sendMessageToAll(ChatColor.GOLD + player.getName() + " has left arena!");
        player.sendMessage(ChatColor.GREEN + "You have succesfully left arena!");
    }

    private void arenaSubComm(Player player, String[] args) {
        if (!player.isOp()) {
            player.sendMessage(ChatColor.RED + "Sorry this command is for OP players only!");
            return;
        } else if (args.length == 1) {
            player.sendMessage(ChatColor.GOLD + "Usage:");
            player.sendMessage(ChatColor.GREEN + "/racing arena new <arenaName> - starts creating of new arena");
            player.sendMessage(ChatColor.GREEN + "/racing arena confirm - finishes up new arena");
            player.sendMessage(ChatColor.GREEN + "/racing arena cancel - cancel new arena");
            player.sendMessage(ChatColor.GREEN + "/racing arena destroy <arenaName> - removes target arena");
            player.sendMessage(ChatColor.GREEN + "/racing arena power <arenaName> on/off - activates creating of new power up spawns");
            player.sendMessage(ChatColor.GREEN + "/racing arena spawn <arenaName> on/off - activates creating of new spawns");
            player.sendMessage(ChatColor.GREEN + "/racing arena checkpoint <arenaName> on/off - activates creating of checkpoints");
            return;
        }
        String comm = args[1];
        if (comm.equalsIgnoreCase("new")) {
            newArenaCommand(player, args);
        } else if (comm.equalsIgnoreCase("confirm")) {
            confirmArenaCommand(player, args);
        } else if (comm.equalsIgnoreCase("cancel")) {
            cancelArenaCommand(player, args);
        } else if (comm.equalsIgnoreCase("destroy")) {
            destroyArenaCommand(player, args);
        } else if (comm.equalsIgnoreCase("power")) {
            powerArenaCommand(player, args);
        } else if (comm.equalsIgnoreCase("spawn")) {
            spawnArenaCommand(player, args);
        } else if (comm.equalsIgnoreCase("checkpoint")) {
            checkpointArenaCommand(player, args);
        } else {
            player.sendMessage(ChatColor.RED + "Your command was not correct. Type /racing arena for more information");
        }
    }

    private void newArenaCommand(Player player, String[] args) {
        if (args.length != 3) {
            player.sendMessage(ChatColor.RED + "Usage:");
            player.sendMessage(ChatColor.RED + "/racing arena new <arenaName> - starts creating of new arena");
            return;
        } else if (RacingManager.getInstance().containsArenaName(player.getUniqueId())) {
            player.sendMessage(ChatColor.BLUE + "Creating of new arena has been allowed before!");
            return;
        } else if (RacingManager.getInstance().containsPowerEnabled(player.getUniqueId())) {
            player.sendMessage(ChatColor.RED + "Turn of power up creating first!");
            return;
        } else if (RacingManager.getInstance().containsSpawnEnabled(player.getUniqueId())) {
            player.sendMessage(ChatColor.RED + "Turn of spawn creating first!");
            return;
        } else if (RacingManager.getInstance().containsCheckpointEnabled(player.getUniqueId())) {
            player.sendMessage(ChatColor.RED + "You have to turn off checkpoint creating first!");
            return;
        }
        String arenaName = args[2];
        RacingManager.getInstance().addArenaName(player.getUniqueId(), arenaName);
        player.sendMessage(ChatColor.GREEN + "Now you can start creating your arena");
        player.sendMessage(ChatColor.GOLD + "HINT: You have to have clear hands before selecting any blocks.");
        player.sendMessage(ChatColor.GOLD + "HINT: Select 2 blocks the same way as residence.");
    }

    private void confirmArenaCommand(Player player, String[] args) {
        UUID uuid = player.getUniqueId();
        RacingManager rm = RacingManager.getInstance();
        if (args.length != 2) {
            player.sendMessage(ChatColor.RED + "Usage:");
            player.sendMessage(ChatColor.RED + "/racing arena confirm - finishes up new arena");
            return;
        } else if (!rm.containsArenaName(uuid)) {
            player.sendMessage(ChatColor.RED + "There is no pending arena creating.");
            return;
        } else if (!rm.containsArenaLocation1(uuid)
                || !rm.containsArenaLocation2(uuid)) {
            player.sendMessage(ChatColor.RED + "You have to select both locations first!");
            return;
        }
        recalculatingLocations(player);
        String arenaName = rm.removeArenaName(uuid);
        Location location1 = rm.removeArenaLocation1(uuid);
        Location location2 = rm.removeArenaLocation2(uuid);
        Arena arena = new Arena(new Location[]{}, arenaName, location1, location2);
        rm.registerArena(arena);
        player.sendMessage(ChatColor.GREEN + "Arena has been created!");
    }

    private void cancelArenaCommand(Player player, String[] args) {
        UUID uuid = player.getUniqueId();
        RacingManager rm = RacingManager.getInstance();
        if (args.length != 2) {
            player.sendMessage(ChatColor.RED + "Usage:");
            player.sendMessage(ChatColor.RED + "/racing arena cancel - cancel new arena");
            return;
        } else if (!rm.containsArenaName(uuid)) {
            player.sendMessage(ChatColor.RED + "There is no pending arena creating.");
            return;
        }
        rm.removeArenaName(uuid);
        rm.removeArenaLocation1(uuid);
        rm.removeArenaLocation2(uuid);
        player.sendMessage(ChatColor.GREEN + "Creating of new arena has been canceled!");
    }

    private void destroyArenaCommand(Player player, String[] args) {
        if (args.length != 3) {
            player.sendMessage(ChatColor.RED + "Usage:");
            player.sendMessage(ChatColor.RED + "/racing arena destroy <arenaName> - removes target arena");
            return;
        }
        String arenaName = args[2];
        if (RacingManager.getInstance().removeArena(arenaName) == null) {
            player.sendMessage(ChatColor.RED + "There is no arena with this name!");
        } else {
            player.sendMessage(ChatColor.GREEN + "Arena has been destroyed");
        }
    }

    private void powerArenaCommand(Player player, String[] args) {
        UUID uuid = player.getUniqueId();
        RacingManager rm = RacingManager.getInstance();
        if (args.length != 4) {
            player.sendMessage(ChatColor.RED + "Usage:");
            player.sendMessage(ChatColor.RED + "/racing arena power <arenaName> on/off - activates creating of new power up spawns");
            return;
        } else if (rm.containsArenaName(uuid)) {
            player.sendMessage(ChatColor.RED + "You have pending arena creating!");
            return;
        } else if (rm.containsSpawnEnabled(uuid)) {
            player.sendMessage(ChatColor.RED + "You have to turn off spawn creating first!");
            return;
        } else if (rm.containsCheckpointEnabled(uuid)) {
            player.sendMessage(ChatColor.RED + "You have to turn off checkpoint creating first!");
            return;
        }
        String arenaName = args[2];
        String operator = args[3];
        Arena arena = rm.getArena(arenaName);
        if (arena == null) {
            player.sendMessage(ChatColor.RED + "There is no arena of this name: " + arenaName);
            return;
        }
        if (operator.equalsIgnoreCase("on")) {
            rm.addPowerEnabled(uuid, arenaName);
            player.sendMessage(ChatColor.GREEN + "Power up spawning has been enabled for arena: " + arenaName);
        } else if (operator.equalsIgnoreCase("off")) {
            rm.removePowerEnabled(uuid);
            player.sendMessage(ChatColor.GREEN + "Power up spawning has been canceled");
        } else {
            player.sendMessage(ChatColor.RED + "Only on/off allowed");
        }
    }

    private void spawnArenaCommand(Player player, String[] args) {
        UUID uuid = player.getUniqueId();
        RacingManager rm = RacingManager.getInstance();
        if (args.length != 4) {
            player.sendMessage(ChatColor.RED + "Usage:");
            player.sendMessage(ChatColor.RED + "/racing arena spawn <arenaName> on/off - activates creating of new spawns");
            return;
        } else if (rm.containsArenaName(uuid)) {
            player.sendMessage(ChatColor.RED + "You have pending arena creating!");
            return;
        } else if (rm.containsPowerEnabled(uuid)) {
            player.sendMessage(ChatColor.RED + "You have to turn off power up creating first!");
            return;
        } else if (rm.containsCheckpointEnabled(uuid)) {
            player.sendMessage(ChatColor.RED + "You have to turn off checkpoint creating first!");
            return;
        }
        String arenaName = args[2];
        String operator = args[3];
        Arena arena = rm.getArena(arenaName);
        if (arena == null) {
            player.sendMessage(ChatColor.RED + "There is no arena of this name: " + arenaName);
            return;
        }
        if (operator.equalsIgnoreCase("on")) {
            rm.addSpawnEnabled(uuid, arenaName);
            player.sendMessage(ChatColor.GREEN + "Spawn creating has been enabled for arena: " + arenaName);
        } else if (operator.equalsIgnoreCase("off")) {
            rm.removeSpawnEnabled(uuid);
            player.sendMessage(ChatColor.GREEN + "Spawn creating has been canceled");
        } else {
            player.sendMessage(ChatColor.RED + "Only on/off allowed");
        }
    }

    private void checkpointArenaCommand(Player player, String[] args) {
        UUID uuid = player.getUniqueId();
        RacingManager rm = RacingManager.getInstance();
        if (args.length != 4) {
            player.sendMessage(ChatColor.RED + "Usage:");
            player.sendMessage(ChatColor.RED + "/racing arena spawn <arenaName> on/off - activates creating of new spawns");
            return;
        } else if (rm.containsArenaName(uuid)) {
            player.sendMessage(ChatColor.RED + "You have pending arena creating!");
            return;
        } else if (rm.containsPowerEnabled(uuid)) {
            player.sendMessage(ChatColor.RED + "You have to turn off power up creating first!");
            return;
        } else if (rm.containsSpawnEnabled(uuid)) {
            player.sendMessage(ChatColor.RED + "You have to turn off spawn creating first!");
            return;
        }
        String arenaName = args[2];
        String operator = args[3];
        Arena arena = rm.getArena(arenaName);
        if (arena == null) {
            player.sendMessage(ChatColor.RED + "There is no arena of this name: " + arenaName);
            return;
        }
        if (operator.equalsIgnoreCase("on")) {
            rm.addCheckpointEnabled(uuid, arenaName);
            player.sendMessage(ChatColor.GREEN + "Checkpoint creating has been enabled for arena: " + arenaName);
        } else if (operator.equalsIgnoreCase("off")) {
            rm.removeCheckpointEnabled(uuid);
            player.sendMessage(ChatColor.GREEN + "Checkpoint creating has been canceled");
        } else {
            player.sendMessage(ChatColor.RED + "Only on/off allowed");
        }
    }

    private void recalculatingLocations(Player player) {
        UUID uuid = player.getUniqueId();
        Location l1 = RacingManager.getInstance().removeArenaLocation1(uuid);
        Location l2 = RacingManager.getInstance().removeArenaLocation2(uuid);
        double x1;
        double y1;
        double z1;
        double x2;
        double y2;
        double z2;
        World world = player.getWorld();

        if (l1.getX() < l2.getX()) {
            x1 = l2.getX();
            x2 = l1.getX();
            l1 = new Location(world, x1, l1.getY(), l1.getZ());
            l2 = new Location(world, x2, l2.getY(), l2.getZ());
        }

        if (l1.getY() < l2.getY()) {
            y1 = l2.getY();
            y2 = l1.getY();
            l1 = new Location(world, l1.getX(), y1, l1.getZ());
            l2 = new Location(world, l2.getX(), y2, l2.getZ());
        }
        if (l1.getZ() < l2.getZ()) {
            z1 = l2.getZ();
            z2 = l1.getZ();
            l1 = new Location(world, l1.getX(), l1.getY(), z1);
            l2 = new Location(world, l2.getX(), l2.getY(), z2);
        }
        RacingManager.getInstance().addArenaLocation1(uuid, l1);
        RacingManager.getInstance().addArenaLocation2(uuid, l2);
    }

}
