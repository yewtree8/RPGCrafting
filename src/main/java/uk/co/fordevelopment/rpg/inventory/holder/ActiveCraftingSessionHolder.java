package uk.co.fordevelopment.rpg.inventory.holder;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import uk.co.fordevelopment.rpg.craft.crafting.CraftingSession;

/**
 * Created by matty on 12/08/2017.
 */
public class ActiveCraftingSessionHolder implements InventoryHolder {

    private CraftingSession craftingSession;

    public ActiveCraftingSessionHolder(CraftingSession session)
    {
        this.craftingSession = session;
    }

    public CraftingSession getCraftingSession()
    {
        return craftingSession;
    }


    @Override
    public Inventory getInventory()
    {
        return null;
    }
}
