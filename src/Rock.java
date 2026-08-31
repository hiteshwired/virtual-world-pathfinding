import java.util.List;

import processing.core.PImage;

public class Rock extends Entity{
    public static final int ROCK_NUM_PROPERTIES = 0;

    public static final String ROCK_KEY = "rock";

    public Rock(Point position, List<PImage> images) {


        super(" ", position, images, 0);
    }

    @Override
    public void nextImage() {
        throw new UnsupportedOperationException("Unimplemented method 'nextImage'");
    }

    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        throw new UnsupportedOperationException("Unimplemented method 'executeActivity'");
    }

    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        throw new UnsupportedOperationException("Unimplemented method 'scheduleActions'");

    }

}

