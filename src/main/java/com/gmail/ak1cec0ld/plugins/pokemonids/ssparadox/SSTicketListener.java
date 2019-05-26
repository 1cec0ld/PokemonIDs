package com.gmail.ak1cec0ld.plugins.pokemonids.ssparadox;

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

public class SSTicketListener implements Listener{

    private static TextComponent U = new TextComponent("Ü");  //the ss paradox, it changes position and hoverevent
    private static TextComponent w = new TextComponent("_");
    private static TextComponent I = new TextComponent("I");
    private static TextComponent N = new TextComponent("N");
    private static TextComponent M = new TextComponent("M");
    private static TextComponent m = new TextComponent("^");
    private static TextComponent n = new TextComponent("-");
    private static TextComponent z = new TextComponent("0");
    private static TextComponent L = new TextComponent("L");
    private static TextComponent O = new TextComponent("O");
    private static TextComponent C = new TextComponent("C");
    private static TextComponent S = new TextComponent("S");
    private static TextComponent s = new TextComponent("S");
    private static TextComponent V = new TextComponent("V");
    private static TreeMap<Long, Container> map = new TreeMap<>();
    private static List<List<BaseComponent>> Japan;
    
    
    SSTicketListener(){
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
        map.put(7390L,  new Container(7 ,22, "Slateport City"));
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
        map.put(23250L, new Container(37,16, "En route: Vermilion"));
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
        Japan = new ArrayList<>(Arrays.asList(
            new ArrayList<>(Arrays.asList(w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,N,s,w,w,w,w,w,w,m,w,w,w,w,w,w,w,w,w)),
            new ArrayList<>(Arrays.asList(w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,M,M,M,N,w,w,w,w,M,M,M,w,w,w,w,w,w,w,w)),
            new ArrayList<>(Arrays.asList(w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,C,M,M,M,M,M,N,N,w,w,w,w,w,w,w,w,w,w,w,w)),
            new ArrayList<>(Arrays.asList(w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,N,N,M,M,M,M,M,M,M,N,N,w,w,w,w,w,w,w,w,w,w)),
            new ArrayList<>(Arrays.asList(w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,I,w,w,w,w,N,M,N,w,w,w,w,w,w,w,w,w,w,w,w,w,w)),
            new ArrayList<>(Arrays.asList(w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,N,N,N,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w)),
            new ArrayList<>(Arrays.asList(w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,N,N,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w)),
            new ArrayList<>(Arrays.asList(w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,N,N,N,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w)),
            new ArrayList<>(Arrays.asList(w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,N,M,M,M,N,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w)),
            new ArrayList<>(Arrays.asList(w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,N,M,M,M,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w)),
            new ArrayList<>(Arrays.asList(w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,N,M,M,M,N,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w)),
            new ArrayList<>(Arrays.asList(w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,N,M,M,M,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w)),
            new ArrayList<>(Arrays.asList(w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,N,M,M,M,M,M,N,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w)),
            new ArrayList<>(Arrays.asList(w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,N,N,M,M,M,M,N,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w)),
            new ArrayList<>(Arrays.asList(w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,N,w,N,M,M,M,M,M,M,N,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w)),
            new ArrayList<>(Arrays.asList(w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,N,M,M,M,M,M,M,M,M,N,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w)),
            new ArrayList<>(Arrays.asList(w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,N,M,M,M,M,M,M,M,M,M,N,N,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w)),
            new ArrayList<>(Arrays.asList(w,w,w,w,w,w,w,w,w,w,w,w,w,w,N,N,w,w,w,N,M,M,M,M,M,M,M,M,N,N,V,N,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w)),
            new ArrayList<>(Arrays.asList(w,w,w,w,w,w,w,w,w,w,w,w,N,M,M,M,M,M,N,N,M,M,M,M,N,M,N,N,N,N,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w)),
            new ArrayList<>(Arrays.asList(w,w,w,w,w,w,w,w,w,w,w,M,M,M,N,M,M,N,O,N,M,M,M,N,N,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w)),
            new ArrayList<>(Arrays.asList(w,w,w,w,w,w,N,N,M,M,w,w,w,w,w,w,w,M,w,w,M,M,N,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w)),
            new ArrayList<>(Arrays.asList(w,w,w,w,w,w,S,N,N,M,M,w,w,N,z,N,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w)),
            new ArrayList<>(Arrays.asList(w,w,w,w,w,w,w,w,N,M,M,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w)),
            new ArrayList<>(Arrays.asList(w,w,w,w,w,w,I,w,L,N,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w)),
            new ArrayList<>(Arrays.asList(w,w,w,w,w,w,I,n,N,N,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w))
        ));
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        if(!(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR)))return;
        if(!event.getHand().equals(EquipmentSlot.HAND))return;
        if(event.getItem()==null)return;
        ItemStack item = event.getItem();
        if(!isSSTicket(item))return;
        
        processEvent(event);
    }

    public static boolean isSSTicket(ItemStack item){
        if(!item.hasItemMeta())return false;
        if(!item.getItemMeta().hasLore())return false;
        if(!item.getItemMeta().getLore().get(0).equalsIgnoreCase("§7Permits Entry to the SS Paradox")) return false;
        return item.getType().equals(Material.PAPER);
    }
    
    private void processEvent(PlayerInteractEvent event){
        Player player = event.getPlayer();
        long currentTime = Bukkit.getWorld("Japan").getTime();
        Container c = mappedValue(map,currentTime);
        initializeJapan();
        if (c == null) return;
        placeBoatOnJapan(c);
        sendEditedMap(player);
    }

    private void placeBoatOnJapan(Container container){
        U.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(container.hoverText).create()));
        Japan.get(container.row-1).set(container.col-1, U);
    }
    
    private void sendEditedMap(Player player){
        for (List<BaseComponent> baseComponents : Japan) {
            player.spigot().sendMessage(baseComponentFromArrayList(baseComponents));
        }
    }
    
    private BaseComponent baseComponentFromArrayList(List<BaseComponent> list){
        TextComponent store = new TextComponent();
        for(BaseComponent eachItem : list){
            store.addExtra(eachItem);
        }
        return store;
    }
    
    private static Container mappedValue(TreeMap<Long, Container> map2, long currentTime){
        Entry<Long, Container> e = map2.floorEntry(currentTime);
        if(e != null){
            e = map2.lowerEntry(currentTime);
        }
        return e == null ? null : e.getValue();
    }
    private class Container{
        int col, row;
        String hoverText;
        
        Container(int column, int row, String hoverText){
            this.col = column;
            this.row = row;
            this.hoverText = hoverText;
        }
    }
}
