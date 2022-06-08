package net.danh.bsoulmmaddon.Events;

import io.lumine.mythic.bukkit.BukkitAPIHelper;
import io.lumine.mythic.bukkit.events.MythicMobDeathEvent;
import net.danh.bsoul.Manager.Data;
import net.danh.bsoulmmaddon.bSoulMMAddon;
import org.bukkit.entity.Animals;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.Objects;

import static net.danh.bsoul.Manager.Resources.*;
import static net.danh.dcore.Random.Number.getRandomInt;
import static net.danh.dcore.Utils.Player.sendPlayerMessage;

public class MobDeath implements Listener {

    @EventHandler
    public void onMythicMobDeath(MythicMobDeathEvent e) {
        Player p = (Player) e.getKiller();
        String mob = e.getMobType().getInternalName();
        if (p == null) {
            return;
        }
        if (getconfigfile().getBoolean("HOOK.MYTHICMOBS")) {
            int max = getmobfile().getInt("MYTHICMOBS.DEFAULT.MAX");
            int min = getmobfile().getInt("MYTHICMOBS.DEFAULT.MIN");
            double chance = getmobfile().getInt("MYTHICMOBS.DEFAULT.CHANCE");
            if (max == 0 && min == 0 && chance == 0.0) {
                return;
            }
            for (String getEntityType : Objects.requireNonNull(getmobfile().getConfigurationSection("MYTHICMOBS.")).getKeys(false)) {
                if (mob.equalsIgnoreCase(getEntityType)) {
                    max = getmobfile().getInt("MYTHICMOBS." + mob + ".MAX");
                    min = getmobfile().getInt("MYTHICMOBS." + mob + ".MIN");
                    chance = getmobfile().getInt("MYTHICMOBS." + mob + ".CHANCE");
                    break;
                }
            }
            int soul = getRandomInt(min, max);
            double real_chance = Math.random() * 100.0D;
            if (chance >= real_chance) {
                Data.addSoul(p, soul);
                sendPlayerMessage(p, Objects.requireNonNull(getlanguagefile().getString("KILL_MOB")).replaceAll("%soul%", String.format("%,d", soul)).replaceAll("%mob%", e.getMob().getName()));
            }
        }
    }

    @EventHandler
    public void onKillVanillaMob(EntityDeathEvent e) {
        LivingEntity mob = e.getEntity();
        Player player = mob.getKiller();
        String vanillamobs = e.getEntityType().toString();
        if (player == null) {
            return;
        }
        if (!getconfigfile().getBoolean("MOBS.ENABLE")) {
            return;
        }
        if (bSoulMMAddon.getInstance().getServer().getPluginManager().getPlugin("MythicMobs") != null) {
            BukkitAPIHelper bukkitAPIHelper = new BukkitAPIHelper();
            if (bukkitAPIHelper.isMythicMob(mob)) {
                return;
            }
        }
        if (mob instanceof Animals) {
            if (!getconfigfile().getBoolean("MOBS.ANIMAL")) {
                return;
            }
        }
        if (mob instanceof Monster) {
            if (!getconfigfile().getBoolean("MOBS.MONSTER")) {
                return;
            }
        }
        int max = getmobfile().getInt("VANILLA.DEFAULT.MAX");
        int min = getmobfile().getInt("VANILLA.DEFAULT.MIN");
        double chance = getmobfile().getInt("VANILLA.DEFAULT.CHANCE");
        if (max == 0 && min == 0 && chance == 0.0) {
            return;
        }
        for (String getEntityType : Objects.requireNonNull(getmobfile().getConfigurationSection("Vanilla.")).getKeys(false)) {
            if (vanillamobs.equalsIgnoreCase(getEntityType)) {
                max = getmobfile().getInt("VANILLA." + vanillamobs + ".MAX");
                min = getmobfile().getInt("VANILLA." + vanillamobs + ".MIN");
                chance = getmobfile().getInt("VANILLA." + vanillamobs + ".CHANCE");
                break;
            }
        }
        int soul = getRandomInt(min, max);
        double real_chance = Math.random() * 100.0D;
        if (chance >= real_chance) {
            Data.addSoul(player, soul);
            sendPlayerMessage(player, Objects.requireNonNull(getlanguagefile().getString("KILL_MOB")).replaceAll("%soul%", String.format("%,d", soul)).replaceAll("%mob%", mob.getName()));
        }
    }
}
