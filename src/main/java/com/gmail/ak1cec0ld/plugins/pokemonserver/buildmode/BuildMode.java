package com.gmail.ak1cec0ld.plugins.pokemonserver.buildmode;

import com.gmail.ak1cec0ld.plugins.pokemonserver.PokemonServer;
import com.gmail.ak1cec0ld.plugins.pokemonserver.buildmode.listeners.*;
import org.bukkit.GameMode;
import org.bukkit.Location;
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
        new Attack();
        new BlockEdit();
        new InteractEntity();
    }

    static void turnBuildOn(Player player, String type){
        if(player.hasMetadata("verify_buildmode")){
            player.removeMetadata("verify_buildmode", PokemonServer.instance());
            executeBuildOn(player,type);
        } else {
            player.sendMessage("Do you want to enter Build Mode? Your entire inventory will be erased.");
            player.setMetadata("verify_buildmode", new FixedMetadataValue(PokemonServer.instance(),true));
        }
    }
    static void turnBuildOff(Player player) {
        if (player.hasMetadata("verify_buildoff")) {
            player.removeMetadata("verify_buildoff", PokemonServer.instance());
            executeBuildOff(player);
        } else {
            player.sendMessage("Do you want to finish Build Mode? Your entire inventory will be erased.");
            player.setMetadata("verify_buildoff", new FixedMetadataValue(PokemonServer.instance(), true));
        }
    }

    private static void executeBuildOn(Player player, String type){
        player.setGameMode(GameMode.CREATIVE);
        player.setMetadata("buildmode", new FixedMetadataValue(PokemonServer.instance(),true));
        if(type.equalsIgnoreCase("builder")){
            player.setMetadata("buildgym", new FixedMetadataValue(PokemonServer.instance(),true));
        }
        PokemonServer.instance().getServer().dispatchCommand(PokemonServer.instance().getServer().getConsoleSender(),
                "pex user " + player.getName() + " group add "+type);
        player.getInventory().clear();
    }
    public static void executeBuildOff(Player player){
        player.removeMetadata("buildmode", PokemonServer.instance());
        player.removeMetadata("buildgym", PokemonServer.instance());
        player.setGameMode(GameMode.SURVIVAL);
        removePotionEffects(player);
        PokemonServer.instance().getServer().dispatchCommand(PokemonServer.instance().getServer().getConsoleSender(),
                "pex user " + player.getName() + " group remove manager");
        PokemonServer.instance().getServer().dispatchCommand(PokemonServer.instance().getServer().getConsoleSender(),
                "pex user " + player.getName() + " group remove builder");
        player.getInventory().clear();
    }

    public static boolean isNotBuilding(Player player){
        if(!player.hasMetadata("buildmode"))return true;
        return (!player.getMetadata("buildmode").get(0).asBoolean());
    }
    public static boolean isBuildGym(Player player){
        if(!player.hasMetadata("buildgym"))return false;
        return (player.getMetadata("buildgym").get(0).asBoolean());
    }
    private static void removePotionEffects(Player player){
        for(PotionEffect each : player.getActivePotionEffects()){
            player.removePotionEffect(each.getType());
        }
    }
    public static boolean inBuildZone(Location location){
        int[] kanto_gyms = {583,5,350,717,57,608};
        if(kanto_gyms[0] < location.getX() && location.getX() < kanto_gyms[3] &&
                kanto_gyms[1] < location.getY() && location.getY() < kanto_gyms[4] &&
                kanto_gyms[2] < location.getZ() && location.getZ() < kanto_gyms[5]){
            return true;
        }
        return false;
    }
}
