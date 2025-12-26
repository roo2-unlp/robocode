/*
 * Copyright (c) 2001-2025 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * https://robocode.sourceforge.io/license/epl-v10.html
 */
package net.sf.robocode.battle.peer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Proximity bullet collision strategy using radius-based detection with area damage.
 * 
 * This strategy detects collision when a robot enters the bullet's proximity radius.
 * When detonation is triggered (any robot enters the radius), ALL robots currently
 * inside the radius receive damage (area damage effect).
 * 
 * The damage is scaled based on the distance from the bullet center to each robot,
 * with robots closer to the bullet receiving more damage.
 * 
 * Unlike normal bullets, proximity bullets track which robots are inside their radius
 * to only trigger detonation on entry (not while robots stay inside).
 * 
 * @author Facu
 */
public class ProximityBulletCollisionStrategy implements IBulletCollisionStrategy {

    // Track robots that were inside the proximity radius in the previous frame
    // Each proximity bullet needs its own instance of this strategy to maintain state
    private final Set<RobotPeer> robotsInsideRadius = new HashSet<RobotPeer>();

    /**
     * Creates a new proximity collision strategy.
     * Each proximity bullet should have its own strategy instance to track state.
     */
    public ProximityBulletCollisionStrategy() {
        // Each proximity bullet gets its own stateful strategy
    }

    @Override
    public List<RobotPeer> checkRobotCollision(BulletPeer bullet, List<RobotPeer> robots) {
        double proximityRadius = bullet.getProximityRadius();
        if (proximityRadius <= 0) {
            return Collections.emptyList();
        }

        // Track which robots are currently inside the radius
        Set<RobotPeer> currentlyInside = new HashSet<RobotPeer>();
        boolean shouldDetonate = false;

        for (RobotPeer robot : robots) {
            if (robot == null || robot == bullet.getOwner() || robot.isDead()) {
                continue;
            }

            boolean isInside = bullet.intersectsProximityRadius(robot);

            if (isInside) {
                currentlyInside.add(robot);

                // Trigger detonation if any robot just entered the radius (wasn't inside before)
                if (!robotsInsideRadius.contains(robot)) {
                    shouldDetonate = true;
                }
            }
        }

        // Update the set of robots inside the radius for the next frame
        robotsInsideRadius.clear();
        robotsInsideRadius.addAll(currentlyInside);

        // If detonation triggered, return ALL robots in the blast radius for area damage
        if (shouldDetonate && !currentlyInside.isEmpty()) {
            return new ArrayList<RobotPeer>(currentlyInside);
        }

        return Collections.emptyList();
    }

    @Override
    public void handleImpact(BulletPeer bullet, RobotPeer robot) {
        // Proximity bullets adjust their power based on distance before impact
        // This is handled by getAdjustedPower() being called before damage calculation
    }

    @Override
    public double getAdjustedPower(BulletPeer bullet, RobotPeer robot) {
        double proximityRadius = bullet.getProximityRadius();
        if (proximityRadius <= 0) {
            return bullet.getPower();
        }

        double dx = robot.getX() - bullet.getX();
        double dy = robot.getY() - bullet.getY();
        double distance = Math.hypot(dx, dy);
        double normalized = Math.min(Math.max(distance, 0), proximityRadius) / proximityRadius;
        
        // Impact factor: 90% at center, scaling down to 10% at edge
        double impactFactor = 0.90 - 0.80 * normalized;

        return bullet.getPower() * impactFactor;
    }

    @Override
    public void configureBullet(BulletPeer bullet, double proximityRadius) {
        // Proximity bullets need the proximity radius configured
        bullet.setProximityRadius(proximityRadius);
    }

    /**
     * Clears the tracked robots. Called when the bullet is reset or recycled.
     */
    public void reset() {
        robotsInsideRadius.clear();
    }
}
