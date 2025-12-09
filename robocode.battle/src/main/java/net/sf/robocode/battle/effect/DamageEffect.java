package net.sf.robocode.battle.effect;

import net.sf.robocode.battle.peer.RobotPeer;

public class DamageEffect implements ITrapEffect{
	private final double damage;
	private final int duration;

	public DamageEffect(double damage, int duration)
	{
		this.damage = damage;
		this.duration = duration;
	}
	@Override
	public void apply(RobotPeer robot) {
		robot.applyEnergyEffect(-damage);
	}

	@Override
	public void revert(RobotPeer robot) {
	}

	@Override
	public int getDuration() {
		return duration;
	}

	@Override
	public String getMessage() {
		return "Damage: " + damage;
	}

	public double getDamage() {
		return damage;
	}
}
