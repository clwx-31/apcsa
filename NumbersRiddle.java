public class NumbersRiddle
{
    public static void main(String[] args)
    {
        // Project 1.1.6 — Numbers Riddle
        //
        // The riddle: choose any number, double it, add 6, divide it in half,
        // and subtract the number you started with. The answer is always 3.
        //
        // Why it always works: n -> 2n -> 2n + 6 -> n + 3 -> 3.
        // The starting number cancels itself out in the last step, so the
        // value you begin with never changes the answer.

        System.out.print("Choose any number, double it, add 6, ");
        System.out.println("divide it in half, and subtract the number you started with.");
        System.out.println("The answer is always 3!");
        System.out.println();

        // =================================================================
        // PHASE 1 — Get the logic right for ONE int test value.
        // Every step prints, so I can check each expected value by hand
        // instead of only trusting the final answer.
        // =================================================================

        System.out.println("PHASE 1 — tracing every step for one starting number");

        int testNumber = 7;                     // the one int I am testing with
        double startingNumber = testNumber;     // widening: int -> double, automatic
        double runningValue = startingNumber;   // a copy, so the original survives

        System.out.println("  Step 0, start with:        " + runningValue);

        runningValue *= 2;                      // double it
        System.out.println("  Step 1, doubled:           " + runningValue);

        runningValue += 6;                      // add 6
        System.out.println("  Step 2, plus 6:            " + runningValue);

        runningValue /= 2;                      // divide it in half
        System.out.println("  Step 3, halved:            " + runningValue);

        runningValue -= startingNumber;         // subtract the original number
        System.out.println("  Step 4, minus the start:   " + runningValue);

        int finalAnswer = (int) runningValue;   // narrowing: double -> int, cast required
        System.out.print("  Final answer for " + testNumber + ": ");
        System.out.println(finalAnswer);
        System.out.println();

        // =================================================================
        // PHASE 2 — The same four steps applied to all six required cases.
        // Each case gets its own starting variable so the values stay
        // readable and nothing gets overwritten by accident.
        // =================================================================

        System.out.println("PHASE 2 — all six test cases");

        int testPositiveInt = 12;
        int testNegativeInt = -9;
        int testZero = 0;
        int testOne = 1;
        double testPositiveDouble = 5.5;
        double testNegativeDouble = -3.25;

        // ---- Case 1: positive integer ----
        startingNumber = testPositiveInt;       // int widens into the double
        runningValue = startingNumber;
        runningValue *= 2;
        runningValue += 6;
        runningValue /= 2;
        runningValue -= startingNumber;
        System.out.print("  Positive integer (" + testPositiveInt + "): answer is ");
        System.out.println((int) runningValue); // cast back to int for a whole-number case

        // ---- Case 2: negative integer ----
        startingNumber = testNegativeInt;
        runningValue = startingNumber;
        runningValue *= 2;
        runningValue += 6;
        runningValue /= 2;
        runningValue -= startingNumber;
        System.out.print("  Negative integer (" + testNegativeInt + "): answer is ");
        System.out.println((int) runningValue);

        // ---- Case 3: zero ----
        startingNumber = testZero;
        runningValue = startingNumber;
        runningValue *= 2;
        runningValue += 6;
        runningValue /= 2;
        runningValue -= startingNumber;
        System.out.print("  Zero (" + testZero + "): answer is ");
        System.out.println((int) runningValue);

        // ---- Case 4: one ----
        startingNumber = testOne;
        runningValue = startingNumber;
        runningValue *= 2;
        runningValue += 6;
        runningValue /= 2;
        runningValue -= startingNumber;
        System.out.print("  One (" + testOne + "): answer is ");
        System.out.println((int) runningValue);

        // ---- Case 5: positive double ----
        // No cast at the end: this one prints as a double on purpose,
        // to show the riddle still works on values with decimals.
        startingNumber = testPositiveDouble;
        runningValue = startingNumber;
        runningValue *= 2;
        runningValue += 6;
        runningValue /= 2;
        runningValue -= startingNumber;
        System.out.print("  Positive double (" + testPositiveDouble + "): answer is ");
        System.out.println(runningValue);

        // ---- Case 6: negative double ----
        startingNumber = testNegativeDouble;
        runningValue = startingNumber;
        runningValue *= 2;
        runningValue += 6;
        runningValue /= 2;
        runningValue -= startingNumber;
        System.out.print("  Negative double (" + testNegativeDouble + "): answer is ");
        System.out.println(runningValue);
    }
}
