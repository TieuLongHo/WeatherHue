package org.WeatherLight.TinkerForge;

import com.tinkerforge.*;

import java.util.Arrays;

public class LedStrip {
    private final BrickletLEDStripV2 ls;
    private int ledCount = 50;

    public LedStrip(final String HOST, final int PORT, final String UID, final int ledCount) throws TinkerforgeException {
        this.ledCount = ledCount;
        IPConnection ipcon = TinkerForgeConnection.connect(HOST, PORT);
        this.ls = new BrickletLEDStripV2(UID, ipcon);
        this.ls.setFrameDuration(100);

    }


    public void setState(int[] colors) throws TinkerforgeException {
        ls.setLEDValues(0, colors);
    }

    public int getState() throws TinkerforgeException {
        return RGBToPercent(ls.getLEDValues(0, ledCount)[0]);
    }

    public int[] percentToRGB(int[] rgb) {
        int[] allLeds = new int[ledCount * 3];
        for (int i = 0; i < ledCount; i++) {
            allLeds[i * 3] = rgb[0];
            allLeds[i * 3 + 1] = rgb[1];
            allLeds[i * 3 + 2] = rgb[2];
        }
        return allLeds;
    }
    private int RGBToPercent(int rgb) {
        return (int) (rgb / 255 * 100);
    }

    public void brightenRGB(int[] colors, int duration) throws TinkerforgeException {
        int[] startColors = new int[colors.length];
        Arrays.fill(startColors, 0);

        long startTime = System.currentTimeMillis();
        long elapsedTime = 0;

        while (elapsedTime < duration) {
            float progress = (float) elapsedTime / duration;
            int[] currentColors = new int[colors.length];

            for (int i = 0; i < colors.length; i++) {
                currentColors[i] = (int) (startColors[i] * (1 - progress) + colors[i] * progress);
            }

            setState(currentColors);

            elapsedTime = System.currentTimeMillis() - startTime;
        }

       setState(colors);
    }
}
