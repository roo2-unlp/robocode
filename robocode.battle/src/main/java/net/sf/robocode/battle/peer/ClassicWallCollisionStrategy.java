package net.sf.robocode.battle.peer;

import static java.lang.Math.abs;
import static java.lang.Math.tan;

import robocode.HitWallEvent;
import robocode.Rules;
import robocode.control.snapshot.RobotState;
/*
    Está clase mantiene todo el comportamiento inicial del mapa.
    Cada colisón con una pared reacomoda y te resta energia al robot. 
*/
public class ClassicWallCollisionStrategy implements WallCollisionStrategy {

    @Override
    public void handleWallCollision(RobotPeer robot, double adjustX, double adjustY, double angle, int minX, int minY,
            int maxX, int maxY) {
        robot.addEvent(new HitWallEvent(angle));

        // only fix both x and y values if hitting wall at an angle
        if ((robot.getBodyHeading() % (Math.PI / 2)) != 0) {
            double tanHeading = tan(robot.getBodyHeading());

            // if it hits bottom or top wall
            if (adjustX == 0) {
                adjustX = adjustY * tanHeading;
            } // if it hits a side wall
            else if (adjustY == 0) {
                adjustY = adjustX / tanHeading;
            } // if the robot hits 2 walls at the same time (rare, but just in case)
            else if (abs(adjustX / tanHeading) > abs(adjustY)) {
                adjustY = adjustX / tanHeading;
            } else if (abs(adjustY * tanHeading) > abs(adjustX)) {
                adjustX = adjustY * tanHeading;
            }
        }
        robot.setX(robot.getX() + adjustX);
        robot.setY(robot.getY() + adjustY);

        if (robot.getX() < minX) {
            robot.setX(minX);
        } else if (robot.getX() > maxX) {
            robot.setX(maxX);
        }
        if (robot.getY() < minY) {
            robot.setY(minY);
        } else if (robot.getY() > maxY) {
            robot.setY(maxY);
        }

        if (robot.getStatics().isAdvancedRobot()) {
            robot.setRobotEnergy(robot.getEnergy() - Rules.getWallHitDamage(robot.getVelocity()));
        }

        robot.updateBoundingBox();

        robot.setDistanceRemaining(0);
        robot.setVelocity(0);

        robot.setState(RobotState.HIT_WALL);
    }
}
