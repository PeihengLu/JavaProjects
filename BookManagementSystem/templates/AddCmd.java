import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * add command used to add book entries to a library
 */
public class AddCmd extends LibraryCommand {
    /**
     * the path read from the user input containing the book entries needing to be added
     */
    Path bookPath;

    /**
     * constructor for a AddCmd instance
     * @param bookPath the string containing the paths of the
     *                 books to be added to the library.
     */
    public AddCmd(String bookPath) {
        super(CommandType.ADD, bookPath);
    }

    /**
     * Override the execute method in the LibraryCommand class, making it
     * throws an exception when the Library is empty or load the books data
     * contained in the bookPath.
     * @param data book data to be considered for command execution.
     * @throws NullPointerException if LibraryData has no entries inside
     */
    @Override
    public void execute(LibraryData data) {
        if (data == null) {
            throw new NullPointerException("Given LibraryData can't be null");
        }
        data.loadData(bookPath);
    }

    /**
     * parse the argument input from String into a Path if it has a valid suffix.
     * @param argumentInput argument input for this command
     * @return true if the suffix is correct (.csv), false otherwise.
     */
    @Override
    protected boolean parseArguments(String argumentInput) {
        // if the length of argumentInput is no greater than the length of the valid
        // suffix we are looking for, then it must be incorrect.
        if (argumentInput.length() <= ".csv".length()) {
            return false;
        }
        // get the suffix of the input file name
        String fileSuffix = argumentInput.substring(argumentInput.length() - ".csv".length());
        // checking if the suffix is valid
        if (fileSuffix.equals(".csv")) {
            bookPath = Paths.get(argumentInput);
            return true;
        } else {
            return false;
        }
    }
}
