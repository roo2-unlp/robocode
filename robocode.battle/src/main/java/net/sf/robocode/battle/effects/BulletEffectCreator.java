package net.sf.robocode.battle.effects;

import robocode.BattleRules;


public class BulletEffectCreator{
    public static BulletEffect createBulletEffect(BattleRules battleRules) {
        switch (battleRules.getBulletEffect()) {
            case "STUN":
                return new StunningEffect(battleRules.getStunDuration());
            case "NORMAL":
            default:
                return new NoEffect();
        }
    }
}
