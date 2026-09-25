import java.util.Scanner;

/**
 * THE LONG NIGHT AT VARICK HOUSE -- a murder mystery.
 *
 * Edgar Varick wrote to your office last week and said he believed someone
 * in his house intended to kill him. You arrived at seven o'clock to talk
 * him out of it. He was dead by a quarter past eight.
 *
 * The causeway floods at nine and opens at seven. For ten hours there is no
 * law on this island except you, and there is no one on this island except
 * the six people who had a reason.
 *
 * The murderer is chosen at random when the program starts. You cannot
 * memorize the answer. You have to earn it: the physical evidence tells you
 * five true facts about the killer's body and habits, and exactly one person
 * in the house fits all five. Everything else -- the motives, the alibis,
 * the secrets -- is true and useless. Reasons are not evidence.
 *
 * The counter that matters is ALARM. The killer is awake too, and every
 * door you open tells them how close you are. Alarm only goes up. Past 45
 * they start destroying what you have not found. Past 70 they kill the
 * person who was about to tell you something. Past 90 they come for you.
 *
 * Required elements: Scanner input, if/else, compound if/else, string
 * concatenation, substring, while loop, array, 2D array, for loop.
 */
public class VarickHouse
{
    static Scanner sc = new Scanner(System.in);

    // ---------- who you are ----------
    static String you = "";
    static String initials = "";

    // ---------- the clock ----------
    static int half = 0;        // half-hours since 9:00 PM
    static int lastHalf = 20;   // 20 half-hours later it is 7:00 AM

    // ---------- the killer's nerve. This only ever goes up. ----------
    static int alarm = 0;

    // ---------- who did it (decided by the machine, not by the author) ----------
    static int killer = 0;

    static String[] names =
    {
        "Adele Varick",
        "Thomas Varick",
        "Clare Varick",
        "Dr. Ruben Ostrow",
        "Mrs. Hail",
        "Niall Brennan"
    };

    static String[] roles =
    {
        "the second wife, 54",
        "the son, 31",
        "the daughter, 28",
        "the family physician, 60",
        "the housekeeper, 62",
        "the groundskeeper, 24"
    };

    /**
     * Five things you can observe about a living person by standing near
     * them for one evening. Column order: left-handed, over six feet,
     * carries a study key, smokes, strong enough to carry a grown man.
     *
     * No two rows are the same. That is the whole game.
     */
    static boolean[][] traits =
    {
        { true,  false, true,  false, false },   // Adele
        { false, true,  false, true,  true  },   // Thomas
        { true,  false, false, true,  false },   // Clare
        { false, true,  true,  true,  false },   // Ostrow
        { false, false, true,  false, false },   // Mrs. Hail
        { true,  true,  false, false, true  }    // Niall
    };

    static String[] traitHead = { "LEFT", "TALL", "KEY", "SMOKE", "CARRY" };

    // ---------- the five physical clues ----------
    static boolean[] clueFound = new boolean[5];
    static boolean[] clueGone  = new boolean[5];   // the killer got there first

    // ---------- the house ----------
    static String[] rooms =
    {
        "the study",
        "the library",
        "the kitchen and pantry",
        "the conservatory",
        "the boathouse",
        "the upstairs landing"
    };
    static int[] searchDepth = new int[6];   // 0 untouched, 1 once over, 2 stripped

    // ---------- what you have learned ----------
    static boolean todKnown = false;         // he did not die at 9:40
    static boolean weaponFound = false;
    static boolean letterFound = false;
    static int letterRecovered = 0;
    static boolean willKnown = false;

    static boolean[] questioned = new boolean[6];
    static boolean[] secretKnown = new boolean[6];
    static boolean[] deadSuspect = new boolean[6];
    static int lastQuestioned = -1;

    // ---------- how the night ends ----------
    static boolean evidenceBurned = false;
    static boolean secondMurder = false;
    static int victim2 = -1;
    static boolean attacked = false;
    static boolean youDied = false;
    static boolean confessed = false;
    static boolean finished = false;
    static int accusedIndex = -1;

    /**
     * The letter Edgar was burning when he was interrupted. You recover
     * less of it the later you get to the grate, because someone keeps
     * finding reasons to stand by that fire. substring does the burning.
     */
    static String letter =
        "Cathcart -- I have made up my mind and I will not be talked out of it a third time. "
      + "Draw the new instrument at once. None of them are to have the house, not one, and I "
      + "want that said plainly enough that it cannot be argued around after I am gone. I have "
      + "been used by every person under this roof for eleven years and I let it happen because "
      + "I could not bear to sit in these rooms alone. I am not frightened of dying, Cathcart. "
      + "I am frightened of how quietly it is going to be arranged, and by someone who will "
      + "still call me by my first name while doing it. Come Thursday. Bring the Ruben letter. "
      + "It is time that was on paper somewhere other than my desk. -- E. V.";

    // =========================================================
    //  SAFE INPUT
    // =========================================================

    /** Returns "" instead of crashing if the input runs out. */
    public static String readLine()
    {
        if (sc.hasNextLine())
        {
            return sc.nextLine();
        }
        return "";
    }

    /** substring pulls the first character so messy input still works. */
    public static String readChoice()
    {
        String typed = readLine();

        if (typed.length() == 0)
        {
            return "?";
        }

        return typed.substring(0, 1);
    }

    /** Reads a menu number 1..max and hands back a 0-based index, or -1. */
    public static int readIndex(int max)
    {
        String first = readChoice();

        for (int i = 1; i <= max; i++)
        {
            if (first.equals("" + i))
            {
                return i - 1;
            }
        }

        return -1;
    }

    /** Pads a string out to n characters so the notebook lines up. */
    public static String pad(String s, int n)
    {
        String out = s;

        while (out.length() < n)
        {
            out = out + " ";
        }

        return out;
    }

    // =========================================================
    //  THE CLOCK
    // =========================================================

    public static String clockString()
    {
        int mins = 21 * 60 + half * 30;   // minutes past midnight, rolling over
        int h = (mins / 60) % 24;
        int m = mins % 60;

        String ampm = "AM";
        if (h >= 12)
        {
            ampm = "PM";
        }

        int h12 = h % 12;
        if (h12 == 0)
        {
            h12 = 12;
        }

        String mm = "" + m;
        if (m < 10)
        {
            mm = "0" + m;
        }

        return h12 + ":" + mm + " " + ampm;
    }

    /** Every real action costs half an hour. You have twenty of them. */
    public static void spend()
    {
        half = half + 1;
    }

    // =========================================================
    //  MAIN
    // =========================================================

    public static void main(String[] args)
    {
        // The machine decides. Neither of us knows yet.
        killer = (int) (Math.random() * 6);

        intro();

        while (half < lastHalf && !finished && !youDied)
        {
            header();
            showMenu();
            doChoice(readChoice());

            if (!finished && !youDied)
            {
                killerMoves();
            }
        }

        ending();
    }

    // =========================================================
    //  OPENING
    // =========================================================

    public static void intro()
    {
        System.out.println("=====================================================");
        System.out.println("     T H E   L O N G   N I G H T   A T");
        System.out.println("            V A R I C K   H O U S E");
        System.out.println("=====================================================");
        System.out.println();
        System.out.print("Your last name, investigator: ");
        you = readLine();

        if (you.length() == 0)
        {
            you = "Rook";
        }

        // substring: the initial that goes on the file mark nobody reads
        initials = you.substring(0, 1) + ".";

        System.out.println();
        System.out.println("You are " + you + ". You investigate claims for an insurance company,");
        System.out.println("which means you are not police, you are not clever, and you are not");
        System.out.println("armed. You are a person who reads paperwork closely for a living.");
        System.out.println();
        System.out.println("Eleven days ago Edgar Varick wrote to your office. The letter is in");
        System.out.println("your coat pocket right now and the important sentence is this one:");
        System.out.println();
        System.out.println("      \"I believe someone in this house intends to kill me, and I");
        System.out.println("       believe they will do it in a way that collects.\"");
        System.out.println();
        System.out.println("You came out to Sceptre Island on the seven o'clock tide to tell him");
        System.out.println("that the policy pays either way and he should call the county sheriff.");
        System.out.println();
        System.out.print("Press ENTER.");
        readLine();

        System.out.println();
        System.out.println("-----------------------------------------------------");
        System.out.println();
        System.out.println("At 9:40 PM Mrs. Hail carried the coffee tray into the study and found");
        System.out.println("Edgar Varick at his desk with his reading glasses folded beside him.");
        System.out.println();
        System.out.println("Dr. Ostrow said heart, and everyone in the room wanted it to be heart,");
        System.out.println("and for about four minutes it was heart.");
        System.out.println();
        System.out.println("Then you turned the lamp and saw the wound behind his right ear, low");
        System.out.println("in the hairline, where a man's own hand cannot reach and a dead man's");
        System.out.println("hair falls neatly over it.");
        System.out.println();
        System.out.println("The causeway went under at nine. It comes back at seven.");
        System.out.println("There is no telephone; the line runs under the same water.");
        System.out.println();
        System.out.println("Six people. Ten hours. No police, " + you + ".");
        System.out.println();
        System.out.print("Press ENTER.");
        readLine();

        System.out.println();
        System.out.println("-----------------------------------------------------");
        System.out.println("  WHO IS IN THE HOUSE");
        System.out.println("-----------------------------------------------------");
        System.out.println();

        for (int i = 0; i < names.length; i++)
        {
            System.out.println("  " + (i + 1) + ") " + pad(names[i], 20) + roles[i]);
            System.out.println("     " + describe(i));
            System.out.println();
        }

        System.out.println("You can see all of that from across a room. It is not evidence.");
        System.out.println("It only becomes evidence when the house tells you which of those");
        System.out.println("things the killer was.");
        System.out.println();
        System.out.print("Press ENTER.");
        readLine();
    }

