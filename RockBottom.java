import java.util.Scanner;

/**
 * ROCK BOTTOM -- a text adventure about methamphetamine.
 *
 * You are nineteen years old. Over fourteen days you will chase a feeling
 * you had exactly once and will never have again, and you will find out
 * what you are willing to trade for the attempt.
 *
 * Nothing in this game is exaggerated. The teeth, the sores, the bugs that
 * are not there, the coin jar, the ring, the envelope -- all of it is
 * ordinary. That is the horror. It is not dramatic. It is just Tuesday.
 *
 * Required elements: Scanner input, if/else, compound if/else,
 * string concatenation, substring, while loop, array, for loop.
 */
public class RockBottom
{
    static Scanner sc = new Scanner(System.in);

    // ---------- who you are ----------
    static String name = "";
    static String streetName = "";

    // ---------- what the drug takes, in order ----------
    static int addiction = 0;     // 0-100. Goes up. That is all it does.
    static int health = 100;
    static int weight = 165;      // pounds
    static int teeth = 32;        // you get one set
    static int sores = 0;         // you will make these yourself
    static int cash = 90;
    static int trust = 70;        // Maya
    static int day = 1;

    // ---------- the high you are chasing ----------
    static int firstHigh = 0;     // set on day one, never matched again
    static int timesUsed = 0;

    static boolean usedToday = false;
    static boolean inRehab = false;
    static boolean arrested = false;
    static boolean lilySawYou = false;
    static boolean tookTheRing = false;
    static boolean tookTheEnvelope = false;

    /**
     * The ladder. You do not jump to the bottom rung. You take one step,
     * and the step becomes normal, and then there is a next step.
     * Nobody ever plans past the rung they are standing on.
     */
    static String[] ladder = {
        "You sell your own PlayStation for $40. It cost you $300. You tell yourself it's temporary.",
        "You sell the tools your dad left you. The socket set with his initials scratched in the lid.",
        "You take the coin jar off Maya's fridge. The masking-tape label says LILY COLLEGE in marker.",
        "You pawn Maya's laptop while she's at work. She has two kids' worth of photos on it and no backup.",
        "You take your mother's wedding ring out of the dish by her sink. She has worn it for thirty-one years.",
        "You steal a woman's purse out of a shopping cart at the Food Lion. She is older than your mother.",
        "You take the cash box from the church on Seventh. It is for the food pantry. You know that.",
        "You take the birthday envelope off Lily's dresser. She is six. Her grandmother's handwriting is on the front.",
        "You give a man Maya's address and her work schedule because he offers you enough to get well.",
        "There is nothing left that you have not already sold, and you sell it anyway."
    };
    static int rung = 0;   // only ever goes up

    /**
     * Safe input. Returns "" instead of crashing if the input runs out
     * (piped input, or Ctrl-D at the terminal).
     */
    public static String readLine()
    {
        if (sc.hasNextLine())
        {
            return sc.nextLine();
        }
        return "";
    }

    public static void main(String[] args)
    {
        intro();

        // The loop runs until your body, the law, or the drug ends it.
        while (day <= 14 && health > 0 && addiction < 100 && !inRehab && !arrested)
        {
            usedToday = false;

            System.out.println();
            System.out.println("---------------------------------------------");
            System.out.println("  DAY " + day);
            System.out.println("---------------------------------------------");
            System.out.println(statusBar());
            System.out.println();
            System.out.println(bodyCheck());
            System.out.println();
            System.out.println(mindCheck());

            milestone();

            if (health > 0 && addiction < 100 && !arrested && !inRehab)
            {
                morningCraving();
            }

            if (health > 0 && addiction < 100 && !arrested && !inRehab)
            {
                showMenu();
                doChoice(readChoice());
                nightfall();
            }

            day = day + 1;
        }

        ending();
    }

    // =========================================================
    //  OPENING
    // =========================================================

