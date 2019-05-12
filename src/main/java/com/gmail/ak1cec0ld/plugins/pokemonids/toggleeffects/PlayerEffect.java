package com.gmail.ak1cec0ld.plugins.pokemonids.toggleeffects;

import com.gmail.ak1cec0ld.plugins.pokemonids.PokemonIDs;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class PlayerEffect {

    private String name;
    private Particle particle;
    private long delay;
    private double[] offset;
    private int taskID;
    private Player owner;

    public PlayerEffect(Player owner, String name, Particle type, long interval, double offsetx, double offsety, double offsetz){
        this.name = name;
        this.owner = owner;
        particle = type;
        delay = interval;
        offset = new double[3];
        offset[0] = offsetx;
        offset[1] = offsety;
        offset[2] = offsetz;
    }
    public void play(){
        taskID = PokemonIDs.instance().getServer().getScheduler().scheduleSyncRepeatingTask(PokemonIDs.instance(),() -> {
            if(owner.hasMetadata("te-"+name)){
                for(Player each : PokemonIDs.instance().getServer().getOnlinePlayers()) {
                    each.spawnParticle(particle, owner.getLocation(), 1, offset[0], offset[1], offset[2]);
                }
            } else {
                PokemonIDs.instance().getServer().getScheduler().cancelTask(taskID);
            }
        },1L, delay);
    }
}
