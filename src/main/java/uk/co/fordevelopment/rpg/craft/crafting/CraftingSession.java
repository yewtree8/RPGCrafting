package uk.co.fordevelopment.rpg.craft.crafting;

import net.minecraft.server.v1_11_R1.EnchantmentInfiniteArrows;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_11_R1.inventory.CraftItemStack;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import uk.co.fordevelopment.rpg.CraftPlugin;
import uk.co.fordevelopment.rpg.craft.item.CraftableItem;
import uk.co.fordevelopment.rpg.craft.menu.MenuCategory;
import uk.co.fordevelopment.rpg.craft.menu.MenuParent;
import uk.co.fordevelopment.rpg.flatfile.CraftableSessionStarage;
import uk.co.fordevelopment.rpg.inventory.holder.ActiveCraftingSessionHolder;
import uk.co.fordevelopment.rpg.util.ItemGenerator;
import uk.co.fordevelopment.rpg.util.Serializer;
import uk.co.fordevelopment.rpg.util.TimeFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by matty on 10/08/2017.
 */
public class CraftingSession {

 int currentToCraft;
    private UUID owner;

    private Block workbenchBlock;

    private CraftableItem craftableItem;

    private String ownerName;

    private BukkitTask counterTask;

    private int currentCount;

    private CraftingSession instance;

    private List<ItemStack> outputItems;

    private Inventory inventory;

    private boolean finished;

    private int[] progressBarList = {37, 38, 39, 40, 41, 42, 43};

    private int progressIndex;

    private ArmorStand holoStand;

    public CraftingSession(Player p, Block block, CraftableItem item)
    {
        progressIndex = 0;
        instance = this;
        finished = false;
        this.outputItems = new ArrayList<>();
        this.currentToCraft = 1;
        this.currentCount = item.getCraftingTime();
        this.owner = p.getUniqueId();
        this.ownerName = p.getName();
        this.workbenchBlock = block;
        this.craftableItem = item;
        CraftPlugin.getPlugin().getCraftingSessionManager().addSession(this);
        item.getCraftingRequirements().removeIngredientsFromInventory(p, 1);
        setupInventory();
        loadHologram();
        initiateCraftingLoop();

    }

    public CraftingSession(Location location, String playerName, int currentCrafted, int currentCount, ItemStack guiIcon, UUID owner, int currentToCraft, boolean finished)
    {
        instance = this;
        this.finished = finished;
        this.owner = owner;
        this.craftableItem = CraftableItem.getItem(guiIcon);
        this.outputItems = new ArrayList<>();
        //TODO add output items
        this.workbenchBlock = location.getBlock();
        this.currentToCraft = currentToCraft;
        this.currentCount = currentCount;
        for(int i = 0 ; i < currentCrafted ; i++)
        {
            outputItems.add(craftableItem.getFinalResult());
        }
        this.ownerName = playerName;
        CraftPlugin.getPlugin().getCraftingSessionManager().addSession(this);
        setupInventory();
        loadHologram();
        initiateCraftingLoop();


    }


    public boolean isFinished()
    {
        return finished;
    }


    private void loadHologram()
    {
        Location loc = workbenchBlock.getLocation().clone();
        loc.add(0.5, -0.70, 0.5);
        holoStand = workbenchBlock.getWorld().spawn(loc, ArmorStand.class);
        holoStand.setVisible(false);
        holoStand.setGravity(false);
        holoStand.setRemoveWhenFarAway(false);
        holoStand.setBasePlate(false);
        holoStand.setArms(false);
        holoStand.setCanPickupItems(false);
        updateHolo();
        holoStand.setCustomNameVisible(true);
    }

    private void updateHolo()
    {
        new BukkitRunnable() {
            public void run()
            {
                holoStand.setCustomName(getHoloString());
            }
        }.runTask(CraftPlugin.getPlugin());
    }

