/*
 * Copyright (c) 2001-2025 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * https://robocode.sourceforge.io/license/epl-v10.html
 */
package net.sf.robocode.host.proxies;

import java.util.EnumMap;
import java.util.Map;

import net.sf.robocode.peer.BulletType;
import robocode.Bullet;
import robocode.ProximityBullet;

/**
 * Factory for creating Bullet API objects polymorphically.
 * 
 * This factory uses a registry pattern to map bullet types to their creators,
 * eliminating conditional checks in the firing code.
 * 
 * @author Facu
 */
public final class BulletFactory {

    /**
     * Functional interface for bullet creation.
     */
    @FunctionalInterface
    private interface BulletCreator {
        Bullet create(double heading, double x, double y, double power,
                      String ownerName, String victimName, boolean isActive,
                      int bulletId, double proximityRadius);
    }

    /**
     * Registry mapping BulletType to bullet creators.
     */
    private static final Map<BulletType, BulletCreator> BULLET_REGISTRY = new EnumMap<>(BulletType.class);

    static {
        // Register bullet creators - each type knows how to create itself
        BULLET_REGISTRY.put(BulletType.NORMAL, 
            (heading, x, y, power, ownerName, victimName, isActive, bulletId, proximityRadius) ->
                new Bullet(heading, x, y, power, ownerName, victimName, isActive, bulletId));
        
        BULLET_REGISTRY.put(BulletType.PROXIMITY,
            (heading, x, y, power, ownerName, victimName, isActive, bulletId, proximityRadius) ->
                new ProximityBullet(heading, x, y, power, ownerName, victimName, isActive, bulletId, proximityRadius));
    }

    /**
     * Creates the appropriate Bullet instance based on the bullet type.
     * Uses the registry for polymorphic creation - no conditionals.
     * 
     * @param heading the bullet heading
     * @param x the x position
     * @param y the y position
     * @param power the bullet power
     * @param ownerName the owner robot name
     * @param victimName the victim name (can be null)
     * @param isActive whether the bullet is active
     * @param bulletId the bullet ID
     * @param proximityRadius the proximity radius (only used for proximity bullets)
     * @param bulletType the type of bullet to create
     * @return the appropriate Bullet or ProximityBullet instance
     */
    public static Bullet createBullet(double heading, double x, double y, double power,
                                       String ownerName, String victimName, boolean isActive,
                                       int bulletId, double proximityRadius, BulletType bulletType) {
        return BULLET_REGISTRY.get(bulletType).create(heading, x, y, power, ownerName, victimName, isActive, bulletId, proximityRadius);
    }

    private BulletFactory() {
        // Private constructor - utility class
    }
}
