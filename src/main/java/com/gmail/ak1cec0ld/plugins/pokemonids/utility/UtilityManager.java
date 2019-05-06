package com.gmail.ak1cec0ld.plugins.pokemonids.utility;

import com.gmail.ak1cec0ld.plugins.pokemonids.PokemonIDs;
import com.gmail.ak1cec0ld.plugins.pokemonids.utility.anti_spawner.SpawnerRemover;
import com.gmail.ak1cec0ld.plugins.pokemonids.utility.holotext_command.HoloTextCommand;
import com.gmail.ak1cec0ld.plugins.pokemonids.utility.where_command.WhereCommandListener;
import com.sk89q.worldguard.WorldGuard;

public class UtilityManager {

    public UtilityManager(){
        if(WorldGuard.getInstance() != null) {
            new WhereCommandListener();
        } else {
            PokemonIDs.instance().getLogger().severe("[Where Command] - Disabled due to no Worldguard found!");
        }
        new HoloTextCommand();
        new SpawnerRemover();
    }

}
