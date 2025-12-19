package net.sf.robocode.test.robots;

import net.sf.robocode.test.helpers.Assert;
import net.sf.robocode.test.helpers.RobocodeTestBed;
import org.junit.Test;
import robocode.control.events.TurnEndedEvent;
import robocode.control.snapshot.IBulletSnapshot;

public class TestInfinityShot extends RobocodeTestBed {

	@Test
	public void run() {
		super.run();
	}

	@Override
	public String getRobotName() {
		return "tested.robots.InfinityShooter";
	}

	@Override
	public String getEnemyName() {
		return "sample.SittingDuck";

	}

	@Override
	protected void runSetup() {
		setInfinityShot(true);
		setInfinityShotLaps(2);
	}

	@Override
	public void onTurnEnded(TurnEndedEvent event) {
		super.onTurnEnded(event);

		int time = event.getTurnSnapshot().getTurn();
		IBulletSnapshot[] bullets = event.getTurnSnapshot().getBullets();
		double mapWidth = 800;

		if (bullets.length == 0) return;

		IBulletSnapshot bullet = bullets[0];
		double x = bullet.getX();

		if (time == 30) {
			Assert.assertTrue("La bala debería ir hacia la derecha", x > 100);
			Assert.assertTrue("La bala aún no debería haber chocado", x < mapWidth);
		}
		if (time == 85) {
			Assert.assertTrue("La bala explotó y no debería haberlo hecho", bullet.getState().isActive());
			Assert.assertTrue(
					"La bala debería estar a la izquierda (X < 200) pero está en X=" + x,
					x < 200
			);
			Assert.assertTrue(
					"La bala cambió de dirección",
					Math.abs(bullet.getHeading() - (Math.PI / 2)) < 0.01
			);
		}
	}
}