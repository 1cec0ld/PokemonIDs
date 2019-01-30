package com.gmail.ak1cec0ld.plugins.pokemonids.MapFun;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Rotation;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import com.gmail.ak1cec0ld.plugins.pokemonids.PokemonIDs;

public class MapFun implements Listener{
    PokemonIDs plugin;
    
    private Runnable mapCheck = new Runnable(){
        public void run() {
            if(checkmaps(-1772,56,275) && checkmaps(-1802,54,316) && checkmaps(-1803,54,347) && checkmaps(-1772,55,373)){
                complete();
            }
        }
    };
    
    public MapFun(PokemonIDs plugin){
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        //begin the scheduler
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, mapCheck, 100, 100);
    }
    
    @EventHandler(priority=EventPriority.LOWEST)
    public void onInteractEntity(PlayerInteractEntityEvent event){
        if(inRuins(event.getPlayer().getLocation())){
            Entity e = event.getRightClicked();
            if(e instanceof ItemFrame){
                Bukkit.getLogger().info("Cancelled PIEE in ruins of alph");
                event.setCancelled(true);
                rotate((ItemFrame)e);
            }
        }
    }
    
    @EventHandler()
    public void onEntityDamage(EntityDamageByEntityEvent event){
        if(inRuins(event.getDamager().getLocation())){
            if(event.getEntity() instanceof ItemFrame){
                Bukkit.getLogger().info("Cancelled EDBEE in ruins of alph");
                event.setCancelled(true);
                rotate((ItemFrame)event.getEntity());
            }
        }
    }
    
    @EventHandler()
    public void onInteract(PlayerInteractEvent event){
        if(inRuins(event.getPlayer().getLocation())){
            Bukkit.getLogger().info("Cancelled PIE in ruins of alph");
            event.setCancelled(true);
        }
    }

    private void rotate(ItemFrame frame) {
        if(frame.getRotation().equals(Rotation.NONE)){
            frame.setRotation(Rotation.CLOCKWISE_45);
        } else if(frame.getRotation().equals(Rotation.CLOCKWISE_45)){
            frame.setRotation(Rotation.CLOCKWISE);
        } else if(frame.getRotation().equals(Rotation.CLOCKWISE)){
            frame.setRotation(Rotation.CLOCKWISE_135);
        } else if(frame.getRotation().equals(Rotation.CLOCKWISE_135)){
            frame.setRotation(Rotation.FLIPPED);
        } else if(frame.getRotation().equals(Rotation.FLIPPED)){
            frame.setRotation(Rotation.FLIPPED_45);
        } else if(frame.getRotation().equals(Rotation.FLIPPED_45)){
            frame.setRotation(Rotation.COUNTER_CLOCKWISE);
        } else if(frame.getRotation().equals(Rotation.COUNTER_CLOCKWISE)){
            frame.setRotation(Rotation.COUNTER_CLOCKWISE_45);
        } else {
            frame.setRotation(Rotation.NONE);
        }
    }
    
    private boolean inRuins(Location loc){
        int x = loc.getBlockX();
        int y = loc.getBlockY();
        int z = loc.getBlockZ();
        return x>-1820 && x<-1757 && y>30 && y<70 && z>276 && z<403;
    }

    private void complete(){
        final World w = Bukkit.getWorld("Japan");
        for(Entity e : w.getNearbyEntities(w.getBlockAt(-1790, 65, 337).getLocation(), 30.0, 16.0, 64.0)){
            if(e instanceof Player){
                final Player p = (Player)e;
                p.sendMessage(ChatColor.BLUE+"You hear a click from far away...");
                Bukkit.getScheduler().runTaskLater(plugin, new Runnable(){
                    public void run() {
                        Bukkit.getLogger().info("Teleporting someone to Sinjoh due to Ruins Of Alph Puzzle!");
                        p.teleport(new Location(w,-1869,243,-257), TeleportCause.COMMAND);
                    }}, 50L);
            }
        }
        //reset each of the 4 pictures
        resetmaps();
    }
    
    private boolean checkmaps(int xmin, int ymin, int zmin){
        World w = Bukkit.getWorld("Japan");
        if (w != null){
            for(Entity e : w.getNearbyEntities(w.getBlockAt(xmin, ymin, zmin).getLocation(), 2.5, 2.5, 2.5)){
                if(e instanceof ItemFrame){
                    ItemFrame frame = (ItemFrame)e;
                    if(!(frame.getRotation().equals(Rotation.NONE) || frame.getRotation().equals(Rotation.FLIPPED))){
                        return false;
                    }
                }
            }
        } else {
            Bukkit.getLogger().severe("PokemonIDs.MapFun.checkmaps: Bukkit.getWorld('Japan') is null");
        }
        return true;
    }
    
    private void resetmap(int xmin, int ymin, int zmin){
        World w = Bukkit.getWorld("Japan");
        Random r = new Random();
        for(Entity e : w.getNearbyEntities(w.getBlockAt(xmin, ymin, zmin).getLocation(), 2.5, 2.5, 2.5)){
            if(e instanceof ItemFrame){
                ItemFrame frame = (ItemFrame)e;
                switch(r.nextInt(11)%3){
                case 0:
                    frame.setRotation(Rotation.CLOCKWISE_135);
                    break;
                case 1:
                    frame.setRotation(Rotation.CLOCKWISE_45);
                    break;
                case 2:
                    frame.setRotation(Rotation.CLOCKWISE);
                    break;
                }
            }
        }
    }
    
    private void resetmaps(){
        Bukkit.getScheduler().runTaskLater(plugin, new Runnable(){
            public void run() {
                resetmap(-1772,56,275);
                resetmap(-1802,54,316);
                resetmap(-1803,54,347);
                resetmap(-1772,55,373);
            }
        }, 50L);
    }
}
