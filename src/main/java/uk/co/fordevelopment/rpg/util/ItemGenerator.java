package uk.co.fordevelopment.rpg.util;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

/**
 * Created by matty on 09/08/2017.
 */
public class ItemGenerator {

    public static ItemStack setMeta(ItemStack material, String name, List<String> lore) {
        if (((material == null) || material.getType() == Material.AIR) || (name == null && lore == null))

            return null;

        ItemMeta im = material.getItemMeta();
        if (name != null)
            im.setDisplayName(name);
        if (lore != null) {
            im.setLore(lore);

            material.setItemMeta(im);
            return material;

        }
        return material;
    }

    public static ItemStack setMeta(ItemStack material, String name, List<String> lore, double durability) {
        if (((material == null) || material.getType() == Material.AIR) || (name == null && lore == null))

            return null;

        ItemMeta im = material.getItemMeta();
        if (name != null)
            im.setDisplayName(name);

        if(durability > 0){
            im.spigot().setUnbreakable(true);
            im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        }

        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        if (lore != null) {
            im.setLore(lore);

            material.setItemMeta(im);
            return material;
        }

        return material;
    }

    public static ItemStack whiteOrRed(int slot)
    {
        return slot == 0 ? redPane() : whitePane();
    }

    public static ItemStack redPane()
    {
        ItemStack redPane = new ItemStack(Material.STAINED_GLASS_PANE,1 , (byte)14);
        ItemMeta meta = redPane.getItemMeta();
        meta.setDisplayName(" ");
        redPane.setItemMeta(meta);
        return redPane;
    }

    public static ItemStack orange()
    {
        ItemStack redPane = new ItemStack(Material.STAINED_GLASS_PANE,1 , (byte)3);
        ItemMeta meta = redPane.getItemMeta();
        meta.setDisplayName(" ");
        redPane.setItemMeta(meta);
        return redPane;
    }


    public static ItemStack whitePane()
    {
        ItemStack redPane = new ItemStack(Material.STAINED_GLASS_PANE);
        ItemMeta meta = redPane.getItemMeta();
        meta.setDisplayName(" ");
        redPane.setItemMeta(meta);
        return redPane;
    }

    public static ItemStack grayPane()
    {
        ItemStack i = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte)7);
        return setMeta(i, ChatColor.DARK_GRAY + " ", Arrays.asList(" "));
    }

    public static ItemStack craftOneMore()
    {
        return setMeta(new ItemStack(Material.WORKBENCH), ChatColor.AQUA + "" + ChatColor.BOLD + "Craft 1 Extra", Arrays.asList(ChatColor.GRAY + "Add an additional craft",
                ChatColor.GRAY + "to the result"));
    }

    public static ItemStack craftFiveExtra()
    {
        return setMeta(new ItemStack(Material.WORKBENCH), ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Craft 5 Extra", Arrays.asList(ChatColor.GRAY + "Add an additional 5 crafts",
                ChatColor.GRAY + "to the result"));
    }

    public static ItemStack cancelCraft()
    {
        return setMeta(new ItemStack(Material.BARRIER), ChatColor.RED + "" + ChatColor.BOLD + "Cancel crafting", Arrays.asList(ChatColor.GRAY + "Completely cancel this craft"));
    }

    public static ItemStack greenPane()
    {
        ItemStack i = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte)5);
        return setMeta(i, ChatColor.DARK_GRAY + " ", Arrays.asList(" "));
    }

}
