---
layout: page
title: "Number Riddle Project"
permalink: /number-riddle/
---

Project 1 asks you to write a program that demonstrates a number trick: *choose
any integer, double it, add 6, divide it in half, and subtract the number you
started with — the answer is always 3.* It is the first graded project, and it
is really a checklist project: the riddle itself is easy, and the points come
from proving you can use variables, comments, arithmetic, compound assignment,
`print` and `println`, and conversions between `int` and `double`.

This page gives you the whole process, start to finish: the algebra behind the
riddle, every concept the requirements name, a complete worked program (on a
*different* riddle, so the technique is fully demonstrated without handing you
your own submission), a statement-by-statement runtime trace, and the real
compiler errors you will hit with the exact text they print. Every code block
here was compiled and run with JDK 25 — the outputs are real.

---

## Lesson at a glance

By the end you should be able to:

- Prove algebraically why the riddle always lands on 3
- Explain why the running value **must** be a `double` and not an `int`
- Rewrite each step of the riddle as a compound assignment (`*=`, `+=`, `/=`, `-=`)
- Widen an `int` into a `double` automatically, and narrow a `double` back with an `(int)` cast
- Say what `x *= 2.5` does when `x` is an `int` — and why it compiles
- Use `System.out.print` and `System.out.println` on purpose, not by habit
- Test a program against six deliberately chosen cases instead of one lucky one
- Read `possible lossy conversion`, `';' expected`, `cannot find symbol`, and
  `might not have been initialized`

**Vocabulary:** requirement, test case, compound assignment, widening,
narrowing, explicit cast, implicit cast, integer division, truncation,
accumulator, trace, edge case.

---

## The requirements, as stated

> Your program should show the riddle "Choose any integer, double it, add 6,
> divide it in half, and subtract the number you started with. The answer is
> always 3!"
>
> Make sure that your code works for all the below test cases: positive
> integer, negative integer, zero, one, positive double, negative double.
>
> For full credit your program must include: use of the camelCase naming
> convention, comments, the print and println methods, variables, arithmetic
> expressions, the compound assignment operator, conversions between int and
> double data types.

Eight requirements, six test cases. Treat that as a literal checklist — you can
lose points on a program that runs perfectly if it never uses `print` or never
converts between types. There is a mapping table near the end of this page that
shows where each one gets satisfied.

**Due:** August 31, 2026, 11:59 PM. Multiple submissions allowed, so submit
early and resubmit if you improve it.

---

## The process, in eight steps

This is the order to work in. Doing it in this order means you are never
debugging two things at once.

1. **Do the algebra on paper first.** You cannot tell whether your program is
   right if you do not already know the answer. (Part 1 below.)
2. **Create the file.** `NumberRiddle.java`, with `public class NumberRiddle`
   inside it. The names must match exactly, capital letters included.
3. **Get *something* to compile and run** — just the class, `main`, and one
   `println` of the riddle text. Confirm it runs before adding math.
4. **Build one test case only**, using a positive integer like 7. Get that one
   printing the right answer.
5. **Trace it by hand** against the table in Part 8 to confirm the variables
   hold what you think they hold.
6. **Copy that block five times** and change the starting value to cover the
   other five test cases. Run once; all six answers should be 3.
7. **Walk the requirement checklist** line by line and point at the line in
   your file that satisfies each one. Add comments as you go — they are graded.
8. **Delete the `.class` files, submit the `.java` file.** Resubmit if you
   improve it; multiple submissions are allowed.

Steps 3 and 4 are the ones people skip, and skipping them is why a program ends
up with six errors at once instead of one.

---

## What to do (setup)

You need one file. From the folder where you keep your work:

```sh
javac NumberRiddle.java   # compile -> produces NumberRiddle.class
java NumberRiddle         # run
```

JDK 11 and later also let you skip the separate compile step while you are
iterating:

```sh
java NumberRiddle.java
```

That is convenient for quick checks, but compile with `javac` before you submit
— it is the step that reports every error at once.

`.class` files are build output. Do not submit them and do not commit them.

---

## Part 1 — Why the answer is always 3

