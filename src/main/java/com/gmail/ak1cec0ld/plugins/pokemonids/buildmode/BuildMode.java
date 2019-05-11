package com.gmail.ak1cec0ld.plugins.pokemonids.buildmode;

import com.gmail.ak1cec0ld.plugins.pokemonids.PokemonIDs;
import com.gmail.ak1cec0ld.plugins.pokemonids.teleports.SpawnCommandManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public class BuildMode {

    public BuildMode() {
        new BuildCommand();
        new TeleportListener();
    }

    static void turnBuildOn(Player player){
        if(player.hasMetadata("verify_buildmode")){
            player.removeMetadata("verify_buildmode", PokemonIDs.instance());
            player.getInventory().clear();
            player.setGameMode(GameMode.CREATIVE);
            player.teleport(new Location(Bukkit.getWorld("Japan"),-2000,68,-3232));
        } else {
            player.sendMessage("Do you want to enter Build Mode? Your entire inventory will be erased.");
            player.setMetadata("verify_buildmode", new FixedMetadataValue(PokemonIDs.instance(),true));
        }
    }
    static void turnBuildOff(Player player) {
        if (player.hasMetadata("verify_buildoff")) {
            player.removeMetadata("verify_buildoff", PokemonIDs.instance());
            player.getInventory().clear();
            SpawnCommandManager.spawn(player);
            player.setGameMode(GameMode.SURVIVAL);
        } else {
            player.sendMessage("Do you want to finish Build Mode? Your entire inventory will be erased.");
            player.setMetadata("verify_buildoff", new FixedMetadataValue(PokemonIDs.instance(), true));
        }
    }
    static boolean inKalos(Location loc){
        return -3500 <= loc.getX() &&
                loc.getX() <= -400 &&
                -4300 <= loc.getZ() &&
                loc.getZ() <= 1700;
    }
}