    /** What you can observe about a person without asking them anything. */
    public static String describe(int i)
    {
        String s = "";

        if (traits[i][0])
        {
            s = s + "Signs the register left-handed. ";
        }
        else
        {
            s = s + "Right-handed. ";
        }

        if (traits[i][1])
        {
            s = s + "Ducks under the hall lintel. ";
        }
        else
        {
            s = s + "Does not clear six feet. ";
        }

        if (traits[i][2])
        {
            s = s + "Has a study key on their ring. ";
        }
        else
        {
            s = s + "No study key. ";
        }

        if (traits[i][3])
        {
            s = s + "Smokes. ";
        }
        else
        {
            s = s + "Does not smoke. ";
        }

        if (traits[i][4])
        {
            s = s + "Could carry a grown man.";
        }
        else
        {
            s = s + "Could not lift Edgar off the floor.";
        }

        return s;
    }

    // =========================================================
    //  STATUS
    // =========================================================

    public static void header()
    {
        System.out.println();
        System.out.println("=====================================================");
        System.out.println("  " + clockString() + "   |   causeway opens 7:00 AM   |   " + you);
        System.out.println("=====================================================");
        System.out.println("  Evidence: " + clueCount() + " of 5   |   Still possible: " + stillPossible()
                         + "   |   The house: " + alarmWord());
    }

    public static int clueCount()
    {
        int n = 0;

        for (int t = 0; t < 5; t++)
        {
            if (clueFound[t])
            {
                n = n + 1;
            }
        }

        return n;
    }

    /** How many suspects survive the cross-reference right now. */
    public static int stillPossible()
    {
        int n = 0;

        for (int s = 0; s < 6; s++)
        {
            if (!eliminated(s))
            {
                n = n + 1;
            }
        }

        return n;
    }

    /**
     * A suspect is out if any clue you hold contradicts them, or if they
     * are lying dead in the pantry, which is its own kind of alibi.
     */
    public static boolean eliminated(int s)
    {
        if (deadSuspect[s])
        {
            return true;
        }

        for (int t = 0; t < 5; t++)
        {
            if (clueFound[t] && traits[s][t] != traits[killer][t])
            {
                return true;
            }
        }

        return false;
    }

    /** The killer's nerve, described from the outside. */
    public static String alarmWord()
    {
        if (alarm < 20)
        {
            return "asleep, or pretending";
        }
        else if (alarm < 45)
        {
            return "someone is up";
        }
        else if (alarm < 70)
        {
            return "doors keep closing on other floors";
        }
        else if (alarm < 90)
        {
            return "you are being followed";
        }
        else
        {
            return "whoever it is has stopped hiding it";
        }
    }

    // =========================================================
    //  THE NOTEBOOK -- the cross-reference, which is the whole game
    // =========================================================

    public static void notebook()
    {
        System.out.println();
        System.out.println("-----------------------------------------------------");
        System.out.println("  NOTEBOOK -- " + you + " -- " + clockString());
        System.out.println("-----------------------------------------------------");
        System.out.println();
        System.out.println("  WHAT THE HOUSE HAS TOLD ME ABOUT THE KILLER:");
        System.out.println();

        if (clueCount() == 0)
        {
            System.out.println("    Nothing yet. Six names and no facts.");
        }

        for (int t = 0; t < 5; t++)
        {
            if (clueFound[t])
            {
                System.out.println("    - " + clueSummary(t));
            }
            else if (clueGone[t])
            {
                System.out.println("    - (" + traitHead[t] + ": gone. Someone got there before me.)");
            }
        }

        System.out.println();
        System.out.println("  THE SIX, AGAINST THE FIVE:");
        System.out.println();
        System.out.print("    " + pad("", 21));

        for (int t = 0; t < 5; t++)
        {
            System.out.print(pad(traitHead[t], 7));
        }

        System.out.println("STATUS");

        // The killer's row: only the columns the house has given up.
        System.out.print("    " + pad("THE KILLER", 21));

        for (int t = 0; t < 5; t++)
        {
            if (clueFound[t])
            {
                if (traits[killer][t])
                {
                    System.out.print(pad("yes", 7));
                }
                else
                {
                    System.out.print(pad("no", 7));
                }
            }
            else
            {
                System.out.print(pad("?", 7));
            }
        }

        System.out.println("<-- match this row");
        System.out.println();

        // Nested loop: every suspect against every known fact.
        for (int s = 0; s < 6; s++)
        {
            System.out.print("    " + pad((s + 1) + ") " + names[s], 21));

            for (int t = 0; t < 5; t++)
            {
                if (traits[s][t])
                {
                    System.out.print(pad("yes", 7));
                }
                else
                {
                    System.out.print(pad("no", 7));
                }
            }

            if (deadSuspect[s])
            {
                System.out.println("dead");
            }
            else if (eliminated(s))
            {
                System.out.println("OUT");
            }
            else
            {
                System.out.println("still possible");
            }
        }

        System.out.println();

        if (clueCount() == 5)
        {
            System.out.println("    Five facts. Exactly one person in this house is all five.");
        }
        else if (stillPossible() == 1)
        {
            System.out.println("    One name left standing. It may still be worth more proof.");
        }

        notebookPeople();
    }

    /** The soft half of the notebook: motives, alibis, secrets. */
    public static void notebookPeople()
    {
        System.out.println("  WHAT PEOPLE HAVE TOLD ME:");
        System.out.println();

        int told = 0;

        for (int s = 0; s < 6; s++)
        {
            if (questioned[s])
            {
                told = told + 1;
                System.out.println("    " + names[s] + " -- " + alibi(s));
                System.out.println("     reason to want him gone: " + motive(s));

                if (secretKnown[s])
                {
                    System.out.println("     where they really were at 8:15: " + secret(s));
                }

                System.out.println();
            }
        }

        if (told == 0)
        {
            System.out.println("    I have not spoken to anyone yet.");
        }

        System.out.println();

        if (todKnown)
        {
            System.out.println("    * He died at 8:15, not 9:40. Every alibi above is for the");
            System.out.println("      wrong ninety minutes and every one of them knows it by now.");
        }

        if (letterFound)
        {
            System.out.println();
            System.out.println("    * What I pulled out of the grate:");
            System.out.println("      \"" + letter.substring(0, letterRecovered) + "\"");
        }

        System.out.println();
        System.out.println("    Six motives. All of them real. One of these people is lying to my");
        System.out.println("    face and I cannot hear the difference. Nobody ever can.");
    }

    // =========================================================
    //  THE MENU
    // =========================================================

    public static void showMenu()
    {
        System.out.println();
        System.out.println("What do you do? (each of these costs half an hour, except the notebook)");
        System.out.println("  1) Search a room.");
        System.out.println("  2) Question someone.");
        System.out.println("  3) Confront someone with what you have.");
        System.out.println("  4) Open the notebook.  (free)");
        System.out.println("  5) Name the killer.    (ends it)");
        System.out.print("> ");
    }