Do this before writing any Java. Let the starting number be *n* and follow the
instructions symbolically:

| Instruction | What you have now |
|---|---|
| Choose any integer | `n` |
| Double it | `2n` |
| Add 6 | `2n + 6` |
| Divide it in half | `n + 3` |
| Subtract the number you started with | `(n + 3) − n` = **3** |

The `n` cancels. Nothing about the starting value survives to the end, which is
exactly why the trick works on anything you feed it.

Two consequences for your code, and they are the two design decisions of this
whole project:

- **You must keep the original value.** The last step needs the number you
  started with. If you have been changing that same variable the whole way
  down, it no longer holds the original and there is nothing to subtract.
- **Every answer should be exactly 3.** You are not eyeballing "close enough."
  Any test case that prints something other than `3` or `3.0` is a bug, and you
  will spot it instantly.

---

## Part 2 — Why the running value must be a `double`

Look at the test cases: positive **double** and negative **double**. That single
line of the assignment decides your types.

```java
double startingNumber = 2.5;
int runningValue = startingNumber;
```

```
E1.java:6: error: incompatible types: possible lossy conversion from double to int
        int runningValue = startingNumber;
                           ^
1 error
```

An `int` cannot hold `2.5`. Java will not let you pretend otherwise, because
storing `2.5` in an `int` would silently throw away the `.5` — that is what
"lossy" means. So the variable that carries the value through the four steps has
to be a `double`.

Would an all-`int` version work for the four integer test cases? Yes, and that
is the trap. `2n + 6` is always even, so halving it never truncates, and an
`int` program will happily print `3` for 7, −4, 0, and 1. Then it fails to
compile the moment you add `2.5`, and if you are writing all six cases at the
end you will be staring at a program that "worked five minutes ago." Declare it
`double` from the start.

---

## Part 3 — Widening: `int` into `double`, free of charge

The requirements ask for **conversions between `int` and `double`**. You need
both directions, and the first one is free:

```java
int wholeStart = 7;
double startingNumber = wholeStart;   // widening -- no cast needed
```

This is **widening**: every `int` value fits inside a `double` without losing
anything, so Java performs the conversion automatically. `startingNumber` now
holds `7.0`.

Note what this does to your printing. `startingNumber` is a `double`, so
concatenating it produces `7.0`, not `7`. That is not a bug — it is the type
telling you the truth about itself.

---

## Part 4 — Narrowing: `double` back to `int`, with a cast

Going the other way loses information, so Java makes you say you meant it:

```java
double runningValue = 3.0;
int answer = (int) runningValue;   // narrowing -- explicit cast required
```

The `(int)` in front is an **explicit cast**. It says "I know this can lose
data; do it anyway." Casting a `double` to an `int` **truncates** — it chops the
fractional part off, it does not round. `(int) 3.9` is `3`, and `(int) -3.9` is
`-3`.

Here it is safe and useful: your answer is mathematically exactly `3.0`, so
casting prints a clean `3` instead of `3.0`. Verified:

```java
System.out.println("(int) 3.0 = " + (int) 3.0);
```

```
(int) 3.0 = 3
```

Using widening on the way in and a cast on the way out satisfies the
"conversions between int and double" requirement in both directions, and it does
it for a real reason rather than as decoration.

---

## Part 5 — The four steps are four compound assignments

This is the elegant part of the project, and it is why the requirements ask for
the compound assignment operator. The riddle has four steps; Java has four
compound assignment operators. They line up one to one.

| Riddle step | Compound assignment | Longhand equivalent |
|---|---|---|
| double it | `runningValue *= 2;` | `runningValue = runningValue * 2;` |
| add 6 | `runningValue += 6;` | `runningValue = runningValue + 6;` |
| divide it in half | `runningValue /= 2;` | `runningValue = runningValue / 2;` |
| subtract the original | `runningValue -= startingNumber;` | `runningValue = runningValue - startingNumber;` |

A **compound assignment** means "take what is in the variable, do this math to
it, and put the result back." The variable is acting as an **accumulator** — a
running total that gets updated in place.

