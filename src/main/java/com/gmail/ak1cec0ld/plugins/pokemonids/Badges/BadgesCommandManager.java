package com.gmail.ak1cec0ld.plugins.pokemonids.Badges;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.chat.*;

public class BadgesCommandManager implements CommandExecutor{
    BadgesController controller;
    
    public BadgesCommandManager(BadgesController controller){
        this.controller = controller;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String pokemonregion = "";
        Player player = (sender instanceof Player) ? (Player)sender : null;
        if(player == null)return false;
        if(args.length == 0){
            help(player);
        } else if(args.length == 1){
            if(args[0].matches("^[Kk].{1,4}[HhOo]$")){
                pokemonregion = "kanto";
            } else if(args[0].matches("^[Jj].{1,4}[HhOo]$")){
                pokemonregion = "johto";
            } else if(args[0].matches("^[Hh].{1,4}[Nn]$")){
                pokemonregion = "hoenn";
            } else if(args[0].matches("^[Ss].{1,5}[HhOo]$")){
                pokemonregion = "sinnoh";
            } else if(args[0].matches("^[Uu].{1,4}[AaHh]$")){
                pokemonregion = "unova";
            }
            if (args[0].equalsIgnoreCase("gym")){
                if(getCityFromLeader(player).length() > 0){
                    controller.getPlugin().getServer().dispatchCommand(controller.getPlugin().getServer().getConsoleSender(), "warp "+getCityFromLeader(player)+" "+player.getName());
                } else {
                    return false;
                }
            } else {
                messageBadges(pokemonregion,player,player);
            }
        } else if(args.length == 2){
            if (args[0].equalsIgnoreCase("give")){
                Player target = controller.getPlugin().getPlayerFromString(args[1]);
                if(!target.equals(player)){
                    giveBadge(player,target,getCityFromLeader(player));
                }
            } else if(args[0].equalsIgnoreCase("check")){
                Player p = controller.getPlugin().getPlayerFromString(args[1]);
                if(p!=null){
                    messageBadges("kanto",p,player);
                    messageBadges("johto",p,player);
                    messageBadges("hoenn",p,player);
                    messageBadges("sinnoh",p,player);
                    messageBadges("unova",p,player);
                }
            }
        } else if(args.length == 3){
            if (args[0].equalsIgnoreCase("give")){
                Player target = controller.getPlugin().getPlayerFromString(args[1]);
                String city = getBadgeFromString(args[2]);
                giveBadge(player,target,city);
            } else if (args[0].equalsIgnoreCase("take")){
                Player target = controller.getPlugin().getPlayerFromString(args[1]);
                String city = getBadgeFromString(args[2]);
                if(target!=null){
                    takeBadge(player,target,city);
                } else {help(player);}
            } else if (args[0].equalsIgnoreCase("check")){
                if(args[2].matches("^[Kk].{1,4}[HhOo]$")){
                    pokemonregion = "kanto";
                } else if(args[2].matches("^[Jj].{1,4}[HhOo]$")){
                    pokemonregion = "johto";
                } else if(args[2].matches("^[Hh].{1,4}[Nn]$")){
                    pokemonregion = "hoenn";
                } else if(args[2].matches("^[Ss].{1,5}[HhOo]$")){
                    pokemonregion = "sinnoh";
                } else if(args[2].matches("^[Uu].{1,4}[AaHh]$")){
                    pokemonregion = "unova";
                } else {help(player);}
                Player target = controller.getPlugin().getPlayerFromString(args[1]);
                if(target!=null){
                    messageBadges(pokemonregion,target,player);
                } else {
                    OfflinePlayer target2 = controller.getPlugin().getOfflinePlayerFromString(args[1]);
                    if(target2!=null){
                        messageBadges(pokemonregion,target2,player);
                    } else {help(player);}
                }
            } else if (args[0].equalsIgnoreCase("takeall")){
                if("kanto,johto,hoenn,sinnoh,unova,kalos,alola,galar".contains(args[2].toLowerCase())){
                    Player target = controller.getPlugin().getPlayerFromString(args[1]);
                    if(target!=null){
                        for(String cityname : controller.getStorageManager().getAllCityNames()){
                            if(controller.getStorageManager().getCityRegion(cityname).equals(args[2].toLowerCase())){
                                takeBadge(player,target,cityname);
                            }
                        }
                    } else {
                        OfflinePlayer target2 = controller.getPlugin().getOfflinePlayerFromString(args[1]);
                        if(target2!=null){
                            for(String cityname : controller.getStorageManager().getAllCityNames()){
                                if(controller.getStorageManager().getCityRegion(cityname).equals(args[2].toLowerCase())){
                                    takeBadge(player,target2,cityname);
                                }
                            }
                        }
                    }
                } else {help(player);}
            } else {help(player);}
        } else {help(player);}
        return false;
    }

