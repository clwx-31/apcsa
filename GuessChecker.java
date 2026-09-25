import java.util.Scanner;

public class GuessChecker
{
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args)
    {
        /* Add any variables you will need throughout the program here. */
        int correctPlaces = 0;   // how many digits are right AND in the right spot
        int correctDigits = 0;   // how many digits are in the code but in the wrong spot

        // Generate a random number
        int targetNumber = getRandomNumber();
        //System.out.println(targetNumber);  // uncomment for debugging

        // Break the random number into four variables.
        int r1 = targetNumber / 1000;
        int r2 = targetNumber / 100 % 10;
        int r3 = targetNumber / 10 % 10;
        int r4 = targetNumber % 10;

        // Get the user's guess
        int guess = getGuess();
        //System.out.println(guess);   // uncomment for debugging

        // Break the user's guess into four variables.
        int g1 = guess / 1000;
        int g2 = guess / 100 % 10;
        int g3 = guess / 10 % 10;
        int g4 = guess % 10;

        // The code never repeats a digit, so a guess that repeats one cannot be
        // fully correct. One compound condition replaces six separate checks.
        boolean guessRepeatsADigit = g1 == g2 || g1 == g3 || g1 == g4
                                  || g2 == g3 || g2 == g4
                                  || g3 == g4;

        /*your code here*/
        // Right digit, right spot. scoreSpot returns 1 or 0, so four calls add up
        // in a single compound assignment instead of four if statements.
        correctPlaces += scoreSpot(g1, r1) + scoreSpot(g2, r2)
                       + scoreSpot(g3, r3) + scoreSpot(g4, r4);

        // Right digit, wrong spot. Same idea, but the test needs && and !.
        correctDigits += scoreMisplaced(g1, r1, r1, r2, r3, r4)
                       + scoreMisplaced(g2, r2, r1, r2, r3, r4)
                       + scoreMisplaced(g3, r3, r1, r2, r3, r4)
                       + scoreMisplaced(g4, r4, r1, r2, r3, r4);

        System.out.println("You guessed " + guess + ".");
        System.out.println("Digits in the correct location: " + correctPlaces + " out of 4");
        System.out.println("Right digit, wrong location: " + correctDigits);

        if (guessRepeatsADigit && correctPlaces < 4)
        {
            System.out.println("Hint: the code has no repeated digits, so try four different ones.");
        }

        boolean cracked = correctPlaces == 4;
        boolean warm = !cracked && correctPlaces + correctDigits >= 3;

        if (cracked)
        {
            System.out.println("You cracked the code!");
        }
        else if (warm)
        {
            System.out.println("So close. The number was " + targetNumber + ".");
        }
        else
        {
            System.out.println("Not yet. The number was " + targetNumber + ".");
        }

        // close Scanner when done
        sc.close();
    }

    /**
     * Returns 1 when the guessed digit sits in the same place as the code digit,
     * and 0 otherwise, so callers can add the results together.
     */
    public static int scoreSpot(int guessDigit, int codeDigit)
    {
        if (guessDigit == codeDigit)
        {
            return 1;
        }

        return 0;
    }

    /**
     * Returns 1 when the guessed digit appears somewhere in the code but NOT in
     * the place it was guessed, and 0 otherwise.
     */
    public static int scoreMisplaced(int guessDigit, int codeDigitHere,
                                     int c1, int c2, int c3, int c4)
    {
        boolean inCode = guessDigit == c1 || guessDigit == c2
                      || guessDigit == c3 || guessDigit == c4;
        boolean rightSpot = guessDigit == codeDigitHere;

        if (inCode && !rightSpot)
        {
            return 1;
        }

        return 0;
    }

    /**
     * Returns a random four-digit number from 1000 to 9999 in which no
     * digit is repeated.
     */
    public static int getRandomNumber()
    {
        // The thousands digit cannot be 0, or the number would not be four digits.
        int d1 = (int) (Math.random() * 9) + 1;

        // Each later digit may be 0-9, but it must not match a digit already used.
        // Keep re-rolling while the new digit collides with an earlier one.
        int d2 = (int) (Math.random() * 10);
        while (d2 == d1)
        {
            d2 = (int) (Math.random() * 10);
        }

        int d3 = (int) (Math.random() * 10);
        while (d3 == d1 || d3 == d2)
        {
            d3 = (int) (Math.random() * 10);
        }

        int d4 = (int) (Math.random() * 10);
        while (d4 == d1 || d4 == d2 || d4 == d3)
        {
            d4 = (int) (Math.random() * 10);
        }

        // Rebuild the digits into one int by place value, one place at a time.
        int code = d1 * 1000;
        code += d2 * 100;
        code += d3 * 10;
        code += d4;

        return code;
    }

    /**
     * Prompts until the user types a number in the range 1000 to 9999,
     * then returns it.
     */
    public static int getGuess()
    {
        System.out.print("Enter a four-digit guess (1000-9999): ");
        int guess = sc.nextInt();

        // "Not in range" written as the negation of "in range".
        while (!(guess >= 1000 && guess <= 9999))
        {
            System.out.print("That is not a four-digit number. Try again: ");
            guess = sc.nextInt();
        }

        return guess;
    }
}
