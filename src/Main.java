import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    private static Group organization;
    private static final Pattern NAME_PATTERN =
            Pattern.compile("[A-Z][a-z]+ [A-Z][a-z]+$");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printMenu();
            String choice = scanner.nextLine().trim();
            if (choice.equalsIgnoreCase("q")) {
                System.out.println("Exiting application. Goodbye!");
                break;
            }
            try {
                switch (choice) {
                    case "1" -> {
                        organization = createHardcodedOrganization();
                        organization.print(0);
                    }
                    case "2" -> handleAdd(scanner);
                    case "3" -> handleRemove(scanner);
                    default -> System.out.println(
                            "Invalid choice. Please select 1, 2, 3 or Q.");
                }
            } catch (IllegalStateException | IllegalArgumentException ex) {
                System.out.println(ex.getMessage());
            }
            System.out.println();
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("Organization management system");
        System.out.println("------------------------------");
        System.out.println();
        System.out.println(
                "1. Create and print hard coded organization");
        System.out.println(
                "2. Print organization, add person to it and finally print it");
        System.out.println(
                "3. Print organization, remove person from it and finally print it");
        System.out.println("Q. Quit the application");
        System.out.println();
        System.out.print("Your choice: ");
    }

    private static void handleAdd(Scanner scanner) {
        if (organization == null)
            throw new IllegalStateException(
                    "Organization not created yet. Please choose 1 first.");
        organization.print(0);
        System.out.print("Enter group name to add to: ");
        String groupName = scanner.nextLine().trim();
        Optional<Group> opt = organization.findGroup(groupName);
        if (opt.isEmpty())
            throw new IllegalArgumentException(
                    "Group '" + groupName + "' not found.");
        System.out.print("Enter new worker name (Firstname Lastname): ");
        String workerName = scanner.nextLine().trim();
        if (!NAME_PATTERN.matcher(workerName).matches())
            throw new IllegalArgumentException(
                    "Invalid name format. Use 'Firstname Lastname'.");
        opt.get().add(new Worker(workerName));
        organization.print(0);
    }

    private static void handleRemove(Scanner scanner) {
        if (organization == null)
            throw new IllegalStateException(
                    "Organization not created yet. Please choose 1 first.");
        organization.print(0);
        System.out.print("Enter worker name to remove: ");
        String workerName = scanner.nextLine().trim();
        if (!NAME_PATTERN.matcher(workerName).matches())
            throw new IllegalArgumentException(
                    "Invalid name format. Use 'Firstname Lastname'.");
        boolean removed = organization.removeWorker(workerName);
        if (!removed)
            throw new IllegalArgumentException(
                    "Worker '" + workerName + "' not found.");
        organization.print(0);
    }

    private static Group createHardcodedOrganization() {
        Group root = new Group("Duckburg Enterprises", "Scrooge McDuck");
        Group finance = new Group("Finance", "Donald Duck");
        finance.add(new Worker("Huey Duck"));
        finance.add(new Worker("Dewey Duck"));
        finance.add(new Worker("Louie Duck"));
        Group engineering = new Group("Engineering", "Gyro Gearloose");
        engineering.add(new Worker(
                "Fenton Crackshell (Assistant Engineer)"));
        Group management = new Group("Management", "Daisy Duck");
        management.add(new Worker("Ludwig Von Drake"));
        root.add(finance);
        root.add(engineering);
        root.add(management);
        return root;
    }
}