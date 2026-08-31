import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import processing.core.PImage;

public class Fairy extends Actionable implements Moveable{
    public static final int FAIRY_ANIMATION_PERIOD_IDX = 0;
    public static final int FAIRY_ACTION_PERIOD_IDX = 1;
    public static final int FAIRY_NUM_PROPERTIES = 2;
    public static final String FAIRY_KEY = "fairy";

    //Switch commented line for different path finding
    private PathingStrategy strategy = new SingleStepPathingStrategy();
    //private PathingStrategy strategy = new AstarPathingStrategy();

    public Fairy(String id, Point position, List<PImage> images, double animationPeriod,
                 double activityPeriod) {
        super(id, position, images, 0, animationPeriod, activityPeriod);
    }

    public boolean move(WorldModel world, Entity target, EventScheduler scheduler) {
        if (this.getPosition().adjacent(target.getPosition())) {
            world.removeEntity(scheduler, target);
            return true;
        } else {
            Point nextPos = nextPosition(world, target.getPosition());
            if (!getPosition().equals(nextPos)) {
                world.moveEntity(scheduler, this, nextPos);
            }
            return false;
        }
    }

    /** Determines a Fairy's next position when moving. */
    public Point nextPosition(WorldModel world, Point destPos) {
        List<Point> path = strategy.computePath(
                getPosition(),
                destPos,
                p -> world.withinBounds(p) && !world.isOccupied(p), // canPassThrough
                Point::adjacent, // withinReach
                PathingStrategy.CARDINAL_NEIGHBORS // potentialNeighbors
        );

        return path.isEmpty() ? getPosition() : path.get(0);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> fairyTarget = world.findNearest(getPosition(), new ArrayList<>(List.of(Stump.class)));

        if (fairyTarget.isPresent()) {
            Point tgtPos = fairyTarget.get().getPosition();

            if (move(world, fairyTarget.get(), scheduler)) {
                Sapling sapling = new Sapling(Sapling.SAPLING_KEY + "_" + fairyTarget.get().getId(), tgtPos, imageStore.getImageList(Sapling.SAPLING_KEY),0);

                world.addEntity(sapling);
                sapling.scheduleActions(scheduler, world, imageStore);
            }
        }
        Activity a = new Activity(this, world, imageStore);
        scheduler.scheduleEvent(this, a, this.getActivityPeriod());
    }
}
