package net.sf.robocode.battle.peer;

import robocode.BattleRules;
import robocode.Rules;
import robocode.control.snapshot.BulletState;

import java.util.List;

import static java.lang.Math.*;

/**
 * Bullet that travels out a max distance, then returns to its origin point (boomerang).
 */
class BoomerangBulletPeer extends BulletPeer {

    private final double originX;
    private final double originY;
    private final double maxOutDistance;
    private boolean returning;
    private double traveled;

    BoomerangBulletPeer(RobotPeer owner, BattleRules battleRules, int bulletId,
                        double originX, double originY, double maxOutDistance) {
        super(owner, battleRules, bulletId);
        this.originX = originX;
        this.originY = originY;
        this.maxOutDistance = max(0, maxOutDistance);
        this.returning = false;
        this.traveled = 0.0;
    }

    @Override
    public void update(List<RobotPeer> robots, List<BulletPeer> bullets) {
        double prevX = this.x;
        double prevY = this.y;

        super.update(robots, bullets);

        if (!isActive()) {
            return;
        }

        // track traveled distance (only while outbound)
        if (!returning) {
            traveled += hypot(this.x - prevX, this.y - prevY);
            if (traveled >= maxOutDistance) {
                // start returning: set heading toward origin
                returning = true;
                double dx = originX - this.x;
                double dy = originY - this.y;
                double angle = atan2(dx, dy);
                setHeading(angle);
            }
        } else {
            double distToOrigin = hypot(this.x - originX, this.y - originY);
            double v = Rules.getBulletSpeed(getPower());
            if (distToOrigin <= v) {
                // If close to origin, finish with inactive
                this.x = originX;
                this.y = originY;
                setState(BulletState.INACTIVE);
                frame = 0;
            }
        }
    }
}
