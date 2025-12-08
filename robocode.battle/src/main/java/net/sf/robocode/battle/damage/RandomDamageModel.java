package net.sf.robocode.battle.damage;

import java.lang.Math;

public class RandomDamageModel implements IDamageModel {
    
    // Variables finales: inmutables
    private final double minDamage;
    private final double maxDamage;

    // Inyección por Constructor
    public RandomDamageModel(double minDamage, double maxDamage) {
        // Validaciones 
        if (minDamage < 0) minDamage = 0;
        if (maxDamage < minDamage) maxDamage = minDamage; // Evitar rangos negativos
        
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
    }

    @Override
    public double getRobotHitDamage() {
        return minDamage + (Math.random() * (maxDamage - minDamage));
    }
}