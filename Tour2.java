

/*|Data Structures|Walking tour project| */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;




/*option 1 -- need to just make it so that it works if the user enters "graph.txt", 
at the moment have it working so that the first thing that happens is it loads the file and processes the data

option 2 -- works as long as you put it in exactly as it is in the file as in Eiffel Tower and no spaces after the word, need to get it working for lowercase too maybe?
option 3 and 4 working for me but likewise have to be typed in the same way they are in the graph file
option 5, 6 haven't gotten there yet, the methods i had previously kept giving loads of out of bounds errors 
*/
public class Tour2 {
    private static double[][] edges; // Adjacency matrix to store edges
    private static String[] siteNames; // array to store site names
    private static double[][] coordinates; //to store latitude and longitude

    public static void main(String[] args) 
    {
        readDataFromFile(); // Initialize the data from the file

        displayMenu(); // method to display menu options
    }
    private static void displayMenu() {
        Scanner scanner = new Scanner(System.in);
    
        while (true) {
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
    
            int choice;
            try {
                System.out.print("Enter your choice: ");
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                continue; // Restart the loop to prompt for input again
            }
            
            
    

            switch (choice) 
            {
                case 1:
                    System.out.println("Option 1: Open and input a graph from an external file");
                    break;
                
                case 2:
                    System.out.println("Option 2: Search for a site ");
                    System.out.println("Enter a site name: ");
                    String siteName = scanner.nextLine().trim(); // Read the site name as a string

                    System.out.println("Entered site name: '" + siteName + "' - Length: " + siteName.length());

                    int siteIndex = findNodeIndexIgnoreCase(siteName);

                    if (siteIndex != -1)
                        {
                            System.out.println("Site found!");
                            System.out.println(siteNames[siteIndex] + " - Latitude: " + coordinates[siteIndex][0] +
                            ", Longitude: " + coordinates[siteIndex][1]);
                        } 
                    else 
                        {
                                System.out.println("The site you entered is not included in this walking tour");
                        }
                        break;
                
                case 3:
                    System.out.println("Option 3: Insert an Edge ");
                    // Enter the first site name
                    System.out.println("Enter first site name: ");
                    String siteName1 = scanner.nextLine().trim();
                    int siteIndex1 = findNodeIndexIgnoreCase(siteName1);
                
                    if (siteIndex1 == -1) 
                        {
                            System.out.println("First site not found.");
                            break;
                        }
                
                    // Enter the second site name
                    System.out.println("Enter second site name: ");
                    String siteName2 = scanner.nextLine().trim();
                    int siteIndex2 = findNodeIndexIgnoreCase(siteName2);
                
                    if (siteIndex2 == -1) 
                        {
                            System.out.println("Second site not found.");
                            break;
                        }
                
                    // Enter the distance
                    double newDistance;
                    try 
                        {
                            System.out.println("Enter the distance: ");
                            newDistance = scanner.nextDouble();
                        } 
                    catch (InputMismatchException e)
                        {
                            System.out.println("Invalid input for distance. Please enter a valid number.");
                            scanner.nextLine(); // Consume the invalid input
                            break;
                        }
                    
                    edges[siteIndex1][siteIndex2] = newDistance;
                    edges[siteIndex2][siteIndex1] = newDistance; // Assuming it's an undirected graph
                
                    System.out.println("Edge between " + siteName1 + " and " + siteName2 + " with distance " + newDistance + " inserted successfully.");
                    scanner.nextLine(); // Consume the newline character
                    break; 

                case 4:
                    System.out.println("Option 4: Search for an Edge");
                    System.out.println("Enter first site name: ");
                    String searchSite1 = scanner.nextLine(); // Read the first site name as a string
                    int searchSiteIndex1 = findNodeIndex(searchSite1);
                    // Consume the newline character left by the previous nextLine()
                    scanner.nextLine();

                    System.out.println("Enter second site name: ");
                    String searchSite2 = scanner.nextLine(); // Read the second site name as a string
                    int searchSiteIndex2 = findNodeIndex(searchSite2);

                    if (searchSiteIndex1 != -1 && searchSiteIndex2 != -1) 
                        {
                            if (edges[searchSiteIndex1][searchSiteIndex2] > 0)
                                {
                                    System.out.println("There is an edge between " + searchSite1 + " and " + searchSite2);
                                } 
                            else 
                                {
                                    System.out.println("There is no edge between " + searchSite1 + " and " + searchSite2);
                                }
                        } 
                    else
                        {
                            System.out.println("One or both of the specified sites not found.");
                        }
                    break;

                case 5:
                    System.out.println("Option 5: Enter a site name to display all connected sites");
                    System.out.println("Enter site name: ");
                    break;

                case 6:
                    System.out.println("Option 6: Enter a site + Display closest site ");
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
    
            edges = new double[numNodes][numNodes]; // Initialize the adjacency matrix
    
            // Initialize the edges array with 0 weights
            for (int i = 0; i < numNodes; i++)
                {
                    for (int j = 0; j < numNodes; j++) 
                        {
                            edges[i][j] = 0.0;
                        }
                }
    
            siteNames = new String[numNodes]; // array for keeping site names
            coordinates = new double[numNodes][2]; // array for latitude and longitude
    
            // Processing node information - name, latitude, and longitude
            for (int i = 0; i < numNodes; i++) 
                {
                    String[] nodeInfo = scanner.nextLine().split(",");
                    String nodeName = nodeInfo[0].trim(); // Trim the site name
                    double latitude = Double.parseDouble(nodeInfo[1]);
                    double longitude = Double.parseDouble(nodeInfo[2]);

                    siteNames[i] = nodeName;
                    coordinates[i][0] = latitude;
                    coordinates[i][1] = longitude;

                    // Print the raw site name without trimming
                    System.out.println("Stored site name: " + siteNames[i]);
                }
    
            // Processing edge information
            while (scanner.hasNextLine()) 
                {
                    String[] edgeData = scanner.nextLine().split(",");
                    String node1 = edgeData[0];
                    String node2 = edgeData[1];
                    double weight = Double.parseDouble(edgeData[2]);
        
                    int index1 = findNodeIndex(node1);
                    int index2 = findNodeIndex(node2);
        
                    if (index1 != -1 && index2 != -1) {
                        edges[index1][index2] = weight;
                        edges[index2][index1] = weight; // Assuming it's an undirected graph
                    }
                }
        
            // Display data to the console
            System.out.println("Successfully imported data:");
            for (int i = 0; i < numNodes; i++) 
                {
                    System.out.println(siteNames[i] + " - Latitude: " + coordinates[i][0] + ", Longitude: " + coordinates[i][1]);
                }
        
            System.out.println("\nEdges between nodes:");
            for (int i = 0; i < numNodes; i++) 
                {
                    for (int j = i + 1; j < numNodes; j++)
                        { // Iterate only upper triangular part (it's an undirected graph)
                            if (edges[i][j] != 0.0)
                                {
                                    System.out.println(siteNames[i] + " <-> " + siteNames[j] + " : " + edges[i][j]);
                                }
                        }
                }
    
            System.out.println("\nRemaining lines in the file:");
            while (scanner.hasNextLine()) 
                {
                    System.out.println(scanner.nextLine());
                }
    
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (NoSuchElementException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format in the file: " + e.getMessage());
        }
    }
   
    private static int findNodeIndexIgnoreCase(String nodeName) 
        {
            for (int i = 0; i < siteNames.length; i++)
                {
                    if (siteNames[i].trim().equalsIgnoreCase(nodeName.trim()))
                        {
                            return i;
                        }
                }
            return -1; // Node not found
        }

    private static int findNodeIndex(String nodeName) 
        {
            for (int i = 0; i < siteNames.length; i++) 
                {
                    String storedName = siteNames[i].trim();
                   // System.out.println("Comparing: '" + storedName + "' with '" + nodeName.trim() + "' - Length: " + nodeName.trim().length());
                    if (storedName.equalsIgnoreCase(nodeName.trim()))
                        {
                            return i;
                        }
                }
            return -1; // Node not found
        }

   
}






/*ignore */
/*  private static int findNodeIndex(String nodeName) {
        for (int i = 0; i < siteNames.length; i++) {
            if (siteNames[i].trim().equals(nodeName.trim())) {
                return i;
            }
        }
        return -1; // Node not found
    }*/
    
