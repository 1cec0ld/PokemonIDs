package com.gmail.ak1cec0ld.plugins.pokemonserver.utility.holotext_command;

import io.github.jorelali.commandapi.api.CommandAPI;
import io.github.jorelali.commandapi.api.CommandPermission;
import io.github.jorelali.commandapi.api.arguments.Argument;
import io.github.jorelali.commandapi.api.arguments.GreedyStringArgument;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;

public class HoloTextCommand {



    private static String COMMAND_ALIAS = "holotext";
    private static String[] COMMAND_ALIASES = {"ht"};

    private LinkedHashMap<String, Argument> arguments;

    public HoloTextCommand(){
        registerCommand();
    }

    private void registerCommand(){
        arguments = new LinkedHashMap<>();
        arguments.put("text", new GreedyStringArgument());
        CommandAPI.getInstance().register(COMMAND_ALIAS, CommandPermission.fromString("holotext"), COMMAND_ALIASES,arguments, (sender, args) -> {
            if(!(sender instanceof Player))return;
            Player player = (Player)sender;
            Entity spawned = player.getWorld().spawnEntity(player.getLocation(), EntityType.ARMOR_STAND);
            if(spawned instanceof ArmorStand){
                ArmorStand armorStand = (ArmorStand)spawned;
                armorStand.setVisible(false);
                armorStand.setCustomName(args[0].toString().replace('&','§'));
                armorStand.setCustomNameVisible(true);
                armorStand.setGravity(false);
                armorStand.setInvulnerable(true);
                player.chat("/data merge entity @e[sort=nearest,type=minecraft:armor_stand,limit=1] {Tags:[\"HoloText\"]}");
            }
        });
    }
}
