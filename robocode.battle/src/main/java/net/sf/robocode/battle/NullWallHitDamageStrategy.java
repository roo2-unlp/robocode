package net.sf.robocode.battle;

public class NullWallHitDamageStrategy implements WallHitDamageStrategy{
	@Override
	public int getExtraWallDamage() {
		return 1;
	}
	public int getDiceExtraWallDamage(){return 1;}
	public void initializeRound(){ return; }
}
