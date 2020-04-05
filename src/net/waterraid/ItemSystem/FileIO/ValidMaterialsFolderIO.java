package net.waterraid.ItemSystem.FileIO;

import net.waterraid.ItemSystem.FurnitureMapping.*;
import net.waterraid.ItemSystem.Main;
import net.waterraid.ItemSystem.utils.Utility;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Set;

public class ValidMaterialsFolderIO {
    public static void load() {
        YamlConfiguration config = new YamlConfiguration();
        File file = new File(Main.getFolder() + File.separator + "ValidMaterials" + File.separator + "Brewery.yml");
        try {
            config.load(file);
            Set<Material> Valid = Utility.buildValidMaterialsList(config);
            Set<Material> Explode = Utility.buildExplodeMaterialsList(config);
            if (Valid == null) {
                Main.log("Error Loading Config Files from ValidMaterials Folder");
            }
            Brewery.setValidMaterial(Valid);
            Brewery.setExplodeMaterial(Explode);

            file = new File(Main.getFolder() + File.separator + "ValidMaterials" + File.separator + "Extractor.yml");
            config.load(file);
            Valid = Utility.buildValidMaterialsList(config);
            Explode = Utility.buildExplodeMaterialsList(config);
            if (Valid == null) {
                Main.log("Error Loading Config Files from ValidMaterials Folder");
            }
            Extractor.setValidMaterial(Valid);
            Extractor.setExplodeMaterial(Explode);

            file = new File(Main.getFolder() + File.separator + "ValidMaterials" + File.separator + "Mythic.yml");
            config.load(file);
            Valid = Utility.buildValidMaterialsList(config);
            Explode = Utility.buildExplodeMaterialsList(config);
            if (Valid == null) {
                Main.log("Error Loading Config Files from ValidMaterials Folder");
            }
            Mythic.setValidMaterial(Valid);
            Mythic.setExplodeMaterial(Explode);

            file = new File(Main.getFolder() + File.separator + "ValidMaterials" + File.separator + "Smeltery.yml");
            config.load(file);
            Valid = Utility.buildValidMaterialsList(config);
            Explode = Utility.buildExplodeMaterialsList(config);
            if (Valid == null) {
                Main.log("Error Loading Config Files from ValidMaterials Folder");
            }
            Smeltery.setValidMaterial(Valid);
            Smeltery.setExplodeMaterial(Explode);

            file = new File(Main.getFolder() + File.separator + "ValidMaterials" + File.separator + "Tannery.yml");
            config.load(file);
            Valid = Utility.buildValidMaterialsList(config);
            Explode = Utility.buildExplodeMaterialsList(config);
            if (Valid == null) {
                Main.log("Error Loading Config Files from ValidMaterials Folder");
            }
            Tannery.setValidMaterial(Valid);
            Tannery.setExplodeMaterial(Explode);
        } catch (Exception e) {
            Main.log("Error Loading Config Files from ValidMaterials Folder");
        }

    }


}
