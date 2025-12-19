package tested.robots;

import robocode.Robot;

public class InfinityShooter extends Robot {
	@Override
	public void run() {
		turnRight(90 - getHeading());

		fire(3);

		while (true) {
			doNothing();
		}
	}
}