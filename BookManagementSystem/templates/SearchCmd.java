import java.util.ArrayList;
import java.util.List;

/**
 * search command used to search for a title depending on the search entry entered by user.
 */
public class SearchCmd extends LibraryCommand {
    /** book title that the user want to search (directly read from the user input)*/
    String titleToSearch;
    /** book title that the user want to search (parsed to lower case for comparison
     * with the library data)*/
    String lowerTitleToSearch;

    /**
     * constructor for a SearchCmd instance
     * @param argumentInput the string containing the title searched by the user
     */
    public SearchCmd(String argumentInput) {
        super(CommandType.SEARCH, argumentInput);
    }

    /**
     * Override the execute method in the LibraryCommand class, making it
     * throws an exception when the Library is empty or search for the
     * title entered by the user.
     * @param data book data to be considered for command execution.
     * @throws NullPointerException if LibraryData has no entries inside
     */
    @Override
    public void execute(LibraryData data) {
        if (data == null) {
            throw new NullPointerException("Given LibraryData can't be null");
        }
        // get the BookEntry data stored in LibraryData
        List<BookEntry> books = data.getBookData();
        // all the book titles in the LibraryData
        List<String> titles = new ArrayList<>();
        // all the book titles in the LibraryData (parsed to lower case)
        List<String> lowerTitles = new ArrayList<>();
        // fill up title and lowerTitles lists with titles read from LibraryData
        addTitle(books, titles, lowerTitles);
        // counting how many books contains the title searched by user
        int searchCount = 0;
        searchCount = getSearchCount(titles, lowerTitles, searchCount);
        if (searchCount == 0) {
            System.out.printf("No hits found for search term: %s\n", titleToSearch);
        }
    }

    /**
     * printing out all the titles containing the title searched by user and counting
     * how many of them are there.
     * @param titles the lists for the original titles read from the library data for later display
     * @param lowerTitles the list for the titles parsed to lowercase for comparison with the user input
     * @param searchCount count how many titles contains the title searched by user
     * @return the number of titles containing the String searched by user
     */
    private int getSearchCount(List<String> titles, List<String> lowerTitles, int searchCount) {
        for (String lower: lowerTitles) {
            if (lower.contains(lowerTitleToSearch)) {
                int index = lowerTitles.indexOf(lower);
                String searchedTitle = titles.get(index);
                System.out.println(searchedTitle);
                searchCount ++;
            }
        }
        return searchCount;
    }

    /**
     * fill up the titles and parsing them to fill up lowerTitles list
     * @param books  list of BookEntry instances read from the LibraryData file
     * @param titles the lists for the original titles read from the library data for later display
     * @param lowerTitles the list for the titles parsed to lowercase for comparison with the user input
     */
    private void addTitle(List<BookEntry> books, List<String> titles, List<String> lowerTitles) {
        for (BookEntry book: books) {
            String title = book.getTitle();
            String lowerTitle = title.toLowerCase();
            titles.add(title);
            lowerTitles.add(lowerTitle);
        }
    }

    @Override
    /**
     * checking if the searching entry from user input is in correct format
     * @param argumentInput argument input for this command
     * @return true if the argument input contains no whitespace and is not blank, false otherwise.
     */
    protected boolean parseArguments(String argumentInput) {
        if (argumentInput.contains(" ") ||
            argumentInput == "")
        {
            return false;
        } else {
            titleToSearch = argumentInput;
            lowerTitleToSearch = titleToSearch.toLowerCase();
            return true;
        }
    }

}
