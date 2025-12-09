package net.sf.robocode.battle.traps;

import net.sf.robocode.battle.effect.ITrapEffect;
import net.sf.robocode.battle.peer.RobotPeer;

public abstract class Trap {
	private final double x;
	private final double y;
	private final double radius;
	private final ITrapEffect effect;

	public Trap(double x, double y, double radius, ITrapEffect effect) {
		this.x = x;
		this.y = y;
		this.radius = radius;
		this.effect = effect;
	}

	public boolean contains(double rx, double ry) {
		double dx = rx - x;
		double dy = ry - y;
		return dx * dx + dy * dy <= radius * radius;
	}
	public abstract void applyEffect(RobotPeer robot);

	public double getX() { return x; }
	public double getY() { return y; }
	public double getRadius() { return radius; }
	public ITrapEffect getTrapEffect() { return effect; }
	public boolean intersects(double rx, double ry, double robotHalfSize) {
		double closestX = Math.max(x - radius, Math.min(rx, x + radius));
		double closestY = Math.max(y - radius, Math.min(ry, y + radius));

		double dx = rx - closestX;
		double dy = ry - closestY;

		return (dx * dx + dy * dy) <= robotHalfSize * robotHalfSize;
	}


}
