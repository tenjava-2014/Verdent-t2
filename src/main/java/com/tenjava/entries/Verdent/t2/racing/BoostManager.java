/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tenjava.entries.Verdent.t2.racing;

import com.tenjava.entries.Verdent.t2.boosts.IBoost;
import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author User
 */
public class BoostManager {

    private final static BoostManager SINGLETON = new BoostManager();
    private final HashMap<String, IBoost> boosts = new HashMap<String, IBoost>();

    private BoostManager() {
    }

    public static BoostManager getInstance() {
        return SINGLETON;
    }

    public void registerBoost(IBoost boost) {
        String name = boost.getName();
        this.boosts.remove(name);
        this.boosts.put(name, boost);
    }

    public IBoost getBoost(String name) {
        return this.boosts.get(name);
    }

    public IBoost getRandomBoost() {
        if (this.boosts.isEmpty()) {
            throw new IllegalStateException("There is no boost registered!");
        }
        Random random = new Random();
        int index = random.nextInt(this.boosts.size());
        /*System.out.println(this.boosts.size());
         System.out.println(index);
         index = index < 0 ? 0 : index;
         System.out.println(index);*/
        String boostName = (String) this.boosts.keySet().toArray()[index];
        return getBoost(boostName);
    }

}