The ordering rule that matters: `startingNumber` must still hold the *original*
value when you reach the last line. So copy the start into `runningValue` first
and only ever mutate `runningValue`. Part 10 shows what the output looks like
when you get this wrong.

---

## Part 6 — The hidden cast inside `*=`

This one is worth knowing for the exam as well as for this project. A compound
assignment quietly inserts a cast back to the variable's own type:

```java
int runningValue = 5;
runningValue *= 2.5;   // compiles! the cast is inserted for you
System.out.println("runningValue = " + runningValue);
```

```
runningValue = 12
```

`5 * 2.5` is `12.5`, and the `.5` is thrown away on the way back into the `int`.
No error, no warning. The longhand version, `runningValue = runningValue * 2.5;`,
would **not** compile — it reports the same lossy-conversion error from Part 2.

So the shorthand is not purely shorthand. It hides exactly the mistake this
project is designed to test. If you accidentally declare `runningValue` as an
`int`, the compiler will not save you; your doubles will just quietly truncate
and the last two test cases will print the wrong answer.

---

## Part 7 — `print` versus `println`

Both are required, so use them deliberately.

Picture a **cursor** sitting on the output. `System.out.print` writes text and
leaves the cursor right where it stopped. `System.out.println` writes text and
then moves the cursor to the start of the next line.

```java
System.out.print("no newline...");
System.out.println("same line");
```

```
no newline...same line
```

The natural split for this project: one `println` at the top for the riddle
text, then for each test case a `print` for the label and starting value
followed by a `println` for the answer, so each test case is one tidy line.

`System.out.println()` with nothing inside prints just a blank line — handy for
separating the riddle statement from the results.

---

## Part 8 — The worked example

Below is a complete, working program that uses every technique this project
requires. It runs a **different** riddle, so it demonstrates the method without
being your submission: *pick a number, add 3, multiply by 2, subtract 4, divide
in half, and subtract the number you started with — the answer is always 1.*

Check the algebra the same way you did in Part 1: `n` → `n + 3` → `2n + 6` →
`2n + 2` → `n + 1` → `1`. Always 1.

Your job is to read this, understand why each line exists, and then build the
same structure around *your* riddle's four steps. The mapping is direct.

```java
public class RiddleDemo
{
    public static void main(String[] args)
    {
        // The riddle this demo proves:
        //   pick a number, add 3, multiply by 2, subtract 4, halve it,
        //   then subtract the number you started with. The answer is always 1.
        System.out.println("Pick a number, add 3, multiply by 2, subtract 4,");
        System.out.println("divide in half, and subtract the number you started with.");
        System.out.println("The answer is always 1!");
        System.out.println();

        // ---- Test case 1: positive integer ----
        int wholeStart = 7;                    // an int, to show the conversion
        double startingNumber = wholeStart;    // widening: int -> double, automatic
        double runningValue = startingNumber;  // work on the copy, keep the original

        runningValue += 3;                     // add 3
        runningValue *= 2;                     // multiply by 2
        runningValue -= 4;                     // subtract 4
        runningValue /= 2;                     // divide in half
        runningValue -= startingNumber;        // subtract the original

        int answer = (int) runningValue;       // narrowing: double -> int, cast required
        System.out.print("Started with " + startingNumber + " ... answer is ");
        System.out.println(answer);

        // ---- Test case 2: negative integer ----
        wholeStart = -12;
        startingNumber = wholeStart;
        runningValue = startingNumber;
        runningValue += 3;
        runningValue *= 2;
        runningValue -= 4;
        runningValue /= 2;
        runningValue -= startingNumber;
        System.out.print("Started with " + startingNumber + " ... answer is ");
        System.out.println((int) runningValue);

        // ---- Test case 3: zero ----
        wholeStart = 0;
        startingNumber = wholeStart;
        runningValue = startingNumber;
        runningValue += 3;
        runningValue *= 2;
        runningValue -= 4;
        runningValue /= 2;
        runningValue -= startingNumber;
        System.out.print("Started with " + startingNumber + " ... answer is ");
        System.out.println((int) runningValue);

        // ---- Test case 4: one ----
        wholeStart = 1;
        startingNumber = wholeStart;
        runningValue = startingNumber;
        runningValue += 3;
        runningValue *= 2;
        runningValue -= 4;
        runningValue /= 2;
        runningValue -= startingNumber;
        System.out.print("Started with " + startingNumber + " ... answer is ");
        System.out.println((int) runningValue);

        // ---- Test case 5: positive double (no int variable this time) ----
        startingNumber = 2.5;
        runningValue = startingNumber;
        runningValue += 3;
        runningValue *= 2;
        runningValue -= 4;
        runningValue /= 2;
        runningValue -= startingNumber;
        System.out.print("Started with " + startingNumber + " ... answer is ");
        System.out.println(runningValue);      // left as a double on purpose

        // ---- Test case 6: negative double ----
        startingNumber = -3.75;
        runningValue = startingNumber;
        runningValue += 3;
        runningValue *= 2;
        runningValue -= 4;
        runningValue /= 2;
        runningValue -= startingNumber;
        System.out.print("Started with " + startingNumber + " ... answer is ");
        System.out.println(runningValue);
    }
}
```

