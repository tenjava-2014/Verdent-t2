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
public class Arena {

    private final HashMap<Integer, RacingLap> laps = new HashMap<Integer, RacingLap>();

    public Checkpoint getNextCheckpoint(Checkpoint checkpoint) {
        RacingLap lap = checkpoint.getLap();
        if (lap.hasNextCheckpoint(checkpoint)) {
            return lap.getNextCheckpoint(checkpoint);
        }
        lap = getNextLap(lap);
        if (lap != null) {
            return lap.getNextCheckpoint(0);
        }
        return null;
    }

    public RacingLap getNextLap(RacingLap lap) {
        int lapNumber = lap.getLapNumber();
        return getNextLap(lapNumber);
    }

    public RacingLap getNextLap(int lapNumber) {
        return laps.get(lapNumber + 1);
    }

}
