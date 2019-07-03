package com.gmail.ak1cec0ld.plugins.pokemonserver.utility;

import com.gmail.ak1cec0ld.plugins.pokemonserver.PokemonServer;
import com.gmail.ak1cec0ld.plugins.pokemonserver.utility.anti_spawner.SpawnerRemover;
import com.gmail.ak1cec0ld.plugins.pokemonserver.utility.holotext_command.HoloTextCommand;
import com.gmail.ak1cec0ld.plugins.pokemonserver.utility.where_command.WhereCommandListener;
import com.sk89q.worldguard.WorldGuard;

public class UtilityManager {

    public UtilityManager(){
        if(WorldGuard.getInstance() != null) {
            new WhereCommandListener();
        } else {
            PokemonServer.instance().getLogger().severe("[Where Command] - Disabled due to no Worldguard found!");
        }
        new HoloTextCommand();
        new SpawnerRemover();
    }

}
