package com.gmail.ak1cec0ld.plugins.pokemonserver;

public class Benchmark {
    private static long startTime = 0L;
    private static long endTime = 0L;
    
    public Benchmark(){}
    
    
    public static void start(){
        startTime = System.nanoTime();
    }
    
    public static void end(){
        endTime = System.nanoTime();
    }
    
    public static long getTotal(){
        return endTime-startTime;
    }
}
