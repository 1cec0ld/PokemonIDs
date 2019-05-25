package com.gmail.ak1cec0ld.plugins.pokemonids.ssparadox;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public class BoatTaskManager {

    private BoatController controller;
    
    final long timeBeforeArrival = 20*30;
    
    private final long dockVermilion  = 1000;
    private final long leaveVermilion = dockVermilion + 1200;
    private final long dockOlivine    = 3950;
    private final long leaveOlivine   = dockOlivine + 1200;
    private final long dockSlateport  = 7390;
    private final long leaveSlateport = dockSlateport + 1200;
    private final long dockLilycove   = 9395;
    private final long leaveLilycove  = dockLilycove + 1200;
    private final long dockCanalave   = 16566;
    private final long leaveCanalave  = dockCanalave + 1200;
    private final long dockSnowpoint  = 18970;
    private final long leaveSnowpoint = dockSnowpoint + 1200;
    
    BoatTaskManager(BoatController boatController) {
        this.controller = boatController;
        Bukkit.getScheduler().scheduleSyncRepeatingTask(boatController.getPlugin(), boatCheck, 20L, 20L);
    }
    
    private Runnable boatCheck = () -> {
        long currentTime = Bukkit.getWorld("Japan").getTime();
        if( dockVermilion-timeBeforeArrival < currentTime && currentTime <= leaveVermilion ){
            messageBoat("Vermilion");
        }
        if( dockVermilion < currentTime && currentTime <= leaveVermilion ){
            move(BoatController.vermilion,BoatController.ssparadox);
            move(BoatController.ssparadox,BoatController.vermilion);
        } else if( dockOlivine < currentTime && currentTime <= leaveOlivine ){
            move(BoatController.olivine,BoatController.ssparadox);
            move(BoatController.ssparadox,BoatController.olivine);
        } else if( dockSlateport < currentTime && currentTime <= leaveSlateport ){
            move(BoatController.slateport,BoatController.ssparadox);
            move(BoatController.ssparadox,BoatController.slateport);
        } else if( dockLilycove < currentTime && currentTime <= leaveLilycove ){
            move(BoatController.lilycove,BoatController.ssparadox);
            move(BoatController.ssparadox,BoatController.lilycove);
        } else if( dockCanalave < currentTime && currentTime <= leaveCanalave ){
            move(BoatController.canalave,BoatController.ssparadox);
            move(BoatController.ssparadox,BoatController.canalave);
        } else if( dockSnowpoint < currentTime && currentTime <= leaveSnowpoint ){
            move(BoatController.snowpoint,BoatController.ssparadox);
            move(BoatController.ssparadox,BoatController.snowpoint);
        }
    };

    private void move(Location from, Location to) {
        for(Player each : from.getWorld().getPlayers()){
            if(each.getLocation().distance(from) < 4 && !hasTeleported(each)){
                each.teleport(to);
                each.sendMessage("Thank you for using the SS Paradox! Please move away from the doors to clear the way for other users!");
                markTeleported(each);
            }
        }
    }
    private void messageBoat(String string) {
        // TODO Auto-generated method stub
        
    }
    private void markTeleported(Player player) {
        player.setMetadata("tpd", new FixedMetadataValue(controller.getPlugin(), true));
        Bukkit.getScheduler().scheduleSyncDelayedTask(controller.getPlugin(), () -> player.removeMetadata("tpd", controller.getPlugin()), 100L);
    }

    private boolean hasTeleported(Player player) {
        return !player.getMetadata("tpd").isEmpty() && player.getMetadata("tbd").get(0).asBoolean();
    }
}
