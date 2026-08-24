---
layout: post
title: "Day 2 — Expressions and Assignment Statements (PLTW 1.1.3)"
date: 2026-08-24
categories: [notes]
tags: [variables, data-types, operators, arithmetic, casting, strings]
---

Day 1 was about getting text onto the screen. Today is about **storing values
and doing math with them** — variables, data types, arithmetic expressions,
and assignment statements. The project is `GalaxyWeight`, which computes what
you'd weigh on other planets.

This page covers every concept in the activity, walks through the finished
program statement by statement, and ends with the two mistakes that cost the
most points. Every code snippet here was compiled and run — the outputs shown
are real, not guesses.

---

## Lesson at a glance

By the end you should be able to:

- Declare and initialize variables of type `int`, `double`, and `boolean`
- Explain why `7 / 2` is `3` but `7.0 / 2` is `3.5`
- Use `+ - * / %` and predict the result, including operator precedence
- Cast between `int` and `double` and know what gets lost
- Build a String out of text and numbers with `+`
- Write a formula from a word problem as a Java expression

**Vocabulary:** variable, data type, primitive type, declaration,
initialization, assignment, expression, operator, operand, integer division,
modulus, precedence, casting, concatenation.

---

## Part 1 — What a variable is

A **variable** is a named box in memory that holds a value you can use later.

```java
double earthWeight = 125;
```

Three things happen at once here: you pick a **type** (`double`), you pick a
**name** (`earthWeight`), and you put a **value** in it (`125`).

Why bother instead of just typing `125` everywhere? Because if the number is
in one place, changing it once updates the whole program. In `GalaxyWeight`
you'll see `earthGravity` used four times — if your teacher uses 9.81 instead
of 9.8, you change one line, not four.

---

## Part 2 — Data types

Java is **strongly typed**: every variable must state what kind of value it
holds, and it can never hold anything else.

The three primitive types on the AP exam:

| Type | Holds | Example values | Size |
|---|---|---|---|
| `int` | whole numbers only | `125`, `-7`, `0` | 32 bits |
| `double` | numbers with decimals | `3.711`, `-0.5`, `9.8` | 64 bits |
| `boolean` | true or false | `true`, `false` | 1 bit |

`String` is also used constantly, but it is **not** a primitive — it's an
object. That distinction matters later in the course.

**Choosing a type:** can the value ever have a fraction? Use `double`. Is it a
count of whole things? Use `int`. In `GalaxyWeight` everything is `double`,
because gravity values like `3.711` and answers like `45.79` have decimals.

---

## Part 3 — Declaring, initializing, and assigning

Three different words for three different things, and teachers ask about the
difference:

```java
double weight;            // DECLARATION  — makes the box, no value yet
weight = 125;             // ASSIGNMENT   — puts a value in an existing box
double height = 5.5;      // INITIALIZATION — declare and assign in one line
```

You only write the type **once**, when you declare it. Writing
`double weight = 125;` and later `double weight = 130;` is an error — you're
trying to create the same box twice.

### The `=` sign does not mean "equals"

This is the single biggest mental adjustment from math class. `=` is the
**assignment operator**. It means *"compute the right side, then store the
result in the left side."*

```java
int count = 5;
count = count + 1;    // perfectly normal: compute 5 + 1, store 6 back in count
```

In algebra `count = count + 1` is nonsense. In Java it's an everyday
instruction. Read `=` as **"gets"**, not "equals": *count gets count plus one*.

The right side is **always** fully evaluated before anything is stored.

---

## Part 4 — Naming variables

