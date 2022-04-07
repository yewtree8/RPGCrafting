package uk.co.fordevelopment.rpg.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import uk.co.fordevelopment.rpg.CraftPlugin;
import uk.co.fordevelopment.rpg.craft.crafting.CraftingSession;
import uk.co.fordevelopment.rpg.players.CraftPlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matty on 10/08/2017.
 */
public class PlayerQuitListener implements Listener {



    @EventHandler
    public void onLeave(PlayerQuitEvent e)
    {
        Player p = e.getPlayer();
        CraftPlayer craftPlayer = CraftPlayer.getPlayer(p);
        craftPlayer.remove();
    }

    private List<CraftingSession> getSessions(Player p)
    {
        List<CraftingSession> sessions = new ArrayList<>();
        CraftPlugin.getPlugin().getCraftingSessionManager().getAllSessions().forEach( session ->
        {
            if(session.getOwner().equals(p.getUniqueId()))
            {
                sessions.add(session);
            }
        });
        return sessions;
    }

}
