package net.sf.robocode.battle.damage;

import robocode.Rules;

public class StandardDamageModel implements IDamageModel {
    @Override
    public double getRobotHitDamage() {
        return Rules.ROBOT_HIT_DAMAGE; // 0.6 constante
    }
}
