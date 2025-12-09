package net.sf.robocode.battle.traps;

import net.sf.robocode.battle.effect.StickyEffect;
import net.sf.robocode.battle.peer.RobotPeer;
import net.sf.robocode.io.Logger;
import robocode.HitTrapEvent;

public class StickyTrap extends Trap{

	public StickyTrap(double x, double y, double radius,double slowfactor, int duration) {
		super(x, y, radius, new StickyEffect(slowfactor, duration));
	}

	@Override
	public void applyEffect(RobotPeer robot) {
		// Verificar si el robot ya está muerto o sin energía
		if (robot.isDead() || robot.getEnergy() <= 0) {
			return;
		}

		this.getTrapEffect().apply(robot);

		robot.addEvent(
				new HitTrapEvent(this.getX(), this.getY(), this.getRadius())
		);

		final double slowFactor = ((StickyEffect) this.getTrapEffect()).getSlowFactor();
		Logger.logMessage("TRAMPA: " + robot.getName() + " pisó una trampa PEGAJOSA. Ralentización a: x" + slowFactor);
	}
}
