package net.waterraid.ItemSystem.Enumeration;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum MatchType {
    FAILED(4),
    //CorrectEverything
    PERFECT(4),
    //CorrectMaterialsAndTime
    IMPERFECT(3),
    //CorrectMaterialsAndAmounts
    BRITTLE(2),
    //CorrectMaterial
    RUINED(1),
    //NONE
    NONE(0);
    public final int _rank;
    private MatchType(int label ) {
        this._rank = label;
    }
    public int getRank() {
        return _rank;
    }
    public ItemStack getItem() {
        ItemStack is;
        ItemMeta im;
        switch(_rank) {
            case 1:
                is =  new ItemStack(Material.GOLD_NUGGET, 1);
                im = is.getItemMeta();
                im.setDisplayName(ChatColor.RED+"Ruined Ingot");
                is.setItemMeta(im);
                return is;
            case 2:
                is =  new ItemStack(Material.GOLD_NUGGET, 1);
                im = is.getItemMeta();
                im.setDisplayName(ChatColor.YELLOW+"Brittle Ingot");
                is.setItemMeta(im);
                return is;
            case 3:
                is =  new ItemStack(Material.GOLD_NUGGET, 1);
                im = is.getItemMeta();
                im.setDisplayName(ChatColor.YELLOW+"Imperfect Ingot");
                is.setItemMeta(im);
                return is;

        }
        return null;
    }
}
