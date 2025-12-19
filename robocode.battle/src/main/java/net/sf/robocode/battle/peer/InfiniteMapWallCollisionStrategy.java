package net.sf.robocode.battle.peer;

public class InfiniteMapWallCollisionStrategy implements WallCollisionStrategy {

    @Override
    public void handleWallCollision(RobotPeer robot, double adjustX, double adjustY, double angle, int minX, int minY,
            int maxX, int maxY) {
        double fieldWidth = robot.getBattleFieldWidth();
        double fieldHeight = robot.getBattleFieldHeight();
        double currentX = robot.getX();
        double currentY = robot.getY();

        if (currentX < minX) {
            robot.setX(currentX + fieldWidth);
        } else if (currentX > maxX) {
            robot.setX(currentX - fieldWidth);
        } else {
            robot.setX(currentX);
        }

        if (currentY < minY) {
            robot.setY(currentY + fieldHeight);
        } else if (currentY > maxY) {
            robot.setY(currentY - fieldHeight);
        } else {
            robot.setY(currentY);
        }

        currentX = robot.getX();
        currentY = robot.getY();

        if (fieldWidth > 0) {
            robot.setX(minX + ((currentX - minX) % fieldWidth + fieldWidth) % fieldWidth);
        }

        if (fieldHeight > 0) {
            robot.setY(minY + ((currentY - minY) % fieldHeight + fieldHeight) % fieldHeight);
        }

        robot.updateBoundingBox();
    }
}
