# Graph Report - ip  (2026-10-09)

## Corpus Check
- 49 files · ~21,237 words
- Verdict: corpus is large enough that graph structure adds value.
- Unclassified: 4 file(s) not represented in the graph (top: (none) 1, .jar 1, .properties 1)

## Summary
- 464 nodes · 1150 edges · 20 communities (14 shown, 6 thin omitted)
- Extraction: 87% EXTRACTED · 13% INFERRED · 0% AMBIGUOUS · INFERRED: 153 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `df19a459`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- Task
- org.junit.jupiter.api.Test
- CommandsTest.java
- CommandsTest
- DateTimeParser
- Test Cases
- Deadline
- run_ui_tests.py
- Features
- .load
- Event
- AGENTS.md
- 2. Test Execution Workflow
- Foodielover
- SE-EDU Git Standard
- SE-EDU Java Coding Standard (Basic + Intermediate)
- CONTRIBUTORS.md

## God Nodes (most connected - your core abstractions)
1. `Task` - 45 edges
2. `TaskList` - 43 edges
3. `Ui` - 41 edges
4. `ParserTest` - 33 edges
5. `Storage` - 32 edges
6. `Test Cases` - 29 edges
7. `CommandsTest` - 27 edges
8. `Command` - 22 edges
9. `UiTest` - 17 edges
10. `DateTimeParser` - 12 edges

## Surprising Connections (you probably didn't know these)
- `CommandsTest` --references--> `Storage`  [EXTRACTED]
  src/test/java/foodielover/command/CommandsTest.java → src/main/java/foodielover/storage/Storage.java
- `Deadline` --inherits--> `Task`  [EXTRACTED]
  src/main/java/foodielover/task/Deadline.java → src/main/java/foodielover/task/Task.java
- `Event` --inherits--> `Task`  [EXTRACTED]
  src/main/java/foodielover/task/Event.java → src/main/java/foodielover/task/Task.java
- `Todo` --inherits--> `Task`  [EXTRACTED]
  src/main/java/foodielover/task/Todo.java → src/main/java/foodielover/task/Task.java
- `CommandsTest` --references--> `TaskList`  [EXTRACTED]
  src/test/java/foodielover/command/CommandsTest.java → src/main/java/foodielover/task/TaskList.java

## Import Cycles
- None detected.

## Communities (20 total, 6 thin omitted)

### Community 0 - "Task"
Cohesion: 0.06
Nodes (15): AddCommand, Command, DateFilterCommand, DeleteCommand, ExitCommand, FindCommand, ListCommand, MarkCommand (+7 more)

### Community 1 - "org.junit.jupiter.api.Test"
Cohesion: 0.07
Nodes (4): FoodieloverTest, ParserBranchTest, ParserTest, UiTest

### Community 3 - "CommandsTest"
Cohesion: 0.11
Nodes (3): CommandsTest, TaskListConstructionTest, TaskListTest

### Community 4 - "DateTimeParser"
Cohesion: 0.10
Nodes (4): DateTimeParser, Parser, DateTimeParserNullTest, DateTimeParserTest

### Community 5 - "Test Cases"
Cohesion: 0.06
Nodes (31): TC-01: Greet and Exit, TC-02: Add Todo Task, TC-03: Add Deadline Task, TC-04: Add Event Task, TC-05: Mark Task as Done, TC-06: Unmark Task, TC-07: List Tasks, TC-08: Comprehensive End-to-End Workflow (+23 more)

### Community 6 - "Deadline"
Cohesion: 0.13
Nodes (4): Deadline, DeadlineTest, TaskTest, TodoTest

### Community 7 - "run_ui_tests.py"
Cohesion: 0.18
Nodes (7): compile_java(), main(), match_output(), normalize_text(), parse_test_plan(), run_session(), run_test()

### Community 8 - "Features"
Cohesion: 0.12
Nodes (16): Adding a Deadline task: `deadline`, Adding a ToDo task: `todo`, Adding an Event task: `event`, Automatic Data Persistence & Backup, Command Summary, Deleting a task: `delete`, Exiting the program: `bye`, Features (+8 more)

### Community 10 - "Event"
Cohesion: 0.27
Nodes (3): Event, EventDateMatchingTest, EventTest

### Community 11 - "AGENTS.md"
Cohesion: 0.20
Nodes (9): Coding Standards:, Default user context, Git Conventions:, Guidance for interacting with users, Java version:, Project context, Project-specific requirements, Student profile (+1 more)

### Community 12 - "2. Test Execution Workflow"
Cohesion: 0.22
Nodes (8): 1. Test Plan Organization (`test/ui-test-plan.md`), 2. Test Execution Workflow, 3. Test Behavior & Guarantees, Key Elements of Every Test Case:, Running Ad-Hoc Commands, Running All Test Cases, Running Filtered Test Cases, UI Testing Skill (`test-ui`)

### Community 13 - "Foodielover"
Cohesion: 0.25
Nodes (7): Building and Testing, Developer Guide, Documentation, Foodielover, Key Features, Prerequisites, Quick Start

### Community 14 - "SE-EDU Git Standard"
Cohesion: 0.33
Nodes (5): 1. Commit Message Subject Line, 2. Commit Message Body, 3. Branch Naming Conventions, Example Commit Message, SE-EDU Git Standard

### Community 15 - "SE-EDU Java Coding Standard (Basic + Intermediate)"
Cohesion: 0.33
Nodes (5): 1. Naming Conventions, 2. Layout & Formatting, 3. Statements & Declarations, 4. Comments & Javadoc, SE-EDU Java Coding Standard (Basic + Intermediate)

## Knowledge Gaps
- **70 isolated node(s):** `1. Commit Message Subject Line`, `Example Commit Message`, `3. Branch Naming Conventions`, `1. Naming Conventions`, `2. Layout & Formatting` (+65 more)
  These have ≤1 connection - possible missing edges or undocumented components. (Counts symbols only; 123 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **6 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `Task` connect `Task` to `CommandsTest.java`, `CommandsTest`, `Deadline`, `.load`, `Event`?**
  _High betweenness centrality (0.047) - this node is a cross-community bridge._
- **What connects `1. Commit Message Subject Line`, `Example Commit Message`, `3. Branch Naming Conventions` to the rest of the system?**
  _70 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `Task` be split into smaller, more focused modules?**
  _Cohesion score 0.060526315789473685 - nodes in this community are weakly interconnected._
- **Why does `TaskList` connect `Task` to `CommandsTest.java`, `CommandsTest`?**
  _High betweenness centrality (0.041) - this node is a cross-community bridge._
- **Should `org.junit.jupiter.api.Test` be split into smaller, more focused modules?**
  _Cohesion score 0.07281772953414745 - nodes in this community are weakly interconnected._
- **Why does `Ui` connect `Task` to `CommandsTest.java`?**
  _High betweenness centrality (0.037) - this node is a cross-community bridge._
- **Should `CommandsTest.java` be split into smaller, more focused modules?**
  _Cohesion score 0.08357685563997662 - nodes in this community are weakly interconnected._