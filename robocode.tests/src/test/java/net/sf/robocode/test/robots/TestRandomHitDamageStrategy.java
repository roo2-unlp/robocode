package net.sf.robocode.test.robots;

import net.sf.robocode.battle.NullWallHitDamageStrategy;
import net.sf.robocode.battle.RandomWallHitDamageStrategy;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class TestRandomHitDamageStrategy {
	// Test de Null Strategy
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
	// Test de Random Strategy
	@Test
	public void RangodeDao1a6Test(){
		RandomWallHitDamageStrategy strategy = new RandomWallHitDamageStrategy(1,1);
		strategy.initializeRound();
		Assert.assertTrue(strategy.getDiceExtraWallDamage() >= 1 && strategy.getDiceExtraWallDamage() <= 6);
	}
	@Test
	public void CambioDeDadoIntervaloTest(){
		RandomWallHitDamageStrategy strategy = new RandomWallHitDamageStrategy(1, 1);
		strategy.initializeRound();

		int valor1 = strategy.getExtraWallDamage();
		int valor2 = strategy.getExtraWallDamage();

		Assert.assertTrue(valor1 >= 1 && valor1 <= 6);
		Assert.assertTrue(valor2 >= 1 && valor2 <= 6);
	}
	@Test
	public void MinyMaxIgualTest(){
		RandomWallHitDamageStrategy strategy = new RandomWallHitDamageStrategy(3,3);
		strategy.initializeRound();
		Assert.assertEquals(3,strategy.getExtraWallDamage());
	}

}