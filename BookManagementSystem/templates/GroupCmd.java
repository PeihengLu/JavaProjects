import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * group command for grouping the book entries by the given criteria.
 */
public class GroupCmd extends LibraryCommand {
    String groupPar;
    /**
     * construct a GroupCmd instance
     * @param argumentInput a string containing the way user wants to group the library
     */
    public GroupCmd(String argumentInput) {
        super(CommandType.GROUP, argumentInput);
    }
    /**
     * Override the execute method in the LibraryCommand class, making it throws an exception
     * when the Library is empty or group and print out the data in the library.
     *
     * @param data book data to be considered for command execution.
     * @throws NullPointerException if LibraryData has no entries inside
     */
    @Override
    public void execute(LibraryData data) {
        if (data == null) {
            throw new NullPointerException("Given LibraryData can't be null");
        }
        List<BookEntry> books = data.getBookData();
        if (books.size() == 0) {
            System.out.println("The library has no book entries.");
        } else {
            // the string to store the grouped book information parsed into
            // the format for display
            String grouped = "";
            System.out.printf("Grouped data by %s\n", groupPar);
            if (groupPar.equals("TITLE")) {
                groupByTitle(books, grouped);
            } else {
                groupByAuthor(books, grouped);
            }
        }
    }

    /**
     * Determine whether the user input is in the correct format or not,
     * i.e, if it is either "AUTHOR" or "TITLE".
     * @param argumentInput argument input for this command
     * @return true if the format is correct, false otherwise.
     */
    @Override
    protected boolean parseArguments(String argumentInput) {
        if (!((argumentInput.equals("AUTHOR")) ||
                (argumentInput.equals("TITLE")))) {
            return false;
        } else {
            groupPar = argumentInput;
            return true;
        }
    }

    /**
     * grouping the book entries by the author and print out the result.
     * @param books list of BookEntry instances read from the LibraryData file
     * @param grouped the String used to containing the information from the HashMaps
     */
    private void groupByAuthor(List<BookEntry> books, String grouped) {
        // HashMap for grouping by the authors
        HashMap<String, List<String>> authorMap = new HashMap<>();
        // the list of names of authors, will be used as the keys of the authorMap.
        List<String> authors = new ArrayList<>();
        setAuthorMap(books, authorMap, authors);
        grouped = parseAuthorMap(grouped, authorMap);
        System.out.println(grouped);
    }

    /**
     * fill up authorMao with books grouped according to the author
     * @param books list of BookEntry instances read from the LibraryData file
     * @param authorMap HashMap for grouping by the authors
     * @param authors the list of names of authors, will be used as the keys of the authorMap.
     */
    private void setAuthorMap(List<BookEntry> books, HashMap<String, List<String>> authorMap,
                              List<String> authors) {
        // append the authors list with authors of the entries from books list
        getAuthors(books, authors);
        for (String author : authors) {
            // books written by the author
            List<String> bookByAuthor = new ArrayList<>();
            for (BookEntry book : books) {
                String joined = String.join("-", book.getAuthors());
                if (joined.contains(author)) {
                    bookByAuthor.add(book.getTitle());
                }
            }
            authorMap.put(author, bookByAuthor);
        }
    }

    /**
     * helper function to append the authors list with authors of the entries from books list
     * @param books list of BookEntry instances read from the LibraryData file
     * @param authors the list of names of authors
     */
    private void getAuthors(List<BookEntry> books, List<String> authors) {
        for (BookEntry book : books) {
            String[] author = book.getAuthors();
            for (String auth: author) {
                if (!authors.contains(auth)){
                    authors.add(auth);
                }
            }
        }
    }

    /**
     * parse the authorMap into a String and append it to the grouped String for latter display
     * @param grouped the String used to containing the information from the HashMaps
     * @param authorMap HashMap for grouping by the authors
     * @return the grouped String appended with the information from authorMap
     */
    private String parseAuthorMap(String grouped, HashMap<String, List<String>> authorMap) {
        return parseMaps(grouped, authorMap);
    }

