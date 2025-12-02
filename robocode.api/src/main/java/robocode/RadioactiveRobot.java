/*
 * Copyright (c) 2001-2025 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * https://robocode.sourceforge.io/license/epl-v10.html
 */
package robocode;

import robocode.robotinterfaces.peer.IRadioactiveRobotPeer;

/**
 * A robot that can fire radioactive bullets.
 */
public class RadioactiveRobot extends Robot {

	/**
	 * Immediately fires a radioactive bullet. The bullet will travel in the direction the
	 * gun is pointing.
	 *
	 * @param power the amount of energy given to the bullet, and subtracted
	 *              from the robot's energy.
	 * @return a {@link RadioactiveBullet} that contains information about the bullet if it
	 *         was actually fired, which can be used for tracking the bullet after it
	 *         has been fired. If the bullet was not fired, {@code null} is returned.
	 */
	public RadioactiveBullet fireRadioactiveBullet(double power) {
		if (peer != null) {
			return ((IRadioactiveRobotPeer) peer).fireRadioactiveBullet(power);
		}
		uninitializedException();
		return null;
	}
}
