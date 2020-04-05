package net.waterraid.ItemSystem.FileIO;

import net.waterraid.ItemSystem.Main;

import java.io.File;

public class GeneralFileIO {
    public static void load() {
        if (!Main.getFolder().exists()) {
            Main.getFolder().mkdir();
//            loadDefaults();
        }

        File file = new File(Main.getFolder() + File.separator + "Recipe");
        if (!file.exists()) {
            file.mkdir();
//            loadRecipeDefaults();
        }
        file = new File(Main.getFolder() + File.separator + "ValidMaterials");
        if (!file.exists()) {
            file.mkdir();
        }
        loadValidMaterialDefaults();
        ValidMaterialsFolderIO.load();
        loadCustomItemDefaults();
        CustomItemFolderIO.load();
        loadRecipeDefaults();
        RecipeFolderIO.load();

    }

    public static void loadCustomItemDefaults() {
        Main.getInstance().saveResource("CustomItem" + File.separator + "Steel.yml", true);
        Main.getInstance().saveResource("CustomItem" + File.separator + "Aspirin.yml", true);
        Main.getInstance().saveResource("CustomItem" + File.separator + "WraithHide.yml", true);
    }

    public static void loadRecipeDefaults() {
        Main.getInstance().saveResource("Recipe" + File.separator + "SteelRecipe.yml", true);
    }

    public static void loadValidMaterialDefaults() {
        Main.getInstance().saveResource("ValidMaterials" + File.separator + "Brewery.yml", true);
        Main.getInstance().saveResource("ValidMaterials" + File.separator + "Extractor.yml", true);
        Main.getInstance().saveResource("ValidMaterials" + File.separator + "Mythic.yml", true);
        Main.getInstance().saveResource("ValidMaterials" + File.separator + "Smeltery.yml", true);
        Main.getInstance().saveResource("ValidMaterials" + File.separator + "Tannery.yml", true);
    }

}
