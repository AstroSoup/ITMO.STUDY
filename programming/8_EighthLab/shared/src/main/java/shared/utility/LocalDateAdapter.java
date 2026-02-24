package shared.utility;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Converts between java.time.LocalDate ↔ String (ISO-8601) for JAXB.
 */
public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {

    // Use ISO_LOCAL_DATE (e.g. "2025-06-04")
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    @Override
    public LocalDate unmarshal(String xmlDate) throws Exception {
        // Called when unmarshalling: String → LocalDate
        if (xmlDate == null || xmlDate.trim().isEmpty()) {
            return null;
        }
        return LocalDate.parse(xmlDate, FORMATTER);
    }

    @Override
    public String marshal(LocalDate date) throws Exception {
        // Called when marshalling: LocalDate → String
        if (date == null) {
            return null;
        }
        return date.format(FORMATTER);
    }
}

