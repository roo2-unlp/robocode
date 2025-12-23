package robocode;

import net.sf.robocode.peer.IRobotStatics;
import net.sf.robocode.serialization.ISerializableHelper;
import net.sf.robocode.serialization.RbSerializer;
import robocode.robotinterfaces.IBasicEvents;
import robocode.robotinterfaces.IBasicRobot;

import java.awt.*;
import java.nio.ByteBuffer;

public final class HitTrapEvent extends Event {
	private static final long serialVersionUID = 1L;

	private final static int DEFAULT_PRIORITY = 100;

	private final double trapX;
	private final double trapY;
	private final double radius;

	public HitTrapEvent(double trapX, double trapY, double radius) {
		super();
		this.trapX = trapX;
		this.trapY = trapY;
		this.radius = radius;
	}

	public double getTrapX() {
		return trapX;
	}
	public double getTrapY() {
		return trapY;
	}
	public double getRadius() {
		return radius;
	}

	@Override
	int getDefaultPriority() {
		return DEFAULT_PRIORITY;
	}

	/**
	 * Aquí se conecta el evento con el método onHitTrap() en la interfaz del robot.
	 */
	@Override
	void dispatch(IBasicRobot robot, IRobotStatics statics, Graphics2D graphics) {
		IBasicEvents listener = robot.getBasicEventListener();

		if (listener != null) {
			listener.onHitTrap(this);
		}
	}

	@Override
	byte getSerializationType() {
		return RbSerializer.HitTrapEvent_TYPE;
	}

	static ISerializableHelper createHiddenSerializer() {
		return new SerializableHelper();
	}

	private static class SerializableHelper implements ISerializableHelper {

		public int sizeOf(RbSerializer serializer, Object object) {

			return RbSerializer.SIZEOF_TYPEINFO
					+ (3 * RbSerializer.SIZEOF_DOUBLE);
		}

		public void serialize(RbSerializer serializer, ByteBuffer buffer, Object object) {
			HitTrapEvent obj = (HitTrapEvent) object;

			serializer.serialize(buffer, obj.trapX);
			serializer.serialize(buffer, obj.trapY);
			serializer.serialize(buffer, obj.radius);
		}

		public Object deserialize(RbSerializer serializer, ByteBuffer buffer) {
			double trapX = buffer.getDouble();
			double trapY = buffer.getDouble();
			double radius = buffer.getDouble();

			return new HitTrapEvent(trapX, trapY, radius);
		}
	}
}
