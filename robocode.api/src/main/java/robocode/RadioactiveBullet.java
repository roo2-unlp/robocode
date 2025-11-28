/*
 * Copyright (c) 2001-2025 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * https://robocode.sourceforge.io/license/epl-v10.html
 */
package robocode;

/**
 * A specialized Bullet that carries a fixed proximity radius.
 * This class is not currently integrated with the battle engine; it is
 * provided as an example extension of {@link Bullet} that could be used
 * for custom game modes like radioactive bullets.
 */
public class RadioactiveBullet extends Bullet {
	private static final long serialVersionUID = 1L;

	// Fixed proximity radius within which the bullet will trigger an explosion
	private final double proximityRadius;

	/**
	 * Constructs a new {@code RadioactiveBullet} with a fixed proximity radius.
	 *
	 * @param heading        heading in radians
	 * @param x              starting X position
	 * @param y              starting Y position
	 * @param power          bullet power
	 * @param ownerName      owner robot name
	 * @param victimName     victim robot name (may be null)
	 * @param isActive       whether bullet is active
	 * @param bulletId       unique id for owner robot
	 * @param proximityRadius explosion trigger radius (non-negative)
	 */
	public RadioactiveBullet(double heading,
				        double x,
				        double y,
				        double power,
				        String ownerName,
				        String victimName,
				        boolean isActive,
				        int bulletId,
				        double proximityRadius) {
		super(heading, x, y, power, ownerName, victimName, isActive, bulletId);
		this.proximityRadius = proximityRadius;
	}

	/**
   * Returns the proximity radius within which this bullet will trigger an explosion.
   * 
	 * @return the fixed proximity radius for this bullet.
	 */
	public double getProximityRadius() {
    return this.proximityRadius;
  }

	@Override
	public String toString() {
		return super.toString() + " Prad=" + (int) proximityRadius;
	}
}
