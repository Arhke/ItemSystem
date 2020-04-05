package net.waterraid.ItemSystem.FurnitureMapping;

import net.waterraid.ItemSystem.CraftItem.DurabilityItem;
import org.bukkit.Material;

import java.util.List;

public class SmelteryItem extends CustomItem {
    private DurabilityItem[] _craftResult;
    public SmelteryItem (String Id, String Name, Material Material, List<String> Lore, DurabilityItem[] CraftResult) {
        super(Id, Name, Material, Lore);
        _craftResult = CraftResult;
    }
    public DurabilityItem getHelmet() {
        return _craftResult[0];
    }
    public DurabilityItem getChestPlate() {
        return _craftResult[1];
    }
    public DurabilityItem getLeggings() {
        return _craftResult[2];
    }
    public DurabilityItem getBoots() {
        return _craftResult[3];
    }
    public DurabilityItem getShield() {
        return _craftResult[4];
    }
    public DurabilityItem getSword() {
        return _craftResult[5];
    }
    public DurabilityItem getAxe() {
        return _craftResult[6];
    }
    public DurabilityItem getPickAxe() {
        return _craftResult[7];
    }
    public DurabilityItem getShovel() {
        return _craftResult[8];
    }
    public DurabilityItem getHoe() {
        return _craftResult[9];
    }
}
