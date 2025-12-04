package robocode.control.snapshot;

import robocode.TrapEffectType;

public interface ITrapSnapshot {
	double getX();
	double getY();
	double getRadius();
	TrapEffectType getTrapEffect();
}