package net.waterraid.ItemSystem;

import net.waterraid.ItemSystem.FileIO.GeneralFileIO;
import net.waterraid.ItemSystem.FurnitureMapping.CustomItem;
import net.waterraid.ItemSystem.FurnitureMapping.Recipes;
import net.waterraid.ItemSystem.utils.Utility;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Commands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String Label, String[] args) {
        if(command.getName().equalsIgnoreCase("ItemSystem")){
            if (args.length == 0){
                sender.sendMessage(ChatColor.GRAY + "ItemSystem by Arhke");
            }else if(args.length == 1){
                if(args[0].equalsIgnoreCase("reload")){
                    sender.sendMessage("Reloading...");
                    Recipes.unloadRecipes();
                    CustomItem.unloadCustomItems();
                    GeneralFileIO.load();
                    sender.sendMessage("Reloaded!");
                }
            } else if (args.length == 2){
                if (args[0].equalsIgnoreCase("get")){
                    if (sender instanceof Player) {
                        CustomItem ci = CustomItem.findItem(args[1]);
                        if (ci != null){
                            Utility.addItemtoPlayer((Player)sender, ci.getItem());
                            sender.sendMessage("Giving "+args[1]);
                        }else{
                            sender.sendMessage("Item "+args[1]+" not found!");
                        }
                    }else{
                        sender.sendMessage("Only Players can use this command");
                    }

                }
            }
            return true;
        }
        return false;
    }
}
