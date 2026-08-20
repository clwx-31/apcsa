---
layout: post
title: "Day 1 — Anatomy of a Java Program, Output, and Escape Sequences"
date: 2026-08-20
categories: [notes]
tags: [setup, output, escape-sequences, debugging]
---

First day. We wrote two programs — `HelloWorld` and a happy face drawn with
text — and hit two real compiler/runtime errors along the way. This page
covers every concept those programs touched, including the parts that look
too basic to write down. Those are usually the ones that cost points later.

## 1. What every Java program is made of

Here is the entire `HelloWorld` program, and every single piece of it matters:

```java
public class HelloWorld
{
    public static void main(String[] args)
    {
        System.out.println("Hello World!");
    }
}
```

Read it from the outside in.

**`public class HelloWorld`** — the *class declaration*. In Java, all code
lives inside a class. There is no such thing as a loose statement floating in
a file. `public` means other code is allowed to see this class. `class` is the
keyword. `HelloWorld` is the name we chose.

**`{` and `}`** — *braces* mark the beginning and end of a block. Every
opening brace needs a matching closing brace. The class block holds the method;
the method block holds the statements. Miscounting braces is the single most
common beginner compile error.

**`public static void main(String[] args)`** — the *main method*. This exact
signature is where Java starts running your program. Memorize it; the exam
expects it. Word by word:

| Part | Meaning |
|---|---|
| `public` | visible from outside the class, so the JVM can call it |
| `static` | belongs to the class itself, so it runs without creating an object |
| `void` | returns nothing back to whoever called it |
| `main` | the specific name the JVM looks for to start |
| `String[] args` | an array of Strings holding command-line arguments |

Change any one of those words and the program compiles but won't run — you get
the same "could not find or load main class" family of errors we saw today.

**`System.out.println("Hello World!");`** — a *statement*. Statements end with
a **semicolon**. The semicolon is not decoration; it is how the compiler knows
one instruction ended and the next began.

## 2. The file name rule

> A `public` class must be saved in a file with **exactly** the same name,
> plus `.java`.

`public class HelloWorld` must live in `HelloWorld.java`. Capitalization
counts. This bit us today — more on that in the debugging section.

## 3. Printing output

Two methods, and the difference between them is the whole lesson:

```java
System.out.print("Hello");     // prints, cursor STAYS on the same line
System.out.println("Hello");   // prints, then MOVES to the next line
```

`println` is short for "print line." Think of it as `print` plus a newline at
the end.

This means several `print` calls build up one line together:

```java
System.out.print("A");
System.out.print("B");
System.out.println("C");
```

Output: `ABC` on a single line, and then the cursor drops to the next line.

Breaking down the full call:

- `System` — a built-in class Java gives you
- `out` — the standard output stream (your terminal window)
- `print` / `println` — the method being called on it
- `("...")` — the *argument*, the thing you want printed

## 4. String literals

Anything in double quotes is a **String literal** — a fixed piece of text.

```java
System.out.println("Hello World!");
```

The quotes are delimiters. They mark where the text starts and stops, and they
are **not** printed. That raises an obvious question: what if you want to print
an actual quotation mark? Which brings us to the real topic of the day.

## 5. Escape sequences

An **escape sequence** is a backslash followed by a character. The pair means
something different from what either character means alone. The backslash tells
the compiler "don't take the next character literally."

| Sequence | Name | What it does |
|---|---|---|
| `\n` | newline | moves output to the next line |
| `\t` | tab | jumps to the next tab stop |
| `\"` | double quote | prints a literal `"` |
| `\\` | backslash | prints a literal `\` |
| `\'` | single quote | prints a literal `'` |

**Why `\"` has to exist.** This is broken:

```java
System.out.println("She said "hello" to me");   // COMPILE ERROR
```

The compiler sees the String end at the second quote, then finds the word
`hello` sitting outside any String, and gives up. The fix escapes the inner
quotes so they count as content instead of delimiters:

```java
System.out.println("She said \"hello\" to me");
```

Output: `She said "hello" to me`

**Why `\\` has to exist.** Since backslash starts an escape sequence, printing
one literally requires escaping it. `"C:\\Users"` prints `C:\Users`.

