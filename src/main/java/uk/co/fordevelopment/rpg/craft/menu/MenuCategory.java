package uk.co.fordevelopment.rpg.craft.menu;

import org.bukkit.inventory.ItemStack;
import uk.co.fordevelopment.rpg.craft.item.CraftableItem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by matty on 09/08/2017.
 */
public class MenuCategory {

    private static List<MenuCategory> allMenuCategories = new ArrayList<>();
    public static List<MenuCategory> getAllMenuCategories(){return allMenuCategories;}

    private ItemStack guiIcon;
    private String menuName;
    private int slot;
    private List<CraftableItem> craftableItemList;

    public MenuCategory(String name, ItemStack guiIcon, int slot)
    {
        this.menuName = name;
        this.guiIcon = guiIcon;
        this.craftableItemList = new ArrayList<>();
        this.slot = slot;



        allMenuCategories.add(this);
    }

    public int getSlot()
    {
        return slot;
    }

    public ItemStack getGuiIcon()
    {
        return guiIcon;
    }

    public List<CraftableItem> getCraftableItemList()
    {
        return craftableItemList;
    }

    public void addCraftableItem(CraftableItem item)
    {
        getCraftableItemList().add(item);
    }



    public String getMenuName()
    {
        return menuName;
    }




}
