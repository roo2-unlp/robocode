package net.sf.robocode.battle.traps;

import net.sf.robocode.battle.effect.DamageEffect;
import net.sf.robocode.battle.peer.RobotPeer;
import net.sf.robocode.io.Logger;
import robocode.HitTrapEvent;

public class DamageTrap extends Trap{

	public DamageTrap(double x, double y, double radius, double damage, int duration) {
		super(x, y, radius, new DamageEffect(damage, duration));
	}

	@Override
	public void applyEffect(RobotPeer robot) {
		// Verificar si el robot ya esta muerto o si energia
		if(robot.isDead() || robot.getEnergy() <= 0){
			return;
		}

		this.getTrapEffect().apply(robot);
		double damage = ((DamageEffect) this.getTrapEffect()).getDamage();

		if(robot.isDead() || robot.getEnergy() <= 0){
			Logger.logMessage("TRAMPA: " + robot.getName() + " pisó una trampa de DAÑO. Daño: "+ damage +" (FATAL)");
			return;
		}

		robot.addEvent(
				new HitTrapEvent(this.getX(), this.getY(), this.getRadius())
		);

		Logger.logMessage("TRAMPA: " + robot.getName() + " pisó una trampa de DAÑO: " + damage);
	}
}
