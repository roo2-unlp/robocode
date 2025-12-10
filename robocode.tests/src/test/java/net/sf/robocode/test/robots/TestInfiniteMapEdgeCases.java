package net.sf.robocode.test.robots;

import net.sf.robocode.test.helpers.Assert;
import org.junit.Test;

/**
 * Tests de casos extremos y bordes
 */
public class TestInfiniteMapEdgeCases {

    private double normalizePosition(double position, double fieldSize, int minBound) {
        double usableSize = fieldSize - 2 * (minBound);
        double relativePos = position - minBound;
        relativePos = ((relativePos % usableSize) + usableSize) % usableSize;
        return minBound + relativePos;
    }

    @Test
    public void testWrapLeftEdge() {
        double x = -10.0;
        x = normalizePosition(x, 800.0, 18);
        Assert.assertTrue(x >= 18.0 && x <= 782.0);
    }

    @Test
    public void testWrapRightEdge() {
        double x = 810.0;
        x = normalizePosition(x, 800.0, 18);
        Assert.assertTrue(x >= 18.0 && x <= 782.0);
    }

    @Test
    public void testWrapTopEdge() {
        double y = -10.0;
        y = normalizePosition(y, 600.0, 18);
        Assert.assertTrue(y >= 18.0 && y <= 582.0);
    }

    @Test
    public void testWrapBottomEdge() {
        double y = 590.0;
        y = normalizePosition(y, 600.0, 18);
        Assert.assertTrue(y >= 18.0 && y <= 582.0);
    }

    @Test
    public void testWrapExtremeFarPosition() {
        double x = 10000.0;
        x = normalizePosition(x, 800.0, 18);
        Assert.assertTrue(x >= 18.0 && x <= 782.0);
    }

    @Test
    public void testWrapNegativeExtremeFarPosition() {
        double y = -5000.0;
        y = normalizePosition(y, 600.0, 18);
        Assert.assertTrue(y >= 18.0 && y <= 582.0);
    }

    @Test
    public void testWrapMultipleTimesX() {
        double x = 3200.0;
        x = normalizePosition(x, 800.0, 18);
        Assert.assertTrue(x >= 18.0 && x <= 782.0);
    }

    @Test
    public void testWrapNegativeMultipleTimesY() {
        double y = -1800.0;
        y = normalizePosition(y, 600.0, 18);
        Assert.assertTrue(y >= 18.0 && y <= 582.0);
    }
}