import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * remove command for user to remove specific books from the library
 */
public class RemoveCmd extends LibraryCommand {
    /** decides what kind of removal user wants to do */
    String authorOrTitle;
    /** the name of the author or the title the user expects to remove */
    String name;

    /**
     * constructor for a RemoveCmd instance
     * @param argumentInput the string containing what does the user want to remove.
     */
    public RemoveCmd(String argumentInput) {
        super(CommandType.REMOVE, argumentInput);
    }

    /**
     * Override the execute method in the LibraryCommand class, making it
     * throws an exception when the Library is empty or remove the data
     * appointed by the user.
     * @param data book data to be considered for command execution.
     * @throws NullPointerException if LibraryData has no entries inside
     */
    @Override
    public void execute(LibraryData data) {
        // getting access to the data before removal.
        List<BookEntry> books = data.getBookData();
        if (data == null) {
            throw new NullPointerException("Given LibraryData can't be null");
        }
        switch (authorOrTitle) {
            case "AUTHOR" :
                // provide author array for later comparison in authorRemove.
                authorRemove(books, name);
                break;
            default:
                titleRemove(books, name);
                break;
        }
    }

    /**
     * helper function to remove the data according to the title given
     * @param books BookEntry instances read from LibraryData
     * @param name the title to be removed
     * @return the list after removal
     */
    private void titleRemove(List<BookEntry> books, String name) {
        // counting how many books have been removed
        int removalCount = 0;
        Iterator<BookEntry> it = books.iterator();
        // iterating through all the book entry in books and remove the ones
        // with the given titles.
        while (it.hasNext()) {
            BookEntry book = it.next();
            String title = book.getTitle();
            if (title.equals(name)) {
                it.remove();
                System.out.printf("%s: removed successfully.\n", name);
                removalCount ++;
            }
        }
        if (removalCount == 0) {
            System.out.printf("%s: not found.\n", name);
        }
    }

    /**
     * helper function to remove the data according to the author given
     * @param books BookEntry instances read from LibraryData
     * @param author the author whose titles are to be removed
     * @return the list after removal
     */
    private void authorRemove(List<BookEntry> books, String author) {
        // counting how many books have been removed
        int removalCount = 0;
        Iterator<BookEntry> it = books.iterator();
        // iterating through all the book entry in books and remove the ones
        // written by the given authors.
        while (it.hasNext()) {
            BookEntry book = it.next();
            String[] authors = book.getAuthors();
            String concatenatedAuthors = String.join("-", authors);
            if (concatenatedAuthors.contains(author)) {
                it.remove();
                removalCount ++;
            }
        }
        if (removalCount == 0) {
            System.out.printf("0 books removed for author: %s\n", name);
        } else {
            System.out.printf("%s books removed for author: %s\n", removalCount, name);
        }
    }

    /**
     * overriding the parseArguments method in LibraryCommand making it check if
     * the user input is of a right format.
     * @param argumentInput argument input for this command
     * @return true if the user input is in correct format, false otherwise.
     */
    @Override
    protected boolean parseArguments(String argumentInput) {
        boolean isFormatCorrect = true;
        if (argumentInput == null) {
            return false;
        }
        // checking if the first part of the input is in correct format
        if (argumentInput.contains("AUTHOR")) {
            authorOrTitle = "AUTHOR";
        } else if (argumentInput.contains("TITLE")) {
            authorOrTitle = "TITLE";
        } else {
            return false;
        }
        // checking if the title or the name of the author is in the correct format
        name = extractName(argumentInput);
        if (!(authorOrTitle.equals("AUTHOR") ||
            authorOrTitle.equals("TITLE"))) {
            isFormatCorrect = false;
        }
        if (name.equals("")) {
            isFormatCorrect = false;
        }
        return isFormatCorrect;
    }

    /**
     * helper method to extract the title or the name of the author
     * @param argumentInput argument input for this command
     * @return the title or the name of the author, depending on the
     *         argument input.
     */
    private String extractName(String argumentInput) {
         String name;
         // remove "AUTHOR" or "TITLE".
        if (authorOrTitle.equals("AUTHOR")) {
            name = argumentInput.replace("AUTHOR ", "");
        } else {
            name = argumentInput.replace("TITLE ", "");
        }
        return name;
    }
}
