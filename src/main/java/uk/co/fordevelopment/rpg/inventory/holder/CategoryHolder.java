package uk.co.fordevelopment.rpg.inventory.holder;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import uk.co.fordevelopment.rpg.craft.menu.MenuCategory;

import javax.smartcardio.CardTerminal;
import java.util.UUID;

/**
 * Created by matty on 09/08/2017.
 */
public class CategoryHolder implements InventoryHolder {



    private MenuCategory category;

    public CategoryHolder(MenuCategory category)
    {

        this.category = category;
    }

    public MenuCategory getCategory()
    {
        return this.category;
    }


    @Override
    public Inventory getInventory()
    {
        return null;
    }
}
