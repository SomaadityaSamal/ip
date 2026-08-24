import java.util.Scanner;
import java.util.ArrayList;
public class Friday {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String task = "";
        ArrayList<String> arr = new ArrayList<>();
        int counter = 0;
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
            if (task.equals("list")){
                System.out.println("____________________________________________________________\n");
                for (int i = 0 ; i < counter ; i++){
                    System.out.println(arr.get(i));
                }
                continue;
            }

            counter++;
            arr.add(counter + ". " + task);
            System.out.println("____________________________________________________________\n" +
                    "added: " + task + "\n" +
                    "____________________________________________________________");

        }
        System.out.println("____________________________________________________________\n" +
                "Bye. Hope to see you again soon!" + "\n" +
                "____________________________________________________________");
    }
}
