/*
 * Copyright (c) 2001-2025 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * https://robocode.sourceforge.io/license/epl-v10.html
 */
package net.sf.robocode.battle.peer;

import java.util.List;

/**
 * Strategy interface for bullet-to-robot collision detection.
 * 
 * Implementations define how a bullet detects and handles collisions with robots.
 * This allows different bullet types (normal, proximity, etc.) to have different
 * collision behaviors without conditional logic in the bullet classes.
 * 
 * @author Facu
 */
public interface IBulletCollisionStrategy {

    /**
     * Checks for collisions between the bullet and robots.
     * 
     * Normal bullets return a single robot (or empty list), while proximity bullets
     * may return multiple robots when they detonate, dealing area damage to all
     * robots within the blast radius.
     * 
     * @param bullet the bullet peer performing the collision check
     * @param robots the list of robots to check collision against
     * @return a list of robots that were hit (may be empty, single, or multiple)
     */
    List<RobotPeer> checkRobotCollision(BulletPeer bullet, List<RobotPeer> robots);

    /**
     * Called when a bullet impacts a robot. Implementations may modify
     * the impact behavior (e.g., adjust damage based on distance for proximity bullets).
     * 
     * @param bullet the bullet peer that hit the robot
     * @param robot the robot that was hit
     */
    void handleImpact(BulletPeer bullet, RobotPeer robot);

    /**
     * Gets the adjusted power for this bullet's impact.
     * Normal bullets return the original power; proximity bullets may scale based on distance.
     * 
     * @param bullet the bullet peer
     * @param robot the robot being impacted
     * @return the adjusted power value
     */
    double getAdjustedPower(BulletPeer bullet, RobotPeer robot);

    /**
     * Configures a bullet peer with strategy-specific settings from the command.
     * Each strategy implementation knows what configuration it needs.
     * 
     * @param bullet the bullet peer to configure
     * @param proximityRadius the proximity radius from the command
     */
    void configureBullet(BulletPeer bullet, double proximityRadius);
}
