# Capstone-CP-095-BugBoard

# BugBoard – Defect Lifecycle Tracking System

BugBoard is a console-based Java application designed to manage software defects throughout their lifecycle. It allows users to log bugs, assign developers, track bug status, monitor reopened defects, and generate reports.

This project was developed as a team project to demonstrate Java programming, object-oriented concepts, and software defect management.

## Features

- **Bug Logging:** Create bug reports with title, module, severity, and reproduction steps.
- **Bug Tracking:** View all recorded bugs.
- **Developer Assignment:** Assign bugs to developers.
- **Lifecycle Management:** Track bugs through different statuses.
- **Reopen Tracking:** Record how many times a bug has been reopened.
- **Bug Reports:** Display open bugs by age and identify modules with the highest number of bugs.
- **Input Validation:** Handle invalid menu choices and Bug IDs.

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
- HashMap
- Java Date and Time API
- Scanner

## Project Structure

```text
BugBoard/
├── Main.java
├── Bug.java
└── README.md
```

## Getting Started

### Prerequisites

- Java Development Kit (JDK)
- Command Prompt, terminal, or VS Code
- Git

Check Java installation:

```bash
java -version
javac -version
```

### Installation and Execution

Clone the repository:

```bash
git clone https://github.com/suhask625/BugBoard.git
```

Navigate to the project directory:

```bash
cd BugBoard
```

Compile:

```bash
javac Main.java Bug.java
```

Run:

```bash
java Main
```

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

## Sample Bug Report

```text
----- BUG REPORT -----

Bug ID: 101
Title: Login button not responding
Module: Authentication
Severity: High
Developer: Unassigned
Status: New
Reopen Count: 0
```

## Learning Outcomes

- Understanding object-oriented programming in Java.
- Managing objects using collections.
- Implementing bug lifecycle transitions.
- Handling invalid user input.
- Generating reports using HashMap.
- Applying debugging and problem-solving techniques.

## Future Enhancements

- Database integration for persistent storage.
- User authentication and role-based access.
- Graphical user interface.
- Search and filter functionality.
- Developer performance analytics.
- Duplicate bug detection.

## Team Members

| Team Member | GitHub |
|---|---|
| Suhas | [@suhask625](https://github.com/suhask625) |
| Vishal | |

## Project Information

- **Project Name:** BugBoard – Defect Lifecycle Tracking System
- **Project Type:** Team Project
- **Language:** Java
- **Application Type:** Console-Based Application
- **Domain:** Software Engineering / Bug Tracking

---

Developed as an academic team project to explore Java programming and software defect lifecycle management.
