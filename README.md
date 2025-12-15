<div align="center"><h1>Robocode</h1></div>

## Grupo 3
### Integrantes
* MARINA LENCINAS
* AUGUSTO BOSCHIAZZO

## Consigna del equipo

> Cálculo de daño contra pared.
Cada cierto tiempo (randomizado) se tiran los “dados” (de 1 a 6) y el próximo golpe del robot con la pared genera un descuento en vida multiplicado por el dado.


## Actividades realizadas
*Se realizara una actualización de cada punto implementado*
Para poder correr el programa, si se utiliza Intellij se debe configurar JDK 1.8
La configuracion para correr el programa es Run con el valor build
Una vez que se corren todos los test, se vera en la terminal BUILD SUCCESSFUL
Se debe buscar en la raiz del proyecto la carpeta llamada .sandbox y presionar el archivo robocode.bat
Tener en cuenta que si se realiza cualquier modificación y se quiere compilar, no funcionará cuando este abierto el .bat

Funcionalidades:
Para que el proyecto funcione se agregaron a nivel interfaz un checkbox de la nueva opcion que puede tener valores true(en el caso de que este seleccionada) y false(cuando no se quiere ejecutar esta opcion)
Cuando la opcion esta en true se pueden establecer los valores minimos y maximos de los turnos en los que la opcion random va a volver a tirar el dado.
En la clase robotPeer se agrego...
Existen 2 estrategias relacionadas a las opciones: si es false la estrategia es nula, es decir que multiplicara por 1(el nulo en multiplicación) y en el momento de inicializar no hara nada.
Con respecto a la opción cuando esta en true, se aplicara la RandomWallHitDamageStrategy(es decir la estrategia que establece: un tiempo random en el que se tirará el dado, y un dado que tiene valores de 1 a 6, tambien random)
NullHitDamageStrategy:
--Agregar la imagen de la interfaz sin clickear, mostrar en el menu de la consola y consola del robot que aparece que esta en false y que no altera el comportamiento
RandomHitDamageStrategy:
--Agregar la imagen de la interfaz en la que esta seleccionada y aparecen el min y max, mostrar los mensajes cuando esta vacio, cuando el max es menor al min y ver si falta alguno mas.
Mostrar consola del robot velocity o interactive y comparar con la consola del menu en donde se ve el valor que tira el dado y que el valor coincide con la multiplicacion del golpe del robot
Test:
Explicar cuales son los test que se estan corriendo y como sirven para verificar el correcto funcionamiento