package uk.co.fordevelopment.rpg.craft.item;

import com.google.common.io.CountingInputStream;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import uk.co.fordevelopment.rpg.counter.ItemCounter;
import uk.co.fordevelopment.rpg.counter.ItemRemover;

import java.util.HashSet;
import java.util.List;

/**
 * Created by matty on 09/08/2017.
 */
public class CraftingRequirements {

    private static HashSet<CraftingRequirements> allRequirements = new HashSet<>();
    public static HashSet<CraftingRequirements> getAllRequirements(){return allRequirements;}

    private String permission;
    private List<ItemStack> toolsNeeded;
    private List<ItemStack> ingredientsRequired;

    public CraftingRequirements(String permission, List<ItemStack> toolsNeeded, List<ItemStack> ingredientsRequired)
    {
        this.permission = permission;
        this.toolsNeeded = toolsNeeded;
        this.ingredientsRequired = ingredientsRequired;


        allRequirements.add(this);
    }

    public String getPermission()
    {
        return permission;
    }

    public List<ItemStack> getIngredientsRequired()
    {
        return ingredientsRequired;
    }

    public List<ItemStack> getToolsNeeded()
    {
        return toolsNeeded;
    }

    private boolean hasPermission(Player p)
    {
        return p.hasPermission(getPermission());
    }


    private boolean hasIngredients(Player p)
    {
        for(ItemStack item : getIngredientsRequired())
        {
            ItemCounter counter = new ItemCounter(p, item);
            if(counter.getAmount() < item.getAmount())
            {
                return false;
            }
        }
        return true;
    }


    private boolean hasIngredientsQuantity(Player p, int quantity)
    {
        for(ItemStack item : getIngredientsRequired())
        {
            ItemCounter counter = new ItemCounter(p, item);
            if(counter.getAmount() < ( item.getAmount() * quantity ))
            {
                return false;
            }

        }
        return true;
    }

    public boolean hasIngredients(Player p, int quantity)
    {
        return hasIngredientsQuantity(p, quantity) && hasToolsNeeded(p) && hasPermission(p);
    }


    public void removeIngredientsFromInventory(Player p, int quantity)
    {
        getIngredientsRequired().forEach( item ->
        {
            new ItemRemover(p, item, quantity);
        });
    }



    private boolean hasToolsNeeded(Player p)
    {
        for(ItemStack item : getToolsNeeded())
        {
            ItemCounter counter = new ItemCounter(p, item);
            if(counter.getAmount() < item.getAmount())
            {
                return false;
            }
        }
        return true;
    }

    public boolean meetsRequirements(Player p){
        return hasToolsNeeded(p) && hasIngredients(p) && hasPermission(p);
    }

}
