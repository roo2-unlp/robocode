/*
 * Copyright (c) 2001-2025 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * https://robocode.sourceforge.io/license/epl-v10.html
 */
package net.sf.robocode.host.proxies;


import static java.lang.Math.max;
import static java.lang.Math.min;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import net.sf.robocode.host.IHostManager;
import net.sf.robocode.host.RobotStatics;
import net.sf.robocode.host.events.EventManager;
import net.sf.robocode.peer.BulletCommand;
import net.sf.robocode.peer.BulletStatus;
import net.sf.robocode.peer.ExecCommands;
import net.sf.robocode.peer.ExecResults;
import net.sf.robocode.peer.IRobotPeer;
import net.sf.robocode.peer.TeamMessage;
import net.sf.robocode.repository.IRobotItem;
import net.sf.robocode.robotpaint.Graphics2DSerialized;
import net.sf.robocode.robotpaint.IGraphicsProxy;
import net.sf.robocode.security.HiddenAccess;
import robocode.BattleEndedEvent;
import robocode.Bullet;
import robocode.Condition;
import robocode.Event;
import robocode.PaintEvent;
import robocode.RadioactiveBullet;
import robocode.RobotStatus;
import robocode.Rules;
import robocode.ScannedRobotEvent;
import robocode.StatusEvent;
import robocode.exception.AbortedException;
import robocode.exception.DeathException;
import robocode.exception.DisabledException;
import robocode.exception.RobotException;
import robocode.exception.WinException;
import robocode.robotinterfaces.peer.IRadioactiveRobotPeer;
import robocode.util.Utils;


/**
 * @author Pavel Savara (original)
 * @author Flemming N. Larsen (contributor)
 */
public class BasicRobotProxy extends HostingRobotProxy implements IRadioactiveRobotPeer {
	private static final long
			MAX_SET_CALL_COUNT = 10000,
			MAX_GET_CALL_COUNT = 10000;

	private IGraphicsProxy graphicsProxy;

	private RobotStatus status;
	private boolean isDisabled;
	protected ExecCommands commands;
	private ExecResults execResults;

	private final Map<Integer, Bullet> bullets = new ConcurrentHashMap<Integer, Bullet>();
	private int nextBulletId; // 0 is used for bullet explosions

	private final AtomicInteger setCallCount = new AtomicInteger(0);
	private final AtomicInteger getCallCount = new AtomicInteger(0);

	protected Condition waitCondition;
	private boolean testingCondition;
	private double firedEnergy;
	private double firedHeat;

	BasicRobotProxy(IRobotItem specification, IHostManager hostManager, IRobotPeer peer, RobotStatics statics) {
		super(specification, hostManager, peer, statics);

		eventManager = new EventManager(this);

		graphicsProxy = new Graphics2DSerialized();

		// dummy
		execResults = new ExecResults(null, null, null, null, null, false, false, false);

		setSetCallCount(0);
		setGetCallCount(0);
	}

	@Override
	public void cleanup() {
		super.cleanup();

		// Cleanup and remove current wait condition
		if (waitCondition != null) {
			waitCondition.cleanup();
			waitCondition = null;
		}

		// Cleanup and remove the event manager
		if (eventManager != null) {
			eventManager.cleanup();
			eventManager = null;
		}

		// Cleanup graphics proxy
		graphicsProxy = null;
		execResults = null;
		status = null;
		commands = null;
	}

	// asynchronous actions
	public Bullet setFire(double power) {
		setCall();
		return fireImpl(power, false);
	}

	public RadioactiveBullet setFireRadioactive(double power) {
		setCall();
		return (RadioactiveBullet) fireImpl(power, true);
	}

	// blocking actions
	public void execute() {
		executeImpl();
	}

	public void move(double distance) {
		setMoveImpl(distance);
		do {
			execute(); // Always tick at least once
		} while (getDistanceRemaining() != 0);
	}

	public void turnBody(double radians) {
		setTurnBodyImpl(radians);
		do {
			execute(); // Always tick at least once
		} while (getBodyTurnRemaining() != 0);
	}

	public void turnGun(double radians) {
		setTurnGunImpl(radians);
		do {
			execute(); // Always tick at least once
		} while (getGunTurnRemaining() != 0);
	}

	public Bullet fire(double power) {
		Bullet bullet = setFire(power);

		execute();
		return bullet;
	}

