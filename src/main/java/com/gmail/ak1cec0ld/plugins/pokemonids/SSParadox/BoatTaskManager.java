package com.gmail.ak1cec0ld.plugins.pokemonids.SSParadox;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public class BoatTaskManager {

    private BoatController controller;
    
    final long timeBeforeArrival = 20*30;
    
    final long dockVermilion  = 1000;
    final long leaveVermilion = dockVermilion + 1200;
    final long dockOlivine    = 3950;
    final long leaveOlivine   = dockOlivine + 1200;
    final long dockSlateport  = 7390;
    final long leaveSlateport = dockSlateport + 1200;
    final long dockLilycove   = 9395;
    final long leaveLilycove  = dockLilycove + 1200;
    final long dockCanalave   = 16566;
    final long leaveCanalave  = dockCanalave + 1200;
    final long dockSnowpoint  = 18970;
    final long leaveSnowpoint = dockSnowpoint + 1200;
    
    public BoatTaskManager(BoatController boatController) {
        this.controller = boatController;
        Bukkit.getScheduler().scheduleSyncRepeatingTask(boatController.getPlugin(), boatCheck, 20L, 20L);
    }
    
    private Runnable boatCheck = new Runnable(){
        @Override
        public void run() {
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
        }
    };

    protected void move(Location from, Location to) {
        for(Player each : from.getWorld().getPlayers()){
            if(each.getLocation().distance(from) < 4 && !hasTeleported(each)){
                each.teleport(to);
                each.sendMessage("Thank you for using the SS Paradox! Please move away from the doors to clear the way for other users!");
                markTeleported(each);
            }
        }
    }
    protected void messageBoat(String string) {
        // TODO Auto-generated method stub
        
    }
    private void markTeleported(Player player) {
        player.setMetadata("tpd", new FixedMetadataValue(controller.getPlugin(), true));
        Bukkit.getScheduler().scheduleSyncDelayedTask(controller.getPlugin(), new Runnable(){

            @Override
            public void run() {
                player.removeMetadata("tpd", controller.getPlugin());
                
            }}, 100L);
    }

    private boolean hasTeleported(Player player) {
        if(!player.getMetadata("tpd").isEmpty()){
            return player.getMetadata("tpd").get(0).asBoolean();
        }
        return false;
    }
}
