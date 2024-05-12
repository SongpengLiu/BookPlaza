package com.example.bookplaza.trees;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

/**
 * Test BooksAttributes
 * @author Yucheng Zhu
 */
public class BooksAttributesTest {
    /**
     * Test remove book from dict
     * @author Yucheng Zhu
     */
    @Test
    public void testRemoveBookFromDict() {

        HashMap<String, ArrayList<TreeData<Integer>>> dict = new HashMap<>();
        dict.put("a", new ArrayList<>());
        dict.get("a").add(new TreeData<>(null, 1));
        dict.get("a").add(new TreeData<>(null, 2));
        dict.get("a").add(new TreeData<>(null, 3));


        assertEquals(
                "{a=[TreeData{sortable=null, id='1'}, TreeData{sortable=null, id='2'}, TreeData{sortable=null, id='3'}]}",
                dict.toString()
        );

        BooksAttributes.removeBookFromDict(
                dict, "a", 2);

        // test deleted
        assertEquals(
                "{a=[TreeData{sortable=null, id='1'}, TreeData{sortable=null, id='3'}]}",
                dict.toString()
        );
    }
}