	public RadioactiveBullet fireRadioactiveBullet(double power) {
		RadioactiveBullet bullet = setFireRadioactive(power);

		execute();
		return bullet;
	}

	// fast setters
	public void setBodyColor(Color color) {
		commands.setBodyColor(color != null ? color.getRGB() : ExecCommands.defaultBodyColor);
	}

	public void setGunColor(Color color) {
		commands.setGunColor(color != null ? color.getRGB() : ExecCommands.defaultGunColor);
	}

	public void setRadarColor(Color color) {
		commands.setRadarColor(color != null ? color.getRGB() : ExecCommands.defaultRadarColor);
	}

	public void setBulletColor(Color color) {
		commands.setBulletColor(color != null ? color.getRGB() : ExecCommands.defaultBulletColor);
	}

	public void setScanColor(Color color) {
		commands.setScanColor(color != null ? color.getRGB() : ExecCommands.defaultScanColor);
	}

	// counters
	public void setCall() {
		if (!isDisabled) {
			final int res = setCallCount.incrementAndGet();

			if (res >= MAX_SET_CALL_COUNT) {
				isDisabled = true;
				println("SYSTEM: You have made " + res + " calls to setXX methods without calling execute()");
				throw new DisabledException("Too many calls to setXX methods");
			}
		}
	}

	public void getCall() {
		if (!isDisabled) {
			final int res = getCallCount.incrementAndGet();

			if (res >= MAX_GET_CALL_COUNT) {
				isDisabled = true;
				println("SYSTEM: You have made " + res + " calls to getXX methods without calling execute()");
				throw new DisabledException("Too many calls to getXX methods");
			}
		}
	}

	public double getDistanceRemaining() {
		getCall();
		return commands.getDistanceRemaining();
	}

	public double getRadarTurnRemaining() {
		getCall();
		return commands.getRadarTurnRemaining();
	}

	public double getBodyTurnRemaining() {
		getCall();
		return commands.getBodyTurnRemaining();
	}

	public double getGunTurnRemaining() {
		getCall();
		return commands.getGunTurnRemaining();
	}

	public double getVelocity() {
		getCall();
		return status.getVelocity();
	}

	public double getGunCoolingRate() {
		getCall();
		return statics.getBattleRules().getGunCoolingRate();
	}

	public String getName() {
		getCall();
		return statics.getName();
	}

	public long getTime() {
		getCall();
		return getTimeImpl();
	}

	public double getBodyHeading() {
		getCall();
		return status.getHeadingRadians();
	}

	public double getGunHeading() {
		getCall();
		return status.getGunHeadingRadians();
	}

	public double getRadarHeading() {
		getCall();
		return status.getRadarHeadingRadians();
	}

	public double getEnergy() {
		getCall();
		return getEnergyImpl();
	}

	public double getGunHeat() {
		getCall();
		return getGunHeatImpl();
	}

	public double getX() {
		getCall();
		return status.getX();
	}

	public double getY() {
		getCall();
		return status.getY();
	}

	public int getOthers() {
		getCall();
		return status.getOthers();
	}

	public int getNumSentries() {
		getCall();
		return status.getNumSentries();
	}

	public double getBattleFieldHeight() {
		getCall();
		return statics.getBattleRules().getBattlefieldHeight();
	}

	public double getBattleFieldWidth() {
		getCall();
		return statics.getBattleRules().getBattlefieldWidth();
	}

	public int getNumRounds() {
		getCall();
		return statics.getBattleRules().getNumRounds();
	}

	public int getRoundNum() {
		getCall();
		return status.getRoundNum();
	}

	public int getSentryBorderSize() {
		getCall();
		return statics.getBattleRules().getSentryBorderSize();
	}

	public Graphics2D getGraphics() {
		getCall();
		commands.setTryingToPaint(true);
		return getGraphicsImpl();
	}

	public void setDebugProperty(String key, String value) {
		setCall();
		commands.setDebugProperty(key, value);
	}

	public void rescan() {
		boolean reset = false;
		boolean origInterruptableValue = false;

		if (eventManager.getCurrentTopEventPriority() == eventManager.getScannedRobotEventPriority()) {
			reset = true;
			origInterruptableValue = eventManager.isInterruptible(eventManager.getScannedRobotEventPriority());
			eventManager.setInterruptible(eventManager.getScannedRobotEventPriority(), true);
		}

		commands.setScan(true);
		executeImpl();

		if (reset) {
			eventManager.setInterruptible(eventManager.getScannedRobotEventPriority(), origInterruptableValue);
		}
	}

