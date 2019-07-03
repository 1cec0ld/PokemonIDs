package com.gmail.ak1cec0ld.plugins.pokemonserver;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PServerCommand implements CommandExecutor {

    public PServerCommand() {
        PokemonServer.instance().getServer().getPluginCommand("pokemonserver").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            switch(args.length){
                case 1:
                    Player target_case_1 = PokemonServer.getPlayerFromString(args[0]);
                    if(target_case_1 == null)return false;
                    sender.sendMessage(PokemonServer.getPlayerStorageManager().getRegionChoice(target_case_1.getUniqueId().toString())+" "+
                            PokemonServer.getPlayerStorageManager().getPokemonChoice(target_case_1.getUniqueId().toString()));
                    break;
                case 2:
                    if(args[0].equalsIgnoreCase("purge")){
                        Player target_case_2 = PokemonServer.getPlayerFromString(args[1]);
                        if(target_case_2 == null)return false;
                        PokemonServer.getPlayerStorageManager().removePlayer(target_case_2.getUniqueId().toString());
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement revoke "+target_case_2.getName()+" everything");
                        target_case_2.kickPlayer("You have been purged from pokemon Progress.");
                    }
                    break;
            }
        }
        return false;
    }

}
