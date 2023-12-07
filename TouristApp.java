
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TouristApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // using scanner to read in values entered by the user
        int choice;

        TouristGraph graph = null;

        //using a do while loop for the selection menu
        do {
            System.out.println(" ");
            System.out.println("-------------------------------------------");
            System.out.println(" Welcome to the Paris Walking tour guide!!");
            System.out.println("-------------------------------------------");
            System.out.println("Menu Selection:");
            System.out.println("1. Open and Input a graph from a file :");
            System.out.println("2. Search for a site ");
            System.out.println("3. Insert an edge");
            System.out.println("4. Search for an edge");
            System.out.println("5. Enter a site and search for sites connected to it");
            System.out.println("6. Enter a site and find the closest site to it");
            System.out.println("7. Exit");
            System.out.println(" ");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Option 1 selected: Open and input a graph from a file.");
                    // Open and input a graph from an external file - graph.txt will be used
                    //System.out.print("Enter the filename: ");
                    //String fileName = scanner.next();
                    //
                    try {
                        File myObj = new File("graph.txt");
                        Scanner myReader = new Scanner(myObj);
                        while (myReader.hasNextLine()) {
                            String data = myReader.nextLine();
                            System.out.println(data);
                        }
                        myReader.close();
                    } catch (FileNotFoundException e) {
                        System.out.println("An error occurred.");
                        e.printStackTrace();
                    }
                    //graph = importGraph(fileName);
                    break;
                case 2:
                    System.out.println("Option 2 selected: Search for a site");
                    // Search for a site and display its details
                    if (graph != null) {
                        System.out.print("Enter the site name: ");
                        String siteName = scanner.next();
                        graph.displaySiteDetails(siteName);
                    } else {
                        System.out.println("No graph loaded. Please select Option 1 to load a graph.");
                    }
                    break;
                case 3:
                    System.out.println("Option 3 selected: Insert an edge");
                    // Insert an edge
                    if (graph != null) {
                        System.out.println(" ");
                        //
                    } else {
                        System.out.println("No graph loaded. Please select Option 1 to load a graph.");
                    }
                    break;
                case 4:
                    System.out.println("Option 4 selected: Search for an edge");

                    break;
                case 5:
                    System.out.println("Option 5 selected: Display all sites connected to site entered");
                    // Enter a site and display sites connected to it
                    if (graph != null) {
                        System.out.print("Enter the site name: ");
                        String siteName = scanner.next();
                        graph.displayConnectedSites(siteName);
                    } else {
                        System.out.println("No graph loaded. Please select Option 1 to load a graph.");
                    }
                    break;
                case 6:
                    System.out.println("Option 6 selected: Display closest site to site entered");
                    // Enter a site and display the closest site to it
                    if (graph != null) {
                        System.out.print("Enter the site name: ");
                        String siteName = scanner.next();
                        /*   graph.displayClosestSite(siteName);*/
                    } else {
                        System.out.println("No graph loaded. Please select Option 1 to load a graph.");
                    }
                    break;

                case 7:
                    System.out.println("Exiting programme");
                    break;

                default:
                    System.out.println("Invalid choice, please try again");
                    break;
            }
        } while (choice != 6);

        scanner.close();

    }//end main method

    public static TouristGraph importGraph(String fileName) {
        try {
                File file = new File(fileName);
                Scanner scannerFile = new Scanner(file);

            int numSites = scannerFile.nextInt();//getting the number pf sites from the first int in the file
            scannerFile.nextLine();

            TouristGraph graph = new TouristGraph(numSites);

            for (int i = 0; i < numSites; i++) {
                String[] siteInfo = scannerFile.nextLine().split(",");
                String siteName = siteInfo[0];
                double latitude = Double.parseDouble(siteInfo[1]);
                double longitude = Double.parseDouble(siteInfo[2]);
                graph.addSite(siteName, latitude, longitude);
            }

            for (int i = 0; i < numSites; i++) {
                String[] row = scannerFile.nextLine().split(" ");
                for (int j = 0; j < numSites; j++) {
                    double distance = Double.parseDouble(row[j]);
                    graph.addEdge(i, j, distance);
                }
            }

            scannerFile.close();
            return graph;
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return null;
        }
    }


}//end class



