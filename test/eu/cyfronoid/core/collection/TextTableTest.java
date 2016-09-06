package eu.cyfronoid.core.collection;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TextTableTest {

    @Test
    public void test() {
        Table table = new TextTable();
        TableRow header = table.addRow();
        header.addHeader("id", "name");
        table.addSeparator();
        table.addRow().addFields("1", "widmo");
        table.addRow().addFields("2", "noob");
        assertEquals("-id-|-name-\n-----------\n1   |widmo \n2   |noob  ", table.toString());
    }

}
