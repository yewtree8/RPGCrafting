package uk.co.fordevelopment.rpg.flatfile;

/**
 * Created by matty on 09/08/2017.
 */
public class FileManager {

    private Config config;
    private CraftingFile craftingFile;
    private CraftableSessionStarage craftableSessionStarageFile;

    public FileManager()
    {
        config = new Config();
        craftingFile = new CraftingFile();

    }

    public Config getConfig()
    {
        return config;
    }

    public CraftingFile getCraftingFile()
    {
        return craftingFile;
    }

    public CraftableSessionStarage getCraftableSessionStarageFile(){return craftableSessionStarageFile;}

    public void loadCraftableSessionStorageFile(){
        craftableSessionStarageFile = new CraftableSessionStarage();
    }
}
