package net.sf.robocode.battle.peer;

import robocode.BattleRules;

public interface IWallCollisionStrategy {
	void checkCollision(BulletPeer bullet, BattleRules battleRules);
}
