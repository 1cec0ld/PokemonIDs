package com.gmail.ak1cec0ld.plugins.pokemonserver.secretbases;

import com.gmail.ak1cec0ld.plugins.pokemonserver.PokemonServer;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.metadata.FixedMetadataValue;

public class InteractListener implements Listener {

    InteractListener(){
        PokemonServer.instance().getServer().getPluginManager().registerEvents(this, PokemonServer.instance());
    }

    private static final String KEY_W = "lastLocationW";
    private static final String KEY_X = "lastLocationX";
    private static final String KEY_Y = "lastLocationY";
    private static final String KEY_Z = "lastLocationZ";

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        if(!(event.getAction().equals(Action.LEFT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)))return;
        if(!event.getHand().equals(EquipmentSlot.HAND))return;
        Location target = SBStorage.getBaseTarget(event.getClickedBlock().getLocation());
        if(target==null)return;
        Location base = event.getClickedBlock().getLocation();
        Player clicker = event.getPlayer();
        if(!event.getPlayer().isSneaking()){
            SBStorage.LockState state = SBStorage.getLockstate(base);
            if(state == SBStorage.LockState.LOCKED || state == SBStorage.LockState.TRUE)return;
            if(state == SBStorage.LockState.WHITELIST){
                if(!SBStorage.hasWhitelistPlayer(base, clicker.getName()))return;
            }
            setMetadata(event.getPlayer());
            event.getPlayer().teleport(target);
            PokemonServer.instance().getServer().getScheduler().runTaskLater(PokemonServer.instance(),() -> {
                PokemonServer.msgActionBar(clicker,"Use '/go back' to leave!", ChatColor.YELLOW);
            },4L);

        } else if(SBStorage.isOwner(base, clicker.getName()) || clicker.isOp()){
            PokemonServer.instance().getServer().getScheduler().runTaskLater(PokemonServer.instance(),() -> {
                SBStorage.LockState newState = SBStorage.cycleLock(base);
                String lockResponse =  "You changed the base to " + newState.toString().toLowerCase() + " mode.";
                PokemonServer.msgActionBar(clicker,lockResponse, ChatColor.YELLOW);
            },4L);
        }
    }

    private static void setMetadata(Player p){
        p.setMetadata(KEY_W,new FixedMetadataValue(PokemonServer.instance(),p.getWorld().getName()));
        p.setMetadata(KEY_X,new FixedMetadataValue(PokemonServer.instance(),p.getLocation().getX()));
        p.setMetadata(KEY_Y,new FixedMetadataValue(PokemonServer.instance(),p.getLocation().getY()));
        p.setMetadata(KEY_Z,new FixedMetadataValue(PokemonServer.instance(),p.getLocation().getZ()));
    }
}
