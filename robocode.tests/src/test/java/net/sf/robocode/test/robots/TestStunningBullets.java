package net.sf.robocode.test.robots;


import net.sf.robocode.test.helpers.Assert;
import net.sf.robocode.test.helpers.RobocodeTestBed;

import org.junit.Test;
import robocode.control.events.TurnEndedEvent;
import robocode.control.events.RoundEndedEvent;
import robocode.control.events.BattleFinishedEvent;

import robocode.control.snapshot.IScoreSnapshot;
import robocode.control.snapshot.ITurnSnapshot;
import robocode.control.snapshot.IRobotSnapshot;
import robocode.control.snapshot.RobotState;


public class TestStunningBullets extends RobocodeTestBed {
    private int lastSkipTurns = 0; //Previous value of skip turns
    private int stunCount = 0; //Times SittingDuck was stunned

    ITurnSnapshot lastTurnSnapshot;

    @Test
    public void run() {
        super.run();
    }

    @Override
    public String getRobotName() {
        return "sample.Power1TestShooter,sample.SittingDuck";
    }

    @Override
    public int getNumRounds() {
        return 2;
    }

    @Override
    public int getStunDuration() {
        return 10;
    }

    @Override
    public String getBulletEffect() {
        return "Stunning Effect";
    }

    @Override
    public void onTurnEnded(TurnEndedEvent event) {
        super.onTurnEnded(event);

        lastTurnSnapshot = event.getTurnSnapshot();

        IRobotSnapshot r1 = event.getTurnSnapshot().getRobots()[0];
        IRobotSnapshot r2 = event.getTurnSnapshot().getRobots()[1];
        //Check the robots are using the correct bullet effect
        Assert.assertEquals(r1.getBulletEffect(), "Stunning Effect");
        Assert.assertEquals(r2.getBulletEffect(), "Stunning Effect");
        //Robot 1 will never be stunned
        Assert.assertEquals(r1.getSkipTurns(), 0);

        int skipTurns = r2.getSkipTurns();
        //Test that skipTurns decreases every turn
        if ((lastSkipTurns != 0) && (r2.getState() != RobotState.DEAD)) {
            Assert.assertEquals(skipTurns, lastSkipTurns - 2);
        }
        //Increase stun count
        if ((lastSkipTurns == 0) && (skipTurns > 0) && (r2.getState() != RobotState.DEAD)) {
            stunCount++;
            //When the stun is applied skipTurns is:
            //The bullet power (1) times the stun duration (10)
            //Minus 2 (because Sitting Duck's turn ended)
            Assert.assertEquals(skipTurns, 8);
        }

        lastSkipTurns = skipTurns;
    }

    @Override
    public void onRoundEnded(RoundEndedEvent event) {
        super.onRoundEnded(event);

        System.out.println("ROUND ENDED");

        //Reset value
        lastSkipTurns = 0;
    }

    @Override
    public void onBattleFinished(BattleFinishedEvent event) {
        final IScoreSnapshot[] scores = lastTurnSnapshot.getSortedTeamScores();
        //24 times per round
        Assert.assertEquals(stunCount, 48);
        //SittingDuck always lose
        Assert.assertTrue(scores[0].getTotalScore() > scores[1].getTotalScore());

        System.out.println("BATTLE ENDED");
    }
}
