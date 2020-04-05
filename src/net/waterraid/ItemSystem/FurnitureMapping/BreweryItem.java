package net.waterraid.ItemSystem.FurnitureMapping;

import org.bukkit.Material;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import java.util.List;

public class BreweryItem extends CustomItem {
    public BreweryItem (String Id, String Name, Material Material, List<String> Lore, List<PotionEffect> effects) {
        super(Id, Name, Material, Lore);
        PotionMeta meta = (PotionMeta)(getItem().getItemMeta());
        if (effects != null && effects.size() != 0) {
            for (PotionEffect pe:effects) {
                meta.addCustomEffect(pe, true);
            }
        }
        super._is.setItemMeta(meta);
    }
}
