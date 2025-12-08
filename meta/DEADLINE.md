# Deadline

Modify this file to satisfy a submission requirement related to the project
deadline. Please keep this file organized using Markdown. If you click on
this file in your GitHub repository website, then you will see that the
Markdown is transformed into nice-looking HTML.

## Part 1.1: App Description

> Please provide a friendly description of your app, including
> the primary functions available to users of the app. Be sure to
> describe exactly what APIs you are using and how they are connected
> in a meaningful way.

> **Also, include the GitHub `https` URL to your repository.**

TODO WRITE / REPLACE
My application is a **Makeup and Music Finder** that integrates two APIs to help users discover music based on makeup products. Users select a makeup brand and product type, browse the results, and then click on any product to find related songs on iTunes.
## Part 1.2: APIs

> For each RESTful JSON API that your app uses (at least two are required),
> include an example URL for a typical request made by your app. If you
> need to include additional notes (e.g., regarding API keys or rate
> limits), then you can do that below the URL/URI. Placeholders for this
> information are provided below. If your app uses more than two RESTful
> JSON APIs, then include them with similar formatting.

### API 1

String url = "http://makeup-api.herokuapp.com/api/v1/products.json" +
            "?brand=" + brand + "&product_type=" + type;
```
```

### API 2

```
https://itunes.apple.com/search?term={searchTerm}&media=music&limit=3
```

> Replace this line with notes (if needed) or remove it (if not needed).

## Part 2: New

> What is something new and/or exciting that you learned from working
> on this project?
I learned to think critically about JSON responses and how to interpret them in differentways. Previously, I would just search for the specific data field I needed, but now I canlook at an entire API response and analyze all the information available to me. This helps me identify other useful data points that I might not have considered initially. Understanding the full structure of API responses opened up more creative possibilities for howto use the data.
The connection is meaningful because it creates a fun experience where beauty product characteristics inspire music recommendations, linking two completely separate domains in a way that neither API could accomplish alone
TODO WRITE / REPLACE

## Part 3: Retrospect

> If you could start the project over from scratch, what do
> you think might do differently and why?
If I could start the project over from scratch, I would spend more time on the visual layout and the song selection features. I would make the app's appearance more polished and themed around beauty/cosmetics to better match the makeup products. I would also improve the music selection by adding user controls that let them categorize or filter songs by different criteria, such as genre or popularity, rather than just displaying random results. This would give users more control over their experience and make the integration between makeup and music feel more intentional.

TODO WRITE / REPLACE
