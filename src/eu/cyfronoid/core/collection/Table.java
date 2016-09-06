package eu.cyfronoid.core.collection;

public interface Table {
    TableRow addRow();
    Table addSeparator();
    Table addSingleLine(String line);
    int getRowsNumber();
    Table clear();
    @Override
    String toString();
}
