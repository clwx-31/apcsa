# Site working agreement

Two contributors, separate lanes, so we don't overwrite each other.

## Content (Claude)

- `_posts/*.md` — one entry per class day, named `YYYY-MM-DD-day-NN-topic.md`
- `index.md` — body text only
- `_config.yml` — the `title` / `description` / `exclude` keys at the top

## Presentation (Codex)

- `_layouts/`, `_includes/`, `_sass/`, `assets/`
- `_config.yml` — the `theme` and display keys below the marker comment
- Any diagrams or images

## Rules

- Content never hand-writes HTML or CSS; presentation never edits post prose.
- Post front matter (`layout`, `title`, `date`, `categories`, `tags`) is a
  shared contract — coordinate before changing the field names.
- Pull before you start. Commit only files in your own lane.

## Local preview

```sh
cd docs
bundle exec jekyll serve   # needs Ruby + `gem install bundler jekyll`
```

Not required — GitHub Pages builds on push. Preview only if you want to see
changes before they go live.
