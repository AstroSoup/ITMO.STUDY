package Event;

import java.util.*;

public class PathFinder {

    public static PathResult findShortestPath(Location start, Location end) {
        PriorityQueue<PathNode> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(PathNode::getTotalCost));
        Map<Location, Integer> shortestCost = new HashMap<>();
        Map<Location, Location> previousNode = new HashMap<>();

        shortestCost.put(start, 0);
        priorityQueue.offer(new PathNode(start, 0));

        while (!priorityQueue.isEmpty()) {
            PathNode currentNode = priorityQueue.poll();
            Location currentLocation = currentNode.location;
            int currentCost = currentNode.totalCost;

            if (currentLocation.equals(end)) {
                List<Location> path = reconstructPath(previousNode, end);
                return new PathResult(path, currentCost);
            }

            for (Location neighbor : currentLocation.getNeighbors()) {
                int newCost = currentCost + neighbor.getEnergyCost();
                if (newCost < shortestCost.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                    shortestCost.put(neighbor, newCost);
                    previousNode.put(neighbor, currentLocation);
                    priorityQueue.offer(new PathNode(neighbor, newCost));
                }
            }
        }

        return new PathResult(Collections.emptyList(), -1);
    }

    public static PathResult findLongestPath(Location start, int maxEnergy) {
        List<Location> longestPath = new ArrayList<>();
        dfs(start, new ArrayList<>(), 0, maxEnergy, longestPath, new HashMap<>());
        int totalEnergy = longestPath.stream().mapToInt(Location::getEnergyCost).sum();
        return new PathResult(longestPath, totalEnergy);
    }

    private static void dfs(
            Location current,
            List<Location> currentPath,
            int currentEnergy,
            int maxEnergy,
            List<Location> longestPath,
            Map<Location, Boolean> visited
    ) {
        visited.put(current, true);
        currentPath.add(current);

        if (currentPath.size() > longestPath.size() && currentEnergy <= maxEnergy) {
            longestPath.clear();
            longestPath.addAll(new ArrayList<>(currentPath));
        }

        for (Location neighbor : current.getNeighbors()) {
            int neighborEnergy = currentEnergy + neighbor.getEnergyCost();
            if (!visited.getOrDefault(neighbor, false) && neighborEnergy <= maxEnergy) {
                dfs(neighbor, currentPath, neighborEnergy, maxEnergy, longestPath, visited);
            }
        }

        currentPath.remove(currentPath.size() - 1);
        visited.put(current, false);
    }

    private static List<Location> reconstructPath(Map<Location, Location> previousNode, Location end) {
        List<Location> path = new ArrayList<>();
        for (Location at = end; at != null; at = previousNode.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);
        return path;
    }

    private static class PathNode {
        Location location;
        int totalCost;

        PathNode(Location location, int totalCost) {
            this.location = location;
            this.totalCost = totalCost;
        }

        public int getTotalCost() {
            return totalCost;
        }
    }
}
