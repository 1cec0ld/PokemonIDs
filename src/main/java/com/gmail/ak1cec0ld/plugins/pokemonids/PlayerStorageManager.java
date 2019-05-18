package com.gmail.ak1cec0ld.plugins.pokemonids;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;


public class PlayerStorageManager {
    private CustomYMLStorage yml;
    private YamlConfiguration storage;
    
    PlayerStorageManager(){
        yml = new CustomYMLStorage(PokemonIDs.instance(),"PokemonIDs"+File.separator+"PlayerStorage.yml");
        storage = yml.getYamlConfiguration();
        yml.save();
    }
    
    public int getTotalPlayers(){
        return storage.getKeys(false).size();
    }
    
    public boolean containsPlayer(String uuid){
        return storage.contains(uuid);
    }
    
    void removePlayer(String uuid){
        storage.set(uuid, null);
        yml.save();
    }
    
    public void setPokemonChoice(String uuid, int choice){ //0 1 or 2 for axe, sword or bow
        storage.set(uuid+".firstchoice.pokemon", choice);
        yml.save();
    }
    
    public int getPokemonChoice(String uuid){
        return storage.getInt(uuid+".firstchoice.pokemon", -1);
    }
    
    public void setRegionChoice(String uuid, int choice){ //0-6 for kanto-alola
        storage.set(uuid+".firstchoice.region", choice);
        yml.save();
    }
    
    public int getRegionChoice(String uuid){
        return storage.getInt(uuid+".firstchoice.region", -1);
    }

    public void setHouse(String housename, String uuid){
        storage.set(uuid+".house", housename);
        yml.save();
    }
    
    public void removeHouse(String uuid){
        storage.set(uuid+".house", null);
        yml.save();
    }
    
    public String getHouse(String uuid){
        return storage.getString(uuid+".house","");
    }
    
    public void addBadge(String uuid, String region, int value){
      //assume that the check for existing badge already happened before calling this
        int newvalue = (int) (storage.getInt(uuid+"."+region+"badges",0)+Math.pow(10, value-1));
        storage.set(uuid+"."+region+"badges", newvalue);
        yml.save();
    }
    
    public void deleteBadge(String uuid, String region, int value){
        //assume that the check for existing badge already happened before calling this
        int newvalue = (int) (storage.getInt(uuid+"."+region+"badges",0)-Math.pow(10, value-1));
        storage.set(uuid+"."+region+"badges", newvalue);
        yml.save();
    }
    
    public boolean hasBadge(String uuid, String region, int value){
        int regionbadges = storage.getInt(uuid+"."+region+"badges",0);
        int binary = (int) Math.pow(10, value-1);
        return (regionbadges % ( binary * 10 ) ) / binary > 0;
    }
}
