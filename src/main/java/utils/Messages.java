package utils;

import org.apache.commons.lang3.StringUtils;

public class Messages {

    public static final String INVALID_ENTRY = "\n⛔ Invalid entry - try again.";

    public static void signOutDialog() {
        Helper.println("\n" + Vars.DIVIDER_DASH_LONG);
        Helper.println(StringUtils.center(Emoji.LOCKED_WITH_KEY + " You have been signed out. " + Emoji.LOCKED_WITH_KEY, Vars.DIVIDER_DASH_LONG.length() - 2));
        Helper.println(Vars.DIVIDER_DASH_LONG);

        Helper.getInput("\n[Press any key to continue...]\n");
    }

}
