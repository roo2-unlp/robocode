/*
 * Copyright (c) 2001-2025 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * https://robocode.sourceforge.io/license/epl-v10.html
 */
package net.sf.robocode.battle.peer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import robocode.BattleRules;
import robocode.ProximityBullet;

/**
 * @author Facu
 */
public class ProximityBulletPeer extends BulletPeer {
  private double currentRadius;
  // Track robots that were inside the proximity radius in the previous frame
  private final Set<RobotPeer> robotsInsideRadius = new HashSet<RobotPeer>();

  public ProximityBulletPeer(RobotPeer owner, BattleRules battleRules, int bulletId) {
    super(owner, battleRules, bulletId);
    // Use a larger radius to match the ProximityBullet creation in
    // BasicRobotProxy
    // and ensure better detection
    this.currentRadius = 0; // Default proximity radius
  }

  @Override
  public void update(List<RobotPeer> robots, List<BulletPeer> bullets) {
    super.update(robots, bullets);
  }

  @Override
  protected void checkRobotCollision(List<RobotPeer> robots) {
    // For Proximity bullets, we use proximity detection instead of normal
    // collision
    // This method is called from super.update(), so we only check proximity
    triggerProximityHits(robots);
  }

  @Override
  protected ProximityBullet instantiateBullet(String ownerName, String victimName, boolean isActive) {
    return new ProximityBullet(
        getHeading(),
        getX(),
        getY(),
        getPower(),
        ownerName,
        victimName,
        isActive,
        getBulletId(),
        currentRadius);
  }

  @Override
  public double getProximityRadius() {
    return currentRadius;
  }

	public void setProximityRadius(double radius) {
		this.currentRadius = radius;
	}


	@Override
  protected void handleRobotImpact(RobotPeer otherRobot) {
    // Para balas de proximidad, calculamos el factor de impacto basado en la
    // distancia
    double impactFactor = computeImpactFactor(otherRobot);
    double adjustedPower = getPower() * impactFactor;

    // Establecer el poder ajustado antes de llamar al método padre
    setPower(adjustedPower);

    // Detener la bala en su posición actual antes de impactar
    // Esto asegura que la bala no siga moviéndose después de explotar
    // (lastX y lastY ya están actualizados por updateMovement)

    // Ahora usar el comportamiento estándar que maneja el daño, eventos, etc.
    // Esto también establecerá el estado a HIT_VICTIM y detendrá la bala
    super.handleRobotImpact(otherRobot);
  }

  private void triggerProximityHits(List<RobotPeer> robots) {
    if (currentRadius <= 0) {
      return;
    }

    // Track which robots are currently inside the radius
    Set<RobotPeer> currentlyInside = new HashSet<RobotPeer>();

    for (RobotPeer robot : robots) {
      if (robot == null || robot == getOwner() || robot.isDead()) {
        continue;
      }

      boolean isInside = intersectsProximityRadius(robot);

      if (isInside) {
        currentlyInside.add(robot);

        // Only impact if the robot just entered the radius (wasn't inside before)
        if (!robotsInsideRadius.contains(robot)) {
          handleRobotImpact(robot);
          break; // Only impact one robot per frame
        }
      }
    }

    // Update the set of robots inside the radius for the next frame
    robotsInsideRadius.clear();
    robotsInsideRadius.addAll(currentlyInside);
  }

  private double computeImpactFactor(RobotPeer robot) {
    if (currentRadius <= 0) {
      return 0;
    }
    double dx = robot.getX() - getX();
    double dy = robot.getY() - getY();
    double distance = Math.hypot(dx, dy);
    double normalized = Math.min(Math.max(distance, 0), currentRadius) / currentRadius;
	double factor = 0.90 - 0.80 * normalized;

    return factor;
  }
}
