package com.gmail.ak1cec0ld.plugins.pokemonserver.teleports;

import com.gmail.ak1cec0ld.plugins.pokemonserver.PokemonServer;
import io.github.jorelali.commandapi.api.CommandAPI;
import io.github.jorelali.commandapi.api.CommandPermission;
import io.github.jorelali.commandapi.api.arguments.Argument;
import io.github.jorelali.commandapi.api.arguments.IntegerArgument;
import io.github.jorelali.commandapi.api.arguments.LiteralArgument;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.LinkedHashMap;

public class LobbyCommandManager {

    private String COMMAND_ALIAS_LOBBY = "lobby";
    private String[] COMMAND_ALIASES = {"lobbies"};
    private static final String KEY_W = "lastLocationW";
    private static final String KEY_X = "lastLocationX";
    private static final String KEY_Y = "lastLocationY";
    private static final String KEY_Z = "lastLocationZ";

    private LinkedHashMap<String, Argument> arguments;

    public LobbyCommandManager(){
        arguments = new LinkedHashMap<>();
        registerLobbyCommand();
        registerLobbyCommandWithNumber();
        arguments = new LinkedHashMap<>();
        registerReturnCommand();
    }
    private void registerLobbyCommand(){
        CommandAPI.getInstance().register(COMMAND_ALIAS_LOBBY, CommandPermission.NONE, COMMAND_ALIASES,arguments, (sender, args)->{
            sender.sendMessage("Use /lobby {1-9} to get sent to a central exchange hub!");
        });
    }
    private void registerLobbyCommandWithNumber(){
        arguments.put("floor", new IntegerArgument(1,9));
        CommandAPI.getInstance().register(COMMAND_ALIAS_LOBBY, CommandPermission.fromString("essentials.suicide"), COMMAND_ALIASES, arguments, (sender, args)->{
            if(!(sender instanceof Player))return;
            Player player = (Player)sender;
            if(player.hasMetadata(KEY_W) ||
               player.hasMetadata(KEY_X) ||
               player.hasMetadata(KEY_Y) ||
               player.hasMetadata(KEY_Z)){
               player.sendMessage("&b/go back&e to your old location before you go to another lobby!");
                return;
            }
            player.setMetadata(KEY_W, new FixedMetadataValue(PokemonServer.instance(),player.getWorld().getName()));
            player.setMetadata(KEY_X, new FixedMetadataValue(PokemonServer.instance(),player.getLocation().getX()));
            player.setMetadata(KEY_Y, new FixedMetadataValue(PokemonServer.instance(),player.getLocation().getY()));
            player.setMetadata(KEY_Z, new FixedMetadataValue(PokemonServer.instance(),player.getLocation().getZ()));
            player.teleport(new Location(PokemonServer.instance().getServer().getWorld("Japan"),-34.5,73.4+3*((int)args[0]-1),213.5));
        });
    }
    private void registerReturnCommand(){
        arguments.put("where", new LiteralArgument("back"));
        CommandAPI.getInstance().register("go",CommandPermission.NONE, arguments, (sender,args)->{
            if(!(sender instanceof Player))return;
            Player player = (Player)sender;
            if(!player.hasMetadata(KEY_W))return;
            if(!player.hasMetadata(KEY_X))return;
            if(!player.hasMetadata(KEY_Y))return;
            if(!player.hasMetadata(KEY_Z))return;
            String back_w = player.getMetadata(KEY_W).get(0).asString();
            double back_x = player.getMetadata(KEY_X).get(0).asDouble();
            double back_y = player.getMetadata(KEY_Y).get(0).asDouble();
            double back_z = player.getMetadata(KEY_Z).get(0).asDouble();
            Location loc = new Location(Bukkit.getWorld(back_w),back_x,back_y,back_z);
            PokemonServer.debug(loc.toString());
            player.removeMetadata(KEY_W, PokemonServer.instance());
            player.removeMetadata(KEY_X, PokemonServer.instance());
            player.removeMetadata(KEY_Y, PokemonServer.instance());
            player.removeMetadata(KEY_Z, PokemonServer.instance());
            player.teleport(loc);
        });
    }
}
