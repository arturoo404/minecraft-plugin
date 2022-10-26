package com.arturoo404.minecraftplug;

import com.arturoo404.minecraftplug.commands.HpBarCommands;
import com.arturoo404.minecraftplug.service.BiomeDetector;
import com.arturoo404.minecraftplug.service.HpBarDetectByTargetEvent;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class MinecraftPlug extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        new HpBarDetectByTargetEvent(this).runDetect();
        getCommand("hpbar-status").setExecutor(new HpBarCommands(this));
        getServer().getPluginManager().registerEvents(new BiomeDetector(this), this);
        getServer().getPluginManager().registerEvents(new HpBarDetectByTargetEvent(this), this);
        getServer().getPluginManager().registerEvents(this, this);
    }
}
