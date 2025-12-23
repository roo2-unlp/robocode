package net.sf.robocode.test.trap;

import net.sf.robocode.peer.BadBehavior;
import net.sf.robocode.peer.ExecCommands;
import net.sf.robocode.peer.ExecResults;
import net.sf.robocode.peer.IRobotPeer;

public class TrapRobot implements IRobotPeer {
	private double currentEnergy = 100.0;
	private final double VELOCITY = 8.0;
	private double currentVelocity = VELOCITY;

	public TrapRobot() {
		super();
	}

	public double getEnergy() {
		return this.currentEnergy;
	}

	public double getVelocity() {
		return this.currentVelocity;
	}

	@Override
	public void drainEnergy() {

	}

	@Override
	public void punishBadBehavior(BadBehavior badBehavior) {

	}

	@Override
	public void setRunning(boolean value) {

	}

	@Override
	public boolean isRunning() {
		return false;
	}

	@Override
	public ExecResults waitForBattleEndImpl(ExecCommands newCommands) {
		return null;
	}

	@Override
	public ExecResults executeImpl(ExecCommands newCommands) {
		return null;
	}

	public void setMovementMultiplier(double multiplier) {
		this.currentVelocity = this.VELOCITY * multiplier;
	}

	public void applyEnergyEffect(double delta) {
		this.currentEnergy += delta;
	}
}
