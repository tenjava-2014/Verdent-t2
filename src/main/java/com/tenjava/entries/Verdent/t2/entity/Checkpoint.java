/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tenjava.entries.Verdent.t2.entity;

import java.util.ArrayList;
import java.util.UUID;
import org.bukkit.Location;

/**
 *
 * @author Verdent
 */
public class Checkpoint {

    private final int checkpointNumber;
    private final ArrayList<UUID> players = new ArrayList<UUID>();
    private final Location location1;
    private final Location location2;
    private final RacingLap lap;

    public Checkpoint(int checkpointNumber, Location location1, Location location2, RacingLap lap) {
        this.checkpointNumber = checkpointNumber;
        this.location1 = location1;
        this.location2 = location2;
        this.lap = lap;
    }

    public int getCheckpointNumber() {
        return checkpointNumber;
    }

    public RacingLap getLap() {
        return lap;
    }

    public boolean containsPlayer(UUID uuid) {
        return this.players.contains(uuid);
    }

    public void addPlayer(UUID uuid) {
        this.players.add(uuid);
    }

    public boolean isInCheckpoint(Location loc) {
        return ((loc.getX() <= this.location1.getX()) && (loc.getX() >= this.location2.getX()))
                && ((loc.getY() <= this.location1.getY()) && (loc.getY() >= this.location2.getY()))
                && ((loc.getZ() <= this.location1.getZ()) && (loc.getZ() >= this.location2.getZ()))
                && loc.getWorld().equals(location1.getWorld());
    }

}
