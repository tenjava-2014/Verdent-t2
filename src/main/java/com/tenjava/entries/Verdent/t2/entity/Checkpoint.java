/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tenjava.entries.Verdent.t2.entity;

import java.util.ArrayList;
import java.util.UUID;

/**
 *
 * @author User
 */
public class Checkpoint {

    private final ArrayList<UUID> PLAYERS = new ArrayList<UUID>();

    public Checkpoint() {
    }

    public boolean containsPlayer(UUID uuid) {
        return PLAYERS.contains(uuid);
    }

    public void addPlayer(UUID uuid) {
        PLAYERS.add(uuid);
    }

}
