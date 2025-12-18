package testrobots;

import java.awt.Color;

import robocode.ProximityRobot;
import robocode.Rules;
import robocode.ScannedRobotEvent;

/**
 * Shooter - un robot que usaremos para implementar las balas de proximidad.
 * <p>
 * Su proposito es disparar balas normales y de proximidad alternando.
 *
 * @author Leo Delmas
 */

public class Shooter extends ProximityRobot {
	boolean shootNormalBullet = true;

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

	public void onScannedRobot(ScannedRobotEvent e) {
		if (getGunHeat() == 0) {
			if (shootNormalBullet) {
				fire(3);
				out.println("shot Normal Bullet");
			} else {
				fireProximityBullet(3);
				out.println("shot Proximity Bullet");
			}
			shootNormalBullet = !shootNormalBullet;
			scan();
		}
	}
}