**Rules** (break these and it won't compile):

- Start with a letter, `_`, or `$` — never a digit
- No spaces, no punctuation other than `_` and `$`
- Can't be a Java keyword (`class`, `int`, `public`, `static`, …)
- **Case-sensitive**: `earthWeight` and `earthweight` are different variables

**Conventions** (won't break the compiler, but you'll lose style points):

- `camelCase` for variables — first word lowercase, later words capitalized:
  `earthWeight`, `mercuryGravity`, `averageWeight`
- `PascalCase` for class names: `GalaxyWeight`
- Names should say what the value *means*. `mercuryGravity` beats `mg`
  beats `x`.

---

## Part 5 — Arithmetic operators

| Operator | Name | Example | Result |
|---|---|---|---|
| `+` | addition | `2 + 3` | `5` |
| `-` | subtraction | `10 - 4` | `6` |
| `*` | multiplication | `3 * 4` | `12` |
| `/` | division | `7 / 2` | **`3`** ← see Part 6 |
| `%` | modulus (remainder) | `7 % 2` | `1` |

An **expression** is anything that produces a value: `3 * 4`,
`earthWeight * 2`, or just `125`. The values being operated on are
**operands**.

---

## Part 6 — Integer division (the big one)

**Rule: `int` ÷ `int` always gives an `int`. The decimal part is thrown away —
not rounded, chopped off.**

Real output:

```
7 / 2      = 3
7.0 / 2    = 3.5
-7 / 2     = -3
```

`7 / 2` is `3`, not `3.5` and not `4`. Java looks at the **types** of the
operands, not at whether the answer happens to be even.

`7.0 / 2` gives `3.5` because one operand is a `double`, so Java promotes the
other and does decimal division.

**How to avoid it:** make sure at least one operand is a `double`. Any of
these work:

```java
7.0 / 2           // write a decimal point
(double) 7 / 2    // cast one operand
someDoubleVar / 2 // the variable is already a double
```

**The nastiest version** — this one catches everybody:

```
1 / 2 * 2.0  =  0.0
```

Java works left to right. `1 / 2` is evaluated **first**, both are `int`, so
it's `0`. Then `0 * 2.0` is `0.0`. The `double` showed up too late to help.

---

## Part 7 — Modulus `%`

`%` gives the **remainder** after division.

```
7 % 2      = 1        (7 ÷ 2 is 3 remainder 1)
17 % 5     = 2        (17 ÷ 5 is 3 remainder 2)
-7 % 2     = -1       (sign follows the left operand)
```

It looks useless now, but it's everywhere later:

- `n % 2 == 0` tests whether `n` is **even**
- `n % 10` extracts the **last digit** of a number
- Wrapping a value around a range, like clock arithmetic

---

## Part 8 — Operator precedence

Same idea as PEMDAS, with `%` joining the multiply/divide tier:

1. Parentheses `( )`
2. `*`, `/`, `%` — left to right
3. `+`, `-` — left to right

Real output:

```
2 + 3 * 4    = 14      (not 20 — multiplication first)
(2 + 3) * 4  = 20
10 - 4 - 3   = 3       (left to right: 6 - 3, not 10 - 1)
```

**When in doubt, add parentheses.** They cost nothing, they never change a
correct answer, and they make your intent obvious to whoever grades it.

---

## Part 9 — Mixing types, and casting

When an expression mixes `int` and `double`, Java **promotes** the `int` to a
`double` automatically and the result is a `double`:

```
5 / 2.0  = 2.5
```

**Casting** forces a conversion yourself, with `(type)` in front:

```java
double price = 19.99;
System.out.println((int) price);       // 19  -- truncated, NOT rounded
System.out.println((double) 7 / 2);    // 3.5 -- cast applies to the 7 first
```

Casting `double` → `int` **truncates**: `19.99` becomes `19`, and `19.99`
would become `19` even if it were `19.999`. It chops, it never rounds.

Note the second line carefully: the cast binds tighter than the division, so
it becomes `7.0 / 2`. Writing `(double) (7 / 2)` would give `3.0` instead —
the integer division would already have happened inside the parentheses.

---

## Part 10 — Compound assignment and increment

Shortcuts for updating a variable using its own value:

```java
int score = 10;
score += 5;   // same as score = score + 5   → 15
score *= 2;   // same as score = score * 2   → 30
score++;      // same as score = score + 1   → 31
```

Real output confirms: `15`, `30`, `31`.

All five arithmetic operators have a compound form: `+=`, `-=`, `*=`, `/=`,
`%=`. And `--` decreases by one, mirroring `++`.

---

## Part 11 — String concatenation

The `+` operator does **two different jobs** depending on the types. With
numbers it adds; with a String on either side it **concatenates** — glues text
together:

```java
System.out.println("Weight on Mars: " + marsWeight + " lbs");
```

The number is converted to text automatically and joined into one String.

### Order matters — this trips people up

```
"Total: " + 1 + 2   →   Total: 12
1 + 2 + " is sum"   →   3 is sum
```

Java evaluates **left to right**. In the first line, `"Total: " + 1` is
already a String, so the `2` gets glued on the end as text. In the second,
`1 + 2` is still pure numbers, so it adds to `3` before the String appears.

**Fix:** parenthesize the math you want done first — `"Total: " + (1 + 2)`.

### A blank line

```java
System.out.println();     // no argument at all — just moves to the next line
```

---

## Part 12 — The project: `GalaxyWeight`

**The task:** store your Earth weight, compute your weight on three planets,
print each one, then compute and print the average of the three.

**The physics.** Your weight is how hard a planet's gravity pulls on your
mass, so weight on another planet is your Earth weight scaled by the **ratio**
of the two gravities:

```
planetWeight = earthWeight × (planetGravity ÷ earthGravity)
```

Jupiter's gravity is 24.79 against Earth's 9.8 — a ratio of about 2.53, so you
weigh roughly two and a half times more there.

### Gravity reference table (m/s²)

| Planet | Gravity | Ratio to Earth |
|---|---|---|
| Mercury | 3.59 | 0.366 |
| Venus | 8.87 | 0.905 |
| Earth | **9.8** | 1.000 |
| Mars | 3.711 | 0.379 |
| Jupiter | 24.79 | 2.530 |
| Saturn | 11.08 | 1.131 |
| Uranus | 10.67 | 1.089 |
| Neptune | 11.15 | 1.138 |

Earth is not in the activity's table — **9.8 m/s²** is the standard value.
Some textbooks use 9.81. Check which your teacher wants; because it lives in
one variable, switching is a one-line change.

### The code

```java
public class GalaxyWeight
{
    public static void main(String[] args)
    {
        // My weight on Earth, in pounds. Change this number to your own weight.
        double earthWeight = 125;

        // Acceleration due to gravity, in m/s^2
        double earthGravity = 9.8;
        double mercuryGravity = 3.59;
        double marsGravity = 3.711;
        double jupiterGravity = 24.79;

        // Weight on another planet = Earth weight scaled by how that planet's
        // gravity compares to Earth's gravity
        double mercuryWeight = earthWeight * (mercuryGravity / earthGravity);
        double marsWeight = earthWeight * (marsGravity / earthGravity);
        double jupiterWeight = earthWeight * (jupiterGravity / earthGravity);

        // The parentheses force the three weights to be added FIRST,
        // and only then divided by 3
        double averageWeight = (mercuryWeight + marsWeight + jupiterWeight) / 3;

        System.out.println("Weight on Earth:   " + earthWeight + " lbs");
        System.out.println("Weight on Mercury: " + mercuryWeight + " lbs");
        System.out.println("Weight on Mars:    " + marsWeight + " lbs");
        System.out.println("Weight on Jupiter: " + jupiterWeight + " lbs");
        System.out.println();
        System.out.println("Average of the three planets: " + averageWeight + " lbs");
    }
}
```

**Actual output:**

```
Weight on Earth:   125.0 lbs
Weight on Mercury: 45.7908163265306 lbs
Weight on Mars:    47.33418367346938 lbs
Weight on Jupiter: 316.1989795918367 lbs

Average of the three planets: 136.44132653061223 lbs
```

---

## Part 13 — Line-by-line walkthrough

Tracking what's in memory after each statement.

### Setting up the inputs

```java
double earthWeight = 125;
```

Declares a `double` and initializes it to `125`. Note we wrote the whole
number `125`, not `125.0` — Java promotes it automatically, which is why the
output shows `125.0`. **Memory:** `earthWeight = 125.0`

```java
double earthGravity = 9.8;
double mercuryGravity = 3.59;
double marsGravity = 3.711;
double jupiterGravity = 24.79;
```

Four more doubles, one per gravity value. These are the program's constants —
naming them is what makes the formulas below readable. **Memory:** five
doubles now stored.

### Doing the math

```java
double mercuryWeight = earthWeight * (mercuryGravity / earthGravity);
```

Right side first, innermost parentheses first:

1. `mercuryGravity / earthGravity` → `3.59 / 9.8` → `0.36632653...`
2. `earthWeight * 0.36632653...` → `125 * 0.36632653...` → `45.7908163265306`
3. Store that in the new variable `mercuryWeight`

Both operands are `double`, so this is real decimal division — no integer
truncation. **Memory:** `mercuryWeight = 45.7908163265306`

The parentheses around the division are technically optional here (`*` and `/`
are the same precedence, evaluated left to right, so it'd work either way),
but they make the *ratio* idea visible at a glance. Worth keeping.

```java
double marsWeight = earthWeight * (marsGravity / earthGravity);
double jupiterWeight = earthWeight * (jupiterGravity / earthGravity);
```

Identical pattern. **Memory:** `marsWeight = 47.33418367346938`,
`jupiterWeight = 316.1989795918367`

### The average

```java
double averageWeight = (mercuryWeight + marsWeight + jupiterWeight) / 3;
```

**These parentheses are not optional.** They force all three additions to
complete before the division:

1. `45.7908... + 47.3342... + 316.1990...` → `409.3239795918367`
2. `409.3239795918367 / 3` → `136.44132653061223`

Dividing by the `int` literal `3` is safe because the left side is already a
`double` — Java promotes the `3` to `3.0`. **Memory:**
`averageWeight = 136.44132653061223`

### Printing

```java
System.out.println("Weight on Mercury: " + mercuryWeight + " lbs");
```

Left to right: `"Weight on Mercury: " + 45.79...` makes a String, then
`+ " lbs"` appends the unit. One String, printed, cursor moves down.

```java
System.out.println();
```

Empty argument list — prints nothing, just a blank line separating the
per-planet results from the average.

### How it all fits together

The program is a straight line of three phases: **store the inputs**, **run
each input through the same formula**, **combine the results**. No loops, no
decisions. Every value flows one direction, from the gravity constants at the
top to the average at the bottom. If you change `earthWeight` on line 1, all
four printed numbers change, because everything downstream was written in
terms of variables instead of hard-coded numbers.

---

## Part 14 — Debugging log: the two traps

Both were tested with real code. Both produce a program that **runs fine and
prints a wrong answer** — which is far more dangerous than a crash, because
nothing tells you something went wrong.

### Trap 1 — forgetting parentheses around the average

```java
double wrong = mercuryWeight + marsWeight + jupiterWeight / 3;   // BUG
```

Real results:

```
WITH parentheses:    136.44132653061223   ✅
WITHOUT parentheses: 198.52465986394554   ❌
```

**Cause:** `/` has higher precedence than `+`, so only `jupiterWeight` gets
divided. The expression becomes `45.79 + 47.33 + 105.40`.

**Fix:** parenthesize the sum.

**How to catch it:** estimate before you trust the output. Two of these
numbers are around 46 and one is 316, so the average has to be near
(46 + 47 + 316) / 3 ≈ 136. Getting 198 means something is wrong.

Note that the usual sanity check — *"an average must fall between the smallest
and largest value"* — does **not** catch this bug, since 198.5 does sit
between 45.8 and 316.2. That's exactly why this error survives a quick glance
and lands in submitted work.

### Trap 2 — integer division

```java
int earthWeight = 125;        // BUG: int instead of double
int earthGravity = 9;         // BUG: can't even hold 9.8
```

```
125 / 3    = 41                      ❌
125.0 / 3  = 41.666666666666664      ✅
```

**Cause:** two `int` operands produce an `int`, truncating the decimal. And an
`int` can't hold `9.8` at all — that line wouldn't even compile
(`incompatible types: possible lossy conversion from double to int`).

**Fix:** use `double` for anything that can have a fraction.

**The transferable lesson:** when a number comes out wrong but the program
runs, suspect **types** and **precedence** before you suspect your formula.

---

## Try it yourself

1. **Change the weight.** Put your real weight on line 1. Every output updates.
2. **Swap planets.** Pick three others from the table — change the gravity
   variables, the weight variables, and the labels in the `println` calls.
3. **Add a fourth planet.** You'll need a gravity variable, a weight variable,
   a `println`, *and* you must change the `/ 3` to `/ 4`. Forgetting that last
   one is a classic.
4. **Predict before you run.** Write down what you expect, then run it. Being
   wrong is how you find out what you actually believe.

Compile and run:

```sh
javac GalaxyWeight.java
java GalaxyWeight
```

Or in one step: `java GalaxyWeight.java`

---

## Common mistakes to avoid

- Using `int` for a value that has decimals
- Re-declaring a variable: `double x = 1;` then `double x = 2;`
- Forgetting parentheses around a sum you're about to divide
- Assuming `/` rounds — it truncates
- `(int)` casting expecting rounding — `19.99` becomes `19`
- Reading `=` as "equals" instead of "gets"
- Capitalization slips — `EarthWeight` ≠ `earthWeight`
- `"Total: " + 1 + 2` when you meant `"Total: " + (1 + 2)`
- Adding a planet but forgetting to update the divisor in the average

---

## Vocabulary

| Term | Definition |
|---|---|
| variable | a named location in memory that holds a value |
| data type | what kind of value a variable can hold |
| primitive type | a basic built-in type: `int`, `double`, `boolean` |
| declaration | creating a variable by stating its type and name |
| initialization | giving a variable its first value |
| assignment | storing a value into an existing variable |
| expression | any combination of values and operators producing a value |
| operand | a value an operator acts on |
| integer division | `int ÷ int`, which truncates the decimal part |
| modulus | the `%` operator, giving the remainder |
| precedence | the order operators are applied in |
| promotion | Java automatically widening an `int` to a `double` |
| casting | forcing a value to another type with `(type)` |
| truncate | to chop off the decimal part without rounding |
| concatenation | joining Strings together with `+` |

---

## Check yourself

1. What does `9 / 4` evaluate to? What about `9.0 / 4`?
2. What does `9 % 4` evaluate to?
3. What is `2 + 3 * 4 - 1`?
4. What does `(int) 8.99` give you?
5. What prints? `System.out.println("Sum: " + 3 + 4);`
6. Rewrite that so it prints `Sum: 7`.
7. `int a = 7; a += 3; a /= 2;` — what is `a`?
8. Why is `double avg = x + y + z / 3;` wrong?
9. Why won't `int g = 9.8;` compile?

<details>
<summary>Answers</summary>

1. `9 / 4` is `2` — both are `int`, so the `.25` is chopped. `9.0 / 4` is `2.25`.
2. `1` — 9 ÷ 4 is 2 remainder 1.
3. `13` — multiplication first (`3 * 4` = 12), then `2 + 12 - 1`.
4. `8` — casting truncates, it does not round.
5. `Sum: 34` — left to right, `"Sum: " + 3` becomes a String, then `4` is glued on.
6. `System.out.println("Sum: " + (3 + 4));`
7. `5` — `a` becomes `10`, then integer division `10 / 2` gives `5`.
8. Division binds tighter than addition, so only `z` gets divided. It needs `(x + y + z) / 3`.
9. An `int` can't hold a decimal. The compiler reports `possible lossy conversion from double to int`.

</details>

---

## What's next

With variables and arithmetic in place, the next step is making programs
**react** to their values — comparisons, `boolean` logic, and `if` statements.
