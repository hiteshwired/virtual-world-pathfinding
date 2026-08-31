import java.util.List;

import processing.core.PImage;

public class Sapling extends Resourceable implements Transformeable {

    public static final String SAPLING_KEY = "sapling";
    public static final int SAPLING_HEALTH_IDX = 0;
    public static final int SAPLING_NUM_PROPERTIES = 1;
    public static final int SAPLING_HEALTH_LIMIT = 5;
    public static final double SAPLING_ACTION_ANIMATION_PERIOD = 1.000;
    public static final double SAPLING_ACTIVITY_PERIOD = 1.000;
    public static final double SAPLING_ANIMATION_PERIOD = 1.000;

    public Sapling(String id, Point position, List<PImage> images, int health) {
        super(id, position, images, SAPLING_ANIMATION_PERIOD, SAPLING_ACTIVITY_PERIOD, 0,0, health);
    }

    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (this.getHealth() <= 0) {

            Stump stump = new Stump(Stump.STUMP_KEY + "_" + this.getId(), this.getPosition(), imageStore.getImageList(Stump.STUMP_KEY));

            world.removeEntity(scheduler, this);

            world.addEntity(stump);

            return true;
        }
        else if (this.getHealth() >= SAPLING_HEALTH_LIMIT) {
            Tree tree = new Tree(Tree.TREE_KEY + "_" + this.getId(), this.getPosition(), imageStore.getImageList(Tree.TREE_KEY), Functions.getNumFromRange(Tree.TREE_ANIMATION_MAX, Tree.TREE_ANIMATION_MIN), Functions.getNumFromRange(Tree.TREE_ACTION_MAX, Tree.TREE_ACTION_MIN), Functions.getIntFromRange(Tree.TREE_HEALTH_MAX, Tree.TREE_HEALTH_MIN));

            world.removeEntity(scheduler, this);

            world.addEntity(tree);
            tree.scheduleActions(scheduler, world, imageStore);

            return true;
        }
        return false;
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        this.setHealth(this.getHealth() + 1);
        if (!transform(world, scheduler, imageStore)) {
            scheduleActivity(scheduler, world, imageStore);
        }
    }

    public void updateImage() {
        if (this.getHealth() <= 0) {
            this.setImageIndex(0);
        } else if (this.getHealth() < SAPLING_HEALTH_LIMIT) {
            this.setImageIndex(this.getImages().size() * this.getHealth() / SAPLING_HEALTH_LIMIT);
        } else {
            this.setImageIndex(this.getImages().size() - 1);
        }
    }
}

