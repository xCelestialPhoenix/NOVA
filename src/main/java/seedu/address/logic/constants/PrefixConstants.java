package seedu.address.logic.constants;

import seedu.address.logic.parser.Prefix;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class PrefixConstants {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");

    public static final Prefix PREFIX_ACTIVITY_TYPE = new Prefix("type/");
    public static final Prefix PREFIX_ACTIVITY_DESCRIPTION = new Prefix("desc/");
    public static final Prefix PREFIX_ACTIVITY_VENUE = new Prefix("venue/");
    public static final Prefix PREFIX_ACTIVITY_DATE = new Prefix("date/");
    public static final Prefix PREFIX_ACTIVITY_START_TIME = new Prefix("start/");
    public static final Prefix PREFIX_ACTIVITY_END_TIME = new Prefix("end/");
    public static final Prefix PREFIX_ACTIVITY_NOTES = new Prefix("note/");

}
