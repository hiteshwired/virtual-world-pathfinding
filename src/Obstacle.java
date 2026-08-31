import processing.core.PImage;

import java.util.List;

public class Obstacle extends Actionable{
    public static final String OBSTACLE_KEY = "obstacle";
    public static final int OBSTACLE_ANIMATION_PERIOD_IDX = 0;
    public static final int OBSTACLE_NUM_PROPERTIES = 1;
    public Obstacle(String id, Point position, List<PImage> images, int imageIndex, double animationPeriod, double activityPeriod) {
        super(id, position, images, 0, animationPeriod, 0);
    }
    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        throw new UnsupportedOperationException("Unimplemented method 'executeActivity'");
    }

    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        this.scheduleAnimation(scheduler, world, imageStore);
    }

}
