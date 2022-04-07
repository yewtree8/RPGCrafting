package uk.co.fordevelopment.rpg.craft.menu;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import uk.co.fordevelopment.rpg.CraftPlugin;

import java.util.HashSet;
import java.util.List;

/**
 * Created by matty on 10/08/2017.
 */
public class MenuParent {

    private static HashSet<MenuParent> parentList = new HashSet<>();
    public static HashSet<MenuParent> getParentList(){return parentList;}

    private String name;

    private List<MenuCategory> childCategories;

    private List<Material> craftingBlocks;

    private MenuRequirements requirements;

    public MenuParent(String name, List<Material> blocks, List<MenuCategory> menus, MenuRequirements requirements)
    {
        this.name = name;
        this.requirements = requirements;
        this.childCategories = menus;
        this.craftingBlocks = blocks;
        this.childCategories = menus;
        parentList.add(this);
        CraftPlugin.log("New Menu parent created! Name: " + name + ", Categories: " + menus.size());
    }

    public MenuRequirements getRequirements()
    {
        return requirements;
    }

    public List<Material> getCraftingBlocks()
    {
        return craftingBlocks;
    }

    public List<MenuCategory> getChildCategories()
    {
        return childCategories;
    }

    public MenuCategory getMenuCategoryBySlot(int slot)
    {
        return childCategories.stream().filter( child ->  child.getSlot() == slot).findFirst().orElse(null);
    }


    public static MenuParent getMenuParentFromBlock(Player p, Block block)
    {
        return parentList.stream().filter( parent -> parent.getRequirements().meetsRequirements(p) && parent.getCraftingBlocks().contains(block.getType()) ).findFirst().orElse(null);
    }


}
