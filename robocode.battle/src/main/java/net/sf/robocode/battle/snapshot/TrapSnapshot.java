package net.sf.robocode.battle.snapshot;

import net.sf.robocode.battle.traps.Trap;
import robocode.control.snapshot.ITrapSnapshot;
import java.io.Serializable;


public final class TrapSnapshot implements Serializable, ITrapSnapshot {

	private static final long serialVersionUID = 1L;

	private final double x;
	private final double y;
	private final double radius;

	public TrapSnapshot(Trap trap) {
		this.x = trap.getX();
		this.y = trap.getY();
		this.radius = trap.getRadius();
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

}
