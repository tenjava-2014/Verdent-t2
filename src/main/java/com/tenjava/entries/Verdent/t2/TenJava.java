package com.tenjava.entries.Verdent.t2;

import com.tenjava.entries.Verdent.t2.boosts.SpeedBoost;
import com.tenjava.entries.Verdent.t2.listeners.JockeyListener;
import com.tenjava.entries.Verdent.t2.racing.BoostManager;
import com.tenjava.entries.Verdent.t2.racing.RacingManager;
import com.tenjava.entries.Verdent.t2.utils.HorseManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class TenJava extends JavaPlugin {

    public static String NAME = "Verdent-t2";

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new JockeyListener(), this);
        BoostManager.getInstance().registerBoost(new SpeedBoost());
        for (Player player : getServer().getOnlinePlayers()) {
            RacingManager.getInstance().addJockey(player);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (label.equalsIgnoreCase("test")) {
        }
        return false;
    }

    @Override
    public void onDisable() {
        RacingManager.getInstance().removeAllPowerUps();
        RacingManager.getInstance().removeAllHorses();
    }

}
