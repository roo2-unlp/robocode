package robocode.robotinterfaces.peer;

import robocode.ProximityBullet;

public interface IProximityRobotPeer extends IBasicRobotPeer {
    ProximityBullet fireProximityBullet(double power);
    ProximityBullet setFireProximityBullet(double power);
}
