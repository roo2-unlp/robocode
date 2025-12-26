package net.sf.robocode.battle.peer;

import java.awt.geom.Rectangle2D;

/**
 * Utilidad matemática para balas de proximidad.
 *
 * Esta clase contiene lógica independiente del motor de Robocode para:
 * - determinar si un robot se encuentra dentro del radio de una bala de proximidad
 * - calcular el daño en función de la distancia al centro de la explosión
 */

public class ProximityMath {

	private ProximityMath() {}

	/**
	 * Determina si un rectángulo (bounding box del robot) intersecta un círculo de proximidad.
	 *
	 * @param cx coordenada X del centro del círculo
	 * @param cy coordenada Y del centro del círculo
	 * @param radius radio de proximidad
	 * @param box bounding box del robot
	 * @return true si el rectángulo intersecta o toca el círculo
	 */
	public static boolean intersects(double cx, double cy, double radius, Rectangle2D box) {

		double nearestX = Math.max(box.getMinX(), Math.min(cx, box.getMaxX()));
		double nearestY = Math.max(box.getMinY(), Math.min(cy, box.getMaxY()));
		double dx = nearestX - cx;
		double dy = nearestY - cy;

		return dx * dx + dy * dy <= radius * radius;
	}

	/**
	 * Calcula el poder efectivo de una bala de proximidad en función
	 * de la distancia entre el centro de la bala y el centro del robot.
	 *
	 * El poder se ajusta linealmente dentro del radio de proximidad:
	 * - 90% del poder base cuando la distancia es 0 (impacto en el centro)
	 * - 10% del poder base cuando la distancia es igual o mayor al radio
	 *
	 * @param bulletPower poder base de la bala
	 * @param bulletX coordenada X del centro de la bala
	 * @param bulletY coordenada Y del centro de la bala
	 * @param robotX coordenada X del centro del robot
	 * @param robotY coordenada Y del centro del robot
	 * @param proximityRadius radio efectivo de proximidad
	 * @return poder ajustado de la bala, luego de aplicar el modelo de proximidad
	 */

	public static double adjustedPower(double bulletPower, double bulletX, double bulletY, double robotX, double robotY, double proximityRadius) {

		double dx = robotX - bulletX;
		double dy = robotY - bulletY;
		double distance = Math.hypot(dx, dy);
		double normalized = Math.min(Math.max(distance, 0), proximityRadius) / proximityRadius;

		double impactFactor = 0.90 - 0.80 * normalized;

		return bulletPower * impactFactor;
	}

}
