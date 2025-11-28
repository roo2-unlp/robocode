package net.sf.robocode.battle.traps;

public class Trap {
	private final double x;
	private final double y;
	private final double radius;
	private final double damage;

	public Trap(double x, double y, double radius, double damage) {
		this.x = x;
		this.y = y;
		this.radius = radius;
		this.damage = damage;
	}

	public boolean contains(double rx, double ry) {
		double dx = rx - x;
		double dy = ry - y;
		return dx * dx + dy * dy <= radius * radius;
	}

	public double getDamage() {
		return damage;
	}

	public double getX() { return x; }
	public double getY() { return y; }
	public double getRadius() { return radius; }

	public boolean intersects(double rx, double ry, double robotHalfSize) {
		double closestX = Math.max(x - radius, Math.min(rx, x + radius));
		double closestY = Math.max(y - radius, Math.min(ry, y + radius));

		double dx = rx - closestX;
		double dy = ry - closestY;

		return (dx * dx + dy * dy) <= robotHalfSize * robotHalfSize;
	}


}
