# UI Test Plan

This document defines the automated UI test cases for the Foodielover chatbot.
Tests are executed using the `test-ui` skill and runner script `.agents/skills/test-ui/scripts/run_ui_tests.py`.

---

## Test Environment
* **Application Entrypoint**: `Foodielover.java`
* **Java Version**: Java 25
* **Indentation / Formatting**: SE-EDU Java Coding Standard

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
