package uk.co.fordevelopment.rpg.inventory.holder;

import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import uk.co.fordevelopment.rpg.craft.item.CraftableItem;

/**
 * Created by matty on 11/08/2017.
 */
public class CraftingSessionDataHolder implements InventoryHolder {

    private Block block;
    private CraftableItem item;

    public CraftingSessionDataHolder(Block block, CraftableItem item)
    {
        this.block = block;
        this.item =  item;
    }

    public Block getBlock()
    {
        return this.block;
    }

    public CraftableItem getItem()
    {
        return this.item;
    }

    @Override
    public Inventory getInventory()
    {
        return null;
    }
}
