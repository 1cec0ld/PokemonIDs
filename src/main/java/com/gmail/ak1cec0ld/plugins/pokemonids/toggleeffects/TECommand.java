package com.gmail.ak1cec0ld.plugins.pokemonids.toggleeffects;

import com.gmail.ak1cec0ld.plugins.pokemonids.PokemonIDs;
import io.github.jorelali.commandapi.api.CommandAPI;
import io.github.jorelali.commandapi.api.CommandPermission;
import io.github.jorelali.commandapi.api.arguments.Argument;
import io.github.jorelali.commandapi.api.arguments.LiteralArgument;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.LinkedHashMap;
import java.util.Set;

public class TECommand {



    private static String COMMAND_ALIAS = "toggleeffect";
    private static String[] COMMAND_ALIASES = {"te"};
    private static Set<String> AVAILABLE_EFFECTS;

    private LinkedHashMap<String, Argument> arguments;

    TECommand(){
        AVAILABLE_EFFECTS = EffectFile.getEffects();
        registerCommand();
    }

    private void registerCommand(){
        for(String each : AVAILABLE_EFFECTS){
            arguments = new LinkedHashMap<>();
            arguments.put("effect", new LiteralArgument(each));
            CommandAPI.getInstance().register(COMMAND_ALIAS, CommandPermission.fromString("toggleeffects.toggleself"),COMMAND_ALIASES,arguments,(sender,args) -> {
                if(!(sender instanceof Player))return;
                Player player = (Player)sender;
                if(player.hasMetadata("te-"+each)){
                    player.removeMetadata("te-"+each, PokemonIDs.instance());
                    return;
                }
                player.setMetadata("te-"+each,new FixedMetadataValue(PokemonIDs.instance(), true));
                long interval = EffectFile.timesPerSecondAsTicks(each);
                double[] offset = EffectFile.getOffset(each);
                Particle particle = EffectFile.getParticle(each);
                PlayerEffect effect = new PlayerEffect(player, each,particle,interval,offset[0],offset[1],offset[2]);
                effect.play();
            });
        }
    }
}