    public static void doChoice(String choice)
    {
        if (choice.equals("1"))
        {
            searchMenu();
        }
        else if (choice.equals("2"))
        {
            questionMenu();
        }
        else if (choice.equals("3"))
        {
            confrontMenu();
        }
        else if (choice.equals("4"))
        {
            notebook();
        }
        else if (choice.equals("5"))
        {
            accuseMenu();
        }
        else
        {
            System.out.println();
            System.out.println("You stand in the hall and listen to a house with six people in it");
            System.out.println("and no sound in it at all. Half an hour of the tide goes by.");
            spend();
        }
    }

    // =========================================================
    //  SEARCHING
    // =========================================================

    public static void searchMenu()
    {
        System.out.println();
        System.out.println("Where do you look?");

        for (int i = 0; i < rooms.length; i++)
        {
            String note = "stripped -- nothing left in it";

            if (searchDepth[i] == 0)
            {
                note = "you have not been in here yet";
            }
            else if (searchDepth[i] == 1)
            {
                note = "you have been over it once";
            }

            System.out.println("  " + (i + 1) + ") " + pad(rooms[i], 26) + note);
        }

        System.out.print("> ");
        int r = readIndex(rooms.length);

        if (r < 0)
        {
            System.out.println("You change your mind in the doorway. No time lost.");
            return;
        }

        if (searchDepth[r] >= 2)
        {
            System.out.println();
            System.out.println("You have already taken " + rooms[r] + " apart. There is nothing in");
            System.out.println("there but your own fingerprints now.");
            return;
        }

        if (searchDepth[r] == 0)
        {
            firstPass(r);
            alarm = alarm + 5;
        }
        else
        {
            secondPass(r);
            alarm = alarm + 7;
        }

        searchDepth[r] = searchDepth[r] + 1;
        spend();
    }

    /** First pass: the thing a careful person finds. Four of these are clues. */
    public static void firstPass(int r)
    {
        System.out.println();

        if (r == 5)
        {
            timeOfDeath();
            return;
        }

        revealClue(r);
    }

    /** Second pass: the thing you only find because you came back. */
    public static void secondPass(int r)
    {
        System.out.println();

        if (r == 0)
        {
            burnedLetter();
        }
        else if (r == 1)
        {
            theWill();
        }
        else if (r == 2)
        {
            thePantry();
        }
        else if (r == 3)
        {
            theGlove();
        }
        else if (r == 4)
        {
            theWeapon();
        }
        else
        {
            theBedroom();
        }
    }

    // =========================================================
    //  THE FIVE PHYSICAL CLUES
    // =========================================================

    /**
     * Reveals what the house knows about trait t. Each clue reads two ways
     * depending on the killer the machine picked, and both readings are
     * equally damning to somebody.
     */
    public static void revealClue(int t)
    {
        if (clueGone[t])
        {
            System.out.println(">> " + goneText(t));
            System.out.println(">> Somebody came through here between your visits, and they were");
            System.out.println(">> not tidying. They were editing.");
            return;
        }

        if (clueFound[t])
        {
            System.out.println(">> Nothing you did not already have.");
            return;
        }

        clueFound[t] = true;
        System.out.println(clueText(t));
        System.out.println();
        System.out.println(">> INTO THE NOTEBOOK: " + clueSummary(t));
    }

    public static String clueText(int t)
    {
        boolean v = traits[killer][t];

        if (t == 0)
        {
            if (v)
            {
                return "THE STUDY. You put the lamp on the floor and look at the wound the way\n"
                     + "you would look at a claim you did not believe.\n\n"
                     + "It is behind the RIGHT ear and the bruise tails off toward the front of\n"
                     + "the skull. A blow struck from behind that lands right and travels forward\n"
                     + "is a swing that started over the attacker's LEFT shoulder.\n\n"
                     + "There is a second, smaller thing: the desk blotter is shoved two inches\n"
                     + "to the RIGHT of the pen tray. A right-handed person clears the left side.\n"
                     + "Someone reached across this desk with the wrong hand.";
            }
            else
            {
                return "THE STUDY. You put the lamp on the floor and look at the wound the way\n"
                     + "you would look at a claim you did not believe.\n\n"
                     + "It is behind the right ear, but the bruise tails off toward the BACK of\n"
                     + "the skull -- the blow crossed the body from the attacker's right side and\n"
                     + "carried through. That is an ordinary right-handed swing.\n\n"
                     + "The desk blotter is shoved left, away from the pen tray, the way any\n"
                     + "right-handed person shoves things when they need the middle of a desk.";
            }
        }

        if (t == 1)
        {
            if (v)
            {
                return "THE LIBRARY. The estate ledger is missing. It lived on the top shelf of\n"
                     + "the east case -- seven feet up, and you know that because the eleven books\n"
                     + "beside it have a clean dust line and the gap does not.\n\n"
                     + "The stepstool is where the maid left it on Tuesday. There are no marks in\n"
                     + "the carpet nap, no scuff on the rail, no fingerprints on either side of it.\n\n"
                     + "Whoever took that ledger down took it down standing on the floor.";
            }
            else
            {
                return "THE LIBRARY. The estate ledger is missing from the top shelf of the east\n"
                     + "case, seven feet up, where you could not reach it either.\n\n"
                     + "The stepstool has been dragged three feet and put back a hand's width off\n"
                     + "its dents in the carpet. There is a fresh half-moon of mud on the top tread\n"
                     + "and the rail has been gripped hard enough to lift the wax.\n\n"
                     + "Whoever took that ledger down needed help to reach it.";
            }
        }

        if (t == 2)
        {
            if (v)
            {
                return "THE PANTRY. The key board hangs behind the door: forty tags, forty hooks,\n"
                     + "a layer of kitchen grease on all of them.\n\n"
                     + "The STUDY tag is on its hook. The grease film across it is unbroken, and\n"
                     + "so is the film on the two hooks either side of it.\n\n"
                     + "Nobody took the house key tonight. But the study door was locked at 9:40\n"
                     + "and Mrs. Hail opened it with her own, so it was locked from outside after\n"
                     + "he died. Someone stood in that hall and locked a door behind them with a\n"
                     + "key they already carried.";
            }
            else
            {
                return "THE PANTRY. The key board hangs behind the door: forty tags, forty hooks,\n"
                     + "a layer of kitchen grease on all of them.\n\n"
                     + "The STUDY hook is empty and the grease around it is smeared in four\n"
                     + "directions, the way a hook looks when a hand went at it fast.\n\n"
                     + "You find the key an hour's worth of looking later, wiped clean and dropped\n"
                     + "into the umbrella stand in the front hall.\n\n"
                     + "Someone who did not own a study key had to borrow one, and then had to be\n"
                     + "rid of it, and panicked about where.";
            }
        }

        if (t == 3)
        {
            if (v)
            {
                return "THE CONSERVATORY. Somebody stood in the dark in here for a long time.\n"
                     + "The glass is fogged in one oval at standing height, and the one pane that\n"
                     + "has been wiped clear with a sleeve looks straight down the corridor at the\n"
                     + "study door. This was not somewhere to hide. It was somewhere to watch from.\n\n"
                     + "In the orchid pot below the window there is a small grey cone of ash and\n"
                     + "a plug of dottle knocked out hot enough to have scorched the peat.\n\n"
                     + "The air in here still smells faintly of cavendish. Whoever waited by that\n"
                     + "window smoked while they waited, because that is what they do when they\n"
                     + "are nervous, because it is what they do all the time.";
            }
            else
            {
                return "THE CONSERVATORY. Somebody stood in the dark in here for a long time.\n"
                     + "The glass is fogged in one oval at standing height, and the one pane that\n"
                     + "has been wiped clear with a sleeve looks straight down the corridor at the\n"
                     + "study door. This was not somewhere to hide. It was somewhere to watch from.\n\n"
                     + "There is no ash. You go over that floor on your knees with the lamp and\n"
                     + "there is not one flake, not a match, not a burn in the matting, and the\n"
                     + "air is clean enough to smell the soil.\n\n"
                     + "Everyone in this house who smokes has left ash in three rooms tonight.\n"
                     + "They cannot help it; they do not even see themselves do it. Whoever waited\n"
                     + "twenty nervous minutes at that window did it without lighting anything.";
            }
        }

        if (v)
        {
            return "THE BOATHOUSE. He did not die in that chair. You knew it the moment you\n"
                 + "saw how his shoes sat -- toes down, laces clean, no heel scuff anywhere.\n\n"
                 + "Out here the mud between the door and the water takes a print like plaster.\n"
                 + "There is one set of tracks going down and one coming back, and the ones\n"
                 + "going down are pressed an inch deeper into the same mud.\n\n"
                 + "A man went down to that water carrying about a hundred and seventy pounds\n"
                 + "of Edgar Varick, and came back up without him, and never once set him down.";
        }
        else
        {
            return "THE BOATHOUSE. He did not die in that chair. You knew it the moment you\n"
                 + "saw how his shoes sat -- and out here you find where the heels went.\n\n"
                 + "Two parallel scores run forty feet across the gravel to the boathouse door,\n"
                 + "a hand's width apart, with a rest stop every eight or ten feet where the\n"
                 + "scoring stops and starts again.\n\n"
                 + "Whoever moved Edgar could not lift him. They hauled him backwards under the\n"
                 + "arms in stages, and they had to stop four times in forty feet to do it.";
        }
    }

