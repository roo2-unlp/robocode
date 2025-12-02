package net.sf.robocode.battle.traps;

import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.peer.RobotPeer;
import net.sf.robocode.io.Logger;
import robocode.TrapEffectType;

public class DamageTrap extends Trap{

	private final double damage;

	public DamageTrap(double x, double y, double radius, double damage) {
		super(x, y, radius, TrapEffectType.DAMAGE);
		this.damage = damage;
	}
	public double getDamage() {
		return damage;
	}

	@Override
	public void applyEffect(RobotPeer robot) {

		robot.applyTrapDamage(this.damage);

		if(robot.isDead()){
			return;
		}

		robot.resetTrapCooldown();


		Logger.logMessage("TRAMPA: " + robot.getName() + " pisó una trampa de DAÑO. Daño: " + this.damage);
	}
}
