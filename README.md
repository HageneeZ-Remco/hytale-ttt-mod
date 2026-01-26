## Trouble in Trork Town (TTT) – Hytale Mod

**Trouble in Trork Town** is an open-source gamemode for **Hytale**, inspired by the classic _Trouble in Terrorist Town_
from Garry’s Mod.

Players are assigned hidden roles each round and must rely on deduction, investigation, and deception to survive.
Innocents attempt to uncover the truth, traitors sow chaos from the shadows, and detectives use special tools to piece
everything together—before it’s too late.

The project was prototyped during Hytale’s first week and is designed to be**modular, configurable, and extensible**,
making it easy for the community to build on top of it.

***

## Current Features

### 🎭 Roles System

* **Innocent** – Work together to identify and eliminate traitors.
* **Traitor** – Secretly eliminate innocents and the detective.
* **Detective** – A special innocent role with investigative equipment.
* **Spectator** – Dead players can freely observe without interacting.
* Always-visible role UI for alive players.
* Role ratios are fully configurable.

> Planned: separate chat channels for alive and dead players.

***

### 🔫 Weapons & Equipment

![Image showing the traitor special shop](https://media.forgecdn.net/attachments/description/null/description_ec3f1b76-8cdf-49b7-beb6-5e7b061b243e.png)

* **Configurable equipment store** for traitors and detectives.
* Items can be purchased with credits or looted from dead players.
* Planned items:
    * Detective Skin
    * Heal Totem
    * DNA Scanner
    * Traitor Knife
* Weapon spawning integrated directly into maps.

***

### 🗺️ Custom Map System

Work In Progress...

***

### 🔁 Round & Phase System

![Image showing the starting state](https://media.forgecdn.net/attachments/description/null/description_2abaec02-a468-458b-b7b1-3bea76bd5f49.png)

![Image showing a traitor player](https://media.forgecdn.net/attachments/description/null/description_ac62be34-af26-4a04-86c8-afdec3413289.png)

Rounds are event-driven and divided into clear phases:

1. **Waiting**

    1. Players spawn and explore freely.

    2. Round starts automatically when enough players join.

2. **Playing**

    1. Roles are assigned.

    2. Deaths affect win conditions and karma.

    3. Dead players become spectators.

3. **Aftermath**

    1. Cooldown before the next round.

    2. Ideal for map voting or short downtime.

Win conditions are evaluated dynamically as players die.

***

### ⚖️ Karma System

A configurable karma system that encourages fair play:

* Team kills reduce karma.
* Correct kills increase karma.
* All karma values are configurable.

Planned automatic punishments:

* Temporary bans when karma drops below a threshold.

***

### 🪦 Graves & Body Confirmation

![Image showing a gravestone](https://media.forgecdn.net/attachments/description/null/description_df4cc89e-7af8-440f-9cd6-092265f87706.png)

![Image showing the gravestone GUI](https://media.forgecdn.net/attachments/description/null/description_e7843cd2-1cdd-43a1-abf1-b30d054317c3.png)

* Dead players leave behind**graves**.
* Graves can be inspected to:
    * Confirm death
    * Reveal information about the victim
* Prevents instant role revelation and preserves deduction gameplay.

***

### 📊 Scoreboard

![Image showing the scoreboard](https://media.forgecdn.net/attachments/description/null/description_2971fff6-6c2f-40aa-a5a6-8daa54d6aaca.png)

* Displays:
    * Kills / deaths
    * Karma
    * Role (revealed only after death)
* Traitor roles are hidden from alive players.

***

### 🌍 Localization

* Fully translation-ready.
* All text is externalized via language files.
* English and Spanish included by default.

***

## 🗺️ Roadmap

This project is under active development. The short-term roadmap focuses on **core infrastructure** needed to support
richer gameplay and community-made content.

### Phase 1 – Map System (Next Priority)

The first major milestone is a fully featured **custom map system**, allowing servers to define, rotate, and manage maps
independently of the gamemode logic.

Planned features:

* **Template Worlds as Maps:** Support using predefined world templates as playable maps.
* **Rounds per Map Configuration:** Configure how many rounds are played before switching maps.
* **Map Voting:** Allow players to vote for the next map at the end of a match.
* **Map Instantiation:** Dynamically load and initialize map instances from templates.
* **Player Teleportation on Round Preparation:** Automatically teleport players to the correct spawn points when a new
  round or map starts.

This phase lays the foundation for scalable servers, custom content, and smooth round transitions.

***

### Phase 2 – Weapon Spawning on Maps

Once the map system is stable, development will focus on **map-driven weapon distribution**, enabling more dynamic and
balanced gameplay.

Planned features:

* **Custom Weapon Spawn Points:** Define weapon spawn locations directly in map templates.
* **Weapon Categories & Ammunition:** Support weapon types (e.g. melee, ranged, utility) and associated ammo pools.
* **Loot Chest Integration:** weapon and ammo distribution through loot chests placed in maps.

This phase enables map authors to fully control how and where equipment enters the game.

***

## Backlog & Planned Features

* Expanded detective and traitor equipment
* More weapon variety
* Map voting system
* Separate chat channels (alive / dead / spectator)
* Karma-based punishments and reputation system
* Improved UI/UX polish
* Better modding hooks for third-party extensions

***

## Open-Source Philosophy

This mod is **fully open-source** and built with extensibility in mind.

* ECS-oriented design
* Contributions, maps, translations, and feature ideas are welcome

The goal is not just to recreate TTT, but to provide a **community-driven foundation** for gamemodes in Hytale.

***