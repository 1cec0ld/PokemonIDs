package com.gmail.ak1cec0ld.plugins.pokemonserver.secretbases;

import com.gmail.ak1cec0ld.plugins.pokemonserver.PokemonServer;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.Map;

class SBCommand implements TabExecutor {

    private static final String COMMAND_ALIAS = "sb";

    SBCommand(){
        PokemonServer.instance().getServer().getPluginCommand(COMMAND_ALIAS).setExecutor(this);
    }

    private void help(CommandSender sender){
        sender.sendMessage("/sb show|create|remove|changeowner|whitelist|reload");
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(!(commandSender instanceof Player))return false;
        Player player = (Player)commandSender;
        switch(args.length){
            case 1:
                switch(args[0]){
                    case "reload":
                        SBStorage.reload();
                        player.sendMessage("Reloaded");
                        break;
                    case "show":
                        String name = player.getName();
                        for(Map.Entry<String,Location> each : SBStorage.getAllBases().entrySet()){
                            if(each.getKey().equals(name) || player.hasPermission("secretbase.showall")){
                                if(each.getValue().getChunk().isLoaded()) {
                                    player.sendMessage(each.getKey() + ": " + each.getValue().getBlockX() + ", " + each.getValue().getBlockY() + ", " + each.getValue().getBlockZ());
                                } else {
                                    player.sendMessage(each.getKey() + ": " + each.getValue().getBlockX() + ", " + each.getValue().getBlockY() + ", " + each.getValue().getBlockZ() + " - Unloaded Chunk!");
                                }
                            }
                        }
                        break;
                    case "remove":
                        if(!player.hasPermission("secretbase.remove"))return false;
                        Location location = player.getTargetBlock(null,20).getLocation();
                        if(!SBStorage.hasBase(location))return false;
                        SBStorage.removeBase(location);
                        PokemonServer.msgActionBar(player,"Removed Secret Base", ChatColor.RESET);
                        break;
                    default:
                        help(player);
                }
                break;
            case 2:
                switch(args[0]){
                    case "changeowner":
                        if(!player.hasPermission("secretbase.changeowner"))return false;
                        Location location = player.getTargetBlock(null,20).getLocation();
                        if(!SBStorage.hasBase(location))return false;
                        SBStorage.changeOwner(location,args[1]);
                        PokemonServer.msgActionBar(player,"Changed owner name", ChatColor.RESET);
                        break;
                    default:
                        help(player);
                }
                break;
            case 3:
                switch(args[0]){
                    case "whitelist":
                        Location location;
                        switch(args[1]){
                            case "add":
                                location = player.getTargetBlock(null,20).getLocation();
                                if(!SBStorage.hasBase(location))return false;
                                if(!player.isOp() && !SBStorage.isOwner(location,player.getName()))return false;
                                SBStorage.addWhitelistPlayer(location,args[2]);
                                player.sendMessage("Added to whitelist!");
                                player.sendMessage("New Whitelist:" + SBStorage.getWhitelist(location));
                                break;
                            case "remove":
                                location = player.getTargetBlock(null,20).getLocation();
                                if(!SBStorage.hasBase(location))return false;
                                if(!player.isOp() && !SBStorage.isOwner(location,player.getName()))return false;
                                SBStorage.removeWhitelistPlayer(location,args[2]);
                                player.sendMessage("Removed from whitelist!");
                                player.sendMessage("New Whitelist:" + SBStorage.getWhitelist(location));
                                break;
                            default:
                                help(player);
                        }
                        break;
                    default:
                        help(player);
                }
            case 5:
                switch(args[0]){
                    case "create":
                        if(!player.hasPermission("secretbase.create"))return false;
                        Location location = player.getTargetBlock(null, 20).getLocation();
                        Location destination;
                        try{
                            destination = player.getWorld().getBlockAt(Integer.parseInt(args[2]),
                                    Integer.parseInt(args[3]),
                                    Integer.parseInt(args[4])).getLocation();
                        } catch(NumberFormatException e){
                            return false;
                        }
                        if(SBStorage.createBase(location, destination, args[1])){
                            PokemonServer.msgActionBar(player, "Created Secret Base", ChatColor.RESET);
                            spawnGlowBlock(location);
                        } else {
                            PokemonServer.msgActionBar(player, "A Base already exists here!", ChatColor.RESET);
                        }
                        break;
                    default:
                        help(player);
                }
                break;
            default:
                help(player);
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        return null;
        //todo some other day
    }

    private void spawnGlowBlock(Location where){
        LivingEntity glow = (LivingEntity) where.getWorld().spawnEntity(where, EntityType.SHULKER);
        glow.setAI(false);
        glow.setInvulnerable(true);
        glow.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 99999, 1, false, false));
        glow.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 99999, 1, false, false));
        PokemonServer.instance().getServer().getScheduler().runTaskLater(PokemonServer.instance(), glow::remove, 30L);
    }
}