    public static String clueSummary(int t)
    {
        boolean v = traits[killer][t];

        if (t == 0)
        {
            if (v)
            {
                return "The blow came from a LEFT hand.";
            }
            return "The blow came from a RIGHT hand.";
        }
        if (t == 1)
        {
            if (v)
            {
                return "The killer reached a seven-foot shelf from the floor. TALL.";
            }
            return "The killer needed the stool. NOT tall.";
        }
        if (t == 2)
        {
            if (v)
            {
                return "The killer locked the study with a key they already carried. HAS A KEY.";
            }
            return "The killer had to steal the board key and ditch it. NO KEY of their own.";
        }
        if (t == 3)
        {
            if (v)
            {
                return "The one who waited in the conservatory SMOKED while waiting.";
            }
            return "The one who waited in the conservatory left no ash. DOES NOT SMOKE.";
        }
        if (v)
        {
            return "Edgar was CARRIED to the water. The killer is strong.";
        }
        return "Edgar was DRAGGED in stages. The killer is NOT strong.";
    }

    /** What is left of a clue after the killer has been through it. */
    public static String goneText(int t)
    {
        if (t == 0)
        {
            return "Someone has washed the desk. The blotter is square, the lamp is straight,";
        }
        if (t == 1)
        {
            return "The carpet in front of the east case has been swept in long clean strokes.";
        }
        if (t == 2)
        {
            return "Every hook on the key board has been wiped down to the paint.";
        }
        if (t == 3)
        {
            return "The orchid pot has been turned out and repotted with fresh peat.";
        }
        return "Someone has raked the mud flat from the boathouse door to the waterline.";
    }

    // =========================================================
    //  THE SECOND PASS -- context, not proof
    // =========================================================

    /** The landing. This is the fact that makes every alibi worthless. */
    public static void timeOfDeath()
    {
        System.out.println("THE LANDING. Edgar's pill case sits on the hall table under the clock.");
        System.out.println("Seven compartments. Sunday through Saturday. Tonight's is still full.");
        System.out.println();
        System.out.println("Mrs. Hail will tell you, and the other five will confirm it without");
        System.out.println("understanding what they are confirming, that Edgar Varick took that");
        System.out.println("dose at a quarter past eight every night of his life for nine years,");
        System.out.println("standing right here, because the clock is right here.");
        System.out.println();
        System.out.println("He never came up for it.");
        System.out.println();
        System.out.println("You go back down and put your hand on him again and think about how");
        System.out.println("cold a room this size gets in ninety minutes.");
        System.out.println();
        System.out.println(">> INTO THE NOTEBOOK: Edgar died at 8:15, not 9:40.");
        System.out.println(">> Every alibi anyone has given you tonight is an alibi for the wrong");
        System.out.println(">> ninety minutes. You can go back and ask all of them again.");

        todKnown = true;
    }

    /**
     * The grate in the study. substring decides how much of Edgar's last
     * letter survived, and it survives less the longer you take, because
     * somebody keeps finding reasons to stand by that fire.
     */
    public static void burnedLetter()
    {
        System.out.println("THE STUDY, SECOND PASS. You kneel at the grate.");
        System.out.println();

        if (letterFound)
        {
            System.out.println("Only the ash you already went through.");
            return;
        }

        // The fire has been working the whole time you were elsewhere.
        int recovered = letter.length() - (half - 1) * 34;

        if (recovered > letter.length())
        {
            recovered = letter.length();
        }
        if (recovered < 44)
        {
            recovered = 44;
        }

        letterFound = true;
        letterRecovered = recovered;

        System.out.println("Edgar was burning a letter when he was interrupted, and he was not");
        System.out.println("good at it -- he laid it flat instead of standing it up, so the fire");
        System.out.println("ate it from the outside in and left the middle as a grey wafer.");
        System.out.println();
        System.out.println("You lift it onto the blotter with a fish knife. It holds together.");
        System.out.println();
        System.out.println("  \"" + letter.substring(0, recovered) + "\"");
        System.out.println();

        if (recovered >= letter.length())
        {
            System.out.println(">> The whole page. You went for the grate before anybody else could.");
        }
        else if (recovered > 420)
        {
            System.out.println(">> Most of it. An hour of that fire went up the chimney without you.");
        }
        else if (recovered > 150)
        {
            System.out.println(">> Rather less than half. It stops mid-sentence and the sentence it");
            System.out.println(">> stops in the middle of is the one you wanted.");
        }
        else
        {
            System.out.println(">> " + recovered + " characters out of " + letter.length() + ". Somebody has been feeding");
            System.out.println(">> that fire all night and it was not for the warmth.");
        }
    }

    public static void theWill()
    {
        System.out.println("THE LIBRARY, SECOND PASS. In the desk under the window, folded inside");
        System.out.println("a seed catalogue, is a draft in Edgar's own hand, dated nine days ago.");
        System.out.println();
        System.out.println("It leaves the house and the island to a wildfowl trust in Bangor.");
        System.out.println("It leaves Adele her settlement and not one dollar past it.");
        System.out.println("It leaves Thomas nothing, in a sentence two lines long that goes out");
        System.out.println("of its way to say why.");
        System.out.println("It leaves Clare nothing and does not bother explaining.");
        System.out.println("It cancels Mrs. Hail's pension in four words.");
        System.out.println("It does not mention Dr. Ostrow or Niall Brennan at all.");
        System.out.println();
        System.out.println(">> INTO THE NOTEBOOK: it is unsigned. If it had been signed on Thursday");
        System.out.println(">> it would have ruined four of them. Every person in this house who");
        System.out.println(">> knew about it had a reason to want Thursday not to happen.");
        System.out.println(">> That is four reasons. Reasons are not evidence.");

        willKnown = true;
    }

    public static void thePantry()
    {
        System.out.println("THE PANTRY, SECOND PASS. Behind the flour tins: a bottle of cooking");
        System.out.println("sherry, three-quarters gone, and a water glass with a lip print on it.");
        System.out.println();
        System.out.println("Mrs. Hail has been drinking alone back here every night for six years.");
        System.out.println("It is the saddest thing you will find in this house tonight and it has");
        System.out.println("nothing whatever to do with the murder.");
        System.out.println();
        System.out.println("You put the bottle back exactly where it was, because there is no");
        System.out.println("version of this night where she needs to know that you saw it.");
    }

    public static void theGlove()
    {
        System.out.println("THE CONSERVATORY, SECOND PASS. Wedged under the staging, behind the");
        System.out.println("seed trays, a single gardening glove. Canvas. Stiff with old soil.");
        System.out.println();
        System.out.println("There is a dark patch across the palm and the base of the fingers.");
        System.out.println("It is not soil. It has dried the wrong colour for soil.");
        System.out.println();
        System.out.println("It is also a size that fits four of the six people in this house, it");
        System.out.println("has been in that conservatory since spring, and any one of them could");
        System.out.println("have picked it up on the way through.");
        System.out.println();
        System.out.println(">> A glove with blood on it and no name in it. In a trial this helps");
        System.out.println(">> the defence more than it helps you. Bag it and do not build on it.");
    }

