package com.gmail.ak1cec0ld.plugins.pokemonserver.utility.where_command;

import com.gmail.ak1cec0ld.plugins.pokemonserver.PokemonServer;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.List;

public class WhereCommandListener implements TabExecutor {

    private static String COMMAND_ALIAS = "where";

    public WhereCommandListener(){
        PokemonServer.instance().getServer().getPluginCommand(COMMAND_ALIAS).setExecutor(this);
    }

    private String listRegionsAt(Location loc){
        StringBuilder output = new StringBuilder();
        RegionContainer getRC = WorldGuard.getInstance().getPlatform().getRegionContainer();
        ApplicableRegionSet playerRegions = getRC.createQuery().getApplicableRegions(BukkitAdapter.adapt(loc));
        for(ProtectedRegion region : playerRegions){
            if(region.contains(loc.getBlockX(),loc.getBlockY(),loc.getBlockZ())){
                output.append(",").append(region.getId());
            }
        }
        return (output.length() < 1) ? "" : output.substring(1);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        switch(args.length){
            case 0:
                if(!(commandSender instanceof Player))return false;
                Player player = (Player)commandSender;
                player.sendMessage("You are in: ["+listRegionsAt(player.getLocation())+"] in "+player.getWorld().getName());
                break;
            case 1:
                Player target = PokemonServer.getPlayerFromString(args[0]);
                if(target == null)return false;
                commandSender.sendMessage(target.getName() + " is at: ["+listRegionsAt(target.getLocation())+"] in "+target.getWorld().getName());
                break;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        return null;
        //todo some other day
    }
}
