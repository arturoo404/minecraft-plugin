package com.arturoo404.minecraftplug.service;

import com.arturoo404.minecraftplug.MinecraftPlug;
import org.bukkit.Location;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import static org.bukkit.Bukkit.getServer;

public class BiomeDetector implements Listener{

    private final MinecraftPlug plugin;

    public BiomeDetector(MinecraftPlug plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void userLogin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        detectCurrentPlayerBiome(player);
    }

    private void detectCurrentPlayerBiome(Player p){
        new BukkitRunnable() {
            Biome biome = getServer().getWorld("world").getBlockAt(p.getLocation()).getBiome();

            @Override
            public void run() {
                if (!p.getLocation().getBlock().getBiome().equals(biome)){
                    biome = p.getLocation().getBlock().getBiome();

                    switch (biome){
                        case DESERT:
                            p.sendTitle("§6§lBlack Desert", "§6§l15-30 Level", 20, 60, 20);
                            break;
                        case FOREST:
                            p.sendTitle("§6§lLas", "§6§l1-10 Level", 20, 60, 20);
                            break;
                        case JUNGLE:
                            p.sendTitle("§6§lDżungla", "§6§l10-15 Level", 20, 60, 20);
                            break;
                        case SAVANNA:
                            p.sendTitle("§6§lSawanna", "§6§l20-30 Level", 20, 60, 20);
                            break;
                    }
                }
            }
        }.runTaskTimer(this.plugin, 0, 200);
    }
}
