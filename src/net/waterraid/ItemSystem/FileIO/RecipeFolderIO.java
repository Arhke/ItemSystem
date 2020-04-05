package net.waterraid.ItemSystem.FileIO;

import net.waterraid.ItemSystem.Enumeration.FurnitureType;
import net.waterraid.ItemSystem.FurnitureMapping.Recipes;
import net.waterraid.ItemSystem.FurnitureMapping.WrappedCustomItem;
import net.waterraid.ItemSystem.Main;
import net.waterraid.ItemSystem.utils.Utility;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Arrays;
import java.util.Set;

public class RecipeFolderIO {
    public static void load() {
        File files = new File(Main.getFolder() + File.separator + "Recipe");
        if (!files.isDirectory() && !files.exists()) {
            Main.log("[CustomItemFolderIO.java] Failure Loading Recipe Folder," +
                    " Please Delete the DataFolder and Retry");
            return;
        }
        YamlConfiguration config = new YamlConfiguration();
        for (File file : files.listFiles()) {
            try {
                config.load(file);
                String Id = config.getString("Id");
                if (Id == null || Id.length() == 0) {
                    Main.log("[RecipeFolderIO] Failure Loading Recipe Id in Directory " + file.getPath());
                    continue;
                }
                String furniture = config.getString("Furniture");
                if (furniture == null || furniture.length() == 0 || furniture.equalsIgnoreCase("NONE") || furniture.equalsIgnoreCase("ZTANNERY") || furniture.equalsIgnoreCase("XTANNERY")) {
                    Main.log("[RecipeFolderIO] Failure Loading Recipe Furniture for " + Id);
                    continue;
                }
                FurnitureType Furnituretype = FurnitureType.valueOf(furniture);
                int Time = config.getInt("Time");
                int TimeRange = config.getInt("TimeRange");
                int Chance = config.getInt("Chance");
                WrappedCustomItem[] wciOutput = Utility.buildOutputList(config);
                Set<WrappedCustomItem> wciInput = Utility.buildInputList(config);
                int[] Chances = Utility.buildOutputChanceList(config);
                System.out.println("" + wciOutput +" "+ wciInput +" "+ Chances);
                if (wciOutput == null || wciOutput.length == 0 || wciInput == null || wciInput.size() == 0 || Chances == null || Chances.length == 0) {
                    Main.log("[RecipeFolderIO] Failure loading Recipe Input/Output in Directory" + file.getPath());
                    continue;
                }
                Recipes.getAll().add(new Recipes(Id, Furnituretype, Time, TimeRange, Chance, wciOutput, Chances, wciInput));
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                Main.log("[RecipeFolderIO] Failure loading Recipe Furniture in Directory" + file.getPath());
            } catch (Exception e) {
                e.printStackTrace();
                Main.log("[RecipeFolderIO] Failure loading Recipe in Directory" + file.getPath());
                continue;
            }
        }
    }

}
