package testrobots;

import robocode.Robot;
import java.awt.*;

/**
 * Sleeper - un robot que lo unico que hace es quedarse quieto.
 * <p>
 * Su unico proposito es para testear.
 *
 * @author Leo Delmas
 */

public class Sleeper extends Robot {

	public void run() {

		setBodyColor(Color.blue);
		setGunColor(Color.gray);
		setRadarColor(Color.gray);
		setScanColor(Color.gray);
		setBulletColor(Color.gray);

		while (true) {
			doNothing();
		}
	}
}
