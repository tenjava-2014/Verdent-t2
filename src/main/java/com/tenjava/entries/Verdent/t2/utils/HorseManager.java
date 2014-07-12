/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tenjava.entries.Verdent.t2.utils;

import net.minecraft.server.v1_7_R3.AttributeInstance;
import net.minecraft.server.v1_7_R3.EntityInsentient;
import net.minecraft.server.v1_7_R3.GenericAttributes;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_7_R3.entity.CraftLivingEntity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;

/**
 *
 * @author Verdent
 */
public class HorseManager extends EntitySpawnManager {

    private static final double NORMAL_SPEED = 0.23888842211270872;

    public Horse spawnHorse(Location location, boolean randomize) {
        return spawnHorse(location, "", randomize);
    }

    public Horse spawnHorse(Location location, String name, boolean randomize) {
        boolean tamed = randomize ? random.nextBoolean() : false;
        return spawnHorse(location, name, tamed, randomize);
    }

    public Horse spawnHorse(Location location, String name, boolean tamed, boolean randomize) {
        return spawnHorse(location, name, tamed, null, randomize);
    }

    public Horse spawnHorse(Location location, String name, boolean tamed, Player player, boolean randomize) {
        Horse.Style style = randomize ? Horse.Style.values()[random.nextInt(Horse.Style.values().length - 1)] : Horse.Style.NONE;
        return spawnHorse(location, name, tamed, player, style, randomize);
    }

    public Horse spawnHorse(Location location, String name, boolean tamed, Player player,
            Horse.Style style, boolean randomize) {
        Horse.Color color = randomize ? Horse.Color.values()[random.nextInt(Horse.Color.values().length - 1)] : Horse.Color.BLACK;
        return spawnHorse(location, name, tamed, player, style, color, randomize);
    }

    public Horse spawnHorse(Location location, String name, boolean tamed, Player player,
            Horse.Style style, Horse.Color color, boolean randomize) {
        Horse.Variant variant = randomize ? Horse.Variant.values()[random.nextInt(Horse.Variant.values().length - 1)] : Horse.Variant.HORSE;
        return spawnHorse(location, name, tamed, player, style, color, variant, randomize);
    }

    public Horse spawnHorse(Location location, String name, boolean tamed, Player player,
            Horse.Style style, Horse.Color color, Horse.Variant variant, boolean randomize) {
        boolean adult = randomize ? random.nextBoolean() : true;
        return spawnHorse(location, name, tamed, player, style, color, variant, adult, randomize);
    }

    public Horse spawnHorse(Location location, String name, boolean tamed, Player player,
            Horse.Style style, Horse.Color color, Horse.Variant variant, boolean adult, boolean randomize) {
        Horse horse = (Horse) spawnEntity(location, EntityType.HORSE);
        horse.setCustomName(name);
        horse.setCustomNameVisible(true);
        horse.setTamed(tamed);
        horse.setOwner(player);
        horse.setStyle(style);
        horse.setColor(color);
        horse.setVariant(variant);
        if (adult) {
            horse.setAdult();
        } else {
            horse.setBaby();
        }
        return horse;
    }

    public void setHorseSpeed(Horse horse) {
        setHorseSpeed(horse, NORMAL_SPEED);
    }

    public void setHorseSpeed(Horse horse, double speed) {
        AttributeInstance attributes = ((EntityInsentient) ((CraftLivingEntity) horse).getHandle()).getAttributeInstance(GenericAttributes.d);
        attributes.setValue(speed);
    }

}
