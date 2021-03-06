package com.gmail.ak1cec0ld.plugins.pokemonserver;


import com.gmail.ak1cec0ld.plugins.pokemonserver.autohouse.AutoHouseController;
import com.gmail.ak1cec0ld.plugins.pokemonserver.badges.BadgesController;
import com.gmail.ak1cec0ld.plugins.pokemonserver.buildmode.BuildMode;
import com.gmail.ak1cec0ld.plugins.pokemonserver.choice.ChoiceController;
import com.gmail.ak1cec0ld.plugins.pokemonserver.hms.cut.CutController;
import com.gmail.ak1cec0ld.plugins.pokemonserver.hms.dive.DiveController;
import com.gmail.ak1cec0ld.plugins.pokemonserver.hms.fly.FlyController;
import com.gmail.ak1cec0ld.plugins.pokemonserver.hms.rocksmash.RSmashController;
import com.gmail.ak1cec0ld.plugins.pokemonserver.hms.whirlpool.WhirlpoolController;
import com.gmail.ak1cec0ld.plugins.pokemonserver.mapfun.MapFun;
import com.gmail.ak1cec0ld.plugins.pokemonserver.quickhome.QuickHomeController;
import com.gmail.ak1cec0ld.plugins.pokemonserver.secretbases.SBManager;
import com.gmail.ak1cec0ld.plugins.pokemonserver.ssparadox.BoatController;
import com.gmail.ak1cec0ld.plugins.pokemonserver.teleports.TeleportsController;
import com.gmail.ak1cec0ld.plugins.pokemonserver.utility.UtilityManager;
import com.sk89q.squirrelid.Profile;
import com.sk89q.squirrelid.resolver.HttpRepositoryService;
import com.sk89q.squirrelid.resolver.ProfileService;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;
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

public class PokemonServer extends JavaPlugin{
    private static Economy econ = null;
    private static PlayerStorageManager strMan;
    private static PokemonServer instance;
    
    public void onEnable(){
        instance = this;
        strMan = new PlayerStorageManager();
        new PServerCommand();
        
        new CutController(this);
        new RSmashController(this);
        new DiveController(this);
        new WhirlpoolController(this);
        
        new BadgesController(this);
        new TeleportsController(this);
        new BoatController();
        new SBManager();
        new BuildMode();

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
            this.getLogger().severe("[Utilities: Where,AntiSpawner,HoloText] - Disabled due to no Worldguard found!");
        } else {
            new FlyController(this);
            new ChoiceController(this);
            new QuickHomeController();
            new UtilityManager();
        }
        
    }
    private static WorldGuardPlugin setWorldGuard(){
        Plugin WG = instance.getServer().getPluginManager().getPlugin("WorldGuard");
        
        if (!(WG instanceof WorldGuardPlugin))
        {
            instance.getLogger().severe("WorldGuard Not Found!!!!");
            return null;
        }
        instance.getLogger().info("WorldGuard Plugin Loaded!");
        return (WorldGuardPlugin)WG;
    }
    
    private static boolean setupEconomy() {
        if (instance.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = instance.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return true;
    }
    
    public static Economy getEconomy(){
        return PokemonServer.econ;
    }
    
    public static PlayerStorageManager getPlayerStorageManager(){
        return PokemonServer.strMan;
    }

    public static PokemonServer instance(){
        return instance;
    }
    public static boolean inRegionChild(Location loc, String regionName){
        RegionContainer getRC = WorldGuard.getInstance().getPlatform().getRegionContainer();
        HashSet<ProtectedRegion> store = new HashSet<>();
        ApplicableRegionSet set = null;
        if (getRC == null) return false;
        set =  getRC.createQuery().getApplicableRegions(BukkitAdapter.adapt(loc));
        if (set == null) return false;
        for(ProtectedRegion region : set){
            store.add(region);
            ProtectedRegion regCopy = region;
            while(regCopy.getParent()!=null){
                store.add(regCopy.getParent());
                regCopy = regCopy.getParent();
            }
        }
        for(ProtectedRegion region : store){
            if(region.getId().equalsIgnoreCase(regionName)){
                return true;
            }
        }
        return false;
    }

    public static Player getPlayerFromString(String string) {
        for(Player p : Bukkit.getOnlinePlayers()){
            if(p.getName().toLowerCase().startsWith(string.toLowerCase())){
                return p;
            }
        }
        return null;
    }

    public static OfflinePlayer getOfflinePlayerFromString(String string) {
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
    public static void msgActionBar(Player player, String message, ChatColor color){
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new ComponentBuilder(message).color(color).create());
    }
    public static void debug(String string){
        Bukkit.getLogger().info("[PokemonServer-debug] "+string);
    }
}
