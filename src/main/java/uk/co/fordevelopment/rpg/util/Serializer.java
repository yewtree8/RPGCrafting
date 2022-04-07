package uk.co.fordevelopment.rpg.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

/**
 * Created by matty on 18/08/2017.
 */
public class Serializer {


    private static String convert(double input)
    {
        String newInput = input + "";
        newInput = newInput.replace('.', '#');
        return newInput;
    }

    private static double reconvert(String input)
    {
        String converted = input.replace('#', '.');
        return Double.parseDouble(converted);
    }


    public static String serializeLocation(Location loc)
    {
        String x = convert(loc.getX());
        String y = convert(loc.getBlockY());
        String z = convert(loc.getBlockZ());
        String world = loc.getWorld().getName();
        int pitch = 0;
        int yaw = 0;

        String serializedLocation = x+","+y+","+z+","+world+","+pitch+","+yaw;

        return serializedLocation;
    }

    public static Location rebuildLocation(String serializedLocation)
    {
        String[] tokens = serializedLocation.split(",");
        double x = reconvert(tokens[0]);
        double y = reconvert(tokens[1]);
        double z = reconvert(tokens[2]);
        World world = Bukkit.getWorld(tokens[3]);

        float pitch = Float.parseFloat(tokens[4]);

        float yaw = Float.parseFloat(tokens[5]);

        Location rebuiltLocation = new Location(world, x, y, z);

        rebuiltLocation.setPitch(pitch);
        rebuiltLocation.setYaw(yaw);

        return rebuiltLocation;
    }

}
