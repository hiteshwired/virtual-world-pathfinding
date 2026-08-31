import java.util.List;

import processing.core.PImage;

public abstract class Actionable extends Entity{

    private double animationPeriod;
    private double activityPeriod;

    public Actionable(String id, Point position, List<PImage> images, int imageIndex, double animationPeriod, double activityPeriod) {
        super(id, position, images, imageIndex);
        this.animationPeriod = animationPeriod;
        this.activityPeriod = activityPeriod;
    }

    public double getAnimationPeriod() {
        return animationPeriod;
    }

    public double getActivityPeriod() {
        return activityPeriod;
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        this.scheduleAnimation(scheduler, world, imageStore);
        this.scheduleActivity(scheduler, world, imageStore);
    }

    public void scheduleAnimation(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, new Animation(this, 0), this.getAnimationPeriod());
    }

    /** Schedules a single behavior update for the entity. */
    public void scheduleActivity(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, new Activity(this, world, imageStore), this.getActivityPeriod());
    }



}
