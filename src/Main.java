import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Main {

    static class Bug {
        int id;
        String title;
        String module;
        String severity;
        String steps;
        String developer = "Unassigned";
        String status = "New";
        int reopened = 0;
        LocalDate created = LocalDate.now();
    }

    static Scanner sc = new Scanner(System.in);
    static ArrayList<Bug> bugs = new ArrayList<>();
    static int nextId = 101;

    public static void main(String[] args) {
        System.out.println("Welcome to BugBoard");
        System.out.println("Defect Lifecycle Tracking System");

        int choice = 0;
        while (choice != 6) {
            System.out.println("\n========== BUGBOARD ==========");
            System.out.println("1. Log a new bug");
            System.out.println("2. View all bugs");
            System.out.println("3. Assign a bug to developer");
            System.out.println("4. Update bug status");
            System.out.println("5. Generate reports");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                choice = 0;
            }

            if (choice == 1) logBug();
            else if (choice == 2) viewAll();
            else if (choice == 3) assignBug();
            else if (choice == 4) updateStatus();
            else if (choice == 5) reports();
            else if (choice == 6) System.out.println("Exiting BugBoard... Thank you!");
            else System.out.println("Invalid choice. Please enter a number between 1 and 6.");
        }
    }

    static String askText(String question) {
        while (true) {
            System.out.print(question);
            String text = sc.nextLine().trim();
            if (!text.isEmpty()) return text;
            System.out.println("Error: Input cannot be empty. Please try again.");
        }
    }

    static int askNumber(String question) {
        while (true) {
            System.out.print(question);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid number format. Please enter digits only.");
            }
        }
    }

    static String askSeverity() {
        while (true) {
            System.out.print("Enter bug severity (Low / Medium / High / Critical): ");
            String s = sc.nextLine().trim();
            if (s.equalsIgnoreCase("Low")) return "Low";
            if (s.equalsIgnoreCase("Medium")) return "Medium";
            if (s.equalsIgnoreCase("High")) return "High";
            if (s.equalsIgnoreCase("Critical")) return "Critical";
            System.out.println("Invalid severity! Allowed values are: Low, Medium, High, Critical.");
        }
    }

    static Bug findBug(int id) {
        for (Bug b : bugs) {
            if (b.id == id) return b;
        }
        return null;
    }

    static void show(Bug b) {
        System.out.println("Bug ID             : " + b.id);
        System.out.println("Title              : " + b.title);
        System.out.println("Module             : " + b.module);
        System.out.println("Severity           : " + b.severity);
        System.out.println("Steps to Reproduce : " + b.steps);
        System.out.println("Developer          : " + b.developer);
        System.out.println("Status             : " + b.status);
        System.out.println("Reopen Count       : " + b.reopened);
        System.out.println("Created Date       : " + b.created);
        System.out.println("----------------------------------------");
    }

    static void logBug() {
        System.out.println("\n--- Log a New Bug ---");
        Bug b = new Bug();
        b.id = nextId++;
        b.title = askText("Enter bug title: ");
        b.module = askText("Enter bug module: ");
        b.severity = askSeverity();
        b.steps = askText("Enter steps to reproduce: ");
        bugs.add(b);
        System.out.println("\nBug logged successfully!");
        System.out.println("Generated Bug ID: " + b.id);
    }

    static void viewAll() {
        System.out.println("\n--- All Logged Bugs ---");
        if (bugs.isEmpty()) {
            System.out.println("No bugs found.");
            return;
        }
        System.out.println("Total bugs: " + bugs.size() + "\n");
        for (Bug b : bugs) show(b);
    }

    static void assignBug() {
        System.out.println("\n--- Assign Bug to Developer ---");
        int id = askNumber("Enter Bug ID: ");
        Bug b = findBug(id);
        if (b == null) {
            System.out.println("Error: Bug ID " + id + " not found.");
            return;
        }
        if (!b.status.equals("New") && !b.status.equals("Reopened")) {
            System.out.println("Error: Bug cannot be assigned in its current status ('" + b.status + "').");
            System.out.println("Only bugs with status 'New' or 'Reopened' can be assigned to a developer.");
            return;
        }
        b.developer = askText("Enter developer name: ");
        b.status = "Assigned";
        System.out.println("\nBug assigned successfully!");
        System.out.println("Bug ID " + b.id + " is now assigned to: " + b.developer);
        System.out.println("Status updated to: " + b.status);
    }

    static boolean validMove(String from, String to) {
        return (from.equals("New") && to.equals("Assigned"))
            || (from.equals("Assigned") && to.equals("Fixed"))
            || (from.equals("Fixed") && to.equals("Verified"))
            || (from.equals("Verified") && to.equals("Closed"))
            || (from.equals("Verified") && to.equals("Reopened"))
            || (from.equals("Reopened") && to.equals("Assigned"));
    }

    static void updateStatus() {
        System.out.println("\n--- Update Bug Lifecycle Status ---");
        int id = askNumber("Enter Bug ID: ");
        Bug b = findBug(id);
        if (b == null) {
            System.out.println("Error: Bug ID " + id + " not found.");
            return;
        }

        System.out.println("Selected Bug   : [" + b.id + "] " + b.title);
        System.out.println("Current Status : " + b.status);
        System.out.println("\nSelect new status:");
        System.out.println("1. Assigned");
        System.out.println("2. Fixed");
        System.out.println("3. Verified");
        System.out.println("4. Closed");
        System.out.println("5. Reopened");
        System.out.print("Enter choice (1-5): ");

        int pick;
        try {
            pick = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid choice. Returning to menu.");
            return;
        }

        String newStatus;
        if (pick == 1) newStatus = "Assigned";
        else if (pick == 2) newStatus = "Fixed";
        else if (pick == 3) newStatus = "Verified";
        else if (pick == 4) newStatus = "Closed";
        else if (pick == 5) newStatus = "Reopened";
        else {
            System.out.println("Invalid status choice. Please choose 1 to 5.");
            return;
        }

        if (!validMove(b.status, newStatus)) {
            System.out.println("\nInvalid status transition from '" + b.status + "' to '" + newStatus + "'!");
            System.out.println("Allowed lifecycle transitions:");
            System.out.println("  - New -> Assigned");
            System.out.println("  - Assigned -> Fixed");
            System.out.println("  - Fixed -> Verified");
            System.out.println("  - Verified -> Closed");
            System.out.println("  - Verified -> Reopened");
            System.out.println("  - Reopened -> Assigned");
            return;
        }

        if (newStatus.equals("Assigned") && b.developer.equals("Unassigned")) {
            b.developer = askText("Enter developer name to assign: ");
        }

        b.status = newStatus;

        if (newStatus.equals("Reopened")) {
            b.reopened++;
            System.out.println("Notice: Bug has been reopened! Reopen count: " + b.reopened);
        }

        System.out.println("\nStatus updated successfully!");
        System.out.println("Bug ID " + b.id + " new status: " + b.status);
    }

    static void reports() {
        if (bugs.isEmpty()) {
            System.out.println("\nNo bugs available for reports.");
            return;
        }

        System.out.println("\n========== BUG REPORTS ==========");
        System.out.println("Total Bugs in System: " + bugs.size());
        System.out.println("\n--- Open Bugs by Age ---");

        int open = 0;
        int closed = 0;
        for (Bug b : bugs) {
            if (b.status.equals("Closed")) {
                closed++;
            } else {
                open++;
                long age = ChronoUnit.DAYS.between(b.created, LocalDate.now());
                System.out.println("Bug ID: " + b.id + " | Title: " + b.title + " | Module: " + b.module
                        + " | Status: " + b.status + " | Age: " + age + " day(s)");
            }
        }

        if (open == 0) System.out.println("No open bugs found. All bugs are Closed!");
        else System.out.println("Total Open Bugs: " + open);
        System.out.println("Total Closed Bugs: " + closed);

        System.out.println("\n--- Bugs Grouped by Module ---");
        String topModule = "";
        int topCount = 0;
        for (int i = 0; i < bugs.size(); i++) {
            String module = bugs.get(i).module;

            boolean seen = false;
            for (int j = 0; j < i; j++) {
                if (bugs.get(j).module.equals(module)) seen = true;
            }
            if (seen) continue;

            int count = 0;
            for (Bug b : bugs) {
                if (b.module.equals(module)) count++;
            }
            System.out.println("  - " + module + ": " + count + " bug(s)");

            if (count > topCount) {
                topCount = count;
                topModule = module;
            }
        }

        System.out.println("\nModule with highest number of bugs : " + topModule);
        System.out.println("Total bugs in this module          : " + topCount);
        System.out.println("=================================");
    }
}
