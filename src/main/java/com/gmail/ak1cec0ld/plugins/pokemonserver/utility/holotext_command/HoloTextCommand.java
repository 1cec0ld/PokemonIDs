package com.gmail.ak1cec0ld.plugins.pokemonserver.utility.holotext_command;

import com.gmail.ak1cec0ld.plugins.pokemonserver.PokemonServer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.List;

public class HoloTextCommand implements TabExecutor {

    private static final String COMMAND_ALIAS = "holotext";

    public HoloTextCommand(){
        PokemonServer.instance().getServer().getPluginCommand(COMMAND_ALIAS).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(!(commandSender instanceof Player))return false;
        Player player = (Player)commandSender;
        Entity spawned = player.getWorld().spawnEntity(player.getLocation(), EntityType.ARMOR_STAND);
        String total = String.join(" ", args);
        if(spawned instanceof ArmorStand){
            ArmorStand armorStand = (ArmorStand)spawned;
            armorStand.setVisible(false);
            armorStand.setCustomName(total.replace('&','§'));
            armorStand.setCustomNameVisible(true);
            armorStand.setGravity(false);
            armorStand.setInvulnerable(true);
            player.chat("/data merge entity @e[sort=nearest,type=minecraft:armor_stand,limit=1] {Tags:[\"HoloText\"]}");
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
        //todo some other day
    }
}
