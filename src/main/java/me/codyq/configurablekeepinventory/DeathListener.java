package me.codyq.configurablekeepinventory;

import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

@RequiredArgsConstructor
public class DeathListener implements Listener {

    private final ConfigManager configManager;

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        EntityDamageEvent entityDamageEvent = player.getLastDamageCause();
        if (entityDamageEvent == null) return;
        EntityDamageEvent.DamageCause cause = entityDamageEvent.getCause();

        if (configManager.shouldKeepInventory(cause)) {
            event.setKeepInventory(true);
            event.setKeepLevel(true);
        }
    }

}
