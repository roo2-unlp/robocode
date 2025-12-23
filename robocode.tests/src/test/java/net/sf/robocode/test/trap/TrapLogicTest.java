package net.sf.robocode.test.trap;

import net.sf.robocode.battle.effect.DamageEffect;
import net.sf.robocode.battle.effect.StickyEffect;
import org.junit.Assert;
import org.junit.Test;

public class TrapLogicTest {

	@Test
	public void testDamageEffectAccumulation() {
		// GIVEN: Un robot de mentira con 100 de vida
		TrapRobot mockRobot = new TrapRobot();
		DamageEffect effect = new DamageEffect(7.0, 3); // 7 de daño por 3 turnos

		// WHEN: Aplicamos el efecto simulando 3 turnos
		for (int i = 0; i < 3; i++) {
			effect.apply(mockRobot);
		}

		// THEN: La energía debería ser exactamente 79.0 (100 - 21)
		Assert.assertEquals(79.0, mockRobot.getEnergy(), 0.001);
	}
	@Test
	public void testDamageDuration() {
		TrapRobot mockRobot = new TrapRobot();
		// 10 de daño, pero configurado para durar 2 turnos
		DamageEffect effect = new DamageEffect(10.0, 2);

		effect.apply(mockRobot); // Turno 1
		Assert.assertEquals("La Energía debería reducirse a 90",90.0, mockRobot.getEnergy(), 0.001);

		effect.apply(mockRobot); // Turno 2
		Assert.assertEquals("La Energía debería reducirse a 80",80.0, mockRobot.getEnergy(), 0.001);
	}

	@Test
	public void testStickyEffectSlowdown() {
		// GIVEN: Un robot a máxima velocidad
		TrapRobot mockRobot = new TrapRobot();
		double velocidadInicial = mockRobot.getVelocity();
		StickyEffect sticky = new StickyEffect(0.5,50); // Reduce velocidad al 50%

		// WHEN: Entra en la trampa
		sticky.apply(mockRobot);

		// THEN: La velocidad debe ser 4.0
		Assert.assertEquals("La velocidad debería ser 4.0", 4.0, mockRobot.getVelocity(), 0.001);

		// WHEN: Simulamos que el efecto termina y se llama al revert
		sticky.revert(mockRobot);

		// THEN: La velocidad debería volver a ser la inicial
		Assert.assertEquals("La velocidad debería haber vuelto a 8.0",
				velocidadInicial, mockRobot.getVelocity(), 0.001);
	}
}
