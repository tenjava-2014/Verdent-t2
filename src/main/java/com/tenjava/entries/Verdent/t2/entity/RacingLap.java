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

    private final int lapNumber;
    private final HashMap<Integer, Checkpoint> checkpoints = new HashMap<Integer, Checkpoint>();
    private final Arena arena;

    public RacingLap(int lapNumber, Arena arena) {
        this.lapNumber = lapNumber;
        this.arena = arena;
    }

    public int getLapNumber() {
        return lapNumber;
    }

    public int getNumberOfCheckpoints() {
        return this.checkpoints.size();
    }

    public Arena getArena() {
        return arena;
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

    public Checkpoint getNextCheckpoint(Checkpoint checkpoint) {
        int checpointNumber = checkpoint.getCheckpointNumber();
        return getNextCheckpoint(checpointNumber);
    }

    public Checkpoint getNextCheckpoint(int checkpointNumber) {
        return checkpoints.get(checkpointNumber + 1);
    }

    public boolean hasNextCheckpoint(Checkpoint checkpoint) {
        int checpointNumber = checkpoint.getCheckpointNumber();
        return hasNextCheckpoint(checpointNumber);
    }

    public boolean hasNextCheckpoint(int checkpointNumber) {
        return checkpoints.containsKey(checkpointNumber + 1);
    }

}
