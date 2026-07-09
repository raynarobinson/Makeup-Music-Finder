# Music-Inspired Makeup Recommendation App

A JavaFX desktop application that connects makeup products with music recommendations. Users select a makeup brand and product type, then the app searches for matching makeup products and finds related songs using the iTunes API.

## Features

- Search makeup products by brand and product type
- Display matching makeup products in an interactive list
- Connect selected makeup products to related music results
- Show song title, artist, album, and album artwork
- Retrieve and parse live API data using RESTful JSON APIs

## Technologies Used

- Java 17
- JavaFX
- RESTful APIs
- Makeup API
- iTunes Search API
- Gson
- Java HttpClient
- Maven
- Git/GitHub
- Unix/Linux

## APIs Used

### Makeup API
Used to search for makeup products by brand and product type.

### iTunes Search API
Used to search for music based on a keyword from the selected makeup product name.

## How It Works

1. The user selects a makeup brand and product type.
2. The app sends a request to the Makeup API.
3. The app displays the returned makeup products.
4. The user selects a product.
5. The app uses a word from the product name to search the iTunes API.
6. The app displays related songs and album artwork.

## How to Run

```bash
./run.sh