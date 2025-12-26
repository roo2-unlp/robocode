package testrobots;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import robocode.ProximityBullet;
import sample.Interactive_v2;

/**
 * ProximityInteractive - un robot interactivo que puede disparar balas de proximidad.
 * <p>
 * Hereda de {@link Interactive_v2} para obtener controles de movimiento absoluto
 * con teclado/mouse. Puede disparar una sola bala de proximidad con la barra espaciadora
 * o haciendo click con el mouse.
 * <p>
 * Una vez que la bala de proximidad explota o sale del campo, puede disparar otra.
 * <p>
 * Controles heredados de Interactive_v2:
 * <ul>
 *   <li>W / Flecha arriba: mover hacia arriba (norte)</li>
 *   <li>S / Flecha abajo: mover hacia abajo (sur)</li>
 *   <li>A / Flecha izquierda: mover hacia izquierda (oeste)</li>
 *   <li>D / Flecha derecha: mover hacia derecha (este)</li>
 *   <li>Mouse: apuntar el cañon</li>
 * </ul>
 * <p>
 * Controles de disparo de proximidad:
 * <ul>
 *   <li>Espacio: disparar bala de proximidad</li>
 *   <li>Click izquierdo: disparar bala de proximidad (power 1)</li>
 *   <li>Click medio: disparar bala de proximidad (power 2)</li>
 *   <li>Click derecho: disparar bala de proximidad (power 3)</li>
 * </ul>
 *
 * @author Leo Delmas
 */
public class ProximityInteractive extends Interactive_v2 {
	private ProximityBullet currentProximityBullet = null;

	@Override
	public void run() {
		// Colores distintivos para identificar este robot
		setBodyColor(Color.ORANGE);
		setGunColor(Color.RED);
		setRadarColor(Color.YELLOW);
		setScanColor(Color.YELLOW);
		setBulletColor(Color.ORANGE);

		// Ejecutar el loop principal de Interactive_v2
		super.run();
	}

	@Override
	public void onKeyPressed(KeyEvent e) {
		// Manejar la tecla espacio para disparo de proximidad
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			fireProximityIfReady(3);
			return;
		}

		// Para las demas teclas, usar el comportamiento de Interactive_v2
		super.onKeyPressed(e);
	}

	@Override
	public void onMousePressed(MouseEvent e) {
		// Determinar el poder segun el boton del mouse
		int power;
    switch (e.getButton()) {
        case MouseEvent.BUTTON3:
            power = 1; // Click derecho = poder minimo
            break;
        case MouseEvent.BUTTON2:
            power = 2; // Click medio = poder medio
            break;
        default:
            power = 3; // Click izquierdo = maximo poder
            break;
    }
		
		fireProximityIfReady(power);
	}

	@Override
	public void onMouseReleased(MouseEvent e) {
		// No hacer nada - solo disparamos una vez
	}

	@Override
	public void onPaint(Graphics2D g) {
		super.onPaint(g);

		// Mostrar indicador de bala de proximidad disponible
		if (canFireProximity()) {
			g.setColor(Color.GREEN);
			g.drawString("PROXIMITY READY [SPACE/CLICK]", 10, 20);
		} else {
			g.setColor(Color.RED);
			g.drawString("PROXIMITY IN FLIGHT", 10, 20);
		}
	}

	/**
	 * Verifica si se puede disparar una bala de proximidad.
	 * Solo se puede si no hay otra bala de proximidad activa en el campo.
	 */
	private boolean canFireProximity() {
		return currentProximityBullet == null || !currentProximityBullet.isActive();
	}

	/**
	 * Dispara una bala de proximidad si no hay otra activa y el cañon esta listo.
	 */
	private void fireProximityIfReady(double power) {
		if (canFireProximity() && getGunHeat() == 0) {
			currentProximityBullet = fireProximityBullet(power);
		}
	}
}

