package com.gmail.ak1cec0ld.plugins.pokemonids.toggleeffects;

import io.github.jorelali.commandapi.api.CommandAPI;
import io.github.jorelali.commandapi.api.CommandPermission;
import io.github.jorelali.commandapi.api.arguments.Argument;
import io.github.jorelali.commandapi.api.arguments.LiteralArgument;

import java.util.LinkedHashMap;

public class TECommand {



    private static String COMMAND_ALIAS = "toggleeffect";
    private static String[] COMMAND_ALIASES = {"te"};
    private static String[] AVAILABLE_EFFECTS = {"flames"};

    private LinkedHashMap<String, Argument> arguments;

    TECommand(){
        registerCommand();
    }

    private void registerCommand(){
        for(String each : AVAILABLE_EFFECTS){
            arguments = new LinkedHashMap<>();
            arguments.put("effect", new LiteralArgument(each));
            CommandAPI.getInstance().register(COMMAND_ALIAS, CommandPermission.fromString("toggleeffects.toggleself"),COMMAND_ALIASES,arguments,(sender,args) -> {

            });
        }
    }
}