    public static void theWeapon()
    {
        System.out.println("THE BOATHOUSE, SECOND PASS. You take the lamp out on the float and");
        System.out.println("lie on your stomach and look into four feet of black water.");
        System.out.println();

        if (traits[killer][4])
        {
            System.out.println("It is a long way out -- past the end of the float, a throw that took");
            System.out.println("a shoulder behind it. You find it on the third pass of the lamp: the");
            System.out.println("brass dolphin from Edgar's desk, sitting upright in the silt.");
        }
        else
        {
            System.out.println("It is barely out at all. Six feet from the float, in water you could");
            System.out.println("stand up in, thrown by somebody with nothing left in their arms: the");
            System.out.println("brass dolphin from Edgar's desk, lying on its side in the silt.");
        }

        System.out.println();
        System.out.println("You get it up with a boathook and a coil of line and it comes out of");
        System.out.println("the water still matted with hair.");
        System.out.println();
        System.out.println(">> The weapon. It has been in salt water for hours -- there will be no");
        System.out.println(">> prints. But it came off that desk. Whoever did this did not bring a");
        System.out.println(">> weapon to the house. They picked up whatever was closest.");
        System.out.println(">> This was not planned. It was decided, in a room, in one second.");

        weaponFound = true;
    }

    public static void theBedroom()
    {
        System.out.println("THE LANDING, SECOND PASS. Edgar's bedroom, which nobody has been in.");
        System.out.println();
        System.out.println("On the nightstand, a water glass, a pair of spectacles he did not need");
        System.out.println("for distance, and a photograph of a woman who is not Adele, turned to");
        System.out.println("face the pillow.");
        System.out.println();
        System.out.println("In the drawer: your own letter. Your reply to him. He kept it.");
        System.out.println("He underlined the line where you said he should call the sheriff, and");
        System.out.println("in the margin, in pencil, he wrote: 'and say what -- that they love me?'");
        System.out.println();
        System.out.println("You sit down on a dead man's bed in a house full of his family and you");
        System.out.println("understand that he knew, " + you + ". He knew it was one of them. He just");
        System.out.println("could not stand to be the one who said which.");
    }

    // =========================================================
    //  QUESTIONING
    // =========================================================

    public static void questionMenu()
    {
        System.out.println();
        System.out.println("Who do you sit down with?");

        for (int i = 0; i < names.length; i++)
        {
            String note = "not yet spoken to";

            if (deadSuspect[i])
            {
                note = "past questioning";
            }
            else if (secretKnown[i])
            {
                note = "you have had both conversations";
            }
            else if (questioned[i])
            {
                if (todKnown)
                {
                    note = "worth going back to with the 8:15";
                }
                else
                {
                    note = "already gave you their nine-forty";
                }
            }

            System.out.println("  " + (i + 1) + ") " + pad(names[i], 20) + note);
        }

        System.out.print("> ");
        int s = readIndex(names.length);

        if (s < 0)
        {
            System.out.println("You do not go in. No time lost.");
            return;
        }

        if (deadSuspect[s])
        {
            System.out.println();
            System.out.println("You are not going to get anything else out of " + names[s] + ".");
            return;
        }

        question(s);
        lastQuestioned = s;
        spend();
    }

    public static void question(int s)
    {
        System.out.println();
        System.out.println("-----------------------------------------------------");
        System.out.println("  " + names[s] + ", " + roles[s]);
        System.out.println("-----------------------------------------------------");
        System.out.println();

        if (!questioned[s])
        {
            questioned[s] = true;
            alarm = alarm + 3;

            System.out.println("WHERE THEY SAY THEY WERE AT 9:40:");
            System.out.println("  " + alibi(s));
            System.out.println();
            System.out.println("WHY THEY MIGHT HAVE WANTED HIM GONE:");
            System.out.println("  " + motive(s));
            System.out.println();
            System.out.println(">> Both of those went in the notebook. Neither of them is evidence.");

            if (s == killer)
            {
                // No tell. There is never a tell. That is the point.
                System.out.println(">> They answer you steadily and they look at you while they do it.");
            }
            else
            {
                System.out.println(">> They answer you steadily and they look at you while they do it.");
            }

            return;
        }

        if (!todKnown)
        {
            System.out.println("You have nothing new to put to them, so you get the 9:40 story again,");
            System.out.println("word for word, which is either honesty or rehearsal and you cannot");
            System.out.println("tell the difference and neither can anyone else. Half an hour gone.");
            alarm = alarm + 2;
            return;
        }

        if (secretKnown[s])
        {
            System.out.println("You have taken this person apart twice. There is a third conversation");
            System.out.println("in there somewhere but it is not one you can have before seven o'clock.");
            alarm = alarm + 2;
            return;
        }

        secretKnown[s] = true;
        alarm = alarm + 6;

        System.out.println("You tell them he died at a quarter past eight.");
        System.out.println();
        System.out.println("You watch a person work out, in real time, that the story they have been");
        System.out.println("telling all night covers the wrong ninety minutes and is no use to them.");
        System.out.println();
        System.out.println("WHERE THEY ACTUALLY WERE AT 8:15:");
        System.out.println("  " + secret(s));
        System.out.println();
        System.out.println(">> That is a person handing you the worst thing about themselves to");
        System.out.println(">> stay out of a murder. Five of these six are doing exactly that.");
        System.out.println(">> The sixth is doing it better. You still cannot hear the difference.");
    }

    public static String alibi(int s)
    {
        if (s == 0)
        {
            return "\"In my bath, with the door bolted. I heard nothing at all.\"";
        }
        if (s == 1)
        {
            return "\"In the garage with the Packard and the radio on. Ask anyone who walked past.\"";
        }
        if (s == 2)
        {
            return "\"Upstairs in the blue room, unpacking a bag I had already unpacked twice.\"";
        }
        if (s == 3)
        {
            return "\"In the library with the decanter. Alone, which I realise is unhelpful.\"";
        }
        if (s == 4)
        {
            return "\"In my kitchen putting up the supper things, same as forty years of nights.\"";
        }
        return "\"Down at the boathouse tying the launch off short. Tide runs hard on a flood.\"";
    }

    public static String motive(int s)
    {
        if (s == 0)
        {
            return "She has been signing his name to the household accounts for three years,\n"
                 + "     and on Tuesday he sat down with the bank letter and started adding.";
        }
        if (s == 1)
        {
            return "He owes forty thousand dollars to men who do not retain lawyers, and on\n"
                 + "     Sunday his father refused him at the table, out loud, in front of staff.";
        }
        if (s == 2)
        {
            return "He cut her out last week for marrying a man he called, in writing, an error.\n"
                 + "     She drove six hours tonight to beg and was left on the step until seven.";
        }
        if (s == 3)
        {
            return "Eleven years ago he signed a death certificate for Edgar's first wife that\n"
                 + "     he should not have signed, and Edgar kept the letter that proves it.";
        }
        if (s == 4)
        {
            return "Forty years in this house on a promised pension, and on Friday he dismissed\n"
                 + "     her with two weeks' wages. She is sixty-two and has nowhere to be.";
        }
        return "He is Edgar's son by a housemaid who was put on a train in 1932. Edgar has\n"
             + "     always known it, has never once said it, and hired him as a gardener.";
    }

    public static String secret(int s)
    {
        if (s == 0)
        {
            return "\"In his dressing room. Going through his coat pockets for the bank letter.\n"
                 + "     That is what I am. Write it down.\"";
        }
        if (s == 1)
        {
            return "\"On the hall telephone begging for one more week, before the line went out.\n"
                 + "     There is no one I can call to prove it. That is rather the point of them.\"";
        }
        if (s == 2)
        {
            return "\"Outside the study door. I heard him talking to someone and I could not make\n"
                 + "     myself knock, so I went back up. I was eight feet away and I walked away.\"";
        }
        if (s == 3)
        {
            return "\"In the study at eight, giving him his injection. He was alive when I left\n"
                 + "     and I am the last man in America who should be admitting he was in there.\"";
        }
        if (s == 4)
        {
            return "\"In my pantry with a bottle, the way I have been every night for six years,\n"
                 + "     and I would rather have hung than have you know it.\"";
        }
        return "\"In the potting shed behind the kitchen garden. Waiting for Clare, in the\n"
             + "     dark, like a fool, for twenty minutes. She never came down, and I did not\n"
             + "     want her name in your notebook, so I said the boathouse.\"";
    }

    // =========================================================
    //  CONFRONTING -- the fastest way to learn something, and the
    //  fastest way to get someone killed
    // =========================================================

