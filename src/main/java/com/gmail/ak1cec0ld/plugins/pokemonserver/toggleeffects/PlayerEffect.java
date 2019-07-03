package com.gmail.ak1cec0ld.plugins.pokemonserver.toggleeffects;

import com.gmail.ak1cec0ld.plugins.pokemonserver.PokemonServer;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class PlayerEffect {

    private String name;
    private Particle particle;
    private long delay;
    private double[] offset;
    private int taskID;
    private Player owner;
    private int amount;

    public PlayerEffect(Player owner, String name, Particle type, long interval, double offsetx, double offsety, double offsetz, int amount){
        this.name = name;
        this.owner = owner;
        particle = type;
        delay = interval;
        offset = new double[3];
        offset[0] = offsetx;
        offset[1] = offsety;
        offset[2] = offsetz;
        this.amount = amount;
    }
    public void play(){
        taskID = PokemonServer.instance().getServer().getScheduler().scheduleSyncRepeatingTask(PokemonServer.instance(),() -> {
            if(owner.hasMetadata("te-"+name) && owner.isOnline()){
                owner.getWorld().spawnParticle(particle, owner.getLocation(), amount, offset[0], offset[1], offset[2]);
            } else {
                PokemonServer.instance().getServer().getScheduler().cancelTask(taskID);
            }
        },1L, delay);
    }
}
