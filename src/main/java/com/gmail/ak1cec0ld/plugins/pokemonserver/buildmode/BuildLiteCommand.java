package com.gmail.ak1cec0ld.plugins.pokemonserver.buildmode;

import com.gmail.ak1cec0ld.plugins.pokemonserver.PokemonServer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.List;

class BuildLiteCommand implements TabExecutor {


    private static final String COMMAND_ALIAS = "buildgym";

    BuildLiteCommand(){
        PokemonServer.instance().getServer().getPluginCommand(COMMAND_ALIAS).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player))return false;
        Player player = (Player)commandSender;
        if(!player.getWorld().getName().toLowerCase().equals(Bukkit.getWorld("Japan").getName().toLowerCase()))return false;
        if(!BuildMode.inBuildZone(player.getLocation()))return false;
        if(!player.hasPermission("buildgym"))return false;
        switch(strings.length) {
            case 1:
                switch (strings[0]) {
                    case "on":
                        BuildMode.turnBuildOn(player, "builder");
                        break;
                    case "off":
                        BuildMode.turnBuildOff(player);
                        break;
                }
                break;
            case 0:
                if (BuildMode.isNotBuilding(player)) {
                    BuildMode.turnBuildOn(player, "builder");
                } else {
                    BuildMode.turnBuildOff(player);
                }
                break;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
        //todo some other day
    }
}
