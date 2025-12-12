package net.sf.robocode.test.robots;

import net.sf.robocode.battle.NullWallHitDamageStrategy;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestRandomHitDamageStrategy {
	@Test
	public void calcularDanioRandomNullStrategyTest(){
		NullWallHitDamageStrategy nulo = new NullWallHitDamageStrategy();
		Assert.assertEquals(1, nulo.getDiceExtraWallDamage());
	}
	@Test
	public void DanioExtraRandomNullStrategyTest(){
		NullWallHitDamageStrategy nulo = new NullWallHitDamageStrategy();
		Assert.assertEquals(1, nulo.getExtraWallDamage());
	}
	//agregar los test de la clase strategy
	/*
	@Test
	public void calcularValorDadoTest(){
		int min = 50;
		int max = 150;
		EstrategiaConcreta strategy = new EstrategiaConcreta(min, max);
		int valorDado = getDiceExtraWallDamage();
		Assert.assertTrue(valorDado >= 1 && valorDado <=6);
	}
	 */

}
