package net.sf.robocode.battle.peer;

/*
    Interfaz que define la firma para las distintas estrategias de resolución de colisiones con paredes   
*/

public interface WallCollisionStrategy {
    void handleWallCollision(RobotPeer robot, double adjustX, double adjustY, double angle, int minX, int minY, int maxX, int maxY);
}
