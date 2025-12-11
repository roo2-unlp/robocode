package robocode.robotinterfaces.peer;

import robocode.RadioactiveBullet;

public interface IRadioactiveRobotPeer extends IBasicRobotPeer {
    RadioactiveBullet fireRadioactiveBullet(double power, double radius);
}
