package net.sf.robocode.battle.peer;

import robocode.BattleRules;
import robocode.Bullet;
import robocode.BulletMissedEvent;
import robocode.control.snapshot.BulletState;

public class StandardCollisionStrategy  implements  IWallCollisionStrategy {

	public void checkCollision(BulletPeer bullet, BattleRules battleRules) {
		if ((bullet.getX() - bullet.getRadius() <= 0) || (bullet.getY() - bullet.getRadius() <= 0) || (bullet.getX() + bullet.getRadius() >= battleRules.getBattlefieldWidth())
				|| (bullet.getY() + bullet.getRadius() >= battleRules.getBattlefieldHeight())) {
			bullet.setState(BulletState.HIT_WALL);
			bullet.setFrame(0);
			bullet.getOwner().addEvent(new BulletMissedEvent(bullet.createBullet(false))); // Bugfix #366
		}
	}

}
