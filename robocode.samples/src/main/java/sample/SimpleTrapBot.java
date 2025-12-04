package sample;

import robocode.*;
import java.util.*;

public class SimpleTrapBot extends AdvancedRobot {
    
    private List<Trampa> trampasConocidas = new ArrayList<>();
    
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
            ahead(100);
            turnRight(30);
        }
    }
    
    private boolean esPosicionSegura(double x, double y) {
        for (Trampa trampa : trampasConocidas) {
            if (trampa.estaCerca(x, y, 50)) {
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
            if (trampa.estaCerca(trampaX, trampaY, 30)) {
                yaLaTengo = true;
                break;
            }
        }
        
        if (!yaLaTengo) {
            trampasConocidas.add(new Trampa(trampaX, trampaY, radio));
            out.println("Trampa " + trampasConocidas.size());
        }
        
        back(80);
        turnRight(180);
        ahead(100);
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
            turnRight(90);
            super.ahead(50);
        }
    }
}