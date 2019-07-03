package com.gmail.ak1cec0ld.plugins.pokemonserver.choice;

import com.gmail.ak1cec0ld.plugins.pokemonserver.PlayerStorageManager;
import com.gmail.ak1cec0ld.plugins.pokemonserver.PokemonServer;
import com.gmail.ak1cec0ld.plugins.pokemonserver.choice.pokemon.PokeChoiceInteractListener;
import com.gmail.ak1cec0ld.plugins.pokemonserver.choice.pokemon.PokeChoiceJoinListener;
import com.gmail.ak1cec0ld.plugins.pokemonserver.choice.region.RegionChoiceInteractListener;
import com.gmail.ak1cec0ld.plugins.pokemonserver.choice.region.RegionChoiceJoinListener;

public class ChoiceController {
    private PokemonServer plugin;
    private ChoiceTaskManager taskMan;
    
    public ChoiceController(PokemonServer plugin){
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
