package com.example;
 
import com.azure.cosmos.ConsistencyLevel;
import com.azure.cosmos.CosmosClient;
import com.azure.cosmos.CosmosClientBuilder;
import com.azure.cosmos.CosmosContainer;
import com.azure.cosmos.CosmosDatabase;
import com.azure.cosmos.models.CosmosQueryRequestOptions;
import com.azure.cosmos.models.FeedResponse;
import com.azure.cosmos.util.CosmosPagedIterable;
 
public class App {
    private static final String ENDPOINT = "YOUR_COSMOS_DB_ENDPOINT";
    private static final String KEY = "YOUR_COSMOS_DB_PRIMARY_KEY";
    private static final String DATABASE_NAME = "YOUR_DATABASE_NAME";
    private static final String CONTAINER_NAME = "YOUR_CONTAINER_NAME";
 
    public static void main(String[] args) {
        System.out.println("Starting Azure Cosmos DB demo...");
        try {
            // Create a Cosmos DB client
            CosmosClient cosmosClient = new CosmosClientBuilder()
                .endpoint(ENDPOINT)
                .key(KEY)
                .consistencyLevel(ConsistencyLevel.EVENTUAL)
                .buildClient();
            System.out.println("Connected to Azure Cosmos DB!");
            // Get reference to the database
            CosmosDatabase database = cosmosClient.getDatabase(DATABASE_NAME);
            // Get reference to the container
            CosmosContainer container = database.getContainer(CONTAINER_NAME);
            // Query items from container
            String query = "SELECT * FROM c";
            CosmosPagedIterable<Object> items = container.queryItems(query, new CosmosQueryRequestOptions(), Object.class);
            System.out.println("\nQuerying items from container: " + CONTAINER_NAME);
            System.out.println("Query: " + query);
            // Print results
            int count = 0;
            for (Object item : items) {
                System.out.println(item);
                count++;
                // Limit output to first 10 items to avoid console overflow
                if (count >= 10) {
                    System.out.println("... (Showing first 10 items only)");
                    break;
                }
            }
            System.out.println("\nQuery completed successfully!");
            // Close the client
            cosmosClient.close();
            System.out.println("Connection closed. Demo completed.");
        } catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
