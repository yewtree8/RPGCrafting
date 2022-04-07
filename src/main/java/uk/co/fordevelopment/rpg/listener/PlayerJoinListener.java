package uk.co.fordevelopment.rpg.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import uk.co.fordevelopment.rpg.players.CraftPlayer;

/**
 * Created by matty on 11/08/2017.
 */
public class PlayerJoinListener implements Listener {


    @EventHandler
    public void onJoin(PlayerJoinEvent e)
    {
        Player p = e.getPlayer();
        new CraftPlayer(p);
    }


}
