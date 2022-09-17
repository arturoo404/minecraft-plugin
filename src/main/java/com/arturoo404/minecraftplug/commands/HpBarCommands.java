package com.arturoo404.minecraftplug.commands;

import com.arturoo404.minecraftplug.MinecraftPlug;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HpBarCommands implements CommandExecutor {

    private final MinecraftPlug plugin;

    public HpBarCommands(MinecraftPlug plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            Player p = (Player) sender;

            if (args.length == 0){
                p.sendMessage("Use /hpbar-status <true/false>");
            }else {
                boolean status = Boolean.parseBoolean(args[0]);
                plugin.getConfig().set("hp-bar.status", status);
            }
        }
        return false;
    }
}
