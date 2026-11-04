import java.time.LocalDate;

public class Bug {
    int bugId;
    String title;
    String module;
    String severity;
    String stepsToReproduce;

    String developer = "Unassigned";
    String status = "New";
    int reopenCount = 0;

    LocalDate createdDate = LocalDate.now();
}