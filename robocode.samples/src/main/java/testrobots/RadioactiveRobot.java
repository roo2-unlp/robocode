package testrobots;

import static robocode.util.Utils.normalRelativeAngleDegrees;

import java.awt.Color;

import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.ScannedRobotEvent;

/**
 * RadioactiveRobot - un robot que usaremos para implementar las balas radioactivas.
 * <p>
 * Su unico proposito es disparar balas radioactivas.
 *
 * La logica es igual a la del robot Fire, por lo tanto:
 * @author Mathew A. Nelson (original)
 * @author Flemming N. Larsen (contributor)
 */

public class RadioactiveRobot extends robocode.RadioactiveRobot {
	int dist = 50;

	public void run() {

		setBodyColor(Color.green);
		setGunColor(Color.green);
		setRadarColor(Color.green);
		setScanColor(Color.green);
		setBulletColor(Color.green);

		while (true) {
			turnGunRight(5);
		}
	}

	/**
	 * Fires at detected robots with variable power based on distance and energy
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		if (e.getDistance() < 50 && getEnergy() > 50) {
			fireRadioactiveBullet(3);
		}
		else {
			fireRadioactiveBullet(1);
		}
		scan();
	}

	/**
	 * Reacts to being hit by turning perpendicular to the incoming bullet and moving
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		turnRight(normalRelativeAngleDegrees(90 - (getHeading() - e.getHeading())));

		ahead(dist);
		dist *= -1;
		scan();
	}

	/**
	 * Targets and fires at robots that collide with this robot
	 */
	public void onHitRobot(HitRobotEvent e) {
		double turnGunAmt = normalRelativeAngleDegrees(e.getBearing() + getHeading() - getGunHeading());

		turnGunRight(turnGunAmt);
		fire(3);
	}
}
