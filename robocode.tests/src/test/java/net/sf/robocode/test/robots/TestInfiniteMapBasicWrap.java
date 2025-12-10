package net.sf.robocode.test.robots;

import net.sf.robocode.test.helpers.Assert;
import org.junit.Test;

/**
 * Tests básicos de wrapping en X y Y
 */
public class TestInfiniteMapBasicWrap {

    private double normalizePosition(double position, double fieldSize, int minBound) {
        double usableSize = fieldSize - 2 * (minBound);
        double relativePos = position - minBound;
        relativePos = ((relativePos % usableSize) + usableSize) % usableSize;
        return minBound + relativePos;
    }

    @Test
    public void testWrapPositionX() {
        double x = 790.0;
        x = normalizePosition(x, 800.0, 18);
        Assert.assertTrue(x >= 18.0 && x <= 782.0);
    }

    @Test
    public void testWrapPositionY() {
        double y = -10.0;
        y = normalizePosition(y, 600.0, 18);
        Assert.assertTrue(y >= 18.0 && y <= 582.0);
    }

    @Test
    public void testWrapPositionDiagonal() {
        double x = 2400.0;
        double y = -1200.0;
        x = normalizePosition(x, 800.0, 18);
        y = normalizePosition(y, 600.0, 18);
        Assert.assertTrue(x >= 18.0 && x <= 782.0);
        Assert.assertTrue(y >= 18.0 && y <= 582.0);
    }
}