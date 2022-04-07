package uk.co.fordevelopment.rpg.flatfile;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import uk.co.fordevelopment.rpg.CraftPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by matty on 09/08/2017.
 */
public class CraftingFile {

    private File configFile;
    private FileConfiguration config;


    public CraftingFile()
    {
        loadPaths();
        this.configFile = new File(CraftPlugin.getPlugin().getDataFolder(), "crafting.yml");
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

    }

    private void generateConfig()
    {
        if (!getConfigFile().exists()) {
            List<String> materials = new ArrayList<>();
            materials.add(Material.WORKBENCH.name());

            List<String> biomes = new ArrayList<>();
            biomes.add("ALL");
            set("bench.requirements.materials", materials);
            set("bench.requirements.biomes", biomes);
            set("bench.requirements.permission", "example.permission");

            set("bench.items.0.gui-icon.item", Material.ANVIL.name());
            set("bench.items.0.gui-icon.data-value", 0);
            set("bench.items.0.gui-icon.durability", 0);
            set("bench.items.0.gui-icon.name", "&3&lOpen this example menu");

            List<String> guiLore = new ArrayList<>();
            guiLore.add("&7Open this possible forging menu.");
            guiLore.add("&7Requires an anvil to open.");
            set("bench.items.0.gui-icon.lore", guiLore);

            set("bench.items.0.gui.0.permission", "example.item");
            set("bench.items.0.gui.0.crafting-time", 5);
            set("bench.items.0.gui.0.icon-material", Material.DIAMOND.name());
            set("bench.items.0.gui.0.icon-name", "&4Example item");
            set("bench.items.0.gui.0.icon-data-value", 0);
            set("bench.items.0.gui.0.icon-durability", 0);

            List<String> iconLore = new ArrayList<>();
            iconLore.add("&7Craft time:");
            iconLore.add("&a5 seconds");
            iconLore.add("&7Something else");

            set("bench.items.0.gui.0.icon-lore", iconLore);

            set("bench.items.0.gui.0.tools-required.tool-1.material", Material.SHEARS.name());
            set("bench.items.0.gui.0.tools-required.tool-1.name", "&3&lBlacksmith tongs");
            set("bench.items.0.gui.0.tools-required.tool-1.data-value", 0);
            set("bench.items.0.gui.0.tools-required.tool-1.durability", 0);


            set("bench.items.0.gui.0.ingredients-required.ingredient-1.material", Material.STICK.name());
            set("bench.items.0.gui.0.ingredients-required.ingredient-1.name", "&3&lAn ingredient");
            set("bench.items.0.gui.0.ingredients-required.ingredient-1.durability", 0);
            set("bench.items.0.gui.0.ingredients-required.ingredient-1.quantity", 2);
            set("bench.items.0.gui.0.ingredients-required.ingredient-1.data-value", 0);

            List<String> ingredientLore = new ArrayList<>();
            set("bench.items.0.gui.0.ingredients-required.ingredient-1.lore", ingredientLore);

            set("bench.items.0.gui.0.crafting-result.material", Material.WORKBENCH.name());
            set("bench.items.0.gui.0.crafting-result.data-value", 0);
            set("bench.items.0.gui.0.crafting-result.durability", 0);
            set("bench.items.0.gui.0.crafting-result.quantity", 1);
            set("bench.items.0.gui.0.crafting-result.name", "&fCustom result");

            List<String> resultLore = new ArrayList<>();
            resultLore.add("&7This is a result of crafting");
            set("bench.items.0.gui.0.crafting-result.lore", resultLore);

            List<String> commands = new ArrayList<>();
            commands.add("say %player% just crafted this new item!");
            set("bench.items.0.gui.0.crafting-result.commands", commands);
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

}
