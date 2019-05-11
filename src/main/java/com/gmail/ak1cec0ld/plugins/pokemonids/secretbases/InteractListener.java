package com.gmail.ak1cec0ld.plugins.pokemonids.secretbases;

import com.gmail.ak1cec0ld.plugins.pokemonids.PokemonIDs;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class InteractListener implements Listener {

    InteractListener(){
        PokemonIDs.instance().getServer().getPluginManager().registerEvents(this,PokemonIDs.instance());
    }

    private static final String KEY_W = "lastLocationW";
    private static final String KEY_X = "lastLocationX";
    private static final String KEY_Y = "lastLocationY";
    private static final String KEY_Z = "lastLocationZ";

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        if(!(event.getAction().equals(Action.LEFT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)))return;
        Location target = SBStorage.getBaseTarget(event.getClickedBlock().getLocation());
        if(target==null)return;
        Location base = event.getClickedBlock().getLocation();
        Player clicker = event.getPlayer();
        if(!event.getPlayer().isSneaking() && !SBStorage.isLocked(base)){
            event.getPlayer().teleport(target);
            setMetadata(event.getPlayer());
        } else if(SBStorage.isOwner(base, clicker.getName()) || clicker.isOp()){
            String lockResponse = (SBStorage.toggleLock(base)) ? "You locked the base." : "You unlocked the base.";
            PokemonIDs.msgActionBar(clicker,lockResponse, ChatColor.YELLOW);
        }
    }

    private static void setMetadata(Player p){
        p.setMetadata(KEY_W,new FixedMetadataValue(PokemonIDs.instance(),p.getWorld().toString()));
        p.setMetadata(KEY_X,new FixedMetadataValue(PokemonIDs.instance(),p.getLocation().getX()));
        p.setMetadata(KEY_Y,new FixedMetadataValue(PokemonIDs.instance(),p.getLocation().getY()));
        p.setMetadata(KEY_Z,new FixedMetadataValue(PokemonIDs.instance(),p.getLocation().getZ()));
    }
}
