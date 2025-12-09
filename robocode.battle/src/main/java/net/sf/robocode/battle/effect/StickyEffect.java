package net.sf.robocode.battle.effect;

import net.sf.robocode.battle.peer.RobotPeer;

public class StickyEffect implements ITrapEffect{
	private final double slowFactor;
	private final int duration;

	public StickyEffect(double slowFactor, int duration) {
		this.slowFactor = slowFactor;
		this.duration = duration;
	}
	@Override
	public void apply(RobotPeer robot) {
		robot.setMovementMultiplier(slowFactor);
	}

	@Override
	public void revert(RobotPeer robot) {
		robot.setMovementMultiplier(1.0);
	}

	@Override
	public int getDuration() {
		return duration;
	}

	public double getSlowFactor() {
		return slowFactor;
	}
}
