public interface Moveable {
    public boolean move(WorldModel world, Entity entity1, EventScheduler eventScheduler);
    public Point nextPosition(WorldModel world, Point point);
}