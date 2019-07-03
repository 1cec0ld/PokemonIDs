package com.gmail.ak1cec0ld.plugins.pokemonserver.buildmode;

import io.github.jorelali.commandapi.api.CommandAPI;
import io.github.jorelali.commandapi.api.CommandPermission;
import io.github.jorelali.commandapi.api.arguments.Argument;
import io.github.jorelali.commandapi.api.arguments.LiteralArgument;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

class BuildCommand {

    private static String COMMAND_ALIAS = "buildmode";

    private LinkedHashMap<String, Argument> arguments;

    BuildCommand(){
        registerCommand();
        Set<String> arg1s = new HashSet<>(Arrays.asList("on", "off"));
        for(String each : arg1s){
            arguments = new LinkedHashMap<>();
            arguments.put("state", new LiteralArgument(each));
            registerCommand(each);
        }
    }

    private void registerCommand() {
        CommandAPI.getInstance().register(COMMAND_ALIAS, CommandPermission.fromString("buildmode"),  arguments,(sender,args)->{
            if(!(sender instanceof Player))return;
            Player player = (Player)sender;
            if(!player.getWorld().getName().toLowerCase().equals(Bukkit.getWorld("Japan").getName().toLowerCase()))return;
            if(BuildMode.isNotBuilding(player)){
                BuildMode.turnBuildOn(player, "manager");
            } else {
                BuildMode.turnBuildOff(player);
            }
        });
    }
    private void registerCommand(String param){
        CommandAPI.getInstance().register(COMMAND_ALIAS,CommandPermission.fromString("buildmode"),arguments,(sender,args)->{
            if(!(sender instanceof Player))return;
            Player player = (Player)sender;
            if(!player.getWorld().getName().toLowerCase().equals(Bukkit.getWorld("Japan").getName().toLowerCase()))return;
            switch(param){
                case "on":
                    BuildMode.turnBuildOn(player, "manager");
                    break;
                case "off":
                    BuildMode.turnBuildOff(player);
                    break;
            }
        });
    }
}
