package net.sf.robocode.battle.peer;

public interface WallCollisionStrategy {
    void handleWallCollision(RobotPeer robot, double adjustX, double adjustY, double angle, int minX, int minY, int maxX, int maxY);
}
