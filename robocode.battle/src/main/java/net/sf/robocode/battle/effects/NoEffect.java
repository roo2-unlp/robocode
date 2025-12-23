package net.sf.robocode.battle.effects;

import net.sf.robocode.battle.peer.RobotPeer;
import net.sf.robocode.battle.peer.BulletPeer;

public class NoEffect implements BulletEffect {

    @Override
    public void applyEffect(BulletPeer bullet, RobotPeer target) {
        // No effect applied
    }

    @Override
    public String toString() {
        return "No Effect";
    }
}
