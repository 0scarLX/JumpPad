package net.clustlight.spigot.jumppad;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;

public class PlayerActionListener implements Listener {

    public ArrayList<String> jumping = new ArrayList<>();

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        String name = player.getName();
        Location under = player.getLocation();
        under.setY(under.getY() - 1);
        if (player.getLocation().getBlock().getType().equals(Material.STONE_PRESSURE_PLATE)
                && under.getBlock().getType().equals(Material.EMERALD_BLOCK)) {
            if (!jumping.contains(name) && player.getGameMode().equals(GameMode.SURVIVAL)) {
                jumping.add(name);
            }
            player.playSound(player.getLocation(), Sound.ENTITY_WITHER_SHOOT, 0.6F, 0.7F);
            if (Math.abs(under.getPitch()) == 90) {
                player.setVelocity(player.getLocation().getDirection().multiply(4).setY(2));
            } else {
                player.setVelocity(event.getFrom().subtract(event.getTo()).toVector().multiply(4).setY(2));
            }

        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            String name = player.getName();
            if(event.getCause().equals(EntityDamageEvent.DamageCause.FALL) && jumping.contains(name)) {
                jumping.remove(name);
                event.setCancelled(true);
            }
        }
    }

}