    public static void confrontMenu()
    {
        System.out.println();
        System.out.println("Who do you put it to, and understand that they will tell the others?");

        for (int i = 0; i < names.length; i++)
        {
            String note = "still possible";

            if (deadSuspect[i])
            {
                note = "dead";
            }
            else if (eliminated(i))
            {
                note = "your own notebook has them OUT";
            }

            System.out.println("  " + (i + 1) + ") " + pad(names[i], 20) + note);
        }

        System.out.print("> ");
        int s = readIndex(names.length);

        if (s < 0)
        {
            System.out.println("You keep your mouth shut. No time lost, and that is worth something.");
            return;
        }

        if (deadSuspect[s])
        {
            System.out.println();
            System.out.println("You are shouting at a body. You stop.");
            return;
        }

        confront(s);
        spend();
    }

    public static void confront(int s)
    {
        System.out.println();
        System.out.println("-----------------------------------------------------");
        System.out.println();

        if (s == killer && clueCount() >= 3)
        {
            System.out.println("You lay it out in order and you do not raise your voice.");
            System.out.println();

            for (int t = 0; t < 5; t++)
            {
                if (clueFound[t])
                {
                    System.out.println("  \"" + clueSummary(t) + "\"");
                }
            }

            System.out.println();
            System.out.println("\"There are six people on this island. Five of them are wrong on at");
            System.out.println(" least one of those. You are wrong on none of them.\"");
            System.out.println();
            System.out.println(names[s] + " does not argue with any of it, which is how you know");
            System.out.println("it is over, because an innocent person argues with the first line.");
            System.out.println();

            confessionScene(s);
            confessed = true;
            finished = true;
            return;
        }

        if (s == killer)
        {
            alarm = alarm + 35;

            System.out.println("You put it to them with " + clueCount() + " facts in your hand and you find out");
            System.out.println("what " + clueCount() + " facts are worth.");
            System.out.println();
            System.out.println(names[s] + " takes it calmly, asks you one question you cannot answer,");
            System.out.println("and then asks whether you have said any of this to anyone else.");
            System.out.println();
            System.out.println("You say no before you think about it.");
            System.out.println();
            System.out.println(">> You have just told the only person in this house who needed to know");
            System.out.println(">> exactly how close you are and exactly how alone you are.");
            return;
        }

        if (eliminated(s))
        {
            alarm = alarm + 10;

            System.out.println("Your own notebook has " + names[s] + " out on the evidence and you go at");
            System.out.println("them anyway, because it is three in the morning and you want it to be");
            System.out.println("somebody.");
            System.out.println();
            System.out.println("They cry, or they go very cold, and either way you have burned half an");
            System.out.println("hour and told the house that you are guessing.");
            return;
        }

        alarm = alarm + 15;

        System.out.println("You put it to " + names[s] + " straight, and they take it badly, and in");
        System.out.println("the middle of taking it badly they say something they did not mean to.");
        System.out.println();

        freeClue(s);
    }

    /** A frightened innocent remembers something they had no idea mattered. */
    public static void freeClue(int s)
    {
        for (int t = 0; t < 5; t++)
        {
            if (!clueFound[t] && !clueGone[t])
            {
                System.out.println("\"You want to know what I saw? Fine. Then leave me alone.\"");
                System.out.println();
                System.out.println("  " + witnessLine(t));
                System.out.println();

                clueFound[t] = true;
                System.out.println(">> INTO THE NOTEBOOK: " + clueSummary(t));
                System.out.println(">> It cost you nothing but the fifteen minutes and every person in");
                System.out.println(">> this house now knows how you are working.");
                return;
            }
        }

        System.out.println("They have nothing left to give you. They have not had anything for hours.");
    }

    public static String witnessLine(int t)
    {
        boolean v = traits[killer][t];

        if (t == 0)
        {
            if (v)
            {
                return "\"Whoever went into that study pushed the door with the wrong shoulder.\n"
                     + "     Left. I noticed because the hinge sticks and you have to lean on it.\"";
            }
            return "\"Whoever went into that study leaned on the door right-shouldered, the way\n"
                 + "     everyone does, the way you have to, because the hinge sticks.\"";
        }
        if (t == 1)
        {
            if (v)
            {
                return "\"There was somebody in the corridor and the hall lamp went out above them.\n"
                     + "     It hangs at six foot four. It went out because their head was in it.\"";
            }
            return "\"There was somebody in the corridor and they walked clean under the hall lamp\n"
                 + "     without ducking, and that lamp catches every tall person in this house.\"";
        }
        if (t == 2)
        {
            if (v)
            {
                return "\"I heard the study lock turn. One turn, no fumbling, no scraping at all.\n"
                     + "     That is a person putting in a key they have put in a hundred times.\"";
            }
            return "\"I heard somebody scratching at the study lock for the better part of a minute\n"
                 + "     before it took. Nobody who owns that key does that.\"";
        }
        if (t == 3)
        {
            if (v)
            {
                return "\"I passed the conservatory and I smelled tobacco and I assumed it was Thomas,\n"
                     + "     because I always assume it is Thomas, and I did not look in.\"";
            }
            return "\"I passed the conservatory and there was somebody standing in it in the dark\n"
                 + "     with no light of any kind -- no cigarette, nothing. I could not see a face.\"";
        }
        if (v)
        {
            return "\"I saw somebody go past the kitchen window with him. Carrying him. Upright,\n"
                 + "     like a man carrying a rolled rug, and not slowly either.\"";
        }
        return "\"I heard something being pulled across the gravel, stopping and starting and\n"
             + "     stopping, for a long time, and I told myself it was the dog.\"";
    }

    // =========================================================
    //  THE KILLER'S NIGHT
    // =========================================================

    static int burns = 0;

    public static void killerMoves()
    {
        if (alarm >= 45 && burns == 0)
        {
            burns = 1;
            burnOne();
        }
        else if (alarm >= 62 && burns == 1)
        {
            burns = 2;
            burnOne();
        }

        if (alarm >= 70 && !secondMurder)
        {
            secondBody();
        }

        if (alarm >= 90 && !attacked)
        {
            theyComeForYou();
        }
    }

    /** They clean the first thing you have not got to yet. */
    public static void burnOne()
    {
        for (int t = 0; t < 5; t++)
        {
            if (!clueFound[t] && !clueGone[t])
            {
                clueGone[t] = true;

                System.out.println();
                System.out.println(">>>------------------------------------------------");
                System.out.println(">>> Somewhere below you a door shuts, carefully, the way a door");
                System.out.println(">>> shuts when somebody is trying not to shut it.");
                System.out.println(">>>");
                System.out.println(">>> Whatever was in " + rooms[t] + " is not going to be there when");
                System.out.println(">>> you get to it. You can feel the night getting smaller.");
                System.out.println(">>>------------------------------------------------");
                return;
            }
        }
    }

    /** They kill the person you were last seen talking to. */
    public static void secondBody()
    {
        secondMurder = true;

        int v = lastQuestioned;

        if (v < 0 || v == killer || deadSuspect[v])
        {
            v = 4;
        }
        if (v == killer)
        {
            v = 5;
        }
        if (v == killer)
        {
            v = 0;
        }

        victim2 = v;
        deadSuspect[v] = true;

        System.out.println();
        System.out.println(">>>------------------------------------------------");
        System.out.println(">>> " + names[v] + " IS DEAD.");
        System.out.println(">>>");
        System.out.println(">>> You find them at the bottom of the back stairs at a quarter to the");
        System.out.println(">>> hour, and it is worse than Edgar because Edgar was arranged and");
        System.out.println(">>> this one was not. This one was done fast by somebody who has");
        System.out.println(">>> stopped being careful, in a house where five people are awake.");
        System.out.println(">>>");
        System.out.println(">>> They were killed for something they knew and had not yet told you,");
        System.out.println(">>> and the reason they had not yet told you is that you were somewhere");
        System.out.println(">>> else, doing something you thought was more important.");
        System.out.println(">>>");
        System.out.println(">>> It does prove one thing, and you hate that you thought of it first:");
        System.out.println(">>> " + names[v] + " did not kill Edgar Varick.");
        System.out.println(">>>------------------------------------------------");
    }

