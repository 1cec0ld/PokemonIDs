package com.gmail.ak1cec0ld.plugins.pokemonids.Choice.Pokemon;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import com.gmail.ak1cec0ld.plugins.pokemonids.Choice.ChoiceController;

public class PokeChoiceInteractListener implements Listener{
    private ChoiceController controller;
    
    public PokeChoiceInteractListener(ChoiceController controller){
        this.controller = controller;
    }
    
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        Block b = event.getClickedBlock();
        if(b == null ){
            return;
        }
        if(event.getHand() == null ){
            return;
        }
        if(!event.getHand().equals(EquipmentSlot.HAND)){
            return;
        }
        int choice = searchForPokeSign(b);
        Player player = event.getPlayer();
        if(choice < 0){
            return;
        }
        if(controller.getStorageManager().getPokemonChoice(player.getUniqueId().toString()) >= 0){
            return;
        }
        if(isVerifying(player,choice)){
            switch(choice){
                case 0:
                    setChoice(player,0);
                    player.getWorld().dropItem(player.getLocation(), new ItemStack(Material.STONE_AXE,1));
                    controller.getTaskManager().constrainOff(player);
                    break;
                case 1:
                    setChoice(player,1);
                    player.getWorld().dropItem(player.getLocation(), new ItemStack(Material.STONE_SWORD,1));
                    controller.getTaskManager().constrainOff(player);
                    break;
                case 2:
                    setChoice(player,2);
                    player.getWorld().dropItem(player.getLocation(), new ItemStack(Material.BOW,1));
                    player.getWorld().dropItem(player.getLocation(), new ItemStack(Material.ARROW,32));
                    controller.getTaskManager().constrainOff(player);
                    break;
            }
        } else {
            setVerifying(player,choice);
            switch(controller.getStorageManager().getRegionChoice(player.getUniqueId().toString())){
                case 0:
                    switch(choice){
                        case 0:
                            player.sendMessage(ChatColor.RED+"Did you want to choose Charmander, and wield an Axe?");
                            break;
                        case 1:
                            player.sendMessage(ChatColor.BLUE+"Did you want to choose Squirtle, and wield a Sword?");
                            break;
                        case 2:
                            player.sendMessage(ChatColor.GREEN+"Did you want to choose Bulbasaur, and wield a Bow?");
                            break;
                    }
                    break;
                case 1:
                    switch(choice){
                        case 0:
                            player.sendMessage(ChatColor.RED+"Did you want to choose Cyndaquil, and wield an Axe?");
                            break;
                        case 1:
                            player.sendMessage(ChatColor.BLUE+"Did you want to choose Totodile, and wield a Sword?");
                            break;
                        case 2:
                            player.sendMessage(ChatColor.GREEN+"Did you want to choose Chikorita, and wield a Bow?");
                            break;
                    }
                    break;
                case 2:
                    switch(choice){
                        case 0:
                            player.sendMessage(ChatColor.RED+"Did you want to choose Torchic, and wield an Axe?");
                            break;
                        case 1:
                            player.sendMessage(ChatColor.BLUE+"Did you want to choose Mudkip, and wield a Sword?");
                            break;
                        case 2:
                            player.sendMessage(ChatColor.GREEN+"Did you want to choose Treecko, and wield a Bow?");
                            break;
                    }
                    break;
                case 3:
                    switch(choice){
                        case 0:
                            player.sendMessage(ChatColor.RED+"Did you want to choose Chimchar, and wield an Axe?");
                            break;
                        case 1:
                            player.sendMessage(ChatColor.BLUE+"Did you want to choose Piplup, and wield a Sword?");
                            break;
                        case 2:
                            player.sendMessage(ChatColor.GREEN+"Did you want to choose Turtwig, and wield a Bow?");
                            break;
                    }
                    break;
                case 4:
                    switch(choice){
                        case 0:
                            player.sendMessage(ChatColor.RED+"Did you want to choose Tepig, and wield an Axe?");
                            break;
                        case 1:
                            player.sendMessage(ChatColor.BLUE+"Did you want to choose Oshawott, and wield a Sword?");
                            break;
                        case 2:
                            player.sendMessage(ChatColor.GREEN+"Did you want to choose Snivy, and wield a Bow?");
                            break;
                    }
                    break;
            }
        }
    }

    private void setChoice(Player player, int i) {
        controller.getPlugin().getPlayerStorageManager().setPokemonChoice(player.getUniqueId().toString(), i);
        switch(controller.getStorageManager().getRegionChoice(player.getUniqueId().toString())){
        case(0):
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user "+player.getName()+" group set Kanto");
            break;
        case(1):
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user "+player.getName()+" group set Johto");
            break;
        case(2):
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user "+player.getName()+" group set Hoenn");
            break;
        case(3):
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user "+player.getName()+" group set Sinnoh");
            break;
        case(4):
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user "+player.getName()+" group set Unova");
            break;
        case(5):
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user "+player.getName()+" group set Kalos");
            break;
        case(6):
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user "+player.getName()+" group set Alola");
            break;
        }
    }

    private void setVerifying(Player player, int choice) {
        player.setMetadata("verifying", new FixedMetadataValue(controller.getPlugin(), choice));
    }

    private boolean isVerifying(Player player, int choice) {
        if(!player.getMetadata("verifying").isEmpty()){
            return player.getMetadata("verifying").get(0).asInt()==choice;
        }
        return false;
    }

    private int searchForPokeSign(Block b) {
        for(int y = b.getY(); y > b.getY()-10; y--){
            Block b2 = b.getWorld().getBlockAt(b.getX(), y, b.getZ());
            BlockState bs = b.getWorld().getBlockAt(b.getX(), y, b.getZ()).getState();
            //Bukkit.getLogger().info(b2.getType().toString()+"at x="+b.getX()+", y="+y+", z="+b.getZ());
            if(b2.getType().equals(Material.SIGN) || b2.getType().equals(Material.WALL_SIGN)){// || b2.getType().equals(Material.sign)){
                //Bukkit.getLogger().info("Found a sign");
                Sign blocksign = (Sign)bs;
                String interact_block_line_1 = blocksign.getLine(0);
                if(interact_block_line_1.equalsIgnoreCase("�2[Sword]")){
                    return 1;
                } else if( interact_block_line_1.equalsIgnoreCase("�2[Axe]")){
                    return 0;
                } else if(interact_block_line_1.equalsIgnoreCase("�2[Bow]")){
                    return 2;
                }
            }
        }
        return -1;
    }
}