    private String getHoloString()
    {
        int completeTotalSecondsLeft = currentCount + ( (currentToCraft - 1) * craftableItem.getCraftingTime() );
        ChatColor secondPrefixColour = completeTotalSecondsLeft <= 10 ? ChatColor.RED : completeTotalSecondsLeft <= 30 ? ChatColor.YELLOW : ChatColor.GREEN;
        String finalPrefix =  secondPrefixColour + "" + ChatColor.BOLD  + TimeFormatter.convertsSecondsToMinutesColonFormat(completeTotalSecondsLeft);
        return currentToCraft > 0 ?  finalPrefix : ChatColor.GREEN + "" + ChatColor.GREEN + "" + ChatColor.BOLD + "Completed!";
    }

    private int[] blueList = {10, 11, 12, 13, 14, 15, 16, 19, 220, 21, 22, 23, 24, 25, 28, 29, 30 ,31 ,32 ,33 ,34};

    private void setupInventory()
    {
        Player p =  Bukkit.getPlayer(owner);


        inventory = Bukkit.createInventory(new ActiveCraftingSessionHolder(this), 54, ChatColor.BLUE + "" +
                ChatColor.BOLD + StringUtils.capitalize(craftableItem.getMenuCategory().getMenuName()));
        setupBorder();

        /*
        for(int i = 0 ; i <  blueList.length ; i++)
        {
            inventory.setItem(blueList[i], ItemGenerator.orange());
        }
        */
        inventory.setItem(22, statusItemStack());

        inventory.setItem(30, ItemGenerator.craftOneMore());
        inventory.setItem(31, ItemGenerator.cancelCraft());
        inventory.setItem(32, ItemGenerator.craftFiveExtra());

        if(p!=null)
        {
            p.openInventory(inventory);
        }
    }

    public void openCraftingSessionInventory(Player p)
    {
        p.openInventory(inventory);
        updateInventory();
        p.updateInventory();
    }



    private ItemStack statusItemStack()
    {
        ItemStack base = craftableItem.getFinalResult().clone();
        ItemMeta im = base.getItemMeta();

        int totalSecondsLeft = currentCount;

        int completeTotalSecondsLeft = totalSecondsLeft + ( (currentToCraft - 1) * craftableItem.getCraftingTime() );

        ChatColor prefixColour = totalSecondsLeft <= 10 ? ChatColor.RED : totalSecondsLeft <= 30 ? ChatColor.YELLOW : ChatColor.GOLD;

        ChatColor secondPrefixColour = completeTotalSecondsLeft <= 10 ? ChatColor.RED : completeTotalSecondsLeft <= 30 ? ChatColor.YELLOW : ChatColor.GOLD;

        List<String> newLore = new ArrayList<>();
        if(currentToCraft == 0)
        {
            newLore.add(ChatColor.GREEN + "Completed!");
        } else {
            newLore.add(ChatColor.GRAY + "» Time left for this item:");
            newLore.add(prefixColour + TimeFormatter.convertsSecondsToMinutesColonFormat(totalSecondsLeft));
            newLore.add("");
            newLore.add(ChatColor.GRAY + "» Total time until completion:");
            newLore.add(secondPrefixColour + "  " +  TimeFormatter.convertsSecondsToMinutesColonFormat(completeTotalSecondsLeft));
            newLore.add(ChatColor.GRAY + "» Total Crafted:");
            newLore.add(ChatColor.YELLOW + "  " + getOutputItems().size());
            newLore.add(ChatColor.GRAY + "» Items left:");
            newLore.add(ChatColor.YELLOW + "  " + currentToCraft);
        }


        im.setLore(newLore);

        base.setItemMeta(im);

        return base;
    }

