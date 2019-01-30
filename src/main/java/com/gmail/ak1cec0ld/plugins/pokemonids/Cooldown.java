package com.gmail.ak1cec0ld.plugins.pokemonids;

public class Cooldown {
    
    long current = 0L; //50 of these is one of Duration
    long duration = 0L;
    
    public Cooldown(Long delayInTicks){
        if(delayInTicks > 0){
            this.duration = delayInTicks;
        }
    }
    
    public void start(){
        current = System.currentTimeMillis();
    }
    
    public void end(){
        current = duration*50;
    }
    
    public boolean isDone(){
        return System.currentTimeMillis()-current >= duration*50;
    }
}
