package com.gmail.ak1cec0ld.plugins.pokemonserver.quickhome;

import com.gmail.ak1cec0ld.plugins.pokemonserver.PokemonServer;

public class QuickHomeController {
    
    public QuickHomeController(){
        PokemonServer.instance().getCommand("houses").setExecutor(new QuickHomeCommand());
    }
}
