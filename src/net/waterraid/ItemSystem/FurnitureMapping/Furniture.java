package net.waterraid.ItemSystem.FurnitureMapping;

import net.waterraid.ItemSystem.Enumeration.FurnitureType;
import net.waterraid.ItemSystem.utils.Utility;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Furniture {
    static List<Furniture> furnitureList = new ArrayList<>();

    public static Furniture findFurniture(Location loc) {
        long rank = Furniture.rank((int)loc.getX(),(int)loc.getY(),(int)loc.getZ());
        if (furnitureList.size() == 0) {
            return null;
        }
        int low = 0;
        int high = furnitureList.size() - 1;

        while (low <= high) {
            int middle = (low + high) / 2;
            if (rank > furnitureList.get(middle).getRank()) {
                low = middle + 1;
            } else if (rank < furnitureList.get(middle).getRank()) {
                high = middle - 1;
            } else {
                return furnitureList.get(middle);
            }
        }
        return null;
    }

    public static long rank(int x, int y, int z) {
        long xx = (long)x, yy=(long)y, zz= (long)z;
        return (xx * 1000000 + yy) * 1000000 + zz;
    }

    public static void setValidMaterial(Set<Material> materials) {
        if (materials == null) {
            ValidMaterial = new HashSet<Material>();
            return;
        }
        ValidMaterial = materials;
    }

    public static void setExplodeMaterial(Set<Material> materials) {
        if (materials == null) {
            ExplodeMaterial = new HashSet<Material>();
            return;
        }
        ExplodeMaterial = materials;
    }

    public static boolean isValidMaterial(Material mat) {
        return ValidMaterial.contains(mat);
    }

    public static boolean isExplodingMaterial(Material mat) {
        return ExplodeMaterial.contains(mat);
    }

    public static boolean removeValidMaterial(Material mat) {
        return ValidMaterial.remove(mat);
    }

    public static void addValidMaterial(Material mat) {
        ValidMaterial.add(mat);
    }

    public static void addFurniture(Furniture furniture) {
        Location loc = furniture.getLocation();
        long rank = Furniture.rank((int)loc.getX(),(int)loc.getY(),(int)loc.getZ());
        if (furnitureList.size() == 0) {
            furnitureList.add(furniture);
        }
        int low = 0;
        int high = furnitureList.size() - 1;

        while (low <= high) {
            int middle = (low + high) / 2;
            if (rank > furnitureList.get(middle).getRank()) {
                low = middle + 1;
            } else if (rank < furnitureList.get(middle).getRank()) {
                high = middle - 1;
            } else {
                furnitureList.add(middle, furniture);
                break;
            }
        }

    }

    public static void removeFurniture(Furniture furniture) {
        furniture.unRegisterEvents();
        furnitureList.removeIf(otherFurniture -> otherFurniture.equals(furniture));
    }


    Furniture(Location loc) {
        _loc = loc;
        _startTime = System.currentTimeMillis();
    }

    /**
     * ranks the items, assumes that the ranks
     */
    public long getRank() {
        long x = (long) _loc.getX(), y = (long) _loc.getY(), z = (long) _loc.getZ();
        return (x * 1000000 + y) * 1000000 + z;
    }

    public Set<WrappedCustomItem> getItems(){
        return _inputs;
    }

    public Location getLocation(){
        return _loc;
    }

    public long getStartTime(){
        return _startTime;
    }

    public void addItem(ItemStack itemStack){
        ItemStack is =  new ItemStack(itemStack);
        is.setAmount(1);
        WrappedCustomItem InputItem = new WrappedCustomItem(is);
        for (WrappedCustomItem wci: _inputs){
            if(wci.equalsIgnoreAmount(InputItem)) {
                wci.setAmount(wci.getAmount() + 1);
                return;
            }
        }
        _inputs.add(InputItem);
    }

    public void unRegisterEvents() {
        return;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("FurnitureType: " + _ft);
        sb.append("\nRank: " + this.getRank());
        return sb.toString();
    }

    @Override
    public boolean equals(Object o){
        return o instanceof Furniture && ((Furniture)o).getRank() == this.getRank();
    }

    static Set<Material> ValidMaterial = new HashSet<>();
    static Set<Material> ExplodeMaterial = new HashSet<>();
    FurnitureType _ft = FurnitureType.NONE;
    Location _loc;
    long _startTime;
    Set<WrappedCustomItem> _inputs = new HashSet<>();

}
