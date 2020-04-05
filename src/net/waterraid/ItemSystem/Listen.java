package net.waterraid.ItemSystem;


import de.tr7zw.itemnbtapi.NBTItem;
import net.minecraft.server.v1_12_R1.MobEffectList;
import net.waterraid.ItemSystem.Enumeration.FurnitureType;
import net.waterraid.ItemSystem.Enumeration.MatchType;
import net.waterraid.ItemSystem.FurnitureMapping.*;
import net.waterraid.ItemSystem.utils.Utility;
import net.waterraid.ItemSystem.utils.WrappedLocation;
import org.bukkit.*;
import org.bukkit.block.BlockState;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Cauldron;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;

public class Listen implements Listener {
    @EventHandler
    @SuppressWarnings("deprecation")
    public void onBlockClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            FurnitureType ft = Utility.isFurniture(event.getClickedBlock().getLocation());
            /**
             * Checks if the person is using EnderEye to remove the frame
             */
            if (event.getClickedBlock().getType() == Material.ENDER_PORTAL_FRAME) {
                if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
                    if (event.getItem() != null) {
                        if (event.getItem().getType() == Material.END_CRYSTAL && ft == FurnitureType.NONE) {
                            event.getItem().setAmount(event.getItem().getAmount() - 1);
                            event.getClickedBlock().getLocation().getWorld().spawnEntity(
                                    new WrappedLocation(event.getClickedBlock().getLocation()).add(0.5, 1, 0.5), EntityType.ENDER_CRYSTAL);
                            event.setCancelled(true);
                            return;
                        } else if (event.getItem().getType() == Material.EYE_OF_ENDER) {
                            event.getClickedBlock().setType(Material.AIR);
                            event.getClickedBlock().getLocation().getWorld().dropItem(
                                    new WrappedLocation(event.getClickedBlock().getLocation()).add(0.5, 1, 0.5),
                                    new ItemStack(Material.ENDER_PORTAL_FRAME, 1));
                            event.setCancelled(true);
                            return;
                        } else if (ft == FurnitureType.NONE) {
                            event.getPlayer().sendMessage(ChatColor.RED + "This Mythic Table is incomplete, " +
                                    "Please add an End Crystal");
                        } else {
                            event.getPlayer().sendMessage(ChatColor.RED + "Left Click with Crystal to add Crystal, " +
                                    "Left click with Ender Eye to remove the portal/crystal, " +
                                    "Left Click with Nothing to see your stats");
                            return;
                        }
                    } else if (ft == FurnitureType.MYTHIC_TABLE) {
                        //FIXME
                        event.getPlayer().sendMessage("MYTHIC_TABLE");
                        event.setCancelled(true);
                        return;
                    }
                } else if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getItem() != null && event.getItem().getType() == Material.EYE_OF_ENDER) {
                    event.getPlayer().sendMessage(ChatColor.RED + "Please Left Click The Block, Right Click Glitches In This Version And Runs Twice");
                    event.setCancelled(true);
                    return;
                } else if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    event.getPlayer().sendMessage(ChatColor.RED + "Please Left Click The Block, Right Click Glitches In This Version And Runs Twice");
                    return;
                }
            }
            Player player = event.getPlayer();
            ItemStack is = player.getItemInHand();
            if (is.getType() == Material.LAVA_BUCKET && is.getItemMeta().hasDisplayName() && is.getItemMeta().getDisplayName().equals(ChatColor.RED + "Molten Metal")) {
                event.setCancelled(true);
            }
            if (ft == FurnitureType.NONE || event.getAction() == Action.LEFT_CLICK_BLOCK)
                return;

            Location loc = event.getClickedBlock().getLocation();
            Furniture furniture = Furniture.findFurniture(loc);

            switch (ft) {
                case SMELTERY:
                    if (furniture != null) {
                        if (is.getType() == Material.WATCH) {
                            player.sendMessage(ChatColor.GRAY + "This Forge has been Smelting for " + (int) ((System.currentTimeMillis() - furniture.getStartTime()) / 60000) + " minutes.");
                            event.setCancelled(true);
                            break;
                        } else if (is.getType() == Material.BUCKET) {
                            ItemStack itemstack = new ItemStack(Material.LAVA_BUCKET, 1);
                            Utility.setDisplayName(itemstack, ChatColor.RED + "Molten Metal");
                            NBTItem nbti = new NBTItem(itemstack);
                            Object o = Recipes.matchInputAndTime(furniture);

                            if (o instanceof Recipes) {
                                nbti.setString("Output", ((Recipes) o).getId());
                            } else {
                                nbti.setString("MatchType", ((MatchType) o).toString());
                            }
                            is.setAmount(is.getAmount() - 1);
                            Utility.addItemtoPlayer(player, nbti.getItem());
                            Furniture.removeFurniture(furniture);
                            event.setCancelled(true);
                            break;
                        }
                    }

                    if (is == null || is.getType() == Material.AIR) {
                        if (furniture == null) {
                            player.sendMessage(ChatColor.GRAY + "This is a Forge, Right Click with Stuff to add to the Forge");
                        } else {
                            player.sendMessage(ChatColor.GRAY + "This Forge is Smelting...");
                        }
                    } else if (Furniture.isValidMaterial(is.getType())) {
                        if (furniture == null) {
                            furniture = new Smeltery(loc);
                            Furniture.addFurniture(furniture);
                        }
                        furniture.addItem(is);
                        is.setAmount(is.getAmount() - 1);
                    } else if (Furniture.isExplodingMaterial(is.getType())) {
                        is.setAmount(is.getAmount() - 1);
                        loc.getWorld().createExplosion(loc, 1);
                        if (furniture != null)
                            Furniture.removeFurniture(furniture);
                    }
                    event.setCancelled(true);
                    break;
                case CAULDRON:
                    //FIXME
                    event.getPlayer().sendMessage("CAULDRON");
                    break;
                case EXTRACTOR:
                    //FIXME
                    event.getPlayer().sendMessage("EXTRACTOR");
                    break;
                case COOLANT:
                    if (is.getType() == Material.LAVA_BUCKET && is.getItemMeta().hasDisplayName() && is.getItemMeta().getDisplayName().equals(ChatColor.RED + "Molten Metal")) {
                        BlockState cauldronState = event.getClickedBlock().getState();
                        Cauldron cauldron = (Cauldron) event.getClickedBlock().getState().getData();
                        if (!cauldron.isEmpty()) {
                            NBTItem nbti = new NBTItem(is);
                            if (nbti.hasKey("Output")) {
                                Utility.addItemtoPlayer(player, Recipes.find(nbti.getString("Output")).getOutputItem());
                            } else {
                                if (nbti.getString("MatchType").equals("NONE")) {
                                    event.getPlayer().sendMessage(ChatColor.GRAY + "The Metal Turns to Dust in the Coolant");
                                } else if (nbti.getString("MatchType").equals("FAILED")) {
                                    event.getPlayer().sendMessage(ChatColor.GRAY + "The Metal Begins To Form, but falls apart as you go pick it up");
                                } else {
                                    Utility.addItemtoPlayer(player, MatchType.valueOf(nbti.getString("MatchType")).getItem());
                                }
                            }
                            event.getPlayer().setItemInHand(new ItemStack(Material.BUCKET, 1));
                            cauldronState.getData().setData((byte) (cauldron.getData() - 1));
                            cauldronState.update();
                        } else {
                            event.getPlayer().sendMessage("This Coolant is empty, Fill it up with water to continue to cool metal");
                        }
                    }
                    break;
                case ZTANNERY:
                    //FIXME
                    event.getPlayer().sendMessage("ZTANNERY");
                    event.setCancelled(true);
                    break;
                case XTANNERY:
                    //FIXME
                    event.getPlayer().sendMessage("XTANNERY");
                    event.setCancelled(true);
                    break;
                case BREWING_TABLE:
                    //FIXME
                    event.getPlayer().sendMessage("BREWING_TABLE");
                    event.setCancelled(true);
                    break;
                default:
                    break;
            }
        }

    }

    @EventHandler
    @SuppressWarnings("deprecation")
    public void onCrystalClick(EntityDamageByEntityEvent event) {
        if (event.getEntityType() == EntityType.ENDER_CRYSTAL) {
            if (event.getDamager() instanceof Player) {
                if (((Player) event.getDamager()).getItemInHand().getType() == Material.EYE_OF_ENDER) {
                    event.getEntity().getLocation().getWorld().dropItem(new WrappedLocation(event.getEntity().getLocation()).add(0, 0, 0), new ItemStack(Material.END_CRYSTAL, 1));
                    event.getEntity().remove();
                } else {
                    ((Player) event.getDamager()).sendMessage("Crafting Mythic Table");
                }
            }
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onCraft(PrepareItemCraftEvent event) {
        if (event.getInventory().getContents()[0].getType().getMaxDurability() != 0) {
            String Id;
            if (event.getInventory().getContents()[0].getType().toString().indexOf("IRON_") != -1 ||
                    event.getInventory().getContents()[0].getType() == Material.SHIELD) {
                if ((Id = Utility.getIdOfTable(event.getInventory().getContents(), Material.IRON_INGOT)) != null) {
                    CustomItem ci = CustomItem.findItem(Id);
                    if (ci instanceof SmelteryItem) {
                        event.getInventory().setItem(0, Utility.smelteryGetSlots(ci, event.getInventory().getContents()[0].getType()));
                    } else {
                        return;
                    }
                } else {
                    return;
                }

            } else if (event.getInventory().getContents()[0].getType().toString().indexOf("GOLD_") != -1) {
                if ((Id = Utility.getIdOfTable(event.getInventory().getContents(), Material.GOLD_INGOT)) != null) {
                    CustomItem ci = CustomItem.findItem(Id);
                    if (ci instanceof SmelteryItem) {
                        event.getInventory().setItem(0, Utility.smelteryGetSlots(ci, event.getInventory().getContents()[0].getType()));
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            } else if (event.getInventory().getContents()[0].getType().toString().indexOf("DIAMOND_") != -1) {
                if ((Id = Utility.getIdOfTable(event.getInventory().getContents(), Material.DIAMOND)) != null) {
                    CustomItem ci = CustomItem.findItem(Id);
                    if (ci instanceof SmelteryItem) {
                        event.getInventory().setItem(0, Utility.smelteryGetSlots(ci, event.getInventory().getContents()[0].getType()));
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            }
        }

    }

    @EventHandler
    public void durabilityEvent(PlayerItemDamageEvent event) {

        NBTItem nbti = new NBTItem(event.getItem());
        if (nbti.hasKey("DurabilityMultiplier")) {
            double damage = event.getDamage();
            double durabilityMultiplier = nbti.getDouble("DurabilityMultiplier");
            double dura = 0;
            if (nbti.hasKey("Durability")) {
                dura = nbti.getDouble("Durability");
            } else {
                dura = durabilityMultiplier;
            }

            if (damage % durabilityMultiplier >= dura) {
                event.setDamage((int) (damage / durabilityMultiplier) + 1);
                damage = damage % durabilityMultiplier;
                nbti.setDouble("Durability", durabilityMultiplier - damage + dura);
            } else {
                event.setDamage((int) (damage / durabilityMultiplier));
                damage = damage % durabilityMultiplier;
                nbti.setDouble("Durability", dura - damage);
            }
            event.getItem().setItemMeta(nbti.getItem().getItemMeta());
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event){
        if (event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
            if(event.getDamager() instanceof Arrow && ((Arrow)event.getDamager()).getShooter() instanceof Player && event.getDamage() > 5) {
                event.setDamage(event.getDamage() + Utility.getPlayerSlotInteger((Player)((Arrow)event.getDamager()).getShooter(), "RangedDamage"));
            }
            if (event.getEntity() instanceof Player) {
                Player player = ((Player)event.getEntity());
                if (Utility.chance(100,Utility.getPlayerSlotInteger(player, "Deflect")/6d)){
                    player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 3,3);
                    event.setCancelled(true);
                }

            }
        }else if(event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK ||event.getCause() == EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK) {
            if(event.getDamager() instanceof Player && event.getDamage() > 1.5){
                Player player = ((Player)event.getDamager());
                if(player.getFallDistance() > 0.0F && !player.isSprinting() && !player.isOnGround() && !player.getLocation().getBlock().isLiquid() && !player.hasPotionEffect(PotionEffectType.BLINDNESS) && player.getVehicle() == null){
                    event.setDamage(event.getDamage() / 1.5 * (1.0 + Utility.getPlayerSlotInteger(player, "CriticalDamage")/600d));
                }else if(Utility.chance(100,Utility.getPlayerSlotInteger(player, "CriticalChance")/6d)) {
                    event.setDamage(event.getDamage() * (1.0 + Utility.getPlayerSlotInteger(player, "CriticalDamage")/600d));
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_ATTACK_CRIT, 3,5);
                    event.getEntity().getWorld().spawnParticle(Particle.CRIT, new WrappedLocation(event.getEntity().getLocation()).add(0,event.getEntity().getHeight()*0.9,0), 7);
                }
            }
            if (event.getEntity() instanceof Player) {
                Player player = ((Player)event.getEntity());
                if (Utility.chance(100,Utility.getPlayerSlotInteger(player, "Dodge")/6d)){
                    player.playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, 3,3);
                    event.setCancelled(true);
                }
            }
        }else if(event.getCause() == EntityDamageEvent.DamageCause.MAGIC) {
            if (event.getEntity() instanceof Player) {
                Player player = ((Player) event.getEntity());
                event.setDamage(event.getDamage() / Utility.getPlayerSlotInteger(player, "MagicResist") * 6d);
            }
        }
    }
}
