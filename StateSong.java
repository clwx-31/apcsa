/**
 * StateSong - prints "On, Wisconsin!", the Wisconsin state song, while
 * demonstrating five String concepts. Each concept is labeled below.
 */
public class StateSong
{
    public static void main(String[] args)
    {
        // ============================================================
        // CONCEPT 1: A String created using a literal
        // ============================================================
        // The text in double quotes IS the literal. Java builds a String
        // object out of it and stores a reference in the variable.
        String cheer = "On, Wisconsin!";
        String verseBreak = "";

        // ============================================================
        // CONCEPT 5: The + operator for concatenation
        // ============================================================
        // Each + glues two Strings together and produces a brand-new String.
        // The song opens both verses by repeating the cheer twice.
        String openingLine = cheer + " " + cheer;

        // ============================================================
        // CONCEPT 2: An immutable String
        // ============================================================
        // "Immutable" means a String object can never be edited after it is
        // created. The line above did NOT change cheer - it built a separate
        // String out of copies. Proof: cheer still holds its original text,
        // and verse 2 below reuses it unchanged.
        //
        // substring() is the same story. It does not cut anything out of
        // cheer; it hands back a NEW String and leaves cheer alone.
        String motto = cheer.substring(4, 13);   // "Wisconsin" - cheer is untouched

        // Build verse 1 with the + operator.
        String verseOne = openingLine + "\n"
                        + "Grand old Badger State!" + "\n"
                        + "We, thy loyal sons and daughters," + "\n"
                        + "Hail thee, good and great.";

        // ============================================================
        // CONCEPT 4: The += operator for concatenation
        // ============================================================
        // verseTwo += x is shorthand for verseTwo = verseTwo + x. Because
        // Strings are immutable, each += quietly makes a whole new String
        // and points verseTwo at it - the old text is never edited in place.
        String verseTwo = openingLine;                    // cheer still works: proof of immutability
        verseTwo += "\n";
        verseTwo += "Champion of the right,";
        verseTwo += "\n";
        verseTwo += "'Forward' - our motto -";            // single quotes need no escaping inside ""
        verseTwo += "\n";
        verseTwo += "God will give thee might!";

        // ============================================================
        // CONCEPT 3: An implicit type conversion via concatenation
        // ============================================================
        // verseNumber is an int, not a String. When + has a String on one
        // side, Java automatically converts the other side to a String.
        // No cast is written and none is needed.
        int verseNumber = 1;
        String headingOne = "Verse " + verseNumber;       // int 1 becomes "1"

        verseNumber += 1;                                 // plain int math here, not concatenation
        String headingTwo = "Verse " + verseNumber;       // int 2 becomes "2"

        // ---- Display the lyrics ----
        System.out.println(headingOne);
        System.out.println(verseOne);
        System.out.println(verseBreak);
        System.out.println(headingTwo);
        System.out.println(verseTwo);

        // Immutability, stated out loud: after all that concatenating,
        // the original literal is still exactly what it started as.
        System.out.println(verseBreak);
        System.out.println("Still unchanged after every concatenation: " + cheer);
        System.out.println("Home of the " + motto + " Badgers.");
    }
}
