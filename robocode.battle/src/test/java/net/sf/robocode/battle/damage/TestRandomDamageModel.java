package net.sf.robocode.battle.damage;

import org.junit.Assert;
import org.junit.Test;

public class TestRandomDamageModel {

    // --- PARTICIÓN EQUIVALENTE 1: Rango Máximo Permitido  en la UI ---
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

    // --- PARTICIÓN EQUIVALENTE 2: Rango Estándar (Default Gameplay) ---
    //  Verifica que la aleatoriedad funcione bien en decimales pequeños
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

    // --- PARTICIÓN EQUIVALENTE: Valores Negativos ---
    //  Verifica que capture números negativos, no solo el cero
    @Test
    public void testNegativeValuesCorrection() {
        RandomDamageModel model = new RandomDamageModel(-5.0, -10.0);
        // Debería corregirse al mínimo seguro (0.1)
        Assert.assertEquals("Los negativos deben subir a 0.1", 
                0.1, model.getRobotHitDamage(), 0.0001);
    }

    // --- VALOR DE BORDE: Mínimo igual a Máximo ---
    @Test
    public void testMinEqualsMax() {
        double val = 5.0;
        RandomDamageModel model = new RandomDamageModel(val, val);
        Assert.assertEquals("Si min=max, el daño es constante", 
                val, model.getRobotHitDamage(), 0.0001);
    }

    // --- VALOR DE BORDE: Límite Inferior (Cero) ---
    @Test
    public void testZeroDamage() {
        RandomDamageModel model = new RandomDamageModel(0, 0);
        Assert.assertEquals("El daño debe corregirse a 0.1", 
                0.1, model.getRobotHitDamage(), 0.0001);
    }
    
    // --- PARTICION EQUIVALENTE: Límites Invertidos ---
    @Test
    public void testInvertedLimitsCorrection() {
        double min = 10.0;
        double max = 5.0; 
        RandomDamageModel model = new RandomDamageModel(min, max);
        Assert.assertEquals("Debe igualar max a min", 
                min, model.getRobotHitDamage(), 0.0001);
    }
}