    public static void intro()
    {
        System.out.println("=============================================");
        System.out.println("            R O C K   B O T T O M            ");
        System.out.println("=============================================");
        System.out.println();
        System.out.print("What is your first name? ");
        name = readLine();

        if (name.length() == 0)
        {
            name = "Kid";
        }

        // substring: the name people will replace you with
        streetName = name.substring(0, 1) + "-" + name.substring(name.length() - 1);

        System.out.println();
        System.out.println("You are " + name + ". You are nineteen.");
        System.out.println("You weigh " + weight + " pounds and you have all " + teeth + " of your teeth.");
        System.out.println();
        System.out.println("Your sister Maya is twenty-seven and she raised you more than your mother did.");
        System.out.println("Her daughter Lily is six and calls you Uncle " + name + " like it is a job title.");
        System.out.println("You work second shift at the warehouse. You are not remarkable. You are fine.");
        System.out.println();
        System.out.println("Tonight a guy named Gunner is in the break room and he says he has something");
        System.out.println("that will get you through a double. He is not a monster. He is twenty-three");
        System.out.println("and he is trying to be nice to you.");
        System.out.println();
        System.out.print("Press ENTER.");
        readLine();

        firstTime();
    }

    /**
     * The honest part. If this felt bad, nobody would ever do it twice.
     * It does not feel bad. It feels like the answer.
     */
    public static void firstTime()
    {
        System.out.println();
        System.out.println("---------------------------------------------");
        System.out.println();
        System.out.println("It takes about fifteen seconds.");
        System.out.println();
        System.out.println("Everything that was ever wrong with you is simply not there anymore.");
        System.out.println("Not fixed. Gone. Like it was never real in the first place.");
        System.out.println();
        System.out.println("You are funny. You are fast. You understand things.");
        System.out.println("You work the whole double and then you clean your entire apartment");
        System.out.println("at 4am, and you are not tired, and you have never once in your life");
        System.out.println("felt this okay about being a person.");
        System.out.println();
        System.out.println("You text Maya at 5am about a documentary. She writes back \"go to sleep lol\".");
        System.out.println();
        System.out.println("This is the best you will ever feel. Right now. This paragraph.");
        System.out.println("Your brain is dumping more dopamine in six hours than it makes in six months,");
        System.out.println("and it is spending receptors it cannot buy back.");
        System.out.println();
        System.out.println("You will spend the next fourteen days trying to get back to this page.");
        System.out.println("You will not get back to this page.");
        System.out.println();

        firstHigh = 100;
        timesUsed = 1;
        addiction = 12;
        health = 96;

        System.out.print("Press ENTER.");
        readLine();
    }

    // =========================================================
    //  STATUS READOUTS
    // =========================================================

    public static String statusBar()
    {
        String line = "Addiction " + addiction + "/100"
                    + "  |  " + weight + " lbs"
                    + "  |  " + teeth + " teeth"
                    + "  |  $" + cash
                    + "  |  Maya: " + trustWord();
        return line;
    }

    /** Keeps "1 pound" from reading as "1 pounds". */
    public static String lbs(int n)
    {
        if (n == 1)
        {
            return "1 pound";
        }
        return n + " pounds";
    }

    public static String trustWord()
    {
        if (trust >= 60)
        {
            return "picks up on the first ring";
        }
        else if (trust >= 40)
        {
            return "picks up, eventually";
        }
        else if (trust >= 20)
        {
            return "texts back hours later";
        }
        else if (trust > 0)
        {
            return "read, no reply";
        }
        else
        {
            return "blocked your number";
        }
    }

    /** What the drug is doing to the body. This is the part nobody posts. */
    public static String bodyCheck()
    {
        if (addiction < 20)
        {
            return "THE MIRROR: You look tired. Your jaw aches from clenching all night.\n"
                 + "You have not been hungry in two days and you think that's a bonus.";
        }
        else if (addiction < 40)
        {
            return "THE MIRROR: You are down " + lbs(165 - weight) + " and people have started saying it\n"
                 + "like a compliment. Your gums bleed when you brush. There is a raw patch on your\n"
                 + "cheek where you have been picking at something that is not there.";
        }
        else if (addiction < 60)
        {
            return "THE MIRROR: Your skin has gone gray and slack, like it belongs to someone older.\n"
                 + "A molar cracked while you were chewing gum; it came out in pieces over two days.\n"
                 + "You have " + sores + " open sores on your arms. You made all of them with your own hands.\n"
                 + "You have not eaten solid food since Thursday. You do not remember which Thursday.";
        }
        else if (addiction < 80)
        {
            return "THE MIRROR: The face in there is forty-five years old. You are nineteen.\n"
                 + "Your teeth are gray at the roots and two front ones are black and loose enough\n"
                 + "to move with your tongue, which you do constantly, which makes it worse.\n"
                 + "Your hair comes out in the shower in amounts you have stopped looking at.\n"
                 + sores + " sores. You dig at your forearms because you can feel them moving under the skin.\n"
                 + "Nothing is under the skin. Your nerves are misfiring. You know that and you dig anyway.";
        }
        else
        {
            return "THE MIRROR: You avoid it now. When you do look, you do not entirely recognize\n"
                 + "the arrangement of the face, and it takes a second, and that second is terrifying.\n"
                 + weight + " pounds. " + teeth + " teeth. " + sores + " sores, one of them hot and swollen and going green.\n"
                 + "Your heart has been running at 140 for nine days. It is a muscle. It is nineteen\n"
                 + "years old and it is wearing out like a man's in his sixties.";
        }
    }

