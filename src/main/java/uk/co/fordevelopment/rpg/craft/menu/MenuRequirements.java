package uk.co.fordevelopment.rpg.craft.menu;

import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.List;

/**
 * Created by matty on 09/08/2017.
 */
public class MenuRequirements {

    private static HashSet<MenuRequirements> allRequirements = new HashSet<>();
    public static HashSet<MenuRequirements> getAllRequirements(){return allRequirements;}

    private List<Biome> allowedBiomes;
    private String permission;

    public MenuRequirements(List<Biome> allowedBiomes, String permission)
    {
        this.allowedBiomes = allowedBiomes;
        this.permission = permission;
        allRequirements.add(this);
    }

    public List<Biome> getAllowedBiomes()
    {
        return allowedBiomes;
    }

    public String getPermission()
    {
        return permission;
    }


    private boolean isInCorrectBiome(Player p)
    {
        return getAllowedBiomes().stream().anyMatch( biome -> p.getLocation().getBlock().getBiome().equals(biome));
    }



    private boolean hasPermission(Player p)
    {
        return p.hasPermission(getPermission());
    }

    public boolean meetsRequirements(Player p)
    {
        return isInCorrectBiome(p) && (hasPermission(p));
    }

}
