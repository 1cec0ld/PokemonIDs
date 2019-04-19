package com.gmail.ak1cec0ld.plugins.pokemonids.quickhome;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.gmail.ak1cec0ld.plugins.pokemonids.autohouse.AutoHouseStorageManager;

public class QuickHomeCommand implements CommandExecutor{
    QuickHomeController controller;
    
    public QuickHomeCommand(QuickHomeController controller){
        this.controller = controller;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 1 ){
            if(args[0].equalsIgnoreCase("list")){
                listHousesByPage(sender,1);
            } else {
                listHousesByCity(sender,args[0]);
            }
        } else if(args.length == 2){
            if(args[0].equalsIgnoreCase("list")){
                try{
                    int page = Integer.valueOf(args[1]);
                    if(page > 0){
                        listHousesByPage(sender,page);
                    } else {
                        sender.sendMessage(ChatColor.DARK_RED+"You didn't use a valid number as your Page selection!");
                        return true;
                    }
                } catch (NumberFormatException e){
                    sender.sendMessage(ChatColor.DARK_RED+"You didn't use a valid number as your Page selection!");
                    return true;
                }
            } else if(args[0].equalsIgnoreCase("size")){
                try{
                    int size = Integer.valueOf(args[1]);
                    if(size > 0){
                        listHousesBySize(sender,size);
                    } else {
                        sender.sendMessage(ChatColor.DARK_RED+"You didn't use a valid size!");
                    }
                } catch(NumberFormatException e){
                    sender.sendMessage(ChatColor.DARK_RED+"You didn't use a valid size!");
                }
            }
        } else {
            help(sender);
        }
        return true;
    }
    
    private void listHousesByPage(CommandSender sender, int page) {
        final int start = (page*20)-20;
        int counter = 0;
        Set<String> houselist = AutoHouseStorageManager.getAllHouses();
        if(start < houselist.size()){
            for(String house : houselist){
                if(counter >= start){
                    if(counter < start+20){
                        int ownerCount = AutoHouseStorageManager.getHouseOwners(house).size();
                        if(ownerCount > 2){
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mail send 1cec0ld "+house+" has too many owners???");
                            counter--;
                        } else if(ownerCount < 2){
                            sender.sendMessage(formatHouse(house,counter,ownerCount));
                        }
                    } else {
                        return;
                    }
                }
                counter++;
            }
        } else {
            sender.sendMessage(ChatColor.DARK_RED+"Invalid Page. There are only "+ChatColor.GREEN + (houselist.size()/20+1)+ChatColor.DARK_RED+" pages.");
        }
    }

    private void listHousesByCity(CommandSender sender, String city){
        String citystart = (city.length() > 4) ? city.substring(0, 4) : city;
        Set<String> houselist = AutoHouseStorageManager.getAllHouses();
        int counter = 0;
        for(String house : houselist){
            if(house.startsWith(citystart)){
                int ownerCount = AutoHouseStorageManager.getHouseOwners(house).size();
                if(ownerCount < 2){
                    sender.sendMessage(formatHouse(house,counter,ownerCount));
                    counter++;
                }
            }
        }
    }
    
    private void listHousesBySize(CommandSender sender, int size){
        Set<String> houselist = AutoHouseStorageManager.getAllHouses();
        int counter = 0;
        for(String house : houselist){
            int housesize = AutoHouseStorageManager.getHouseSize(house);
            if(housesize == size || (housesize > size && counter < 20)){
                int ownerCount = AutoHouseStorageManager.getHouseOwners(house).size();
                if(ownerCount < 2){
                    sender.sendMessage(formatHouse(house,counter,ownerCount));
                    counter++;
                }
            }
        }
    }
    
    private String formatHouse(String house, int index, int owners){
        return ChatColor.LIGHT_PURPLE+""+(index+1)+". "+
                (owners==0?ChatColor.DARK_AQUA:ChatColor.AQUA)+capitalize(house).replace("house", " House: ")+ChatColor.GOLD+" (Size: "+AutoHouseStorageManager.getHouseSize(house)+")";
    }
    
    private String capitalize(String input){
        return input.substring(0,1).toUpperCase()+input.substring(1, input.length());
    }
    
    private void help(CommandSender sender){
        sender.sendMessage( ChatColor.translateAlternateColorCodes('&',
                "&6Legend:\n"+ 
                "&6Use &a&o/houses [cityName]&6 to see available homes in that city!\n"+
                "Use &a&o/houses list [page#]&6 to see a list of 20 at once!\n" +
                "Use &a&o/houses size [minimumSize#]&6 to see only houses that size or larger!\n" +
                "&3Dark Blue = 0 owners, open for sale.\n"+ 
                "&bLight Blue = 1 owner, need their permission to join them.\n"));
    }
}
