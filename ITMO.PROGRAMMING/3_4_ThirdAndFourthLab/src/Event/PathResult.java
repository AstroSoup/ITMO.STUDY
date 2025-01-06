package Event;

import java.util.List;

public record PathResult(List<Location> path, int totalCost) {
}
