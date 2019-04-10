package com.gmail.ak1cec0ld.plugins.pokemonids.where_command;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import io.github.jorelali.commandapi.api.CommandAPI;
import io.github.jorelali.commandapi.api.CommandPermission;
import io.github.jorelali.commandapi.api.arguments.Argument;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;

public class WhereCommandListener {

    private static String COMMAND_ALIAS = "where";
    private static String[] COMMAND_ALIASES = {"whatregionsamiin"};

    private LinkedHashMap<String, Argument> arguments;

    public WhereCommandListener(){
        registerCommand();
    }

    private void registerCommand(){
        CommandAPI.getInstance().register(COMMAND_ALIAS, CommandPermission.NONE, COMMAND_ALIASES,arguments, (sender, args) -> {
            if(!(sender instanceof Player))return;
            Player player = (Player)sender;
            player.sendMessage("You are in: ["+listRegionsAt(player.getLocation())+"] in "+player.getWorld().getName());
        });
    }

    private String listRegionsAt(Location loc){
        String output = "";
        RegionContainer getRC = WorldGuard.getInstance().getPlatform().getRegionContainer();
        ApplicableRegionSet playerRegions = getRC.createQuery().getApplicableRegions(BukkitAdapter.adapt(loc));
        for(ProtectedRegion region : playerRegions){
            output += (","+region.getId());
        }
        return (output.length() < 1) ? "" : output.substring(1);
    }
}
