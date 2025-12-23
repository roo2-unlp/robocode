package net.sf.robocode.battle.effect;

import net.sf.robocode.peer.IRobotPeer;

public class StickyEffect implements ITrapEffect{
	private final double slowFactor;
	private final int duration;

	public StickyEffect(double slowFactor, int duration) {
		this.slowFactor = slowFactor;
		this.duration = duration;
	}
	@Override
	public void apply(IRobotPeer robot) {
		robot.setMovementMultiplier(slowFactor);
	}

	@Override
	public void revert(IRobotPeer robot) {
		robot.setMovementMultiplier(1.0);
	}

	@Override
	public int getDuration() {
		return duration;
	}

	@Override
	public String getMessage() {
		return "Slowed by factor: " + slowFactor;
	}

}
