package seedu.address.logic.parser;

/**
 * A prefix that marks the beginning of an argument in an arguments string.
 * E.g. 't/' in 'add James t/ friend'.
 */
public class Prefix {

    private final String prefix;

    /**
     * Instantiates a new Prefix.
     *
     * @param prefix the prefix
     */
    public Prefix(String prefix) {

        this.prefix = prefix;
    }

    /**
     * Gets prefix.
     *
     * @return the prefix
     */
    public String getPrefix() {

        return prefix;
    }

    /**
     * Formats the prefix into string.
     * @return the string prefix
     */
    public String toString() {

        return getPrefix();
    }

    @Override
    public int hashCode() {

        return prefix == null ? 0 : prefix.hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof Prefix)) {
            return false;
        }
        if (obj == this) {
            return true;
        }

        Prefix otherPrefix = (Prefix) obj;
        return otherPrefix.getPrefix().equals(getPrefix());
    }

}