    /** What it is doing to the mind. */
    public static String mindCheck()
    {
        if (addiction < 20)
        {
            return "THE HEAD: You could stop. You genuinely could. Everyone can, on day four.";
        }
        else if (addiction < 40)
        {
            return "THE HEAD: You think about it roughly every nine minutes. You have started\n"
                 + "planning your week around when you can be alone.";
        }
        else if (addiction < 60)
        {
            return "THE HEAD: You have not slept in four days. Sounds arrive with a half-second delay.\n"
                 + "You are certain there is a car outside. You have checked eleven times.";
        }
        else if (addiction < 80)
        {
            return "THE HEAD: Day seven with no sleep. There are people at the edges of rooms who are\n"
                 + "not there, and you have stopped being surprised by them, which is worse.\n"
                 + "You accused Maya of stealing from you. You believed it completely for two hours.";
        }
        else
        {
            return "THE HEAD: You are not frightened anymore because there is no one left in here\n"
                 + "to be frightened. Every thought is one thought wearing different clothes.\n"
                 + "You cannot remember your own phone number. You can remember Gunner's.";
        }
    }

    // =========================================================
    //  SUBSTRING: what you can actually still say
    // =========================================================

    /**
     * Past a point, the sentence in your head does not survive the trip out.
     * Uses substring to cut the line down as the addiction climbs.
     */
    public static String slur(String line)
    {
        if (addiction < 45 || line.length() < 8)
        {
            return line;
        }

        int keep = line.length() - (addiction - 40) * line.length() / 90;

        if (keep < 4)
        {
            keep = 4;
        }
        if (keep > line.length())
        {
            keep = line.length();
        }

        return line.substring(0, keep) + "...";
    }

    // =========================================================
    //  SCRIPTED DAYS -- the family, on a schedule
    // =========================================================

    public static void milestone()
    {
        if (day == 3)
        {
            System.out.println();
            System.out.println(">> Maya calls. \"You sound weird. You sound really fast.\"");
            System.out.println(">> You tell her it's a double shift. It is the first time you have");
            System.out.println(">> ever lied to her about something that mattered, and it is easy,");
            System.out.println(">> and that is the part you should be scared of.");
            trust = trust - 4;
        }
        else if (day == 5)
        {
            lilyParty();
        }
        else if (day == 8)
        {
            System.out.println();
            System.out.println(">> Your mother calls from Tulsa. Her hip is bad and she is sending you $200");
            System.out.println(">> because you told her your car needs a transmission.");
            System.out.println(">> Your car has been sitting in the lot with a flat for two weeks.");
            System.out.println(">> She says she is proud of you for working so hard. She means it.");
            cash = cash + 200;
        }
        else if (day == 11)
        {
            thePorch();
        }
        else if (day == 13 && trust > 0)
        {
            System.out.println();
            System.out.println(">> A voicemail from Lily, recorded by Maya. Six years old, very formal:");
            System.out.println(">> \"" + slur("Uncle " + name + ", you missed my birthday and also Tuesday and also") + "\"");
            System.out.println(">> The recording keeps going. You stop it. You do not listen to the rest.");
            System.out.println(">> You will not listen to it later either. You will keep it for nine years.");
        }
    }

