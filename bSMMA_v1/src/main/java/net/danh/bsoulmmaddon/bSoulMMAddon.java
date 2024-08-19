package net.danh.bsoulmmaddon;

import net.danh.bsoul.bSoul;
import net.danh.bsoulmmaddon.Events.MobDeath;
import org.bukkit.plugin.java.JavaPlugin;

public final class bSoulMMAddon extends JavaPlugin {

    private static bSoulMMAddon instance;

    public static bSoulMMAddon getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        if (getServer().getPluginManager().getPlugin("MythicMobs") != null) {
            getServer().getPluginManager().registerEvents(new MobDeath(), bSoul.getInstance());
            getLogger().info("Hooked with MythicMobs");
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
