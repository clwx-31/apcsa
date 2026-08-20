---
layout: post
title: "Day 1 — Your First Java Programs: Output and Escape Sequences"
date: 2026-08-20
categories: [notes]
tags: [setup, output, escape-sequences, strings, debugging]
---

Everything from day one. We set up the tools, learned how a Java program is
built, learned the two ways to print, and used escape sequences to draw a
happy face out of text.

This covers every concept the lesson touched — including the basics — and
walks through the final project line by line. If you missed class, you can
catch up entirely from this page.

---

## Lesson at a glance

By the end you should be able to:

- Explain every word in `public class` and `public static void main(String[] args)`
- Save, compile, and run a Java program from scratch
- Choose correctly between `System.out.print` and `System.out.println`
- Use `\n`, `\t`, and `\"` inside a String
- Read a compiler error and a `ClassNotFoundException` and know where to look

**Vocabulary introduced:** class, method, statement, String literal, escape
sequence, argument, compile, bytecode, JVM.

---

## Part 1 — What to do (setup)

You need two things: somewhere to type code, and a JDK to run it.

**1. Check you have Java.** Open a terminal and run:

```sh
javac -version
java -version
```

Both should print a version number. If either says "command not found," you
need to install a JDK (we're on JDK 25) before anything else works.

**2. Make a file.** The name matters enormously — see Part 3. For our
assignment:

```sh
touch HappyFacePrgrm.java
```

**3. Compile it, then run it:**

```sh
javac HappyFacePrgrm.java   # produces HappyFacePrgrm.class
java HappyFacePrgrm         # runs it
```

That's the whole loop: **edit → compile → run**. You'll do it a few thousand
times this year.

---

## Part 2 — The anatomy of a Java program

Here is the smallest complete Java program. Every character is doing a job:

```java
public class HelloWorld
{
    public static void main(String[] args)
    {
        System.out.println("Hello World!");
    }
}
```

Read it from the outside in. It's three nested layers: a **class** holds a
**method**, which holds **statements**.

### Layer 1 — the class declaration

```java
public class HelloWorld
```

In Java, **all code lives inside a class**. There is no such thing as a loose
statement floating in a file — unlike Python, you can't just write a print and
run it.

| Word | Meaning |
|---|---|
| `public` | other code is allowed to see this class |
| `class` | the keyword announcing a class definition |
| `HelloWorld` | the name we chose (must match the file name) |

### Layer 2 — the main method

```java
public static void main(String[] args)
```

This is the **entry point**. When you run a program, the JVM hunts for this
exact signature and starts there. Memorize it word for word — the exam asks.

| Word | Meaning | What breaks without it |
|---|---|---|
| `public` | visible from outside the class | JVM can't reach it |
| `static` | belongs to the class, not to an object | JVM would need to build an object first, and it won't |
| `void` | hands nothing back when finished | JVM isn't expecting a return value |
| `main` | the specific name the JVM looks for | it looks for `main` and nothing else |
| `String[] args` | an array of Strings holding command-line arguments | signature no longer matches |

Change any one of those and the class still *compiles* — but it won't *run*.
That's a confusing failure mode, so it's worth knowing now.

### Layer 3 — statements

```java
System.out.println("Hello World!");
```

A **statement** is one instruction. Statements end in a **semicolon**. The
semicolon isn't decoration — it's how the compiler knows where one instruction
stops and the next begins.

### Braces

`{` and `}` mark the start and end of a **block**. Every `{` needs a matching
`}`. Here the class block holds the method, and the method block holds the
statements. Miscounting braces is the #1 beginner compile error, which is why
indenting consistently matters — it makes mismatches visible.

---

## Part 3 — The file name rule

> **A `public` class must be saved in a file with exactly the same name, plus `.java`.**

`public class HelloWorld` → must be `HelloWorld.java`. Capitalization counts.
`helloworld.java` will not do.

This rule bit us today, and the error message is genuinely helpful when it
happens:

```
class HappyFace is public, should be declared in a file named HappyFace.java
```

Two valid fixes, and it doesn't matter which you pick as long as the two sides
match: rename the file, or rename the class.

---

## Part 4 — Printing

Two methods. The difference between them is the heart of this lesson.

```java
System.out.print("Hello");     // prints; cursor STAYS on this line
System.out.println("Hello");   // prints; cursor MOVES to the next line
```

`println` = "print line." It's `print` plus a newline on the end.

The mental model that makes this click: **there is a cursor**, an invisible
"you are here" marker. Printing puts characters at the cursor and pushes it
right. `println` additionally drops it to the start of the next line.

So consecutive `print` calls build a single line:

```java
System.out.print("A");
System.out.print("B");
System.out.println("C");
```

Output — one line reading `ABC`, then the cursor moves down.

### What the pieces mean

`System.out.println("Hi")` breaks into four parts:

| Piece | What it is |
|---|---|
| `System` | a built-in class Java gives you for free |
| `out` | the standard output stream — your terminal window |
| `println` | the **method** being called on `out` |
| `("Hi")` | the **argument** — the value handed to the method |

---

## Part 5 — String literals

Text in double quotes is a **String literal** — a fixed piece of text.

```java
System.out.println("Hello World!");
```

The quotes are **delimiters**: they mark where the text starts and stops, and
they are not themselves printed. Which raises the question that drives the
rest of the lesson — *what if you want to print an actual quotation mark?*

---

## Part 6 — Escape sequences

An **escape sequence** is a backslash plus one character. The pair means
something neither character means alone. The backslash tells the compiler:
*don't read the next character literally.*

| Sequence | Name | Effect |
|---|---|---|
| `\n` | newline | output jumps to the next line |
| `\t` | tab | output jumps to the next tab stop |
| `\"` | double quote | prints a literal `"` |
| `\\` | backslash | prints a literal `\` |
| `\'` | single quote | prints a literal `'` |

The first three are the ones this assignment requires.

### Why `\"` must exist

This does not compile:

```java
System.out.println("She said "hello" to me");   // ERROR
```

The compiler reads left to right. It opens a String at the first `"`, and
closes it at the second — so the String is `"She said "`. Then it finds the
bare word `hello` sitting outside any String, which is meaningless, and gives
up.

Escaping the inner quotes marks them as *content* rather than *delimiter*:

```java
System.out.println("She said \"hello\" to me");
```

Output: `She said "hello" to me`

### Why `\\` must exist

Backslash is now a special character — it starts escape sequences. So printing
a real one requires escaping it. `"C:\\Users"` prints `C:\Users`.

### `\n` and `println` are two routes to the same place

These are exactly equivalent:

```java
System.out.println("Hi");
System.out.print("Hi\n");
```

Both print `Hi` and move to the next line. Recognizing that equivalence is
worth real points on multiple-choice questions.

### Tabs are not spaces

`\t` doesn't insert a fixed number of spaces — it jumps to the next **tab
stop** (typically every 8 columns). Which is precisely why it's good for
lining up columns, and unreliable for fine-grained art.

---

## Part 7 — Comments

```java
// everything after two slashes, to the end of the line, is ignored

/* this style spans
   multiple lines */
```

Comments are written for humans. The compiler discards them completely — they
never reach the `.class` file, never slow the program down, and never appear
in output.

---

## Part 8 — The project: a happy face

**Requirements:** use `System.out.print` at least twice, `System.out.println`
at least twice, and all three escape sequences.

```java
public class HappyFacePrgrm
{
    public static void main(String[] args)
    {
        // \n makes a blank line, \t pushes the face over one tab stop
        System.out.println("\n\t ****** ");

        // print() leaves the cursor on the same line, so println() finishes the row
        System.out.print("\t*      ");
        System.out.println("*");

        // \" prints a real quotation mark -- these are the eyes
        System.out.print("\t* \"  \" *\n");
        System.out.println("\t*      *");

        System.out.print("\t*  __  *\n");
        System.out.println("\t ****** \n");
    }
}
```

**Output:**

```
	 ****** 
	*      *
	* "  " *
	*      *
	*  __  *
	 ****** 
```

---

## Part 9 — Line-by-line walkthrough

This is the part worth reading twice. We'll track the **cursor** after every
statement, because that's what `print` vs `println` is really about.

### Statement 1

```java
System.out.println("\n\t ****** ");
```

The String contains, in order: `\n`, `\t`, a space, six `*`, a space. So:

1. `\n` — move to a new line, creating a **blank line** above the face
2. `\t` — indent one tab stop
3. ` ****** ` — draw the top border
4. …and because it's `print**ln**`, finish by moving to the next line

**Cursor after:** start of a fresh line.

### Statements 2 and 3 — the pair that proves you understand

```java
System.out.print("\t*      ");   // tab, a star, six spaces
System.out.println("*");         // one star, then end the line
```

Statement 2 uses `print`, so after it runs the cursor is **still sitting on
that row**, immediately after the sixth space. The row is unfinished.

Statement 3 then drops a `*` right where the cursor waits, completing
`*      *`, and — being `println` — ends the row.

Two statements, one line of output. This is the clearest demonstration of the
difference, which is exactly why the assignment asks for both.

### Statement 4 — the eyes

```java
System.out.print("\t* \"  \" *\n");
```

Read the String literal character by character. This is the trickiest line in
the program:

| In the source | Prints as | Why |
|---|---|---|
| `\t` | *(tab)* | escape sequence — indent |
| `*` | `*` | ordinary character |
| *(space)* | *(space)* | ordinary |
| `\"` | `"` | escape sequence — **left eye** |
| *(2 spaces)* | *(2 spaces)* | the nose gap |
| `\"` | `"` | escape sequence — **right eye** |
| *(space)* | *(space)* | ordinary |
| `*` | `*` | ordinary |
| `\n` | *(newline)* | escape sequence — ends the row |

Result: `	* "  " *`

Note this uses `print`, not `println` — but the `\n` at the end does the same
job. That's the equivalence from Part 6, used deliberately.

### Statement 5

```java
System.out.println("\t*      *");
```

A plain row: tab, star, six spaces, star, newline from the `println`.

### Statement 6 — the mouth

```java
System.out.print("\t*  __  *\n");
```

Two underscores make a closed, straight mouth. Again `print` + `\n`.

### Statement 7

```java
System.out.println("\t ****** \n");
```

Bottom border, then **two** line breaks — one from the `\n` inside the String,
one from the `println` itself. That leaves a blank line below the face,
mirroring the blank line statement 1 put above it.

### How the requirements are satisfied

| Requirement | Where |
|---|---|
| `print` ≥ 2 | statements 2, 4, 6 — **three uses** |
| `println` ≥ 2 | statements 1, 3, 5, 7 — **four uses** |
| `\n` | statement 1 (blank line), and ending statements 4, 6, 7 |
| `\t` | every statement — indents the whole drawing |
| `\"` | statement 4 — both eyes |

### How it all fits together

The program is **seven statements executed strictly top to bottom**. No loops,
no decisions, no jumping around — each statement adds characters at the
cursor, and the drawing accumulates one row at a time. Every row is exactly 8
characters wide, which is the only reason the borders line up. Change one
space in the middle of a row and the whole face goes crooked.

---

## Part 10 — Debugging log: two real errors

Both of these happened for real while writing this program.

### Error 1 — public class in a wrongly named file

**Symptom:** the code said `public class HappyFace`, but the file was saved as
`HappyFacePrgrm.java`.

**Cause:** violates the rule in Part 3, so it never compiled.

**Fix:** make the names match. We renamed the class to `HappyFacePrgrm`.

### Error 2 — running stale bytecode

```
Error: Could not find or load main class HappyFacePrgrm
Caused by: java.lang.ClassNotFoundException: HappyFacePrgrm
```

**How to read this.** `ClassNotFoundException` means *the `.class` file isn't
where Java looked*. It almost never means your source code is wrong. It
usually means **the compile failed and you ran anyway** — so the real error is
further up your screen.

**Cause here:** the editor's build folder still held only `HelloWorld.class`
from twenty minutes earlier. Our new file had never been compiled at all.

**Fix:** compile from the terminal, or reset the editor's build state
(in VS Code: `Cmd+Shift+P` → "Java: Clean Java Language Server Workspace").

**The transferable lesson:** when a program fails at *run* time, first confirm
it actually *compiled*. Scroll up. Check the Problems tab. Don't start
rewriting working code.

---

## Common mistakes to avoid

- Forgetting the **semicolon** at the end of a statement
- **Mismatched braces** — count your `{` and `}`
- Writing `Println` or `PrintLn` — Java is **case-sensitive**, it's `println`
- File name not matching the public class name
- Using `"` inside a String without escaping it as `\"`
- Running `java HappyFacePrgrm.class` — no extension, just `java HappyFacePrgrm`
- Assuming `\t` equals a set number of spaces

---

## Vocabulary

| Term | Definition |
|---|---|
| class | the container all Java code lives in |
| method | a named block of statements, such as `main` |
| statement | a single instruction, ended with a semicolon |
| block | code grouped between `{` and `}` |
| String literal | fixed text written in double quotes |
| escape sequence | a backslash plus a character, with special meaning |
| delimiter | a character marking where something starts or ends |
| argument | the value passed to a method inside its parentheses |
| compile | translate `.java` source into `.class` bytecode |
| bytecode | the compiled form the JVM actually executes |
| JVM | Java Virtual Machine — the program that runs bytecode |
| entry point | where execution begins; in Java, `main` |

---

## Check yourself

1. What exactly does this print?
   `System.out.print("1"); System.out.println("2"); System.out.print("3");`
2. Write one line that outputs: `He said "go" then left`
3. Why won't `public class Dog` compile in a file named `Animal.java`?
4. Give two different ways to print `Hi` followed by a line break.
5. What does `ClassNotFoundException` usually tell you to go check?
6. What's wrong with `System.out.println("C:\Users");`?
7. In the happy face, why do statements 2 and 3 produce only **one** line?

<details>
<summary>Answers</summary>

1. `12` on one line, then `3` on the next. The `println` breaks the line after the `2`, so the `3` lands below.
2. `System.out.println("He said \"go\" then left");`
3. A public class must live in a file matching its name exactly — it would have to be `Dog.java`.
4. `System.out.println("Hi");` or `System.out.print("Hi\n");`
5. Whether the compile actually succeeded. The `.class` file is missing, so the real error is upstream.
6. `\U` isn't a valid escape sequence — the compiler rejects it. You need `"C:\\Users"`.
7. Statement 2 uses `print`, which leaves the cursor mid-row, so statement 3's `*` lands on that same row before `println` ends it.

</details>

---

## What's next

Now that output works, the next step is storing values instead of hard-coding
them — **variables** and the primitive types `int`, `double`, and `boolean`.
