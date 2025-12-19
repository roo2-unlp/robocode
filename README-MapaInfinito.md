# Mapa Infinito — Branch

**Objetivo** Extender el framework para que se admita el comportamiento “mapa infinito” siendo retrocompatible con la versión actual del framework. 
El mapa infinito solo aplica a los robots, no aplica para los proyectiles. 

**Definición de "Mapa Infinito"** El escenario finito simula ser infinito mediante la teletransportación de los robots a coorednadas equivalentes en el extremo contrario al momento de llegar a un borde del mapa, manteniendo su velocidad.

**Lógica de Teletransporte**
Cuando el robot intenta desplazarse y llego a uno de los límites del mapa:
1. Si cruza el límite izquierdo, debe reaparecer en el extremo derecho.
2. Si cruza el límite derecho, debe reaparecer en el extremo izquierdo.
3. Si cruza el borde superior, debe reaparecer en el borde inferior.
4. Si cruza el borde inferior, debe reaparecer en el borde superior.
5. En ninguno de estos casos el robot pierde energía ni recibe daño.

Para lograr esto, se modificó la clase RobotPeer.java, dentro de esa clase hay un método checkWallCollision() que es donde se implemntaron los cambios, teniendo en cuenta que Y es la altura y X es el ancho del mapa: 
1. Si un robot llega al borde superior del limite Y, a su posición actual se le resta el valor Y y se mantiene el eje X, de está manera se logra el efecto de mapa infinito. Con esto logramos que el robot pase de estar arriba a estar en la parte de abajo del mapa manteniendo la orientación en la que estaba.
2. Si un robot llega al borde inferior del limite Y, en este caso se le suma el valor de Y y se mantiene el eje X. Con esto se logra que el robot pase de estar abajo a estar en la parte de arriba del mapa, manteniendo la orientación en la que estaba. 
3. Si un robot llega al borde superior del limite X, a su posición se le suma el valor X y se mantiene el eje Y. Con esto logramos que el robot pase de estar en el limite de la derecha al limite de la izquierda manteniendo la orientación en la que estaba (es decir, mirando para la derecha)
4. Si un robot llego al borde inferior del limite X, a su posición se le suma el valor X y se mantiene el eje Y. Con esto logramos que el robot pase de estar en el limite de la izquierdo al limite de la derecho, manteniendo la orientación en la que estaba (es decir, mirando para la izquierda)

**Logica de selección de mapa**
Para lograr que el mapa infinito sea una regla de batalla configurable y mantener por defecto el modo normal del mapa, dentro de la clase NewBattleRulesTab.java que la clase donde se definen los componentes visuales de para las reglas de batalla, se agrego un checkbox que permite habilitar el mapa infinito antes de comenzar una partida. 
No se puede cambiar el modo del mapa durante la partida, el modo se elige antes de comenzarla y tiene vigencia hasta que se termina la partida. 

**Clases del código modificadas**
- RobotPeer.java - Modificación de logica de colisiones con los limites del mapa.
- README-MapaInfinito.md — Documentación de los cambios.
- NewBattleRulesTab.java - Modificación de la parte visual para agregar el checkBox que permite habilitar el mapa infinito.


**Número del Grupo** Grupo 6
