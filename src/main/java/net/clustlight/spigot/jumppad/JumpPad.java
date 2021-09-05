package net.clustlight.spigot.jumppad;

import org.bukkit.plugin.java.JavaPlugin;


public final class JumpPad extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new PlayerActionListener(), this);
    }

}
