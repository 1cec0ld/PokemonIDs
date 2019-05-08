package com.gmail.ak1cec0ld.plugins.pokemonids.buildmode;

import io.github.jorelali.commandapi.api.CommandAPI;
import io.github.jorelali.commandapi.api.CommandPermission;
import io.github.jorelali.commandapi.api.arguments.Argument;
import io.github.jorelali.commandapi.api.arguments.LiteralArgument;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

public class BuildCommand {

/*
  rules:
    buildmode needs permission to use
    if using it as survival mode it will
      confirm you want to use it
      if confirmed
        put you in creative
        teleport you to a build site
        clear your inventory



 */

    private static String COMMAND_ALIAS = "buildmode";
    private static String[] COMMAND_ALIASES = {"bm"};

    private LinkedHashMap<String, Argument> arguments;

    public BuildCommand(){
        registerCommand();
        Set<String> arg1s = new HashSet<>(Arrays.asList("on", "off"));
        for(String each : arg1s){
            arguments = new LinkedHashMap<>();
            arguments.put("state", new LiteralArgument(each));
            registerCommand(each);
        }
    }

    private void registerCommand() {
        CommandAPI.getInstance().register(COMMAND_ALIAS, CommandPermission.fromString("buildmode"), COMMAND_ALIASES, arguments,(sender,args)->{
            if(!(sender instanceof Player))return;
            Player player = (Player)sender;
            if(player.getGameMode().equals(GameMode.ADVENTURE) || player.getGameMode().equals(GameMode.SURVIVAL)){
                BuildMode.turnBuildOn(player);
            } else {
                BuildMode.turnBuildOff(player);
            }
        });
    }
    private void registerCommand(String param){
        CommandAPI.getInstance().register(COMMAND_ALIAS,CommandPermission.fromString("buildmode"),COMMAND_ALIASES,arguments,(sender,args)->{
            if(!(sender instanceof Player))return;
            Player player = (Player)sender;
            switch(param){
                case "on":
                    BuildMode.turnBuildOn(player);
                    break;
                case "off":
                    BuildMode.turnBuildOff(player);
                    break;
            }
        });
    }
}
