public class Activity extends Action{

    private final WorldModel world;
    private final ImageStore imageStore;

    public Activity(Entity entity, WorldModel world, ImageStore imageStore){
        super(entity);
        this.world = world;
        this.imageStore = imageStore;
    }

    public void execute(EventScheduler scheduler) {
        ((Actionable)super.getEntity()).executeActivity(world, imageStore, scheduler);
    }

}
