package com.arturoo404.minecraftplug.service;

import com.arturoo404.minecraftplug.MinecraftPlug;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;

public class HpBarDetectByTargetEvent implements Listener {

    private static Set<LivingEntity> entities = new HashSet<>();
    private final MinecraftPlug plugin;

    public HpBarDetectByTargetEvent(MinecraftPlug plugin) {
        this.plugin = plugin;
    }

    //TODO boolean status = Boolean.parseBoolean(plugin.getConfig().getString("hp-bar.status"));
    @EventHandler
    public void event(EntityTargetEvent e) {
        if (e.getEntity().getType().equals(EntityType.EXPERIENCE_ORB)){
            return;
        }

        if (e.getReason().equals(EntityTargetEvent.TargetReason.FORGOT_TARGET)){
            e.getEntity().setCustomNameVisible(false);
            String[] name = e.getEntity().getName().split(":");
            e.getEntity().setCustomName(name[0]);
            entities.remove((LivingEntity) e.getEntity());
            return;
        }
            LivingEntity entity = (LivingEntity) e.getEntity();
            entities.add(entity);
    }

    public void runDetect() {

        new BukkitRunnable(){
            @Override
            public void run() {
                for (LivingEntity living : entities){
                    if (living.getType().name().equals("ARMOR_STAND")){
                        return;
                    }
                    if (living.getNoDamageTicks() > 100){
                        String[] name = living.getName().split(":");
                        living.setCustomName(name[0]);
                        return;
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
                        living.setCustomName(ChatColor.WHITE + mobName + ": " + ChatColor.GREEN + hpStr + ChatColor.RED + " ❤");
                        living.setCustomNameVisible(true);
                    }
                    if (v < 50 && v > 20) {
                        living.setCustomName(ChatColor.WHITE + mobName + ": " + ChatColor.YELLOW + hpStr + ChatColor.RED + " ❤");
                        living.setCustomNameVisible(true);
                    }
                    if (v <= 20) {
                        living.setCustomName(ChatColor.WHITE + mobName + ": " + ChatColor.RED + hpStr + ChatColor.RED + " ❤");
                        living.setCustomNameVisible(true);
                    }
                }
            }
        }.runTaskTimer(this.plugin, 0, 5);
    }
}
