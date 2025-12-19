package net.sf.robocode.test.robots;

import net.sf.robocode.test.helpers.RobocodeTestBed;
import org.junit.Test;
import robocode.control.events.TurnEndedEvent;
import robocode.control.snapshot.IRobotSnapshot;

/**
 * Tests de integracion de consistencia en mapas infinitos
 */
public class TestInfiniteMapConsistency extends RobocodeTestBed {

    @Test
    public void run() {
        super.run();
    }

    @Override
    public String getRobotName() {
        return "sample.Crazy";
    }

    @Override
    public String getEnemyName() {
        return "sample.Target";
    }

    @Override
    protected void beforeInit() {
        super.beforeInit();
        System.setProperty("robocode.battle.infiniteMap", "true");
    }

    @Override
    public void onTurnEnded(TurnEndedEvent event) {
        super.onTurnEnded(event);
        IRobotSnapshot robot = event.getTurnSnapshot().getRobots()[0];

        // Verificar que el robot existe
        org.junit.Assert.assertNotNull("El robot debe existir", robot);
    }
}
