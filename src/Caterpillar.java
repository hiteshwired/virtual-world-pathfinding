import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import processing.core.PImage;

public class Caterpillar extends Actionable implements Moveable {
    private PathingStrategy strategy = new AstarPathingStrategy();
    public static final String CATERPILLAR_KEY = "caterpillar";
    public static final double CATERPILLAR_ANIMATION_PERIOD = 0.500;
    public static final double CATERPILLAR_ACTION_PERIOD = 0.500;

    public Caterpillar(String id, Point position, List<PImage> images, double animationPeriod, double activityPeriod) {
        super(id, position, images, 0, animationPeriod, activityPeriod);
    }
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> target;
        target = world.findNearest(this.getPosition(), new ArrayList<>(Arrays.asList(Sapling.class)));


        if (target.isEmpty() || !this.move(world, target.get(), scheduler)) {
            scheduleActivity(scheduler, world, imageStore);
        }
    }

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
    public boolean move(WorldModel world, Entity target, EventScheduler scheduler) {

        if (this.getPosition().adjacent(target.getPosition())) {
            Resourceable plant = ((Resourceable) target);
            plant.setHealth(plant.getHealth()-1);
            return true;
        } else {
            Point nextPos = nextPosition(world, target.getPosition());

            if (!this.getPosition().equals(nextPos)) {
                world.moveEntity(scheduler, this, nextPos);
            }
            return false;
        }
    }
}
