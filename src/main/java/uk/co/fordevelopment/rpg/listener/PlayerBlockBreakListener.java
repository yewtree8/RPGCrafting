package uk.co.fordevelopment.rpg.listener;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import uk.co.fordevelopment.rpg.CraftPlugin;
import uk.co.fordevelopment.rpg.craft.crafting.CraftingSession;

/**
 * Created by matty on 12/08/2017.
 */
public class PlayerBlockBreakListener implements Listener {


    @EventHandler
    public void onBreak(BlockBreakEvent e)
    {
        Player p = e.getPlayer();
        CraftingSession session = CraftPlugin.getPlugin().getCraftingSessionManager().getCraftingSession(e.getBlock());
        if(session != null)
        {
            p.sendMessage(ChatColor.RED + "You cannot break this work station while it is trying to craft!");
            e.setCancelled(true);
        }
    }

}
