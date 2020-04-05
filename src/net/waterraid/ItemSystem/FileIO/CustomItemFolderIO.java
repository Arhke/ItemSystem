package net.waterraid.ItemSystem.FileIO;

import net.waterraid.ItemSystem.CraftItem.DurabilityItem;
import net.waterraid.ItemSystem.FurnitureMapping.*;
import net.waterraid.ItemSystem.Main;
import net.waterraid.ItemSystem.utils.Utility;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;

public class CustomItemFolderIO {
    public static void load() {
        File files = new File(Main.getFolder() + File.separator + "CustomItem");
        if (!files.isDirectory() && !files.exists()) {
            Main.log("[CustomItemFolderIO.java] Failure Loading CustomItem Folder," +
                    " Please Delete the DataFolder and Retry");
            return;
        }
        YamlConfiguration config = new YamlConfiguration();
        for (File file : files.listFiles()) {
            try {
                config.load(file);
                String Id = config.getString("Id");
                if (Id == null || Id.length() == 0) {
                    Main.log("[CustomItemFolderIO] Failure Loading Item Id in Directory " + file.getPath());
                    continue;
                }
                String Name = config.getString("Name");
                if (Name == null || Name.length() == 0) {
                    Main.log("[CustomFolderIO] Invalid Name or Name Not Given for " + Id + ";Using Id as Name");
                    Name = Id;
                } else {
                    Name = Utility.colorWrap(Name);
                }
                List<String> Lore = config.getStringList("Lore");
                Material mat = Material.getMaterial(config.getString("Material"));
                if (mat == null || mat == Material.AIR) {
                    Main.log("[CustomItemFolderIO.java] Error Parsing Material for " + Id);
                    continue;
                }
                String Type = config.getString("Type");
                if (Type == null || Type.length() == 0) {
                    Main.log("[CustomItemFolderIO.java] Error Parsing Type for " + Id);
                    continue;
                }
                if (Type.equalsIgnoreCase("Smeltery")) {
                    if (mat == Material.IRON_INGOT || mat == Material.GOLD_INGOT || mat == Material.DIAMOND) {
                        CustomItem.getAll().add(new SmelteryItem(Id, Name, mat, Lore, Utility.buildDurabilityItemList(config)));
                    } else {
                        Main.log("[Utility.java] Type Mismatch: " + Type + " does not go with " + mat);
                    }
                    continue;
                }
                else if (Type.equalsIgnoreCase("Tannery")) {
                    if (mat == Material.LEATHER) {
                        CustomItem.getAll().add(new TanneryItem(Id, Name, mat, Lore, Utility.buildTanneryItemList(config)));
                    } else {
                        Main.log("[Utility.java] Type Mismatch: " + Type + " does not go with " + mat);
                    }
                    continue;
                }
                else if (Type.equalsIgnoreCase("Brewery")) {
                    if (mat == Material.POTION || mat == Material.SPLASH_POTION || mat == Material.LINGERING_POTION) {
                        CustomItem.getAll().add(new BreweryItem(Id, Name, mat, Lore, Utility.buildPotionEffectList(config)));
                    } else {
                        Main.log("[Utility.java] Type Mismatch: " + Type + " does not go with " + mat);
                    }
                    continue;
                }
                else if (Type.equalsIgnoreCase("Extractor")) {
                    CustomItem.getAll().add(new ExtractorItem(Id, Name, mat, Lore));
                    continue;
                }
                else if (Type.equalsIgnoreCase("Mythic")) {
                    //FIXME
                    continue;
                }
                else {
                    Main.log("[Utility.java] Invalid Type " + Type);
                    continue;
                }

            } catch (Exception e) {
                Main.log("[CustomItemFolderIO] Failure Loading Custom Items");
                e.printStackTrace();
                continue;
            }
        }


    }

    public static void save() {

    }

    public static void createDefaultFiles() {

    }
}
