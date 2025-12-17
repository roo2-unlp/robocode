package net.sf.robocode.battle;

import net.sf.robocode.io.Logger;
import java.util.Random;

public class RandomWallHitDamageStrategy implements WallHitDamageStrategy {
	private int intervaloDado;
	private int tiempoTranscurrido;
	private final Random dadoRandom = new Random();
	private int minRandom;
	private int maxRandom;
	private int extraWallDamage;

	public RandomWallHitDamageStrategy(int minRandom, int maxRandom) {
		this.minRandom = minRandom;
		this.maxRandom = maxRandom;
	}

	@Override
	public int getExtraWallDamage() {
		tiempoTranscurrido++;
		if (tiempoTranscurrido >= intervaloDado){
			//aca tiro los dados
			tiempoTranscurrido = 0;
			intervaloDado = rangoRandom(dadoRandom, minRandom, maxRandom);
			extraWallDamage = getDiceExtraWallDamage();
			//if (!RobocodeProperties.isTestingOn()){
			Logger.logMessage("este es el intervalo de tiempo " + intervaloDado);
			Logger.logMessage("este es el valor del dado " + extraWallDamage);
			//}
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