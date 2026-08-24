import java.util.Scanner;
import java.util.ArrayList;
public class Friday {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = "";

        ArrayList<item> arr = new ArrayList<>();
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



        while (!input.equals("bye")) {
            boolean flag = true;
             input = scanner.nextLine().trim();
             String task = input;
            String[] parts = input.split(" ");
            String command = parts[0];
            if (command.equals("mark") || command.equals("unmark")) {

                int taskIndex = Integer.parseInt(parts[1]) - 1;
                flag = false;

                if (command.equals("mark")) {
                    arr.get(taskIndex).setDone();
                    System.out.println("____________________________________________________________\n" +
                            " Nice! I've marked this task as done:\n" +
                            "   " + arr.get(taskIndex) + "\n" +
                            "____________________________________________________________");
                } else {
                    arr.get(taskIndex).setunDone();
                    System.out.println("____________________________________________________________\n" +
                            " OK, I've marked this task as not done yet:\n" +
                            "   " + arr.get(taskIndex) + "\n" +
                            "____________________________________________________________");
                }
            }

            if (input.equals("list")){
                System.out.println("____________________________________________________________\n");
                for (int i = 0 ; i < arr.size() ; i++){
                    System.out.println(arr.get(i).toString());
                }
                continue;
            }



            if (flag) {
                counter++;
                arr.add(new item(task, counter));
                System.out.println("____________________________________________________________\n" +
                        "added: " + task + "\n" +
                        "____________________________________________________________");
            }
        }
        System.out.println("____________________________________________________________\n" +
                "Bye. Hope to see you again soon!" + "\n" +
                "____________________________________________________________");
    }
}
