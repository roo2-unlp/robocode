package net.sf.robocode.battle.effects;

import net.sf.robocode.battle.peer.RobotPeer;
import net.sf.robocode.battle.peer.BulletPeer;


public interface BulletEffect {
    public void applyEffect(BulletPeer bullet, RobotPeer target);
}