    /** Day 5. The party. This is the one people remember. */
    public static void lilyParty()
    {
        System.out.println();
        System.out.println(">> It is Lily's sixth birthday party. You said you would be there at two.");
        System.out.print(">> Do you go? (yes/no) ");

        String answer = readLine();
        String first = "n";

        if (answer.length() > 0)
        {
            first = answer.substring(0, 1);
        }

        boolean saidYes = first.equals("y") || first.equals("Y");

        // Compound condition: showing up is not the same as being there.
        if (saidYes && addiction < 35)
        {
            System.out.println(">> You go. You are thin and jumpy and you talk too fast, and Maya watches");
            System.out.println(">> you across the yard with an expression you have not seen before.");
            System.out.println(">> Lily sits on your lap for the candles. You are still the good uncle here.");
            System.out.println(">> This is the last day that sentence is true.");
            trust = trust + 6;
        }
        else if (saidYes && addiction >= 35)
        {
            System.out.println(">> You go, four hours late, after everyone has left.");
            System.out.println(">> You are sweating in a way that is not from heat and you cannot stop");
            System.out.println(">> moving your jaw. There is a paper plate with her name on it saved for you.");
            System.out.println(">> Lily asks why your arms look like that. Nobody answers her.");
            System.out.println(">> In the kitchen Maya says, quietly, \"What are you on.\" Not a question.");
            System.out.println(">> You say \"nothing\" and you both know, and she lets you leave anyway,");
            System.out.println(">> which she will think about every night for the rest of her life.");
            trust = trust - 14;
            lilySawYou = true;
        }
        else
        {
            System.out.println(">> You do not go. You are two miles away in a house on Carbide Street");
            System.out.println(">> with people whose last names you do not know.");
            System.out.println(">> Maya sends one photo at 6pm: Lily in a paper crown, one chair empty.");
            System.out.println(">> No message with it. Just the photo. That is worse than a message.");
            trust = trust - 18;
        }
    }

    /** Day 11. Maya on the porch. The last door that is still open. */
    public static void thePorch()
    {
        System.out.println();
        System.out.println(">> Maya is on your porch at 7am. She has Lily in the car with the engine running.");
        System.out.println(">> She has a printout of your bank account, because you gave her access");
        System.out.println(">> three years ago when you were eighteen and thought that was normal.");
        System.out.println();
        System.out.println(">> She says: \"I'm not angry. I'm asking you one time and I'll believe you.\"");
        System.out.print(">> What do you tell her? ");

        String said = readLine();

        if (said.length() == 0)
        {
            said = "nothing";
        }

        String out = slur(said);

        System.out.println();
        System.out.println(">> YOU SAY: \"" + out + "\"");

        // Compound: telling the truth only reaches her if she can still hear it,
        // and if what left your mouth resembles what you meant.
        if (out.length() == said.length() && trust >= 35)
        {
            System.out.println(">> She looks at you for a long time. Then she says \"okay\" and she means");
            System.out.println(">> she is still here. She writes a number on the back of a receipt.");
            System.out.println(">> It is a bed at a place in Greensboro. She says it is open until Friday.");
            trust = trust + 10;
        }
        else if (trust >= 35)
        {
            System.out.println(">> Half of it does not make it out of your mouth. She watches you lose");
            System.out.println(">> the sentence in the middle and something in her face closes.");
            System.out.println(">> She gets back in the car. Lily waves at you through the glass.");
            System.out.println(">> You wave back. You are already thinking about what is in her purse.");
            trust = trust - 12;
        }
        else
        {
            System.out.println(">> She is not really asking. She already knows. She came to say one thing:");
            System.out.println(">> \"You can't be around Lily. I need you to hear that I love you and also");
            System.out.println(">> that you can't be around my daughter.\"");
            System.out.println(">> Both of those are true at once. That is what this does to families.");
            trust = trust - 10;
        }
    }

    // =========================================================
    //  THE DAY
    // =========================================================

    /** Compound if/else: past a point, the day starts without your permission. */
    public static void morningCraving()
    {
        if (addiction >= 55 && cash >= 20 && Math.random() < 0.45)
        {
            System.out.println();
            System.out.println(">> You do not decide anything. You are already in the car.");
            System.out.println(">> You lose four hours. They are simply not in your memory.");
            use(true);
        }
        else if (addiction >= 45 && cash < 20)
        {
            System.out.println();
            System.out.println(">> You are broke and coming down and you need money in the next hour.");
            descend();
        }
    }

    public static void showMenu()
    {
        System.out.println();
        System.out.println("What do you do today?");
        System.out.println("  1) Use.");
        System.out.println("  2) Go to work.");
        System.out.println("  3) Text Maya.");
        System.out.println("  4) Try to sleep.");
        System.out.println("  5) Call the number for the bed in Greensboro.");
        System.out.print("> ");
    }

