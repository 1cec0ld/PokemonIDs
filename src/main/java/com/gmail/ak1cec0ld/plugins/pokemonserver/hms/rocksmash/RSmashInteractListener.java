package com.gmail.ak1cec0ld.plugins.pokemonserver.hms.rocksmash;

import com.gmail.ak1cec0ld.plugins.pokemonserver.PokemonServer;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RSmashInteractListener implements Listener{
    private RSmashController controller;
    private Random r;
    private List<BlockFace> faces;
    
    public RSmashInteractListener(RSmashController ctrl){
        this.controller = ctrl;
        this.r = new Random();
        faces = Arrays.asList(BlockFace.valueOf("WEST"), BlockFace.valueOf("EAST"), BlockFace.valueOf("NORTH"), BlockFace.valueOf("SOUTH"));
    }
    
    
    @EventHandler
    public void onInteractWithBlock(PlayerInteractEvent event){
        if(!(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)))return;
        Block hitblock = event.getClickedBlock();
        assert hitblock != null;
        if(!hitblock.getType().equals(Material.COBBLESTONE_STAIRS))return;
        int id = getIdentifierY(hitblock.getLocation());
        if(id < 0)return;

        if(controller.permissionToBreak(event.getPlayer())){
            PokemonServer.msgActionBar(event.getPlayer(),"You used ROCK SMASH!", ChatColor.DARK_GREEN);
            breakRock(getRockLoc(hitblock.getLocation(),id));
        } else {
            PokemonServer.msgActionBar(event.getPlayer(),"This rock looks breakable.",ChatColor.RED);
        }
    }
    
    private void breakRock(Block block){
        setLayer(block,Material.AIR);
        setLayer(block.getRelative(0,1,0),Material.AIR);
        PokemonServer.instance().getServer().getScheduler().runTaskLater(PokemonServer.instance(), () -> makeRock(block), 100L);
    }

    private Block getRockLoc(Location hitblockLoc, int id_y){
        int identifier = id_y;
        int idshiftx = -1;
        int idshiftz = -1;
        int locsearch = 0;
        while(isSmashRock(hitblockLoc.getWorld().getBlockAt((int)hitblockLoc.getX(), identifier, (int)hitblockLoc.getZ()-1-locsearch))){
            locsearch++;
        }
        if(locsearch%2 == 0){
            idshiftz=1;
        }
        locsearch = 0;
        while(isSmashRock(hitblockLoc.getWorld().getBlockAt((int)hitblockLoc.getX()-1-locsearch, identifier, (int)hitblockLoc.getZ()))){
            locsearch++;
        }
        if(locsearch%2 == 0){
            idshiftx = 1;
        }
        
        int minx = (int)Math.min(hitblockLoc.getX(), idshiftx+hitblockLoc.getX());
        int minz = (int)Math.min(hitblockLoc.getZ(), idshiftz+hitblockLoc.getZ());
        World world = hitblockLoc.getWorld();
        for(int rocksearch = identifier; rocksearch <= identifier+8; rocksearch++){
            if(world.getBlockAt(minx, rocksearch, minz).getType().equals(Material.COBBLESTONE_STAIRS)){
                return world.getBlockAt(minx,rocksearch,minz);
            }
        }
        return null;
    }
    
    private int getIdentifierY(Location hitblock){
        World world = hitblock.getWorld();
        double IB_x = hitblock.getX();
        double IB_z = hitblock.getZ();
        for(double id = hitblock.getY(); id > hitblock.getY()-8; id--){
            if(isSmashRock(world.getBlockAt((int)IB_x, (int)id, (int)IB_z))){
                return (int)id;
            }
        }
        return -1;
    }
    
    private void makeRock(Block block){
        setLayer(block,Material.COBBLESTONE_STAIRS);
        setLayer(block.getRelative(0,1,0),Material.COBBLESTONE_STAIRS);
        randomizeLayer(block);
        randomizeLayer(block.getRelative(0,1,0));
    }
    
    private void setLayer(Block block, Material newmat){
        block.setType(newmat);
        block.getRelative(0,0,1).setType(newmat);
        block.getRelative(1,0,0).setType(newmat);
        block.getRelative(1,0,1).setType(newmat);
    }
    
    private void randomizeLayer(Block block){
        setFacingRandom(block);
        setFacingRandom(block.getRelative(0,0,1));
        setFacingRandom(block.getRelative(1,0,0));
        setFacingRandom(block.getRelative(1,0,1));
    }
    
    private void setFacingRandom(Block target){
        Stairs st = (Stairs)target.getBlockData();
        st.setFacing(faces.get(r.nextInt(4)));
        st.setHalf(Bisected.Half.values()[r.nextInt(2)]);
        target.setBlockData(st);
    }
    
    private boolean isSmashRock(Block block){
        return (block.getType().equals(Material.END_PORTAL_FRAME) && ((Directional)block.getBlockData()).getFacing().equals(BlockFace.EAST));
    }
}
