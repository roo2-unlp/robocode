package testrobots;

import java.awt.Color;

import robocode.ScannedRobotEvent;

/**
 * Shooter - un robot que usaremos para implementar las balas radioactivas.
 * <p>
 * Su proposito es disparar balas normales y radiactivas alternando.
 *
 * @author Leo Delmas
 */

public class Shooter extends robocode.RadioactiveRobot {
	boolean shootNormalBullet = true;
	double aumentar = 0;

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
				fire(0.1);
				out.println("shot Normal Bullet: Power: 0.1");
			} else {
				fireRadioactiveBullet(3, aumentar);
				out.println("shot Radioactive Bullet: Power: 3 ; Radius: "+aumentar);
				aumentar = aumentar + 10;
			}
			shootNormalBullet = !shootNormalBullet;
			scan();
		}
	}
}
