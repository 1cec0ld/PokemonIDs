package com.gmail.ak1cec0ld.plugins.pokemonids.toggleeffects;

import org.bukkit.Location;
import org.bukkit.Particle;

public interface IEffect {

    Particle getParticleType();
    void playEffect(Location loc);

}
