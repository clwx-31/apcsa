# Required structure for every class-day post

Day 1 (`2026-08-20-day-01-output-and-escape-sequences.md`) is the reference
implementation. Every entry follows this shape.

**Standard:** a classmate who missed class can learn the entire lesson from
the page alone. Completeness beats brevity. Never skip a concept for being
"too basic" — the basics are what people actually get wrong.

## Front matter

```yaml
---
layout: post
title: "Day NN — Topic"
date: YYYY-MM-DD
categories: [notes]
tags: [lowercase, topic, tags]
---
```

File name: `YYYY-MM-DD-day-NN-topic.md`

## Required sections, in order

1. **Intro** — two short paragraphs: what the lesson covered, and a promise
   that the page is self-contained.
2. **Lesson at a glance** — bulleted "you should be able to…" objectives, plus
   a one-line list of vocabulary introduced.
3. **What to do (setup)** — the concrete steps to follow along: commands to
   run, files to create. Only when the lesson adds new tooling.
4. **Concept parts** — numbered `## Part N — Name`, one concept each, in
   teaching order. Every concept from the lesson gets one, basics included.
   Explain *why* a rule exists, not just that it exists. Use tables for
   word-by-word breakdowns.
5. **The project** — requirements as stated, full final code, exact output.
6. **Line-by-line walkthrough** — the heart of the post. Every statement
   explained in order. Trace the runtime state (cursor position, variable
   values) statement by statement. Break tricky literals down character by
   character in a table. End with a table mapping each requirement to where it
   is satisfied, then a short "how it all fits together" paragraph.
7. **Debugging log** — every error actually hit, with symptom, cause, fix, and
   *how to read the error message*. Close with the transferable lesson. Never
   omit this; it ages better than anything else on the page.
8. **Common mistakes to avoid** — short bulleted list.
9. **Vocabulary** — two-column table of every term introduced.
10. **Check yourself** — 5–7 questions, answers inside a `<details>` block.
11. **What's next** — one or two sentences pointing at the next lesson.

## Writing rules

- Second person. Direct. No filler.
- Every code block is real code that was actually compiled and run. Verify the
  output by running it — never guess at output.
- Tables for anything with parallel structure (keyword → meaning).
- Bold the term being defined on first use.
- Horizontal rules (`---`) between major sections.
- Explain the mental model, not just the mechanics — e.g. "there is a cursor"
  makes `print` vs `println` click in a way that restating the docs does not.
