package uk.co.fordevelopment.rpg.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import uk.co.fordevelopment.rpg.craft.item.CraftingRequirements;

/**
 * Created by matty on 11/08/2017.
 */
public class GivePlsCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if(sender instanceof Player)
        {
            Player p = (Player) sender;
            if(!p.isOp()){return false;}
            CraftingRequirements.getAllRequirements().forEach( req ->
            {
                req.getIngredientsRequired().forEach( ing -> p.getInventory().addItem(ing));
                req.getToolsNeeded().forEach(tool -> p.getInventory().addItem(tool));
            });
            p.sendMessage("giving pls");
        }
        return false;
    }
}