    private void updateInventory()
    {
        new BukkitRunnable() {
            public void run()
            {
                if (!finished) {
                    Player p = Bukkit.getPlayer(owner);
                    if (p != null) {
                        if (p.getOpenInventory() != null && (p.getOpenInventory().getTopInventory().getHolder() instanceof ActiveCraftingSessionHolder)) {
                            ItemStack toSet = finished ? CraftPlugin.getOptions().getClaimItem() : statusItemStack();
                            inventory.setItem(22, toSet);
                            handleProgressBar();
                            p.updateInventory();
                        }
                    }
                }
            }
        }.runTask(CraftPlugin.getPlugin());

    }


    private int iterations = 0;
    private void handleProgressBar()
    {
        if(iterations== ( progressBarList.length-1))
        {
            for(int i = 0 ; i <= progressBarList.length ;i++)
            {
                for(int j = 0 ; j < progressBarList.length ; j++)
                {
                    inventory.setItem(progressBarList[j], new ItemStack(Material.AIR));
                }
                inventory.setItem(progressBarList[1], ItemGenerator.greenPane());
            }
            iterations = 0;
        } else {
            for (int i = 0; i <= iterations; i++) {
                inventory.setItem(progressBarList[i], ItemGenerator.greenPane());
            }
        }
        iterations++;
    }

    public void claimAndRemoveInstance()
    {

        //TODO cancel crafting session
        new BukkitRunnable() {
            public void run()
            {
                holoStand.remove();
                executeCommands();
                Player p = Bukkit.getPlayer(getOwner());
                getOutputItems().forEach(item ->
                {
                    if (p.getInventory().firstEmpty() == -1) {
                        p.getWorld().dropItemNaturally(p.getLocation(), item);
                    } else {
                        p.getInventory().addItem(item);
                    }

                });
                p.updateInventory();
                CraftPlugin.getPlugin().getCraftingSessionManager().removeSession(instance);
                if (counterTask != null) {
                    counterTask.cancel();
                }


            }


        }.runTask(CraftPlugin.getPlugin());

    }





