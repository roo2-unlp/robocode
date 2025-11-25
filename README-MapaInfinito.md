# Mapa Infinito — Branch

**Objetivo** Modificar el sistema de juego para que admita el comportamiento “mapa infinito”.

**Definición de "Mapa Infinito"** Un escenario finito que simula ser infinito mediante la teletransportación del robot al lado opuesto del mapa en el momento en el que cruza alguno de los bordes, manteniendo su velocidad.

**Lógica de Teletransporte**

1. Se detecta colisión con el límite del mapa.
2. La posición del robot se actualiza al lado opuesto.
3. La velocidad se mantiene.

**Partes del código modificadas**

- RobotPeer.java - Modificación de logica de colisiones con los limites del mapa.
- README-MapaInfinito.md — documentación del nuevo comportamiento

**Número del Grupo** Grupo 6
