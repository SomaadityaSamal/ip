# FRIDAY User Guide

FRIDAY is a desktop task assistant with a concise, command-based chat interface. It stores your tasks locally and can
manage todos, deadlines, events, recurring work, and reminders.

![FRIDAY desktop interface](Ui.png)

## Quick start

1. Install Java 25.
2. Download `friday.jar`.
3. Place the JAR in the folder where you want FRIDAY to keep its `data` folder.
4. Open a terminal in that folder and run:

```bash
java -jar friday.jar
```

Enter commands in the message field and press `Enter` or select the send icon. You can resize the window and scroll
through earlier messages using a mouse wheel or trackpad.

## Command summary

| Action | Command |
| --- | --- |
| Show help | `help` |
| List tasks | `list` |
| Add a todo | `todo DESCRIPTION` |
| Add a deadline | `deadline DESCRIPTION /by DATE_TIME` |
| Add an event | `event DESCRIPTION /from DATE_TIME /to DATE_TIME` |
| Mark a task complete | `mark TASK_NUMBER` |
| Mark a task incomplete | `unmark TASK_NUMBER` |
| Delete a task | `delete TASK_NUMBER` |
| Find tasks | `find KEYWORD` |
| Sort tasks | `sort` |
| Repeat a task | `repeat TASK_NUMBER FREQUENCY` |
| Show reminders | `reminders` |
| Exit FRIDAY | `bye` |

## Managing tasks

### Adding a todo

Use `todo DESCRIPTION` for a task without a date.

```text
todo read the project brief
```

### Adding a deadline

Use `deadline DESCRIPTION /by DATE_TIME`. Enter dates as `day/month/year` and times using the 24-hour `HHmm` format.

```text
deadline submit report /by 20/9/2026 1800
```

### Adding an event

Use `event DESCRIPTION /from DATE_TIME /to DATE_TIME`. The ending time must be later than the starting time.

```text
event project meeting /from 20/9/2026 1400 /to 20/9/2026 1600
```

### Listing tasks

Use `list` to display every task and its number. FRIDAY uses `[X]` for a completed task and `[ ]` for an incomplete
task.

### Updating task status

Use the task number shown by `list`.

```text
mark 2
unmark 2
```

### Deleting a task

```text
delete 2
```

Task numbers can change after deletion, so use `list` before deleting when unsure.

## Finding and organizing tasks

### Finding tasks

Use `find KEYWORD` to show tasks whose descriptions contain the keyword.

```text
find report
```

### Sorting tasks

Use `sort` to arrange dated tasks chronologically. Todos without dates are placed after dated tasks.

### Adding recurrence

Use `repeat TASK_NUMBER FREQUENCY`. Supported frequencies are `daily`, `weekly`, `biweekly`, `monthly`, and `yearly`.

```text
repeat 1 weekly
```

### Viewing reminders

Use `reminders` to show all dated tasks in chronological order. When FRIDAY starts, it also highlights tasks due within
the next two days.

## Errors and saved data

FRIDAY explains invalid commands in a red response bubble. It rejects missing descriptions, invalid task numbers,
impossible dates, repeated command markers, and events whose ending time is not after their starting time.

Tasks are saved automatically in a `data` folder beside the JAR. If no save file exists, FRIDAY starts with an empty
task list and creates the file when the first task is saved.

## Exiting

Enter `bye` to close FRIDAY gracefully. You can also close the application window normally.
