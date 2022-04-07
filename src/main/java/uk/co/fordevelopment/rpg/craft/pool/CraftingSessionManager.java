package uk.co.fordevelopment.rpg.craft.pool;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import uk.co.fordevelopment.rpg.CraftPlugin;
import uk.co.fordevelopment.rpg.craft.crafting.CraftingSession;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by matty on 10/08/2017.
 */
public class CraftingSessionManager {

    private List<CraftingSession> allSessions;

    public CraftingSessionManager()
    {
        allSessions = new ArrayList<>();
    }

    public boolean isCraftingSessionActive(Block block)
    {
        return allSessions.stream().anyMatch( session ->
                session.getActiveBlock().getLocation().equals(block.getLocation()));
    }

    public CraftingSession getCraftingSession(Block block)
    {
        return allSessions.stream().filter( session -> session.getActiveBlock().getLocation().equals(block.getLocation())).findFirst().orElse(null);
    }

    public void addSession(CraftingSession session)
    {
        allSessions.add(session);
    }

    public void removeSession(CraftingSession session)
    {
        allSessions.remove(session);
    }


    public List<CraftingSession> getAllSessions(){return allSessions;}

    public void handleDisable()
    {
        getAllSessions().forEach( session -> session.completeyCancelAndRefund());
    }

    public boolean hasCraftingSessionActive(Player p)
    {
        return getAllSessions().stream().anyMatch(session -> session.getOwner().equals(p.getUniqueId()));
    }

    public boolean hasCraftingSessionActiveAt(Block block, Player p)
    {
        return getAllSessions().stream().anyMatch( session -> session.getOwner().equals(p.getUniqueId()) && session.getActiveBlock().getLocation().equals(block.getLocation()));
    }

    public void serializeSessions()
    {
        Iterator iterator = getAllSessions().iterator();
        while(iterator.hasNext())
        {
            CraftingSession session = (CraftingSession) iterator.next();
            CraftPlugin.log("About to serialize");
            session.serialzeSession();
        }
    }

}
