package uk.co.fordevelopment.rpg.listener;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import uk.co.fordevelopment.rpg.CraftPlugin;
import uk.co.fordevelopment.rpg.craft.menu.MenuParent;
import uk.co.fordevelopment.rpg.craft.crafting.CraftingSession;
import uk.co.fordevelopment.rpg.inventory.loader.MainMenuLoader;
import uk.co.fordevelopment.rpg.players.CraftPlayer;

/**
 * Created by matty on 10/08/2017.
 */
public class PlayerInteractListener implements Listener {


    @EventHandler
    public void onInteract(PlayerInteractEvent e)
    {
        Player p = e.getPlayer();
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK)
        {
            Block block = e.getClickedBlock();
            if(block!=null)
            {
                CraftPlayer cp = CraftPlayer.getPlayer(p);
                cp.setBlock(block);
                if(!CraftPlugin.getPlugin().getCraftingSessionManager().isCraftingSessionActive(block))
                {
                    MenuParent parent = MenuParent.getMenuParentFromBlock(p, block);

                    if(parent != null)
                    {
                        if(CraftPlugin.getPlugin().getCraftingSessionManager().hasCraftingSessionActive(p))
                        {
                            e.setCancelled(true);
                            p.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "You are already crafting something!");
                            return;
                        }
                        else
                        {
                            e.setCancelled(true);
                            new MainMenuLoader(p, parent);
                        }
                    }

                }
                else
                {
                    CraftingSession session = CraftPlugin.getPlugin().getCraftingSessionManager().getCraftingSession(block);

                    if(CraftPlugin.getPlugin().getCraftingSessionManager().hasCraftingSessionActiveAt(block, p))
                    {
                        e.setCancelled(true);
                        session.openCraftingSessionInventory(p);
                    }
                    else
                    {
                        p.sendMessage(CraftPlugin.getOptions().getCraftingSessionOccupiedBase(session.getOwnerName()));
                    }


                }
            }
        }
    }

}
