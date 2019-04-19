package com.gmail.ak1cec0ld.plugins.pokemonids;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PIDCommand implements CommandExecutor {
    PokemonIDs plugin;

    public PIDCommand(PokemonIDs pokemonIDs) {
        this.plugin = pokemonIDs;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            if(args.length == 2){
                if(args[0].equalsIgnoreCase("purge")){
                    for(Player p : Bukkit.getOnlinePlayers()){
                        if(p.getName().startsWith(args[1])){
                            plugin.getPlayerStorageManager().removePlayer(p.getUniqueId().toString());
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement revoke "+p.getName()+" everything");
                            p.kickPlayer("You have been purged from pokemon Progress.");
                        }
                    }
                }
            }
        }
        return false;
    }

}
