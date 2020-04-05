package net.waterraid.ItemSystem.FurnitureMapping;

import de.tr7zw.itemnbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class WrappedCustomItem {
    private CustomItem _ci;
    private Material _mat;
    private String _name;
    private int _amount;
    private ItemStack _is;

    public WrappedCustomItem(CustomItem ci, int Amount) {
        _ci = ci;
        _amount = Amount;
        _is = ci.getItem();
        _is.setAmount(_amount);
    }

    public WrappedCustomItem(Material Material, String Name, int Amount) {
        _name = Name;
        _mat = Material;
        _amount = Amount;
        _is = new ItemStack(Material, Amount);
        if (Name != null && Name.length() != 0) {
            ItemMeta im = _is.getItemMeta();
            im.setDisplayName(Name);
            _is.setItemMeta(im);
        }
    }

    public WrappedCustomItem(ItemStack is) {
        _mat = is.getType();
        if (is.getItemMeta().hasDisplayName()) {
            _name = is.getItemMeta().getDisplayName();
        }
        _amount = is.getAmount();
        NBTItem nbti = new NBTItem(is);
        if (nbti.hasKey("Material")) {
            CustomItem ci = CustomItem.findItem(nbti.getString("Material"));
            _ci = ci;
        }
        _is = is;
    }

    public boolean isCustomItem() {
        return _ci != null;
    }

    public CustomItem getCustomItem() {
        return _ci;
    }

    public Material getMaterial() {
        if (_mat == null) {
            return _ci.getMaterial();
        }
        return _mat;
    }

    public void setAmount(int amount) {
        _amount = amount;
        _is.setAmount(amount);
    }

    public int getAmount() {
        return _amount;
    }

    @Nullable
    public String getName() {
        return _name;
    }

    public ItemStack getItem() {
        return _is;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof WrappedCustomItem) {
            WrappedCustomItem wci = (WrappedCustomItem) o;
            if (wci.getMaterial() == this.getMaterial() && wci.getAmount() == this.getAmount()) {
                if (wci.isCustomItem() && this.isCustomItem() && wci.getCustomItem().equals(this.getCustomItem())) {
                    return true;
                } else if (wci.getName() != null && this.getName() != null && wci.getName().equals(this.getName())) {
                    return true;
                }else if(wci.getName() == null && this.getName() == null) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean equalsIgnoreAmount(Object o) {
        if (o instanceof WrappedCustomItem) {
            WrappedCustomItem wci = (WrappedCustomItem) o;
            if (wci.getMaterial() == this.getMaterial()) {
                if (wci.isCustomItem() && this.isCustomItem() && wci.getCustomItem().equals(this.getCustomItem())) {
                    return true;
                } else if (wci.getName() != null && this.getName() != null && wci.getName().equals(this.getName())) {
                    return true;
                }else if(wci.getName() == null && this.getName() == null) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        if (isCustomItem()) {
            return _ci.toString() + ", Amount: " + _amount;
        } else {
            return "name: " + _name + ", Material: " + _mat + ", Amount: " + _amount;
        }
    }
}
