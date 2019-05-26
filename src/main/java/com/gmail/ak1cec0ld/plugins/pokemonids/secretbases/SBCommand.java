package com.gmail.ak1cec0ld.plugins.pokemonids.secretbases;

import com.gmail.ak1cec0ld.plugins.pokemonids.PokemonIDs;
import io.github.jorelali.commandapi.api.CommandAPI;
import io.github.jorelali.commandapi.api.CommandPermission;
import io.github.jorelali.commandapi.api.arguments.*;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.LinkedHashMap;
import java.util.Map;

class SBCommand {

    private static String COMMAND_ALIAS = "secretbase";
    private static String[] COMMAND_ALIASES = {"sb"};

    private LinkedHashMap<String, Argument> arguments;

    SBCommand(){
        registerBaseCommand();
        registerCreateCommand();
        registerRemoveCommand();
        registerChangeOwnerCommand();
        registerShowCommand();
    }

    private void registerBaseCommand(){
        arguments = new LinkedHashMap<>();
        CommandAPI.getInstance().register(COMMAND_ALIAS,CommandPermission.NONE,COMMAND_ALIASES,arguments,(sender, args)->{
            sender.sendMessage("/sb show|create|remove|changeowner");
        });
    }

    private void registerCreateCommand() {
        arguments = new LinkedHashMap<>();
        arguments.put("action", new LiteralArgument("create"));
        arguments.put("owner", new PlayerArgument());
        arguments.put("destination", new LocationArgument());
        CommandAPI.getInstance().register(COMMAND_ALIAS, CommandPermission.NONE, COMMAND_ALIASES, arguments, (sender, args) -> {
            if(!(sender instanceof Player))return;
            if(!sender.hasPermission("secretbase.create"))return;
            Player player = (Player) sender;
            Location location = player.getTargetBlock(null, 20).getLocation();
            if(SBStorage.createBase(location, (Location) args[1], ((Player)args[0]).getName())){
                PokemonIDs.msgActionBar(player, "Created Secret Base", ChatColor.RESET);
                spawnGlowBlock((Location)args[1]);
            } else {
                PokemonIDs.msgActionBar(player, "A Base already exists here!", ChatColor.RESET);
            }

        });
    }
    private void registerRemoveCommand(){
        arguments = new LinkedHashMap<>();
        arguments.put("action",new LiteralArgument("remove"));
        CommandAPI.getInstance().register(COMMAND_ALIAS, CommandPermission.NONE,COMMAND_ALIASES,arguments, (sender,args) -> {
            if(!(sender instanceof Player))return;
            if(!sender.hasPermission("secretbase.remove"))return;
            Player player = (Player)sender;
            Location location = player.getTargetBlock(null,20).getLocation();
            if(!SBStorage.hasBase(location))return;
            SBStorage.removeBase(location);
            PokemonIDs.msgActionBar(player,"Removed Secret Base", ChatColor.RESET);
        });
    }
    private void registerChangeOwnerCommand(){
        arguments = new LinkedHashMap<>();
        arguments.put("action",new LiteralArgument("changeowner"));
        arguments.put("new-owner", new PlayerArgument());
        CommandAPI.getInstance().register(COMMAND_ALIAS,CommandPermission.NONE,COMMAND_ALIASES,arguments, (sender,args) -> {
            if(!(sender instanceof Player))return;
            if(!sender.hasPermission("secretbase.changeowner"))return;
            Player player = (Player)sender;
            Location location = player.getTargetBlock(null,20).getLocation();
            if(!SBStorage.hasBase(location))return;
            SBStorage.changeOwner(location,((Player)args[0]).getName());
            PokemonIDs.msgActionBar(player,"Changed owner name", ChatColor.RESET);
        });
    }
    private void registerShowCommand(){
        arguments = new LinkedHashMap<>();
        arguments.put("action", new LiteralArgument("show"));
        CommandAPI.getInstance().register(COMMAND_ALIAS,CommandPermission.NONE,COMMAND_ALIASES,arguments,(sender,args) -> {
            if(!(sender instanceof Player))return;
            Player player = (Player)sender;
            String name = player.getName();
            for(Map.Entry<String,Location> each : SBStorage.getAllBases().entrySet()){
                if(each.getKey().equals(name) || player.hasPermission("secretbase.showall")){
                    if(each.getValue().getChunk().isLoaded()) {
                        spawnGlowBlock(each.getValue());
                        player.sendMessage(each.getKey() + ": " + each.getValue().getBlockX() + ", " + each.getValue().getBlockY() + ", " + each.getValue().getBlockZ());
                    } else {
                        player.sendMessage(each.getKey() + ": " + each.getValue().getBlockX() + ", " + each.getValue().getBlockY() + ", " + each.getValue().getBlockZ() + " - Unloaded Chunk!");
                    }
                }
            }
        });
    }
    private void spawnGlowBlock(Location where){
        LivingEntity glow = (LivingEntity) where.getWorld().spawnEntity(where, EntityType.SHULKER);
        glow.setAI(false);
        glow.setInvulnerable(true);
        glow.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 99999, 1, false, false));
        glow.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 99999, 1, false, false));
        PokemonIDs.instance().getServer().getScheduler().runTaskLater(PokemonIDs.instance(), glow::remove, 30L);
    }
}