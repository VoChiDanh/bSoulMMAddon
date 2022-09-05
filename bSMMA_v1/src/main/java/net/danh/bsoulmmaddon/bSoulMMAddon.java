package net.danh.bsoulmmaddon;

import net.danh.bsoulmmaddon.Events.MobDeath;
import net.danh.dcore.DCore;
import org.bukkit.plugin.java.JavaPlugin;

public final class bSoulMMAddon extends JavaPlugin {

    private static bSoulMMAddon instance;

    public static bSoulMMAddon getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        DCore.RegisterDCore(this);
        if (getServer().getPluginManager().getPlugin("MythicMobs") != null) {
            getServer().getPluginManager().registerEvents(new MobDeath(), this);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
