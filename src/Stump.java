import java.util.List;

import processing.core.PImage;

public class Stump extends Entity{
    public static final int STUMP_NUM_PROPERTIES = 0;

    public static final String STUMP_KEY = "stump";

    public Stump(String id, Point position, List<PImage> images) {
        super(id, position, images, 0);
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
    }

}
