# Robocode

![Robocode logo](https://robocode.sourceforge.io/gfx/robocode_logo_tanks.png)

## Introduccion

Nosotros somos el **Grupo 4** conformado por:
- Leonardo Delmas
- Leandro Joaquín Canosa

Se nos introdujo el enunciado de la extensión a implementar: **Bullets de impacto por proximidad**

> “Los proyectiles pueden descontar energía por impacto (colisión) con un robot o pueden dañar con menor intensidad si pasan cerca (proximidad) de un robot. Se puede pensar que los proyectiles por proximidad son “radioactivos” y que descuentan energía en función de la distancia a la que pasan de un robot. El proyectil radioactivo no debería dañar al robot que la disparó.”

Las características que se le agregaron a Robocode fueron las siguientes:
- La posibilidad de disparar balas de proximidad
- Desde las reglas de batalla poder cambiar el radio de proximidad que van a tener todas las balas de proximidad.
- Desactivar/Activar el radio visual de escaneo del nuevo tipo de munición desde el menú “view options“.

## ¿Cómo funciona una bala de proximidad?

Una bala de proximidad es un proyectil especial que no necesita impactar directamente contra un robot enemigo para causar daño. En su lugar, define un radio de detección circular a su alrededor. Cuando un robot entra por primera vez dentro de ese radio, la bala detona y aplica daño. Este tipo de bala puede alternarse con la bala normal durante la batalla.

El daño aplicado no es fijo, sino que depende de la distancia entre el robot y el centro de la explosión. Cuanto más cerca se encuentre el robot del centro del radio, mayor será el daño. A medida que la distancia aumenta, el daño disminuye de forma progresiva hasta alcanzar un valor mínimo en el borde del radio.

## Uso de la bala de proximidad

A cualquier robot se le da la posibilidad de usar esta bala de proximidad a través de un nuevo método:

```fireProximityBullet(power)```

Donde power representa el poder de la bala y el tamaño del radio se define en las reglas de batalla.

Para esto es importante que al momento de crear un nuevo robot este herede comportamiento de Proximity Robot de la siguiente forma:
```
import robocode.*;
public class MiPrimerRobot extends ProximityRobot {
…
}
```
## ¿Cómo cambio el tamaño del radio de una bala de proximidad?

En la sección de reglas de batalla se agregó una nueva opción proximity radius en la que se podrá ingresar el valor de radio que quieras.

Puedes acceder a ella: NewBattle -> Next -> ProximityRadius

<img width="417" height="320" alt="BattleRulesEdit" src="https://github.com/user-attachments/assets/eca37f58-1a7a-4c22-8192-c377c2b16ec3" />

## No me gusta el dibujo del radio de proximidad, ¿puedo desactivarlo?

Si!, para eso puedes navegar a Options -> Preferences -> View Options -> y desactivar:
- [x] VisibleScanBulletsArcs.

<img width="433" height="198" alt="ViewScanEdit" src="https://github.com/user-attachments/assets/eaba793d-2ea2-4059-80d7-cdc166054d85" />


En principio esta opción está activada por defecto porque consideramos que si estuviese desactivada uno no podría distinguir visualmente si se disparó una bala de proximidad o una bala normal.

## Estructura de la Feature

Se implementaron los diferentes tipos de proyectiles (impacto y proximidad) utilizando el patrón Strategy, permitiendo intercambiar el algoritmo de detección de colisión sin modificar la clase BulletPeer.

**Características tecnicas de Proximidad**
- Detonación por entrada: El proyectil detona cuando cualquier robot entra al radio (no mientras permanece adentro)
- Daño en área (AoE): Al detonar, todos los robots dentro del radio reciben daño
- Daño escalado por distancia: El daño no utiliza el poder completo de la bala. En el centro del radio se aplica como máximo el 90% del poder base de la bala de proximidad, y este valor disminuye progresivamente hasta un 10% del poder base cuando el robot se encuentra en el borde del radio.
- Protección al dueño: El robot que disparó nunca recibe daño de su propio proyectil

**Componentes**
|Clase|Responsabilidad|
|------|-----------------|
|IBulletCollisionStrategy|Interface que define el contrato para estrategias de colisión
|NormalBulletCollisionStrategy|Detecta colisión cuando la línea de movimiento del proyectil intersecta el bounding box del robot
|ProximityBulletCollisionStrategy|Detecta cuando robots entran al radio de proximidad. Daño en área: al detonar, daña a todos los robots dentro del radio
|BulletCollisionStrategyFactory|Factory con registro EnumMap<BulletType, Supplier> para crear estrategias polimórficamente

La retrocompatibilidad se preserva ya que los robots existentes no necesitan ser modificados para seguir funcionando correctamente. Aquellos que no utilizan balas radioactivas continúan comportándose exactamente igual que antes, ya que el flujo de disparo estándar no fue alterado.

## Tests

Para comprobar que nuestra feature funciona de forma correcta se implementaron  tanto **pruebas unitarias** como **pruebas de integración**.
Para las pruebas unitarias se creó la clase `ProximityBulletTest`, que testea los siguientes puntos:

- La detección de intersección entre el radio de proximidad de la bala y el bounding box de un robot, validando los casos en los que el robot se encuentra dentro o fuera del área efectiva.
  - `elRobotSeEncuentraDentroDeRadio`
  - `elRobotSeEncuentraFueraDeRadio`

- El cálculo del poder ajustado de la bala según la distancia entre la bala y el robot, verificando los valores extremos del modelo (máximo en el centro del radio y mínimo en el borde).
  - `elRobotRecibeElDanioMaximoEnElCentroDelRadio`
  - `elRobotRecibeElDanioMinimoEnElBordeDelRadio`

Mientras que para las pruebas de integración se creó la clase `TestProximityBullet`, que testea los siguientes puntos:
- El robot que dispara una bala de proximidad no recibe daño de su propia bala.
- Un robot enemigo que entra en el radio de proximidad recibe daño como resultado del disparo.

Para ello, se utilizan robots de prueba con comportamientos controlados: uno que dispara una única vez al detectar un enemigo dentro del rango (OneShotGuy), y otro que se aproxima hasta quedar dentro del radio sin colisionar (Charger).

Ambas pruebas son ejecutadas al momento de correr el comando ```./gradlew build```

Tambien se realizaron **tests manuales**, corriendo robocode y generando situaciones particulares para ver si todo funcionaba como debía.

|Robot|Comportamiento|
|------|-----------------|
|Shooter|Va alternando disparos con bala normal y con bala de proximidad|
|Sleeper|Se queda inmovil|
|Charger|Se acerca a Shooter hasta cierto punto para no provocar colisión|

Daño de radio en el borde:

![GIFBalaNormalBalaRadioactivaEDITADO](https://github.com/user-attachments/assets/2acdc78e-7d52-4e4e-a1aa-d0b567fba07e)

Como se puede observar el daño que recibe Sleeper es muy poco al estar al borde del radio.

Daño de radio mas proximo al centro:

![GIFChargerEDITADO](https://github.com/user-attachments/assets/92c6826c-488a-4675-99b7-e0e80458c243)

Como se puede observar el daño que recibe Charger es superior al que recibio Sleeper al estar mas cerca del centro del radio.

(Estos videos eran los que queriamos mostrar en la presentacion y no pudimos 😔)

## Notas y aclaraciones:

Al abrir Robocode por primera vez después de ejecutar `./gradlew build` y modificar el valor de proximityRadius, dicho cambio no se refleja al iniciar la primera batalla al presionar Start Battle. Inicialmente se asumió que se trataba de un error en nuestra implementación, pero tras clonar y ejecutar el repositorio original de Robocode se pudo confirmar que el comportamiento también ocurre allí, por lo que se trata de un bug propio de Robocode.

El problema no afecta únicamente a proximityRadius, cualquier campo perteneciente a las Battle Rules vuelve a su valor por defecto al iniciar la primera batalla. De todas formas este comportamiento se presenta exclusivamente en la primera batalla ejecutada luego de un gradlew build inicial. A partir de la segunda batalla, los cambios en las reglas se aplican correctamente.

## Imagenes
<img width="403" height="304" alt="1vs1" src="https://github.com/user-attachments/assets/089eddc0-e0c3-4790-98f0-2fa435114360" />
<img width="404" height="304" alt="BattleRoyale" src="https://github.com/user-attachments/assets/7f0f7595-389e-4e3c-a0ec-737a39326f05" />

