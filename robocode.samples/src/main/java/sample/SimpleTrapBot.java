package sample;

import robocode.*;
import java.util.*;

public class SimpleTrapBot extends AdvancedRobot {
    
    private List<Trampa> trampasConocidas = new ArrayList<>();
    private static final double ROBOT_RADIUS =20.0;
    private static final double SAFETY_MARGIN = 45.0; 
    private static final double DETECTION_RADIUS = 100.0; 
    private int movimientosAtascados = 0;
    
    private static class Trampa {
        double x, y, radio;
        
        Trampa(double x, double y, double radio) {
            this.x = x;
            this.y = y;
            this.radio = radio;
        }
        
        boolean estaCerca(double px, double py, double margen) {
            double dx = px - x;
            double dy = py - y;
            double distancia = Math.sqrt(dx * dx + dy * dy);
            return distancia <= radio + margen;
        }
    }
    
    public void run() {
        while (true) {
            navegarConDeteccionPredictiva();
        }
    }

    private void navegarConDeteccionPredictiva() {
        double miX = getX();
        double miY = getY();
        double miAngulo = getHeading();
        
        boolean hayPeligroAdelante = detectarPeligroEnSemicirculo(miX, miY, miAngulo);
        
        if (!hayPeligroAdelante) {
            super.ahead(100);
            turnRight(30);
            movimientosAtascados = 0;
        } else {
            ejecutarManiobraprobablesEvasiva(miX, miY, miAngulo);
        }
    }
    
    private boolean detectarPeligroEnSemicirculo(double robotX, double robotY, double robotAngulo) {
        if (trampasConocidas.isEmpty()) {
            return false;
        }
        
        double anguloRad = Math.toRadians(robotAngulo);
        
        for (int i = -10; i <= 10; i++) {
            double anguloVerificacion = anguloRad + Math.toRadians(i * 9);

            for (double distancia = 15; distancia <= DETECTION_RADIUS; distancia += 15) {
                double puntoX = robotX + Math.cos(anguloVerificacion) * distancia;
                double puntoY = robotY + Math.sin(anguloVerificacion) * distancia;

                for (Trampa trampa : trampasConocidas) {
                    double dx = puntoX - trampa.x;
                    double dy = puntoY - trampa.y;
                    double distanciaTrampa = Math.sqrt(dx * dx + dy * dy);

                    if (distanciaTrampa <= trampa.radio + ROBOT_RADIUS + SAFETY_MARGIN) {
                        return true;
                    }
                }
            }
        }
        
        return false;
    }    private void ejecutarManiobraprobablesEvasiva(double robotX, double robotY, double robotAngulo) {
        boolean puedeIrIzquierda = esDireccionSegura(robotX, robotY, robotAngulo - 90);
        boolean puedeIrDerecha = esDireccionSegura(robotX, robotY, robotAngulo + 90);
        
        if (puedeIrIzquierda && puedeIrDerecha) {
            if (Math.random() < 0.5) {
                turnLeft(90);
            } else {
                turnRight(90);
            }
            super.ahead(60);
            movimientosAtascados = 0;
        } else if (puedeIrIzquierda) {
            turnLeft(90);
            super.ahead(60);
            movimientosAtascados = 0;
        } else if (puedeIrDerecha) {
            turnRight(90);
            super.ahead(60);
            movimientosAtascados = 0;
        } else {
            ejecutarRetroceso();
        }
    }
    
