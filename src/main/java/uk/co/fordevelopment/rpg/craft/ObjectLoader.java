package uk.co.fordevelopment.rpg.craft;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import sun.awt.SunGraphicsCallback;
import uk.co.fordevelopment.rpg.CraftPlugin;
import uk.co.fordevelopment.rpg.craft.item.CraftableItem;
import uk.co.fordevelopment.rpg.craft.item.CraftingRequirements;
import uk.co.fordevelopment.rpg.craft.menu.MenuCategory;
import uk.co.fordevelopment.rpg.craft.menu.MenuParent;
import uk.co.fordevelopment.rpg.craft.menu.MenuRequirements;
import uk.co.fordevelopment.rpg.util.ItemGenerator;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by matty on 09/08/2017.
 */
public class ObjectLoader {


    public ObjectLoader()
    {
        loadEverything(); //Yeah horrible naming convention, whatever.
    }



    private void loadEverything() //Oh god this is about to get interesting, it's going to look hard to follow but bear with me.
    {
        FileConfiguration file = CraftPlugin.getFileManager().getCraftingFile().getConfig();
        file.getKeys(false).forEach( menuName ->
        {


            List<MenuCategory> childCategories = new ArrayList<>();

            List<Biome> biomes = convertBiomeList(file.getStringList(menuName + ".requirements.biomes"));
            List<Material> materialRequirements = convertMaterialList( file.getStringList(menuName + ".requirements.materials" ));
            String requiredPermission = file.getString(menuName + ".requirements.permission");

            MenuRequirements menuRequirements = new MenuRequirements(biomes, requiredPermission);

            ConfigurationSection itemSection = file.getConfigurationSection( menuName + ".items" );

            itemSection.getKeys(false).forEach(slotNum ->
            {

                Material menuGuiMaterial = Material.getMaterial( file.getString(menuName + ".items." + slotNum + ".gui-icon.item") );
                short menuGuiDataValue = (short) file.getInt(menuName + ".items." + slotNum + ".gui-icon.data-value");
                short menuGuiDurability = (short) file.getDouble(menuName + ".items." + slotNum + ".gui-icon.durability");
                String menuGuiName = translate(file.getString(menuName + ".items." + slotNum + ".gui-icon.name"));
                List<String> menuGuiLore = convertLore( file.getStringList( menuName + ".items." + slotNum +  ".gui-icon.lore" ) );

                ItemStack menuGUIIcon = menuGuiDurability > 0 ? ItemGenerator.setMeta(new ItemStack(menuGuiMaterial, 1, menuGuiDataValue), menuGuiName, menuGuiLore, menuGuiDurability) :
                        ItemGenerator.setMeta(new ItemStack(menuGuiMaterial, 1, menuGuiDataValue), menuGuiName, menuGuiLore);

                MenuCategory menuCategory = new MenuCategory(menuName, menuGUIIcon, Integer.parseInt(slotNum));

                childCategories.add(menuCategory);

                ConfigurationSection guiSection = file.getConfigurationSection(menuName + ".items." + slotNum +  ".gui");

                guiSection.getKeys(false).forEach( slot ->
                {
                    String path = menuName + ".items." + slotNum  + ".gui." + slot;

                    String itemPermission = file.getString(path + ".permission");
                    String itemName = translate( file.getString( path + ".icon-name") );
                    List<String> itemLore = convertLore( file.getStringList( path + ".icon-lore") );
                    int itemCraftingTimeSeconds = file.getInt(path + ".crafting-time");
                    Material itemMaterial = Material.getMaterial( file.getString(path + ".icon-material").toUpperCase() );
                    short itemDataValue = (short) file.getInt(path + ".icon-data-value");
                    short itemDurability = (short) file.getDouble( path + ".icon-durability" );

                    ItemStack guiIcon = ItemGenerator.setMeta(new ItemStack(itemMaterial, 1, itemDataValue), itemName, itemLore, itemDurability);

                    List<ItemStack> requiredTools = new ArrayList<>();
                    List<ItemStack> requiredIngredients = new ArrayList<>();

                    ConfigurationSection toolSection = file.getConfigurationSection(path + ".tools-required");

                    toolSection.getKeys(false).forEach( toolNumber ->
                    {
                        String toolPath = path + ".tools-required." + toolNumber;
                        CraftPlugin.log("The tool path is " + toolPath);
                        Material material = Material.getMaterial( file.getString( toolPath + ".material").toUpperCase());
                        String name = translate( file.getString( toolPath + ".name") );
                        ItemStack newTool = new ItemStack(material);
                        ItemMeta toolMeta = newTool.getItemMeta();
                        toolMeta.setDisplayName(name);
                        newTool.setItemMeta(toolMeta);
                        requiredTools.add(newTool);
                    });

                    ConfigurationSection ingredientSection = file.getConfigurationSection(path + ".ingredients-required");

                    ingredientSection.getKeys(false).forEach( ingredientNumber ->
                    {
                        String ingredientPath = path + ".ingredients-required." + ingredientNumber;
                        Material ingredientMaterial = Material.getMaterial(  file.getString(ingredientPath + ".material").toUpperCase()  );
                        String ingredientName = translate( file.getString( ingredientPath + ".name" ) );
                        short ingredientDurability = (short) file.getDouble( ingredientPath + ".durability" );
                        short dataValue = (short) file.getInt(ingredientPath + ".data-value");
                        int ingredientQuantity = file.getInt( ingredientPath + ".quantity" );
                        List<String> ingredientLore = convertLore( file.getStringList(ingredientPath + ".lore") );
                        ItemStack ingredient = ItemGenerator.setMeta(new ItemStack(ingredientMaterial, ingredientQuantity, dataValue), ingredientName, ingredientLore,ingredientDurability);
                        requiredIngredients.add(ingredient);
                    });

                    CraftingRequirements craftingRequirements = new CraftingRequirements(itemPermission, requiredTools, requiredIngredients);

                    Material finalMaterial = Material.getMaterial( file.getString(path + ".crafting-result.material").toUpperCase() );
                    short finalDataValue = (short) file.getInt(path + ".crafting-result.data-value");
                    short durability = (short) file.getDouble( path + ".crafting-result.durability" );
                    int quantity = file.getInt(path + ".crafting-result.quantity");
                    String finalName = translate(file.getString( path + ".crafting-result.name" ));
                    List<String> finalLore = convertLore( file.getStringList( path + ".crafting-result.lore" ) );

                    List<String> commands = file.getStringList(path + ".crafting-result.commands");

                    ItemStack finalItem = ItemGenerator.setMeta(new ItemStack(finalMaterial, quantity, finalDataValue), finalName, finalLore, durability);

                    new CraftableItem(Integer.parseInt(slot), itemCraftingTimeSeconds, guiIcon, craftingRequirements, commands, finalItem, menuCategory);


                });

            });

            new MenuParent(menuName, materialRequirements, childCategories, menuRequirements);


        });
    }

    private List<Material> convertMaterialList(List<String> baseList)
    {
        List<Material> toReturn = new ArrayList<>();
        baseList.forEach( materialName -> toReturn.add( Material.getMaterial(materialName.toUpperCase()) ));
        return toReturn;
    }

    private List<Biome> convertBiomeList(List<String> baseList)
    {
        List<Biome> toReturn = new ArrayList<>();
        for(String biomeString : baseList)
        {
            if(biomeString.equalsIgnoreCase("all"))
            {
                toReturn = Arrays.asList(Biome.values());
                break;
            } else {
                toReturn.add(Biome.valueOf(biomeString.toUpperCase()));
            }
        }
        return toReturn;
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
