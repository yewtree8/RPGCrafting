package uk.co.fordevelopment.rpg.listener.menu;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import uk.co.fordevelopment.rpg.craft.item.CraftableItem;
import uk.co.fordevelopment.rpg.craft.menu.MenuCategory;
import uk.co.fordevelopment.rpg.inventory.holder.CategoryHolder;
import uk.co.fordevelopment.rpg.inventory.holder.CraftingSessionDataHolder;
import uk.co.fordevelopment.rpg.inventory.loader.CraftConfirmationMenuLoader;
import uk.co.fordevelopment.rpg.players.CraftPlayer;

/**
 * Created by matty on 11/08/2017.
 */
public class MenuCategoryClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e)
    {
        ItemStack item = e.getCurrentItem();
        if(item==null || (item.getType()== Material.AIR) || (!item.hasItemMeta()))return;
        Player p = (Player) e.getWhoClicked();
        Inventory inv = e.getInventory();
        if(inv.getHolder() instanceof CategoryHolder)
        {
            CraftPlayer craftPlayer = CraftPlayer.getPlayer(p);
            e.setCancelled(true);
            CategoryHolder holder = (CategoryHolder) inv.getHolder();
            MenuCategory category = holder.getCategory();
            int slot = e.getSlot();
            CraftableItem selectedItem = category.getCraftableItemList().get(slot);
            if(selectedItem != null)
            {
                if(selectedItem.getCraftingRequirements().meetsRequirements(p))
                {
                    new CraftConfirmationMenuLoader(p, new CraftingSessionDataHolder(craftPlayer.getClickedBlock(), selectedItem));
                } else {
                    p.closeInventory();
                    p.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "You do not meet the crafting requirements for this item!" );
                }
            }
        }
    }

}
