import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class FoodiesFaveFoodCenter
{
    private static final String[] cashier1 = new String[2];
    private static final String[] cashier2 = new String[3];
    private static final String[] cashier3 = new String[5];

    private static int burgerStock = 50 ;

    public static void main(String[] args)
    {
        boolean exit = false;

        do {
            displayMenu();
            Scanner scan = new Scanner(System.in);
            String input = scan.nextLine();

            switch (input) {
                case "100", "VFQ" -> viewAllQueues();
                case "101", "VEQ" -> viewAllEmptyQueues();
                case "102", "ACQ" -> addCustomerToQueue();
                case "103", "RCQ" -> removeCustomer();
                case "104", "PCQ" -> removeServedCustomer();
                case "105", "VCS" -> viewCustomersSorted();
                case "106", "SPD" -> storeProgramDataIntoFile();
                case "107", "LPD" -> loadStoreDataFromFile();
                case "108", "STK" -> viewRemainingStock();
                case "109", "AFS" -> addBurgersToStock();
                case "999", "EXT" -> exit = true;
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }while (!exit);

    }
    private static void displayMenu () {
        System.out.println();
        System.out.println("-------------------Menu--------------------");
        System.out.println("100 or VFQ: View all Queues");
        System.out.println("101 or VEQ: View all Empty Queues");
        System.out.println("102 or ACQ: Add customer to a Queue");
        System.out.println("103 or RCQ: Remove a customer from a Queue ");
        System.out.println("104 or PCQ: Remove a served customer");
        System.out.println("105 or VCS: View Customers Sorted in alphabetical order");
        System.out.println("106 or SPD: Store Program Data into file");
        System.out.println("107 or LPD: Load Program Data from file");
        System.out.println("108 or STK: View Remaining burgers Stock");
        System.out.println("109 or AFS: Add burgers to Stock");
        System.out.println("999 or EXT: Exit the Program");
        System.out.println("--------------------------------------------");
        System.out.println("Enter your choice:");
    }

    private static void viewAllQueues()
    {
        System.out.println("**********");
        System.out.println("* Cashiers *");
        System.out.println("**********");
        StringBuilder sb = new StringBuilder();
        sb.append("X X X\n");
        sb.append("X X X\n");
        sb.append("  X X \n");
        sb.append("    X \n");
        sb.append("    X");

        System.out.println(sb);
    }

    private static void viewAllEmptyQueues (){
        System.out.println("************");
        System.out.println("* Cashiers *");
        System.out.println("************");
        String sb = (cashier1[0] == null ? "X" : "O") + " " + (cashier2[0] == null ? "X" : "O") + " " + (cashier3[0] == null ? "X" : "O") + "\n" +
                    (cashier1[1] == null ? "X" : "O") + " " + (cashier2[1] == null ? "X" : "O") + " " + (cashier3[1] == null ? "X" : "O") + "\n" +
                "  " + (cashier2[2] == null ? "X" : "O") + " " + (cashier3[2] == null ? "X" : "O") + "\n" +
                "    " + (cashier3[3] == null ? "X" : "O") + "\n" +
                "    " + (cashier3[4] == null ? "X" : "O");

        System.out.println(sb);
    }

    /* ---------------------------Customer Add part------------------------------------ */

    private static void addCustomerToQueue (){
        if (burgerStock <= 10)
            System.out.println("!!----- Warning: Low stock (" + burgerStock + " burgers left)-----!!");

        int selectedQueue = selectQueue();
        switch (selectedQueue) {
            case 1 -> addCustomersToCashier(cashier1, nameValidate());
            case 2 -> addCustomersToCashier(cashier2, nameValidate());
            case 3 -> addCustomersToCashier(cashier3, nameValidate());
            default -> System.out.println("!!--------Invalid queue selection.-----------!!");
        }
    }

    private static String nameValidate() {
        while (true) {
            Scanner scan = new Scanner(System.in);
            System.out.println("Enter customer name:");
            String customerName = scan.nextLine();

            if (customerName.matches("[a-zA-Z]+")) {
                return customerName;
            } else {
                System.out.println("Enter Valid Name");
            }
        }
    }

    private static int selectQueue (){
        Scanner scan = new Scanner(System.in);
        int selectedQueue;

        do {
            System.out.println("Select the queue to add the customer (1, 2, or 3):");
            if (scan.hasNextInt()){
                selectedQueue = scan.nextInt();
                scan.nextLine(); // Consume the newline character

                if (selectedQueue < 1 || selectedQueue > 3) {
                    System.out.println("!!------ Invalid input. Please enter correct queue you want to add. -----!!");
                }
            }else{
                scan.nextLine(); // Consume the invalid input
                System.out.println("!!-------Invalid input. Please enter a valid integer value.----!!");
                selectedQueue = 0; // Set an invalid value to continue the loop
            }
        } while (selectedQueue < 1 || selectedQueue > 3);

        return selectedQueue;
    }

    private static void addCustomersToCashier (String[] queue,String customerName){
        if (isQueueFull(queue)) {
            System.out.println("!!----------Queue is already full. Cannot add more customers.------!!");
            return;
        }
        for (int i = 0; i < queue.length; i++) {
            if (queue[i] == null) {
                queue[i] = customerName;
                burgerStock -= 5;
                System.out.println("  Customer added to the queue. ");
                return;
            }
        }
    }
    private static boolean isQueueFull(String[] queue) {
        for (String customer : queue) {
            if (customer == null)
                return false;
        }
        return true;
    }


    /*----------------------------Removing Customers Part--------------------------------------*/

    private static void removeCustomer() {
        int selectedQueue = selectQueueToRemove();
        String[] selectedQueueArray;

        switch (selectedQueue) {
            case 1 -> selectedQueueArray = cashier1;
            case 2 -> selectedQueueArray = cashier2;
            case 3 -> selectedQueueArray = cashier3;
            default -> {
                System.out.println("!!-------- Invalid queue selection. ----------!!");
                return;
            }
        }

        int location = selectLocationToRemove(selectedQueueArray.length);
        if (location == -1) {
            System.out.println("!!----------- Invalid location. Customer removal canceled. -------!!");
            return;
        }

        if (selectedQueueArray[location] == null) {
            System.out.println("!!------ No customer at the specified location.-------!!");
        } else {
            selectedQueueArray[location] = null;
            System.out.println(" Customer removed from the queue.");
    }
    }

    private static int selectQueueToRemove() {
        Scanner scan = new Scanner(System.in);
        int selectedQueue;

        do {
            System.out.println("Select the queue to remove the customer from (1, 2, or 3):");
                if (scan.hasNextInt()) {
                    selectedQueue = scan.nextInt();
                    scan.nextLine(); // Consume the newline character

                    if (selectedQueue < 1 || selectedQueue > 3) {
                        System.out.println("!!--------Invalid input. Please enter correct queue---------!!");
                    }
                }else {
                    scan.nextLine();
                    System.out.println("!!---Invalid Input. Please enter a Valid input--!!");
                    selectedQueue = 0;
                }
        } while (selectedQueue < 1 || selectedQueue > 3);

        return selectedQueue;
    }

    private static int selectLocationToRemove(int queueLength) {
        Scanner scan = new Scanner(System.in);
        int location;

        do {
            System.out.println("Enter the location to remove the customer (1 to " + (queueLength) + "):");
                if (scan.hasNextInt()) {
                    location = scan.nextInt();
                    scan.nextLine(); // Consume the newline character

                    if (location < 1 || location > queueLength) {
                        System.out.println("!!--------Invalid location! Please enter a value between 1 and " + (queueLength) + ". ---------------!!");
                    }else {
                        location--;
                    }
                }else {
                    scan.nextLine();
                    System.out.println(" Invalid Input. Please enter a valid input ");
                    location =0;
                }
        } while (location < 0 || location >= queueLength);

        return location;
    }

    /*----------------------------Served Customer Remove Part--------------------------------------*/

    private static void removeServedCustomer() {
        if (isQueueEmpty(cashier1) && isQueueEmpty(cashier2) && isQueueEmpty(cashier3)) {
            System.out.println("All queues are empty. No customer to remove.");
            return;
        }

        if (!isQueueEmpty(cashier1))
            removeFromQueue(cashier1);
        else if (!isQueueEmpty(cashier2))
            removeFromQueue(cashier2);
        else if (!isQueueEmpty(cashier3))
            removeFromQueue(cashier3);
    }

    private static boolean isQueueEmpty(String[] queue) {
        for (String values : queue) {
            if (values != null)
                return false;
        }
        return true;
    }

    private static void removeFromQueue(String[] queue) {
        for (int i = 0; i < queue.length; i++) {
            if (queue[i] != null) {
                queue[i] = null;
                System.out.println("Served customer removed from the queue.");
                return;
            }
        }
    }

    /*----------------------------View Remaining Burgers Part--------------------------------------*/

    private static void viewRemainingStock() {
        System.out.println("Remaining burgers in stock: " + burgerStock);
    }

    /*---------------------------- Add Burgers to Stock --------------------------------------*/

    private static void addBurgersToStock() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the number of burgers to add to stock:");

        int burgersToAdd = 0;
        boolean validInput = false;

        while (!validInput) {
            if (scan.hasNextInt()) {
                burgersToAdd = scan.nextInt();
                validInput = true;
            } else {
                System.out.println("Invalid input. Please enter an integer:");
                scan.next(); // Clear the invalid input from the scanner
            }
        }

        int availableSpace = 50 - burgerStock;

        if (burgersToAdd <= availableSpace) {
            burgerStock += burgersToAdd;
            System.out.println(burgersToAdd + " burgers added to stock. Total stock: " + burgerStock);
        } else {
            System.out.println("Cannot add " + burgersToAdd + " burgers to stock. Available space: " + availableSpace);
        }
    }

    /*---------------------------- Store Programme Data to the File --------------------------------------*/

    private static void storeProgramDataIntoFile (){

        try {
            FileWriter myWriter = new FileWriter("FoodCenter.txt");

            myWriter.write("First Cashier queue Details: " + getArrayValues(cashier1) + "\n");
            myWriter.write("Second Cashier queue Details: " + getArrayValues(cashier2) + "\n");
            myWriter.write("Third Cashier queue Details: " + getArrayValues(cashier3) + "\n");
            myWriter.write("Burgers in stock : " + burgerStock +  "\n");
            myWriter.close();
            System.out.println("Successfully Wrote the data to the File. ");

        } catch (IOException e){
            System.out.println("An Error Occurred!");
        }
    }

    private static String getArrayValues(String[] array) {
        StringBuilder result = new StringBuilder();
        for (String value : array) {
            if (value != null) {
                result.append(value).append(", ");
            }
        }
        // Remove the trailing comma and space
        if (result.length() > 0) {
            result.setLength(result.length() - 2);
        }
        return "" + result + "";
    }

    /*---------------------------- Load Store Program Data from file --------------------------------------*/

    private static void loadStoreDataFromFile () {
        try {
            File file = new File("FoodCenter.txt");
            Scanner fileReader = new Scanner(file);
            while (fileReader.hasNextLine()){
                String text = fileReader.nextLine();
                System.out.println(text);
            }
            fileReader.close();

        } catch (IOException e){
            System.out.println(" !!ERROR!! while reading a file. ");
        }
    }

    /*---------------------------- View Customers Sorted in alphabetical order ---------------------------------*/

    private static void viewCustomersSorted (){
        int totalLength = 0;
        for (String[] arr : new String[][] {cashier1, cashier2, cashier3}) {
            if (arr != null) {
                totalLength += arr.length;
            }
        }

        // Create a new array to store the combined elements
        String[] combinedArray = new String[totalLength];

        // Copy elements from the input arrays to the combined array
        int currentIndex = 0;
        for (String[] arr : new String[][] {cashier1, cashier2, cashier3}) {
            if (arr != null) {
                for (String element : arr) {
                    if (element != null) {
                        combinedArray[currentIndex] = element;
                        currentIndex++;
                    }
                }
            }
        }

        //---------------- Insertion Sort -------------------
        // Resize the combined array to exclude null elements
        combinedArray = Arrays.copyOf(combinedArray, currentIndex);

        for (int i = 1; i < combinedArray.length; i++) {
            String key = combinedArray[i];
            int j = i-1;

            while (j >= 0 && key.compareToIgnoreCase(combinedArray[j]) < 0){
                combinedArray[ j + 1] = combinedArray[j];
                --j;
            }
            combinedArray[j + 1] = key;
        }
        System.out.println(" Sorted Array :" + Arrays.toString(combinedArray));
    }

}