package com.gmail.ak1cec0ld.plugins.pokemonserver.hms.fly;

import com.gmail.ak1cec0ld.plugins.pokemonserver.PlayerStorageManager;
import com.gmail.ak1cec0ld.plugins.pokemonserver.PokemonServer;
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
    private PokemonServer plugin;
    private FlyStorageManager flyStrMan;
    
    public FlyController(PokemonServer plugin){
        this.plugin = plugin;
        
        plugin.getServer().getPluginCommand("fly").setExecutor(new FlyCommandManager(this));
        flyStrMan = new FlyStorageManager(plugin);
    }

    public FlyStorageManager getStorageManager(){
        return this.flyStrMan;
    }
    
    public PlayerStorageManager getPlayerStorageManager(){
        return PokemonServer.getPlayerStorageManager();
    }
    
    public PokemonServer getPlugin(){
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
            Bukkit.getLogger().info(player.getName() + " tried to fly to "+flypoint+" but advancement was null");
        }
        return false;
    }

    private Advancement getAdvancement(String flypoint){
        String[] regions = new String[] {"kanto","johto","hoenn", "sinnoh", "unova", "kalos", "alola", "galar"};
        NamespacedKey nsk;
        try {
            for (String each : regions) {
                nsk = new NamespacedKey(getPlugin(), "places/" + each.toLowerCase() + "/" + flypoint);
                if (Bukkit.getServer().getAdvancement(nsk) != null) {
                    return Bukkit.getServer().getAdvancement(nsk);
                }
            }
        } catch(IllegalArgumentException e) {
            return null;
        }
        return null;
    }
}
