package net.waterraid.ItemSystem.FurnitureMapping;

import de.tr7zw.itemnbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomItem {
    String _id;
    String _name;
    Material _material;
    ItemStack _is;
    public static Set<CustomItem> allCustomItems = new HashSet<>();
    public CustomItem(String Id, String Name, Material Material, List<String> Lore) {
        _id = Id;
        _name = Name;
        _material = Material;
        _is = new ItemStack (_material, 1);
        ItemMeta im = _is.getItemMeta();
        im.setDisplayName(_name);
        if (Lore != null && Lore.size() != 0) {
            im.setLore(Lore);
        }
        _is.setItemMeta(im);
        NBTItem nbti = new NBTItem(_is);
        nbti.setString("Material", _id);
        _is = nbti.getItem();
    }

    public String getId() {
        return _id;
    }

    public String getName() {
        return _name;
    }

    public Material getMaterial() {
        return _material;
    }

    public ItemStack getItem() {
        return _is;
    }

    @Override
    public int hashCode() {
        return _id.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof CustomItem && ((CustomItem)o).getId().equals(this.getId())) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "id: " + _id + ", name: " + _name + ", material: " + _material;
    }

    public static Set<CustomItem> getAll() {
        return allCustomItems;
    }

    public static CustomItem findItem(String Id) {
        for (CustomItem ci: allCustomItems) {
            if (ci.getId().equals(Id)){
                return ci;
            }
        }
        return null;
    }

    public static void unloadCustomItems(){
        allCustomItems = new HashSet<>();
    }
}
