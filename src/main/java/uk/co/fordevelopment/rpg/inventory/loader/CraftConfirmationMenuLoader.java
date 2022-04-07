package uk.co.fordevelopment.rpg.inventory.loader;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import uk.co.fordevelopment.rpg.CraftPlugin;
import uk.co.fordevelopment.rpg.inventory.holder.CraftingSessionDataHolder;
import uk.co.fordevelopment.rpg.util.ItemGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by matty on 11/08/2017.
 */
public class CraftConfirmationMenuLoader {

    private UUID id;
    private Inventory inventory;
    private int invSize;
    private CraftingSessionDataHolder holder;

    private ItemStack confirmCraftIcon;

    public CraftConfirmationMenuLoader(Player p, CraftingSessionDataHolder holder)
    {
        this.holder = holder;
        invSize = 9;
        this.id = p.getUniqueId();
        this.inventory = p.getInventory();
        loadConfirmCraftIcon();
        openInventory();
    }

    private void loadConfirmCraftIcon()
    {
        confirmCraftIcon = holder.getItem().getFinalResult().clone();
        ItemMeta im = confirmCraftIcon.getItemMeta();
        im.setDisplayName(ChatColor.GOLD + "Craft");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "You have selected to craft " + confirmCraftIcon.getItemMeta().getDisplayName());
        lore.add(ChatColor.GRAY + "click this to craft it");
        im.setLore(lore);
        confirmCraftIcon.setItemMeta(im);
    }

    private void openInventory()
    {

        inventory = Bukkit.createInventory(holder, invSize, ChatColor.BLUE + "" + ChatColor.BOLD + StringUtils.capitalize(holder.getItem().getMenuCategory().getMenuName()));
        Player p = Bukkit.getPlayer(id);
        p.openInventory(inventory);
        inventory.setItem(0, CraftPlugin.getOptions().getCancelItem());
        inventory.setItem(2, confirmCraftIcon);
        fillInv();
        p.updateInventory();
    }


    private void fillInv()
    {
        for(int i = 0; i < inventory.getSize() ; i++)
        {
            ItemStack item = inventory.getItem(i);
            if(item==null || (item.getType() == Material.AIR))
            {
                inventory.setItem(i, ItemGenerator.grayPane());
            }
        }
    }





}
