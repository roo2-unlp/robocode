/*
 * Copyright (c) 2001-2025 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * https://robocode.sourceforge.io/license/epl-v10.html
 */
package net.sf.robocode.battle.peer;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

import net.sf.robocode.peer.BulletCommand;
import net.sf.robocode.peer.BulletType;

/**
 * Factory for creating bullet collision strategies based on bullet commands.
 * 
 * This factory uses a registry pattern to map bullet types to their strategies,
 * eliminating conditional checks in the bullet creation code.
 * 
 * @author Facu
 */
public final class BulletCollisionStrategyFactory {

    /**
     * Registry mapping BulletType enum to strategy suppliers.
     * Uses EnumMap for efficient lookup by bullet type.
     */
    private static final Map<BulletType, Supplier<IBulletCollisionStrategy>> STRATEGY_REGISTRY = new EnumMap<>(BulletType.class);

    static {
        // Register strategy suppliers - each bullet type maps to its strategy creator
        STRATEGY_REGISTRY.put(BulletType.NORMAL, () -> NormalBulletCollisionStrategy.getInstance());
        STRATEGY_REGISTRY.put(BulletType.PROXIMITY, ProximityBulletCollisionStrategy::new);
    }

    /**
     * Creates the appropriate collision strategy for a bullet command.
     * Uses the registry to lookup the strategy supplier by bullet type - no conditionals.
     * 
     * @param bulletCmd the bullet command containing bullet configuration
     * @return the appropriate collision strategy for this bullet type
     */
    public static IBulletCollisionStrategy createStrategy(BulletCommand bulletCmd) {
        return STRATEGY_REGISTRY.get(bulletCmd.getBulletType()).get();
    }

    /**
     * Configures a bullet peer based on the bullet command.
     * Delegates to the bullet's collision strategy for polymorphic configuration.
     * 
     * @param bullet the bullet peer to configure
     * @param bulletCmd the bullet command with configuration
     */
    public static void configureBullet(BulletPeer bullet, BulletCommand bulletCmd) {
        // Polymorphic configuration - each strategy knows what it needs
        bullet.getCollisionStrategy().configureBullet(bullet, bulletCmd.getProximityRadius());
    }

    private BulletCollisionStrategyFactory() {
        // Private constructor - utility class
    }
}
