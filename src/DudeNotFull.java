import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import processing.core.PImage;

public class DudeNotFull extends Dude {
    public DudeNotFull(String id, Point position, List<PImage> images, double animationPeriod, double activityPeriod, int resourceLimit) {
        super(id, position, images, animationPeriod, activityPeriod, resourceLimit);
    }
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> target;
        target = world.findNearest(this.getPosition(), new ArrayList<>(Arrays.asList(Tree.class, Sapling.class)));

        if (target.isEmpty() || !this.move(world, target.get(), scheduler) || !this.transform(world, scheduler, imageStore)) {
            scheduleActivity(scheduler, world, imageStore);
        }
    }

    @Override
    public boolean move(WorldModel world, Entity target, EventScheduler scheduler) {

        if (this.getPosition().adjacent(target.getPosition())) {
            this.setResourceCount(this.getResourceCount() + 1);
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

    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (this.getResourceCount() >= this.getResourceLimit()) {
            DudeFull dudeFull = new DudeFull(this.getId(), this.getPosition(), this.getImages(), this.getAnimationPeriod(), this.getActivityPeriod(), this.getResourceLimit());

            world.removeEntity(scheduler, this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(dudeFull);
            dudeFull.scheduleActions(scheduler, world, imageStore);

            return true;
        }

        return false;
    }
}
