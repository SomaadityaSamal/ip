import java.util.Scanner;
public class Friday {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String task = "";
        String banner = "_____ ____  ___ ____    _ __   __\n" +
                "|  ___|  _ \\|_ _|  _ \\  / \\\\ \\ / /\n" +
                "| |_  | |_) || || | | |/ _ \\\\ V / \n" +
                "|  _| |  _ < | || |_| / ___ \\| |  \n" +
                "|_|   |_| \\_\\___|____/_/   \\_\\_|";
        System.out.println(banner);
        System.out.println("...\n" +
                "\n" +
                "What can i do for you?\n" +
                "____________________________________________________________\n"
                );
        while (!task.equals("bye")) {
            task = scanner.nextLine();
            System.out.println("____________________________________________________________\n" +
                    task + "\n" +
                    "____________________________________________________________");

        }
        System.out.println("____________________________________________________________\n" +
                "Bye. Hope to see you again soon!" + "\n" +
                "____________________________________________________________");
    }
}
