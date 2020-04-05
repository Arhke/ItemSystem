package net.waterraid.ItemSystem.CraftItem;

import com.sun.jmx.snmp.SnmpUnknownAccContrModelException;
import de.tr7zw.itemnbtapi.*;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class DurabilityItem {
    double _damage = 0.0;
    double _attackSpeed = 0.0;
    int _maxHealth = 0;
    double _durabilityMultiplier = 0.0;
    int _armor = 0;
    int _armorToughness = 0;
    double _knockbackResist = 0.0;
    double _movementSpeed = 0.0;
    int _magicResist = 0;
    int _luck = 0;
    int _dodge = 0;
    int _deflect = 0;
    int _rangedDamage = 0;
    int _criticalChance = 0;
    double _criticalDmg = 0;
    boolean _craftable = true;

    public DurabilityItem(){
        _craftable = false;
    }

    public DurabilityItem(double Damage, double AttackSpeed, int MaxHealth, double DurabilityMultiplier, int Armor, int ArmorToughness, double KnockbackResist,
                          double MovementSpeed, int MagicResist, int Luck, int Dodge, int Deflect, int RangedDamage, int CriticalChance, double CriticalDmg) {
        _damage = Damage;
        _attackSpeed = AttackSpeed;
        _maxHealth = MaxHealth;
        _durabilityMultiplier = DurabilityMultiplier;
        _armor = Armor;
        _armorToughness = ArmorToughness;
        _knockbackResist = KnockbackResist;
        _movementSpeed = MovementSpeed;
        _magicResist = MagicResist;
        _luck = Luck;
        _dodge = Dodge;
        _deflect = Deflect;
        _rangedDamage = RangedDamage;
        _criticalChance = CriticalChance;
        _criticalDmg = CriticalDmg;

    }
    public boolean isCraftable() {
        return _craftable;
    }

    public ItemStack applyTags(ItemStack is, String Name){
        
        String slot;
        String newName;
        List<String> Lore = is.getItemMeta().getLore();
        if (Lore == null){
            Lore = new ArrayList<>();
        }
        if (is.getType().toString().equals("SHIELD")) {
            slot = "offhand";
            Lore.add(ChatColor.GRAY+"When in off hand:");
            newName = Name + " Shield";
            if (_damage != 0){
                Lore.add(" "+(_damage > 0?(ChatColor.BLUE+"+"):ChatColor.RED)+_damage + " Attack Damage");
            }
            if (_attackSpeed != 0){
                Lore.add(" "+(_attackSpeed > 0?(ChatColor.BLUE+"+"):ChatColor.RED)+_attackSpeed+ " Attack Speed");
            }
        }else if(is.getType().toString().contains("SWORD")){
            slot = "mainhand";
            Lore.add(ChatColor.GRAY+"When in main hand:");
            newName = Name + " Sword";
            Lore.add(ChatColor.GRAY+" "+ _attackSpeed + " Attack Speed");
            Lore.add(ChatColor.GRAY+" "+_damage + " Attack Damage");
        } else if(is.getType().toString().contains("AXE")) {
            slot = "mainhand";
            Lore.add(ChatColor.GRAY+"When in main hand:");
            newName = Name + " Axe";
            Lore.add(ChatColor.GRAY+" "+ _attackSpeed + " Attack Speed");
            Lore.add(ChatColor.GRAY+" "+_damage + " Attack Damage");
        }else if(is.getType().toString().contains("PICKAXE")) {
            slot = "mainhand";
            Lore.add(ChatColor.GRAY+"When in main hand:");
            newName = Name + " Pickaxe";
            Lore.add(ChatColor.GRAY+" "+ _attackSpeed + " Attack Speed");
            Lore.add(ChatColor.GRAY+" "+_damage + " Attack Damage");
        }else if(is.getType().toString().contains("HOE")) {
            slot = "mainhand";
            Lore.add(ChatColor.GRAY+"When in main hand:");
            newName = Name + " Reaper";
            Lore.add(ChatColor.GRAY+" "+ _attackSpeed + " Attack Speed");
            Lore.add(ChatColor.GRAY+" "+_damage + " Attack Damage");
        }else if(is.getType().toString().contains("SPADE")) {
            slot = "mainhand";
            Lore.add(ChatColor.GRAY+"When in main hand:");
            newName = Name + " Shovel";
            Lore.add(ChatColor.GRAY+" "+ _attackSpeed + " Attack Speed");
            Lore.add(ChatColor.GRAY+" "+_damage + " Attack Damage");
        }else if(is.getType().toString().contains("HELMET")) {
            slot = "head";
            Lore.add(ChatColor.GRAY+"When on "+slot +":");
            newName = Name + " Helmet";
            if (_damage != 0){
                Lore.add(" "+(_damage > 0?(ChatColor.BLUE+"+"):ChatColor.RED)+_damage + " Attack Damage");
            }
            if (_attackSpeed != 0){
                Lore.add(" "+(_attackSpeed > 0?(ChatColor.BLUE+"+"):ChatColor.RED)+_attackSpeed+ " Attack Speed");
            }
        }else if(is.getType().toString().contains("CHESTPLATE")){
            slot = "chest";
            Lore.add(ChatColor.GRAY+"When on body:");
            newName = Name + " Chestplate";
            if (_damage != 0){
                Lore.add(" "+(_damage > 0?(ChatColor.BLUE+"+"):ChatColor.RED)+_damage + " Attack Damage");
            }
            if (_attackSpeed != 0){
                Lore.add(" "+(_attackSpeed > 0?(ChatColor.BLUE+"+"):ChatColor.RED)+_attackSpeed+ " Attack Speed");
            }
        }else if(is.getType().toString().contains("LEGGINGS")) {
            slot = "legs";
            Lore.add(ChatColor.GRAY+"When on "+slot +":");
            newName = Name + " Leggings";
            if (_damage != 0){
                Lore.add(" "+(_damage > 0?(ChatColor.BLUE+"+"):ChatColor.RED)+_damage + " Attack Damage");
            }
            if (_attackSpeed != 0){
                Lore.add(" "+(_attackSpeed > 0?(ChatColor.BLUE+"+"):ChatColor.RED)+_attackSpeed+ " Attack Speed");
            }
        }else if(is.getType().toString().contains("BOOTS")){
            slot = "feet";
            Lore.add(ChatColor.GRAY+"When on "+slot +":");
            newName = Name + " Boots";
            if (_damage != 0){
                Lore.add(" "+(_damage > 0?(ChatColor.BLUE+"+"):ChatColor.RED)+_damage + " Attack Damage");
            }
            if (_attackSpeed != 0){
                Lore.add(" "+(_attackSpeed > 0?(ChatColor.BLUE+"+"):ChatColor.RED)+_attackSpeed+ " Attack Speed");
            }
        }else {
            return null;
        }

        NBTItem nbti = new NBTItem(is);
        NBTList attribute = nbti.getList("AttributeModifiers", NBTType.NBTTagCompound);
        NBTListCompound attr = attribute.addCompound();

        attr.setDouble("Amount", _attackSpeed - 4);
        attr.setString("AttributeName", "generic.attackSpeed");
        attr.setString("Name", "generic.attackSpeed");
        attr.setInteger("Operation", 0);
        attr.setInteger("UUIDLeast", (int)(Math.random()*2147483647));
        attr.setInteger("UUIDMost", (int)(Math.random()*2147483647));
        attr.setString("Slot", slot);

        attr = attribute.addCompound();
        attr.setDouble("Amount", _damage);
        attr.setString("AttributeName", "generic.attackDamage");
        attr.setString("Name", "generic.attackDamage");
        attr.setInteger("Operation", 0);
        attr.setInteger("UUIDLeast", (int)(Math.random()*2147483647));
        attr.setInteger("UUIDMost", (int)(Math.random()*2147483647));
        attr.setString("Slot", slot);


        attr = attribute.addCompound();
        attr.setDouble("Amount", _maxHealth);
        attr.setString("AttributeName", "generic.maxHealth");
        attr.setString("Name", "generic.maxHealth");
        attr.setInteger("Operation", 0);
        attr.setInteger("UUIDLeast", (int)(Math.random()*2147483647));
        attr.setInteger("UUIDMost", (int)(Math.random()*2147483647));
        attr.setString("Slot", slot);
        if (_maxHealth != 0){
            Lore.add(" "+(_maxHealth > 0?(ChatColor.BLUE+"+"):ChatColor.RED)+_maxHealth + " Max Health");
        }


        nbti.setDouble("DurabilityMultiplier", _durabilityMultiplier);
        if (_durabilityMultiplier != 0){
            Lore.add(" "+(_durabilityMultiplier > 0?(ChatColor.BLUE+"+"):ChatColor.RED+"-")+ "Durability");
        }


        attr = attribute.addCompound();
        attr.setDouble("Amount", _armor);
        attr.setString("AttributeName", "generic.armor");
        attr.setString("Name", "generor");
        attr.setInteger("Operation", 0);
        attr.setInteger("UUIDLeast", (int)(Math.random()*2147483647));
        attr.setInteger("UUIDMost", (int)(Math.random()*2147483647));
        attr.setString("Slot", slot);
        if (_armor != 0){
            Lore.add(" "+(_armor > 0?(ChatColor.BLUE+"+"):ChatColor.RED)+_armor + " Armor");
        }


        attr = attribute.addCompound();
        attr.setDouble("Amount", _armorToughness);
        attr.setString("AttributeName", "generic.armorToughness");
        attr.setString("Name", "generic.armorToughness");
        attr.setInteger("Operation", 0);
        attr.setInteger("UUIDLeast", (int)(Math.random()*2147483647));
        attr.setInteger("UUIDMost", (int)(Math.random()*2147483647));
        attr.setString("Slot", slot);
        if (_armorToughness != 0){
            Lore.add(" "+(_armorToughness > 0?(ChatColor.BLUE+"+"):ChatColor.RED)+_armorToughness + " Armor Toughness");
        }


        attr = attribute.addCompound();
        attr.setDouble("Amount", _knockbackResist);
        attr.setString("AttributeName", "generic.knockbackResistance");
        attr.setString("Name", "generic.knockbackResistance");
        attr.setInteger("Operation", 0);
        attr.setInteger("UUIDLeast", (int)(Math.random()*2147483647));
        attr.setInteger("UUIDMost", (int)(Math.random()*2147483647));
        attr.setString("Slot", slot);
        if (_knockbackResist != 0){
            Lore.add(" "+(_knockbackResist > 0?(ChatColor.BLUE+"+"):ChatColor.RED)+ (int)(_knockbackResist*100) + "% KnockBack Resist");
        }

        attr = attribute.addCompound();
        attr.setDouble("Amount", _movementSpeed);
        attr.setString("AttributeName", "generic.movementSpeed");
        attr.setString("Name", "generic.movementSpeed");
        attr.setInteger("Operation", 0);
        attr.setInteger("UUIDLeast", (int)(Math.random()*2147483647));
        attr.setInteger("UUIDMost", (int)(Math.random()*2147483647));
        attr.setString("Slot", slot);
        if (_movementSpeed != 0){
            Lore.add(" "+(_movementSpeed> 0?(ChatColor.BLUE+"+"):ChatColor.RED)+ (int)(_movementSpeed/0.007) + "% Speed");
        }

        nbti.setInteger("MagicResist", _magicResist);
        if (_magicResist != 0){
            Lore.add(" "+(_magicResist > 0?(ChatColor.BLUE+"+"):ChatColor.RED+"-")+ "Magic Resist");
        }

        attr = attribute.addCompound();
        attr.setDouble("Amount", _luck);
        attr.setString("AttributeName", "generic.luck");
        attr.setString("Name", "generic.luck");
        attr.setInteger("Operation", 0);
        attr.setInteger("UUIDLeast", (int)(Math.random()*2147483647));
        attr.setInteger("UUIDMost", (int)(Math.random()*2147483647));
        attr.setString("Slot", slot);
        nbti.setInteger("MagicResist", _magicResist);
        if (_luck != 0){
            Lore.add(" "+(_luck > 0?(ChatColor.BLUE+"+"):ChatColor.RED+"-")+ "Luck");
        }

        nbti.setInteger("Dodge",_dodge);
        if (_dodge != 0){
            Lore.add(" "+(_dodge > 0?(ChatColor.BLUE+"+"):ChatColor.RED+"-")+ "Dodge");
        }
        nbti.setInteger("Deflect",_deflect);
        if (_deflect != 0){
            Lore.add(" "+(_deflect > 0?(ChatColor.BLUE+"+"):ChatColor.RED+"-")+ "Deflect");
        }
        nbti.setInteger("RangedDamage", _rangedDamage);
        if (_rangedDamage != 0){
            Lore.add(" "+(_rangedDamage > 0?(ChatColor.BLUE+"+"):ChatColor.RED)+_rangedDamage + " Ranged Damage");
        }
        nbti.setInteger("CriticalChance", _criticalChance);
        if (_criticalChance != 0){
            Lore.add(" "+(_criticalChance > 0?(ChatColor.BLUE+"+"):ChatColor.RED)+ (int)(_criticalChance) + "% Critical Chance");
        }

        nbti.setDouble("CriticalDamage", _criticalDmg);
        if (_criticalDmg != 50){
            Lore.add(" "+(_criticalDmg > 50?(ChatColor.BLUE+"+"):ChatColor.RED)+ (int)(_criticalDmg-50) + "% Critical Damage");
        }
        nbti.setInteger("HideFlags",2);
        is = nbti.getItem();
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(newName);
        im.setLore(Lore);
        is.setItemMeta(im);
        return is;
    }
}
