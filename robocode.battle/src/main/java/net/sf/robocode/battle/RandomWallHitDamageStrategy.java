package net.sf.robocode.battle;

import net.sf.robocode.io.Logger;
import java.util.Random;

public class RandomWallHitDamageStrategy implements WallHitDamageStrategy {
	private int intervaloDado;
	private int tiempoTranscurrido;
	private final Random dadoRandom;
	private int minRandom;
	private int maxRandom;
	private int extraWallDamage;

	public RandomWallHitDamageStrategy(int minRandom, int maxRandom) {
		this.minRandom = minRandom;
		this.maxRandom = maxRandom;
		this.dadoRandom = new Random();
	}

	public RandomWallHitDamageStrategy(int minRandom, int maxRandom, int seed) {
		this.minRandom = minRandom;
		this.maxRandom = maxRandom;
		this.dadoRandom = new Random(seed);
	}

	@Override
	public int getExtraWallDamage() {
		tiempoTranscurrido++;
		if (tiempoTranscurrido >= intervaloDado){
			tiempoTranscurrido = 0;
			intervaloDado = rangoRandom(dadoRandom, minRandom, maxRandom);
			extraWallDamage = getDiceExtraWallDamage();
		}
		return extraWallDamage;
	}

	@Override
	public int getDiceExtraWallDamage() {
		return  dadoRandom.nextInt(6) + 1;
	}
	public int rangoRandom(Random random, int min, int max){
		return (min == max) ? min : min + random.nextInt((max - min) + 1);
	}
	@Override
	public void initializeRound(){
		this.tiempoTranscurrido = 0;
		this.intervaloDado = rangoRandom(dadoRandom, minRandom, maxRandom);
		this.extraWallDamage = getDiceExtraWallDamage();
	}
}