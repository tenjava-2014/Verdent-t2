package com.tenjava.entries.Verdent.t2;

import com.tenjava.entries.Verdent.t2.racing.RacingCommand;
import com.tenjava.entries.Verdent.t2.boosts.*;
import com.tenjava.entries.Verdent.t2.listeners.JockeyListener;
import com.tenjava.entries.Verdent.t2.racing.BoostManager;
import com.tenjava.entries.Verdent.t2.racing.RacingManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class TenJava extends JavaPlugin {

    public static String NAME = "Verdent-t2";

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new JockeyListener(), this);
        BoostManager.getInstance().registerBoost(new SpeedBoost());
        BoostManager.getInstance().registerBoost(new GreaterSpeedBoost());
        for (Player player : getServer().getOnlinePlayers()) {
            RacingManager.getInstance().addJockey(player);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can be run only by player!");
            return false;
        }
        Player player = (Player) sender;
        if (label.equalsIgnoreCase("racing")) {
            RacingCommand rc = new RacingCommand();
            rc.onCommand(player, args);
        }
        return false;
    }

    @Override
    public void onDisable() {
        RacingManager.getInstance().clearAllArenas();
    }

}
