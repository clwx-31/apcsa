import java.util.Scanner;

/**
 * MadLibs - collects parts of speech from the user, parses them into a
 * story template, and prints the finished story.
 */
public class MadLibs
{
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args)
    {
        // ---- REQUIREMENT: a variable containing the incomplete Mad Lib ----
        // Placeholders are written in ALL CAPS inside angle brackets so they
        // are easy to spot by eye and easy to find with indexOf.
        String madLib = "One <ADJECTIVE> morning, a <NOUN> named <NAME> woke up and "
                      + "decided to <VERB> all the way to <PLACE>. "
                      + "Along the way it collected seventeen <PLURALNOUN>, "
                      + "which is far more <PLURALNOUN> than any <NOUN> really needs.";

        // ---- REQUIREMENT: sections of the Mad Lib to include ----
        // Both endings live in one variable. Only the section the user picks
        // gets parsed out and attached to the story.
        String endings = "<HAPPY>Everyone in <PLACE> cheered, and the mayor declared "
                       + "a holiday in honor of the <ADJECTIVE> <NOUN>.</HAPPY>"
                       + "<SPOOKY>Then every light in <PLACE> went out at once, and the "
                       + "<NOUN> was never seen again.</SPOOKY>";

        // ---- REQUIREMENT: various prompts to keep the user on track ----
        System.out.println("=== MAD LIBS ===");
        System.out.println("Answer 7 quick questions. Do not think too hard!");
        System.out.println();

        // ---- REQUIREMENT: algorithms that process user input ----
        // Each call prompts, reads a line, and re-prompts if the user just
        // pressed Enter without typing anything.
        String adjective  = getWord("Give me an adjective (a describing word): ");
        String noun       = getWord("Give me a noun (a person, place, or thing): ");
        String name       = getWord("Give me somebody's name: ");
        String verb       = getWord("Give me a verb (an action word): ");
        String place      = getWord("Give me a place: ");
        String pluralNoun = getWord("Give me a plural noun (more than one thing): ");

        // The seventh input decides which ending section to include.
        String ending = getEndingChoice();

        // ---- REQUIREMENT: parse for the parts of speech to replace ----
        // Attach the chosen ending first, so its placeholders get filled in
        // by the same passes that fill in the main story.
        String story = madLib + " " + getSection(endings, ending);

        story = fillIn(story, "<ADJECTIVE>", adjective);
        story = fillIn(story, "<NOUN>", noun);
        story = fillIn(story, "<NAME>", name);
        story = fillIn(story, "<VERB>", verb);
        story = fillIn(story, "<PLACE>", place);
        story = fillIn(story, "<PLURALNOUN>", pluralNoun);

        // ---- REQUIREMENT: a final print statement to display the story ----
        System.out.println();
        System.out.println("=== YOUR STORY ===");
        System.out.println(story);

        sc.close();
    }

    /**
     * Prompts with the given message and returns what the user typed.
     * Keeps asking as long as the user enters an empty line.
     */
    public static String getWord(String prompt)
    {
        System.out.print(prompt);
        String word = sc.nextLine();

        while (word.length() == 0)
        {
            System.out.print("You have to type something! " + prompt);
            word = sc.nextLine();
        }

        return word;
    }

    /**
     * Asks which ending the user wants and returns the matching section tag.
     * Loops until the answer is one the program understands.
     */
    public static String getEndingChoice()
    {
        System.out.print("Last one - do you want a HAPPY or a SPOOKY ending? ");
        String choice = sc.nextLine();

        while (!choice.equals("HAPPY") && !choice.equals("SPOOKY"))
        {
            System.out.print("Please type HAPPY or SPOOKY exactly: ");
            choice = sc.nextLine();
        }

        return choice;
    }

    /**
     * Parses out the text between <tag> and </tag>.
     * Returns an empty String if the section is not found.
     */
    public static String getSection(String text, String tag)
    {
        String openTag = "<" + tag + ">";
        String closeTag = "</" + tag + ">";

        int start = text.indexOf(openTag);
        int end = text.indexOf(closeTag);

        if (start == -1 || end == -1)
        {
            return "";
        }

        // Skip past the opening tag itself, then stop right before the closing tag.
        return text.substring(start + openTag.length(), end);
    }

    /**
     * Replaces every copy of placeholder in story with word.
     * Moves the finished text into result and keeps shrinking story, so the
     * loop always ends - even if the user types a placeholder as their word.
     */
    public static String fillIn(String story, String placeholder, String word)
    {
        String result = "";
        int spot = story.indexOf(placeholder);

        // indexOf returns -1 when there is nothing left to find, which ends the loop.
        while (spot != -1)
        {
            // Keep the text before the placeholder, then the user's word.
            result = result + story.substring(0, spot) + word;

            // Throw away everything up through the placeholder and search again.
            story = story.substring(spot + placeholder.length());
            spot = story.indexOf(placeholder);
        }

        return result + story;
    }
}
