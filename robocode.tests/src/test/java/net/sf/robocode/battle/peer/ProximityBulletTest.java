package net.sf.robocode.battle.peer;

import org.junit.Test;

import java.awt.geom.Rectangle2D;

import static org.junit.Assert.*;

public class ProximityBulletTest {

	@Test
	public void elRobotSeEncuentraDentroDeRadio() {
		// El bounding box del robot se solapa con el radio de proximidad, por lo que debe considerarse dentro del área de efecto.
		Rectangle2D box = new Rectangle2D.Double(90, -10, 20, 20);

		boolean result = ProximityMath.intersects(0, 0, 100, box);

		assertTrue(result);
	}

	@Test
	public void elRobotSeEncuentraFueraDeRadio() {
		// El bounding box del robot se encuentra completamente fuera del radio de proximidad, por lo que no debe haber intersección.
		Rectangle2D box = new Rectangle2D.Double(150, -10, 20, 20);

		boolean result = ProximityMath.intersects(0, 0, 100, box);

		assertFalse(result);
	}

	@Test
	public void elRobotRecibeElDanioMaximoEnElCentroDelRadio() {
		// El robot está en el mismo punto que la bala (distancia 0).
		// En este caso, el poder ajustado debe ser el máximo permitido, el 90% del poder base de la bala.
		double power = ProximityMath.adjustedPower(3.0,0, 0,0, 0,100);

		assertEquals(2.7, power, 1e-6); // 3 * 0.90

	}

	@Test
	public void elRobotRecibeElDanioMinimoEnElBordeDelRadio() {
		// El robot se encuentra exactamente en el borde del radio de proximidad.
		// En este caso, el poder ajustado debe ser el mínimo permitido, el 10% del poder base de la bala.
		double power = ProximityMath.adjustedPower(3.0, 0, 0, 100, 0, 100);

		assertEquals(0.3, power, 1e-6); // 3 * 0.10
	}
}
