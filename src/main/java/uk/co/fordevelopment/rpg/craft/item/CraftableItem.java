package uk.co.fordevelopment.rpg.craft.item;

import org.bukkit.inventory.ItemStack;
import uk.co.fordevelopment.rpg.craft.menu.MenuCategory;

import java.util.HashSet;
import java.util.List;

/**
 * Created by matty on 09/08/2017.
 */
public class CraftableItem {

    private static HashSet<CraftableItem> allItems = new HashSet<>();
    public static HashSet<CraftableItem> getAllItems(){return allItems;}

    private int slot;
    private int craftingTime;
    private ItemStack finalResult;
    private ItemStack guiIcon;
    private CraftingRequirements craftingRequirements;
    private MenuCategory menuCategory;
    private List<String> commands;

    public CraftableItem(int slot, int craftingTime, ItemStack guiIcon, CraftingRequirements requirements, List<String> commands, ItemStack finalResult, MenuCategory menuCategory)
    {
        this.slot = slot;
        this.craftingTime = craftingTime;
        this.guiIcon = guiIcon;
        this.craftingRequirements = requirements;
        this.finalResult = finalResult;
        this.menuCategory = menuCategory;
        this.commands = commands;

        getMenuCategory().getCraftableItemList().add(this);
        allItems.add(this);
    }

    public List<String> getCommands()
    {
        return this.commands;
    }

    public int getCraftingTime()
    {
        return this.craftingTime;
    }

    public MenuCategory getMenuCategory()
    {
        return this.menuCategory;
    }

    public ItemStack getGuiIcon()
    {
        return this.guiIcon;
    }

    public int getSlot()
    {
        return this.slot;
    }

    public CraftingRequirements getCraftingRequirements()
    {
        return this.craftingRequirements;
    }

    public ItemStack getFinalResult()
    {
        return this.finalResult;
    }

    public static CraftableItem getItem(MenuCategory category, int slot)
    {
        return category.getCraftableItemList().stream().filter( item -> item.getSlot() == slot).findFirst().orElse(null);
    }

    public static CraftableItem getItem(ItemStack guiIcon)
    {
        return getAllItems().stream().filter( item -> (item.getGuiIcon().getItemMeta().getDisplayName().equalsIgnoreCase(guiIcon.getItemMeta().getDisplayName()))).findFirst().orElse(null);
    }

}
