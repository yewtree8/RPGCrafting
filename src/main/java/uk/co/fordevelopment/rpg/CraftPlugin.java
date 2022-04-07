package uk.co.fordevelopment.rpg;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import uk.co.fordevelopment.rpg.command.GivePlsCommand;
import uk.co.fordevelopment.rpg.craft.ObjectLoader;
import uk.co.fordevelopment.rpg.craft.pool.CraftingSessionManager;
import uk.co.fordevelopment.rpg.flatfile.FileManager;
import uk.co.fordevelopment.rpg.listener.PlayerBlockBreakListener;
import uk.co.fordevelopment.rpg.listener.PlayerInteractListener;
import uk.co.fordevelopment.rpg.listener.PlayerJoinListener;
import uk.co.fordevelopment.rpg.listener.PlayerQuitListener;
import uk.co.fordevelopment.rpg.listener.menu.ActiveCraftingMenuClickListener;
import uk.co.fordevelopment.rpg.listener.menu.ConfirmationMenuClickListener;
import uk.co.fordevelopment.rpg.listener.menu.MainMenuClickListener;
import uk.co.fordevelopment.rpg.listener.menu.MenuCategoryClickListener;
import uk.co.fordevelopment.rpg.options.Options;

import javax.swing.text.html.Option;

/**
 * Created by matty on 09/08/2017.
 */
public class CraftPlugin extends JavaPlugin {


    /**
     *                                           HELLO THERE DEVELOPER
     *
     *                                   Welcome to the codebase for RPGCrafting
     *                       This is a rather large plugin that went through a fairly large top to
     *                   bottom conversion 40% through development, so excuse the messiness in some parts.
     *
     *                   The CraftSession object is probably the most important, holds all the data about a
     *                                  current crafting session a player is in.
     *
     *              The object loader is horrible, don't kill me, it was the main topic of that large conversion.
     *
     * The /givepls command can only be done by OPs, it basically gives you ALL the ingredients and tools needed for EVERYTHING (used for debugging)
     *
     *        With some basic java skill you should be able to add more configuration options, just look at the "Config.Java" to set up
     *                  the paths, and then use the "Options" object to load them into fields so it's quicker.
     *
     *         This plugin should be very stable, I've tried my best to think of any possible exploits, it will also cause ZERO LAG.
     *
     *                  There's comments here and there as a small reminder for me, but nothing too in depth.
     *
     *                                          Good luck navigating
     *                                                  - Mat
     *
     */

    private static CraftPlugin instance;
    public static CraftPlugin getPlugin(){return instance;}

    private static Listener[] listenerList =
            {
                    new PlayerInteractListener(), new PlayerQuitListener(), new MainMenuClickListener(), new PlayerJoinListener(),
                        new MenuCategoryClickListener(), new ConfirmationMenuClickListener(), new ActiveCraftingMenuClickListener(),
                            new PlayerBlockBreakListener(),

            };

    private static FileManager fileManager;
    private static Options options;

    private CraftingSessionManager craftingSessionManager;

    public static void log(String msg){System.out.println("RPGCrafting -> " + msg);}

    public void onEnable()
    {
        instance = this;
        this.getCommand("givepls").setExecutor(new GivePlsCommand());
        setup();

    }
    public void onDisable()
    {
        getCraftingSessionManager().serializeSessions();
    }

    private void setup()
    {
        craftingSessionManager = new CraftingSessionManager();
        loadConfigurations();
        new ObjectLoader();
        getFileManager().loadCraftableSessionStorageFile();
        reglisteners();
        //TODO register commands
        init();
    }

    private void init()
    {
        options = new Options();
    }

    private void loadConfigurations()
    {
        fileManager = new FileManager();
    }

    private void reglisteners()
    {
        for(Listener listener : listenerList)
        {
            Bukkit.getServer().getPluginManager().registerEvents(listener, this);
        }
    }

    public static FileManager getFileManager()
    {
        return fileManager;
    }

    public CraftingSessionManager getCraftingSessionManager()
    {
        return this.craftingSessionManager;
    }

    public static Options getOptions() { return options; }

}
