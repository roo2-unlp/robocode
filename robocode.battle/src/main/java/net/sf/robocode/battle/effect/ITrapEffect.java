package net.sf.robocode.battle.effect;

import net.sf.robocode.peer.IRobotPeer;

public interface ITrapEffect {
	/** Aplica el efecto al robot objetivo. */
	void apply(IRobotPeer robot);
	/** Revierte los cambios de estado aplicados por el efecto. */
	void revert(IRobotPeer robot);
	/** Obtiene la duración del efecto. */
	int getDuration();
	String getMessage();
}
