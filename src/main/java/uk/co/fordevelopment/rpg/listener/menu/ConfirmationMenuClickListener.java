package uk.co.fordevelopment.rpg.listener.menu;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import uk.co.fordevelopment.rpg.CraftPlugin;
import uk.co.fordevelopment.rpg.craft.crafting.CraftingSession;
import uk.co.fordevelopment.rpg.inventory.holder.CraftingSessionDataHolder;
import uk.co.fordevelopment.rpg.inventory.loader.CategoryMenuLoader;
import uk.co.fordevelopment.rpg.players.CraftPlayer;

/**
 * Created by matty on 12/08/2017.
 */
public class ConfirmationMenuClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e)
    {
        ItemStack item = e.getCurrentItem();
        if(item==null || (item.getType()== Material.AIR) || (!item.hasItemMeta()))return;
        Player p = (Player) e.getWhoClicked();
        Inventory inv = e.getInventory();
        if(inv.getHolder() instanceof CraftingSessionDataHolder)
        {
            CraftPlayer cp = CraftPlayer.getPlayer(p);

            e.setCancelled(true);
            CraftingSessionDataHolder holder = (CraftingSessionDataHolder) inv.getHolder();
            if(item.equals(CraftPlugin.getOptions().getCancelItem()))
            {
                new CategoryMenuLoader(p, holder.getItem().getMenuCategory());
            }
            else if(ChatColor.stripColor(item.getItemMeta().getDisplayName()).equalsIgnoreCase("Craft"))
            {
                CraftingSession session = CraftPlugin.getPlugin().getCraftingSessionManager().getCraftingSession(cp.getClickedBlock());
                if(session == null)
                {
                    p.closeInventory();
                    new CraftingSession(p, cp.getClickedBlock(), holder.getItem());
                } else {
                    p.closeInventory();
                    p.sendMessage(session.getOccupiedMessage());
                }
            }
        }
    }

}
