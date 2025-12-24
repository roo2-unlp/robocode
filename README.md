<div align="center"><h1>Robocode</h1></div>

![Robocode logo](https://robocode.sourceforge.io/gfx/robocode_logo_tanks.png)

## Grupo 3
### Integrantes
* MARINA LENCINAS
* AUGUSTO BOSCHIAZZO

---

## Consigna del equipo

> Cálculo de daño contra pared.
> Cada cierto tiempo (randomizado) se tiran los “dados” (de 1 a 6) y 
> el próximo golpe del robot con la pared genera un descuento en vida multiplicado por el dado.

---

## Configuración

Para poder ejecutar el programa si se utiliza **IntelliJ**, se debe configurar **JDK 1.8**
El programa se ejecuta mediante una configuración **Run** de Gradle con la tarea **build**
Una vez que se corren todos los test, se mostrará en la terminal BUILD SUCCESSFUL
Luego, se debe buscar en la raiz del proyecto la carpeta llamada **.sandbox** y ejecutar el archivo **robocode.bat**
Tener en cuenta que si se realiza cualquier modificación y se quiere compilar, no funcionará cuando este abierto el .bat

---

## Funcionalidades
* Para que el proyecto funcione se agregó a nivel interfaz un checkbox de la nueva opción de daño random contra paredes. Cuando la opcion esta seleccionada se pueden establecer los valores minimos y maximos de los turnos en los que la opcion random va a volver a tirar el dado.
* Cuando se selecciona la opción, se puede elegir un mínimo y máximo de turnos. Estos valores definiran el rango de duraciones posibles del timer.
* Cuando inicia la batalla, se iniciará a su vez un timer cuya duración se elegirá al azar entre los valores de mínimo y máximo. Cuando se cumple el tiempo, se vuelve a seleccionar un valor aleatorio del timer, y se multiplica el daño contra paredes en un valor aleatorio del 1 al 6.
* El daño extra de colisiones contra la pared se mantiene hasta que se vuelven a tirar los dados, momento en el que puede llegar a elegirse un nuevo valor.

## Patrones
Se implemento el Patrón Strategy y el Null Object
- **WallHitDamageStrategy (Strategy)**: define los metodos que deben implementar las distintas estrategias
- **NullHitDamageStrategy (Null Object)**: representa una estrategia nula, no aplica modificaciones al daño.
- **RandomHitDamageStrategy (Concrete Strategy)**: se utiliza cuando la opción está habilitada. Define intervalo de tiempo aleatorio en el que se lanzan los dados(los dados tienen valores de 1 al 6).

## Implementacion
Fue necesario modificar la clase RobotPeer, y la clase Rules, que es la que contiene el método que calcula el daño contra paredes.
Además, se modificaron clases del diálogo para poder mostrar el checkbox en la sección de reglas de la batalla.

---

## Test
* Se genera TestRandomHitDamageStrategy para validar el uso de las implementaciones de la interfaz: NullHitDamageStrategy y RandomHitDamageStrategy
