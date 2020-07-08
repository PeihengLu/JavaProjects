/**
 * Immutable class encapsulating data for a single book entry.
 */
public class BookEntry {
    /** the title of the book */
    private String title;
    /** the author or authors of the book */
    private String[] authors;
    /** the rating of the book (between 0 and 5) */
    private float rating;
    /** an ISBN code of the book */
    private String ISBN;
    /** the number of pages */
    private int pages;

    /**
     * Constructor for a book entry instance
     * @param title the title of the book
     * @param authors the author or authors of the book
     * @param rating the rating of the book (between 0 and 5)
     * @param ISBN an ISBN code of the book
     * @param pages the number of pages of the book
     * @throws IllegalArgumentException if pages is negative and rating is not between 0 and 5
     * @throws NullPointerException if title, authors and ISBN is null
     */
    public BookEntry(String title, String[] authors, float rating, String ISBN, int pages) {
        // checking the validity of all five data provided for the bookEntry instance.
        if (!isValidTitle(title)) {
            throw new NullPointerException("Book title can't be null");
        }
        if (!isValidAuthors(authors)) {
            throw new NullPointerException("Authors can't be null");
        }
        if (!isValidRating(rating)) {
            throw new IllegalArgumentException("Rating need to be between 0 and 5");
        }
        if (!isValidISBN(ISBN)) {
            throw new NullPointerException("ISBN can't be null");
        }
        if (!isValidPages(pages)) {
            throw new IllegalArgumentException("Page number need to be positive");
        }
        this.title = title;
        this.authors = authors;
        this.rating = rating;
        this.ISBN = ISBN;
        this.pages = pages;
    }

    /**
     * check if page number is positive
     * @param pages the number of pages of the book
     * @return true if page is positive, false otherwise
     */
    private boolean isValidPages(int pages) {
        if (pages >= 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * check if the ISBN code is null
     * @param ISBN the ISBN code of the book
     * @return true if the ISBN is not null, false otherwise
     */
    private boolean isValidISBN(String ISBN) {
        if (ISBN == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * check if the rating of the book is between 0 and 5
     * @param rating the rating of the book
     * @return true if the rating is between 0 and 5, false otherwise
     */
    private boolean isValidRating(float rating) {
        if (rating >= 0 &&
            rating <= 5) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * check if the authors array is null
     * @param authors the author or authors of the book
     * @return true if the authors is not null, false otherwise
     */
    private boolean isValidAuthors(String[] authors) {
        if (authors == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * check if the title of the book is null
     * @param title the title of the book
     * @return true if the title is not null, false otherwise
     */
    private boolean isValidTitle(String title) {
        if (title == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * getter method for the title of the book
     * @return title of the book
     */
    public String getTitle() {
        return title;
    }

    /**
     * getter method for the page number of the book
     * @return page number of the book
     */
    public int getPages() {
        return pages;
    }

    /**
     * getter method for the ISBN code of the book
     * @return the ISBN code of the book
     */
    public String getISBN() {
        return ISBN;
    }

    /**
     * getter method for the rating of the book
     * @return the rating of the book
     */
    public float getRating() {
        return rating;
    }

    /**
     * getter method fot the authors of the book
     * @return the author/authors of the book
     */
    public String[] getAuthors() {
        return authors;
    }

    /**
     * form a string containing all the information of the book entry
     * @return the string containing all the information of the book entry parsed for
     *         displaying
     */
    public String toString() {
        String bookInfo;
        bookInfo = getTitle()
                + "\nby " + String.join(", ", getAuthors())
                + "\nRating: " + String.format("%.2f", rating)
                + "\nISBN: " + ISBN + "\n"
                + pages + " pages";
        return bookInfo;
    }

    @Override
    /**
     * override the equals() method to compare the content equality of all five instance fields
     * of two Book Entry instances
     * @param book1 the other Book Entry instance to be compared with the current instance.
     * @return true if the two instances are equal, false otherwise.
     */
    public boolean equals(Object book1) {
        if (this == book1) {
            return true; // Checking reference equality.
        }
        if (!(book1 instanceof BookEntry)) {
            return false; // Not same type.
        }
        // Gain access to the other bookEntry's field
        BookEntry other = (BookEntry) book1;
        return title.equals(other.title) &&
                ISBN.equals(other.ISBN) &&
                authors.equals(other.authors) &&
                pages == other.pages &&
                rating == other.rating;
    }

    @Override
    /**
     * override the hashCode() method to generate a hash code from all ﬁve instance fields.
     * @return the hashCode of the Book Entry instance.
     */
    public int hashCode() {
        int result = 17; // An arbitrary starting value.
        // generate a hash code from all ﬁve instance fields.
        result = 31 * result + title.hashCode();
        result = 31 * result + authors.hashCode();
        // multiply the rating by 100 to reduce data loss during the conversion.
        result = 31 * result + (int) (rating * 100);
        result = 31 * result + pages;
        result = 31 * result + ISBN.hashCode();
        return result;
    }
}