    /** substring pulls the first character so messy input still works. */
    public static String readChoice()
    {
        String typed = readLine();

        if (typed.length() == 0)
        {
            return "4";
        }

        return typed.substring(0, 1);
    }

    public static void doChoice(String choice)
    {
        if (choice.equals("1"))
        {
            use(false);
        }
        else if (choice.equals("2"))
        {
            work();
        }
        else if (choice.equals("3"))
        {
            textMaya();
        }
        else if (choice.equals("4"))
        {
            sleep();
        }
        else if (choice.equals("5"))
        {
            callGreensboro();
        }
        else
        {
            System.out.println();
            System.out.println("You sit on the floor with your back against the door and the day goes by.");
            addiction = addiction + 4;
        }
    }

    // =========================================================
    //  USING
    // =========================================================

    public static void use(boolean forced)
    {
        usedToday = true;
        timesUsed = timesUsed + 1;

        int price = 30 + addiction / 2;

        if (cash < price)
        {
            descend();
        }

        if (arrested)
        {
            return;
        }

        cash = cash - price;

        if (cash < 0)
        {
            cash = 0;
        }

        // The whole point. Tolerance is subtraction, and it is permanent.
        int thisHigh = firstHigh - (timesUsed * 6) - addiction / 2;

        if (thisHigh < 3)
        {
            thisHigh = 3;
        }

        System.out.println();

        if (!forced)
        {
            if (thisHigh > 60)
            {
                System.out.println("It is good. Not like the first night. You tell yourself that's because");
                System.out.println("you were tired, or it's a different batch, or you didn't do enough.");
            }
            else if (thisHigh > 25)
            {
                System.out.println("It barely lifts you. You are not getting high anymore; you are getting");
                System.out.println("to normal, briefly, and normal now costs you $" + price + " and a day.");
            }
            else
            {
                System.out.println("Nothing. A flutter, and your heart going too hard, and that is all.");
                System.out.println("You do more. Still nothing. There is no more room in you for it.");
                System.out.println("You do it anyway because not doing it is unbearable.");
            }
        }

        System.out.println();
        System.out.println("   THIS HIGH: " + thisHigh + "% of your first night. Use #" + timesUsed + ".");
        System.out.println("   You will never reach 100 again. Those receptors are gone.");

        int hit = 5 + addiction / 20;
        addiction = addiction + hit;
        health = health - (3 + addiction / 25);
        weight = weight - (1 + addiction / 40);
        sores = sores + 1;

        // Compound: deep addiction AND a wrecked body is how nineteen-year-olds
        // have strokes in gas station bathrooms.
        if (addiction >= 70 && health <= 30)
        {
            System.out.println();
            System.out.println("   Your left hand stops working for ninety seconds. Then it works again.");
            System.out.println("   You do not go to a hospital. You have no insurance and an active warrant.");
            health = health - 20;
        }

        if (addiction >= 55 && (addiction >= 80 || Math.random() < 0.5) && teeth > 12)
        {
            teeth = teeth - 1;
            System.out.println();
            System.out.println("   Another one comes out. You put it in your pocket. You do not know why.");
        }
    }

    /**
     * The ladder. Each rung is only one step from the last one,
     * which is exactly how anybody ends up at the bottom of it.
     */
    public static void descend()
    {
        System.out.println();
        System.out.println("   You need money. Here is what is left.");
        System.out.println();
        System.out.println("   " + ladder[rung]);

        if (rung == 4)
        {
            tookTheRing = true;
            System.out.println("   You get $80 for it. It is worth $2,000 and it is not about the money.");
            trust = trust - 15;
        }
        else if (rung == 7)
        {
            tookTheEnvelope = true;
            System.out.println("   There is $35 in it. Two twenties would have been too obvious, so her");
            System.out.println("   grandmother put in a five to make it look like a kid's amount.");
            System.out.println("   Lily will ask Maya where it went. Maya will have to decide what to say.");
            trust = trust - 25;
        }
        else if (rung == 8)
        {
            System.out.println("   You tell yourself he only wants the TV. You know that is not true");
            System.out.println("   while you are saying it. You take the money.");
            trust = -100;
        }
        else if (rung >= 2)
        {
            trust = trust - 9;
        }

        cash = cash + 30 + rung * 5;

        if (rung >= 5 && Math.random() < 0.3)
        {
            System.out.println();
            System.out.println("   There is a camera you did not see. There is always a camera.");
            arrested = true;
            return;
        }

        if (rung < ladder.length - 1)
        {
            rung = rung + 1;
        }
    }

