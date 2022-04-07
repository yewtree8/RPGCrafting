package uk.co.fordevelopment.rpg.inventory.loader;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import uk.co.fordevelopment.rpg.craft.item.CraftableItem;
import uk.co.fordevelopment.rpg.craft.menu.MenuCategory;
import uk.co.fordevelopment.rpg.craft.menu.MenuParent;
import uk.co.fordevelopment.rpg.inventory.holder.CategoryHolder;
import uk.co.fordevelopment.rpg.inventory.holder.ParentHolder;

import java.util.List;
import java.util.UUID;

/**
 * Created by matty on 10/08/2017.
 */
public class CategoryMenuLoader {


    private UUID id;
    private Inventory inventory;
    private int invSize;
    private MenuCategory category;
    private List<CraftableItem> itemList;


    public CategoryMenuLoader(Player p, MenuCategory category)
    {
        this.category = category;
        this.itemList = category.getCraftableItemList();
        invSize = 9;
        this.id = p.getUniqueId();
        this.inventory = p.getInventory();
        openInventory();
    }

    private void openInventory()
    {
        findInventorySize();
        inventory = Bukkit.createInventory(new CategoryHolder(category), invSize, ChatColor.BLUE + "" + ChatColor.BOLD + StringUtils.capitalize(category.getMenuName()));
        Player p = Bukkit.getPlayer(id);
        p.openInventory(inventory);
        itemList.forEach( item ->
            inventory.setItem(item.getSlot(), item.getGuiIcon())
        );
        p.updateInventory();
    }


    private void findInventorySize()
    {
        for(int i = 1 ; i <= 6 ; i++)
        {
            int size = i * 9;
            if(size >= itemList.size())
            {
                invSize = size;
                break;
            }
        }
    }

}
