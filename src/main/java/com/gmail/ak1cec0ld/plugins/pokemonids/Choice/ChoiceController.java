package com.gmail.ak1cec0ld.plugins.pokemonids.Choice;

import com.gmail.ak1cec0ld.plugins.pokemonids.PlayerStorageManager;
import com.gmail.ak1cec0ld.plugins.pokemonids.PokemonIDs;
import com.gmail.ak1cec0ld.plugins.pokemonids.Choice.Pokemon.PokeChoiceInteractListener;
import com.gmail.ak1cec0ld.plugins.pokemonids.Choice.Pokemon.PokeChoiceJoinListener;
import com.gmail.ak1cec0ld.plugins.pokemonids.Choice.Region.RegionChoiceInteractListener;
import com.gmail.ak1cec0ld.plugins.pokemonids.Choice.Region.RegionChoiceJoinListener;

public class ChoiceController {
    private PokemonIDs plugin;
    private ChoiceTaskManager taskMan;
    
    public ChoiceController(PokemonIDs plugin){
        this.plugin = plugin;

        plugin.getServer().getPluginManager().registerEvents(new RegionChoiceInteractListener(this), plugin);
        plugin.getServer().getPluginManager().registerEvents(new RegionChoiceJoinListener(this), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PokeChoiceInteractListener(this), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PokeChoiceJoinListener(this), plugin);
        taskMan = new ChoiceTaskManager(this);
    }
    
    public PokemonIDs getPlugin(){
        return this.plugin;
    }
    
    public ChoiceTaskManager getTaskManager(){
        return this.taskMan;
    }
    
    public PlayerStorageManager getStorageManager(){
        return plugin.getPlayerStorageManager();
    }
}
