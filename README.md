# CP-095 BugBoard: Defect Lifecycle Tracking System

BugBoard is a console-based Java application that manages software defects through their lifecycle. It lets users log bugs, assign developers, move bugs through valid statuses, track how many times a bug was reopened, and generate reports.

This project was developed as a team capstone project (Problem CP-095, Java track).

## Team

| Team Member | USN | GitHub Profile |
|---|---|---|
| Suhas | [1AT24CS218] | [@suhask625](https://github.com/suhask625) |
| Vishal | [1AT24CS256] | [@vishalsingh959147-byte](https://github.com/vishalsingh959147-byte) |

Track: Java

## How to run

Prerequisites: Java Development Kit (JDK). Check the installation:

```bash
java -version
javac -version
```

Clone the repository and go into the project folder:

```bash
git clone https://github.com/suhask625/BugBoard.git
cd BugBoard
```

Compile and run:

```bash
javac Main.java
java Main
```

## Features that work

Core features:

1. **Log a bug:** title, module, severity (Low, Medium, High or Critical) and steps to reproduce. A Bug ID is generated automatically, starting from 101.
2. **Assign a bug to a developer:** only bugs with status New or Reopened can be assigned.
3. **Move a bug through its lifecycle, allowing only valid steps.** Any other move is rejected and the allowed steps are shown.
4. **Count reopens:** every time a bug is reopened, its reopen count goes up by 1.
5. **Reports:** open bugs with their age in days, the number of open and closed bugs, bugs per module, and the module with the most bugs.

Also available: view all logged bugs.

Input validation: invalid menu choices, letters instead of numbers, empty text, unknown Bug IDs and invalid severity are all handled without crashing.

## Bug Lifecycle

```text
New
 |
 v
Assigned
 |
 v
Fixed
 |
 v
Verified
 |       \
 v        v
Closed   Reopened
           |
           v
        Assigned
```

## Technologies Used

- Java
- ArrayList
- Scanner
- Java Date and Time API (LocalDate, ChronoUnit)

## Project Structure

```text
BugBoard/
├── Main.java
└── README.md
```

`Main.java` contains the whole program, including the small `Bug` class that stores the details of one bug.

## Application Menu

```text
========== BUGBOARD ==========
1. Log a new bug
2. View all bugs
3. Assign a bug to developer
4. Update bug status
5. Generate reports
6. Exit
```

## Sample run

The menu is printed again after every action. It is shown only once here to keep this short.

```text
Welcome to BugBoard
Defect Lifecycle Tracking System

========== BUGBOARD ==========
1. Log a new bug
2. View all bugs
3. Assign a bug to developer
4. Update bug status
5. Generate reports
6. Exit
Enter your choice: 1

--- Log a New Bug ---
Enter bug title: Login button not responding
Enter bug module: Authentication
Enter bug severity (Low / Medium / High / Critical): High
Enter steps to reproduce: Click login with correct details

Bug logged successfully!
Generated Bug ID: 101

Enter your choice: 3

--- Assign Bug to Developer ---
Enter Bug ID: 101
Enter developer name: Asha

Bug assigned successfully!
Bug ID 101 is now assigned to: Asha
Status updated to: Assigned

Enter your choice: 4

--- Update Bug Lifecycle Status ---
Enter Bug ID: 101
Selected Bug   : [101] Login button not responding
Current Status : Assigned

Select new status:
1. Assigned
2. Fixed
3. Verified
4. Closed
5. Reopened
Enter choice (1-5): 3

Invalid status transition from 'Assigned' to 'Verified'!
Allowed lifecycle transitions:
  - New -> Assigned
  - Assigned -> Fixed
  - Fixed -> Verified
  - Verified -> Closed
  - Verified -> Reopened
  - Reopened -> Assigned

Enter your choice: 4
Enter Bug ID: 101
Enter choice (1-5): 2

Status updated successfully!
Bug ID 101 new status: Fixed

Enter your choice: 5

========== BUG REPORTS ==========
Total Bugs in System: 1

--- Open Bugs by Age ---
Bug ID: 101 | Title: Login button not responding | Module: Authentication | Status: Fixed | Age: 0 day(s)
Total Open Bugs: 1
Total Closed Bugs: 0

--- Bugs Grouped by Module ---
  - Authentication: 1 bug(s)

Module with highest number of bugs : Authentication
Total bugs in this module          : 1
=================================

Enter your choice: 6
Exiting BugBoard... Thank you!
```

## Learning Outcomes

- Managing objects with an ArrayList.
- Implementing bug lifecycle transitions with simple conditions.
- Handling invalid user input.
- Generating reports with loops and counters.
- Applying debugging and problem-solving techniques.

## Future Enhancements

- Save bugs to a file so data is kept between runs.
- Mark duplicate bugs and link them to the original.
- Show each developer's fix rate.
- Search and filter bugs.
- Graphical user interface.
