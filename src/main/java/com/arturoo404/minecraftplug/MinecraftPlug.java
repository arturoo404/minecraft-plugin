package com.arturoo404.minecraftplug;

import com.arturoo404.minecraftplug.commands.HpBarCommands;
import com.arturoo404.minecraftplug.service.BiomeDetector;
import com.arturoo404.minecraftplug.service.HpBar;
import org.bukkit.plugin.java.JavaPlugin;

public final class MinecraftPlug extends JavaPlugin {

    @Override
    public void onEnable() {
        new HpBar(this).entityHp();
        getCommand("hpbar-status").setExecutor(new HpBarCommands(this));
        getServer().getPluginManager().registerEvents(new BiomeDetector(this), this);
    }

}
