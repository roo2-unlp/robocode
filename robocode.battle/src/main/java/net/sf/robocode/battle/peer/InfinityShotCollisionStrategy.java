package net.sf.robocode.battle.peer;

import robocode.BattleRules;

public class InfinityShotCollisionStrategy implements  IWallCollisionStrategy{
	@Override
	public void checkCollision(BulletPeer bullet, BattleRules battleRules) {
		double width = battleRules.getBattlefieldWidth();
		double height = battleRules.getBattlefieldHeight();
		boolean wrapped = false;

		if (bullet.getX() <= 0) {
			bullet.setX(width);
			wrapped = true;
		} else if (bullet.getX() >= width) {
			bullet.setX(0);
			wrapped = true;
		}

		if (bullet.getY() <= 0) {
			bullet.setY(height);
			wrapped = true;
		} else if (bullet.getY() >= height) {
			bullet.setY(0);
			wrapped = true;
		}

		if (wrapped) {
			double x = bullet.getX();
			double y = bullet.getY();
			bullet.setLastX(x);
			bullet.setLastY(y);
			bullet.getBoundingLine().setLine(x,y,x,y);
		}
	}
}