package net.sf.robocode.battle.traps;

import java.util.ArrayList;
import java.util.List;

public class TrapRepository {

	private static final List<Trap> traps = new ArrayList<>();

	public static void addTrap(Trap trap) {
		traps.add(trap);
	}

	public static List<Trap> getTraps() {
		return traps;
	}

	public static void clear() {
		traps.clear();
	}
}