	public long getTimeImpl() {
		return status.getTime();
	}
	
	// -----------
	// implementations
	// -----------

	public Graphics2D getGraphicsImpl() {
		return (Graphics2D) graphicsProxy;
	}

	public void setTestingCondition(boolean testingCondition) {
		this.testingCondition = testingCondition;
	}

	@Override
	public String toString() {
		return statics.getShortName() + "(" + (int) status.getEnergy() + ") X" + (int) status.getX() + " Y"
				+ (int) status.getY();
	}

	protected void initializeRound(ExecCommands commands, RobotStatus status) {
		updateStatus(commands, status);

		eventManager.reset();
		eventManager.add(new StatusEvent(status)); // Start event

		setSetCallCount(0);
		setGetCallCount(0);

		// make bulletId unique for entire battle and across all robots
		nextBulletId = 1 + this.statics.getRobotIndex() * 10000 + status.getRoundNum() * 1000000;
	}

	@Override
	protected final void executeImpl() {
		if (execResults == null) {
			// this is to slow down undead robot after cleanup, from fast exception-loop
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ignore) {}
		}

		// Entering tick
		robotThreadManager.checkRunThread();
		if (testingCondition) {
			throw new RobotException(
					"You cannot take action inside Condition.test().  You should handle onCustomEvent instead.");
		}

		setSetCallCount(0);
		setGetCallCount(0);

		// This stops auto-scan from scanning...
		if (waitCondition != null && waitCondition.test()) {
			waitCondition = null;
			commands.setScan(true);
		}

		commands.setOutputText(out.readAndReset());
		commands.setGraphicsCalls(graphicsProxy.readoutQueuedCalls());

		// Call server
		execResults = peer.executeImpl(commands);

		updateStatus(execResults.getCommands(), execResults.getStatus());

		graphicsProxy.setPaintingEnabled(execResults.isPaintEnabled());
		firedEnergy = 0;
		firedHeat = 0;

		// add new events
		eventManager.add(new StatusEvent(execResults.getStatus()));
		if (statics.isPaintRobot() && execResults.isPaintEnabled()) {
			// Add paint event, if robot is a paint robot and its painting is enabled
			eventManager.add(new PaintEvent());
		}

		// add other events
		if (execResults.getEvents() != null) {
			for (Event event : execResults.getEvents()) {
				eventManager.add(event);
			}
		}

		if (execResults.getBulletUpdates() != null) {
			for (BulletStatus bulletStatus : execResults.getBulletUpdates()) {
				final Bullet bullet = bullets.get(bulletStatus.bulletId);

				if (bullet != null) {
					HiddenAccess.update(bullet, bulletStatus.x, bulletStatus.y, bulletStatus.victimName,
							bulletStatus.isActive);
					if (!bulletStatus.isActive) {
						bullets.remove(bulletStatus.bulletId);
					}
				}
			}
		}

		// add new team messages
		loadTeamMessages(execResults.getTeamMessages());

