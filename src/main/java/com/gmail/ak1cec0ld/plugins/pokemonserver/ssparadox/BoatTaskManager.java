package com.gmail.ak1cec0ld.plugins.pokemonserver.ssparadox;

import com.gmail.ak1cec0ld.plugins.pokemonserver.PokemonServer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

class BoatTaskManager {


    BoatTaskManager() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(PokemonServer.instance(), boatCheck, 20L, 20L);
    }
    
    private Runnable boatCheck = () -> {
        long currentTime = Bukkit.getWorld("Japan").getTime();
        long dockVermilion = 1000;
        long dockOlivine = 3950;
        long dockSlateport = 7390;
        long dockLilycove = 9395;
        long dockCanalave = 16566;
        long dockSnowpoint = 18970;
        long dockTime = 1200;

        if(dockVermilion -10 < currentTime && currentTime < dockVermilion +10 ){
            messageTicketHolders(ChatColor.AQUA+"SS Paradox, Arriving at Vermilion City");
        } else if(dockOlivine -10 < currentTime && currentTime < dockOlivine +10){
            messageTicketHolders(ChatColor.AQUA+"SS Paradox, Arriving at Olivine City");
        } else if(dockSlateport -10 < currentTime && currentTime < dockSlateport +10){
            messageTicketHolders(ChatColor.AQUA+"SS Paradox, Arriving at Slateport City");
        } else if(dockLilycove -10 < currentTime && currentTime < dockLilycove +10){
            messageTicketHolders(ChatColor.AQUA+"SS Paradox, Arriving at Lilycove City");
        } else if(dockCanalave -10 < currentTime && currentTime < dockCanalave +10){
            messageTicketHolders(ChatColor.AQUA+"SS Paradox, Arriving at Canalave City");
        } else if(dockSnowpoint -10 < currentTime && currentTime < dockSnowpoint +10){
            messageTicketHolders(ChatColor.AQUA+"SS Paradox, Arriving at Snowpoint City");
        }

        if( dockVermilion < currentTime && currentTime <= dockVermilion + dockTime){
            moveTicketHolders(BoatController.vermilion,BoatController.ssparadox);
            moveTicketHolders(BoatController.ssparadox,BoatController.vermilion);
        } else if( dockOlivine < currentTime && currentTime <= dockOlivine + dockTime){
            moveTicketHolders(BoatController.olivine,BoatController.ssparadox);
            moveTicketHolders(BoatController.ssparadox,BoatController.olivine);
        } else if( dockSlateport < currentTime && currentTime <= dockSlateport + dockTime){
            moveTicketHolders(BoatController.slateport,BoatController.ssparadox);
            moveTicketHolders(BoatController.ssparadox,BoatController.slateport);
        } else if( dockLilycove < currentTime && currentTime <= dockLilycove + dockTime){
            moveTicketHolders(BoatController.lilycove,BoatController.ssparadox);
            moveTicketHolders(BoatController.ssparadox,BoatController.lilycove);
        } else if( dockCanalave < currentTime && currentTime <= dockCanalave + dockTime){
            moveTicketHolders(BoatController.canalave,BoatController.ssparadox);
            moveTicketHolders(BoatController.ssparadox,BoatController.canalave);
        } else if( dockSnowpoint < currentTime && currentTime <= dockSnowpoint + dockTime){
            moveTicketHolders(BoatController.snowpoint,BoatController.ssparadox);
            moveTicketHolders(BoatController.ssparadox,BoatController.snowpoint);
        }
    };

    private void moveTicketHolders(Location from, Location to) {
        for(Player each : from.getWorld().getPlayers()){
            if(!hasTeleported(each) && each.getLocation().distance(from) < 4 && hasSSTicket(each)){
                each.teleport(to);
                each.sendMessage("Thank you for using the SS Paradox! Please move Ticket Holders away from the doors to clear the way for other users!");
                markTeleported(each);
            }
        }
    }
    private void messageTicketHolders(String message){
        for(Player each : Bukkit.getOnlinePlayers()){
            if(hasSSTicket(each)){
                each.sendMessage(message);
            }
        }
    }
    private void markTeleported(Player player) {
        player.setMetadata("tpd", new FixedMetadataValue(PokemonServer.instance(), true));
        Bukkit.getScheduler().scheduleSyncDelayedTask(PokemonServer.instance(), () -> player.removeMetadata("tpd", PokemonServer.instance()), 100L);
    }

    private boolean hasTeleported(Player player) {
        return player.hasMetadata("tpd") && player.getMetadata("tpd").size() > 0 && player.getMetadata("tpd").get(0).asBoolean();
    }

    private boolean hasSSTicket(Player player){
        for(ItemStack item : player.getInventory().getContents()){
            if(SSTicketListener.isSSTicket(item)){
                return true;
            }
        }
        return false;
    }
}
