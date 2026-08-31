# Forest Simulation & Virtual World

An interactive 2D grid-based ecosystem simulation and game engine implemented in Java using the Processing library. The simulation models autonomous agents, natural resources, lifecycles, and environmental dynamics on a tile grid, powered by an event-driven scheduler and intelligent pathfinding strategies.

---

## Overview

In this virtual world, autonomous entities interact with the environment according to customized behaviors and rules:
- **Dudes (`DudeFull`, `DudeNotFull`, `DudeScared`):** Resource gatherers that navigate the world to harvest wood from Trees and Saplings, deposit resources at Houses, and respond dynamically to environmental hazards.
- **Fairies:** Forest caretakers that roam the grid seeking Stumps to revitalize them into living Trees.
- **Flora (`Tree`, `Sapling`, `Stump`):** Plant life that grows, undergoes lifecycle transitions, provides resources, or transforms upon depletion.
- **Caterpillars & Obstacles:** Secondary creatures and terrain features that populate the landscape.
- **Interactive Meteor Impact:** Real-time mouse interactions allow the user to trigger meteor strikes, creating rock craters, altering terrain, and frightening nearby entities.

---

## Architecture & OOP Design Patterns

The project emphasizes object-oriented principles, modularity, and clean separation of concerns:

### 1. Entity Hierarchy & Interfaces
- **`Entity`**: Base abstract class managing entity identity, position coordinates, and sprite animations.
- **`Actionable`**: Extends `Entity` to support scheduled recurring activities via the `EventScheduler`.
- **`Resourceable`**: Extends `Actionable` to incorporate health and resource tracking for plants and gatherers.
- **`Moveable`**: Interface defining movement and destination-targeting contracts across entities (`Dude`, `Fairy`, `Caterpillar`).
- **`Transformeable`**: Interface defining state transitions (e.g., `DudeNotFull` transforming to `DudeFull` upon reaching capacity, or `Sapling` growing into a `Tree`).

### 2. Strategy Pattern for Pathfinding
Pathfinding algorithms are decoupled from entity logic using the `PathingStrategy` interface:
- **`AstarPathingStrategy`**: Implements the A* search algorithm using Manhattan distance heuristics and an optimized priority queue open set / closed set to find the shortest obstacle-free path.
- **`SingleStepPathingStrategy`**: A simplified greedy single-step movement strategy used for baseline movement and regression verification.

### 3. Event-Driven Scheduling
- **`EventScheduler`**: Priority-queue-based event loop tracking simulation timestamps.
- **`Activity` & `Animation`**: Discrete scheduled actions that trigger behavioral updates and frame animations at independent rates.

---

## Project Structure

```text
├── images/                  # Sprite assets for entities and terrain tiles
├── lib/                     # Library dependencies (Processing, JUnit 5)
├── src/                     # Java source code
│   ├── AstarPathingStrategy.java     # A* graph search implementation
│   ├── SingleStepPathingStrategy.java# Single-step greedy pathing
│   ├── PathingStrategy.java          # Strategy interface for pathfinding
│   ├── Dude.java                     # Abstract base class for Dude gatherers
│   ├── DudeFull.java                 # Full inventory state
│   ├── DudeNotFull.java              # Gathering state
│   ├── DudeScared.java               # Frightened state from meteor strikes
│   ├── Fairy.java                    # Fairy entity logic
│   ├── Caterpillar.java              # Caterpillar entity logic
│   ├── Tree.java / Sapling.java      # Plant lifecycle entities
│   ├── Stump.java / Rock.java        # Static / obstacle entities
│   ├── House.java / Obstacle.java    # Environmental structures
│   ├── EventScheduler.java           # Simulation clock & queue
│   ├── WorldModel.java               # Spatial grid model & entity management
│   ├── WorldView.java                # Viewport rendering & camera
│   ├── VirtualWorld.java             # Main Processing application entrypoint
│   └── WorldTests.java               # JUnit test suite
├── imagelist                # Mapping of entity keys to sprite image files
├── world.sav                # Default world save configuration
└── README.md                # Project documentation
```

---

## Getting Started

### Prerequisites
- **Java Development Kit (JDK):** Version 17 or higher (tested with OpenJDK 23).
- **IDE:** IntelliJ IDEA (recommended) or any standard Java IDE / CLI build tools.

### Running the Virtual World
You can launch the graphical simulation by running `VirtualWorld`:

#### From IntelliJ IDEA:
1. Open the project directory in IntelliJ IDEA.
2. Ensure the JDK is configured in **Project Structure > Project Settings > Project SDK**.
3. Run `src/VirtualWorld.java`.

#### Controls:
- **Arrow Keys (Up / Down / Left / Right):** Pan the camera viewport across the world.
- **Left Mouse Click:** Trigger a meteor strike at the target tile, spawning craters and frightening nearby Dudes.

---

## Running Automated Tests

The test suite validates entity parsing, lifecycle transitions, trampling mechanics, event scheduling, and pathfinding accuracy.

You can run `WorldTests` directly in IntelliJ IDEA or via JUnit 5 runner:
```bash
# Example running test suite via command line (Windows PowerShell)
java -classpath "out/production/Project2Forestv2;lib/*" org.junit.platform.console.ConsoleLauncher --scan-class-path
```

All unit tests in `WorldTests.java` are verified and passing.
