package cs1302.api;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import java.util.ArrayList;

/**
 * Main application for Makeup and Music Finder.
 */
public class ApiDriver extends Application {

    private ComboBox<String> brandBox;
    private ComboBox<String> typeBox;
    private Button searchButton;
    private ListView<String> productList;
    private TextArea resultArea;
    private ImageView albumArtView;
    private Label statusLabel;

    private ApiHandler apiHandler;

    private static final String[] BRANDS = {
        "maybelline", "nyx", "covergirl", "revlon", "l'oreal"
    };

    private static final String[] TYPES = {
        "lipstick", "foundation", "eyeshadow", "mascara", "blush"
    };

    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 700;
    private static final int PRODUCT_LIST_HEIGHT = 150;
    private static final int RESULT_AREA_HEIGHT = 200;
    private static final int ALBUM_ART_SIZE = 200;
    private static final int SPACING = 15;
    private static final int PADDING = 20;
    private static final int SEARCH_SPACING = 10;
    private static final int MAX_SONGS = 3;

    /**
     * Starts the JavaFX application.
     * @param stage the primary stage
     */
    @Override
    public void start(Stage stage) {
        this.apiHandler = new ApiHandler();

        VBox root = new VBox(SPACING);
        root.setPadding(new Insets(PADDING));

        Label title = new Label("Makeup and Music Finder");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        HBox searchBox = createSearchBox();

        Label productsLabel = new Label("Products:");
        productList = new ListView<>();
        productList.setPrefHeight(PRODUCT_LIST_HEIGHT);
        productList.getSelectionModel().selectedItemProperty().addListener(
            (obs, old, newVal) -> {
                if (newVal != null) {
                    showMusic();
                }
            }
        );

        HBox contentBox = createContentBox();

        statusLabel = new Label("Select brand and type, then click Search");

        root.getChildren().addAll(
            title, searchBox,
            productsLabel, productList,
            contentBox,
            statusLabel
        );

        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        stage.setScene(scene);
        stage.setTitle("API Integration Project");
        stage.show();
    }

    /**
     * Creates the search box with dropdowns and button.
     * @return HBox containing search controls
     */
    private HBox createSearchBox() {
        HBox searchBox = new HBox(SEARCH_SPACING);

        brandBox = new ComboBox<>();
        brandBox.getItems().addAll(BRANDS);
        brandBox.setValue("maybelline");

        typeBox = new ComboBox<>();
        typeBox.getItems().addAll(TYPES);
        typeBox.setValue("lipstick");

        searchButton = new Button("Search");
        searchButton.setOnAction(e -> performSearch());

        searchBox.getChildren().addAll(
            new Label("Brand:"), brandBox,
            new Label("Type:"), typeBox,
            searchButton
        );

        return searchBox;
    }

    /**
     * Creates the content box with results and image.
     * @return HBox containing text area and image view
     */
    private HBox createContentBox() {
        HBox contentBox = new HBox(SPACING);

        VBox leftBox = new VBox(SEARCH_SPACING);
        Label resultsLabel = new Label("Matching Music:");
        resultArea = new TextArea();
        resultArea.setEditable(false);
        resultArea.setPrefHeight(RESULT_AREA_HEIGHT);
        leftBox.getChildren().addAll(resultsLabel, resultArea);

        VBox rightBox = new VBox(SEARCH_SPACING);
        rightBox.setAlignment(Pos.TOP_CENTER);
        Label artLabel = new Label("Album Art:");
        albumArtView = new ImageView();
        albumArtView.setFitWidth(ALBUM_ART_SIZE);
        albumArtView.setFitHeight(ALBUM_ART_SIZE);
        albumArtView.setPreserveRatio(true);
        rightBox.getChildren().addAll(artLabel, albumArtView);

        contentBox.getChildren().addAll(leftBox, rightBox);

        return contentBox;
    }

    /**
     * Performs the makeup product search.
     */
    private void performSearch() {
        String brand = brandBox.getValue();
        String type = typeBox.getValue();

        statusLabel.setText("Searching makeup products...");
        searchButton.setDisable(true);

        Thread thread = new Thread(() -> {
            try {
                ArrayList<MakeupProduct> products = apiHandler.searchMakeup(brand, type);

                Platform.runLater(() -> {
                    productList.getItems().clear();
                    if (products != null && !products.isEmpty()) {
                        for (MakeupProduct p : products) {
                            productList.getItems().add(p.getName());
                        }
                        statusLabel.setText("Found " + products.size()
                            + " products. Click one to find music.");
                    } else {
                        statusLabel.setText("No products found.");
                    }
                    searchButton.setDisable(false);
                });
            } catch (Exception e) {
                Platform.runLater(() -> {
                    statusLabel.setText("Error: " + e.getMessage());
                    searchButton.setDisable(false);
                });
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Shows music results for the selected product.
     */
    private void showMusic() {
        int index = productList.getSelectionModel().getSelectedIndex();
        if (index < 0) {
            return;
        }

        statusLabel.setText("Finding matching music...");
        resultArea.setText("Loading...");
        albumArtView.setImage(null);

        Thread thread = new Thread(() -> {
            try {
                ArrayList<MakeupProduct> products = apiHandler.getLastProducts();
                if (products != null && index < products.size()) {
                    MakeupProduct product = products.get(index);
                    ITunesResponse itunes = apiHandler.searchMusic(product);

                    Platform.runLater(() -> {
                        displayResults(product, itunes);
                    });
                }
            } catch (Exception e) {
                Platform.runLater(() -> {
                    resultArea.setText("Error: " + e.getMessage());
                    statusLabel.setText("Music search failed");
                });
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Displays the music results and album art.
     * @param product the makeup product
     * @param itunes the iTunes API response
     */
    private void displayResults(MakeupProduct product, ITunesResponse itunes) {
        StringBuilder sb = new StringBuilder();
        sb.append("Product: ").append(product.getName()).append("\n");
        sb.append("Brand: ").append(product.getBrand()).append("\n\n");
        sb.append("Matching Songs:\n\n");

        if (itunes.results != null && itunes.results.length > 0) {
            for (int i = 0; i < Math.min(MAX_SONGS, itunes.results.length); i++) {
                ITunesResult song = itunes.results[i];
                sb.append((i + 1)).append(". ").append(song.trackName).append("\n");
                sb.append("   Artist: ").append(song.artistName).append("\n");
                sb.append("   Album: ").append(song.collectionName).append("\n\n");
            }

            if (itunes.results[0].artworkUrl100 != null) {
                try {
                    Image albumArt = new Image(itunes.results[0].artworkUrl100);
                    albumArtView.setImage(albumArt);
                } catch (Exception e) {
                    System.out.println("Could not load album art");
                }
            }
        } else {
            sb.append("No music found.\n");
        }

        resultArea.setText(sb.toString());
        statusLabel.setText("Found music for: " + product.getName());
    }

    /**
     * Main entry point.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
