package com.gmail.ak1cec0ld.plugins.pokemonids.choice;

import com.gmail.ak1cec0ld.plugins.pokemonids.PlayerStorageManager;
import com.gmail.ak1cec0ld.plugins.pokemonids.PokemonIDs;
import com.gmail.ak1cec0ld.plugins.pokemonids.choice.pokemon.PokeChoiceInteractListener;
import com.gmail.ak1cec0ld.plugins.pokemonids.choice.pokemon.PokeChoiceJoinListener;
import com.gmail.ak1cec0ld.plugins.pokemonids.choice.region.RegionChoiceInteractListener;
import com.gmail.ak1cec0ld.plugins.pokemonids.choice.region.RegionChoiceJoinListener;

public class ChoiceController {
    private PokemonIDs plugin;
    private ChoiceTaskManager taskMan;
    
    public ChoiceController(PokemonIDs plugin){
        this.plugin = plugin;

        plugin.getServer().getPluginManager().registerEvents(new RegionChoiceInteractListener(this), plugin);
        plugin.getServer().getPluginManager().registerEvents(new RegionChoiceJoinListener(this), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PokeChoiceInteractListener(this), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PokeChoiceJoinListener(this), plugin);
        taskMan = new ChoiceTaskManager();
    }

    public ChoiceTaskManager getTaskManager(){
        return this.taskMan;
    }
    
    public PlayerStorageManager getStorageManager(){
        return plugin.getPlayerStorageManager();
    }
}
