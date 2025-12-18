/*
 * Copyright (c) 2001-2025 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * https://robocode.sourceforge.io/license/epl-v10.html
 */
package net.sf.robocode.peer;


import java.io.Serializable;
import java.nio.ByteBuffer;

import net.sf.robocode.serialization.ISerializableHelper;
import net.sf.robocode.serialization.RbSerializer;


/**
 * @author Pavel Savara (original)
 */
public class BulletCommand implements Serializable {

	private static class SerializableHelper implements ISerializableHelper {
		public int sizeOf(RbSerializer serializer, Object object) {
			return RbSerializer.SIZEOF_TYPEINFO + RbSerializer.SIZEOF_DOUBLE + RbSerializer.SIZEOF_BOOL
					+ RbSerializer.SIZEOF_DOUBLE + RbSerializer.SIZEOF_INT + RbSerializer.SIZEOF_DOUBLE + RbSerializer.SIZEOF_BOOL;
		}

		public void serialize(RbSerializer serializer, ByteBuffer buffer, Object object) {
			BulletCommand obj = (BulletCommand) object;

			serializer.serialize(buffer, obj.power);
			serializer.serialize(buffer, obj.fireAssistValid);
			serializer.serialize(buffer, obj.fireAssistAngle);
			serializer.serialize(buffer, obj.bulletId);
			serializer.serialize(buffer, obj.proximityRadius);
      		serializer.serialize(buffer, obj.isProximity);
		}

		public Object deserialize(RbSerializer serializer, ByteBuffer buffer) {
			double power = buffer.getDouble();
			boolean fireAssistValid = serializer.deserializeBoolean(buffer);
			double fireAssistAngle = buffer.getDouble();
			int bulletId = buffer.getInt();
			double proximityRadius = buffer.getDouble();
      		boolean isProximity = serializer.deserializeBoolean(buffer);

			return new BulletCommand(power, fireAssistValid, fireAssistAngle, bulletId, proximityRadius, isProximity);
		}
	}

	private static final long serialVersionUID = 1L;
	static ISerializableHelper createHiddenSerializer() {
		return new SerializableHelper();
	}
	private final double power;
	private final boolean fireAssistValid;
	private final double fireAssistAngle;

	private final int bulletId;

	private final double proximityRadius;
	private final boolean isProximity;

	public BulletCommand(double power, boolean fireAssistValid, double fireAssistAngle, int bulletId,double proximityRadius, boolean isProximity) {
		this.fireAssistValid = fireAssistValid;
		this.fireAssistAngle = fireAssistAngle;
		this.bulletId = bulletId;
		this.power = power;
		this.proximityRadius = proximityRadius;
		this.isProximity = isProximity;
	}

	public boolean isProximity() {
		return isProximity;
	}

	public boolean isFireAssistValid() {
		return fireAssistValid;
	}

	public int getBulletId() {
		return bulletId;
	}

	public double getPower() {
		return power;
	}

	public double getProximityRadius() {
		return proximityRadius;
	}

	public double getFireAssistAngle() {
		return fireAssistAngle;
	}
}
