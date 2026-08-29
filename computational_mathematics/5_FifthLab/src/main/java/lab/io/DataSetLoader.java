package lab.io;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lab.model.DataPoint;
import lab.model.DataSet;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public final class DataSetLoader {
    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

    public DataSet loadJson(Path path) throws IOException {
        DataSetPayload payload = objectMapper.readValue(path.toFile(), DataSetPayload.class);
        return DataSet.table(payload.name(), payload.points());
    }

    public record DataSetPayload(String name, List<DataPoint> points) {
        @JsonCreator
        public DataSetPayload(@JsonProperty("name") String name, @JsonProperty("points") List<DataPoint> points) {
            this.name = name;
            this.points = points;
        }
    }
}
