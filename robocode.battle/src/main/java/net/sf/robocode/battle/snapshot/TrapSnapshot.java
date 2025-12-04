package net.sf.robocode.battle.snapshot;

import net.sf.robocode.battle.traps.Trap;
import robocode.TrapEffectType;
import robocode.control.snapshot.ITrapSnapshot;

import java.io.Serializable;

/**
 * Representa un snapshot inmutable del estado de una trampa
 * en un momento dado para renderizado y logging.
 */
public final class TrapSnapshot implements Serializable, ITrapSnapshot {

	private static final long serialVersionUID = 1L;

	private final double x;
	private final double y;
	private final double radius;
	private final TrapEffectType trapEffect;

	public TrapSnapshot(Trap trap) {
		this.x = trap.getX();
		this.y = trap.getY();
		this.radius = trap.getRadius();
		this.trapEffect = trap.getTrapEffect();
	}

	public TrapSnapshot(double x, double y, double radius, TrapEffectType trapEffect) {
		this.x = x;
		this.y = y;
		this.radius = radius;
		this.trapEffect = trapEffect;
	}

	@Override
	public double getX() {
		return x;
	}

	@Override
	public double getY() {
		return y;
	}

	@Override
	public double getRadius() {
		return radius;
	}

	@Override
	public TrapEffectType getTrapEffect() {
		return trapEffect;
	}

}
