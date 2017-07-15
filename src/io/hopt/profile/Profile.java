package io.hopt.profile;

import io.hopt.sql.MySQL;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerLeashEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

/**
 * Created by Owner on 15/07/2017.
 */
public class Profile implements Listener {

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if(!player.hasPlayedBefore()) {
            MySQL.registerPlayer(player.getUniqueId(), player, player.getAddress().getHostName().toString());
        } else {
            MySQL.dataExists(player.getUniqueId(), player);
        }
    }

    @EventHandler
    public void onPLayerQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        MySQL.saveProfile(player.getUniqueId(), player, player.getAddress().getHostName().toString());
    }
}
