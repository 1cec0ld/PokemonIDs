package com.gmail.ak1cec0ld.plugins.pokemonids;


import com.gmail.ak1cec0ld.plugins.pokemonids.AutoHouse.AutoHouseController;
import com.gmail.ak1cec0ld.plugins.pokemonids.Badges.BadgesController;
import com.gmail.ak1cec0ld.plugins.pokemonids.Choice.ChoiceController;
import com.gmail.ak1cec0ld.plugins.pokemonids.HMs.Cut.CutController;
import com.gmail.ak1cec0ld.plugins.pokemonids.HMs.Dive.DiveController;
import com.gmail.ak1cec0ld.plugins.pokemonids.HMs.Fly.FlyController;
import com.gmail.ak1cec0ld.plugins.pokemonids.HMs.RockSmash.RSmashController;
import com.gmail.ak1cec0ld.plugins.pokemonids.HMs.Whirlpool.WhirlpoolController;
import com.gmail.ak1cec0ld.plugins.pokemonids.MapFun.MapFun;
import com.gmail.ak1cec0ld.plugins.pokemonids.QuickHome.QuickHomeController;
import com.gmail.ak1cec0ld.plugins.pokemonids.SSParadox.BoatController;
import com.gmail.ak1cec0ld.plugins.pokemonids.Teleports.TeleportsController;
import com.sk89q.squirrelid.Profile;
import com.sk89q.squirrelid.resolver.HttpRepositoryService;
import com.sk89q.squirrelid.resolver.ProfileService;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.HashSet;
import java.util.UUID;

public class PokemonIDs extends JavaPlugin{
    private static Economy econ = null;
    private PlayerStorageManager strMan;
    
    public void onEnable(){
        strMan = new PlayerStorageManager(this);
        this.getServer().getPluginCommand("pokemonids").setExecutor(new PIDCommand(this));
        
        new CutController(this);
        new RSmashController(this);
        new DiveController(this);
        new WhirlpoolController(this);
        
        new BadgesController(this);
        new TeleportsController(this);
        new BoatController(this);
        
        
        new MapFun(this);
        
        
        if (!setupEconomy()) {
            this.getLogger().severe("[AutoHouse] - Disabled due to no Vault found!");
        } else {
            new AutoHouseController(this);
        }

        if(setWorldGuard() == null){
            this.getLogger().severe("[Fly] - Disabled due to no Worldguard found!");
            this.getLogger().severe("[RegionChoice] - Disabled due to no Worldguard found!");
            this.getLogger().severe("[PokeChoice] - Disabled due to no Worldguard found!");
            this.getLogger().severe("[QuickHome] - Disabled due to no Worldguard found!");
        } else {
            new FlyController(this);
            new ChoiceController(this);
            new QuickHomeController(this);
        }
        
    }
    private WorldGuardPlugin setWorldGuard(){
        Plugin WG = getServer().getPluginManager().getPlugin("WorldGuard");
        
        if (!(WG instanceof WorldGuardPlugin))
        {
            this.getLogger().severe("WorldGuard Not Found!!!!");
            return null;
        }
        this.getLogger().info("WorldGuard Plugin Loaded!");
        return (WorldGuardPlugin)WG;
    }
    
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return true;
    }
    
    public Economy getEconomy(){
        return PokemonIDs.econ;
    }
    
    public PlayerStorageManager getPlayerStorageManager(){
        return this.strMan;
    }
    
    public boolean inRegionChild(Location loc, String regionName){
        RegionContainer getRC = WorldGuard.getInstance().getPlatform().getRegionContainer();
        HashSet<ProtectedRegion> store = new HashSet<>();
        ApplicableRegionSet set = null;
        if (getRC != null) {
            set =  getRC.createQuery().getApplicableRegions(BukkitAdapter.adapt(loc));
        }
        if (set != null) {
            for(ProtectedRegion region : set){
                store.add(region);
                ProtectedRegion regCopy = region;
                while(regCopy.getParent()!=null){
                    store.add(regCopy.getParent());
                    regCopy = regCopy.getParent();
                }
            }
        }
        for(ProtectedRegion region : store){
            if(region.getId().equalsIgnoreCase(regionName)){
                return true;
            }
        }
        return false;
    }

    public Player getPlayerFromString(String string) {
        for(Player p : Bukkit.getOnlinePlayers()){
            if(p.getName().startsWith(string)){
                return p;
            }
        }
        return null;
    }

    public OfflinePlayer getOfflinePlayerFromString(String string) {
        ProfileService resolver = HttpRepositoryService.forMinecraft();
        try {
            Profile profile = resolver.findByName(string);
            if(profile !=null){
                UUID uuid = profile.getUniqueId();
                return Bukkit.getOfflinePlayer(uuid);
            }
        } catch (IOException | InterruptedException e) {
            return null;
        }
        return null;
    }
}
