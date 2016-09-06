package eu.cyfronoid.core.collection;

import java.util.List;

public interface TableRow {
    TableRow addHeader(String... fields);
    TableRow addFields(String... fields);
    List<String> getFields();
}