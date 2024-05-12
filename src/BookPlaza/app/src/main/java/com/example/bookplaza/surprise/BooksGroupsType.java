package com.example.bookplaza.surprise;

/**
 * Get Book groups' types
 *
 * @author Yucheng Zhu
 */
public enum BooksGroupsType {
    DailyReading(0),
    HotFeed(1),
    Explore(2);

    private final int groupCount;

    private BooksGroupsType(int groupCount) {
        this.groupCount = groupCount;
    }

    /**
     * get count from type
     * @return Book group's count
     */
    public int getGroupCount() {
        return groupCount;
    }

    /**
     * Get the book's group from the group's
     * 
     * @author Yucheng Zhu
     * @param groupCount The group's count (i.e. 0, 1 or 2)
     * @return The corresponding book group's type
     */
    public static BooksGroupsType fromValue(int groupCount) {
        for (BooksGroupsType booksGroupsType : BooksGroupsType.values()) {
            if (booksGroupsType.getGroupCount() == groupCount) {
                return booksGroupsType;
            }
        }
        throw new IllegalArgumentException("Invalid groupCount: " + groupCount);
    }

    /**
     * Get the book's group from the group's
     *
     * @author Yucheng Zhu
     * @param groupValue The group's count (i.e. "DailyReading", "HotFeed" or "Explore")
     * @return The corresponding book group's type
     */
    public static BooksGroupsType fromValue(String groupValue) {
        switch (groupValue){
            case ("DailyReading"):
                return BooksGroupsType.DailyReading;
            case ("HotFeed"):
                return BooksGroupsType.HotFeed;
            case ("Explore"):
                return BooksGroupsType.Explore;
            default:
                throw new IllegalArgumentException("Invalid groupValue: " + groupValue);
        }
    }
}
