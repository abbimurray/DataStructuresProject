

public class TouristGraph {
    private int numSites;
    private String[] siteNames; //array to store site names
    private double[][] adjacencyMatrix;
    private double[] latitudes;//array to store latitude values of sites
    private double[] longitudes;//array to store longitude values of sites

    //constructor method
    public TouristGraph(int numSites) {
        this.numSites = numSites;
        this.siteNames = new String[numSites];
        this.adjacencyMatrix = new double[numSites][numSites];
        this.latitudes = new double[numSites];
        this.longitudes = new double[numSites];
    }


    //add site method
    public void addSite(String siteName, double latitude, double longitude) {
        int index = findEmptyIndex(siteNames);
        if (index != -1) {
            siteNames[index] = siteName;
            latitudes[index] = latitude;
            longitudes[index] = longitude;
        }
        else {
            System.out.println("Cannot add site. Graph is full.");
        }
    }

    //option 1 method is in tourist app



    //option 2 in menu
    //method to display details of site entered by user
    public void displaySiteDetails(String siteName) {
        int siteIndex = findSiteIndex(siteName);
        if (siteIndex != -1) {
            String name = siteNames[siteIndex];
            double latitude = latitudes[siteIndex];
            double longitude = longitudes[siteIndex];

            System.out.println("Site Name: " + name);
            System.out.println("Latitude: " + latitude);
            System.out.println("Longitude: " + longitude);
        } else {
            System.out.println("Site not found in the graph.");
        }
    }

    //option3 in menu
    //method to add edge -using index of sites which are stored in adjacency matrix
    public void addEdge(int siteIndex1, int siteIndex2, double distance) {
        adjacencyMatrix[siteIndex1][siteIndex2] = distance;
        adjacencyMatrix[siteIndex2][siteIndex1] = distance;
    }


    //option 4 in menu
    //method to search for an edge
    public boolean searchEdge(int siteIndex1, int siteIndex2, double distance){
        return adjacencyMatrix[siteIndex1][siteIndex2] > 0;//edge found
    }


    //option 5 in menu
    //method to display all connected sites
    public void displayConnectedSites(String siteName) {
        int siteIndex = findSiteIndex(siteName);
        if (siteIndex != -1) {
            System.out.println("Sites connected to " + siteName + ":");
            for (int i = 0; i < numSites; i++) {
                if (adjacencyMatrix[siteIndex][i] != 0) {
                    System.out.println(siteNames[i]);
                }
            }
        } else {
            System.out.println("Site not found in the graph.");
        }
    }


    //option 6 in menu
    //method to find the closest site to a site user enters
 

    // other methods used to make above methods clearer to follow
    private int findEmptyIndex(String[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                return i;
            }
        }
        return -1;
    }


    //method to find site index
    //need this method as it is used in multiple methods above
    private int findSiteIndex(String siteName) {
        for (int i = 0; i < numSites; i++) {
            if (siteNames[i].equalsIgnoreCase(siteName)) {
                return i;
            }
        }
        return -1;
    }
}



