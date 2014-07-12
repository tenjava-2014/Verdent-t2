/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tenjava.entries.Verdent.t2;

import com.tenjava.entries.Verdent.t2.racing.RacingManager;
import java.util.List;
import java.util.Set;
import org.bukkit.ChatColor;
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
            player.sendMessage(ChatColor.GREEN + "/racing arena - tools for arena maintenance");
            player.sendMessage(ChatColor.GREEN + "Created by Verdent");
            return;
        }
        String subComm = args[0];
        if (subComm.equalsIgnoreCase("list")) {
            listSubComm(player, args);
        }

    }

    public List<String> onTabComplete(Player player, String[] args) {
        return null;
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
}
