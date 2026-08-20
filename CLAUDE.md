# AP CSA (AP Computer Science A)

This repository is a workspace for AP Computer Science A coursework. Starting
2026-08-20.

## Purpose

Claude is used here to answer questions about **creating and debugging** Java
programs for AP CSA:

- Writing new classes, methods, and programs from an assignment prompt.
- Explaining compiler errors, runtime exceptions, and wrong output.
- Walking through code by hand (tracing) the way the exam expects.
- Reviewing student code for correctness and AP-style conventions.

## Teaching posture

This is schoolwork, so prefer explanation over hand-off:

- Show the reasoning and the fix, not just corrected code.
- Point at the exact line and name the concept behind the bug.
- Keep to the AP CSA Java subset (see below) unless asked otherwise.
- When a student is stuck, offer the next step first; give the full solution
  when they ask for it.

## AP CSA Java subset

Stay inside what the exam covers unless the user explicitly wants more:

- Primitives: `int`, `double`, `boolean`; plus `String`.
- Control flow: `if`/`else`, `while`, `for`, enhanced `for`.
- Classes, constructors, instance/static variables, methods, `this`.
- Inheritance, `super`, polymorphism, `Object` methods (`equals`, `toString`).
- 1D arrays, 2D arrays, `ArrayList<E>`.
- Recursion, and searching/sorting (linear, binary, selection, insertion,
  merge).
- `Math` methods: `abs`, `pow`, `sqrt`, `random`.

Avoid in answers unless requested: generics beyond `ArrayList`, lambdas,
streams, `var`, interfaces beyond simple examples, third-party libraries.

## Layout

Flat directory of `.java` files for now. One public class per file, file name
matching the class name.

- `HelloWorld.java` — starter program.

## Build and run

Toolchain: JDK 25 (`javac` / `java` on PATH).

```sh
javac HelloWorld.java   # compile; produces HelloWorld.class
java HelloWorld         # run
```

Single-file programs can also run directly without a separate compile step:

```sh
java HelloWorld.java
```

`.class` files are build output and should not be committed.

## Style

Use the College Board / textbook style students are graded on:

- 4-space indentation, braces on their own lines are acceptable (the existing
  `HelloWorld.java` uses that style) but be consistent within a file.
- Descriptive `camelCase` for variables and methods, `PascalCase` for classes.
- Comments explain intent, not restate the code.

## Class notes site

`docs/` is a Jekyll site published via GitHub Pages from the `main` branch,
`/docs` folder. It is **public** — classmates are an intended audience.

One entry per class day in `docs/_posts/`, named
`YYYY-MM-DD-day-NN-topic.md`.

**`docs/POST-TEMPLATE.md` defines the required structure for every entry.
Read it before writing one.** Day 1 is the reference implementation; match its
depth and shape.

Non-negotiables:

- The page must teach the whole lesson on its own, to a classmate who missed
  class. Completeness over brevity. Never skip a concept for being basic.
- A line-by-line walkthrough of the day's project, tracing runtime state
  statement by statement, is the centerpiece.
- A debugging log of errors actually hit, including how to read each error
  message.
- Compile and run every code block before publishing. Never guess at output.
- Source material is the assignment prompt plus the `.java` files from that
  day. Ask about anything not inferable from the code.

`docs/README.md` holds the working agreement splitting content (Claude) from
presentation (Codex). Respect those lanes.
