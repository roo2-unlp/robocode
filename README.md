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

Para poder ejecutar el programa si se utiliza **Intellij**, se debe configurar **JDK 1.8**
El programa se ejecuta mediante una configuración **Run** de Gradle con la tarea **build**
Una vez que se corren todos los test, se mostrará en la terminal BUILD SUCCESSFUL
Luego, se debe buscar en la raiz del proyecto la carpeta llamada **.sandbox** y ejecutar el archivo **robocode.bat**
Tener en cuenta que si se realiza cualquier modificación y se quiere compilar, no funcionará cuando este abierto el .bat

---

## Funcionalidades
* Para que el proyecto funcione se agregaron a nivel interfaz un checkbox de la nueva opcion que puede tener valores true(en el caso de que este seleccionada) y false(cuando no se quiere ejecutar esta opcion)
Cuando la opcion esta en true se pueden establecer los valores minimos y maximos de los turnos en los que la opcion random va a volver a tirar el dado.
* En la clase robotPeer se agrego...
* 

## Patrones
Se implemento el Patrón Strategy y el Null Object
- **WallHitDamageStrategy (Strategy)**: define los metodos que deben implementar las distintas estrategias
- **NullHitDamageStrategy (Null Object)**: representa una estrategia nula, no aplica modificaciones al daño.
- **RandomHitDamageStrategy (Concrete Strategy)**: se utiliza cuando la opción está habilitada. Define intervalo de tiempo aleatorio en el que se lanzan los dados(los dados tienen valores de 1 al 6).
---

## Test
* Se genera TestRandomHitDamageStrategy para validar el uso de las implementaciones de la interfaz: NullHitDamageStrategy y RandomHitDamageStrategy
