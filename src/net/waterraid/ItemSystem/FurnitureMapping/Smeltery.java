package net.waterraid.ItemSystem.FurnitureMapping;

import net.waterraid.ItemSystem.Enumeration.FurnitureType;
import net.waterraid.ItemSystem.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Furnace;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashSet;
import java.util.Set;

public class Smeltery extends Furniture{
    public Smeltery(Location loc) {
        super (loc);
        _ft = FurnitureType.SMELTERY;
        _bt = new BukkitRunnable() {
            Furnace furnace = ((Furnace)(loc.getBlock().getState()));
            @Override
            public void run() {
                furnace.setBurnTime((short)1200);
                furnace.update();
            }
        }.runTaskTimer(Main.getInstance(), 0,2400);
    }
    @Override
    public void unRegisterEvents(){
        Furnace furnace = ((Furnace)(_loc.getBlock().getState()));
        furnace.setBurnTime((short)0);
        furnace.update();
        _bt.cancel();

    }
    BukkitTask _bt;
    static Set<Material> ValidMaterial = new HashSet<>();
    static Set<Material> ExplodeMaterial = new HashSet<>();
}
