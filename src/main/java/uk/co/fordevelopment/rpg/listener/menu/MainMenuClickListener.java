package uk.co.fordevelopment.rpg.listener.menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import uk.co.fordevelopment.rpg.craft.menu.MenuCategory;
import uk.co.fordevelopment.rpg.inventory.holder.ParentHolder;
import uk.co.fordevelopment.rpg.inventory.loader.CategoryMenuLoader;

/**
 * Created by matty on 10/08/2017.
 */
public class MainMenuClickListener implements Listener {


    @EventHandler
    public void onClick(InventoryClickEvent e)
    {
        ItemStack item = e.getCurrentItem();
        if(item==null || (item.getType()== Material.AIR) || (!item.hasItemMeta()))return;
        Player p = (Player) e.getWhoClicked();
        Inventory inv = e.getInventory();
        if(inv.getHolder() instanceof ParentHolder)
        {
            e.setCancelled(true);
            ParentHolder holder = (ParentHolder) inv.getHolder();
            MenuCategory category = holder.getMenuParent().getMenuCategoryBySlot(e.getSlot());
            if(category != null)
            {
                new CategoryMenuLoader(p, category);
            }
        }
    }


}
