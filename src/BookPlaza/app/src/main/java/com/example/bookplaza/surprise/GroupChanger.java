package com.example.bookplaza.surprise;

import android.content.Context;
import com.example.bookplaza.data.Book;
import com.example.bookplaza.dictTree.DictTree;
import com.example.bookplaza.dictTree.NumbersPair;
import com.example.bookplaza.io.Data;
import com.example.bookplaza.io.IndicesReader;
import com.example.bookplaza.io.Reader;
import com.example.bookplaza.trees.AttributesLoader;
import com.example.bookplaza.trees.BooksAttributes;
import com.example.bookplaza.trees.Library;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Change a book from one group to another
 * @author Yucheng Zhu
 */
public class GroupChanger {
    // a library to find books fast
    Library library;

    public GroupChanger(Library library) {
        this.library = library;
    }

    /**
     * Add a book index to books attributes
     * @author Yucheng Zhu
     * @param index Book index
     * @param attributes Books' attributes
     * @return Changed books' attributes
     */
    public BooksAttributes add(int index, BooksAttributes attributes) {

        // get books
        Book book = library.get(index);

        // add the book to all attributes
        AttributesLoader.addToAttributes(attributes, book);

        return attributes;
    }

    /**
     * Delete a book index from books attributes
     * @author Yucheng Zhu
     * @param index Book index
     * @param attributes Books' attributes
     * @return Changed books' attributes
     */
    public BooksAttributes delete(int index, BooksAttributes attributes) {

        // get books
        Book book = library.get(index);
        AttributesLoader.removeFromAttributes(attributes, book);
        return attributes;
    }

    /**
     * Move a book index from one books attributes to another
     * @author Yucheng Zhu
     * @param index Book index
     * @param moveFromAttributes Books' old attributes
     * @param moveToAttributes Books' new attributes
     */
    public void move(
            int index,
            BooksAttributes moveFromAttributes, BooksAttributes moveToAttributes
    ) {
        add(index, moveToAttributes);
        delete(index, moveFromAttributes);
    }

    /**
     * Load books' indices in all 3 book groups
     * @author Yucheng Zhu
     * @return Books' indices all 3 book groups
     */
    public HashSet<Integer>[] loadAllBooksGroupsIndices() {
        return loadAllBooksGroupsIndices(null);
    }

    /**
     * Load books' indices in all 3 book groups
     * @author Yucheng Zhu
     * @return Books' indices all 3 book groups
     */
    public HashSet<Integer>[] loadAllBooksGroupsIndices(Context context) {
        HashSet<Integer>[] allGroupsIndices = new HashSet[Data.MAX_GROUP_COUNT];

        for (BooksGroupsType booksGroupsType : BooksGroupsType.values()) {
            String groupFileName = booksGroupsType.name();

            // load the file
            String indicesFileContent;
            try {
                if (context == null) {
                    indicesFileContent = Reader.read(Reader.assetsFolder(groupFileName));
                } else {
                    indicesFileContent = Reader.read(Reader.assetsFolder(context, groupFileName));
                }
            } catch (IOException e) {
                throw new RuntimeException();
            }
            HashSet<Integer> indices = IndicesReader.toIndices(indicesFileContent);
            allGroupsIndices[booksGroupsType.getGroupCount()] = indices;
        }
        return allGroupsIndices;
    }

    /**
     * After a book is clicked, record all books that changed book groups
     * @author Yucheng Zhu
     * @param data Book's constants
     * @param meanPreferencesValuesMapUpdater Preference values updater
     * @param selectedBookIndex Book that the user clicked into
     * @return The old and new book groups for all books that changed book groups
     */
    public HashSet<ArrayList<Integer>> recordBooksThatChangedGroups(
            Context context,
            Data data,
            MeanPreferencesValuesMapUpdater meanPreferencesValuesMapUpdater,
            int selectedBookIndex
    ) {
        // update preference values
        meanPreferencesValuesMapUpdater.updateMeanPreferenceValues(
                context,
                selectedBookIndex
        );

        // load books' indices in all 3 book groups
        HashSet<Integer>[] allGroupsIndices = loadAllBooksGroupsIndices(context);

        // get preference values
        DictTree<Integer, Double> preferenceValues = meanPreferencesValuesMapUpdater.getPreferenceValues();

        // record books that changed groups
        HashSet<ArrayList<Integer>> booksChangedGroups = new HashSet<>();
        int sortedCount = 0;
        // iterate through preference values by sorted values
        System.out.println("GroupChanger: preferenceValues.getValuesTree().size():"+preferenceValues.getValuesTree().size());
        for (int bookId : preferenceValues.getValuesTree()) {
            // check if the bookId is in the right group
            int newGroup = data.findGroup(sortedCount);
            if (allGroupsIndices[newGroup].contains(bookId)) {
                // is in the correct group
                sortedCount++;
                continue;
            }

            // if not, check which group it is in
            int oldGroup;
            for (int i = 0; i < allGroupsIndices.length; i++) {
                // is in the wrong group
                if (allGroupsIndices[i].contains(bookId)) {
                    oldGroup = i; // found the actual group

                    //
                    ArrayList<Integer> bookChange = new ArrayList<>();
                    bookChange.add(bookId); // add bookId
                    bookChange.add(oldGroup); // add old group
                    bookChange.add(newGroup); // add new group

                    // record books that changed group
                    booksChangedGroups.add(bookChange);

                    break; // a book can only be in one group
                }
            }

            // the book is not in any group
            // increment the sorting order
            sortedCount++;
        }

        return booksChangedGroups;
    }

    /**
     * After a book is clicked, record all books that changed book groups
     * @author Yucheng Zhu
     * @param data Book's constants
     * @param meanPreferencesValuesMapUpdater Preference values updater
     * @param selectedBookIndex Book that the user clicked into
     * @return The old and new book groups for all books that changed book groups
     */
    public HashSet<ArrayList<Integer>> recordBooksThatChangedGroups(
            Data data,
            MeanPreferencesValuesMapUpdater meanPreferencesValuesMapUpdater,
            int selectedBookIndex
    ) {
        return recordBooksThatChangedGroups(
            null,
            data,
            meanPreferencesValuesMapUpdater,
            selectedBookIndex
        );
    }
}
