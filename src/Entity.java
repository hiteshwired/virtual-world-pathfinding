import java.util.*;

import processing.core.PImage;

/**
 * An entity that exists in the world. See EntityKind for the
 * different kinds of entities that exist.
 */
public abstract class Entity {
    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;

    public Entity(String id, Point position, List<PImage> images, int imageIndex) {
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = imageIndex;
    }

    public String getId() {
        return id;
    }

    public int getImageIndex() {
        return imageIndex;
    }
    public void nextImage() {
        this.imageIndex = this.imageIndex + 1;
    }

    public void setImageIndex(int imageIndex) {
        this.imageIndex = imageIndex;
    }

    public List<PImage> getImages() {
        return images;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public String log(){
        if (id.isEmpty()) {
            return null;
        } else {
            return String.format("%s %d %d %d", id, position.x, position.y, imageIndex);
        }
    }
    public PImage getCurrentImage(){
        return this.images.get(this.imageIndex % this.images.size());
    }
    public static Optional<Entity> nearestEntity(List<Entity> entities, Point pos) {
        if (entities.isEmpty()) {
            return Optional.empty();
        } else {
            Entity nearest = entities.getFirst();
            int nearestDistance = nearest.position.distanceSquared(pos);

            for (Entity other : entities) {
                int otherDistance = other.position.distanceSquared(pos);

                if (otherDistance < nearestDistance) {
                    nearest = other;
                    nearestDistance = otherDistance;
                }
            }

            return Optional.of(nearest);
        }
    }
    public abstract void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore);
    public abstract void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);
}
//
//    public double getAnimationPeriod() {
//        return switch (this.kind) {
//            case DUDE_FULL, DUDE_NOT_FULL, OBSTACLE, FAIRY, SAPLING, TREE -> this.animationPeriod;
//            default ->
//                    throw new UnsupportedOperationException(String.format("getAnimationPeriod not supported for %s", this.kind));
//        };
//    }
//

//
//    /**
//     * Creates a new Obstacle.
//     * @param id The new Obstacle's id.
//     * @param position The Obstacle's x,y position in the World.
//     * @param animationPeriod The time (seconds) taken for each animation.
//     * @param images Images to use for the Obstacle.
//     * @return a new Entity whose type is Obstacle.
//     */
//    public static Entity createObstacle(String id, Point position, double animationPeriod, List<PImage> images) {
//        return new Entity(EntityKind.OBSTACLE, id, position, images, 0, 0, 0, animationPeriod, 0, 0);
//    }