		eventManager.processEvents();
	}

	@Override
	protected final void waitForBattleEndImpl() {
		eventManager.clearAllEvents(false);
		graphicsProxy.setPaintingEnabled(false);
		do {
			// Make sure remaining system events like e.g. are processed this round
			try {
				eventManager.processEvents();

				// The exceptions below are expected to occur, and has already been logged in the robot console,
				// but still exists in the robot's event queue. Hence, we just ignore these!
				// Look in the HostingRobotProxy.run() to see which robot errors that are already handled.
			} catch (DeathException ignore) {} catch (WinException ignore) {// Bug fix [2952549]
			} catch (AbortedException ignore) {} catch (DisabledException ignore) {// Bug fix [2976258]
			}

			commands.setOutputText(out.readAndReset());
			commands.setGraphicsCalls(graphicsProxy.readoutQueuedCalls());

			// Call server
			execResults = peer.waitForBattleEndImpl(commands);

			updateStatus(execResults.getCommands(), execResults.getStatus());

			// Add remaining events like BattleEndedEvent Otherwise, the robot might never receive those events
			if (execResults.getEvents() != null) {
				for (Event event : execResults.getEvents()) {
					if (event instanceof BattleEndedEvent) {
						eventManager.add(event);
					}
				}
			}
			eventManager.resetCustomEvents();
		} while (!execResults.isHalt() && execResults.isShouldWait());
	}

	protected void loadTeamMessages(java.util.List<TeamMessage> teamMessages) {}

	protected final void setMoveImpl(double distance) {
		if (getEnergyImpl() == 0) {
			return;
		}
		commands.setDistanceRemaining(distance);
		commands.setMoved(true);
	}

	protected final void setTurnGunImpl(double radians) {
		commands.setGunTurnRemaining(radians);
	}

	protected final void setTurnBodyImpl(double radians) {
		if (getEnergyImpl() > 0) {
			commands.setBodyTurnRemaining(radians);
		}
	}

	protected final void setTurnRadarImpl(double radians) {
		commands.setRadarTurnRemaining(radians);
	}

	private void updateStatus(ExecCommands commands, RobotStatus status) {
		this.status = status;
		this.commands = commands;
	}

	private final double getEnergyImpl() {
		return status.getEnergy() - firedEnergy;
	}

	// -----------
	// battle driven methods
	// -----------

	private final double getGunHeatImpl() {
		return status.getGunHeat() + firedHeat;
	}

	private final Bullet fireImpl(double power, boolean isRadioactive) {
		if (Double.isNaN(power)) {
			println("SYSTEM: You cannot call " + (isRadioactive ? "fireRadioactive" : "fire") + "(NaN)");
			return null;
		}
		if (getGunHeatImpl() > 0 || getEnergyImpl() == 0) {
			return null;
		}

		double minPower = Rules.MIN_BULLET_POWER;
		double maxPower = Rules.MAX_BULLET_POWER;

		power = min(getEnergyImpl(), min(max(power, minPower), maxPower));

    double battleRuleRadius = statics.getBattleRules().getRadioactiveBulletProximityRadius();
    double minPR = Rules.MIN_PROXIMITY_RADIUS;
		double maxPR = Rules.MAX_PROXIMITY_RADIUS;
		
    //proximityRadius = min(getEnergyImpl(), min(max(proximityRadius, minPR), maxPR)); Calculo de radio con energia involucrada
		double proximityRadius = min(max(battleRuleRadius, minPR), maxPR); //Calculo de radio sin energia involucrada

		Bullet bullet;
		BulletCommand wrapper;
		Event currentTopEvent = eventManager.getCurrentTopEvent();

		nextBulletId++;

		if (currentTopEvent != null && currentTopEvent.getTime() == status.getTime() && !statics.isAdvancedRobot()
				&& status.getGunHeadingRadians() == status.getRadarHeadingRadians()
				&& ScannedRobotEvent.class.isAssignableFrom(currentTopEvent.getClass())) {
			// this is angle assisted bullet
			ScannedRobotEvent e = (ScannedRobotEvent) currentTopEvent;
			double fireAssistAngle = Utils.normalAbsoluteAngle(status.getHeadingRadians() + e.getBearingRadians());

			if (isRadioactive) {
				bullet = new RadioactiveBullet(fireAssistAngle, getX(), getY(), power, statics.getName(), null, true, nextBulletId, proximityRadius);
			} else {
				bullet = new Bullet(fireAssistAngle, getX(), getY(), power, statics.getName(), null, true, nextBulletId);
			}
			wrapper = new BulletCommand(power, true, fireAssistAngle, nextBulletId, proximityRadius, isRadioactive);
		} else {
			// this is normal bullet
			if (isRadioactive) {
				bullet = new RadioactiveBullet(status.getGunHeadingRadians(), getX(), getY(), power, statics.getName(), null, true,
						nextBulletId, proximityRadius);
			} else {
				bullet = new Bullet(status.getGunHeadingRadians(), getX(), getY(), power, statics.getName(), null, true,
						nextBulletId);
			}
			wrapper = new BulletCommand(power, false, 0, nextBulletId, proximityRadius, isRadioactive);
		}

		firedEnergy += power;
		firedHeat += Rules.getGunHeat(power);

		commands.getBullets().add(wrapper);

		bullets.put(nextBulletId, bullet);

		return bullet;
	}

	// -----------
	// for robot thread
	// -----------

	private void setSetCallCount(int setCallCount) {
		this.setCallCount.set(setCallCount);
	}

	private void setGetCallCount(int getCallCount) {
		this.getCallCount.set(getCallCount);
	}
}
