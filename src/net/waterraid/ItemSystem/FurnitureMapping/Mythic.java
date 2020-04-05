package net.waterraid.ItemSystem.FurnitureMapping;

import org.bukkit.Location;
import org.bukkit.Material;

import java.util.HashSet;
import java.util.Set;

public class Mythic extends Furniture {
    public Mythic(Location loc) {
        super(loc);
    }
    static Set<Material> ValidMaterial = new HashSet<>();
    static Set<Material> ExplodeMaterial = new HashSet<>();
}
