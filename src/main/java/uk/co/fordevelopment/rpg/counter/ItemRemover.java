package uk.co.fordevelopment.rpg.counter;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

/**
 * Created by matty on 12/08/2017.
 */
public class ItemRemover {


    private UUID id;
    private ItemStack itemStack;
    private Inventory inventory;
    private int totalQuantityToRemove;

    public ItemRemover(Player p, ItemStack itemStack, int quantity)
    {
        this.totalQuantityToRemove = itemStack.getAmount() * quantity;
        this.id = p.getUniqueId();
        this.itemStack = itemStack;
        this.inventory = p.getInventory();
        countItems();
    }

    private void countItems()
    {
        Player p = Bukkit.getPlayer(id);

        for(int i = 0 ; i < inventory.getContents().length ; i++)
        {
            ItemStack item = inventory.getContents()[i];
            if(item != null && (item.getType() != Material.AIR))
            {
                Material targetMaterial = itemStack.getType();
                Material currentMaterial = item.getType();
                if(targetMaterial == currentMaterial)
                {
                    if(itemStackNameMatches(item, itemStack))
                    {
                        short targetDurability = itemStack.getDurability();
                        short currentDurability = item.getDurability();
                        if(targetDurability == currentDurability)
                        {
                            int foundQuantity = item.getAmount();
                            if(foundQuantity > totalQuantityToRemove) //time to finish
                            {
                                int difference = foundQuantity - totalQuantityToRemove;
                                item.setAmount(difference);
                                break;
                            }
                            else if(foundQuantity <= totalQuantityToRemove)
                            {
                                totalQuantityToRemove = totalQuantityToRemove - foundQuantity;
                                inventory.setItem(i, new ItemStack(Material.AIR));
                                if(totalQuantityToRemove <= 0) { break; }
                            }
                        }

                    }
                }
            }
        }
        p.updateInventory();


    }

    private boolean itemStackNameMatches(ItemStack item, ItemStack otherItem)
    {
        String name = item.hasItemMeta() && item.getItemMeta().hasDisplayName() ? item.getItemMeta().getDisplayName() : "";
        String nameTwo = otherItem.hasItemMeta() && otherItem.getItemMeta().hasDisplayName() ? otherItem.getItemMeta().getDisplayName() : "";
        return name.equalsIgnoreCase(nameTwo);
    }

}
