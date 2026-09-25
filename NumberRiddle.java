public class NumberRiddle
{
    public static void main(String[] args)
    {
        // Project 1 — Number Riddle
        // Choose any integer, double it, add 6, divide it in half, and
        // subtract the number you started with. The answer is always 3.
        //
        // Algebra: n -> 2n -> 2n + 6 -> n + 3 -> 3.  The original number
        // always cancels, so the starting value never matters.

        // State the riddle first. print leaves the cursor on the same line,
        // so the next println finishes that sentence.
        System.out.print("Choose any integer, double it, add 6, ");
        System.out.println("divide it in half, and subtract the number you started with.");
        System.out.println("The answer is always 3!");
        System.out.println();

        // ---- Test case 1: positive integer ----
        int wholeStart = 8;                    // starts life as an int
        double startingNumber = wholeStart;    // widening: int -> double, automatic
        double runningValue = startingNumber;  // work on a copy so the original survives

        runningValue *= 2;                     // double it
        runningValue += 6;                     // add 6
        runningValue /= 2;                     // divide it in half
        runningValue -= startingNumber;        // subtract the number you started with

        int answer = (int) runningValue;       // narrowing: double -> int, cast required
        System.out.print("Started with " + startingNumber + " ... answer is ");
        System.out.println(answer);

        // ---- Test case 2: negative integer ----
        wholeStart = -15;
        startingNumber = wholeStart;
        runningValue = startingNumber;
        runningValue *= 2;
        runningValue += 6;
        runningValue /= 2;
        runningValue -= startingNumber;
        System.out.print("Started with " + startingNumber + " ... answer is ");
        System.out.println((int) runningValue);

        // ---- Test case 3: zero ----
        wholeStart = 0;
        startingNumber = wholeStart;
        runningValue = startingNumber;
        runningValue *= 2;
        runningValue += 6;
        runningValue /= 2;
        runningValue -= startingNumber;
        System.out.print("Started with " + startingNumber + " ... answer is ");
        System.out.println((int) runningValue);

        // ---- Test case 4: one ----
        wholeStart = 1;
        startingNumber = wholeStart;
        runningValue = startingNumber;
        runningValue *= 2;
        runningValue += 6;
        runningValue /= 2;
        runningValue -= startingNumber;
        System.out.print("Started with " + startingNumber + " ... answer is ");
        System.out.println((int) runningValue);

        // ---- Test case 5: positive double ----
        // No int variable here: the riddle still works on values with decimals.
        startingNumber = 4.5;
        runningValue = startingNumber;
        runningValue *= 2;
        runningValue += 6;
        runningValue /= 2;
        runningValue -= startingNumber;
        System.out.print("Started with " + startingNumber + " ... answer is ");
        System.out.println(runningValue);      // printed as a double on purpose

        // ---- Test case 6: negative double ----
        startingNumber = -2.25;
        runningValue = startingNumber;
        runningValue *= 2;
        runningValue += 6;
        runningValue /= 2;
        runningValue -= startingNumber;
        System.out.print("Started with " + startingNumber + " ... answer is ");
        System.out.println(runningValue);
    }
}
