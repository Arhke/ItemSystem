package net.waterraid.ItemSystem.utils;

import de.tr7zw.itemnbtapi.NBTItem;
import net.waterraid.ItemSystem.CraftItem.ColorDurabilityItem;
import net.waterraid.ItemSystem.CraftItem.DurabilityItem;
import net.waterraid.ItemSystem.Enumeration.FurnitureType;
import net.waterraid.ItemSystem.FurnitureMapping.CustomItem;
import net.waterraid.ItemSystem.FurnitureMapping.SmelteryItem;
import net.waterraid.ItemSystem.FurnitureMapping.WrappedCustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class Utility {
    public static Map<String, Color> ColorChart = new HashMap<String, Color>() {{
        put("AQUA", Color.AQUA);
        put("BLACK", Color.BLACK);
        put("BLUE", Color.BLUE);
        put("FUCHSIA", Color.FUCHSIA);
        put("GRAY", Color.GRAY);
        put("GREEN", Color.GREEN);
        put("LIME", Color.LIME);
        put("MAROON", Color.MAROON);
        put("NAVY", Color.NAVY);
        put("OLIVE", Color.OLIVE);
        put("ORANGE", Color.ORANGE);
        put("PURPLE", Color.PURPLE);
        put("RED", Color.RED);
        put("SILVER", Color.SILVER);
        put("TEAL", Color.TEAL);
        put("WHITE", Color.WHITE);
        put("YELLOW", Color.YELLOW);
    }};

    public static void addItemtoPlayer(Player player, ItemStack is) {
        if (player.getInventory().firstEmpty() != -1) {
            player.getInventory().addItem(is);
        } else {
            player.sendMessage(ChatColor.RED + "Your Inventory was Full, Dropping Item On Ground!");
            player.getLocation().getWorld().dropItem(player.getLocation(), is);
        }
    }

    public static void setDisplayName(ItemStack is, String name) {
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(name);
        is.setItemMeta(im);
    }

    public static FurnitureType isFurniture(Location location) {
        WrappedLocation loc = new WrappedLocation(location);
        Material mat = location.getBlock().getType();
        if (mat == Material.WORKBENCH) {
            if (loc.add(0, 3, 0).getBlock().getType() == Material.FENCE) {
                if (loc.add(1, 0, 0).getBlock().getType() == Material.FENCE &&
                        loc.add(-1, 0, 0).getBlock().getType() == Material.FENCE &&
                        loc.add(1, 1, 0).getBlock().getType() == Material.FENCE &&
                        loc.add(-1, 1, 0).getBlock().getType() == Material.FENCE &&
                        loc.add(1, 2, 0).getBlock().getType() == Material.FENCE &&
                        loc.add(-1, 2, 0).getBlock().getType() == Material.FENCE &&
                        loc.add(1, 3, 0).getBlock().getType() == Material.FENCE &&
                        loc.add(-1, 3, 0).getBlock().getType() == Material.FENCE) {
                    return FurnitureType.XTANNERY;
                } else if (loc.add(0, 0, 1).getBlock().getType() == Material.FENCE &&
                        loc.add(0, 0, -1).getBlock().getType() == Material.FENCE &&
                        loc.add(0, 1, 1).getBlock().getType() == Material.FENCE &&
                        loc.add(0, 1, -1).getBlock().getType() == Material.FENCE &&
                        loc.add(0, 2, 1).getBlock().getType() == Material.FENCE &&
                        loc.add(0, 2, -1).getBlock().getType() == Material.FENCE &&
                        loc.add(0, 3, 1).getBlock().getType() == Material.FENCE &&
                        loc.add(0, 3, -1).getBlock().getType() == Material.FENCE) {
                    return FurnitureType.ZTANNERY;
                }
                return FurnitureType.NONE;
            } else {
                return FurnitureType.NONE;
            }
        } else if (mat == Material.FURNACE || mat == Material.BURNING_FURNACE) {
            if (loc.add(0, 1, 0).getBlock().getType() == Material.COBBLE_WALL &&
                    (loc.add(0, -1, 0).getBlock().getType() == Material.LAVA ||
                            loc.add(0, -1, 0).getBlock().getType() == Material.STATIONARY_LAVA)) {
                return FurnitureType.SMELTERY;
            }
            return FurnitureType.NONE;
        } else if (mat == Material.ENDER_PORTAL_FRAME) {
            for (Entity e : location.getWorld().getNearbyEntities(loc.add(0, 1, 0), 0.2, 0.2, 0.2)) {
                if (e instanceof EnderCrystal) {
                    return FurnitureType.MYTHIC_TABLE;
                }
            }
            return FurnitureType.NONE;
        } else if (mat == Material.BREWING_STAND) {
            if (loc.add(0, -1, 0).getBlock().getType() == Material.BRICK) {
                if ((loc.add(1, 0, 0).getBlock().getType() == Material.END_ROD &&
                        loc.add(2, 0, 0).getBlock().getType() == Material.BREWING_STAND &&
                        loc.add(2, -1, 0).getBlock().getType() == Material.BRICK &&
                        loc.add(1, -1, 0).getBlock().getType() == Material.COBBLE_WALL) ||

                        (loc.add(-1, 0, 0).getBlock().getType() == Material.END_ROD &&
                                loc.add(-2, 0, 0).getBlock().getType() == Material.BREWING_STAND &&
                                loc.add(-2, -1, 0).getBlock().getType() == Material.BRICK &&
                                loc.add(-1, -1, 0).getBlock().getType() == Material.COBBLE_WALL) ||

                        (loc.add(0, 0, 1).getBlock().getType() == Material.END_ROD &&
                                loc.add(0, 0, 2).getBlock().getType() == Material.BREWING_STAND &&
                                loc.add(0, -1, 2).getBlock().getType() == Material.BRICK &&
                                loc.add(0, -1, 1).getBlock().getType() == Material.COBBLE_WALL) ||

                        (loc.add(0, 0, -1).getBlock().getType() == Material.END_ROD &&
                                loc.add(0, 0, -2).getBlock().getType() == Material.BREWING_STAND &&
                                loc.add(0, -1, -2).getBlock().getType() == Material.BRICK &&
                                loc.add(0, -1, -1).getBlock().getType() == Material.COBBLE_WALL)) {
                    return FurnitureType.BREWING_TABLE;
                } else {
                    return FurnitureType.NONE;
                }
            } else {
                return FurnitureType.NONE;
            }
        } else if (mat == Material.CAULDRON) {
            if (loc.add(0, 1, 0).getBlock().getType() == Material.COBBLE_WALL &&
                    loc.add(0, 2, 0).getBlock().getType() == Material.WEB) {
                return FurnitureType.EXTRACTOR;
            } else if (loc.add(0, -1, 0).getBlock().getType() == Material.MAGMA) {
                return FurnitureType.CAULDRON;
            } else {
                return FurnitureType.COOLANT;
            }
        } else {
            return FurnitureType.NONE;
        }
    }

    public static String getIdOfTable(ItemStack[] itemstacks, Material mat) {
        String ret = null;
        for (int i = 1; i<itemstacks.length; i++ ){
            String s;
            if (itemstacks[i].getType() == mat) {
                if ((s = Utility.getItemMaterialTag(itemstacks[i])) == null) {
                    return null;
                } else if (ret == null) {
                    ret = s;
                }else if (!ret.equals(s)){
                    return null;
                }
            }
        }
        return ret;

    }

    public static String getItemMaterialTag(ItemStack is) {
        NBTItem nbti = new NBTItem(is);
        if (nbti.hasKey("Material")) {
            return nbti.getString("Material");
        }
        return null;
    }

    public static ItemStack setNBTIString(ItemStack is, String key, String value) {
        NBTItem nbti = new NBTItem(is);
        nbti.setString(key, value);
        return nbti.getItem();
    }

    public static String colorWrap(String input) {
        return input.replace('&', ChatColor.COLOR_CHAR);
    }

    public static Set<Material> buildValidMaterialsList(YamlConfiguration config) {
        List<String> valid = config.getStringList("Valid");
        Set<Material> ret = new HashSet<>();
        if (valid == null || valid.size() == 0) {
            return null;
        }
        valid.forEach(s -> {
                    if (s != null) {
                        ret.add(Material.valueOf(s));
                    }
                }

        );
        if (ret.size() == 0) {
            return null;
        }
        return ret;


    }

    public static Set<Material> buildExplodeMaterialsList(YamlConfiguration config) {
        List<String> valid = config.getStringList("Explode");
        Set<Material> ret = new HashSet<>();
        if (valid == null || valid.size() == 0) {
            return null;
        }
        valid.forEach(s -> {
                    if (s != null) {
                        ret.add(Material.valueOf(s));
                    }
                }

        );
        return ret;
    }

    public static DurabilityItem[] buildDurabilityItemList(YamlConfiguration config) {
        String[] str = new String[]{"Helmet", "Chestplate", "Leggings", "Boots", "Shield", "Sword", "Axe", "Pickaxe", "Shovel", "Hoe"};
        DurabilityItem[] di = new DurabilityItem[10];
        for (int i = 0; i < str.length; i++) {
            if (config.contains(str[i])) {
                di[i] = new DurabilityItem(config.getInt(str[i] + ".Damage"), config.getDouble(str[i] + ".AttackSpeed"), config.getInt(str[i] + ".MaxHealth"), config.getInt(str[i] + ".DurabilityMultiplier"), config.getInt(str[i] + ".Armor"),
                        config.getInt(str[i] + ".ArmorToughness"), config.getInt(str[i] + ".KnockbackResist"), config.getDouble(str[i] + ".MovementSpeed"),
                        config.getInt(str[i] + ".MagicResist"), config.getInt(str[i] + ".Luck"), config.getInt(str[i] + ".Dodge"), config.getInt(str[i] + ".Deflect"),
                        config.getInt(str[i] + ".RangedDamage"), config.getInt(str[i] + ".CriticalChance"), config.getDouble(str[i] + ".CriticalDmg"));
            } else {
                di[i] = new DurabilityItem();
            }
        }
        return di;
    }

    public static List<PotionEffect> buildPotionEffectList(YamlConfiguration config) {
        List<PotionEffect> lpe = new ArrayList<>();
        for (String s : config.getConfigurationSection("effects").getKeys(false)) {
            PotionEffectType pet = PotionEffectType.getByName(config.getString("effects." + s + ".PotionEffectType"));
            if (pet == null) {
                continue;
            }
            int duration = config.getInt("effects." + s + ".Duration");
            int amplifier = config.getInt("effects." + s + ".Amplifier");
            boolean ambient = config.getBoolean("effects." + s + ".Ambient");
            boolean particles = config.getBoolean("effects." + s + ".Particles");

            if (!config.contains("effects." + s + ".Color")) {
                lpe.add(new PotionEffect(pet, duration, amplifier, ambient, particles));
                continue;
            }
            Color color;
            if (config.isInt("effects." + s + ".Color")) {
                color = Color.fromRGB(config.getInt("effects." + s + ".Color"));
            } else if (config.isColor("effects." + s + ".Color")) {
                color = config.getColor("effects." + s + ".Color");
            } else {
                color = ColorChart.get(config.getString("effects." + s + ".Color"));
                if (color == null) {
                    lpe.add(new PotionEffect(pet, duration, amplifier, ambient, particles));
                    continue;
                }
            }
            lpe.add(new PotionEffect(pet, duration, amplifier, ambient, particles, color));

        }
        return lpe;
    }

    public static DurabilityItem[] buildTanneryItemList(YamlConfiguration config) {
        String[] str = new String[]{"Helmet", "Chestplate", "Leggings", "Boots"};
        DurabilityItem[] di = new DurabilityItem[10];

        for (int i = 0; i < str.length; i++) {
            Color color;
            if (config.isInt(str[i] + ".Color")) {
                color = Color.fromRGB(config.getInt(str[i] + ".Color"));
            } else if (config.isColor(str[i] + ".Color")) {
                color = config.getColor(str[i] + ".Color");
            } else {
                color = ColorChart.get(config.getString(str[i] + ".Color"));
            }
            if (config.contains(str[i])) {
                di[i] = new ColorDurabilityItem(config.getInt(str[i] + ".Damage"), config.getDouble(str[i] + ".AttackSpeed"), config.getInt(str[i] + ".MaxHealth"), config.getInt(str[i] + ".DurabilityMultiplier"), config.getInt(str[i] + ".Armor"),
                        config.getInt(str[i] + ".ArmorToughness"), config.getInt(str[i] + ".KnockbackResist"), config.getDouble(str[i] + ".MovementSpeed"),
                        config.getInt(str[i] + ".MagicResist"), config.getInt(str[i] + ".Luck"), config.getInt(str[i] + ".Dodge"), config.getInt(str[i] + ".Deflect"),
                        config.getInt(str[i] + ".RangedDamage"), config.getInt(str[i] + ".CriticalChance"), config.getDouble(str[i] + ".CriticalDmg"), color);
            } else {
                di[i] = new DurabilityItem();
            }
        }
        return di;
    }

    public static WrappedCustomItem[] buildOutputList(YamlConfiguration config) {
        List<WrappedCustomItem> wci = new ArrayList<>();
        for (String s : config.getConfigurationSection("Output").getKeys(false)) {
            String Id = config.getString("Output." + s + ".Id");
            if (Id == null || Id.length() == 0) {
                return null;
            }
            String[] splitId = Id.split(":");
            if (splitId.length == 2) {
                Material mat = Material.getMaterial(splitId[0]);
                String name = Utility.colorWrap(splitId[1]);
                int amount = config.getInt("Output." + s + ".Amount");
                if (mat == null) {
                    return null;
                }
                wci.add(new WrappedCustomItem(mat, name, amount));
            } else if (splitId.length == 1) {
                if (Id.indexOf(':') != -1) {
                    Material mat = Material.getMaterial(splitId[0]);
                    int amount = config.getInt("Output." + s + ".Amount");
                    if (mat == null) {
                        return null;
                    }
                    wci.add(new WrappedCustomItem(mat, null, amount));
                } else {
                    CustomItem ci = CustomItem.findItem(splitId[0]);
                    if (ci == null) {
                        return null;
                    }
                    int amount = config.getInt("Output." + s + ".Amount");
                    wci.add(new WrappedCustomItem(ci, amount));
                }
            } else {
                return null;
            }
        }
        WrappedCustomItem[] wciArray = new WrappedCustomItem[wci.size()];
        for (int i = 0; i < wci.size(); i++) {
            wciArray[i] = wci.get(i);
        }
        return wciArray;
    }

    public static Set<WrappedCustomItem> buildInputList(YamlConfiguration config) {
        Set<WrappedCustomItem> wci = new HashSet<>();
        for (String s : config.getConfigurationSection("Input").getKeys(false)) {
            String Id = config.getString("Input." + s + ".Id");
            if (Id == null || Id.length() == 0) {
                return null;
            }
            String[] splitId = Id.split(":");
            if (splitId.length == 2) {
                Material mat = Material.getMaterial(splitId[0]);
                String name = Utility.colorWrap(splitId[1]);
                int amount = config.getInt("Input." + s + ".Amount");
                if (mat == null) {
                    return null;
                }
                wci.add(new WrappedCustomItem(mat, name, amount));
            } else if (splitId.length == 1) {
                if (Id.indexOf(':') != -1) {
                    Material mat = Material.getMaterial(splitId[0]);
                    int amount = config.getInt("Input." + s + ".Amount");
                    if (mat == null) {
                        return null;
                    }
                    wci.add(new WrappedCustomItem(mat, null, amount));
                } else {
                    CustomItem ci = CustomItem.findItem(splitId[0]);
                    if (ci == null) {
                        return null;
                    }
                    int amount = config.getInt("Input." + s + ".Amount");
                    wci.add(new WrappedCustomItem(ci, amount));
                }
            } else {
                return null;
            }
        }
        return wci;
    }

    public static int[] buildOutputChanceList(YamlConfiguration config) {
        List<Integer> chances = new ArrayList<>();
        for (String s : config.getConfigurationSection("Output").getKeys(false)) {
            int chance = config.getInt("Output." + s + ".Chance");
            if (chance == 0) {
                return null;
            }
            chances.add(chance);
        }
        int[] ret = new int[chances.size()];
        for (int i = 0; i < chances.size(); i++) {
            ret[i] = chances.get(i);
        }
        return ret;
    }

    public static ItemStack getDurabilityItemSlot(CustomItem ci, Material mat){
        if (ci instanceof SmelteryItem) {
            SmelteryItem si = (SmelteryItem)ci;
            if(mat.toString().indexOf("Shield") != -1 && si.getShield().isCraftable()){
//                return si.getShield().applyTags();
            }

        }
        return null;
    }

    public static double getPlayerSlotInteger(Player player, String key){
        double ret = 0.0;
        for (ItemStack is: player.getInventory().getArmorContents()) {
            if(is != null && is.getType() != Material.AIR){
                NBTItem nbti = new NBTItem(is);
                if (nbti.hasKey(key)){
                    ret += nbti.getInteger(key);
                }
            }
        }
        ItemStack is = player.getInventory().getItemInOffHand();
        if(is != null && is.getType() != Material.AIR){
            NBTItem nbti = new NBTItem(is);
            if (nbti.hasKey(key)){
                ret += nbti.getInteger(key);
            }
        }
        is = player.getInventory().getItemInMainHand();
        if(is != null && is.getType() != Material.AIR){
            NBTItem nbti = new NBTItem(is);
            if (nbti.hasKey(key)){
                ret += nbti.getInteger(key);
            }
        }
        return ret;
    }

    public static boolean chance(double max, double chance){
        return Math.random()*max < chance;
    }

    public static ItemStack smelteryGetSlots (CustomItem ci, Material mat){
        if(mat == Material.DIAMOND_HELMET || mat == Material.GOLD_HELMET || mat == Material.IRON_HELMET) {
            return ((SmelteryItem) ci).getHelmet().applyTags(new ItemStack(mat, 1), ci.getName());
        }else if(mat == Material.DIAMOND_CHESTPLATE || mat == Material.GOLD_CHESTPLATE || mat == Material.IRON_CHESTPLATE){
            return ((SmelteryItem) ci).getChestPlate().applyTags(new ItemStack(mat, 1), ci.getName());
        }else if(mat == Material.DIAMOND_LEGGINGS || mat == Material.GOLD_LEGGINGS || mat == Material.IRON_LEGGINGS){
            return ((SmelteryItem) ci).getLeggings().applyTags(new ItemStack(mat, 1), ci.getName());
        }else if(mat == Material.DIAMOND_BOOTS || mat == Material.GOLD_BOOTS || mat == Material.IRON_BOOTS){
            return ((SmelteryItem) ci).getBoots().applyTags(new ItemStack(mat, 1), ci.getName());
        }else if(mat == Material.SHIELD){
            return ((SmelteryItem) ci).getShield().applyTags(new ItemStack(mat, 1), ci.getName());
        }else if(mat == Material.DIAMOND_SWORD || mat == Material.GOLD_SWORD || mat == Material.IRON_SWORD){
            return ((SmelteryItem) ci).getSword().applyTags(new ItemStack(mat, 1), ci.getName());
        }else if(mat == Material.DIAMOND_AXE || mat == Material.GOLD_AXE || mat == Material.IRON_AXE){
            return ((SmelteryItem) ci).getAxe().applyTags(new ItemStack(mat, 1), ci.getName());
        }else if(mat == Material.DIAMOND_SPADE || mat == Material.GOLD_SPADE || mat == Material.IRON_SPADE){
            return ((SmelteryItem) ci).getShovel().applyTags(new ItemStack(mat, 1), ci.getName());
        }else if(mat == Material.DIAMOND_PICKAXE || mat == Material.GOLD_PICKAXE || mat == Material.IRON_PICKAXE){
            return ((SmelteryItem) ci).getPickAxe().applyTags(new ItemStack(mat, 1), ci.getName());
        }else if(mat == Material.DIAMOND_HOE || mat == Material.GOLD_HOE || mat == Material.IRON_HOE){
            return ((SmelteryItem) ci).getHoe().applyTags(new ItemStack(mat, 1), ci.getName());
        }
        return null;

    }

}





