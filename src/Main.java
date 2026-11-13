import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static ArrayList<Bug> bugs = new ArrayList<>();
    static int nextBugId = 101;

    public static void main(String[] args) {

        System.out.println("Welcome to BugBoard");
        System.out.println("Defect Lifecycle Tracking System");

        int choice;

        do {
            System.out.println("\n========== BUGBOARD ==========");
            System.out.println("1. Log a new bug");
            System.out.println("2. View all bugs");
            System.out.println("3. Assign a bug to developer");
            System.out.println("4. Update bug status");
            System.out.println("5. Generate reports");
            System.out.println("6. Search bug by ID");
            System.out.println("7. View bugs by developer");
            System.out.println("8. Clear all bugs");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");

            String input = sc.nextLine();

            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                choice = 0;
            }

            switch (choice) {

                case 1:
                    logBug();
                    break;

                case 2:
                    viewAllBugs();
                    break;

                case 3:
                    assignBug();
                    break;

                case 4:
                    updateBugStatus();
                    break;

                case 5:
                    generateReports();
                    break;

                case 6:
                    searchBugById();
                    break;

                case 7:
                    viewBugsByDeveloper();
                    break;

                case 8:
                    clearAllBugs();
                    break;

                case 9:
                    System.out.println("Exiting BugBoard... Thank you!");
                    break;

                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 9.");
            }

        } while (choice != 9);

        sc.close();
    }

    // Helper: Find a bug by its ID
    static Bug findBugById(int id) {
        for (Bug bug : bugs) {
            if (bug.bugId == id) {
                return bug;
            }
        }
        return null;
    }

    // Helper: Read a non-empty string from user input
    static String readNonEmptyString(String prompt) {
        String input;
        do {
            System.out.print(prompt);
            input = sc.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Error: Input cannot be empty. Please try again.");
            }
        } while (input.isEmpty());
        return input;
    }

    // Helper: Read a valid integer from user input
    static int readInteger(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid number format. Please enter digits only.");
            }
        }
    }

    // Allowed severity values
    static final String[] SEVERITIES = {"Low", "Medium", "High", "Critical"};

    // Helper: Read and validate bug severity
    static String readSeverity() {
        while (true) {
            System.out.print("Enter bug severity (Low / Medium / High / Critical): ");
            String input = sc.nextLine().trim();

            for (String severity : SEVERITIES) {
                if (severity.equalsIgnoreCase(input)) {
                    return severity;
                }
            }
            System.out.println("Invalid severity! Allowed values are: Low, Medium, High, Critical.");
        }
    }

    // Helper: Print formatted details of a single bug
    static void printBugDetails(Bug bug) {
        System.out.println("Bug ID             : " + bug.bugId);
        System.out.println("Title              : " + bug.title);
        System.out.println("Module             : " + bug.module);
        System.out.println("Severity           : " + bug.severity);
        System.out.println("Steps to Reproduce : " + bug.stepsToReproduce);
        System.out.println("Developer          : " + bug.developer);
        System.out.println("Status             : " + bug.status);
        System.out.println("Reopen Count       : " + bug.reopenCount);
        System.out.println("Created Date       : " + bug.createdDate);
        System.out.println("----------------------------------------");
    }

    // Feature 1: Log a new bug
    static void logBug() {
        System.out.println("\n--- Log a New Bug ---");

        Bug bug = new Bug();
        bug.bugId = nextBugId++;

        bug.title = readNonEmptyString("Enter bug title: ");
        bug.module = readNonEmptyString("Enter bug module: ");
        bug.severity = readSeverity();
        bug.stepsToReproduce = readNonEmptyString("Enter steps to reproduce: ");

        bugs.add(bug);

        System.out.println("\nBug logged successfully!");
        System.out.println("Generated Bug ID: " + bug.bugId);
    }

    // Feature 2: View all bugs
    static void viewAllBugs() {
        System.out.println("\n--- All Logged Bugs ---");

        if (bugs.isEmpty()) {
            System.out.println("No bugs found.");
            return;
        }

        System.out.println("Total bugs: " + bugs.size() + "\n");
        for (Bug bug : bugs) {
            printBugDetails(bug);
        }
    }

    // Feature 3: Assign bug to developer
    static void assignBug() {
        System.out.println("\n--- Assign Bug to Developer ---");

        int id = readInteger("Enter Bug ID: ");

        Bug bug = findBugById(id);

        if (bug == null) {
            System.out.println("Error: Bug ID " + id + " not found.");
            return;
        }

        if (!bug.status.equals("New") && !bug.status.equals("Reopened")) {
            System.out.println("Error: Bug cannot be assigned in its current status ('" + bug.status + "').");
            System.out.println("Only bugs with status 'New' or 'Reopened' can be assigned to a developer.");
            return;
        }

        bug.developer = readNonEmptyString("Enter developer name: ");
        bug.status = "Assigned";

        System.out.println("\nBug assigned successfully!");
        System.out.println("Bug ID " + bug.bugId + " is now assigned to: " + bug.developer);
        System.out.println("Status updated to: " + bug.status);
    }

    // Status options shown in the "Update status" menu (menu number = index + 1)
    static final String[] STATUS_OPTIONS = {"Assigned", "Fixed", "Verified", "Closed", "Reopened"};

    // Lifecycle rule: can this bug move from its current status to newStatus?
    static boolean canMoveTo(Bug bug, String newStatus) {
        switch (bug.status) {
            case "New":      return newStatus.equals("Assigned");
            case "Assigned": return newStatus.equals("Fixed");
            case "Fixed":    return newStatus.equals("Verified");
            case "Verified": return newStatus.equals("Closed") || newStatus.equals("Reopened");
            case "Reopened": return newStatus.equals("Assigned");
            default:         return false;   // Closed bugs cannot move anywhere
        }
    }

    // Feature 4: Update bug lifecycle status
    static void updateBugStatus() {
        System.out.println("\n--- Update Bug Lifecycle Status ---");

        int id = readInteger("Enter Bug ID: ");
        Bug bug = findBugById(id);

        if (bug == null) {
            System.out.println("Error: Bug ID " + id + " not found.");
            return;
        }

        System.out.println("Selected Bug   : [" + bug.bugId + "] " + bug.title);
        System.out.println("Current Status : " + bug.status);
        System.out.println("\nSelect new status:");
        for (int i = 0; i < STATUS_OPTIONS.length; i++) {
            System.out.println((i + 1) + ". " + STATUS_OPTIONS[i]);
        }
        System.out.print("Enter choice (1-5): ");

        int statusChoice;
        try {
            statusChoice = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid choice. Returning to menu.");
            return;
        }

        if (statusChoice < 1 || statusChoice > STATUS_OPTIONS.length) {
            System.out.println("Invalid status choice. Please choose 1 to 5.");
            return;
        }
        String newStatus = STATUS_OPTIONS[statusChoice - 1];

        // Check the lifecycle rules
        if (!canMoveTo(bug, newStatus)) {
            System.out.println("\nInvalid status transition from '" + bug.status + "' to '" + newStatus + "'!");
            System.out.println("Allowed lifecycle transitions:");
            System.out.println("  - New -> Assigned");
            System.out.println("  - Assigned -> Fixed");
            System.out.println("  - Fixed -> Verified");
            System.out.println("  - Verified -> Closed");
            System.out.println("  - Verified -> Reopened");
            System.out.println("  - Reopened -> Assigned");
            return;
        }

        // Moving to Assigned with no developer yet: ask for one
        if (newStatus.equals("Assigned") && bug.developer.equals("Unassigned")) {
            bug.developer = readNonEmptyString("Enter developer name to assign: ");
        }

        bug.status = newStatus;

        if (newStatus.equals("Reopened")) {
            bug.reopenCount++;
            System.out.println("Notice: Bug has been reopened! Reopen count: " + bug.reopenCount);
        }

        System.out.println("\nStatus updated successfully!");
        System.out.println("Bug ID " + bug.bugId + " new status: " + bug.status);
    }

    // Feature 5: Generate reports
    static void generateReports() {

        if (bugs.isEmpty()) {
            System.out.println("\nNo bugs available for reports.");
            return;
        }

        System.out.println("\n========== BUG REPORTS ==========");
        System.out.println("Total Bugs in System: " + bugs.size());

        // 1. Open Bugs by Age
        System.out.println("\n--- Open Bugs by Age ---");

        int openBugsCount = 0;
        int closedBugsCount = 0;

        for (Bug bug : bugs) {

            if (!bug.status.equals("Closed")) {
                openBugsCount++;
                long age = ChronoUnit.DAYS.between(
                    bug.createdDate,
                    LocalDate.now()
                );

                System.out.println(
                    "Bug ID: " + bug.bugId +
                    " | Title: " + bug.title +
                    " | Module: " + bug.module +
                    " | Status: " + bug.status +
                    " | Age: " + age + " day(s)"
                );
            } else {
                closedBugsCount++;
            }
        }

        if (openBugsCount == 0) {
            System.out.println("No open bugs found. All bugs are Closed!");
        } else {
            System.out.println("Total Open Bugs: " + openBugsCount);
        }
        System.out.println("Total Closed Bugs: " + closedBugsCount);

        // 2. Bugs grouped by Module using HashMap
        System.out.println("\n--- Bugs Grouped by Module ---");
        HashMap<String, Integer> moduleCount = new HashMap<>();

        for (Bug bug : bugs) {
            moduleCount.put(bug.module, moduleCount.getOrDefault(bug.module, 0) + 1);
        }

        String maxModule = "";
        int maxCount = 0;

        for (String module : moduleCount.keySet()) {
            int count = moduleCount.get(module);
            System.out.println("  - " + module + ": " + count + " bug(s)");

            if (count > maxCount) {
                maxCount = count;
                maxModule = module;
            }
        }

        System.out.println("\nModule with highest number of bugs : " + maxModule);
        System.out.println("Total bugs in this module          : " + maxCount);
        System.out.println("=================================");
    }

    // Additional Improvement 1: Search Bug by ID
    static void searchBugById() {
        System.out.println("\n--- Search Bug by ID ---");
        int id = readInteger("Enter Bug ID to search: ");

        Bug bug = findBugById(id);

        if (bug == null) {
            System.out.println("Error: No bug found with ID " + id + ".");
        } else {
            System.out.println("\nBug Found:");
            System.out.println("----------------------------------------");
            printBugDetails(bug);
        }
    }

    // Additional Improvement 2: View Bugs by Developer
    static void viewBugsByDeveloper() {
        System.out.println("\n--- View Bugs by Developer ---");
        String devName = readNonEmptyString("Enter developer name: ");

        boolean found = false;
        System.out.println("\nBugs assigned to '" + devName + "':");
        System.out.println("----------------------------------------");

        for (Bug bug : bugs) {
            if (bug.developer.equalsIgnoreCase(devName)) {
                printBugDetails(bug);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No bugs found assigned to developer: " + devName);
        }
    }

    // Additional Improvement 3: Clear all bugs
    static void clearAllBugs() {
        System.out.println("\n--- Clear All Bugs ---");
        if (bugs.isEmpty()) {
            System.out.println("The bug list is already empty.");
            return;
        }

        System.out.print("Are you sure you want to delete all " + bugs.size() + " bug(s)? (yes/no): ");
        String confirm = sc.nextLine().trim();

        if (confirm.equalsIgnoreCase("yes") || confirm.equalsIgnoreCase("y")) {
            bugs.clear();
            nextBugId = 101;
            System.out.println("All bugs have been successfully cleared. Bug ID counter reset to 101.");
        } else {
            System.out.println("Operation cancelled. No bugs were deleted.");
        }
    }
}
