package testrobots;

import robocode.Robot;
import robocode.ScannedRobotEvent;
import java.awt.*;

/**
 * Charger - un robot que lo unico que hace es cargar contra el primer robot que vea.
 * <p>
 * Su unico proposito es para testear si el robot que lanzo la bala radiactiva recibe daño.
 *
 * @author Leo Delmas
 */

public class Charger extends Robot {

	public void run() {

		setBodyColor(Color.red);
		setGunColor(Color.gray);
		setRadarColor(Color.gray);
		setScanColor(Color.gray);
		setBulletColor(Color.gray);

		while (true) {
			turnRadarRight(360);
		}
	}

	/**
	 * Cuando detecta a otro robot se acerca pero solo lo suficiente como para que no colisionen
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		turnRight(e.getBearing());

		double distancia = e.getDistance();
		double margen = 50;  // seguridad para no chocar

		if (distancia > margen) {
			ahead(distancia - margen);
		} else {
			// si está muy cerca, mejor no mover
			stop();
		}
	}
}
