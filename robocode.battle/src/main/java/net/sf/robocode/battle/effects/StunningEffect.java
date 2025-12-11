package net.sf.robocode.battle.effects;

import net.sf.robocode.battle.peer.RobotPeer;
import net.sf.robocode.battle.peer.BulletPeer;

public class StunningEffect implements BulletEffect {
    private final int stunDuration;

    public StunningEffect(int stunDuration) {
        this.stunDuration = stunDuration;
    }

    @Override
    public void applyEffect(BulletPeer bullet, RobotPeer target) {
        target.skipNextTurns((int) stunDuration * (int) bullet.getPower());
    }
}
