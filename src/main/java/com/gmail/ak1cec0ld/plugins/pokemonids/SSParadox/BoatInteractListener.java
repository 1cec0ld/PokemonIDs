package com.gmail.ak1cec0ld.plugins.pokemonids.SSParadox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;

public class BoatInteractListener implements Listener{
    

    static TextComponent U = new TextComponent("�");  //the ss paradox, it changes position and hoverevent
    static TextComponent x = new TextComponent("x");  //the player, it changes position
    static TextComponent w = new TextComponent("_");  
    static TextComponent I = new TextComponent("1");  
    static TextComponent N = new TextComponent("N"); 
    static TextComponent M = new TextComponent("M");  
    static TextComponent m = new TextComponent("^");  
    static TextComponent n = new TextComponent("-");  
    static TextComponent z = new TextComponent("0");  
    static TextComponent L = new TextComponent("L");  
    static TextComponent O = new TextComponent("O");  
    static TextComponent C = new TextComponent("C");  
    static TextComponent S = new TextComponent("S");  
    static TextComponent s = new TextComponent("S");  
    static TextComponent V = new TextComponent("V");  
    TreeMap<Long, Container> map = new TreeMap<Long, Container>();
    List<List<BaseComponent>> Japan;
    
    
    public BoatInteractListener(){
        map.put(0L,     new Container(34,19, "En route: Vermilion"));
        map.put(130L,   new Container(34,19, "En route: Vermilion"));
        map.put(570L,   new Container(31,20, "En route: Vermilion"));
        map.put(1000L,  new Container(29,19, "Vermilion City"));
        map.put(2200L,  new Container(28,20, "En route: Olivine"));
        map.put(2550L,  new Container(27,21, "En route: Olivine"));
        map.put(2900L,  new Container(24,22, "En route: Olivine"));
        map.put(3250L,  new Container(21,22, "En route: Olivine"));
        map.put(3600L,  new Container(20,21, "En route: Olivine"));
        map.put(3950L,  new Container(19,20, "Olivine City"));
        map.put(5150L,  new Container(19,21, "En route: Slateport"));
        map.put(5430L,  new Container(18,22, "En route: Slateport"));
        map.put(5710L,  new Container(17,21, "En route: Slateport"));
        map.put(5990L,  new Container(15,21, "En route: Slateport"));
        map.put(6270L,  new Container(13,21, "En route: Slateport"));
        map.put(6550L,  new Container(11,20, "En route: Slateport"));
        map.put(6830L,  new Container(9 ,20, "En route: Slateport"));
        map.put(7110L,  new Container(6 ,21, "En route: Slateport"));
        map.put(7390l,  new Container(7 ,22, "Slateport City"));
        map.put(8590L,  new Container(8 ,23, "En route: LilyCove"));
        map.put(9395L,  new Container(9 ,24, "Lilycove City"));
        map.put(10595L, new Container(8 ,23, "En route: Canalave"));
        map.put(11135L, new Container(6 ,21, "En route: Canalave"));
        map.put(11675L, new Container(8 ,19, "En route: Canalave"));
        map.put(12215L, new Container(11,17, "En route: Canalave"));
        map.put(12755L, new Container(14,15, "En route: Canalave"));
        map.put(13295L, new Container(17,13, "En route: Canalave"));
        map.put(13835L, new Container(20,11, "En route: Canalave"));
        map.put(14375L, new Container(23,9 , "En route: Canalave"));
        map.put(14915L, new Container(26,7 , "En route: Canalave"));
        map.put(15455L, new Container(29,5 , "En route: Canalave"));
        map.put(15995L, new Container(32,3 , "En route: Canalave"));
        map.put(16566L, new Container(34,3 , "Canalave City"));
        map.put(17766L, new Container(35,1 , "En route: Snowpoint"));
        map.put(18970L, new Container(37,1 , "Snowpoint City"));
        map.put(20170L, new Container(39,2 , "En route: Vermilion"));
        map.put(20610L, new Container(42,3 , "En route: Vermilion"));
        map.put(21050L, new Container(44,4 , "En route: Vermilion"));
        map.put(21490L, new Container(42,6 , "En route: Vermilion"));
        map.put(21930L, new Container(41,8 , "En route: Vermilion"));
        map.put(22370L, new Container(39,12, "En route: Vermilion"));
        map.put(22810L, new Container(38,14, "En route: Vermilion"));
        map.put(23250l, new Container(37,16, "En route: Vermilion"));
        map.put(23690L, new Container(36,18, "En route: Vermilion"));
        
        initializeMarkers();
    }
    
