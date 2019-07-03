package com.gmail.ak1cec0ld.plugins.pokemonserver.utility.anti_spawner;

import com.gmail.ak1cec0ld.plugins.pokemonserver.PokemonServer;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class SpawnerRemover implements Listener {

    public SpawnerRemover(){
        PokemonServer.instance().getServer().getPluginManager().registerEvents(this, PokemonServer.instance());
    }


    @EventHandler
    public void onEntitySpawnsNearSpawner(CreatureSpawnEvent event){
        if(!event.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.SPAWNER))return;
        for(int x = - 5; x < + 5; x++){
            for(int y = - 5; y < + 5; y++){
                for(int z = - 5; z < + 5; z++){
                    if(event.getEntity().getLocation().getBlock().getRelative(x,y,z).getType().equals(Material.SPAWNER)){
                        event.getEntity().getLocation().getBlock().getRelative(x,y,z).setType(Material.IRON_BLOCK);
                    }
                }
            }
        }
    }
}
