import java.util.List;
import java.util.Optional;
import processing.core.PImage;

public abstract class Dude extends Resourceable implements Moveable, Transformeable {
    public static final String DUDE_KEY = "dude";
    public static final int DUDE_ACTION_PERIOD_IDX = 0;
    public static final int DUDE_ANIMATION_PERIOD_IDX = 1;
    public static final int DUDE_RESOURCE_LIMIT_IDX = 2;
    public static final int DUDE_NUM_PROPERTIES = 3;

    //Switch commented line for different path finding
    private PathingStrategy strategy = new SingleStepPathingStrategy();
    //private PathingStrategy strategy = new AstarPathingStrategy();
    private boolean isScared = false;

    public Dude(String id, Point position, List<PImage> images, double animationPeriod, double activityPeriod, int resourceLimit) {
        super(id, position, images, animationPeriod, activityPeriod, resourceLimit, 0, 0);
    }

    public abstract void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);

    public boolean move(WorldModel world, Entity target, EventScheduler scheduler) {
        if (this.getPosition().adjacent(target.getPosition())) {
            return true;
        } else {
            Point nextPos = this.nextPosition(world, target.getPosition());
            if (!this.getPosition().equals(nextPos)) {
                world.moveEntity(scheduler, this, nextPos);
            }
            return false;
        }
    }



    public Point nextPosition(WorldModel world, Point destPos) {
        List<Point> path = strategy.computePath(
                getPosition(),
                destPos,
                p -> world.withinBounds(p) && (!world.isOccupied(p) || world.getOccupancyCell(p) instanceof Stump), // canPassThrough
                Point::adjacent, // withinReach
                PathingStrategy.CARDINAL_NEIGHBORS // potentialNeighbors
        );

        return path.isEmpty() ? getPosition() : path.get(0);
    }

public boolean isScared() {
    return isScared;
}

public void setScared(boolean scared) {
    isScared = scared;
}
}