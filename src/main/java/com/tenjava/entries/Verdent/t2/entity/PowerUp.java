/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tenjava.entries.Verdent.t2.entity;

import org.bukkit.entity.Entity;

/**
 *
 * @author Verdent
 */
public class PowerUp {
    
    private final int INTERVAL;
    
    private Entity entity;

    public PowerUp(int INTERVAL, Entity entity) {
        this.INTERVAL = INTERVAL;
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }
    
    
}
