package net.sf.robocode.battle.effects;

import robocode.BattleRules;


public class BulletEffectCreator {

    public static BulletEffect createBulletEffect(BattleRules battleRules) {
        switch (battleRules.getBulletEffect()) {
            case "StunningEffect":
                return new StunningEffect(battleRules.getStunDuration());
            default:
                return new NoEffect();
        }
    }
}
