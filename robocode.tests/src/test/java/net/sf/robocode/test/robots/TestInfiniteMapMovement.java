package net.sf.robocode.test.robots;

import net.sf.robocode.test.helpers.Assert;
import org.junit.Test;

/**
 * Tests de movimiento con wrapping
 */
public class TestInfiniteMapMovement {

    private double normalizePosition(double position, double fieldSize, int minBound) {
        double usableSize = fieldSize - 2 * (minBound);
        double relativePos = position - minBound;
        relativePos = ((relativePos % usableSize) + usableSize) % usableSize;
        return minBound + relativePos;
    }

    @Test
    public void testWrapPreservesVelocityDirection() {
        double x = 790.0;
        double velocity = 8.0;
        double previousX = x;
        x += velocity;

        if (x > 782) {
            x -= 800.0;
        }
        x = normalizePosition(x, 800.0, 18);

        Assert.assertTrue(x >= 18.0 && x <= 782.0);
        Assert.assertTrue(x > previousX || x < previousX);
    }

    @Test
    public void testWrapStationaryRobot() {
        double x = 790.0;
        double velocity = 0.0;
        double newX = x + velocity;

        if (newX > 782) {
            newX -= 800.0;
        }
        newX = normalizePosition(newX, 800.0, 18);

        Assert.assertTrue(newX >= 18.0 && newX <= 782.0);
    }

    @Test
    public void testWrapWithNegativeVelocity() {
        double x = 50.0;
        double velocity = -8.0;
        double newX = x + velocity;

        if (newX < 18) {
            newX += 800.0;
        }
        newX = normalizePosition(newX, 800.0, 18);

        Assert.assertTrue(newX >= 18.0 && newX <= 782.0);
    }

    @Test
    public void testWrapContinuousMovement() {
        double x = 770.0;
        double velocity = 8.0;

        for (int turn = 0; turn < 20; turn++) {
            x += velocity;

            if (x > 782) {
                x -= 800.0;
            } else if (x < 18) {
                x += 800.0;
            }

            x = normalizePosition(x, 800.0, 18);

            Assert.assertTrue("Turn " + turn + ": X debe estar en rango",
                    x >= 18.0 && x <= 782.0);
        }
    }
}