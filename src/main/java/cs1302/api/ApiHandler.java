package cs1302.api;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.Random;

/**
 * Handles API calls to both Makeup API and iTunes API.
 */
public class ApiHandler {
    
    private HttpClient client;
    private Gson gson;
    private ArrayList<MakeupProduct> lastProducts;
    private Random random;
    
    private static final int MAX_RESULTS = 3;
    
    /**
     * Constructs a new ApiHandler.
     */
    public ApiHandler() {
        this.client = HttpClient.newBuilder().build();
        this.gson = new Gson();
        this.random = new Random();
    }
    
    /**
     * Searches for makeup products by brand and type.
     * @param brand the brand name
     * @param type the product type
     * @return list of makeup products
     * @throws Exception if API call fails
     */
    public ArrayList<MakeupProduct> searchMakeup(String brand, String type) throws Exception {
        String url = "http://makeup-api.herokuapp.com/api/v1/products.json" +
                    "?brand=" + brand + "&product_type=" + type;
        
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .build();
        
        HttpResponse<String> response = client.send(request, 
            HttpResponse.BodyHandlers.ofString());
        
        lastProducts = gson.fromJson(response.body(), 
            new TypeToken<ArrayList<MakeupProduct>>(){}.getType());
        
        return lastProducts;
    }
    
    /**
     * Searches for music based on product name.
     * Uses a random word from product name for variety.
     * @param product the makeup product
     * @return iTunes API response
     * @throws Exception if API call fails
     */
    public ITunesResponse searchMusic(MakeupProduct product) throws Exception {
        String[] words = product.getName().split(" ");
        String searchTerm = words[random.nextInt(words.length)];
        
        String encoded = URLEncoder.encode(searchTerm, StandardCharsets.UTF_8);
        
        String url = "https://itunes.apple.com/search?term=" + encoded + 
                    "&media=music&limit=" + MAX_RESULTS;
        
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .build();
        
        HttpResponse<String> response = client.send(request,
            HttpResponse.BodyHandlers.ofString());
        
        return gson.fromJson(response.body(), ITunesResponse.class);
    }
    
    /**
     * Gets the last search results.
     * @return list of products from last search
     */
    public ArrayList<MakeupProduct> getLastProducts() {
        return lastProducts;
    }
}
