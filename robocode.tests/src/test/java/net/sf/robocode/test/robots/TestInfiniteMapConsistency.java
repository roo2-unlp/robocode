package net.sf.robocode.test.robots;

import net.sf.robocode.test.helpers.Assert;
import org.junit.Test;

/**
 * Tests de consistencia y bucles infinitos
 */
public class TestInfiniteMapConsistency {

    private double normalizePosition(double position, double fieldSize, int minBound) {
        double usableSize = fieldSize - 2 * (minBound);
        double relativePos = position - minBound;
        relativePos = ((relativePos % usableSize) + usableSize) % usableSize;
        return minBound + relativePos;
    }

    @Test
    public void testNoBucleInfinito() {
        double x = 790.0;
        int minX = 18, maxX = 782;

        for (int i = 0; i < 10; i++) {
            if (x > maxX) {
                x -= 800.0;
            } else if (x < minX) {
                x += 800.0;
            }

            x = normalizePosition(x, 800.0, minX);

            Assert.assertTrue("Iteración " + i + ": X debe estar en rango",
                    x >= minX && x <= maxX);
        }
    }

    @Test
    public void testWrapConsistencyAfterMultipleWraps() {
        double x = 790.0;
        double result1 = normalizePosition(x, 800.0, 18);
        double result2 = normalizePosition(result1, 800.0, 18);
        double result3 = normalizePosition(result2, 800.0, 18);

        Assert.assertNear(result1, result2);
        Assert.assertNear(result2, result3);
        Assert.assertTrue(result3 >= 18.0 && result3 <= 782.0);
    }
}