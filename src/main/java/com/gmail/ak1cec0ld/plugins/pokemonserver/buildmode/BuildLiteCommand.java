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

class BuildLiteCommand {


    private static String COMMAND_ALIAS = "buildgym";

    private LinkedHashMap<String, Argument> arguments;

    BuildLiteCommand(){
        registerCommand();
        Set<String> arg1s = new HashSet<>(Arrays.asList("on", "off"));
        for(String each : arg1s){
            arguments = new LinkedHashMap<>();
            arguments.put("state", new LiteralArgument(each));
            registerCommand(each);
        }
    }

    private void registerCommand() {
        CommandAPI.getInstance().register(COMMAND_ALIAS, CommandPermission.fromString("buildgym"),  arguments,(sender, args)->{
            if(!(sender instanceof Player))return;
            Player player = (Player)sender;
            if(!player.getWorld().getName().toLowerCase().equals(Bukkit.getWorld("Japan").getName().toLowerCase()))return;
            if(!BuildMode.inBuildZone(player.getLocation()))return;
            if(BuildMode.isNotBuilding(player)){
                BuildMode.turnBuildOn(player, "builder");
            } else {
                BuildMode.turnBuildOff(player);
            }
        });
    }
    private void registerCommand(String param){
        CommandAPI.getInstance().register(COMMAND_ALIAS,CommandPermission.fromString("buildgym"),arguments,(sender,args)->{
            if(!(sender instanceof Player))return;
            Player player = (Player)sender;
            if(!player.getWorld().getName().toLowerCase().equals(Bukkit.getWorld("Japan").getName().toLowerCase()))return;
            if(!BuildMode.inBuildZone(player.getLocation()))return;
            switch(param){
                case "on":
                    BuildMode.turnBuildOn(player,"builder");
                    break;
                case "off":
                    BuildMode.turnBuildOff(player);
                    break;
            }
        });
    }
}
