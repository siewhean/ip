# UI Test Plan

This document defines the automated UI test cases for the Foodielover chatbot.
Tests are executed using the `test-ui` skill and runner script `.agents/skills/test-ui/scripts/run_ui_tests.py`.

---

## Test Environment

- **Application Entrypoint**: `foodielover.Foodielover` (`src/main/java/foodielover/Foodielover.java`)
- **Java Version**: Java 25
- **Indentation / Formatting**: SE-EDU Java Coding Standard

---

## Test Cases

### TC-01: Greet and Exit

- **Aim**: Verify the application prints the startup banner, greeting message, and farewell upon `bye`.
- **Inputs**:

```
bye
```

- **Expected Output**:

```
____________________________________________________________
 ______              _ _      _
|  ____|            | (_)    | |
| |__ ___   ___   __| |_  ___| | _____   _____ _ __
|  __/ _ \ / _ \ / _` | |/ _ \ |/ _ \ \ / / _ \ '__|
| | | (_) | (_) | (_| | |  __/ | (_) \ V /  __/ |
|_|  \___/ \___/ \__,_|_|\___|_|\___/ \_/ \___|_|

Hello! I'm Foodielover.
What can I do for you?
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

---

### TC-02: Add Todo Task

- **Aim**: Verify that adding a `todo` task prints confirmation with `[T][ ]` and the updated task count.
- **Inputs**:

```
todo borrow book
bye
```

- **Expected Output**:

```
Got it. I've added this task:
  [T][ ] borrow book
Now you have 1 tasks in the list.
```

---

### TC-03: Add Deadline Task

- **Aim**: Verify that adding a `deadline` task prints confirmation with `[D][ ]`, description, deadline date `(by: ...)`, and updated count.
- **Inputs**:

```
deadline return book /by Sunday
bye
```

- **Expected Output**:

```
Got it. I've added this task:
  [D][ ] return book (by: Sunday)
Now you have 1 tasks in the list.
```

---

### TC-04: Add Event Task

- **Aim**: Verify that adding an `event` task prints confirmation with `[E][ ]`, description, start and end dates `(from: ... to: ...)`, and updated count.
- **Inputs**:

```
event project meeting /from Mon 2pm /to 4pm
bye
```

- **Expected Output**:

```
Got it. I've added this task:
  [E][ ] project meeting (from: Mon 2pm to: 4pm)
Now you have 1 tasks in the list.
```

---

### TC-05: Mark Task as Done

- **Aim**: Verify that marking a task by index updates its status icon to `[X]` and prints confirmation.
- **Inputs**:

```
todo read book
mark 1
bye
```

- **Expected Output**:

```
Nice! I've marked this task as done:
  [T][X] read book
```

---

### TC-06: Unmark Task

- **Aim**: Verify that unmarking a previously marked task reverts its status icon to `[ ]` and prints confirmation.
- **Inputs**:

```
todo read book
mark 1
unmark 1
bye
```

- **Expected Output**:

```
OK, I've marked this task as not done yet:
  [T][ ] read book
```

---

### TC-07: List Tasks

- **Aim**: Verify that the `list` command displays all added tasks with correct 1-based indexing, type tags, status icons, and date details.
- **Inputs**:

```
todo read book
deadline return book /by June 6th
event project meeting /from Aug 6th 2pm /to 4pm
list
bye
```

- **Expected Output**:

```
Here are the tasks in your list:
1.[T][ ] read book
2.[D][ ] return book (by: June 6th)
3.[E][ ] project meeting (from: Aug 6th 2pm to: 4pm)
```

---

### TC-08: Comprehensive End-to-End Workflow

- **Aim**: Verify a complete multi-step session involving todos, deadlines, events, marking, unmarking, and list status integrity.
- **Inputs**:

```
todo read book
deadline return book /by June 6th
event project meeting /from Aug 6th 2pm /to 4pm
todo join sports club
todo borrow book
mark 1
mark 4
list
deadline return book /by Sunday
event project meeting /from Mon 2pm /to 4pm
bye
```

- **Expected Output**:

```
Here are the tasks in your list:
1.[T][X] read book
2.[D][ ] return book (by: June 6th)
3.[E][ ] project meeting (from: Aug 6th 2pm to: 4pm)
4.[T][X] join sports club
5.[T][ ] borrow book
```

---

### TC-09: Invalid Command Arguments

- **Aim**: Verify that missing, malformed, and non-numeric command arguments produce helpful errors without terminating the application.
- **Inputs**:

```
mark
mark abc
unmark 1
todo
deadline return book
deadline return book /by
event meeting /from 2pm
event meeting /to 4pm /from 2pm
bye
```

- **Expected Output**:

```
Please enter something after 'mark'.
____________________________________________________________
____________________________________________________________
Please enter a number after 'mark'.
____________________________________________________________
____________________________________________________________
This is not a valid task number. Please enter a number from 1 to 0.
____________________________________________________________
____________________________________________________________
Please enter a description after 'todo'.
____________________________________________________________
____________________________________________________________
Please include a deadline using '/by'.
____________________________________________________________
____________________________________________________________
Please provide both a deadline description and a value after '/by'.
____________________________________________________________
____________________________________________________________
Please include an event description, '/from' time, and '/to' time.
____________________________________________________________
____________________________________________________________
Please include an event description, '/from' time, and '/to' time.
```

---

### TC-10: Unknown Commands and Deletion Interleaved with Valid Additions

- **Aim**: Verify that unrecognized command strings are rejected while a valid deletion removes the intended task without corrupting task listing.
- **Inputs**:

```
blah
list
todo submit assignment
delete 1
list
bye
```

- **Expected Output**:

```
This is not a valid input. Please try again. With the following: add, mark, unmark, todo, deadline, event, list
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [T][ ] submit assignment
Now you have 1 tasks in the list.
____________________________________________________________
____________________________________________________________
Noted. I've removed this task:
[T][ ] submit assignment
Now you have 0 tasks in the list.
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
```

---

### TC-11: Boundary and Out-of-Range Index Handling for Mark and Unmark

- **Aim**: Verify that non-positive indices (0, -1) and indices exceeding the current list size produce bounds error messages without altering existing task completion states.
- **Inputs**:

```
todo read book
todo return book
mark 0
mark -1
mark 3
mark 1
unmark 0
unmark 3
list
bye
```

- **Expected Output**:

```
This is not a valid task number. Please enter a number from 1 to 2.
____________________________________________________________
____________________________________________________________
This is not a valid task number. Please enter a number from 1 to 2.
____________________________________________________________
____________________________________________________________
This is not a valid task number. Please enter a number from 1 to 2.
____________________________________________________________
____________________________________________________________
Nice! I've marked this task as done:
  [T][X] read book
____________________________________________________________
____________________________________________________________
This is not a valid task number. Please enter a number from 1 to 2.
____________________________________________________________
____________________________________________________________
This is not a valid task number. Please enter a number from 1 to 2.
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[T][X] read book
2.[T][ ] return book
```

---

### TC-12: Malformed Deadline and Event Syntax (Missing Delimiters and Empty Fields)

- **Aim**: Verify that empty descriptions before or after delimiters (`/by`, `/from`, `/to`) are rejected and do not increment task counts.
- **Inputs**:

```
deadline /by Sunday
deadline    /by
event /from Mon /to Tue
event project meeting /from /to Tue
event project meeting /from Mon /to
list
deadline submit quiz /by tomorrow
list
bye
```

- **Expected Output**:

```
Please provide both a deadline description and a value after '/by'.
____________________________________________________________
____________________________________________________________
Please provide both a deadline description and a value after '/by'.
____________________________________________________________
____________________________________________________________
Please provide an event description and values after '/from' and '/to'.
____________________________________________________________
____________________________________________________________
Please provide an event description and values after '/from' and '/to'.
____________________________________________________________
____________________________________________________________
Please provide an event description and values after '/from' and '/to'.
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [D][ ] submit quiz (by: tomorrow)
Now you have 1 tasks in the list.
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[D][ ] submit quiz (by: tomorrow)
```

---

### TC-13: Interleaved Positive and Negative Workflow (State Resilience)

- **Aim**: Verify that interleaved invalid commands, bad index arguments, and malformed inputs do not corrupt task ordering, task counts, or completion states during an extended session.
- **Inputs**:

```
unknown_command
list
todo read book
mark 0
mark 2
mark 1
list
deadline /by Sunday
deadline return book /by Sunday
unmark -1
unmark 3
unmark 1
list
event /from Mon /to Tue
event project meeting /from Mon 2pm /to 4pm
mark 4
list
bye
```

- **Expected Output**:

```
This is not a valid input. Please try again. With the following: add, mark, unmark, todo, deadline, event, list
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [T][ ] read book
Now you have 1 tasks in the list.
____________________________________________________________
____________________________________________________________
This is not a valid task number. Please enter a number from 1 to 1.
____________________________________________________________
____________________________________________________________
This is not a valid task number. Please enter a number from 1 to 1.
____________________________________________________________
____________________________________________________________
Nice! I've marked this task as done:
  [T][X] read book
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[T][X] read book
____________________________________________________________
____________________________________________________________
Please provide both a deadline description and a value after '/by'.
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [D][ ] return book (by: Sunday)
Now you have 2 tasks in the list.
____________________________________________________________
____________________________________________________________
This is not a valid task number. Please enter a number from 1 to 2.
____________________________________________________________
____________________________________________________________
This is not a valid task number. Please enter a number from 1 to 2.
____________________________________________________________
____________________________________________________________
OK, I've marked this task as not done yet:
  [T][ ] read book
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[T][ ] read book
2.[D][ ] return book (by: Sunday)
____________________________________________________________
____________________________________________________________
Please provide an event description and values after '/from' and '/to'.
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [E][ ] project meeting (from: Mon 2pm to: 4pm)
Now you have 3 tasks in the list.
____________________________________________________________
____________________________________________________________
This is not a valid task number. Please enter a number from 1 to 3.
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[T][ ] read book
2.[D][ ] return book (by: Sunday)
3.[E][ ] project meeting (from: Mon 2pm to: 4pm)
```

---

### TC-14: Reject Command Prefix Collisions

- **Aim**: Verify that inputs beginning with, but not equal to, a command keyword are rejected without creating or modifying tasks.
- **Inputs**:

```
todoist submit assignment
deadlineplus exam /by Friday
eventual meeting /from 2pm /to 4pm
marking 1
unmarking 1
list
bye
```

- **Expected Output**:

```
This is not a valid input. Please try again. With the following: add, mark, unmark, todo, deadline, event, list
____________________________________________________________
____________________________________________________________
This is not a valid input. Please try again. With the following: add, mark, unmark, todo, deadline, event, list
____________________________________________________________
____________________________________________________________
This is not a valid input. Please try again. With the following: add, mark, unmark, todo, deadline, event, list
____________________________________________________________
____________________________________________________________
This is not a valid input. Please try again. With the following: add, mark, unmark, todo, deadline, event, list
____________________________________________________________
____________________________________________________________
This is not a valid input. Please try again. With the following: add, mark, unmark, todo, deadline, event, list
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
```

---

### TC-15: Delete Tasks and Preserve Ordering

- **Aim**: Verify that deleting tasks at different positions removes only the selected task, shifts later tasks correctly, and updates the task count.
- **Inputs**:

```
todo first task
todo second task
deadline third task /by Friday
delete 2
list
delete 1
list
delete 1
list
bye
```

- **Expected Output**:

```
Noted. I've removed this task:
[T][ ] second task
Now you have 2 tasks in the list.
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[T][ ] first task
2.[D][ ] third task (by: Friday)
____________________________________________________________
____________________________________________________________
Noted. I've removed this task:
[T][ ] first task
Now you have 1 tasks in the list.
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[D][ ] third task (by: Friday)
____________________________________________________________
____________________________________________________________
Noted. I've removed this task:
[D][ ] third task (by: Friday)
Now you have 0 tasks in the list.
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
```

---

### TC-16: Invalid Delete Arguments

- **Aim**: Verify that missing, non-numeric, and out-of-range delete arguments do not change the task list.
- **Inputs**:

```
delete
delete abc
delete 1
todo keep this task
delete 0
delete 2
delete 1
list
bye
```

- **Expected Output**:

```
Please enter something after 'delete'.
____________________________________________________________
____________________________________________________________
Please enter a number after 'delete'.
____________________________________________________________
____________________________________________________________
This is not a valid task number. Please enter a number from 1 to 0.
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [T][ ] keep this task
Now you have 1 tasks in the list.
____________________________________________________________
____________________________________________________________
This is not a valid task number. Please enter a number from 1 to 1.
____________________________________________________________
____________________________________________________________
This is not a valid task number. Please enter a number from 1 to 1.
____________________________________________________________
____________________________________________________________
Noted. I've removed this task:
[T][ ] keep this task
Now you have 0 tasks in the list.
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
```

---

### TC-17: Idempotent State Transitions and Status Toggling

- **Aim**: Verify that repeating mark or unmark commands on the same task is safe, retains the correct state, and toggling back and forth preserves status.
- **Inputs**:

```
todo write report
mark 1
mark 1
unmark 1
unmark 1
mark 1
list
bye
```

- **Expected Output**:

```
Got it. I've added this task:
  [T][ ] write report
Now you have 1 tasks in the list.
____________________________________________________________
____________________________________________________________
Nice! I've marked this task as done:
  [T][X] write report
____________________________________________________________
____________________________________________________________
Nice! I've marked this task as done:
  [T][X] write report
____________________________________________________________
____________________________________________________________
OK, I've marked this task as not done yet:
  [T][ ] write report
____________________________________________________________
____________________________________________________________
OK, I've marked this task as not done yet:
  [T][ ] write report
____________________________________________________________
____________________________________________________________
Nice! I've marked this task as done:
  [T][X] write report
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[T][X] write report
```

---

### TC-18: List Depletion Lifecycle and Clean Index Reset

- **Aim**: Verify that deleting all tasks until the list is empty properly resets bounds, rejects operations on the empty list, and resumes indexing cleanly from 1 upon adding new tasks.
- **Inputs**:

```
todo task one
deadline task two /by tomorrow
event task three /from 2pm /to 4pm
delete 2
delete 1
delete 1
delete 1
mark 1
unmark 1
list
todo fresh task
list
bye
```

- **Expected Output**:

```
Got it. I've added this task:
  [T][ ] task one
Now you have 1 tasks in the list.
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [D][ ] task two (by: tomorrow)
Now you have 2 tasks in the list.
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [E][ ] task three (from: 2pm to: 4pm)
Now you have 3 tasks in the list.
____________________________________________________________
____________________________________________________________
Noted. I've removed this task:
[D][ ] task two (by: tomorrow)
Now you have 2 tasks in the list.
____________________________________________________________
____________________________________________________________
Noted. I've removed this task:
[T][ ] task one
Now you have 1 tasks in the list.
____________________________________________________________
____________________________________________________________
Noted. I've removed this task:
[E][ ] task three (from: 2pm to: 4pm)
Now you have 0 tasks in the list.
____________________________________________________________
____________________________________________________________
This is not a valid task number. Please enter a number from 1 to 0.
____________________________________________________________
____________________________________________________________
This is not a valid task number. Please enter a number from 1 to 0.
____________________________________________________________
____________________________________________________________
This is not a valid task number. Please enter a number from 1 to 0.
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [T][ ] fresh task
Now you have 1 tasks in the list.
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[T][ ] fresh task
```

---

### TC-19: Special Characters, Punctuation, and Complex Timestamps

- **Aim**: Verify that task descriptions and parameters containing symbols, numbers, parentheses, and realistic timestamps are accepted and displayed accurately.
- **Inputs**:

```
todo CS2113 Tutorial #4 (read pages 12-30) & submit PR!
deadline project v1.0 /by 2026-10-15 23:59
event Tech Symposium (Hall 2) /from 10:00 AM /to 4:30 PM
list
bye
```

- **Expected Output**:

```
Got it. I've added this task:
  [T][ ] CS2113 Tutorial #4 (read pages 12-30) & submit PR!
Now you have 1 tasks in the list.
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [D][ ] project v1.0 (by: 2026-10-15 23:59)
Now you have 2 tasks in the list.
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [E][ ] Tech Symposium (Hall 2) (from: 10:00 AM to: 4:30 PM)
Now you have 3 tasks in the list.
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[T][ ] CS2113 Tutorial #4 (read pages 12-30) & submit PR!
2.[D][ ] project v1.0 (by: 2026-10-15 23:59)
3.[E][ ] Tech Symposium (Hall 2) (from: 10:00 AM to: 4:30 PM)
```

---

### TC-20: Numeric Boundary Extremes, Signed Integers, and Formatting Errors

- **Aim**: Verify that integer overflows, signed numbers, decimals, and alphanumeric index errors are handled gracefully without crashing.
- **Inputs**:

```
todo sample task
mark +1
mark 99999999999999999999
delete 99999999999999999999
unmark 1.5
delete 2a
mark #1
list
bye
```

- **Expected Output**:

```
Got it. I've added this task:
  [T][ ] sample task
Now you have 1 tasks in the list.
____________________________________________________________
____________________________________________________________
Nice! I've marked this task as done:
  [T][X] sample task
____________________________________________________________
____________________________________________________________
Please enter a number after 'mark'.
____________________________________________________________
____________________________________________________________
Please enter a number after 'delete'.
____________________________________________________________
____________________________________________________________
Please enter a number after 'unmark'.
____________________________________________________________
____________________________________________________________
Please enter a number after 'delete'.
____________________________________________________________
____________________________________________________________
Please enter a number after 'mark'.
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[T][X] sample task
```

---

### TC-21: Whitespace Padding within Command Arguments

- **Aim**: Verify that extra whitespace between command keywords and arguments, or around parameter delimiters, is trimmed correctly.
- **Inputs**:

```
todo    padded description
deadline   lab assignment   /by   tomorrow night
event   workshop   /from   noon   /to   midnight
list
bye
```

- **Expected Output**:

```
Got it. I've added this task:
  [T][ ] padded description
Now you have 1 tasks in the list.
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [D][ ] lab assignment (by: tomorrow night)
Now you have 2 tasks in the list.
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [E][ ] workshop (from: noon to: midnight)
Now you have 3 tasks in the list.
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[T][ ] padded description
2.[D][ ] lab assignment (by: tomorrow night)
3.[E][ ] workshop (from: noon to: midnight)
```