    // =========================================================
    //  EVERYTHING ELSE
    // =========================================================

    public static void work()
    {
        System.out.println();

        if (addiction < 30)
        {
            System.out.println("You work the full shift. Your supervisor says you've been on fire lately.");
            System.out.println("You are the most productive you have ever been. That is not a good sign.");
            cash = cash + 110;
            addiction = addiction + 2;
            health = health - 2;
        }
        else if (addiction < 55)
        {
            System.out.println("You fail the random. They walk you out through the floor, past everybody,");
            System.out.println("which is company policy and also a specific kind of humiliation.");
            System.out.println("Your badge stops working before you reach your car.");
            cash = cash + 40;
            addiction = addiction + 4;
            health = health - 5;
        }
        else
        {
            System.out.println("There is no job. There has not been a job since the second week.");
            System.out.println("You drive to the lot anyway some mornings and sit there, out of habit.");
            addiction = addiction + 4;
            health = health - 7;
        }
    }

    /** substring: what you meant to say versus what actually sends. */
    public static void textMaya()
    {
        System.out.println();

        if (trust <= 0)
        {
            System.out.println("Your message does not deliver. There is a green bubble and nothing after it.");
            System.out.println("She blocked you the day after the envelope. You keep typing to her anyway,");
            System.out.println("most nights, into a number that does not go anywhere.");
            addiction = addiction + 4;
            return;
        }

        System.out.print("What do you type to Maya? ");
        String message = readLine();

        if (message.length() == 0)
        {
            message = "hey";
        }

        String sent = slur(message);

        System.out.println();
        System.out.println("SENT: \"" + sent + "\"");

        if (addiction < 30 && message.length() >= 12)
        {
            System.out.println("She answers immediately. She always answers immediately.");
            System.out.println("She asks if you want to come for dinner Sunday. Lily is learning chess.");
            trust = trust + 8;
        }
        else if (sent.length() < message.length() && trust >= 25)
        {
            System.out.println("She reads it three times trying to find the rest of the sentence.");
            System.out.println("Then: \"where are you. tell me where you are and I'll come get you.\"");
            System.out.println("You do not answer. You are going to, later. You keep meaning to.");
            trust = trust - 7;
        }
        else if (trust < 25)
        {
            System.out.println("Delivered 9:41 PM. Read 9:41 PM. No reply.");
            System.out.println("She is sitting on her bed looking at it with her hand over her mouth.");
            System.out.println("Not replying is the hardest thing she has ever made herself do.");
            trust = trust - 3;
        }
        else
        {
            System.out.println("\"ok\" she says. One word from a person who used to send paragraphs.");
            trust = trust - 2;
        }

        addiction = addiction + 1;
    }

    public static void sleep()
    {
        System.out.println();

        if (addiction < 35)
        {
            System.out.println("Sixteen hours, face down. You wake up almost like a person.");
            health = health + 14;
            weight = weight + 2;
            addiction = addiction + 2;
        }
        else if (addiction < 65)
        {
            System.out.println("You lie in the dark for nine hours with your heart going and your legs");
            System.out.println("kicking on their own. You do not sleep. You are just horizontal.");
            health = health + 2;
            addiction = addiction + 4;
        }
        else
        {
            System.out.println("Withdrawal is not dramatic. It is a gray, total, flu-like despair, and it");
            System.out.println("keeps saying the one true thing: this all stops the second you use again.");
            System.out.println("It is right. That is the trap. It is not lying to you.");
            health = health - 12;
            addiction = addiction + 4;
        }

        if (health > 100)
        {
            health = 100;
        }
    }

