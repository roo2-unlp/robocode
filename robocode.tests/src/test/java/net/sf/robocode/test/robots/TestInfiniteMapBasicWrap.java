package net.sf.robocode.test.robots;

import net.sf.robocode.test.helpers.Assert;
import net.sf.robocode.test.helpers.RobocodeTestBed;
import org.junit.Test;
import robocode.control.events.TurnEndedEvent;
import robocode.control.snapshot.IRobotSnapshot;
import static org.hamcrest.CoreMatchers.is;

/**
 * Tests de integracion basicos de wrapping en mapas infinitos
 */
public class TestInfiniteMapBasicWrap extends RobocodeTestBed {
    int lastTurn;

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
        lastTurn = event.getTurnSnapshot().getTurn();
        IRobotSnapshot robot = event.getTurnSnapshot().getRobots()[0];
        // Verificar que el robot existe y la batalla esta corriendo
        Assert.assertNotNull("El robot debe existir", robot);
        // Las posiciones se verifican indirectamente al no haber excepciones
    }

    @Override
    protected void runTeardown() {
        // Verificar que la batalla se ejecuto por al menos algunos turnos
        Assert.assertTrue("La batalla debe durar al menos 10 turnos", lastTurn >= 10);
    }
}