    /**
     * grouped the book entries by their titles in a lexicographical order
     * @param books list of BookEntry instances read from the LibraryData file
     * @param grouped the String used to containing the information from the HashMaps
     */
    private void groupByTitle(List<BookEntry> books, String grouped) {
        // HashMap for book with titles with letter initials
        HashMap<String, List<String>> titleMap = new HashMap<>();
        // HashMap for books with titles with non-letter initials
        HashMap<String, List<String>> otherMap = new HashMap<>();
        List<String> groupedTitles = new ArrayList<>();
        // the list of books with non-letter initials in their titles
        List<String> others = new ArrayList<>();
        // fill up the titleMap with books with letter initials and parse the HashMap into a String
        // and append it to the grouped String for latter display
        grouped = setTitleMap(books, grouped, titleMap, groupedTitles);
        // fill up the otherMap with books with non-letter initials and parse the HashMap into a String
        // and append it to the grouped String for latter display
        grouped = setOtherMap(books, grouped, otherMap, groupedTitles, others);
        System.out.println(grouped);
    }

    /**
     * fill up the titleMap with books with letter initials, parse the titleMap into a String
     * and append it to the grouped String for latter display
     * @param books list of BookEntry instances read from the LibraryData file
     * @param grouped the String used to containing the information from the HashMaps
     * @param titleMap HashMap for book with titles with letter initials
     * @param groupedTitles the grouped String before appending the information from books with letter initial titles
     * @return the grouped String after appending the information from books with letter initial titles
     */
    private String setTitleMap(List<BookEntry> books, String grouped,
                               HashMap<String, List<String>> titleMap, List<String> groupedTitles) {
        // put books with letter initials into titleMap according to their initials
        for (Character a = 'A'; a <= 'Z'; a++) {
            List<String> titles = new ArrayList<>();
            for (BookEntry book : books) {
                String title = book.getTitle().toUpperCase();
                // get the initial letter of the title
                Character init = title.charAt(0);
                if (init == a) {
                    titles.add(title);
                    groupedTitles.add(title);
                }
            }
            if (titles.size() != 0) {
                titleMap.put(String.valueOf(a), titles);
            }
        }
        grouped = parseTitleMap(grouped, titleMap);
        return grouped;
    }

    /**
     * parse the titleMap into a String and append it to the grouped String for latter display
     * @param grouped the String used to containing the information from the HashMaps
     * @param titleMap HashMap for book with titles with letter initials
     * @return the grouped String appended with the information from titleMap
     */
    private String parseTitleMap(String grouped, HashMap<String, List<String>> titleMap) {
        return parseMaps(grouped, titleMap);
    }


    /**
     * fill up the otherMap with books with non-letter initials, parse the otherMap into a String
     * and append it to the grouped String for latter display
     * @param books list of BookEntry instances read from the LibraryData file
     * @param grouped the String used to containing the information from the HashMaps
     * @param otherMap HashMap for books with titles with non-letter initials
     * @param groupedTitles the grouped String before appending the information from books with titles
     *                      in the others List
     * @param others  the list of books with non-letter initials in their titles
     * @return the grouped String after appending the information from books with titles
     *         in the others List
     */
    private String setOtherMap(List<BookEntry> books, String grouped, HashMap<String, List<String>> otherMap,
                               List<String> groupedTitles, List<String> others) {
        // group the books that hasn't been in the titleMap into the others List
        for (BookEntry book : books) {
            if (!groupedTitles.contains(book.getTitle().toUpperCase())) {
                others.add(book.getTitle());
            }
        }
        // filling up otherMap if there are presence of books with non-letter initial title
        if (others.size() != 0) {
            otherMap.put("[0-9]", others);
            grouped = parseOtherMap(grouped, otherMap);
        }
        return grouped;
    }

    /**
     * parse the otherMap into a String and append it to the grouped String for latter display
     * @param grouped the String used to containing the information from the HashMaps
     * @param otherMap HashMap for books with titles with non-letter initials
     * @return the grouped String appended with the information from otherMap
     */
    private String parseOtherMap(String grouped, HashMap<String, List<String>> otherMap) {
        List<String> values = otherMap.get("[0-9]");
        String value = String.join("\n   ", values);
        grouped += "## " + "[0-9]" + "\n" + "   " + value + "\n";
        return grouped;
    }


    /**
     * helper function for parsing authorMap and titleMap
     * @param grouped the String used to containing the information from the HashMaps
     * @param maps HashMap providing the information we need for parsing
     * @return the grouped String appended with the information from the maps.
     */
    private String parseMaps(String grouped, HashMap<String, List<String>> maps) {
        Set<String> keys = maps.keySet();
        List<String> keyList = new LinkedList<>(keys);
        // arrange the key list lexicographically
        Collections.sort(keyList);
        for (String key : keyList) {
            List<String> values = maps.get(key);
            String value = String.join("\n   ", values);
            grouped += "## " + key + "\n" + "   " + value + "\n";
        }
        return grouped;
    }
}