    /** The exit. It is real, and it gets harder to reach every single day. */
    public static void callGreensboro()
    {
        System.out.println();
        System.out.println("A woman answers on the second ring. She is not surprised by anything.");
        System.out.println("She asks one question: \"Are you using?\"");
        System.out.print("> (yes/no) ");

        String answer = readLine();
        String first = "n";

        if (answer.length() > 0)
        {
            first = answer.substring(0, 1);
        }

        boolean toldTruth = first.equals("y") || first.equals("Y");

        // Compound: the truth is necessary but not sufficient. You also need
        // one person left who will drive you, and that is the resource you spent.
        if (toldTruth && trust >= 30)
        {
            System.out.println();
            System.out.println("You say yes and your voice does something you were not expecting.");
            System.out.println("There is a bed. Maya is outside in eleven minutes. She does not say");
            System.out.println("anything in the car. She holds your hand at every red light.");
            inRehab = true;
        }
        else if (toldTruth && trust < 30)
        {
            System.out.println();
            System.out.println("You say yes. There is a bed, forty miles away, and intake closes at five,");
            System.out.println("and there is no one left to drive you, and the bus is $14 you do not have.");
            System.out.println("You write the address on your forearm, across the sores.");
            System.out.println("By morning the ink is gone and so is the day and so is the bed.");
            addiction = addiction + 6;
        }
        else
        {
            System.out.println();
            System.out.println("\"No,\" you say. \"I was just asking for somebody.\"");
            System.out.println("You hang up and you are alone in the room and the phone is still warm.");
            addiction = addiction + 7;
            health = health - 4;
        }
    }

    public static void nightfall()
    {
        // You are not eating. That happens whether or not you used today.
        if (addiction >= 45)
        {
            weight = weight - 3;
        }
        else if (addiction >= 20)
        {
            weight = weight - 2;
        }

        if (addiction >= 50 && !usedToday)
        {
            System.out.println();
            System.out.println("At 3am the crash lands on you like a car and you cannot get off the floor.");
            health = health - 10;
        }
        else if (addiction >= 20 && !usedToday)
        {
            addiction = addiction + 1;
        }

        if (weight < 96)
        {
            weight = 96;
        }
        if (cash < 0)
        {
            cash = 0;
        }
        if (health < 0)
        {
            health = 0;
        }
    }

    // =========================================================
    //  ENDINGS
    // =========================================================

