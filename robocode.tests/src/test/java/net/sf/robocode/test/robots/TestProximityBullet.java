package net.sf.robocode.test.robots;

import net.sf.robocode.test.helpers.Assert;
import net.sf.robocode.test.helpers.RobocodeTestBed;

import org.junit.Test;

import robocode.control.events.BattleFinishedEvent;
import robocode.control.events.TurnEndedEvent;
import robocode.control.snapshot.IRobotSnapshot;
import robocode.control.snapshot.ITurnSnapshot;

public class TestProximityBullet extends RobocodeTestBed {

	private double oneShotGuyInitialEnergy;
	private double oneShotGuyAfterShotEnergy;
	private double chargerInitialEnergy;
	private double chargerAfterBeingShotEnergy;
	private boolean oneShotGuyShoot = false;
	private boolean chargerHit = false;

	@Test
	public void run() {
		super.run();
	}

	@Override
	public String getRobotName() {
		// oneShotGuy dispara, Charger se acerca a oneShotGuy
		return "testrobots.OneShotGuy,testrobots.Charger";
	}

	@Override
	public int getNumRounds() {
		return 1;
	}

	@Override
	public void onTurnEnded(TurnEndedEvent event) {
		IRobotSnapshot oneShotGuy = event.getTurnSnapshot().getRobots()[0];
		IRobotSnapshot charger = event.getTurnSnapshot().getRobots()[1];

		// Guardar energía inicial en el primer turno
		if (event.getTurnSnapshot().getTurn() == 1) {
			oneShotGuyInitialEnergy = oneShotGuy.getEnergy();
			chargerInitialEnergy = charger.getEnergy();
		}

		// Cuando se efectuo el disparo
		if (oneShotGuy.getEnergy() != oneShotGuyInitialEnergy && !oneShotGuyShoot) {
			oneShotGuyAfterShotEnergy = oneShotGuy.getEnergy();
			oneShotGuyShoot = true;
		}

		// Cuando charger recibio daño
		if (charger.getEnergy() < chargerInitialEnergy && !chargerHit) {
			chargerAfterBeingShotEnergy = charger.getEnergy();
			chargerHit = true;
		}

	}

	@Override
	public void onBattleFinished(BattleFinishedEvent event) {

		//Verificamos que robot oneShotGuy disparo
		Assert.assertTrue(oneShotGuyShoot);
		//Verificamos que robot charger fue dañado
		Assert.assertTrue(chargerHit);

		//Verificamos que oneShotGuy no haya recibido daño
		//oneShotGuy dispara con power 3, por lo tanto si tuviese menos de 97 indicaria daño por su propia bala de proximidad.
		Assert.assertTrue(oneShotGuyAfterShotEnergy >= 97);

		//Verificamos que la bala de proximidad haya hecho daño al robot charger
		Assert.assertTrue(chargerAfterBeingShotEnergy < chargerInitialEnergy);
	}
}
