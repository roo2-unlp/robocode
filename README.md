# Robocode (Infinity Shot Edition)

![Robocode logo](https://robocode.sourceforge.io/gfx/robocode_logo_tanks.png)

**Motto: _Build the best — destroy the rest!_**

**Robocode** is an engaging and educational [programming game] that allows you to code, test, and improve robot battle tanks. The game's primary aim is to teach programming skills in a fun and interactive environment.

### 🚀 New Feature: Infinity Shot Mode
This version introduces the **Infinity Shot** mechanic. Bullets don't just stop at the walls—they teleport to the other side!

With Robocode, you can:

- **Learn to Program**: Develop programming skills in languages like Java and Kotlin.
- **Create Battle Tanks**: Design and program virtual robots that compete against each other in battles.
- **Master Toroidal Combat**: Fight in an arena where bullets can loop around the map multiple times.
- **Improve Problem-Solving**: Enhance your algorithmic thinking by writing code that anticipates threats from all directions.

---

## 🌀 Infinity Shot Feature Breakdown

The **Infinity Shot** update changes how projectiles interact with the battlefield boundaries.

### 1. Wall Wrapping
The concept is simple: when a bullet hits a wall, it instantly teleports to the opposite side.
* **Horizontal Wrap:** Bullets hitting the **Right** wall appear on the **Left**.
* **Vertical Wrap:** Bullets hitting the **Top** wall appear on the **Bottom**.
* The bullet maintains its trajectory, allowing it to cross the map multiple times.

### 2. How to Configure
You can fully customize this feature in the **Battle Rules** tab before starting a match:

1.  **Enable Infinity Shot:** Look for the new checkbox. Checking it activates the mode.
2.  **Set Bullet Lives:** Once enabled, the input field next to it becomes active. Here you can define **how many times** a bullet is allowed to wrap around the map (loops) before it finally explodes.

### 3. Strategic Changes
* **No Safe Zones:** You can no longer hide against a wall with your back covered. An enemy in front of you can shoot "through" the wall behind them to hit your back.
* **Long-Range Sniping:** Bullets can travel much longer distances (depending on the configured laps), making energy management and prediction even more critical.

---

## 🛠️ Developer Guide

If you want to modify the source code, run the project locally, or execute tests, follow these steps.

### Prerequisites
* **Java 8 (JDK 1.8):** This project requires Java 8 to compile and run. Please ensure your `JAVA_HOME` environment variable is pointing to a JDK 8 installation.

### Build & Run
To compile the project and start the Robocode UI, use the included Gradle wrapper. Open your terminal in the project root:

**Windows:**
```bash
./gradlew build
./gradlew :robocode.tests:test --tests "net.sf.robocode.test.robots.TestInfinityShot"