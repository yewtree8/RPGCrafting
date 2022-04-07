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
import uk.co.fordevelopment.rpg.inventory.holder.ActiveCraftingSessionHolder;
import uk.co.fordevelopment.rpg.util.ItemGenerator;

/**
 * Created by matty on 12/08/2017.
 */
public class ActiveCraftingMenuClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e)
    {
        ItemStack item = e.getCurrentItem();
        if(item==null || (item.getType()== Material.AIR) || (!item.hasItemMeta()))return;
        Player p = (Player) e.getWhoClicked();
        Inventory inventory = e.getInventory();
        if(inventory.getHolder() instanceof ActiveCraftingSessionHolder)
        {
            e.setCancelled(true);
            ActiveCraftingSessionHolder holder = (ActiveCraftingSessionHolder) inventory.getHolder();
            CraftingSession session = holder.getCraftingSession();
            if(item.equals(ItemGenerator.craftOneMore()))
            {
                session.addToCraft(1, p);
            }
            else if(item.equals(ItemGenerator.craftFiveExtra()))
            {
                session.addToCraft(5, p);
            }
            else if(item.equals(CraftPlugin.getOptions().getClaimItem()))
            {
                p.closeInventory();
                session.claimAndRemoveInstance();
            }
            else if(item.equals(ItemGenerator.cancelCraft()))
            {
                p.closeInventory();
                session.completeyCancelAndRefund();
                p.sendMessage(ChatColor.GOLD + "" + ChatColor.ITALIC + "Craft cancelled");
            }
            else if(session.isFinished())
            {
                p.closeInventory();
                session.claimAndRemoveInstance();
            }
        }
    }

}
