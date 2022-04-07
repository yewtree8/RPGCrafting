package uk.co.fordevelopment.rpg.inventory.holder;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import uk.co.fordevelopment.rpg.craft.menu.MenuParent;

/**
 * Created by matty on 11/08/2017.
 */
public class ParentHolder implements InventoryHolder {

    private MenuParent menuParent;

    public ParentHolder(MenuParent parent)
    {
        this.menuParent = parent;
    }

    public MenuParent getMenuParent()
    {
        return this.menuParent;
    }

    @Override
    public Inventory getInventory()
    {
        return null;
    }
}
