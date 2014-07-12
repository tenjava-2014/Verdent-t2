/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tenjava.entries.Verdent.t2.utils;

import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

/**
 *
 * @author Verdent
 */
public class FireworkManager {

    private static final Random random = new Random();

    public static void playRandomFirework(Location location) {
        Type type = getRandomFireworkType();
        playRandomFirework(location, type);
    }

    public static void playRandomFirework(Location location, Type type) {
        Color color1 = getRandomColor();
        playRandomFirework(location, type, color1);
    }

    public static void playRandomFirework(Location location, Type type, Color color1) {
        Color color2 = getRandomColor();
        playRandomFirework(location, type, color1, color2);
    }

    public static void playRandomFirework(Location location, Type type, Color color1, Color color2) {
        Color colorFade = getRandomColor();
        playRandomFirework(location, type, color1, color2, colorFade);
    }

    public static void playRandomFirework(Location location, Type type, Color color1, Color color2, Color colorFade) {
        boolean trail = random.nextBoolean();
        playRandomFirework(location, type, color1, color2, colorFade, trail);
    }

    public static void playRandomFirework(Location location, Type type, Color color1, Color color2, Color colorFade, boolean trail) {
        boolean flicker = random.nextBoolean();
        playFirework(location, type, color1, color2, colorFade, trail, flicker);
    }

    public static void playStrictFirework(Location location, Type type, Color color1) {
        Color color2 = color1;
        playStrictFirework(location, type, color1, color2);
    }

    public static void playStrictFirework(Location location, Type type, Color color1, Color color2) {
        Color colorFade = color1;
        playStrictFirework(location, type, color1, color2, colorFade);
    }

    public static void playStrictFirework(Location location, Type type, Color color1, Color color2, Color colorFade) {
        playStrictFirework(location, type, color1, color2, colorFade, false);
    }

    public static void playStrictFirework(Location location, Type type, Color color1, Color color2, Color colorFade, boolean trail) {
        playFirework(location, type, color1, color2, colorFade, trail, false);
    }

    public static void playFirework(Location location, Type type, Color color1, Color color2, Color colorFade, boolean trail, boolean flicker) {
        final Firework firework = location.getWorld().spawn(location, Firework.class);
        editFirework(firework, type, color1, color2, colorFade, trail, flicker);
        //firework.detonate();
        new BukkitRunnable() {
            @Override
            public void run() {
                firework.detonate();
            }
        }.runTaskLater(Bukkit.getPluginManager().getPlugin("WiveServerPlugin"), 5);

    }

    public static void editFirework(Firework firework, Type type, Color color1, Color color2, Color colorFade, boolean trail, boolean flicker) {
        FireworkMeta meta = firework.getFireworkMeta();
        firework.setFireworkMeta(editFireworkMeta(meta, type, color1, color2, colorFade, trail, flicker));
    }

    public static FireworkMeta editFireworkMeta(FireworkMeta meta, Type type, Color color1, Color color2, Color colorFade, boolean trail, boolean flicker) {
        meta.addEffect(FireworkEffect.builder()
                .flicker(flicker)
                .trail(trail)
                .with(type)
                .withColor(color1)
                .withColor(color2)
                .withFade(colorFade)
                .build());
        meta.setPower(0);
        return meta;
    }

    private static Type getRandomFireworkType() {
        int num = random.nextInt(Type.values().length);
        return Type.values()[num];
    }

    private static Color getRandomColor() {
        int num = random.nextInt(17) + 1;
        switch (num) {
            case 1:
                return Color.AQUA;
            case 2:
                return Color.BLACK;
            case 3:
                return Color.BLUE;
            case 4:
                return Color.FUCHSIA;
            case 5:
                return Color.GRAY;
            case 6:
                return Color.GREEN;
            case 7:
                return Color.LIME;
            case 8:
                return Color.MAROON;
            case 9:
                return Color.NAVY;
            case 10:
                return Color.OLIVE;
            case 11:
                return Color.ORANGE;
            case 12:
                return Color.PURPLE;
            case 13:
                return Color.RED;
            case 14:
                return Color.SILVER;
            case 15:
                return Color.TEAL;
            case 16:
                return Color.WHITE;
            case 17:
                return Color.YELLOW;
            default:
                return Color.AQUA;
        }
    }
}
