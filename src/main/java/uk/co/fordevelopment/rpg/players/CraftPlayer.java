package uk.co.fordevelopment.rpg.players;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import uk.co.fordevelopment.rpg.CraftPlugin;

import java.util.HashSet;
import java.util.UUID;

/**
 * Created by matty on 09/08/2017.
 */
public class CraftPlayer {

    private static HashSet<CraftPlayer> players = new HashSet<>();
    public static HashSet<CraftPlayer> getPlayers(){ return players; }

    private UUID id;

    private Block clickedBlock;

    public CraftPlayer(Player p)
    {
        this.id = p.getUniqueId();

        players.add(this);
    }

    public Block getClickedBlock()
    {
        return clickedBlock;
    }

    public void setBlock(Block block)
    {
        this.clickedBlock = block;
    }

    public UUID getUUID()
    {
        return this.id;
    }


    public static CraftPlayer getPlayer(Player p)
    {
        return players.stream().filter( pl -> pl.getUUID() .equals( p.getUniqueId()) ).findFirst().orElse(null);
    }

    public void remove()
    {
        players.remove(this);
    }

}