**`\n` versus `println`.** These two lines do exactly the same thing:

```java
System.out.println("Hi");
System.out.print("Hi\n");
```

Two routes to the same result. Knowing they're equivalent is worth real points
on multiple choice questions.

## 6. Comments

```java
// everything after two slashes on this line is ignored by the compiler

/* this style
   spans multiple lines */
```

Comments are for humans. The compiler skips them entirely. They don't slow the
program down and they never appear in output.

## 7. Today's assignment — the happy face

Requirements: use `print` at least twice, `println` at least twice, and all
three escape sequences.

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

Output:

```
	 ****** 
	*      *
	* "  " *
	*      *
	*  __  *
	 ****** 
```

How each requirement is met:

- **`print` ×3** — the two half-rows and the mouth row
- **`println` ×4** — the top border, the row-finisher `"*"`, and two full rows
- **`\t`** — starts every row, indenting the whole drawing
- **`\n`** — the blank line before the face, and the line-ender inside `print`
- **`\"`** — the two eyes

The `print`/`println` pair on the second row is the part that shows you
understand the difference: `print` lays down `\t*      ` and stops mid-line,
then `println("*")` closes the row and moves down.

## 8. Compiling and running

Java is a **compiled** language. Your `.java` source is translated into a
`.class` file of bytecode, and *that* is what runs.

```sh
javac HappyFacePrgrm.java   # compile -> produces HappyFacePrgrm.class
java HappyFacePrgrm         # run (no .java, no .class -- just the class name)
```

Note the asymmetry: `javac` takes the **file name**, `java` takes the **class
name**. Mixing those up is a classic first-week mistake.

Shortcut for a single file — compiles and runs in one step, leaving no
`.class` behind:

```sh
java HappyFacePrgrm.java
```

## 9. Debugging log: two real errors

### Error 1 — public class in a wrongly named file

The code said `public class HappyFace` but the file was saved as
`HappyFacePrgrm.java`. Violates the rule from section 2, so it never compiled.

**Fix:** make the two match. Either rename the file to `HappyFace.java` or
rename the class to `HappyFacePrgrm`. We renamed the class.

### Error 2 — running stale bytecode

```
Error: Could not find or load main class HappyFacePrgrm
Caused by: java.lang.ClassNotFoundException: HappyFacePrgrm
```

**How to read this:** `ClassNotFoundException` means the `.class` file does not
exist where Java looked. It almost never means your code is wrong — it means
**the compile failed and you ran anyway**, so the real error is upstream.

In our case VS Code's build folder held only `HelloWorld.class`, from a build
20 minutes earlier. The new file had never been compiled.

**Fixes:** compile from the terminal, or reset the editor's build state
(`Cmd+Shift+P` → "Java: Clean Java Language Server Workspace").

**The transferable lesson:** when you get a runtime error, check whether the
compile actually succeeded before you touch your source code.

## Vocabulary

| Term | Definition |
|---|---|
| class | the container all Java code lives in |
| method | a named block of statements, like `main` |
| statement | one instruction, ended with a semicolon |
| String literal | fixed text in double quotes |
| escape sequence | backslash + character with special meaning |
| argument | the value passed into a method inside its parentheses |
| compile | translate `.java` source into `.class` bytecode |
| bytecode | the compiled form the JVM actually runs |
| JVM | Java Virtual Machine, the program that runs bytecode |

## Check yourself

1. What prints? `System.out.print("1"); System.out.println("2"); System.out.print("3");`
2. Write a line that outputs: `He said "go" then left`
3. Why won't `public class Dog` compile in a file named `Animal.java`?
4. Give two different ways to print `Hi` followed by a line break.
5. What does `ClassNotFoundException` usually tell you to go check?

<details>
<summary>Answers</summary>

1. `12` on one line, then `3` on the next — the `println` breaks after the `2`.
2. `System.out.println("He said \"go\" then left");`
3. A public class must be in a file matching its name exactly; it would have to be `Dog.java`.
4. `System.out.println("Hi");` or `System.out.print("Hi\n");`
5. Whether the compile actually succeeded — the `.class` file is missing.

</details>
