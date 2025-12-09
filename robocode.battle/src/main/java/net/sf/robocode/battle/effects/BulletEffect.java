package net.sf.robocode.battle.effects;

import net.sf.robocode.battle.peer.RobotPeer;


public interface BulletEffect {
    public void applyEffect(RobotPeer target, double bulletPower);
}