    /** Past ninety, you are not the investigator anymore. You are a loose end. */
    public static void theyComeForYou()
    {
        attacked = true;

        System.out.println();
        System.out.println(">>>------------------------------------------------");
        System.out.println(">>> The lamp in the corridor behind you goes out.");
        System.out.println(">>>");
        System.out.println(">>> Not flickers. Goes out, with a hand on it.");
        System.out.println(">>>------------------------------------------------");

        if (clueCount() >= 4)
        {
            System.out.println();
            System.out.println("You have been waiting for this for an hour, because you know what this");
            System.out.println("person is: somebody who does not plan, who decides in one second, who");
            System.out.println("picks up whatever is nearest. You knew the nearest thing to you was you.");
            System.out.println();
            System.out.println("You get an arm up and you get your back to the wall and you shout, and");
            System.out.println("a door opens upstairs, and that is all it takes. Whoever it was is gone");
            System.out.println("down the servants' passage before the light reaches the corridor.");
            System.out.println();
            System.out.println("You never see a face. What you get out of it is one fact, the way you");
            System.out.println("get everything tonight: off a body, in the dark, without being told.");
            System.out.println();

            freeClueQuiet();
        }
        else
        {
            System.out.println();
            System.out.println("You turn around into it.");
            System.out.println();
            youDied = true;
        }
    }

    /** The attack in the dark hands you one more fact, if you live to keep it. */
    public static void freeClueQuiet()
    {
        for (int t = 0; t < 5; t++)
        {
            if (!clueFound[t] && !clueGone[t])
            {
                clueFound[t] = true;
                System.out.println(">> INTO THE NOTEBOOK: " + clueSummary(t));
                return;
            }
        }

        System.out.println(">> Nothing you did not already have. You already had all of it.");
    }

    // =========================================================
    //  NAMING A NAME
    // =========================================================

    public static void accuseMenu()
    {
        System.out.println();
        System.out.println("The causeway opens at seven and after that this is a police matter and");
        System.out.println("you are a man with a notebook. Say a name now or do not say one at all.");
        System.out.println();

        for (int i = 0; i < names.length; i++)
        {
            String note = "still possible";

            if (deadSuspect[i])
            {
                note = "dead";
            }
            else if (eliminated(i))
            {
                note = "OUT on your own evidence";
            }

            System.out.println("  " + (i + 1) + ") " + pad(names[i], 20) + note);
        }

        System.out.println("  0) Not yet.");
        System.out.print("> ");
        int s = readIndex(names.length);

        if (s < 0)
        {
            System.out.println();
            System.out.println("You close the notebook. Not yet. No time lost.");
            return;
        }

        System.out.println();
        System.out.println("You are naming " + names[s] + " with " + clueCount() + " of the five facts in hand.");
        System.out.print("Type YES to say it out loud: ");
        String confirm = readLine();

        String yn = "n";
        if (confirm.length() > 0)
        {
            yn = confirm.substring(0, 1);
        }

        if (!yn.equals("y") && !yn.equals("Y"))
        {
            System.out.println();
            System.out.println("You do not say it. The room goes back to waiting.");
            return;
        }

        accusedIndex = s;
        finished = true;
    }

    public static void confessionScene(int s)
    {
        System.out.println("-----------------------------------------------------");
        System.out.println();

        if (s == 0)
        {
            System.out.println("\"Three years of my handwriting on his accounts. Three years of being");
            System.out.println(" the woman who married a rich old man, and I was going to be the woman");
            System.out.println(" who stole from one, in a courtroom, in this county.\"");
            System.out.println();
            System.out.println("\"He was so gentle about it. That was the unbearable part. He said we");
            System.out.println(" would sort it out on Thursday. He turned around to get the ledger.\"");
        }
        else if (s == 1)
        {
            System.out.println("\"You have never owed money to that kind of man. There is no bottom to");
            System.out.println(" it. There is no talking. There is only Thursday, and what happens on");
            System.out.println(" Friday if Thursday does not go your way.\"");
            System.out.println();
            System.out.println("\"I did not go in there to do it. I went in there to ask him one more");
            System.out.println(" time. He said no in four words and turned around.\"");
        }
        else if (s == 2)
        {
            System.out.println("\"He left me on the step for two hours. Do you understand that? In the");
            System.out.println(" rain, at my own house, where my mother died, for two hours.\"");
            System.out.println();
            System.out.println("\"And then he let me in and he was kind, and he asked about my husband");
            System.out.println(" in that voice, and I picked up the first thing on the desk.\"");
        }
        else if (s == 3)
        {
            System.out.println("\"I signed a certificate in 1943 that said heart failure, because the");
            System.out.println(" alternative was an inquest and a family and a scandal, and because he");
            System.out.println(" asked me to, and because I was thirty-two and frightened of him.\"");
            System.out.println();
            System.out.println("\"He kept the letter. Eleven years, in that desk, and every year he");
            System.out.println(" found a way to mention it. Thursday it was going to a solicitor.\"");
        }
        else if (s == 4)
        {
            System.out.println("\"Forty years. I came into this house at twenty-two and I have buried");
            System.out.println(" his wife and raised his children and I know which drawer everything");
            System.out.println(" in it lives in, and he gave me two weeks and a reference.\"");
            System.out.println();
            System.out.println("\"I only went in to ask him to say it to my face. He said it to his");
            System.out.println(" papers. He did not even turn round, and I have never in my life been");
            System.out.println(" so angry at a back.\"");
        }
        else
        {
            System.out.println("\"He knew. Every day for four years he knew exactly who I was and he");
            System.out.println(" paid me forty dollars a week to cut his grass and he called me Brennan.");
            System.out.println(" Not once. Not one time in four years.\"");
            System.out.println();
            System.out.println("\"I asked him tonight. Straight out. And he looked at me like I had");
            System.out.println(" brought a bill to the wrong door, and he turned back to his desk.\"");
        }

        System.out.println();
        System.out.println("\"And then I stood there for a long time with him on the floor, and the");
        System.out.println(" whole rest of it -- the water, the door, the fire -- all of that was");
        System.out.println(" somebody else. I watched them do it. I could not make them stop.\"");
        System.out.println();
        System.out.println("They put their hands flat on the table and wait for you to do something,");
        System.out.println("and there is nothing to do until seven o'clock, so the two of you sit in");
        System.out.println("that room for three hours with the lamp on.");
    }

    // =========================================================
    //  ENDINGS
    // =========================================================

    public static void ending()
    {
        System.out.println();
        System.out.println("=====================================================");
        System.out.println("  " + clockString() + " -- SCEPTRE ISLAND");
        System.out.println("=====================================================");
        System.out.println();

        if (youDied)
        {
            endingYouDied();
        }
        else if (confessed)
        {
            endingConfession();
        }
        else if (accusedIndex == killer)
        {
            endingRight();
        }
        else if (accusedIndex >= 0)
        {
            endingWrong();
        }
        else
        {
            endingTide();
        }

        truth();
    }

    public static void endingYouDied()
    {
        System.out.println("ENDING: THE THIRD BODY, AND THE EASIEST ONE TO EXPLAIN");
        System.out.println();
        System.out.println("It is not a fight. You are not a man who has fights. It takes about as");
        System.out.println("long as Edgar took and it happens in a corridor you had walked four times");
        System.out.println("that night without once thinking about the lamp.");
        System.out.println();
        System.out.println("At seven o'clock the tide goes out and the county sheriff comes over the");
        System.out.println("causeway to a house with three dead people in it and six survivors, and");
        System.out.println("one of those six walks him through it, calmly, room by room.");
        System.out.println();
        System.out.println("Your notebook is in your coat. " + clueCount() + " facts, in pencil, in order, and");
        System.out.println("nobody who reads it will know what they are looking at, because the");
        System.out.println("cross-reference was never on the page. It was in your head.");
        System.out.println();
        System.out.println("That is the mistake, " + you + ". You had " + clueCount() + " of five and kept going");
        System.out.println("as though the person you were hunting was not also hunting you.");
    }

    public static void endingConfession()
    {
        System.out.println("ENDING: A KITCHEN TABLE AT FOUR IN THE MORNING");
        System.out.println();
        System.out.println("It is not the accusation that does it. Anyone can accuse anyone.");
        System.out.println("It is the fact that you held up five true things about a body and a");
        System.out.println("house, and only one person in six rooms matched all of them, and they");
        System.out.println("were sitting across from you while you read them out.");
        System.out.println();
        System.out.println("The sheriff takes " + names[killer] + " over the causeway at half past seven.");
        System.out.println("They go quietly. They have been sitting quietly since three.");
        System.out.println();
        System.out.println("A statement given to an insurance investigator in an unlocked room is");
        System.out.println("worth less in court than you would like. But the ledger comes up, and");
        System.out.println("the dolphin comes up out of the water, and the state's man goes over");
        System.out.println("your notebook for two hours and then shakes your hand.");

        if (secondMurder)
        {
            System.out.println();
            System.out.println("You got there. It took you until four in the morning, and " + names[victim2]);
            System.out.println("did not live until four in the morning, and both of those are true");
            System.out.println("and you will be carrying the second one a great deal longer.");
        }
        else
        {
            System.out.println();
            System.out.println("Nobody else died tonight. Given how this house was built, and who was");
            System.out.println("in it, that is the part worth putting in the report.");
        }
    }

