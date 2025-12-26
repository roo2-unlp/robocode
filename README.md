# Robocode

![Robocode logo](https://robocode.sourceforge.io/gfx/robocode_logo_tanks.png)

# Feature: Bullets de Impacto y Proximidad
### Grupo 4

**Integrantes:**
- Leonardo Delmas
- Leandro Joaquín Canosa

## Enunciado
> *"Los proyectiles pueden descontar energía por impacto (colisión) con un robot o pueden dañar con menor intensidad si pasan cerca (proximidad) de un robot. Se puede pensar que los proyectiles por proximidad son 'radioactivos' y que descuentan energía en función de la distancia a la que pasan de un robot. El proyectil radioactivo no debería dañar al robot que la disparó."*

---

## Patrón Strategy: Detección de Colisión de Proyectiles

Se implementaron los diferentes tipos de proyectiles (impacto y proximidad) utilizando el patrón **Strategy**, permitiendo intercambiar el algoritmo de detección de colisión sin modificar la clase `BulletPeer`.

### Diagrama de Clases

```
┌─────────────────────────────────┐
│    IBulletCollisionStrategy     │ ◄─── Interface
├─────────────────────────────────┤
│ + checkRobotCollision()         │ → List<RobotPeer>
│ + handleImpact()                │
│ + getAdjustedPower()            │
│ + configureBullet()             │
└─────────────────────────────────┘
              ▲
              │ implements
    ┌─────────┴─────────┐
    │                   │
┌───┴───────────────┐  ┌┴──────────────────────────┐
│ NormalBullet      │  │ ProximityBullet           │
│ CollisionStrategy │  │ CollisionStrategy         │
├───────────────────┤  ├───────────────────────────┤
│ (Singleton)       │  │ - robotsInsideRadius: Set │
│ Colisión por      │  │ Colisión por radio        │
│ intersección      │  │ Daño en área (AoE)        │
│ de línea          │  │ Daño escalado por         │
└───────────────────┘  │ distancia                 │
                       └───────────────────────────┘

┌─────────────────────────────────┐
│          BulletPeer             │
├─────────────────────────────────┤
│ - collisionStrategy: IStrategy  │ ◄─── Composición
│ + checkRobotCollision(robots)   │ → Delega al strategy
└─────────────────────────────────┘
```

### Características de Proximidad

- **Detonación por entrada**: El proyectil detona cuando cualquier robot *entra* al radio (no mientras permanece adentro)
- **Daño en área (AoE)**: Al detonar, **todos** los robots dentro del radio reciben daño
- **Daño escalado por distancia**: 90% del poder en el centro → 10% en el borde del radio
- **Protección al dueño**: El robot que disparó nunca recibe daño de su propio proyectil

### Componentes

| Clase | Responsabilidad |
|-------|-----------------|
| `IBulletCollisionStrategy` | Interface que define el contrato para estrategias de colisión |
| `NormalBulletCollisionStrategy` | Detecta colisión cuando la línea de movimiento del proyectil intersecta el bounding box del robot |
| `ProximityBulletCollisionStrategy` | Detecta cuando robots entran al radio de proximidad. **Daño en área**: al detonar, daña a **todos** los robots dentro del radio |
| `BulletCollisionStrategyFactory` | Factory con registro `EnumMap<BulletType, Supplier>` para crear estrategias polimórficamente |

---

## Batallas de Prueba

Para motivos de testeos se pueden ejecutar las siguientes batallas de prueba:

### Test 1: Visualización del Radio de Proximidad

**Objetivo:** Verificar visualmente el radio de proximidad y la detonación al entrar un robot.

**Robots:**
| Robot | Comportamiento |
|-------|----------------|
| `Shooter` | Dispara proyectiles de proximidad hacia el enemigo |
| `Sleeper` | Permanece quieto en su posición inicial |

**Configuración:**
1. Iniciar Robocode
2. Crear nueva batalla: `Battle` → `New`
3. Agregar robots: `testrobots.Shooter` y `testrobots.Sleeper`
4. Configurar el radio de proximidad: `Rules` → **`Proximity Radius`**
5. Iniciar batalla

**Qué observar:**
- El círculo visual del radio de proximidad alrededor del proyectil
- La detonación ocurre cuando el radio "toca" al robot Sleeper
- El Sleeper recibe daño escalado según la distancia al centro del proyectil

---

### Test 2: Daño en Área (AoE)

**Objetivo:** Comprobar que al detonar, el proyectil daña a **todos** los robots dentro del radio simultáneamente.

**Robots:**
| Robot | Comportamiento |
|-------|----------------|
| `Shooter` | Dispara proyectiles de proximidad |
| `Charger` (x2) | Se mueven agresivamente hacia el Shooter |

**Configuración:**
1. Iniciar Robocode
2. Crear nueva batalla: `Battle` → `New`
3. Agregar robots: `testrobots.Shooter` y 2x `testrobots.Charger`
4. (se recomienda) Configurar al máximo el radio de proximidad: `Rules` → **`Proximity Radius`** (en el valor máximo)
5. Iniciar batalla

**Qué observar:**
- Cuando ambos Chargers están dentro del radio de proximidad y uno de ellos *entra* al radio, el proyectil detona
- **Ambos Chargers reciben daño** en el mismo instante (daño en área)
- El daño de cada Charger varía según su distancia al centro de la explosión
- El Shooter (dueño del proyectil) **no recibe daño** aunque esté cerca
