# Virtual World: Forest Simulation with A* Pathfinding

A 2D grid world in Java where autonomous entities go about their business: gatherers
harvest wood and carry it to houses, fairies revive dead stumps into trees, saplings
grow, and you can drop a meteor on the whole thing with a mouse click. Rendering is
done with Processing.

I built this to get comfortable with object-oriented design on a codebase big enough
that bad decisions actually hurt. The two things I care about in it are the entity
hierarchy and the A* implementation.

## What happens in the world

Entities are scheduled rather than updated in a loop. Each one registers activities
and animations with an `EventScheduler` at its own rate, so a fast-moving fairy and a
slow-growing sapling coexist without a global tick.

- **Dudes** gather wood from trees and saplings, carry it to a house, and switch
  behavior when full (`DudeNotFull` → `DudeFull`). A meteor nearby turns them into
  `DudeScared`, which runs away instead of working.
- **Fairies** look for stumps and turn them back into saplings, which then grow into
  trees. The forest can recover from being clear-cut.
- **Trees, saplings, and stumps** have their own lifecycles; trees get trampled down
  to stumps, saplings mature or get destroyed.
- **Caterpillars and obstacles** move around and block paths.
- **Left-clicking** drops a meteor: it replaces terrain with rock and frightens any
  dudes in range.

## Design

`Entity` is the abstract base holding position and sprite animation state. Above it,
`Actionable` adds scheduled behavior and `Resourceable` adds health and resource
tracking. Movement and state changes are interfaces (`Moveable`, `Transformeable`)
rather than inheritance, because a fairy and a dude both move but share almost nothing
else.

Pathfinding sits behind a `PathingStrategy` interface with two implementations:

- **`AstarPathingStrategy`**: A* using a Manhattan-distance heuristic, a
  `PriorityQueue` ordered on f = g + h for the open set, a `HashSet` closed set, and a
  map of best known g-costs so a node is only re-queued when a cheaper route to it
  turns up. Neighbors arrive as a `Stream<Point>` filtered by a passability predicate,
  and the caller supplies its own "close enough" test, so the same search works for
  entities that need to reach a tile versus stand next to one.
- **`SingleStepPathingStrategy`**: a greedy one-step move. It's the baseline I
  compared against, and it gets stuck on concave obstacles the way you'd expect.

## Running it

Requires a JDK. The IntelliJ project is configured for JDK 23 (`.idea/misc.xml`);
anything recent should compile, though I've only run it on 23. Every dependency is
vendored in `lib/`, including the Processing core, so there's nothing to download.

Open the project in IntelliJ, confirm the SDK under **Project Structure → Project**,
then run `src/VirtualWorld.java`.

- **Arrow keys** pan the viewport
- **Left click** drops a meteor

The starting world is read from `world.sav`, and `imagelist` maps entity keys to the
sprites in `images/`. Editing `world.sav` is the quickest way to set up a specific
scenario.

## Tests

`src/WorldTests.java` holds 20 JUnit 5 tests covering save-file parsing, grid bounds,
entity placement, the trample and lifecycle transitions, dude carry limits, and both
pathfinding strategies. They run headlessly through `VirtualWorld.headlessMain`, which
steps the scheduler a fixed number of ticks and returns entity positions as strings,
so no display is needed and results are deterministic.

Run them from IntelliJ (right-click `WorldTests` → Run). The JUnit 5 jars in `lib/`
are the API and engine only; the standalone console launcher isn't vendored, so
there's no working command-line invocation without adding
`junit-platform-console-standalone` yourself.

## Layout

```
src/          Java sources: entities, world model, view, pathfinding, tests
images/       Sprite assets
lib/          Vendored jars (Processing core, JUnit 5)
imagelist     Entity key → sprite file mapping
world.sav     Default starting world
```

## Limitations

The sprite and world-file formats are positional and unforgiving; a malformed line in
`world.sav` fails in ways that aren't always obvious. There's no packaging or build
file; it's an IntelliJ project with jars on the classpath, which is how it was written
and not how I'd set it up now.
