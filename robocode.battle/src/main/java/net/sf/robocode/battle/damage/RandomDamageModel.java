package net.sf.robocode.battle.damage;

import robocode.Rules;
import java.lang.Math;

public class RandomDamageModel implements IDamageModel {
    @Override
    public double getRobotHitDamage() {
        // Daño entre 0.1 y el máximo estándar (0.6)
        double min = 0.1;
        double max = Rules.ROBOT_HIT_DAMAGE;//preguntar si puedo darle mas daño, se acortarian las batallas
        return min + (Math.random() * (max - min));
    }
}
