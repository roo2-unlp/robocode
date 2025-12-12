package net.sf.robocode.battle.peer;

import robocode.BattleRules;
import robocode.BulletMissedEvent;
import robocode.control.snapshot.BulletState;

public class InfinityShotCollisionStrategy implements IWallCollisionStrategy {

	private int count = 0;

	@Override
	public void checkCollision(BulletPeer bullet, BattleRules battleRules) {
		double width = battleRules.getBattlefieldWidth();
		double height = battleRules.getBattlefieldHeight();
		int MAX_BOUNCES = battleRules.getInfinityShotLaps();
		boolean isAlive = count < MAX_BOUNCES;

		boolean hitX = updateHorizontalPosition(bullet, height, width, isAlive);
		boolean hitY = updateVerticalPosition(bullet, height, width, isAlive);

		if (hitX || hitY) {
			handleImpact(bullet, isAlive);
		}
	}

	private boolean updateHorizontalPosition(BulletPeer bullet, double height, double width, boolean isAlive) {
		double x = bullet.getX();
		if (x <= 0) {
			bullet.setX(isAlive ? width : 0);
			return true;
		} else if (x >= width) {
			bullet.setX(isAlive ? 0 : width);
			return true;
		}
		return false;
	}

	private boolean updateVerticalPosition(BulletPeer bullet, double height, double width, boolean isAlive) {
		double y = bullet.getY();
		if (y <= 0) {
			bullet.setY(isAlive ? height : 0);
			return true;
		} else if (y >= height) {
			bullet.setY(isAlive ? 0 : height);
			return true;
		}
		return false;
	}

	private void handleImpact(BulletPeer bullet, boolean isAlive) {
		count++;
		if (isAlive) {
			teleportBullet(bullet);
		} else {
			killBullet(bullet);
		}
	}

	private void teleportBullet(BulletPeer bullet) {
		double x = bullet.getX();
		double y = bullet.getY();
		bullet.setLastX(x);
		bullet.setLastY(y);
		bullet.getBoundingLine().setLine(x, y, x, y);
	}

	private void killBullet(BulletPeer bullet) {
		bullet.setState(BulletState.HIT_WALL);
		bullet.setFrame(0);
		bullet.getOwner().addEvent(new BulletMissedEvent(bullet.createBullet(false)));
	}
}