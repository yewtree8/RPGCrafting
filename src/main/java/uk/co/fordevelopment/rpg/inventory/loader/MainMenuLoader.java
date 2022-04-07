package uk.co.fordevelopment.rpg.inventory.loader;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import uk.co.fordevelopment.rpg.craft.menu.MenuParent;
import uk.co.fordevelopment.rpg.inventory.holder.CategoryHolder;
import uk.co.fordevelopment.rpg.inventory.holder.ParentHolder;

import java.util.UUID;

/**
 * Created by matty on 10/08/2017.
 */
public class MainMenuLoader {

    private static String menuName = ChatColor.BLUE + "" + ChatColor.BOLD + "Select your craft";
    public static String getMenuName(){return menuName;}

    private UUID id;
    private Inventory inventory;
    private int inventorySize;
    private int invSize;
    private MenuParent menuParent;

    public MainMenuLoader(Player p, MenuParent parent)
    {
        this.menuParent = parent;
        invSize = 9;
        this.id = p.getUniqueId();
        this.inventory = p.getInventory();
        openInventory();
    }

    private void openInventory()
    {
        findInventorySize();
        inventory = Bukkit.createInventory(new ParentHolder(menuParent), invSize, menuName);
        Player p = Bukkit.getPlayer(id);
        p.openInventory(inventory);
        for(int i = 0 ; i < menuParent.getChildCategories().size() ; i++)
        {
            inventory.setItem(i, menuParent.getChildCategories().get(i).getGuiIcon());
        }
        p.updateInventory();
    }


    private void findInventorySize()
    {
        for(int i = 1 ; i <= 6 ; i++)
        {
            int size = i * 9;
            if(size >= menuParent.getChildCategories().size())
            {
                invSize = size;
                break;
            }
        }
    }

}
