import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TourOfParis
{
    private static double[][] edges; // Adjacency matrix to store edges
    private static String[] siteNames; // array to store site names
    private static double[][] coordinates; // array to store latitude and longitude

    public static void main(String[] args)
        {
            displayMenu(); // method to display menu options
        }

    private static void displayMenu()
        {
            Scanner scanner = new Scanner(System.in);

        while (true) 
        {
            System.out.println("");
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

            int choice = scanner.nextInt();

            switch (choice) {
                case 1://import file from graph.txt
                    System.out.println("Option 1: Open and input a graph from an external file");
                    readDataFromFile();
                    System.out.println("Imported file successfully");
                    break;

                //method to find a site and print details- print name, latitude and longitude 
                case 2: System.out.println("Option 2: Search for a site ");
                    System.out.println("Enter a site name: ");
                    String siteName = scanner.next();
                    int siteIndex = findNodeIndex(siteName);//using method below to find index of entered site name

                    if (siteIndex != -1)
                        {
                            System.out.println("Site found!");
                            System.out.println(siteNames[siteIndex] + " - Latitude: " + coordinates[siteIndex][0] 
                            + ", Longitude: " + coordinates[siteIndex][1]);
                        }
                    else 
                        {
                            System.out.println("The site you entered is not included in this walking tour");
                        }
                    break;

                //method to insert an edge- user needs to input two site names and distance/weight
                case 3: System.out.println("Option 3: Insert an Edge ");
                        System.out.println("Enter first site name: ");
                        String siteName1 = scanner.next();
                        int siteIndex1 = findNodeIndex(siteName1);

                        System.out.println("Enter second site name:  ");
                        String siteName2 = scanner.next();
                        int siteIndex2 = findNodeIndex(siteName2);

                        System.out.println("Enter the distance between the sites: ");
                        double newDistance= scanner.nextDouble();

                        if (siteIndex1 != -1 && siteIndex2 != -1) {
                            edges[siteIndex1][siteIndex2] = newDistance;
                            edges[siteIndex2][siteIndex1] = newDistance; // Assuming it's an undirected graph
                            
                            System.out.println("Edge between " + siteName1 + " and " + siteName2 + " with distance " + newDistance + " inserted successfully.");
                        } else {
                            System.out.println("One or both of the specified sites not found.");
                        }
                    break;

                //method to search for an edge- site to site
                case 4: System.out.println("Option 4: Search for an Edge");
                    System.out.println("Enter first site name: ");
                    String searchSite1 = scanner.next();
                    int searchSiteIndex1 = findNodeIndex(searchSite1);

                    System.out.println("Enter second site name:  ");
                    String searchSite2 = scanner.next();
                    int searchSiteIndex2 = findNodeIndex(searchSite2);

                    if (edges[searchSiteIndex1][searchSiteIndex2]> 0)
                        {
                            System.out.println("There is an edge between " + searchSite1 + " and " + searchSite2);
                        }
                    else 
                        {
                            System.out.println("There is no edge between "+ searchSite1 + " and " + searchSite2);
                        }
                    break;
                    
                //method to display all connected sites to site entered by user
                case 5: System.out.println("Option 5: Enter a site name to display all connected sites");
                    System.out.println("Enter site name: ");
                    String sitename = scanner.next();
                    int indexOfSite = findNodeIndex(sitename);
                  
                    break;


                case 6:System.out.println("Option 6: Enter a site + Display closest site ");
                    break;
                

                case 7:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private static void readDataFromFile() {
        try {
            File file = new File("graph.txt");
            Scanner scanner = new Scanner(file);
    
            int numNodes = Integer.parseInt(scanner.nextLine());
    
            edges = new double[numNodes][numNodes]; // adjacency matrix - 2D array for edges
           
            // Initialize the adjacency matrix with 0 weights
            for (int i = 0; i < numNodes; i++) {
                for (int j = 0; j < numNodes; j++) {
                    edges[i][j] = 0.0;
                }
            }
    
            siteNames = new String[numNodes]; // array for keeping site names
            coordinates = new double[numNodes][2]; // array for latitude and longitude
    
            // Processing node information - name, latitude, and longitude
            for (int i = 0; i < numNodes; i++) {
                String[] nodeInfo = scanner.nextLine().split(",");
                String nodeName = nodeInfo[0];
                double latitude = Double.parseDouble(nodeInfo[1]);
                double longitude = Double.parseDouble(nodeInfo[2]);
    
                siteNames[i] = nodeName;
                coordinates[i][0] = latitude;
                coordinates[i][1] = longitude;
            }
    
            // Processing edge information
            while (scanner.hasNextLine()) {
                String[] edgeData = scanner.nextLine().split(",");
                String node1 = edgeData[0];
                String node2 = edgeData[1];
                double weight = Double.parseDouble(edgeData[2]);
    
                int index1 = findNodeIndex(node1);
                int index2 = findNodeIndex(node2);
    
                if (index1 != -1 && index2 != -1) 
                {
                    edges[index1][index2] = weight;
                    edges[index2][index1] = weight; // Assuming it's an undirected graph
    
                }
            }
    
            // Display data to the console
            System.out.println("Successfully imported data:");
            for (int i = 0; i < numNodes; i++) {
                System.out.println(siteNames[i] + " - Latitude: " + coordinates[i][0] + ", Longitude: " + coordinates[i][1]);
            }
    
            System.out.println("\nEdges between nodes:");
            for (int i = 0; i < numNodes; i++) {
                for (int j = i + 1; j < numNodes; j++) { // Iterate only upper triangular part (assuming it's an undirected graph)
                    if (edges[i][j] != 0.0) {
                        System.out.println(siteNames[i] + " <-> " + siteNames[j] + " : " + edges[i][j]);
                    }
                }
            }
    
            System.out.println("\nRemaining lines in the file:");
            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
            }
    
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static int findNodeIndex(String nodeName)
    {
        for (int i = 0; i < siteNames.length; i++) {
            if (siteNames[i].equals(nodeName)) {
                return i;
            }
        }
        return -1; // Node not found
    }

    
}
