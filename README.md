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

Robocode is suitable for all levels—from beginners taking their first steps in coding to experienced developers looking for a fun challenge. Whether you are learning to program or just seeking a stimulating activity, Robocode offers a platform where you can have fun while honing your skills.

Get started with Robocode today and unleash your inner programmer!

- [Robocode home page]
- [Introduction] to Robocode
- [RoboWiki] is the best way to learn about Robocoding
- [Robocode group] is where you can ask questions
- [Facebook group] is a community for enthusiasts of the Robocode programming game
- [Robocode Application Developers] is for people that want to develop or experiments with the Robocode application (game)
- [Robocode Guide for building Robocode], if you want to build Robocode yourself

Also note that a new version of Robocode supports C# as well (and more languages in the future):

- [Robocode Tank Royale] is a new platform for Robocode

Happy Robocoding! 🤖⌨️

[programming game]: https://x-team.com/magazine/coding-games "23 Programming Games to Level Up Your Programming Skills"

[Robocode home page]: https://robocode.sourceforge.io/ "Home page for Robocode"

[Introduction]: https://robocode.sourceforge.io/docs/ReadMe.html "Introduction into Robocode"

[RoboWiki]: https://robowiki.net/ "RoboWiki - Collecting Robocode knowledge since 2003"

[Robocode group]: https://groups.google.com/g/robocode "The Robocode Group"

[Facebook group]: https://www.facebook.com/groups/129627130234/ "The Facebook group for Robocode"

[Robocode Guide for building Robocode]: https://robowiki.net/wiki/Robocode/Developers_Guide_for_building_Robocode "The guide for how to how to build Robocode (the game)"

[Robocode Application Developers]: https://groups.google.com/g/robocode-developers "Group for developers of the Robocode application"

[Robocode Tank Royale]: https://github.com/robocode-dev/tank-royale/blob/main/README.md "Robocode Tank Royale"