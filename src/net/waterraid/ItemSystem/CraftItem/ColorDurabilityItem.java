package net.waterraid.ItemSystem.CraftItem;


import de.tr7zw.itemnbtapi.NBTItem;
import de.tr7zw.itemnbtapi.NBTList;
import de.tr7zw.itemnbtapi.NBTListCompound;
import de.tr7zw.itemnbtapi.NBTType;
import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;

public class ColorDurabilityItem extends DurabilityItem {
    Color _color;

    public ColorDurabilityItem(double Damage, double AttackSpeed, int MaxHealth, double DurabilityMultiplier, int Armor, int ArmorToughness, double KnockbackResist,
                               double MovementSpeed, int MagicResist, int Luck, int Dodge, int Deflect, int RangedDamage, int CriticalChance, double CriticalDmg, Color Color) {
        super(Damage, AttackSpeed, MaxHealth, DurabilityMultiplier, Armor, ArmorToughness, KnockbackResist,
                MovementSpeed, MagicResist, Luck, Dodge, Deflect, RangedDamage, CriticalChance, CriticalDmg);
        _color = Color;
    }

    /**
     * @return null if no Color otherwise returns the color of the item
     */
    public Color getColor() {
        return _color;
    }

    public ItemStack applyTags(ItemStack is) {
        if (!is.getType().toString().contains("LEATHER_")) {
            return null;
        }
        String slot;
        if (is.getType().toString().contains("SWORD") || is.getType().toString().contains("AXE")
                || is.getType().toString().contains("PICKAXE") || is.getType().toString().contains("SPADE")
                || is.getType().toString().contains("HOE")) {
            slot = "mainhand";
        } else if (is.getType().toString().contains("HELMET")) {
            slot = "Head";
        } else if (is.getType().toString().contains("CHESTPLATE")) {
            slot = "Chest";
        } else if (is.getType().toString().contains("LEGGINGS")) {
            slot = "Leg";
        } else if (is.getType().toString().contains("BOOTS")) {
            slot = "Feet";
        } else {
            return null;
        }
        NBTItem nbti = new NBTItem(is);
        NBTList attribute = nbti.getList("AttributeModifiers", NBTType.NBTTagCompound);
        NBTListCompound attr = attribute.addCompound();
        attr.setDouble("Amount", _damage);
        attr.setString("AttributeName", "generic.attackDamage");
        attr.setString("Name", "generic.attackDamage");
        attr.setInteger("Operation", 0);
        attr.setInteger("UUIDLeast", 894654);
        attr.setInteger("UUIDMost", 2872);
        attr.setString("Slot", slot);

        attr = attribute.addCompound();
        attr.setDouble("Amount", _maxHealth);
        attr.setString("AttributeName", "generic.maxHealth");
        attr.setString("Name", "generic.maxHealth");
        attr.setInteger("Operation", 0);
        attr.setInteger("UUIDLeast", 894654);
        attr.setInteger("UUIDMost", 2872);
        attr.setString("Slot", slot);

        nbti.setDouble("DurabilityMultiplier", _durabilityMultiplier);

        attr = attribute.addCompound();
        attr.setDouble("Amount", _armor);
        attr.setString("AttributeName", "generic.armor");
        attr.setString("Name", "generic.armor");
        attr.setInteger("Operation", 0);
        attr.setInteger("UUIDLeast", 894654);
        attr.setInteger("UUIDMost", 2872);
        attr.setString("Slot", slot);

        attr = attribute.addCompound();
        attr.setDouble("Amount", _armorToughness);
        attr.setString("AttributeName", "generic.armorToughness");
        attr.setString("Name", "generic.armorToughness");
        attr.setInteger("Operation", 0);
        attr.setInteger("UUIDLeast", 894654);
        attr.setInteger("UUIDMost", 2872);
        attr.setString("Slot", slot);


        attr = attribute.addCompound();
        attr.setDouble("Amount", _knockbackResist);
        attr.setString("AttributeName", "generic.knockbackResistance");
        attr.setString("Name", "generic.knockbackResistance");
        attr.setInteger("Operation", 0);
        attr.setInteger("UUIDLeast", 894654);
        attr.setInteger("UUIDMost", 2872);
        attr.setString("Slot", slot);

        attr = attribute.addCompound();
        attr.setDouble("Amount", _movementSpeed);
        attr.setString("AttributeName", "generic.movementSpeed");
        attr.setString("Name", "generic.movementSpeed");
        attr.setInteger("Operation", 0);
        attr.setInteger("UUIDLeast", 894654);
        attr.setInteger("UUIDMost", 2872);
        attr.setString("Slot", slot);

        nbti.setInteger("MagicResist", _magicResist);

        attr = attribute.addCompound();
        attr.setDouble("Amount", _luck);
        attr.setString("AttributeName", "generic.luck");
        attr.setString("Name", "generic.luck");
        attr.setInteger("Operation", 0);
        attr.setInteger("UUIDLeast", 894654);
        attr.setInteger("UUIDMost", 2872);
        attr.setString("Slot", slot);

        nbti.setInteger("Dodge", _dodge);
        nbti.setInteger("Deflect", _deflect);
        nbti.setInteger("RangedDamage", _rangedDamage);
        nbti.setInteger("CriticalChance", _criticalChance);
        nbti.setDouble("CriticalDamage", _criticalDmg);
        nbti.setInteger("HideFlags", 2);

        System.out.println(is.getItemMeta().getDisplayName());


        return nbti.getItem();
    }
}