    public static void endingRight()
    {
        System.out.println("You named " + names[killer] + " and you were right.");
        System.out.println();

        if (clueCount() >= 4)
        {
            System.out.println("ENDING: PROVED");
            System.out.println();
            System.out.println("The state's attorney reads your notebook the way you read a policy:");
            System.out.println("slowly, looking for the hole. There is not one. Five physical facts,");
            System.out.println("six people, one match, and a boathook's worth of brass with hair on it.");
            System.out.println();
            System.out.println("He asks you twice whether you are certain you are not police.");
            System.out.println();
            System.out.println("The trial is in March and it takes four days. The defence spends most");
            System.out.println("of that time on the glove, because the glove is the only soft thing");
            System.out.println("you brought them, which is why you told yourself not to build on it.");
        }
        else if (clueCount() >= 2)
        {
            System.out.println("ENDING: RIGHT, AND NOT ENOUGH");
            System.out.println();
            System.out.println("You are right. You are right the way a man is right about weather.");
            System.out.println();
            System.out.println("With " + clueCount() + " facts you can put " + names[killer] + " in a room with a motive");
            System.out.println("and a bad ninety minutes, and so can any competent defence attorney put");
            System.out.println("four other people in the same room with the same thing.");
            System.out.println();
            System.out.println("The grand jury sits in April. They return no bill in ninety minutes.");
            System.out.println();
            System.out.println("Being right is a private condition, " + you + ". Proving is a public one.");
            System.out.println("The house was offering you five facts all night and you took " + clueCount() + ".");
        }
        else
        {
            System.out.println("ENDING: A GUESS THAT LANDED");
            System.out.println();
            System.out.println("You had " + clueCount() + " facts and six motives and you picked a name out of the");
            System.out.println("six because it was ten to seven and you wanted the night to mean");
            System.out.println("something.");
            System.out.println();
            System.out.println("You happened to be right. Nobody will ever know that, including, in");
            System.out.println("about two years, you -- because nothing you can show anyone separates");
            System.out.println("the name you said from the five you did not.");
            System.out.println();
            System.out.println("No charge is brought. The file stays open. You are right and it is");
            System.out.println("worth precisely nothing, which is the lesson the house was teaching");
            System.out.println("all night and you kept mistaking for a mystery.");
        }
    }

    public static void endingWrong()
    {
        System.out.println("ENDING: THE WRONG NAME, SAID OUT LOUD, IN A SMALL PLACE");
        System.out.println();
        System.out.println("You named " + names[accusedIndex] + ".");
        System.out.println();

        boolean contradicted = false;

        for (int t = 0; t < 5; t++)
        {
            if (clueFound[t] && traits[accusedIndex][t] != traits[killer][t])
            {
                contradicted = true;
            }
        }

        if (contradicted)
        {
            System.out.println("Your own notebook said no. It said no on this line:");
            System.out.println();

            for (int t = 0; t < 5; t++)
            {
                if (clueFound[t] && traits[accusedIndex][t] != traits[killer][t])
                {
                    System.out.println("   " + clueSummary(t));
                    System.out.println("   ...and " + names[accusedIndex] + " is not.");
                }
            }

            System.out.println();
            System.out.println("You had it written down in your own hand and you named them anyway,");
            System.out.println("because at some point in the night you stopped reading and started");
            System.out.println("deciding, and those feel identical from the inside.");
        }
        else
        {
            System.out.println("Nothing you had ruled them out. Nothing you had ruled them in either.");
            System.out.println("With " + clueCount() + " of five facts there were still " + stillPossible() + " people that fit, and");
            System.out.println("you picked the one whose motive read worst out loud.");
            System.out.println();
            System.out.println("Motive is a story. Stories are how you get the wrong person.");
        }

        System.out.println();
        System.out.println("This is an island of four hundred people. " + names[accusedIndex] + " is never");
        System.out.println("charged with anything, and never stops being the one from the Varick");
        System.out.println("business, and leaves in the spring, and the leaving is taken as proof.");
        System.out.println();
        System.out.println("The real one stands in the hall at seven o'clock while the sheriff comes");
        System.out.println("up the causeway, and hears you say a different name, and does not react,");
        System.out.println("because by then they have had ten hours of practice.");
    }

    public static void endingTide()
    {
        System.out.println("ENDING: SEVEN O'CLOCK");
        System.out.println();
        System.out.println("The water goes off the causeway at two minutes past and the county car");
        System.out.println("is over it by twenty past, and after that it stops being your night.");
        System.out.println();
        System.out.println("You hand over " + clueCount() + " facts and a notebook and six statements and a");
        System.out.println("piece of brass, and a man who has done this for nineteen years listens");
        System.out.println("to all of it and says the thing you already know: that everyone on this");
        System.out.println("island had a reason, and reasons do not convict anybody.");
        System.out.println();
        System.out.println("Nobody is charged. The six of them go back over the causeway one at a");
        System.out.println("time across the morning, and one of them takes it with them.");
        System.out.println();

        if (clueCount() == 0)
        {
            System.out.println("You never found a single physical fact. Ten hours in a house with the");
            System.out.println("evidence still in it, and you spent them talking to people, because");
            System.out.println("talking to people feels like working.");
        }
        else if (stillPossible() == 1)
        {
            System.out.println("The worst part is the notebook. There is one name on it that is not");
            System.out.println("crossed out. You got there. You got there at six-thirty and you had");
            System.out.println("nothing left to spend, and a name in a notebook is not an arrest.");
        }
        else
        {
            System.out.println("Your notebook narrows it to " + stillPossible() + ". That is real work and it is not an");
            System.out.println("answer, and the difference between those two things is the whole job.");
        }
    }

    // =========================================================
    //  AFTERWARD -- the machine shows its hand
    // =========================================================

    public static void truth()
    {
        System.out.println();
        System.out.println("-----------------------------------------------------");
        System.out.println("  IT WAS " + names[killer] + ", " + roles[killer] + ".");
        System.out.println("-----------------------------------------------------");
        System.out.println();
        System.out.println("It was decided before you typed your name, at random, out of six.");
        System.out.println("Then the house was made to tell the truth about it, five times over.");
        System.out.println();
        System.out.println("  THE FIVE FACTS THAT WERE IN THIS HOUSE ALL NIGHT:");
        System.out.println();

        for (int t = 0; t < 5; t++)
        {
            String mark = "  [ found  ] ";

            if (clueGone[t])
            {
                mark = "  [destroyed] ";
            }
            else if (!clueFound[t])
            {
                mark = "  [ missed ] ";
            }

            System.out.println(mark + clueSummary(t));
            System.out.println("               it was waiting in " + rooms[t] + ".");
        }

        System.out.println();
        System.out.println("  AND THE CROSS-REFERENCE, FINISHED, THE WAY IT WOULD HAVE READ:");
        System.out.println();
        System.out.print("    " + pad("", 21));

        for (int t = 0; t < 5; t++)
        {
            System.out.print(pad(traitHead[t], 7));
        }

        System.out.println();

        for (int s = 0; s < 6; s++)
        {
            String row = "    " + pad((s + 1) + ") " + names[s], 21);
            int wrong = 0;

            for (int t = 0; t < 5; t++)
            {
                if (traits[s][t] == traits[killer][t])
                {
                    row = row + pad("=", 7);
                }
                else
                {
                    row = row + pad("x", 7);
                    wrong = wrong + 1;
                }
            }

            if (wrong == 0)
            {
                row = row + "<-- all five";
            }
            else
            {
                row = row + "out on " + wrong;
            }

            System.out.println(row);
        }

        System.out.println();
        System.out.println("Every one of the other five is wrong on at least one line. That is not");
        System.out.println("luck and it is not cleverness. It is what a locked house is: a finite");
        System.out.println("list of people and a finite list of facts, and the only real question");
        System.out.println("is whether you spent your ten hours collecting facts or collecting");
        System.out.println("reasons.");
        System.out.println();
        System.out.println("Six people had a reason to kill Edgar Varick and five of them did not.");
        System.out.println();
        System.out.println("-- " + initials + " " + you + ", filed under Varick, E., policy 4471-C, pending.");
    }
}
