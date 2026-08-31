import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class AstarPathingStrategy implements PathingStrategy {
    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors) {
        PriorityQueue<Node> openList = new PriorityQueue<>(Comparator.comparingInt(n -> n.f));
        Map<Point, Integer> bestGCosts = new HashMap<>();
        Set<Point> closedSet = new HashSet<>();

        Node startNode = new Node(start, 0, heuristic(start, end), null);
        openList.add(startNode);
        bestGCosts.put(start, 0);

        while (!openList.isEmpty()) {
            Node current = openList.poll();

            if (withinReach.test(current.point, end)) {
                return reconstructPath(current);
            }

            if (closedSet.contains(current.point)) {
                continue;
            }
            closedSet.add(current.point);

            potentialNeighbors.apply(current.point)
                    .filter(canPassThrough)
                    .forEach(neighbor -> {
                        if (closedSet.contains(neighbor)) {
                            return;
                        }

                        int gCost = current.g + 1;
                        if (!bestGCosts.containsKey(neighbor) || gCost < bestGCosts.get(neighbor)) {
                            bestGCosts.put(neighbor, gCost);
                            int hCost = heuristic(neighbor, end);
                            Node neighborNode = new Node(neighbor, gCost, hCost, current);
                            openList.add(neighborNode);
                        }
                    });
        }
        return Collections.emptyList();
    }

    private int heuristic(Point a, Point b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }

    private List<Point> reconstructPath(Node node) {
        LinkedList<Point> path = new LinkedList<>();
        while (node.previous != null) {
            path.addFirst(node.point);
            node = node.previous;
        }
        return path;
    }

    private static class Node {
        final Point point;
        final int g;
        final int f;
        final Node previous;

        Node(Point point, int g, int h, Node previous) {
            this.point = point;
            this.g = g;
            this.f = g + h;
            this.previous = previous;
        }
    }
}
