package testrobots;

import robocode.AdvancedRobot;
import java.awt.*;

/**
 * Circle - un robot que lo unico que hace es dar vueltas en circulos.
 * <p>
 * Su unico proposito es para testear lo relacionado al daño por radio de las balas radioactivas.
 *
 * @author Leo Delmas
 */

public class Circle extends AdvancedRobot {

	public void run() {

		setBodyColor(Color.cyan);
		setGunColor(Color.gray);
		setRadarColor(Color.gray);
		setScanColor(Color.gray);
		setBulletColor(Color.gray);

		while (true) {
			setTurnRight(10000);
			setMaxVelocity(5);
			ahead(10000);
		}
	}
}
