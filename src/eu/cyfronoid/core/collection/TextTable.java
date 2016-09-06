package eu.cyfronoid.core.collection;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import eu.cyfronoid.core.text.ItemStringBuilder;

public class TextTable implements Table {
    private final List<TableItem> items = Lists.newArrayList();
    private final Map<Integer, Integer> fieldSizes = Maps.newHashMap();
    private final String columnSeparator;

    public TextTable() {
        this("|");
    }

    public TextTable(String columnSeparator, Integer... minFieldSizes) {
        for (int i = 0; i < minFieldSizes.length; i++) {
            fieldSizes.put(i, minFieldSizes[i]);
        }

        this.columnSeparator = columnSeparator;
    }

    @Override
    public Table addSeparator() {
        items.add(new TableItem());
        return this;
    }

    @Override
    public TableRow addRow() {
        TextTableRow row = new TextTableRow();
        items.add(new TableItem(row));
        return row;
    }

    @Override
    public TextTable addSingleLine(String line) {
        items.add(new TableItem(line));
        return this;
    }



    @Override
    public String toString() {
        calculateFieldSizes();
        String separator = createSeparator();
        ItemStringBuilder tableBuilder = new ItemStringBuilder("\n");
        for (TableItem item : items) {
            if(item.isSeparator()) {
                tableBuilder.append(separator);
            } else if(item.isSingleLine()) {
                tableBuilder.append(item.singleLine);
            } else {
                TextTableRow row = item.getRow();
                ItemStringBuilder fieldBuilder = new ItemStringBuilder("");
                List<String> fields = row.getFields();
                for (int i = 0; i < fields.size(); i++) {
                    fieldBuilder.appendWithLength(fields.get(i), fieldSizes.get(i));
                    if(i < fields.size() - 1) {
                        fieldBuilder.append(columnSeparator);
                    }
                }
                tableBuilder.append(fieldBuilder.toString());
            }
        }
        return tableBuilder.toString();
    }

    private String createSeparator() {
        int tableWidth = 0;
        Collection<Integer> values = fieldSizes.values();
        if(!values.isEmpty()) {
            for (Integer size : values) {
                tableWidth = tableWidth + size + columnSeparator.length();
            }
            tableWidth -= columnSeparator.length();
        }
        char[] dashes = new char[tableWidth];
        Arrays.fill(dashes, 0, tableWidth, '-');
        return new String(dashes);
    }

    private void calculateFieldSizes() {
        for (TableItem item : items) {
            if(item.isTableRow()) {
                calculateFieldSizesForItem(item);
            }
        }
    }

    private void calculateFieldSizesForItem(TableItem item) {
        TextTableRow row = item.getRow();
        List<String> fields = row.getFields();
        for (int i = 0; i < fields.size(); i++) {
            String field = fields.get(i);
            if(fieldSizes.get(i) == null) {
                fieldSizes.put(i, 0);
            }
            Integer size = fieldSizes.get(i);
            int fieldSize = (field == null) ? 4 : field.length();
            if (size < fieldSize) {
                fieldSizes.put(i, fieldSize);
            }
        }
    }

    public static class TextTableRow implements TableRow {
        private final List<String> fields = Lists.newArrayList();

        public TextTableRow addHeader(String... fields) {
            Arrays.asList(fields).forEach(f -> this.fields.add("-" + f + "-"));
            return this;
        }

        public TextTableRow addFields(String... fields) {
            this.fields.addAll(Arrays.asList(fields));
            return this;
        }

        public List<String> getFields() {
            return fields;
        }

    }

    public int getRowsNumber() {
        return items.size();
    }

    public TextTable clear() {
        items.clear();
        fieldSizes.clear();
        return this;
    }

    private class TableItem {
        private final String singleLine;
        private final TextTableRow row;

        public TableItem() {
            row = null;
            singleLine = null;
        }

        public TableItem(String singleLine) {
            this.singleLine = singleLine;
            row = null;
        }

        public TableItem(TextTableRow row) {
            this.row = row;
            singleLine = null;
        }

        public boolean isTableRow() {
            return row != null;
        }

        public boolean isSeparator() {
            return row == null && singleLine == null;
        }

        public boolean isSingleLine() {
            return singleLine != null;
        }

        public TextTableRow getRow() {
            return row;
        }

    }

    public static void main(String[] argv) {
        Table table = new TextTable();
        TableRow header = table.addRow();
        header.addHeader("id", "name");
        table.addSeparator();
        table.addRow().addFields("1", "widmo");
        table.addRow().addFields("2", "noob");
        System.out.println(table);
    }

}

