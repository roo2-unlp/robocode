/*
 * Copyright (c) 2001-2025 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * https://robocode.sourceforge.io/license/epl-v10.html
 */
package robocode;

import robocode.robotinterfaces.peer.IProximityRobotPeer;

/**
 * A robot that can fire proximity bullets.
 */
public class ProximityRobot extends Robot {

	/**
	 * Immediately fires a proximity bullet. The bullet will travel in the direction the
	 * gun is pointing, using the proximity radius defined by the current battle rules.
	 *
	 * @param power the amount of energy given to the bullet, and subtracted
	 *              from the robot's energy.
	 * @return a {@link ProximityBullet} that contains information about the bullet if it
	 *         was actually fired, which can be used for tracking the bullet after it
	 *         has been fired. If the bullet was not fired, {@code null} is returned.
	 */
	public ProximityBullet fireProximityBullet(double power) {
		if (peer != null) {
			return ((IProximityRobotPeer) peer).fireProximityBullet(power);
		}
		uninitializedException();
		return null;
	}
}