Exact output:

```
Pick a number, add 3, multiply by 2, subtract 4,
divide in half, and subtract the number you started with.
The answer is always 1!

Started with 7.0 ... answer is 1
Started with -12.0 ... answer is 1
Started with 0.0 ... answer is 1
Started with 1.0 ... answer is 1
Started with 2.5 ... answer is 1.0
Started with -3.75 ... answer is 1.0
```

Notice the last two lines print `1.0` rather than `1`, because those two cases
skip the `(int)` cast on purpose. Showing both forms in one program is a fine
way to make the type conversions visible to whoever is grading it.

Repeating the block six times instead of using a loop is deliberate — loops are
a later unit, and this mirrors the repeated-block style used in class.

---

## Part 9 — Line-by-line walkthrough with a runtime trace

Take test case 1 from the worked example, `wholeStart = 7`, and follow the
memory state after every statement. A dash means the variable does not exist
yet.

| # | Statement | `wholeStart` | `startingNumber` | `runningValue` |
|---|---|---|---|---|
| 1 | `int wholeStart = 7;` | `7` | — | — |
| 2 | `double startingNumber = wholeStart;` | `7` | `7.0` | — |
| 3 | `double runningValue = startingNumber;` | `7` | `7.0` | `7.0` |
| 4 | `runningValue += 3;` | `7` | `7.0` | `10.0` |
| 5 | `runningValue *= 2;` | `7` | `7.0` | `20.0` |
| 6 | `runningValue -= 4;` | `7` | `7.0` | `16.0` |
| 7 | `runningValue /= 2;` | `7` | `7.0` | `8.0` |
| 8 | `runningValue -= startingNumber;` | `7` | `7.0` | `1.0` |
| 9 | `int answer = (int) runningValue;` | `7` | `7.0` | `1.0` (`answer` is `1`) |

Statement by statement:

**1.** `int wholeStart = 7;` declares an `int` and initializes it. This variable
exists only to demonstrate the conversion — the riddle would work without it.

**2.** `double startingNumber = wholeStart;` copies the value into a `double`.
This is the widening conversion. `startingNumber` now holds `7.0`. Crucially it
is a *copy*: changing `wholeStart` later would not change `startingNumber`.

**3.** `double runningValue = startingNumber;` makes the second copy — the one
that gets modified. This single line is what makes step 8 possible. Everything
after this touches only `runningValue`.

**4–7.** The four riddle steps, each a compound assignment. Read `runningValue
+= 3;` as "runningValue becomes runningValue plus 3." The right side is
evaluated using the current value, then the result is stored back into the same
box. Line 7, `/= 2`, is real division rather than integer division because
`runningValue` is a `double` — this is the line an `int` version would break on.

**8.** `runningValue -= startingNumber;` is the payoff. `startingNumber` still
holds `7.0` because nothing ever wrote to it, so `8.0 - 7.0` gives `1.0`.

**9.** `int answer = (int) runningValue;` narrows the result for a clean print.
`(int) 1.0` is `1`.

