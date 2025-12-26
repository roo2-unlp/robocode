package net.sf.robocode.test.robots;

import net.sf.robocode.test.helpers.RobocodeTestBed;
import org.junit.Test;
import robocode.control.events.TurnEndedEvent;
import robocode.control.snapshot.IRobotSnapshot;

/**
 * Test que verifica que el robot wrapea de derecha a izquierda
 * en mapas infinitos.
 */
public class TestInfiniteMapRealWrap extends RobocodeTestBed {

    private Double previousX = null;
    private boolean wrapped = false;

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
    public String getInitialPositions() {
        // Robot cerca del borde derecho mirando hacia la derecha (heading 0)
        return "770,300,0";
    }

    @Override
    public void onTurnEnded(TurnEndedEvent event) {
        super.onTurnEnded(event);

        IRobotSnapshot robot = event.getTurnSnapshot().getRobots()[0];
        org.junit.Assert.assertNotNull("El robot debe existir", robot);

        double x = robot.getX();

        if (previousX != null) {

            // Detecta wrap derecha -> izquierda
            boolean crossedRightToLeft =
                    previousX > 700 && x < 100;

            if (crossedRightToLeft) {
                wrapped = true;
            }
        }

        previousX = x;
    }

    @Override
    protected void runTeardown() {
        org.junit.Assert.assertTrue(
                "El robot debería wrapear al salir por el borde derecho e ingresar por el izquierdo",
                wrapped
        );
    }
}
