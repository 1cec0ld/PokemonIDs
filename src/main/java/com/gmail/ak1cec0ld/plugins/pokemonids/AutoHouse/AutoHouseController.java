package com.gmail.ak1cec0ld.plugins.pokemonids.AutoHouse;

import com.gmail.ak1cec0ld.plugins.pokemonids.PokemonIDs;

import java.util.ArrayList;

public class AutoHouseController {

    private PokemonIDs plugin;
    private AutoHouseTaskManager taskManager;
    private AutoHouseStorageManager storageManager;
    
    public AutoHouseController(PokemonIDs pl){
        this.plugin = pl;
        storageManager = new AutoHouseStorageManager(this);
        taskManager = new AutoHouseTaskManager(this);
        new AutoHouseConfigManager(this);
        plugin.getServer().getPluginManager().registerEvents(new AutoHouseInteractListener(this), pl);
    }
    public PokemonIDs getPlugin(){
        return this.plugin;
    }
    
    AutoHouseTaskManager getTaskManager(){
        return this.taskManager;
    }
    public AutoHouseStorageManager getStorageManager(){
        return this.storageManager;
    }
    
    int getPrice(int tiles){
        ArrayList<Integer> configVals = AutoHouseConfigManager.getParabolaCoords();
        int x1 = configVals.get(0);
        int y1 = configVals.get(1);
        int x2 = configVals.get(2);
        int y2 = configVals.get(3);
        int x3 = configVals.get(4);
        int y3 = configVals.get(5);
        //https://www.desmos.com/calculator/lac2i0bgum
        int A1 = (x2*x2)-(x1*x1);
        double B1 = x2-x1;
        int D1 = y2-y1;
        int A2 = (x3*x3)-(x2*x2);
        double B2 = x3-x2;
        int D2 = y3-y2;
        double BMultiplier = -1*(B2/B1);
        double A3 = BMultiplier*A1 +A2;
        double D3 = BMultiplier*D1+D2;
        double a = D3/A3;
        double b = (D1-A1*a)/B1;
        double c = y1-(a*x1*x1)-(b*x1);
        int result = (int)(a*tiles*tiles + b*tiles + c);
        double discount = AutoHouseConfigManager.getBonusMultiplier()*AutoHouseConfigManager.getBonus();
        double multiplier = 1-discount;

        /*
        plugin.getLogger().info("original: " + result);
        plugin.getLogger().info("Discount: " + discount);
        plugin.getLogger().info("Multiplier: " + multiplier);
        plugin.getLogger().info("return: " + (int)(result*multiplier));
        */

        return (int) (result * multiplier);
    }
    /*
    public static void assignPlayerToHouse(String houseName, String playerName){
        return;
    }
    */
}
