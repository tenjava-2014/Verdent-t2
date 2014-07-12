package com.tenjava.entries.Verdent.t2;

import com.tenjava.entries.Verdent.t2.utils.EntitySpawnManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class TenJava extends JavaPlugin {

    @Override
    public void onEnable() {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (label.equalsIgnoreCase("test")) {
            EntitySpawnManager manager = new EntitySpawnManager();
            manager.spawnEntity(player.getLocation(), EntityType.ENDER_CRYSTAL);
        }
        return false;
    }

}
