package sample;

import robocode.*;

public class Power3TestShooter extends AdvancedRobot {

    private boolean enemyFound = false;
    private double lastBearing;

    @Override
    public void run() {
        setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);

        // Escaneo inicial
        while (!enemyFound) {
            turnRadarRight(360);  // Giro completo hasta encontrar algo
        }

        // Una vez encontrado: SOLO disparar hacia esa última posición
        while (true) {
            apuntarYDisparar();
            execute();
        }
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent e) {
        if (!enemyFound) {
            enemyFound = true;

            // Guardamos datos del primer y ÚNICO escaneo
            lastBearing = e.getBearing();
        }
    }

    private void apuntarYDisparar() {
        // Apuntamos el cañón al ángulo original donde lo vimos
        double gunTurn = normalizeBearing(getHeading() + lastBearing - getGunHeading());
        turnGunRight(gunTurn);

        // Disparamos siempre a potencia fija (podés cambiar 3 por lo que quieras)
        fire(3);
    }

    private double normalizeBearing(double angle) {
        while (angle > 180) angle -= 360;
        while (angle < -180) angle += 360;
        return angle;
    }
}
