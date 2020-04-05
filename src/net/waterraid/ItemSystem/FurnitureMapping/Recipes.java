package net.waterraid.ItemSystem.FurnitureMapping;


import net.waterraid.ItemSystem.Enumeration.FurnitureType;
import net.waterraid.ItemSystem.Enumeration.MatchType;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;

public class Recipes {
    public static Set<Recipes> recipes = new HashSet<>();
    private String _id;
    private FurnitureType _furnitureType;
    private int _time;
    private int _timeRange;
    private int _chance;
    private WrappedCustomItem[] _output;
    private Set<WrappedCustomItem> _input;
    private int[] _outputChance;
    private int _totalChance = 0;

    public Recipes(String Id, FurnitureType Furniture, int Time, int TimeRange, int Chance, WrappedCustomItem[] Output, int[] OutputChance, Set<WrappedCustomItem> Input) {
        _id = Id;
        _furnitureType = Furniture;
        _chance = Chance;
        _time = Time;
        _timeRange = TimeRange;
        _output = Output;
        _outputChance = OutputChance;
        _input = Input;
        for (int i : OutputChance) {
            _totalChance += i;
        }
    }

    public MatchType matchesInputAndTime(Set<WrappedCustomItem> Input, long startTime) {
        MatchType mt = matchesInput(Input);
        if (matchesTime(startTime) && mt != MatchType.NONE){
            if(mt == MatchType.BRITTLE) {
                return MatchType.PERFECT;
            }
            return MatchType.IMPERFECT;
        }
        return mt;
    }

    public MatchType matchesInput(Set<WrappedCustomItem> Input) {
        Set<WrappedCustomItem> tempInputSet = new HashSet<WrappedCustomItem>(_input);
        boolean AmountMatches = true;
        boolean MatchesMaterialFully = true;
        a:
        for (WrappedCustomItem is : Input) {
            for (WrappedCustomItem wci: tempInputSet) {
                if (wci.equalsIgnoreAmount(is)) {
                    AmountMatches = is.getAmount() == wci.getAmount() && AmountMatches;
                    continue a;
                }
            }
            MatchesMaterialFully = false;
            break;
        }
        if (MatchesMaterialFully) {
            if (AmountMatches) {
                return MatchType.BRITTLE;
            } else {
                return MatchType.RUINED;
            }
        }
        return MatchType.NONE;

    }

    public ItemStack getOutputItem() {
        double value = Math.random() * _totalChance;
        int i;
        for (i = 0; i < _outputChance.length; i++) {
            if (_outputChance[i] > value) {
                break;
            } else {
                value -= _outputChance[i];
            }
        }
        return _output[i].getItem();

    }

    public boolean isFormed() {
        return (int) (Math.random() * 100) < _chance;
    }

    public String getId() {
        return _id;
    }

    /**
     * Check if the time at which the item was taken out matches the time for the recipe
     *
     * @param StartTime
     * @return
     */
    private boolean matchesTime(long StartTime) {
        int check = (int) ((System.currentTimeMillis() - StartTime) / 100);
        return check > (_time - _timeRange) && check < (_time + _timeRange);
    }

    public static Set<Recipes> getAll() {
        return recipes;
    }

    public static Object matchInputAndTime(Furniture furniture) {
        MatchType match = MatchType.NONE;
        for (Recipes recipe : Recipes.getAll()) {
            MatchType mt = recipe.matchesInputAndTime(furniture.getItems(), furniture.getStartTime());
            match = match.getRank() < mt.getRank() ? mt : match;
            if (match == MatchType.PERFECT) {
                if (recipe.isFormed()) {
                    return recipe;
                } else {
                    return MatchType.FAILED;
                }
            }
        }
        return match;
    }

    public static Recipes find(String id) {
        for(Recipes recipe: recipes) {
            if(recipe.getId().equals(id))
                return recipe;
        }
        return null;
    }

    public static void unloadRecipes(){
        recipes = new HashSet<>();
    }

    @Override
    public int hashCode() {
        return _id.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Recipes && ((Recipes) o).getId().equals(this.getId())) {
            return true;
        }
        return false;
    }
}
