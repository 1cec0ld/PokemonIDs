package com.gmail.ak1cec0ld.plugins.pokemonids.secretbases;

import com.gmail.ak1cec0ld.plugins.pokemonids.PokemonIDs;
import io.github.jorelali.commandapi.api.CommandAPI;
import io.github.jorelali.commandapi.api.CommandPermission;
import io.github.jorelali.commandapi.api.arguments.*;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;

class SBCommand {

    private static String COMMAND_ALIAS = "secretbase";
    private static String[] COMMAND_ALIASES = {"sb"};

    private LinkedHashMap<String, Argument> arguments;

    SBCommand(){
        registerCreateCommand();
        registerRemoveCommand();
        registerChangeOwnerCommand();
    }

    private void registerCreateCommand() {
        arguments = new LinkedHashMap<>();
        arguments.put("action", new LiteralArgument("create"));
        arguments.put("owner", new PlayerArgument());
        arguments.put("destination", new LocationArgument());
        CommandAPI.getInstance().register(COMMAND_ALIAS, CommandPermission.fromString("secretbase.create"), COMMAND_ALIASES, arguments, (sender, args) -> {
            if(!(sender instanceof Player))return;
            Player player = (Player) sender;
            Location location = player.getTargetBlock(null, 20).getLocation();
            SBStorage.createBase(location, (Location) args[1], ((Player)args[0]).getName());
            PokemonIDs.msgActionBar(player, "Created Secret Base", ChatColor.RESET);
        });
    }
    private void registerRemoveCommand(){
        arguments = new LinkedHashMap<>();
        arguments.put("action",new LiteralArgument("remove"));
        CommandAPI.getInstance().register(COMMAND_ALIAS, CommandPermission.fromString("secretbase.remove"),COMMAND_ALIASES,arguments, (sender,args) -> {
            if(!(sender instanceof Player))return;
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
        CommandAPI.getInstance().register(COMMAND_ALIAS,CommandPermission.fromString("secretbase.changeowner"),COMMAND_ALIASES,arguments, (sender,args) -> {
            if(!(sender instanceof Player))return;
            Player player = (Player)sender;
            Location location = player.getTargetBlock(null,20).getLocation();
            if(!SBStorage.hasBase(location))return;
            SBStorage.changeOwner(location,((Player)args[0]).getName());
            PokemonIDs.msgActionBar(player,"Changed owner name", ChatColor.RESET);
        });
    }
}