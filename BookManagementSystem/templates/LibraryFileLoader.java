import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/** 
 * Class responsible for loading
 * book data from file.
 */
public class LibraryFileLoader {

    /**
     * Contains all lines read from a book data file using
     * the loadFileContent method.
     * 
     * This field can be null if loadFileContent was not called
     * for a valid Path yet.
     * 
     * NOTE: Individual line entries do not include line breaks at the 
     * end of each line.
     */
    private List<String> fileContent;

    /** Create a new loader. No file content has been loaded yet. */
    public LibraryFileLoader() { 
        fileContent = null;
    }

    /**
     * Load all lines from the specified book data file and
     * save them for later parsing with the parseFileContent method.
     * 
     * This method has to be called before the parseFileContent method
     * can be executed successfully.
     * 
     * @param fileName file path with book data
     * @return true if book data could be loaded successfully, false otherwise
     * @throws NullPointerException if the given file name is null
     */
    public boolean loadFileContent(Path fileName) {
        Objects.requireNonNull(fileName, "Given filename must not be null.");
        boolean success = false;

        try {
            fileContent = Files.readAllLines(fileName);
            success = true;
        } catch (IOException | SecurityException e) {
            System.err.println("ERROR: Reading file content failed: " + e);
        }

        return success;
    }

    /**
     * Has file content been loaded already?
     * @return true if file content has been loaded already.
     */
    public boolean contentLoaded() {
        return fileContent != null;
    }

    /**
     * Parse file content loaded previously with the loadFileContent method.
     * @return books parsed from the previously loaded book data or an empty list
     * if no book data has been loaded yet.
     */
    public List<BookEntry> parseFileContent() {
        // A list of BookEntry constructed from the fileContent.
        List<BookEntry> books = new ArrayList<>();
        if (fileContent == null) {
            System.err.println("ERROR: No content loaded before parsing.");
            return books;
        } else {
            List<String> validBooks = fileContent.subList(1, fileContent.size());
            // iterate through all the valid part of fileContent and construct a BookEntry for every String contained.
            getBookEntries(books, validBooks);
            return books;
        }
    }

    /**
     * constructing bookEntry instances with information read from the fileContent and
     * append them to the books list.
     * @param books a list of BookEntry before appending the data from the file content.
     * @param fileContent the book entries in the String form read from the path given
     */
    private void getBookEntries(List<BookEntry> books, List<String> fileContent) {
        // A string array containing all information for a Book Entry instance
        String[] bookinfo;
        // below are the five instance fields needed for every book entry to be constructed.
        String title;
        String[] authors;
        float rating;
        String ISBN;
        int pages;
        for (String book : fileContent) {
            bookinfo = book.split(",");
            // temporarily store the splited and parsed book info for constructing a corresponding BookEntry instance.
            title = bookinfo[0];
            authors = bookinfo[1].split("-");
            rating = Float.parseFloat(bookinfo[2]);
            ISBN = bookinfo[3];
            pages = Integer.parseInt(bookinfo[4]);
            // construct a BookEntry instance to be stored in the books list.
            BookEntry book1 = new BookEntry(title, authors, rating, ISBN, pages);
            books.add(book1);
        }
    }
}