    private void help(Player player) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6Badges &bv3.0 &dCommand Usage:"));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6/badges &c[Region] &6to check your Badges!"));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6/badges check &b[Player] &c[Region] &6to see player's badges!"));
        if(getCityFromLeader(player).length()>0){
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6/badges give &b[Player] &6to give a player's badges!"));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6/badges gym &6to return to your gym's city."));
        }
        if(player.hasPermission("badges.give")){
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6/badges give &b[Player] &c[City] &6to give a specific badge."));
        }
        if(player.hasPermission("badges.take")){
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6/badges take &b[Player] &c[City] &6to take a specific badge."));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6/badges takeall &b[Player] &c[Region] &6to take region badges!"));
        }
    /*
     *
     * /badges 
     * /badges {region}
     * /badges give [Player]
     * /badges take [Player] [badge]
     * /badges give [Player] {region}
     * /badges check [Player] [region]
     * /badges takeall [Player] [region]
     * 
     */
    }

    private String getCityFromLeader(Player player) {
        for(String cityname : controller.getStorageManager().getAllCityNames()){
            String perm = controller.getStorageManager().getCityRegion(cityname)+"."+cityname;
            if(!player.isOp() && player.hasPermission(perm)){
                return cityname;
            }
        }
        return "";
    }

    private String getBadgeFromString(String string) {
        if(string.length() > 3){
            for(String badge : controller.getStorageManager().getAllCityNames()){
                if (badge.startsWith(string)){
                    return badge;
                }
            }
        }
        return "";
    }

    private void giveBadge(Player sender, Player target, String cityname) {
        int value = controller.getStorageManager().getCityValue(cityname);
        if(value>0){
            //get the region of the cityname
            String regionname = controller.getStorageManager().getCityRegion(cityname);
            //check if the player has the badge
            if(!controller.getPlugin().getPlayerStorageManager().hasBadge(target.getUniqueId().toString(), regionname, value)){
                controller.getPlugin().getPlayerStorageManager().addBadge(target.getUniqueId().toString(), regionname, value);
                sender.sendMessage(ChatColor.GREEN+"Congrats, you gave a badge away!");
                target.sendMessage(ChatColor.YELLOW+"Congrats you got a badge!");
            } else {
                sender.sendMessage(ChatColor.RED+"The player already has the "+cityname+" badge!");
            }
        }
    }

    private void takeBadge(Player sender, OfflinePlayer target, String cityname) {
        int value = controller.getStorageManager().getCityValue(cityname);
        if(value>0){
            String regionname = controller.getStorageManager().getCityRegion(cityname);
            if(controller.getPlugin().getPlayerStorageManager().hasBadge(target.getUniqueId().toString(), regionname, value)){
                controller.getPlugin().getPlayerStorageManager().deleteBadge(target.getUniqueId().toString(), regionname, value);
                sender.sendMessage(ChatColor.YELLOW+"Badge removed!");
            } else {
                sender.sendMessage(ChatColor.RED+"That player doesn't appear to have "+cityname+" badge...");
            }
        }
    }

    private void messageBadges(String pokemonregion, OfflinePlayer player, Player receiver) {
        for(String cityname : controller.getStorageManager().getAllCityNames()){
            if(pokemonregion.equalsIgnoreCase(controller.getStorageManager().getCityRegion(cityname))){
                if(controller.getPlugin().getPlayerStorageManager().hasBadge(player.getUniqueId().toString(), pokemonregion.toLowerCase(), controller.getStorageManager().getCityValue(cityname))){
                    receiver.sendMessage(controller.getStorageManager().getCityBadgeName(cityname));
                } else {
                    String replaced = ChatColor.YELLOW+cityname.substring(0, 1).toUpperCase()+cityname.substring(1);
                    for(Player each : Bukkit.getOnlinePlayers()){
                        if(this.getCityFromLeader(each).equals(cityname)){
                            replaced += ChatColor.DARK_PURPLE+" Leader: "+each.getName();
                        }
                    }
                    TextComponent message = new TextComponent("??? Badge");
                    message.setColor(net.md_5.bungee.api.ChatColor.DARK_GRAY);
                    message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(replaced).create()));
                    receiver.spigot().sendMessage(message);
                }
            }
        }
    }
}
