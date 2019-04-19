package com.gmail.ak1cec0ld.plugins.pokemonids.hms.fly;

import com.gmail.ak1cec0ld.plugins.pokemonids.PlayerStorageManager;
import com.gmail.ak1cec0ld.plugins.pokemonids.PokemonIDs;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.entity.Player;

import java.util.HashSet;

public class FlyController {
    private PokemonIDs plugin;
    private FlyStorageManager flyStrMan;
    
    public FlyController(PokemonIDs plugin){
        this.plugin = plugin;
        
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
    
    String getParentRegion(Location loc){
        RegionContainer getRC = WorldGuard.getInstance().getPlatform().getRegionContainer();
        ApplicableRegionSet playerRegions = getRC.createQuery().getApplicableRegions(BukkitAdapter.adapt(loc));
        HashSet<ProtectedRegion> rTree = getRegionTree(playerRegions); 
        for(ProtectedRegion r : rTree){
            if(getStorageManager().getRegions().contains(r.getId())){
                return r.getId().toLowerCase();
            }
        }
        return "";
    }
    
    private HashSet<ProtectedRegion> getRegionTree(ApplicableRegionSet playerRegions) {
        HashSet<ProtectedRegion> store = new HashSet<>();
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
    
    boolean playerHasFlyPoint(Player player, String flypoint){
        Advancement a = getAdvancement(flypoint);
        if(a != null){
            AdvancementProgress avp = player.getAdvancementProgress(a);
            return avp.isDone();
        } else {
            Bukkit.getLogger().info("Someone tried to fly to "+flypoint+" but advancement was null");
        }
        return false;
    }

    private Advancement getAdvancement(String flypoint){
        String[] regions = new String[] {"kanto","johto","hoenn", "sinnoh", "unova", "kalos", "alola", "galar"};
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
