import java.util.ArrayList;
import java.util.List;

/**
 * list command used to print out the data in a library using a format user desires.
 */
public class ListCmd extends LibraryCommand {
    /**
     * the command for SearchCmd ("short", "long" or blank) read from user input.
     */
    String inputCmd;

    /**
     * constructor for a ListCmd instance
     * @param argumentInput a string containing info about how the user wants to
     *                      print out the library
     */
    public ListCmd(String argumentInput) {
        super(CommandType.LIST, argumentInput);
    }

    /**
     * Override the execute method in the LibraryCommand class, making it
     * throws an exception when the Library is empty or print out the books data
     * contained in the bookPath according to the users' requirement.
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
        Integer numberOfBooks = books.size();
        // print out general information of the library.
        System.out.printf("%s books in library:\n", numberOfBooks);
        // print library data in "long" form when user requires to, in "short" form
        // if user require to or if user has given no specific requirement.
        switch (inputCmd) {
            case "long":
                longPrint(books);
                break;
            case "short":
            default:
                shortPrint(books);
        }
    }

    /**
     * print out the library data in "short" form
     * @param books BookEntry instances read from LibraryData
     */
    private void shortPrint(List<BookEntry> books) {
        // To temporarily store the title of the BookEntry instance for printing;
        String bookTitle;
        for (BookEntry book: books) {
            bookTitle = book.getTitle();
            System.out.println(bookTitle);
        }
    }

    /**
     * print out the library data in "long" form
     * @param books BookEntry instances read from LibraryData
     */
    private void longPrint(List<BookEntry> books) {
        // To temporarily store the information of the BookEntry instance for printing;
        String bookInfo;
        for (BookEntry book: books) {
            bookInfo = book.toString();
            System.out.println(bookInfo + "\n");
        }
    }

    /**
     * overriding the parseArguments method in LibraryCommand making it check if
     * the user input is of a right format.
     * @param argumentInput argument input for this command
     * @return true if the user input is "long", "short" or blank, false otherwise.
     */
    @Override
    protected boolean parseArguments(String argumentInput) {
        // A String List with expected inputs for the comparison later.
        ArrayList<String> expectedInput = new ArrayList<>();
        expectedInput.add("short");
        expectedInput.add("long");
        expectedInput.add("");
        if (expectedInput.contains(argumentInput)) {
            inputCmd = argumentInput;
            return true;
        } else {
            return false;
        }
    }
}
