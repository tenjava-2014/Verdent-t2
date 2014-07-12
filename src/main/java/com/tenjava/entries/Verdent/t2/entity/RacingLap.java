/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tenjava.entries.Verdent.t2.entity;

import java.util.HashMap;

/**
 *
 * @author Verdent
 */
public class RacingLap {

    private final HashMap<Integer, Checkpoint> checkpoints = new HashMap<Integer, Checkpoint>();
    private final Arena arena;

    public RacingLap(Arena arena) {
        this.arena = arena;
    }

    public void registerCheckpoint(Checkpoint checkpoint) {
        registerCheckpoint(this.checkpoints.size(), checkpoint);
    }

    public void registerCheckpoint(int number, Checkpoint checkpoint) {
        if (this.checkpoints.containsKey(number)) {
            throw new IllegalArgumentException("Checkpoint with number " + number + " is already registered!");
        }
        this.checkpoints.put(number, checkpoint);
    }

    public boolean hasCheckpoints() {
        return this.checkpoints.isEmpty();
    }

    public int getNumberOfCheckpoints() {
        return this.checkpoints.size();
    }

    public Arena getArena() {
        return arena;
    }

}
