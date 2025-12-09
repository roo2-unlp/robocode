package net.sf.robocode.battle.effects;

import net.sf.robocode.battle.peer.RobotPeer;

public class StunningEffect implements BulletEffect {
    private final int stunDuration;

    public StunningEffect(int stunDuration) {
        this.stunDuration = stunDuration;
    }

    @Override
    public void applyEffect(RobotPeer target, double bulletPower) {
        target.skipNextTurns(stunDuration * (int) bulletPower);
        
    }
}