    private void executeCommands()
    {
        craftableItem.getCommands().forEach( cmd ->
        {
            cmd = cmd.replaceAll("%player%", getOwnerName());
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), cmd);

        });
    }

    private void initiateCraftingLoop()
    {

        counterTask = new BukkitRunnable()
        {
            public void run()
            { //do all calculations async, no lag that way.

                if(finished) return;

                currentCount--;

                workbenchBlock.getWorld().playEffect(workbenchBlock.getLocation(), Effect.FLAME, 20);


                updateHolo();

                if(currentCount == 0)//ending of current craft
                {

                    currentToCraft--;

                    outputItems.add(craftableItem.getFinalResult());

                    workbenchBlock.getWorld().playEffect(workbenchBlock.getLocation(), Effect.FLAME, 40);

                    if(currentToCraft == 0) //Stop crafting
                    {
                        new BukkitRunnable()
                        {
                            public void run()
                            {

                                inventory.setItem(22, CraftPlugin.getOptions().getClaimItem());
                                updateInventory();
                                Player p = Bukkit.getPlayer(getOwner());

                                if(p!=null)
                                {
                                    p.sendMessage(CraftPlugin.getOptions().getCraftingSessionFinishedMessage(CraftPlugin.getPlugin().getCraftingSessionManager().getCraftingSession(workbenchBlock)));
                                }

                                finished = true;

                            }
                        }.runTask(CraftPlugin.getPlugin());

                    }
                    else
                    {
                        currentCount = craftableItem.getCraftingTime();
                    }
                }

                updateInventory();

            }
        }.runTaskTimerAsynchronously(CraftPlugin.getPlugin(), 20L, 20L);

    }

    public List<ItemStack> getOutputItems()
    {
        return this.outputItems;
    }

    public void addToCraft(int quantity, Player player)
    {
        if(!finished)
        {
            if(craftableItem.getCraftingRequirements().hasIngredients(player, quantity))
            {
                craftableItem.getCraftingRequirements().removeIngredientsFromInventory(player, quantity);
                currentToCraft = currentToCraft + quantity;
            } else
            {
                player.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "Invalid materials to continue crafting.");
            }
        } else {
            player.sendMessage(ChatColor.RED + "Claim the items you've already crafted first.");
        }

    }


    ItemStack getWhiteOrRed(int slot)
    { return slot % 2 == 0  ? ItemGenerator.whitePane() : ItemGenerator.redPane();}

    public void setupBorder()
    {
        Player p = Bukkit.getPlayer(getOwner());
        for(int i = 0 ; i < 9 ; i++)
        {
            inventory.setItem(i, getWhiteOrRed(i));

        }
        for(int i = inventory.getSize()-9 ; i < inventory.getSize() ; i++)
        {
            inventory.setItem(i, getWhiteOrRed(i));
        }
        for(int i = 1 ; i < ((inventory.getSize()/9)) ; i++)
        {
            int slot = i*9;
            inventory.setItem(slot, getWhiteOrRed(i));
        }
        for(int i = 2 ; i < ((inventory.getSize()/9)+1) ; i++)
        {
            int slot = ( ( i * 9 ) - 1 );
            inventory.setItem(slot, getWhiteOrRed(i));
        }
        if(p!=null) {
            p.updateInventory();
        }
    }

    public int getCurrentCount()
    {
        return this.currentCount;
    }


    public CraftableItem getCraftableItem()
    {
        return craftableItem;
    }

    public Block getActiveBlock()
    {
        return this.workbenchBlock;
    }

    public UUID getOwner()
    {
        return this.owner;
    }

    public String getOwnerName()
    {
        return this.ownerName;
    }


    public String getOccupiedMessage()
    {
        return CraftPlugin.getOptions().getCraftingSessionOccupiedBase(getOwnerName());
    }


    public void completeyCancelAndRefund()
    {
        if(counterTask!=null) {
            counterTask.cancel();
        }
        holoStand.setVisible(false);
        holoStand.setCustomNameVisible(false);
        holoStand.setCustomName(null);
        holoStand.setCustomNameVisible(false);
        holoStand.setSmall(true);
        holoStand.remove();
        CraftPlugin.getPlugin().getCraftingSessionManager().removeSession(this);
        Player p = Bukkit.getPlayer(getOwner());
        int totalToRefund = getOutputItems().size() + currentToCraft;
        for(int i = 0 ; i < totalToRefund ;i++)
        {
            craftableItem.getCraftingRequirements().getIngredientsRequired().forEach( ingredient ->
            {
                if(p.getInventory().firstEmpty() == -1){p.getWorld().dropItemNaturally(p.getLocation(), ingredient);}
                else { p.getInventory().addItem(ingredient); }
            });
        }
        p.updateInventory();
        inventory = null;
        craftableItem = null;

    }

    /**
     Need to save:name
     final
     Location
      CraftableItem
     Players  item

     */

    public void serialzeSession()
    {
        CraftPlugin.log("Serializing");
        if(counterTask != null) {
            counterTask.cancel();
        }
        holoStand.setVisible(false);
        holoStand.setCustomNameVisible(false);
        holoStand.setCustomName(null);
        holoStand.setCustomNameVisible(false);
        holoStand.setSmall(true);
        holoStand.remove();
        CraftableSessionStarage conf = CraftPlugin.getFileManager().getCraftableSessionStarageFile();
        String base = Serializer.serializeLocation(workbenchBlock.getLocation()) + ".";
        conf.set(base + "player-name", getOwnerName());
        conf.set(base + "uuid", getOwner().toString());
        conf.set(base + "current-crafted", getOutputItems().size());
        conf.set(base + "current-to-craft", currentToCraft);
        conf.set(base + "gui-icon", craftableItem.getGuiIcon());
        conf.set(base + "current-count", currentCount);
        conf.set(base + "finished", finished);
        CraftPlugin.log("Serialized");
    }






}
