/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tenjava.entries.Verdent.t2.boosts;

/**
 *
 * @author Verdent
 */
public abstract class Boost implements IBoost {

    private final String name;

    public Boost(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

}
