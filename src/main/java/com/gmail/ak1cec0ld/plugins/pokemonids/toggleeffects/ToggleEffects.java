package com.gmail.ak1cec0ld.plugins.pokemonids.toggleeffects;

import com.gmail.ak1cec0ld.plugins.pokemonids.PokemonIDs;

public class ToggleEffects {


    public ToggleEffects(){
        new TECommand();
        beginTick();
    }
    private static void beginTick(){
        PokemonIDs.instance().getServer().getScheduler().scheduleSyncRepeatingTask(PokemonIDs.instance(),() -> {

        },1L, 10L);
    }
}
