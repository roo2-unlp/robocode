package net.sf.robocode.battle.damage;

import org.junit.Assert;
import org.junit.Test;

public class TestRandomDamageModel {

    @Test
    public void testDamageWithinConfiguredRange_LargeScale() {
        double min = 0.1;
        double max = 1000.0;
        RandomDamageModel model = new RandomDamageModel(min, max);

        for (int i = 0; i < 1000; i++) {
            double damage = model.getRobotHitDamage();
            Assert.assertTrue("Error: Daño fuera de rango", damage >= min && damage <= max);
        }
    }

    @Test
    public void testDamageWithinConfiguredRange_SmallScale() {
        double min = 0.1;
        double max = 0.6;
        RandomDamageModel model = new RandomDamageModel(min, max);

        for (int i = 0; i < 1000; i++) {
            double damage = model.getRobotHitDamage();
            Assert.assertTrue("Error: Daño estándar fuera de rango", damage >= min && damage <= max);
        }
    }


    @Test
    public void testNegativeValuesCorrection() {
        RandomDamageModel model = new RandomDamageModel(-5.0, -10.0);
        Assert.assertEquals("Los negativos deben subir a 0.1", 
                0.1, model.getRobotHitDamage(), 0.0001);
    }


    @Test
    public void testMinEqualsMax() {
        double val = 5.0;
        RandomDamageModel model = new RandomDamageModel(val, val);
        Assert.assertEquals("Si min=max, el daño es constante", 
                val, model.getRobotHitDamage(), 0.0001);
    }


    @Test
    public void testZeroDamage() {
        RandomDamageModel model = new RandomDamageModel(0, 0);
        Assert.assertEquals("El daño debe corregirse a 0.1", 
                0.1, model.getRobotHitDamage(), 0.0001);
    }
    
    @Test
    public void testInvertedLimitsCorrection() {
        double min = 10.0;
        double max = 5.0; 
        RandomDamageModel model = new RandomDamageModel(min, max);
        Assert.assertEquals("Debe igualar max a min", 
                min, model.getRobotHitDamage(), 0.0001);
    }
}