package uk.co.fordevelopment.rpg.flatfile;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import uk.co.fordevelopment.rpg.CraftPlugin;
import uk.co.fordevelopment.rpg.util.ItemGenerator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by matty on 09/08/2017.
 */
public class Config {

    private File configFile;
    private FileConfiguration config;

    private String finishedCraftingMessagePath;
    private String craftingStationOccupied;

    private String claimItemMaterialPath;
    private String claimItemNamePath;
    private String claimItemLorePath;
    private String claimItemDataValuePath;
    private String claimItemDurabilityValue;

    private String cancelItemMaterialPath;
    private String cancelItemNamePath;
    private String cancelItemLorePath;
    private String cancelItemDataValuePath;
    private String cancelItemDurabilityValue;

    public Config()
    {
        loadPaths();
        this.configFile = new File(CraftPlugin.getPlugin().getDataFolder(), "config.yml");
        this.config = YamlConfiguration.loadConfiguration(configFile);
        generateConfig();
    }

    public File getConfigFile()
    {
        return this.configFile;
    }

    public FileConfiguration getConfig()
    {
        return this.config;
    }

    public void save()
    {
        try {
            getConfig().save(getConfigFile());
        } catch (IOException e) {
            CraftPlugin.log("Error, cannot save config file for some reason.");
            e.printStackTrace();
        }
    }

    private void loadPaths()
    {
        finishedCraftingMessagePath = "finished-crafting-message";
        craftingStationOccupied = "crafting-session-occupied-message";

        claimItemMaterialPath = "claim-item.material";
        claimItemNamePath = "claim-item.name";
        claimItemLorePath = "claim-item.lore";
        claimItemDataValuePath = "claim-item.data-value";
        claimItemDurabilityValue = "claim-item.durability";

        cancelItemMaterialPath = "cancel-item.material";
        cancelItemNamePath = "cancel-item.name";
        cancelItemLorePath = "cancel-item.lore";
        cancelItemDataValuePath = "cancel-item.data-value";
        cancelItemDurabilityValue = "cancel-item.durability";
    }

    private void generateConfig()
    {
        if (!getConfigFile().exists()) {

            set(craftingStationOccupied, "&cThis workstation is currently being used by %player%" );
            set(finishedCraftingMessagePath, "&aYour %item% &ahas finished crafting at &cX: %x% ; Y: %y% ; Z: %z%" );

            set(claimItemMaterialPath, Material.PRISMARINE_SHARD.name());
            set(claimItemNamePath, "&6Claim item");
            List<String> claimItemLore = new ArrayList<>();
            claimItemLore.add("&7Click to claim your item");
            set(claimItemLorePath, claimItemLore);
            set(claimItemDataValuePath, 0);
            set(claimItemDurabilityValue, 0);

            List<String> cancelItemLore = new ArrayList<>();
            cancelItemLore.add("&7Go back");
            set(cancelItemMaterialPath, Material.BARRIER.name());
            set(cancelItemNamePath, "&c&lCancel");
            set(cancelItemLorePath, cancelItemLore);
            set(cancelItemDataValuePath, 0);
            set(cancelItemDurabilityValue, 0);
        }
    }

    public String getString(String path)
    {
        return getConfig().getString(path);
    }

    public int getInt(String path)
    {
        return getConfig().getInt(path);
    }

    public List<String> getStringList(String path)
    {
        return getConfig().getStringList(path);
    }

    public void set(String path, Object value)
    {
        getConfig().set(path, value);
        save();
    }

    public String getCraftingSessionOccupiedBase()
    {
        return getString(craftingStationOccupied);
    }

    public String getFinishedCraftingMessageBase()
    {
        return getString(finishedCraftingMessagePath);
    }

    public ItemStack getClaimItem()
    {
        Material material = Material.getMaterial( getString(claimItemMaterialPath).toUpperCase() );
        String name = translate(getString( claimItemNamePath ));
        List<String> lore = convertLore( getStringList(claimItemLorePath) );
        short durability = (short) getConfig().getDouble(claimItemDurabilityValue);
        short dataValue = (short) getInt( claimItemDataValuePath );
        ItemStack claimItem = ItemGenerator.setMeta(new ItemStack(material, 1, dataValue), name, lore, durability);
        return claimItem;
    }

    public ItemStack getCancelItem()
    {
        Material material = Material.getMaterial( getString(cancelItemMaterialPath).toUpperCase() );
        String name = translate(getString( cancelItemNamePath ));
        List<String> lore = convertLore( getStringList(cancelItemLorePath) );
        short durability = (short) getConfig().getDouble(cancelItemDurabilityValue);
        short dataValue = (short) getInt( cancelItemDataValuePath );
        ItemStack cancelItem = ItemGenerator.setMeta(new ItemStack(material, 1, dataValue), name, lore, durability);
        return cancelItem;
    }

    private List<String> convertLore(List<String> baseLore)
    {
        List<String> toReturn = new ArrayList<>();
        baseLore.forEach( uncoloured -> toReturn.add(translate(uncoloured)));
        return toReturn;
    }

    private String translate(String input)
    {
        return ChatColor.translateAlternateColorCodes('&', input);
    }


}
