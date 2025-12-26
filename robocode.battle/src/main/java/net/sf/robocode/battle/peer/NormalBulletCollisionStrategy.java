/*
 * Copyright (c) 2001-2025 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * https://robocode.sourceforge.io/license/epl-v10.html
 */
package net.sf.robocode.battle.peer;

import java.util.Collections;
import java.util.List;

/**
 * Normal bullet collision strategy using line intersection detection.
 * 
 * This strategy preserves the original Robocode collision behavior for retrocompatibility.
 * A bullet collides with a robot when its movement line (from last position to current position)
 * intersects the robot's bounding box.
 * 
 * @author Facu
 */
public class NormalBulletCollisionStrategy implements IBulletCollisionStrategy {

    private static final NormalBulletCollisionStrategy INSTANCE = new NormalBulletCollisionStrategy();

    /**
     * Returns the singleton instance of this strategy.
     * Normal bullets are stateless, so a single instance can be shared.
     */
    public static NormalBulletCollisionStrategy getInstance() {
        return INSTANCE;
    }

    private NormalBulletCollisionStrategy() {
        // Private constructor for singleton
    }

    @Override
    public List<RobotPeer> checkRobotCollision(BulletPeer bullet, List<RobotPeer> robots) {
        for (RobotPeer otherRobot : robots) {
            if (!(otherRobot == null || otherRobot == bullet.getOwner() || otherRobot.isDead())
                    && otherRobot.getBoundingBox().intersectsLine(bullet.getBoundingLine())) {
                // Normal bullets hit only one robot
                return Collections.singletonList(otherRobot);
            }
        }
        return Collections.emptyList();
    }

    @Override
    public void handleImpact(BulletPeer bullet, RobotPeer robot) {
        // Normal bullets use default impact handling - no special behavior needed
        // The BulletPeer.handleRobotImpact() method handles the standard logic
    }

    @Override
    public double getAdjustedPower(BulletPeer bullet, RobotPeer robot) {
        // Normal bullets always use their full power
        return bullet.getPower();
    }

    @Override
    public void configureBullet(BulletPeer bullet, double proximityRadius) {
        // Normal bullets don't need any special configuration
    }
}
