import java.util.List;

import processing.core.PImage;

class House extends Entity {

    public static final String HOUSE_KEY = "house";
    public static final int HOUSE_NUM_PROPERTIES = 0;
    public House(String id, Point position, List<PImage> images) {
        super(id, position, images, 0);
    }

    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        throw new UnsupportedOperationException("Unimplemented method 'executeActivity'");
    }

    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
    }


}
