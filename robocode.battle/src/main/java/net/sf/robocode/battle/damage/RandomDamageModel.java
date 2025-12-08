package net.sf.robocode.battle.damage;

import java.lang.Math;

public class RandomDamageModel implements IDamageModel {
    
    private final double minDamage;
    private final double maxDamage;

    public RandomDamageModel(double minDamage, double maxDamage) {
        // 1. Validación de Seguridad: Nada puede ser menor a 0.1
        if (minDamage < 0.1 || maxDamage < 0.1) minDamage = 0.1;           

        // 2. Validación de Consistencia: El máximo nunca puede ser menor al mínimo
        // Esto cubre tanto el caso de inversión (10, 5) como el caso de corrección por cero (0, 0)
        if (maxDamage < minDamage) maxDamage = minDamage;
                
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
    }

    @Override
    public double getRobotHitDamage() {
        return minDamage + (Math.random() * (maxDamage - minDamage));
    }
}