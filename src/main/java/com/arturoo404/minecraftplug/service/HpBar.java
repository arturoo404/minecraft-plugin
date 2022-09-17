package com.arturoo404.minecraftplug.service;

import com.arturoo404.minecraftplug.MinecraftPlug;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

import static org.bukkit.Bukkit.getServer;

public class HpBar {

    private final MinecraftPlug plugin;

    public HpBar(MinecraftPlug plugin) {
        this.plugin = plugin;
    }

    public void entityHp(){
        new BukkitRunnable(){

            @Override
            public void run() {
                boolean status = Boolean.parseBoolean(plugin.getConfig().getString("hp-bar.status"));

                if (status) {
                    for (LivingEntity living : getServer().getWorld("world").getLivingEntities()) {
                        if (living.getType().name().equals("ARMOR_STAND")){
                            continue;
                        }
                        double entHp = living.getHealth();
                        String hpStr;

                        String[] name = living.getName().split(":");

                        String mobName = name[0];

                        if (entHp <= 10000) {
                            hpStr = String.valueOf((int) entHp);
                        } else if (entHp > 10000 && entHp < 1000000) {
                            hpStr = (int) entHp / 1000 + "K";
                        } else {
                            hpStr = (int) entHp / 1000000 + "M";
                        }

                        final double baseValue = living.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
                        double v = entHp / baseValue * 100;

                        if (v >= 50) {
                            living.setCustomName(ChatColor.WHITE + mobName + ": " + ChatColor.GREEN + hpStr);
                            living.setCustomNameVisible(true);
                        }
                        if (v < 50 && v > 20) {
                            living.setCustomName(ChatColor.WHITE + mobName + ": " + ChatColor.YELLOW + hpStr);
                            living.setCustomNameVisible(true);
                        }
                        if (v <= 20) {
                            living.setCustomName(ChatColor.WHITE + mobName + ": " + ChatColor.RED + hpStr);
                            living.setCustomNameVisible(true);
                        }

                    }
                }else {
                    for (LivingEntity living : getServer().getWorld("world").getLivingEntities()) {
                        if (living.getType().name().equals("ARMOR_STAND")){
                            living.setCustomName(null);
                            continue;
                        }

                        String[] name = living.getName().split(":");
                        living.setCustomName(name[0]);
                    }
                }
            }
        }.runTaskTimer(plugin, 0, 10);
    }
}
