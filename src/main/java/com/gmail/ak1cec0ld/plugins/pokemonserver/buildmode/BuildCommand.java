package com.gmail.ak1cec0ld.plugins.pokemonserver.buildmode;

import com.gmail.ak1cec0ld.plugins.pokemonserver.PokemonServer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.List;

class BuildCommand implements TabExecutor {

    private static final String COMMAND_ALIAS = "buildmode";


    BuildCommand(){
        PokemonServer.instance().getServer().getPluginCommand(COMMAND_ALIAS).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player))return false;
        Player player = (Player)commandSender;
        if(!player.getWorld().getName().toLowerCase().equals(Bukkit.getWorld("Japan").getName().toLowerCase()))return false;
        if(!player.hasPermission("buildmode"))return false;
        switch(strings.length) {
            case 1:
                switch (strings[0]) {
                    case "on":
                        BuildMode.turnBuildOn(player, "manager");
                        break;
                    case "off":
                        BuildMode.turnBuildOff(player);
                        break;
                }
                break;
            case 0:
                if (BuildMode.isNotBuilding(player)) {
                    BuildMode.turnBuildOn(player, "manager");
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
