package com.gmail.ak1cec0ld.plugins.pokemonids.buildmode;

import com.gmail.ak1cec0ld.plugins.pokemonids.PokemonIDs;
import com.gmail.ak1cec0ld.plugins.pokemonids.buildmode.listeners.*;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;

public class BuildMode {

    public BuildMode() {
        new BuildCommand();
        new BuildLiteCommand();
        new DropItem();
        new InventoryClick();
        new Quit();
        new Teleport();
        new GameModeChange();
        new ProjectileThrow();
        new PotionEffectChange();
    }

    static void turnBuildOn(Player player, String type){
        if(player.hasMetadata("verify_buildmode")){
            player.removeMetadata("verify_buildmode", PokemonIDs.instance());
            executeBuildOn(player,type);
        } else {
            player.sendMessage("Do you want to enter Build Mode? Your entire inventory will be erased.");
            player.setMetadata("verify_buildmode", new FixedMetadataValue(PokemonIDs.instance(),true));
        }
    }
    static void turnBuildOff(Player player) {
        if (player.hasMetadata("verify_buildoff")) {
            player.removeMetadata("verify_buildoff", PokemonIDs.instance());
            executeBuildOff(player);
        } else {
            player.sendMessage("Do you want to finish Build Mode? Your entire inventory will be erased.");
            player.setMetadata("verify_buildoff", new FixedMetadataValue(PokemonIDs.instance(), true));
        }
    }

    private static void executeBuildOn(Player player, String type){
        player.setGameMode(GameMode.CREATIVE);
        player.setMetadata("buildmode", new FixedMetadataValue(PokemonIDs.instance(),true));
        PokemonIDs.instance().getServer().dispatchCommand(PokemonIDs.instance().getServer().getConsoleSender(),
                "pex user " + player.getName() + " group add "+type);
        player.getInventory().clear();
    }
    public static void executeBuildOff(Player player){
        player.removeMetadata("buildmode", PokemonIDs.instance());
        player.setGameMode(GameMode.SURVIVAL);
        removePotionEffects(player);
        PokemonIDs.instance().getServer().dispatchCommand(PokemonIDs.instance().getServer().getConsoleSender(),
                "pex user " + player.getName() + " group remove manager");
        PokemonIDs.instance().getServer().dispatchCommand(PokemonIDs.instance().getServer().getConsoleSender(),
                "pex user " + player.getName() + " group remove builder");
        player.getInventory().clear();
    }

    public static boolean isNotBuilding(Player player){
        if(!player.hasMetadata("buildmode"))return true;
        if(player.getMetadata("buildmode")==null)return true;
        return (!player.getMetadata("buildmode").get(0).asBoolean());
    }
    private static void removePotionEffects(Player player){
        for(PotionEffect each : player.getActivePotionEffects()){
            player.removePotionEffect(each.getType());
        }
    }

}