**10–11.** `print` writes the label and leaves the cursor in place; `println`
writes the answer and ends the line. Together they produce
`Started with 7.0 ... answer is 1`.

### Now trace your own riddle

Same table, your four steps, starting from 7:

| # | Step | `startingNumber` | `runningValue` |
|---|---|---|---|
| 1 | copy the start | `7.0` | `7.0` |
| 2 | double it | `7.0` | `14.0` |
| 3 | add 6 | `7.0` | `20.0` |
| 4 | divide in half | `7.0` | `10.0` |
| 5 | subtract the original | `7.0` | `3.0` |

If your program disagrees with this table, find the first row where it diverges.
That row is your bug.

---

## Part 10 — Debugging log

These are the errors this project actually produces, with the exact text the
compiler prints. Learning to read the message is more valuable than memorizing
the fix.

### Error 1 — `possible lossy conversion`

```
E1.java:6: error: incompatible types: possible lossy conversion from double to int
        int runningValue = startingNumber;
                           ^
1 error
```

**How to read it:** file and line number first (`E1.java:6`), then the problem,
then the offending line with a `^` under the exact spot. Read the direction:
"from double to int" tells you which way the conversion was going.

**Cause:** trying to store a `double` in an `int`. **Fix:** declare the variable
`double`, or add `(int)` if you genuinely want to truncate. In this project you
want `double`.

### Error 2 — file name does not match the class

```
Wrong.java:1: error: class NumberRiddle is public, should be declared in a file named NumberRiddle.java
public class NumberRiddle
       ^
1 error
```

**Cause:** a `public` class must live in a file with exactly its name plus
`.java`. Capitalization counts. **Fix:** rename the file, or rename the class.
The message tells you the required file name outright.

### Error 3 — `';' expected`

```
E3.java:5: error: ';' expected
        double runningValue = 7
                               ^
1 error
```

**Cause:** missing semicolon. **How to read it:** the `^` points just past the
end of the incomplete statement, so the fix goes where the caret is. Note it
blames line 5 even though line 6 is what "looked wrong" — the compiler reports
where the statement should have ended, not where it noticed.

### Error 4 — `might not have been initialized`

```
E4.java:6: error: variable runningValue might not have been initialized
        runningValue += 3;
        ^
1 error
```

**Cause:** you declared `double runningValue;` but never gave it a value, then
tried to add to it. `+=` reads the old value first, and there isn't one.
**Fix:** initialize at declaration — `double runningValue = startingNumber;`.

### Error 5 — `cannot find symbol`

```
E5.java:8: error: cannot find symbol
        runningValue -= StartingNumber;
                        ^
  symbol:   variable StartingNumber
  location: class E5
1 error
```

**Cause:** a name that does not exist — here, `StartingNumber` with a capital S
when the variable is `startingNumber`. Java is case-sensitive. **How to read
it:** the `symbol:` line names exactly what it could not find, and `location:`
says where it looked. Nine times out of ten it is a typo or a capitalization
slip; the tenth is a variable declared in the wrong place.

### Silent bug 1 — you changed the original

No error at all. This is worse than an error.

```java
double startingNumber = 7;
startingNumber += 3;
startingNumber *= 2;
startingNumber -= 4;
startingNumber /= 2;
startingNumber -= startingNumber;   // subtracts itself
System.out.println("answer = " + startingNumber);
```

```
answer = 0.0
```

**Cause:** with only one variable, the last step subtracts the number from
itself, which is always `0.0`. **Symptom to recognize:** every test case prints
`0.0`. **Fix:** two variables — one that never changes, one that does.

### Silent bug 2 — precedence, when you write it as one expression

```java
double startingNumber = 7;
double runningValue = startingNumber + 3 * 2 - 4 / 2 - startingNumber;
System.out.println("answer = " + runningValue);
double fixed = ((startingNumber + 3) * 2 - 4) / 2 - startingNumber;
System.out.println("fixed  = " + fixed);
```

```
answer = 4.0
fixed  = 1.0
```

