import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import processing.core.PImage;

public class DudeFull extends Dude {

    public DudeFull(String id, Point position, List<PImage> images, double animationPeriod, double activityPeriod, int resourceLimit) {
        super(id, position, images, animationPeriod, activityPeriod, resourceLimit);
    }

    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> fullTarget = world.findNearest(this.getPosition(), new ArrayList<>(List.of(House.class)));

        if (fullTarget.isPresent() && this.move(world, fullTarget.get(), scheduler)) {
            this.transform(world, scheduler, imageStore);
        } else {
            scheduleActivity(scheduler, world, imageStore);
        }
    }

    @Override
    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        DudeNotFull dude = new DudeNotFull(this.getId(), this.getPosition(), this.getImages(), this.getAnimationPeriod(), this.getActivityPeriod(), this.getResourceLimit());

        world.removeEntity(scheduler, this);
        world.addEntity(dude);
        dude.scheduleActions(scheduler, world, imageStore);
        return true;
    }
}
