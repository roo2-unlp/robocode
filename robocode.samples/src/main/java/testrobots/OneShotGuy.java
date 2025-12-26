package testrobots;

import robocode.ProximityRobot;
import robocode.ScannedRobotEvent;

import java.awt.*;

/**
 * OneShotGuy - un robot que usaremos para testear las balas de proximidad.
 * <p>
 * Su proposito es testear, para ello espera a que un robot se le acerque lo suficiente
 * y efectua un solo disparo de proximidad.
 *
 * @author Leo Delmas
 */

public class OneShotGuy extends ProximityRobot {
	private boolean fired = false;
	private static final double FIRE_DISTANCE = 80;

	public void run() {
		setBodyColor(Color.yellow);
		setGunColor(Color.black);
		setRadarColor(Color.black);
		setScanColor(Color.yellow);
		setBulletColor(Color.yellow);

		// No moverse, solo escanear
		while (true) {
			turnGunRight(180);
		}
	}

	@Override
	public void onScannedRobot(ScannedRobotEvent e) {
		if (!fired && getGunHeat() == 0 && e.getDistance() <= FIRE_DISTANCE) {
			fireProximityBullet(3);
			fired = true;
		}
	}
}