**Cause:** `*` and `/` run before `+` and `-`, so the one-liner computes
something entirely different from the riddle. **Fix:** parentheses — or, better
for this project, keep the four steps on four separate lines with compound
assignments. Then there is no precedence to get wrong, and you satisfy the
compound-assignment requirement at the same time.

### Silent bug 3 — integer division truncating

```java
int startingNumber = 5;
int runningValue = startingNumber;
runningValue += 3;
runningValue *= 2;
runningValue -= 5;      // deliberately makes the total odd
runningValue /= 2;      // int division: the .5 is thrown away
System.out.println("int  version: " + runningValue);

double d = 5;
double r = d;
r += 3; r *= 2; r -= 5; r /= 2;
System.out.println("double version: " + r);
```

```
int  version: 5
double version: 5.5
```

**Cause:** when both operands of `/` are `int`, Java does integer division and
throws the fraction away — it truncates toward zero, it does not round. **Fix:**
make the running value a `double`.

Your riddle happens to be immune to this (`2n + 6` is always even), which is
precisely why it is dangerous: the bug is present but invisible until the day an
input makes it show.

### The transferable lesson

Errors that stop compilation are the friendly ones — they name the file, the
line, and the character. The bugs that cost points are the three that compile
cleanly and print a confident wrong answer. Defend against those by knowing the
expected answer *before* you run (Part 1), and by testing values that are
different in kind, not just different in size.

---

## Part 11 — The six test cases

Pick values that differ in *kind*. Two positive integers is one test case wearing
two hats; an integer and a negative double are genuinely different.

| Test case | Suggested value | Expected answer |
|---|---|---|
| Positive integer | `7` | `3` |
| Negative integer | `-4` | `3` |
| Zero | `0` | `3` |
| One | `1` | `3` |
| Positive double | `2.5` | `3.0` |
| Negative double | `-3.75` | `3.0` |

All six were verified by running the arithmetic: every one lands on exactly
`3.0`, with no floating-point dust. So do messier values — `0.1`, `3.3`, `-2.7`
and `1.0E15` all produce exactly `3.0` too, because doubling and halving are
exact operations in binary, so the rounding cancels out.

(For the curious: it finally breaks around `1.0E16`, where `2n` is so large that
adding 6 does not change the number at all — `1.0E16` yields `4.0` and `1.0E17`
yields `0.0`. Nowhere near anything you would test, but it is a real reminder
that `double` is approximate at extreme magnitudes.)

**Why zero and one are on the list:** they are **edge cases**. Zero breaks
anything that divides by the input, and one breaks anything that confuses
multiplication with addition. Assignments include them because they catch a
whole family of mistakes.

---

## Part 12 — Requirement checklist

Before you submit, point at the line in your file that satisfies each row.

| Requirement | Where it lives | How to confirm |
|---|---|---|
| camelCase | `startingNumber`, `runningValue`, `wholeStart` | lowercase first word, capital on each word after; no underscores |
| Comments | one per section and on each non-obvious line | they explain *why*, not what |
| `println` | riddle text, and the end of each result line | output lands on separate lines |
| `print` | label and starting value of each result line | label and answer share one line |
| Variables | `wholeStart`, `startingNumber`, `runningValue`, `answer` | no magic numbers repeated inline |
| Arithmetic expressions | the four riddle steps | `* 2`, `+ 6`, `/ 2`, `- startingNumber` |
| Compound assignment | `*=`, `+=`, `/=`, `-=` | four of them, one per step |
| `int` ↔ `double` conversion | `double startingNumber = wholeStart;` and `(int) runningValue` | widening in, cast out |
| All six test cases | six blocks in `main` | one run, six lines, every answer 3 |

### How it all fits together

The shape of the program is: state the riddle once, then repeat one small
pattern six times. The pattern is *save the original, copy it, transform the
copy four times, subtract the original, print*. Every graded requirement is
satisfied inside that pattern rather than bolted on beside it — the conversions
exist because the test cases mix `int` and `double`, the compound assignments
exist because the riddle has exactly four steps, and the two variables exist
because the final step needs a value the first four steps did not destroy. That
is the difference between a program that meets a checklist and one where the
checklist describes what the program naturally needed.

---

## Common mistakes to avoid

