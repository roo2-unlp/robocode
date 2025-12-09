package net.sf.robocode.battle.effect;

import net.sf.robocode.battle.peer.RobotPeer;

public interface ITrapEffect {
	/** Aplica el efecto al robot objetivo. */
	void apply(RobotPeer robot);
	/** Revierte los cambios de estado aplicados por el efecto. */
	void revert(RobotPeer robot);
	/** Obtiene la duración del efecto. */
	int getDuration();
	String getMessage();
}
