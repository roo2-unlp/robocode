package net.sf.robocode.battle.traps;

import net.sf.robocode.battle.peer.RobotPeer;
import net.sf.robocode.io.Logger;
import robocode.HitTrapEvent;
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
		// Verificar si el robot ya esta muerto o si energia
		if(robot.isDead() || robot.getEnergy() <= 0){
			return;
		}

		robot.applyTrapDamage(this.damage);

		if(robot.isDead() || robot.getEnergy() <= 0){
			Logger.logMessage("TRAMPA: " + robot.getName() + " pisó una trampa de DAÑO. Daño: " + this.damage + " (FATAL)");
			return;
		}

		robot.resetTrapCooldown();

		robot.addEvent(
				new HitTrapEvent(this.getX(), this.getY(), this.getRadius(), TrapEffectType.DAMAGE)
		);

		Logger.logMessage("TRAMPA: " + robot.getName() + " pisó una trampa de DAÑO. Daño: " + this.damage);
	}
}
