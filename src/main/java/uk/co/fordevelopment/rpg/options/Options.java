package uk.co.fordevelopment.rpg.options;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import uk.co.fordevelopment.rpg.CraftPlugin;
import uk.co.fordevelopment.rpg.craft.crafting.CraftingSession;

/**
 * Created by matty on 10/08/2017.
 */
public class Options {

    private ItemStack claimItem;
    private ItemStack cancelItem;

    private String craftingSessionOccupiedBase;
    private String finishedCraftingMessageBase;

    public Options()
    {
        loadOptions();
    }

    private void loadOptions()
    {
        claimItem = CraftPlugin.getFileManager().getConfig().getClaimItem();
        cancelItem = CraftPlugin.getFileManager().getConfig().getCancelItem();

        craftingSessionOccupiedBase = CraftPlugin.getFileManager().getConfig().getCraftingSessionOccupiedBase();
        finishedCraftingMessageBase = CraftPlugin.getFileManager().getConfig().getFinishedCraftingMessageBase();

    }

    public String getCraftingSessionOccupiedBase(String ownerName)
    {
        String base = craftingSessionOccupiedBase;
        base = base.replaceAll("%player%", ownerName);
        return translate(base);
    }

    public String getCraftingSessionFinishedMessage(CraftingSession session)
    {
        String base = finishedCraftingMessageBase;
        Location blockLocation = session.getActiveBlock().getLocation();
        String blockX = blockLocation.getBlockX() + "";
        String blockY = blockLocation.getBlockY() + "";
        String blockZ = blockLocation.getBlockZ() + "";
        String itemName = session.getCraftableItem().getFinalResult().getItemMeta().getDisplayName();
        base = base.replaceAll("%x%", blockX);
        base = base.replaceAll("%y%", blockY);
        base = base.replaceAll("%z%", blockZ);
        base = base.replaceAll("%item%", itemName);
        return translate(base);
    }

    public ItemStack getClaimItem()
    {
        return this.claimItem;
    }

    public ItemStack getCancelItem()
    {
        return this.cancelItem;
    }

    private String translate(String input)
    {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

}
