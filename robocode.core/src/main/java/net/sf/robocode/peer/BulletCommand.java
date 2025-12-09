/*
 * Copyright (c) 2001-2025 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * https://robocode.sourceforge.io/license/epl-v10.html
 */
package net.sf.robocode.peer;


import net.sf.robocode.serialization.ISerializableHelper;
import net.sf.robocode.serialization.RbSerializer;
import robocode.BattleRules;
import net.sf.robocode.battle.peer.BulletPeer;

import java.io.Serializable;
import java.nio.ByteBuffer;


/**
 * @author Pavel Savara (original)
 */
public class BulletCommand implements Serializable {
    private static final long serialVersionUID = 1L;

    public BulletCommand(double power, boolean fireAssistValid, double fireAssistAngle, int bulletId) {
        this.fireAssistValid = fireAssistValid;
        this.fireAssistAngle = fireAssistAngle;
        this.bulletId = bulletId;
        this.power = power;
        this.boomerang = false;
        this.boomerangMaxDistance = 0.0;
    }

    public BulletCommand(double power, boolean fireAssistValid, double fireAssistAngle, int bulletId,
                         boolean boomerang, double boomerangMaxDistance) {
        this.fireAssistValid = fireAssistValid;
        this.fireAssistAngle = fireAssistAngle;
        this.bulletId = bulletId;
        this.power = power;
        this.boomerang = boomerang;
        this.boomerangMaxDistance = boomerangMaxDistance;
    }

    private final double power;
    private final boolean fireAssistValid;
    private final double fireAssistAngle;
    private final int bulletId;
    private final boolean boomerang;
    private final double boomerangMaxDistance;

    public boolean isFireAssistValid() {
        return fireAssistValid;
    }

    public int getBulletId() {
        return bulletId;
    }

    public double getPower() {
        return power;
    }

    public double getFireAssistAngle() {
        return fireAssistAngle;
    }

    public boolean isBoomerang() {
        return boomerang;
    }

    public double getBoomerangMaxDistance() {
        return boomerangMaxDistance;
    }

    static ISerializableHelper createHiddenSerializer() {
        return new SerializableHelper();
    }

    private static class SerializableHelper implements ISerializableHelper {
        public int sizeOf(RbSerializer serializer, Object object) {
            return RbSerializer.SIZEOF_TYPEINFO
                    + RbSerializer.SIZEOF_DOUBLE // power
                    + RbSerializer.SIZEOF_BOOL   // fireAssistValid
                    + RbSerializer.SIZEOF_DOUBLE // fireAssistAngle
                    + RbSerializer.SIZEOF_INT    // bulletId
                    + RbSerializer.SIZEOF_BOOL   // boomerang
                    + RbSerializer.SIZEOF_DOUBLE; // boomerangMaxDistance
        }

        public void serialize(RbSerializer serializer, ByteBuffer buffer, Object object) {
            BulletCommand obj = (BulletCommand) object;

            serializer.serialize(buffer, obj.power);
            serializer.serialize(buffer, obj.fireAssistValid);
            serializer.serialize(buffer, obj.fireAssistAngle);
            serializer.serialize(buffer, obj.bulletId);
            serializer.serialize(buffer, obj.boomerang);
            serializer.serialize(buffer, obj.boomerangMaxDistance);
        }

        public Object deserialize(RbSerializer serializer, ByteBuffer buffer) {
            double power = buffer.getDouble();
            boolean fireAssistValid = serializer.deserializeBoolean(buffer);
            double fireAssistAngle = buffer.getDouble();
            int bulletId = buffer.getInt();
            boolean boomerang = serializer.deserializeBoolean(buffer);
            double boomerangMaxDistance = buffer.getDouble();

            return new BulletCommand(power, fireAssistValid, fireAssistAngle, bulletId, boomerang, boomerangMaxDistance);
        }
    }
}
