# Robocode feature - Random Collision Damage - Grupo 5
![alt text](image-2.png)



## 👥 Integrantes
* **Javier Arias**
* **Francisco Balbo**


## Enunciado
Randomizar el daño que los robots reciben al chocar entre sí.  
Se debe poder asignar al inicio de la batalla, de manera opcional.


Esta rama introduce la posibilidad de configurar un daño aleatorio **calculando un valor de un rango de daño (mínimo y máximo)** para la colisión entre 2 robots en Robocode, reemplazando o extendiendo el comportamiento estándar de daño fijo. 



## 🚀 Descripción de la Feature
El objetivo de esta funcionalidad es añadir un factor de variabilidad táctica a las batallas. En lugar de que cada colisión entre robots inflija un daño fijo predefinido en una constante en el código, el sistema permite definir un umbral de daño aleatorio que se calcula al momento de la colisión.

### Implementaciones clave:
* **Configuración desde la UI:** Nuevos campos de entrada en la pestaña de reglas de batalla.
* **Modelo Conmutable:** Capacidad de alternar entre el daño estándar y el aleatorio.
* **Validación de Rangos:** Control de lógica para asegurar que el daño mínimo no supere al máximo.

---

## 🏗️ Arquitectura y Diseño

Para mantener la extensibilidad y legibilidad del código (siguiendo las mejores prácticas de POO), se implementó el **Patrón Strategy** y se creó un punto de extensión que permite que además de elegir entre las 2 variables diposibles (daño default y aleatorio), ahora desarrolladores puedan extender la interfaz añadiendo un comportamiento propio para el calculo de este daño, siendo esto completamente retrocompatible con el código, robots y reglas existentes.

### Diagrama de Flujo de Datos
El siguiente diagrama detalla cómo los parámetros ingresados en la interfaz de usuario viajan a través del núcleo de Robocode hasta impactar en el cálculo de salud de los robots:

![alt text](image-1.png)
> *Figura 1: Diagrama de secuencia y flujo de datos desde la UI hacia el DamageModel.*

### Componentes Principales:
* **`IDamageModel` (Interface):** Define el contrato para el cálculo de daño.
* **`StandardDamageModel`:** Implementación por defecto que mantiene la lógica original de Robocode.
* **`RandomDamageModel`:** Nueva estrategia concreta que implementa la interfaz `IDamageModel` para determinar el daño final basado en los límites configurados.
* **`NewBattleRulesTab`:** Modificación de la UI para capturar los valores `minDamage` y `maxDamage`.

---

## 🖥️ Interfaz de Usuario (UI)

Se han añadido controles específicos en el menú de configuración de la batalla para facilitar la personalización de la feature sin necesidad de modificar archivos de propiedades manualmente.

![alt text](image.png)
> *Figura 2: Vista de los nuevos campos de configuración en la pestaña "Rules".*

---

## 🧪 Pruebas Realizadas
**Tests de unidad:** Validación de la logica en RandomDamageModel.

**Tests de integración:** Verificación de la persistencia de los valores desde la UI hacia el objeto BattleProperties y de la lógica de selección del tipo de modelo de daño.

**Testeo manual:** Ejecución de batallas de prueba observando la variabilidad de la energía de los robots tras recibir impactos.

---
## 🛠️ Setup y Ejecución

### Requisitos previos
* JDK 8 o superior.
* Gradle (incluido mediante el wrapper).

### Setup (Unix/Linux/Mac):
```bash
-- UNIX --
./gradlew build
cd .sandbox
./robocode.sh
```
### Setup (Windows):
```bash
--WINDOWS--
.\gradlew.bat build
cd .sandbox
.\robocode.bat

💡 Solución de problemas en Windows:
Si la compilación falla por procesos bloqueados o conflictos de caché:
1)Cerrar todos los IDEs (IntelliJ, Eclipse, VS Code).
2)Abrir PowerShell como Administrador.
3)Ejecutar los siguientes comandos:

.\gradlew.bat --stop
Get-ChildItem -Path . -Recurse -Directory -Filter "build" | Remove-Item -Recurse -Force

Reintentar el proceso de Setup normal.  (los paso mencinados anteriormente)
```
### Ejecución de Tests por separado:

#### Test unitario
```bash
  ./gradlew :robocode.battle:test --tests "net.sf.robocode.battle.damage.TestRandomDamageModel"
```

#### Test de integración
```bash
  -Persistencia:
    ./gradlew :robocode.core:test --tests "net.sf.robocode.battle.TestBattleProperties"
  -Logica de selección de modelo de daño desde la UI
    ./gradlew :robocode.battle:test --tests "net.sf.robocode.battle.TestBattleModeSelection"
```
