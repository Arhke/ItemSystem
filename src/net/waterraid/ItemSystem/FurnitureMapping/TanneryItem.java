package net.waterraid.ItemSystem.FurnitureMapping;

import net.waterraid.ItemSystem.CraftItem.DurabilityItem;
import org.bukkit.Material;

import java.util.List;

public class TanneryItem extends SmelteryItem {
    public TanneryItem (String Id, String Name, Material Material, List<String> Lore, DurabilityItem[] CraftResult) {
        super(Id, Name, Material, Lore, CraftResult);
    }

    @Override
    public DurabilityItem getShield() {
        return null;
    }
    @Override
    public DurabilityItem getSword() {
        return null;
    }
    @Override
    public DurabilityItem getAxe() {
        return null;
    }
    @Override
    public DurabilityItem getPickAxe() {
        return null;
    }
    @Override
    public DurabilityItem getShovel() {
        return null;
    }
    @Override
    public DurabilityItem getHoe() {
        return null;
    }
}