    private boolean esDireccionSegura(double robotX, double robotY, double angulo) {
        double anguloRad = Math.toRadians(angulo);
        double distanciaVerificacion = 90;
        
        double destinoX = robotX + Math.cos(anguloRad) * distanciaVerificacion;
        double destinoY = robotY + Math.sin(anguloRad) * distanciaVerificacion;
        
        if (destinoX < ROBOT_RADIUS + 15 || destinoX > getBattleFieldWidth() - ROBOT_RADIUS - 15 ||
            destinoY < ROBOT_RADIUS + 15 || destinoY > getBattleFieldHeight() - ROBOT_RADIUS - 15) {
            return false;
        }
        
        for (Trampa trampa : trampasConocidas) {
            for (double t = 0.1; t <= 1.0; t += 0.1) {
                double puntoX = robotX + (destinoX - robotX) * t;
                double puntoY = robotY + (destinoY - robotY) * t;

                double dx = puntoX - trampa.x;
                double dy = puntoY - trampa.y;
                double distancia = Math.sqrt(dx * dx + dy * dy);

                if (distancia <= trampa.radio + ROBOT_RADIUS + SAFETY_MARGIN) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    private void ejecutarRetroceso() {
        movimientosAtascados++;
        
        if (movimientosAtascados > 3) {
            out.println("Ejecutando maniobra de emergencia...");
            back(120);
            turnRight(135 + Math.random() * 90);
            movimientosAtascados = 0;
        } else {
            back(80);
            double mejorAngulo = encontrarMejorDireccionLibre();
            double diferencia = mejorAngulo - getHeading();
            
            while (diferencia > 180) diferencia -= 360;
            while (diferencia < -180) diferencia += 360;
            
            if (diferencia > 0) {
                turnRight(diferencia);
            } else {
                turnLeft(-diferencia);
            }
        }
    }    private double encontrarMejorDireccionLibre() {
        double robotX = getX();
        double robotY = getY();
        double mejorAngulo = getHeading();
        double mayorDistanciaLibre = 0;
        
        for (int i = 0; i < 36; i++) {
            double angulo = i * 10;
            double distanciaLibre = calcularDistanciaLibre(robotX, robotY, angulo);

            if (distanciaLibre > mayorDistanciaLibre) {
                mayorDistanciaLibre = distanciaLibre;
                mejorAngulo = angulo;
            }
        }
        
        return mejorAngulo;
    }
    
    private double calcularDistanciaLibre(double robotX, double robotY, double angulo) {
        double anguloRad = Math.toRadians(angulo);
        double maxDistancia = 150;
        
        for (double distancia = 15; distancia <= maxDistancia; distancia += 15) {
            double puntoX = robotX + Math.cos(anguloRad) * distancia;
            double puntoY = robotY + Math.sin(anguloRad) * distancia;

            if (puntoX < ROBOT_RADIUS + 10 || puntoX > getBattleFieldWidth() - ROBOT_RADIUS - 10 ||
                puntoY < ROBOT_RADIUS + 10 || puntoY > getBattleFieldHeight() - ROBOT_RADIUS - 10) {
                return distancia - 15;
            }

            for (Trampa trampa : trampasConocidas) {
                double dx = puntoX - trampa.x;
                double dy = puntoY - trampa.y;
                double distanciaTrampa = Math.sqrt(dx * dx + dy * dy);

                if (distanciaTrampa <= trampa.radio + ROBOT_RADIUS + SAFETY_MARGIN) {
                    return distancia - 15;
                }
            }
        }
        
        return maxDistancia;
    }
    
    private boolean esPosicionSegura(double x, double y) {
        for (Trampa trampa : trampasConocidas) {
            if (trampa.estaCerca(x, y, ROBOT_RADIUS + SAFETY_MARGIN)) {
                return false;
            }
        }
        return true;
    }
    
    public void onHitTrap(HitTrapEvent evento) {
        double trampaX = evento.getTrapX();
        double trampaY = evento.getTrapY();
        double radio = evento.getRadius();

        boolean yaLaTengo = false;
        for (Trampa trampa : trampasConocidas) {
            if (trampa.estaCerca(trampaX, trampaY, radio * 0.3)) {
                yaLaTengo = true;
                break;
            }
        }

        if (!yaLaTengo) {
            trampasConocidas.add(new Trampa(trampaX, trampaY, radio));
            out.println("Nueva trampa detectada #" + trampasConocidas.size() + 
                       " en (" + (int)trampaX + "," + (int)trampaY + ") radio=" + (int)radio);
        } else {
            out.println("PISO TRAMPA CONOCIDA en (" + (int)trampaX + "," + (int)trampaY + ") - Fallo en navegacion!");
        }

        double miX = getX();
        double miY = getY();
        double dx = miX - trampaX;
        double dy = miY - trampaY;
        
        double distanciaAlCentro = Math.sqrt(dx * dx + dy * dy);
        double anguloEscape;
        
        if (distanciaAlCentro < radio + ROBOT_RADIUS) {
            anguloEscape = Math.random() * 360;
        } else {
            anguloEscape = Math.toDegrees(Math.atan2(dy, dx));
        }
        
        double diferenciaAngulo = anguloEscape - getHeading();
        while (diferenciaAngulo > 180) diferenciaAngulo -= 360;
        while (diferenciaAngulo < -180) diferenciaAngulo += 360;
        
        if (diferenciaAngulo > 0) {
            turnRight(diferenciaAngulo);
        } else {
            turnLeft(-diferenciaAngulo);
        }
        
        double distanciaEscape = Math.max(120, radio * 3 + SAFETY_MARGIN);
        super.ahead(distanciaEscape);
    }

    public void onHitWall(HitWallEvent e) {
        back(100);
        turnRight(180);
    }
    
    public void onScannedRobot(ScannedRobotEvent e) {
        fire(1);
    }
    
    public void ahead(double distance) {
        double miX = getX();
        double miY = getY();
        double rad = Math.toRadians(getHeading());
        double destinoX = miX + Math.cos(rad) * distance;
        double destinoY = miY + Math.sin(rad) * distance;

        if (esPosicionSegura(destinoX, destinoY)) {
            super.ahead(distance);
        } else {
            boolean puedeIzquierda = esDireccionSegura(miX, miY, getHeading() - 45);
            boolean puedeDerecha = esDireccionSegura(miX, miY, getHeading() + 45);

            if (puedeIzquierda) {
                turnLeft(45);
                super.ahead(Math.min(distance, 50));
            } else if (puedeDerecha) {
                turnRight(45);
                super.ahead(Math.min(distance, 50));
            } else {
                back(30);
            }
        }
    }
}