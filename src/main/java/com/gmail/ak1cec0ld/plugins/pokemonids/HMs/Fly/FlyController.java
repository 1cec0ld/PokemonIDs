package com.gmail.ak1cec0ld.plugins.pokemonids.HMs.Fly;

import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.entity.Player;

import com.gmail.ak1cec0ld.plugins.pokemonids.PlayerStorageManager;
import com.gmail.ak1cec0ld.plugins.pokemonids.PokemonIDs;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class FlyController {
    private PokemonIDs plugin;
    private FlyStorageManager flyStrMan;
    //private Scoreboard scr;
    
    public FlyController(PokemonIDs plugin){
        this.plugin = plugin;
        //scr = Bukkit.getScoreboardManager().getMainScoreboard();
        
        plugin.getServer().getPluginCommand("fly").setExecutor(new FlyCommandManager(this));
        flyStrMan = new FlyStorageManager(plugin);
    }

    public FlyStorageManager getStorageManager(){
        return this.flyStrMan;
    }
    
    public PlayerStorageManager getPlayerStorageManager(){
        return plugin.getPlayerStorageManager();
    }
    
    public PokemonIDs getPlugin(){
        return this.plugin;
    }
    
    public String getParentRegion(Location loc){
        RegionManager getRM = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(loc.getWorld()));
        ApplicableRegionSet playerRegions = getRM.getApplicableRegions(BukkitAdapter.asBlockVector(loc));
        HashSet<ProtectedRegion> rTree = getRegionTree(playerRegions); 
        for(ProtectedRegion r : rTree){
            if(getStorageManager().getRegions().contains(r.getId())){
                return r.getId().toLowerCase();
            }
        }
        return "";
    }
    
    private HashSet<ProtectedRegion> getRegionTree(ApplicableRegionSet playerRegions) {
        HashSet<ProtectedRegion> store = new HashSet<ProtectedRegion>();
        for(ProtectedRegion reg : playerRegions){
            store.add(reg);
            ProtectedRegion regCopy = reg;
            while(regCopy.getParent()!=null){
                store.add(regCopy.getParent());
                regCopy = regCopy.getParent();
            }
        }
        return store;
    }
    
    public boolean playerHasFlyPoint(Player player, String flypoint){
        /*if(scr != null){
            for(String ent : scr.getEntries()){
                Bukkit.getLogger().info(ent);
            }
            if(scr.getObjective("fly_"+flypoint)!= null){
                if(scr.getObjective("fly_"+flypoint).getScore(player.getUniqueId().toString())!= null){
                    //Bukkit.getLogger().info("fly_" + flypoint + " " + scr.getObjective("fly_"+flypoint).getScore(player.getName()).getScore());
                    return scr.getObjective("fly_"+flypoint).getScore(player.getName()).getScore() > 0;
                } else {
                    Bukkit.getLogger().severe("getScore is null");
                }
            } else {
                Bukkit.getLogger().severe("getObjective fly_"+flypoint+ " is null");
            }*/
        Advancement a = getAdvancement(flypoint);
        if(a != null){
            AdvancementProgress avp = player.getAdvancementProgress(a);
            if(avp.isDone()){
                return true;
            }
        } else {
            Bukkit.getLogger().info("Someone tried to Fly to "+flypoint+" but advancement was null");
        }
        return false;
        /*Advancement ach = null;
        Advancement adv = null;
        for(Iterator<Advancement> iter = Bukkit.getServer().advancementIterator(); iter.hasNext();){
            adv = iter.next();
            Bukkit.getLogger().info(adv.getKey().getNamespace());
            if(adv.getKey().getKey().equalsIgnoreCase(flypoint)){
                ach = adv;
                break;
            }
        }
        if(ach == null){
            return false;
        }
        AdvancementProgress p = player.getAdvancementProgress(ach);

        return p.isDone();*/
    }
    
    public boolean playerHasAllPoints(Player player, String regionname){
        for(String cityname : getStorageManager().getPoints(regionname)){
            if(!playerHasFlyPoint(player, cityname)){
                return false;
            }
        }
        return true;
    }

    private Advancement getAdvancement(String flypoint){
        String[] regions = new String[] {"kanto","johto","hoenn", "sinnoh", "unova", "kalos", "alola"};
        NamespacedKey nsk;
        for(String each : regions){
            nsk = new NamespacedKey(getPlugin(), "places/"+each+"/"+flypoint);
            if(Bukkit.getServer().getAdvancement(nsk) != null){
                return Bukkit.getServer().getAdvancement(nsk);
            }
        }
        return null;
    }
}
