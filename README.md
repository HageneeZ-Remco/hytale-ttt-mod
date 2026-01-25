# Trouble in Terrorist Town (TTT) Features

Trouble in Terrorist Town (TTT) is a social deduction game where players are assigned roles and must work together (or
against each other) to achieve their objectives. Below is a list of essential features that a TTT game should include:

## Core Features

### 1. **Roles**

- **Innocents**: Work together to identify and eliminate the traitors.
- **Traitors**: Secretly work to eliminate all innocents and the detective.
- **Detective**: A special innocent role with unique tools to investigate and identify traitors.

### 2. **Weapons and Tools**

- **Knife (Traitor)**: A silent weapon for traitors to eliminate players stealthily.
- **Guns (Detective and Innocents)**: Firearms for self-defense and eliminating threats.
- **Special Equipment**:
    - Traitors: C4 explosives, disguises, and radar.
    - Detective: DNA scanner, health station, and binoculars.

### 3. **Rounds**

- Each game consists of multiple rounds.
- Players are assigned roles at the start of each round.
- Rounds end when either the traitors or innocents achieve their objectives.

### 5. **Karma System**

- Karma tracks player behavior:
    - Killing teammates or innocents reduces karma.
    - Killing traitors increases karma.
- Low karma results in penalties, such as reduced damage output.

### 6. **Scoreboard**

- Displays player stats:
    - Role (revealed after death).
    - Kills and deaths.
    - Karma.
- Updates dynamically after each round.

### 7. **Map Features**

- Interactive environments with traps and secrets.
- Designated areas for traitor equipment purchases.

### 8. **Deathmatch Events**

- After X amounts of rounds, a special round with deatch match modality should take placed if option enabled

### 9. **Customizable Settings**

- Adjustable round time limits.
- Configurable role ratios (e.g., number of traitors per innocent).

## Immplementation plan

### 1. Roles

- [X] Custom UI always on screen that shows the assigned role for the player
- There are a list of predefined player roles:
    - TRAITOR
        - Starts with a knife that kills players with 3 hits
    - INNOCENT
        - Should follow the detective orders (role based gameplay)
        - Can grab weapons from the floor
    - DETECTIVE
        - Starts with a wand (Proven weapon)
        - Nametag should be tinted blue
    - SPECTATOR
        - are only visible among them
            - Invisible effect (?)
        - can fly as ghosts
        - can not grab weapons or interact with the world
    - There should be separate chats between living and dead players

### 2. **Weapons and Tools**

- **Knife (Traitor)**: Modified sword model with increased damage.
- **Guns (Detective and Innocents)**: TBD already existing guns of the game.
- **Special Equipment**:
    - Detective: "Proven weapon", magic wand with 2 ammo that shoots a projectile which kills the detective if the
      target player was innocent, or in the contrary kills the target player if it was a traitor.

### 3. **Rounds**

- Event system to define the round phases:
    - Phases:
        - Waiting for players
            - Players are spawned, and can walk around the world.
            - When the required player amount is reunited the START_ROUND event is triggered.
        - Playing
            - At the beginning assign player roles
                - Detective role should only be given to one player and start appearing after N amount of players
                - Assignable traitor roles should be a X% of player count
            - On player death:
                - If it was a traitor and there are no remaining traitors, innocents win
                - If it was an innocent and there are no remaining innocents, traitors win
                - Adjust killer karma based on rules and trigger a karma check
            - Both cases trigger the END_ROUND event.
            - All players that die here can not respawn, should vanish into spectator mode
        - Aftermatch
            - Configurable time that lets players rest until next round. Useful for voting maps.

### 5. **Karma System**

- [X] Custom UI that shows player karma score,
    - [ ] and paints the value red based on if its below 33% starting karma, yellow if it is below 66%, and green above
      or equal to 66%
- Karma starts with N points, and each time:
    - An innocent or detective kills a traitor karma gets incremented U points
    - A traitor kills an innocent karma gets incremented V points
    - An innocent kills an innocent karma gets reduced W points
    - An innocent kills a detective karma gets reduced X points
    - A detective kills and innocent karma gets reduced Y points
    - A traitor kills a traitor karma gets reduced Z points
- N, U, V, W, X, Y, Z should be configurable values
- If karma gets below an A amount the player is banned for T amount of time
    - A, T configurable

### 6. **Scoreboard**

- [X] Custom toggeable UI that shows players kills, deaths, karma
- Should always display detective role
- Should show traitor roles only when beeing a traitor
- Updates dynamically after each round

### 7. **World rules**

- [X] Inventory should be disabled
- [X] Crafting should be disabled
- AFK players should be kicked after a configurable amount of time
- Weapons should spawn on the floor at the preparing round phase

## Additional Considerations

GameStates:

- PREPARING
- STARTING
- IN_GAME
- AFTER_GAME

Events:

- START_NEW_ROUND:
    1. GameState gets setted to **STARTING**
    2. All roles should be cleaned
    3. All spectators should transition to survival
    4. All inventories should be cleaned
    5. After X amount of time game transitions to **IN_GAME**
    6. Roles get setted to all players and display their roles on screen.

Victory Check Process:

- This process is triggered after relevant events (e.g., player death, player disconnect) to determine if the round
  should end.
- If victory conditions are met:
    - GameState changes to AFTER_GAME
    - Show notification to all players saying the winning team
    - Karma updates should be applied to all players
    - After x amount of time:
        - If there are enough players, GameState triggers **START_NEW_ROUND**
        - If there are not enough players, GameState changes to **PREPARING**

### When the server starts:

- Game state is PREPARING
- Karma should be resetted for every player

### When a player joins:

- Remove any items the player had.
- Remove any role the player had.
- If the game state is **PREPARING** and there are enough players, triggers START_NEW_ROUND event. Game state changes to
  STARTING
- If the game state is **PREPARING** and there are not enough players. Nothing happens, player spawns normally.
- If the game state is **STARTING**, player spawns normally.
- If the game state is **IN_GAME** or **AFTER_GAME**, set the player to spectator mode.

### When a player disconnects:

- Remove any items the player had.
- Remove any role the player had.
- If the game state is **PREPARING** or **AFTER_GAME**. Nothing happens
- If the game state is **STARTING** and there are not enough players left, game state changes to **PREPARING**
- If the game state is **IN_GAME**, substract karma from player, and check victory conditions to determine if the round
  should end.

### When a player dies:

- Check victory conditions to determine if the round should end.
- Remove any items the player had.
- Set the player to spectator mode.

### Spectator Mode:

- Make the player invisible.
- Block all interactions.
- Prevent the player from dealing damage to others.
- Set the player to creative mode.
