# Robocode

![Robocode logo](https://robocode.sourceforge.io/gfx/robocode_logo_tanks.png)

## Implementación de trampas en el mapa
- se activan desde la pestaña de "rules"
- se pueden seleccionar la cantidad de trampas que se activan
## Para crear un efecto de trampa personalizado:
- implementar la interfaz ITrapEffect en "robocode.battle/src/main/java/net/sf/robocode/battle/effect"
- agregar nuevo tipo de efecto al input en NewBattleRulesTab.java, también en Battle.java

```java
public class DamageEffect implements ITrapEffect{
	private final double damage;
	private final int duration;

	public DamageEffect(double damage, int duration)
	{
		this.damage = damage;
		this.duration = duration;
	}
	@Override
	public void apply(RobotPeer robot) {
		robot.applyEnergyEffect(-damage);
	}

	@Override
	public void revert(RobotPeer robot) {
	}

	@Override
	public int getDuration() {
		return duration;
	}

	@Override
	public String getMessage() {
		return "Damage: " + damage;
	}
}
```
- `apply` se llama cuando se activa la trampa
- `revert` se llama para revertir el efecto de la trampa cuando se acaba la duración
- `getDuration` devuelve el tiempo que dura la trampa
- `getMessage` devuelve el mensaje que se muestra en la consola cuando se activa la trampa`

### efectos aplicables al robot (se ampliara en el futuro)
- `applyEnergyEffect` afecta a la energia del robot
- `setMovementMultiplier` afecta a la velocidad del robot
