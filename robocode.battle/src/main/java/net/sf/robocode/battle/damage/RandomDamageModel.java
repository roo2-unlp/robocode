package net.sf.robocode.battle.damage;

public class RandomDamageModel implements IDamageModel {
    
    private final double minDamage;
    private final double maxDamage;

    public RandomDamageModel(double minDamage, double maxDamage) {
        if (minDamage < 0.1 || maxDamage < 0.1) minDamage = 0.1;           

        if (maxDamage < minDamage) maxDamage = minDamage;
                
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
    }

    @Override
    public double getRobotHitDamage() {
        return minDamage + (Math.random() * (maxDamage - minDamage));
    }
}