- Using one variable and subtracting it from itself — prints `0.0` every time.
- Declaring the running value `int`, which works for four test cases and then
  refuses to compile on `2.5`.
- Assuming `*=` protects you from truncation. It hides it. `int x = 5; x *= 2.5;`
  gives `12`.
- Writing the whole riddle as one expression and losing to operator precedence.
- Comments that restate the code. `// multiply by 2` earns nothing;
  `// keep the original so the last step has something to subtract` does.
- Capitalization slips — `StartingNumber` and `startingNumber` are two different
  names, and you will meet `cannot find symbol`.
- Naming the file something other than the public class.
- Testing only one value, seeing `3`, and declaring victory.
- Submitting the `.class` file instead of the `.java` file.

---

## Vocabulary

| Term | Meaning |
|---|---|
| Requirement | A graded item the program must contain, whether or not it affects the output |
| Test case | A specific input chosen to check one kind of behavior |
| Edge case | An input at a boundary — zero, one, negative — where mistakes hide |
| Accumulator | A variable holding a running value that is updated repeatedly |
| Compound assignment | `+=`, `-=`, `*=`, `/=` — update a variable using its own current value |
| Widening | Automatic conversion to a larger type (`int` → `double`); nothing is lost |
| Narrowing | Conversion to a smaller type (`double` → `int`); requires a cast, loses the fraction |
| Explicit cast | `(int) x` — you stating that you accept the loss |
| Implicit cast | A conversion Java inserts for you, as inside a compound assignment |
| Integer division | `/` on two `int`s: the fractional part is discarded |
| Truncation | Chopping off the fraction rather than rounding — `(int) 3.9` is `3` |
| Trace | Walking a program by hand, recording variable values after each statement |

---

## Check yourself

Answers are hidden — try each one first.

**1.** Why does the riddle always produce 3, in one sentence of algebra?

<details><summary>Answer</summary>
Doubling and adding 6 gives <code>2n + 6</code>; halving gives <code>n + 3</code>;
subtracting the original cancels the <code>n</code> and leaves 3.
</details>

**2.** What does this print, and why?

```java
int x = 5;
x *= 2.5;
System.out.println(x);
```

<details><summary>Answer</summary>
<code>12</code>. The compound assignment inserts an implicit cast back to
<code>int</code>, so <code>12.5</code> is truncated. Written longhand as
<code>x = x * 2.5;</code> it would not compile at all.
</details>

**3.** You run your program and every test case prints `0.0`. What is the bug?

<details><summary>Answer</summary>
You only have one variable, so the last step subtracts the number from itself.
Add a second variable that keeps the original untouched.
</details>

**4.** Which conversion needs a cast, and which one is automatic?

<details><summary>Answer</summary>
<code>int</code> → <code>double</code> is automatic (widening, nothing is lost).
<code>double</code> → <code>int</code> needs an explicit <code>(int)</code>
cast, because the fraction is discarded.
</details>

**5.** Your program prints `3.0` for the four integer cases, but you want `3`.
What is the one-word change?

<details><summary>Answer</summary>
Cast: print <code>(int) runningValue</code> instead of <code>runningValue</code>.
The value is exactly <code>3.0</code>, so truncation loses nothing.
</details>

**6.** The compiler says `cannot find symbol` and points at `StartingNumber`.
The variable is declared three lines above. What happened?

<details><summary>Answer</summary>
Capitalization. Java is case-sensitive, so <code>StartingNumber</code> and
<code>startingNumber</code> are different names.
</details>

**7.** Why are zero and one on the required test list when 7 already works?

<details><summary>Answer</summary>
They are edge cases. Zero exposes anything that divides by the input, and one
exposes confusion between multiplying and adding — bugs that a mid-sized value
like 7 sails right past.
</details>

---

## What's next

Once the riddle is submitted, the same skills carry straight into the next unit:
compound assignment becomes the standard way to update a counter, and the
`int`/`double` distinction turns up in every calculation that mixes whole
numbers with measurements. If you want to push this project further, try
restating the riddle so the answer is a number other than 3, then work out from
the algebra which constant you would have to change.
