package uk.co.fordevelopment.rpg.counter;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import uk.co.fordevelopment.rpg.CraftPlugin;
import uk.co.fordevelopment.rpg.players.CraftPlayer;

import java.util.UUID;

/**
 * Created by matty on 10/08/2017.
 */
public class ItemCounter {

    private UUID id;
    private ItemStack itemStack;
    private Inventory inventory;
    private int quantity;
    private boolean remove;

    public ItemCounter(Player p, ItemStack itemStack)
    {
        this.id = p.getUniqueId();
        this.itemStack = itemStack;
        this.inventory = p.getInventory();
        quantity = 0;
        countItems();
    }

    private void countItems()
    {
        for(ItemStack item : inventory.getContents())
        {
            if(item!=null && (item.getType()!= Material.AIR))
            {
                Material targetMaterial = itemStack.getType();
                Material currentMaterial = item.getType();
                if(targetMaterial == currentMaterial)
                {
                    if (itemStackNameMatches(item, itemStack))
                    {
                        short targetDurability = itemStack.getDurability();
                        short currentDurability = item.getDurability();
                        if(targetDurability == currentDurability)
                        {
                            quantity = quantity + item.getAmount();
                        }
                    }

                }

            }
        }

    }

    public int getAmount()
    {

        return this.quantity;
    }


    private boolean itemStackNameMatches(ItemStack item, ItemStack otherItem)
    {
        String name = item.hasItemMeta() && item.getItemMeta().hasDisplayName() ? item.getItemMeta().getDisplayName() : "";
        String nameTwo = otherItem.hasItemMeta() && otherItem.getItemMeta().hasDisplayName() ? otherItem.getItemMeta().getDisplayName() : "";
        return name.equalsIgnoreCase(nameTwo);
    }


}