    public static void ending()
    {
        if (addiction > 100)
        {
            addiction = 100;
        }

        System.out.println();
        System.out.println("=============================================");
        System.out.println("  DAY " + day + " -- and what is left of " + name);
        System.out.println("=============================================");
        System.out.println(statusBar());
        System.out.println();
        System.out.println("In " + (day - 1) + " days you lost " + lbs(165 - weight) + " and " + (32 - teeth) + " teeth.");
        System.out.println();

        if (health <= 0)
        {
            System.out.println("ENDING: THE FLOOR OF A BATHROOM ON ROUTE 9");
            System.out.println();
            System.out.println("It is not an overdose in the way movies do it. Your heart has been");
            System.out.println("sprinting for two weeks and it is nineteen and it simply stops.");
            System.out.println("A gas station attendant finds you at 4:50am and does CPR for six minutes");
            System.out.println("and will not sleep right for a year.");
            System.out.println();
            System.out.println("Maya identifies you. She keeps telling the man with the clipboard that");
            System.out.println("you are nineteen, like if she says it enough he will correct the form.");
            System.out.println("Lily is told you got sick. She is six. She will get the real answer at");
            System.out.println("eleven, from a cousin, at a barbecue, and that is how she will find out.");
        }
        else if (arrested)
        {
            System.out.println("ENDING: COUNTY");
            System.out.println();
            System.out.println("Felony larceny. You get clean in the worst way there is: on a concrete");
            System.out.println("floor, for nine days, with the lights on, sweating through a paper mat.");
            System.out.println();
            System.out.println("Around week three your head clears enough to understand what you did,");
            System.out.println("all of it at once, with nothing to take the edge off it.");
            System.out.println("That is the actual punishment. The sentence is just scheduling.");

            if (tookTheEnvelope)
            {
                System.out.println();
                System.out.println("You keep coming back to the envelope. Not the purse, not the church.");
                System.out.println("The envelope with a six-year-old's name in her grandmother's handwriting.");
            }

            System.out.println();
            System.out.println("Maya comes on the first visiting day. She brings a photo of Lily and");
            System.out.println("holds it against the glass because they will not let her pass it through.");
        }
        else if (addiction >= 100)
        {
            System.out.println("ENDING: NOBODY HOME");
            System.out.println();
            System.out.println("There is still a body on Carbide Street. It answers to \"" + streetName + "\".");
            System.out.println("It does not answer to " + name + ". Nobody has used that name in nine days,");
            System.out.println("including, at this point, you.");
            System.out.println();
            System.out.println(lbs(weight) + ". " + teeth + " teeth. You are nineteen years old.");
            System.out.println("Maya still drives past the house sometimes, slowly, looking for you.");
            System.out.println("Twice she has seen you and not recognized you until she was past.");
        }
        else if (inRehab && trust >= 50)
        {
            System.out.println("ENDING: NINETY DAYS, ONE AT A TIME");
            System.out.println();
            System.out.println("Detox is nine days of the worst flu that has ever existed plus a grief");
            System.out.println("you cannot explain, because you are burying the only thing that ever");
            System.out.println("made you feel okay, and you have to be sad about it, which feels insane.");
            System.out.println();
            System.out.println("Your teeth do not grow back. That is permanent. So is some of the memory");
            System.out.println("damage; you will lose words mid-sentence for years and it will scare you.");
            System.out.println();
            System.out.println("Maya is in the lot every Sunday at noon. Week six she brings Lily.");
            System.out.println("Lily asks if you are better now and you tell her the truth, which is");
            System.out.println("\"I'm trying really hard today,\" and she accepts that completely.");
            System.out.println();
            System.out.println("You are not fixed. You are here. Today that is the whole thing.");
        }
        else if (inRehab)
        {
            System.out.println("ENDING: THE BED BY THE WINDOW");
            System.out.println();
            System.out.println("You check in alone, in clothes that smell, with a plastic bag of nothing.");
            System.out.println("Nobody comes on Sunday. Nobody comes the Sunday after that.");
            System.out.println("You get clean anyway, badly, slowly, out of pure stubbornness.");
            System.out.println();
            System.out.println("On day forty you write Maya nine pages. You do not send it.");
            System.out.println("On day seventy you throw it away and write four lines and send those.");
            System.out.println("She does not write back for five months. Then she does.");
        }
        else if (addiction < 40 && trust >= 50)
        {
            System.out.println("ENDING: STILL STANDING");
            System.out.println();
            System.out.println("Fourteen days and you still have a job and Maya still picks up.");
            System.out.println("From the outside you got away with it.");
            System.out.println();
            System.out.println("But you know a number by heart that you did not know three weeks ago,");
            System.out.println("and you have not deleted it, and every few days you look at it,");
            System.out.println("and there is a version of tomorrow where you are back on day one.");
            System.out.println("It is waiting. It is patient. It does not need you to decide today.");
        }
        else
        {
            System.out.println("ENDING: THE SAME DAY, AGAIN");
            System.out.println();
            System.out.println("Nothing cinematic happens. That is what nobody warns you about.");
            System.out.println("There is no bottom to hit. Day fifteen looks like day fourteen.");
            System.out.println("So does day two hundred. So does day nine hundred.");
            System.out.println();
            System.out.println("Addiction " + addiction + " and climbing. " + weight + " pounds. " + teeth + " teeth.");

            if (tookTheRing)
            {
                System.out.println("Your mother has stopped asking about the ring. She knows.");
            }
            if (tookTheEnvelope)
            {
                System.out.println("Lily does not ask about you anymore, which Maya notices, and hates.");
            }

            System.out.println();
            System.out.println("There is no rock bottom, " + name + ". That was a lie people tell to make");
            System.out.println("this sound like it has a floor. There is only further down, and you");
            System.out.println("have fourteen days of proof that you will keep going.");
        }

        System.out.println();
        System.out.println("---------------------------------------------");
        System.out.println("  WHAT IT COST, LINE BY LINE");
        System.out.println("---------------------------------------------");

        for (int i = 0; i < rung; i++)
        {
            System.out.println("  " + (i + 1) + ". " + ladder[i]);
        }

        // An empty list means one of two very different things.
        if (rung == 0 && addiction < 40)
        {
            System.out.println("  Nothing. You stopped early enough that the list is empty.");
            System.out.println("  Almost nobody gets to read a list this short.");
        }
        else if (rung == 0)
        {
            System.out.println("  Nothing on it yet -- only because the money has not run out yet.");
            System.out.println("  The list does not go away when you skip it. It waits.");
        }

        System.out.println();

        if (rung > 0)
        {
            System.out.println("Every item above is a real thing real people have done in their first month.");
            System.out.println("None of them planned past the step they were standing on.");
            System.out.println();
        }
        System.out.println();
        System.out.println("SAMHSA National Helpline: 1-800-662-4357. Free, 24/7, confidential.");
    }
}