    private void initializeMarkers(){
        w.setColor(ChatColor.DARK_BLUE);
        I.setColor(ChatColor.DARK_GREEN); 
        N.setColor(ChatColor.DARK_GREEN);
        M.setColor(ChatColor.DARK_GREEN);
        m.setColor(ChatColor.DARK_GREEN);
        n.setColor(ChatColor.DARK_GREEN);
        z.setColor(ChatColor.DARK_GREEN);
        x.setHoverEvent( new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("You are here!").create()));
        L.setColor(ChatColor.LIGHT_PURPLE);
        L.setHoverEvent( new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Lilycove City").create()));
        O.setColor(ChatColor.LIGHT_PURPLE);
        O.setHoverEvent( new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Olivine City").create()));
        C.setColor(ChatColor.LIGHT_PURPLE);
        C.setHoverEvent( new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Canalave City").create()));
        S.setColor(ChatColor.LIGHT_PURPLE);
        S.setHoverEvent( new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Slateport City").create()));
        s.setColor(ChatColor.LIGHT_PURPLE);
        s.setHoverEvent( new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Snowpoint City").create()));
        V.setColor(ChatColor.LIGHT_PURPLE);
        V.setHoverEvent( new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Vermilion City").create()));
    }
    
    private void initializeJapan() {
        ArrayList<BaseComponent> row0 = new ArrayList<>(Arrays.asList(w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,N,s,w,w,w,w,w,w,m,w,w,w,w,w,w,w,w,w));
        ArrayList<BaseComponent> row1 = new ArrayList<>(Arrays.asList(w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,M,M,M,N,w,w,w,w,M,M,M,w,w,w,w,w,w,w,w));
        ArrayList<BaseComponent> row2 = new ArrayList<>(Arrays.asList(w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,C,M,M,M,M,M,N,N,w,w,w,w,w,w,w,w,w,w,w,w));
        ArrayList<BaseComponent> row3 = new ArrayList<>(Arrays.asList(w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,N,N,M,M,M,M,M,M,M,N,N,w,w,w,w,w,w,w,w,w,w));
        ArrayList<BaseComponent> row4 = new ArrayList<>(Arrays.asList(w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,I,w,w,w,w,N,M,N,w,w,w,w,w,w,w,w,w,w,w,w,w,w));
        ArrayList<BaseComponent> row5 = new ArrayList<>(Arrays.asList(w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,N,N,N,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w));
        ArrayList<BaseComponent> row6 = new ArrayList<>(Arrays.asList(w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,N,N,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w));
        ArrayList<BaseComponent> row7 = new ArrayList<>(Arrays.asList(w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,N,N,N,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w));
        ArrayList<BaseComponent> row8 = new ArrayList<>(Arrays.asList(w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,N,M,M,M,N,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w));
        ArrayList<BaseComponent> row9 = new ArrayList<>(Arrays.asList(w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,N,M,M,M,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w));
        ArrayList<BaseComponent> rowa = new ArrayList<>(Arrays.asList(w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,N,M,M,M,N,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w));
        ArrayList<BaseComponent> rowb = new ArrayList<>(Arrays.asList(w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,N,M,M,M,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w));
        ArrayList<BaseComponent> rowc = new ArrayList<>(Arrays.asList(w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,N,M,M,M,M,M,N,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w));
        ArrayList<BaseComponent> rowd = new ArrayList<>(Arrays.asList(w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,N,N,M,M,M,M,N,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w));
        ArrayList<BaseComponent> rowe = new ArrayList<>(Arrays.asList(w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,N,w,N,M,M,M,M,M,M,N,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w));
        ArrayList<BaseComponent> rowf = new ArrayList<>(Arrays.asList(w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,N,M,M,M,M,M,M,M,M,N,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w));
        ArrayList<BaseComponent> rowg = new ArrayList<>(Arrays.asList(w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,N,M,M,M,M,M,M,M,M,M,N,N,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w));
        ArrayList<BaseComponent> rowh = new ArrayList<>(Arrays.asList(w,w,w,w,w,w,w,w,w,w,w,w,w,w,N,N,w,w,w,N,M,M,M,M,M,M,M,M,N,N,V,N,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w));
        ArrayList<BaseComponent> rowi = new ArrayList<>(Arrays.asList(w,w,w,w,w,w,w,w,w,w,w,w,N,M,M,M,M,M,N,N,M,M,M,M,N,M,N,N,N,N,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w));
        ArrayList<BaseComponent> rowj = new ArrayList<>(Arrays.asList(w,w,w,w,w,w,w,w,w,w,w,M,M,M,N,M,M,N,O,N,M,M,M,N,N,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w));
        ArrayList<BaseComponent> rowk = new ArrayList<>(Arrays.asList(w,w,w,w,w,w,N,N,M,M,w,w,w,w,w,w,w,M,w,w,M,M,N,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w));
        ArrayList<BaseComponent> rowl = new ArrayList<>(Arrays.asList(w,w,w,w,w,w,S,N,N,M,M,w,w,N,z,N,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w));
        ArrayList<BaseComponent> rowm = new ArrayList<>(Arrays.asList(w,w,w,w,w,w,w,w,N,M,M,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w));
        ArrayList<BaseComponent> rown = new ArrayList<>(Arrays.asList(w,w,w,w,w,w,I,w,L,N,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w));
        ArrayList<BaseComponent> rowo = new ArrayList<>(Arrays.asList(w,w,w,w,w,w,I,n,N,N,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w));
        Japan = new ArrayList<>(Arrays.asList(row0,row1,row2,row3,row4,row5,row6,row7,row8,row9,rowa,rowb,rowc,rowd,rowe,rowf,rowg,rowh,rowi,rowj,rowk,rowl,rowm,rown,rowo));
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        if(!(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR)))return;
        if(!event.getHand().equals(EquipmentSlot.HAND))return;
        if(event.getItem()==null)return;
        ItemStack item = event.getItem();
        if(!item.hasItemMeta())return;
        if(!item.getItemMeta().hasLore())return;
        if(!item.getItemMeta().getLore().get(0).equalsIgnoreCase("�7Permits Entry to the SS Paradox"))
        if(!item.getType().equals(Material.PAPER))return;
        
        processEvent(event);
    }
    
    public void processEvent(PlayerInteractEvent event){
        Player player = event.getPlayer();
        long currentTime = Bukkit.getWorld("Japan").getTime();
        Container c = mappedValue(map,currentTime);
        initializeJapan();
        placeBoatOnJapan(c);  //the boat overwrites the player if it is where they are
        sendEditedMap(player);
    }

    private void placeBoatOnJapan(Container container){
        U.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(container.hoverText).create()));
        Japan.get(container.row-1).set(container.col-1, U);
    }
    
    private void sendEditedMap(Player player){
        for(int eachRow = 0; eachRow < Japan.size(); eachRow++){
            player.spigot().sendMessage(BaseComponentFromArrayList(Japan.get(eachRow)));
        }
    }
    
    private BaseComponent BaseComponentFromArrayList(List<BaseComponent> list){
        TextComponent store = new TextComponent();
        for(BaseComponent eachItem : list){
            store.addExtra(eachItem);
        }
        return store;
    }
    
    private static <K, V> Container mappedValue(TreeMap<Long, Container> map2, long currentTime){
        Entry<Long, Container> e = map2.floorEntry(currentTime);
        if(e!=null && e.getValue()==null){
            e = map2.lowerEntry(currentTime);
        }
        return e == null ? null : e.getValue();
    }
    
    private class Container{
        public int col, row;
        public String hoverText;
        
        public Container(int column, int row, String hoverText){
            this.col = column;
            this.row = row;
            this.hoverText = hoverText;
        }
    }
}
