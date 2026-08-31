import java.util.List;

import processing.core.PImage;

public class Tree extends Resourceable implements Transformeable {

    public static final String TREE_KEY = "tree";
    public static final double TREE_ANIMATION_MAX = 0.600;
    public static final double TREE_ANIMATION_MIN = 0.050;
    public static final double TREE_ACTION_MAX = 1.400;
    public static final double TREE_ACTION_MIN = 1.000;
    public static final int TREE_HEALTH_MAX = 3;
    public static final int TREE_HEALTH_MIN = 1;
    public static final int TREE_ANIMATION_PERIOD_IDX = 0;
    public static final int TREE_ACTION_PERIOD_IDX = 1;
    public static final int TREE_HEALTH_IDX = 2;
    public static final int TREE_NUM_PROPERTIES = 3;


    public Tree(String id, Point position, List<PImage> images, double animationPeriod,
                double activityPeriod, int health) {
        super(id, position, images, animationPeriod, activityPeriod, 0, 0, health);
    }

    public Tree(){
        super("", null, null, 0, 0, 0, 0, 0);
    }

    @Override
    public void nextImage() {
        this.setImageIndex(this.getImageIndex() + 1);
    }

    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (this.getHealth() <= 0) {
            Stump stump = new Stump(Stump.STUMP_KEY + "_" + this.getId(), this.getPosition(), imageStore.getImageList(Stump.STUMP_KEY));

            world.removeEntity(scheduler, this);

            world.addEntity(stump);

            return true;
        }

        return false;
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        if (!transform(world, scheduler, imageStore)) {
            scheduleActivity(scheduler, world, imageStore);
        }
    }

}
