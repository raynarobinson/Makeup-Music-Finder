package cs1302.api;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a makeup product from the Makeup API.
 */
public class MakeupProduct {
    private int id;
    private String name;
    private String brand;
    private String price;

    @SerializedName("price_sign")
    private String priceSign;

    @SerializedName("image_link")
    private String imageLink;

    @SerializedName("product_type")
    private String productType;

    private String description;

    @SerializedName("product_colors")
    private ProductColor[] productColors;

    /**
     * Gets the product ID.
     * @return the product ID
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the product name.
     * @return the product name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the brand name.
     * @return the brand name
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Gets the price.
     * @return the price
     */
    public String getPrice() {
        return price;
    }

    /**
     * Gets the price sign.
     * @return the price sign
     */
    public String getPriceSign() {
        return priceSign;
    }

    /**
     * Gets the image link.
     * @return the image link URL
     */
    public String getImageLink() {
        return imageLink;
    }

    /**
     * Gets the product type.
     * @return the product type
     */
    public String getProductType() {
        return productType;
    }

    /**
     * Gets the description.
     * @return the product description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the product colors.
     * @return array of product colors
     */
    public ProductColor[] getProductColors() {
        return productColors;
    }

    /**
     * Represents a product color option.
     */
    public static class ProductColor {

        @SerializedName("colour_name")
        private String colorName;

        @SerializedName("hex_value")
        private String hexValue;

        /**
         * Gets the color name.
         * @return the color name
         */
        public String getColorName() {
            return colorName;
        }

        /**
         * Gets the hex value.
         * @return the hex color value
         */
        public String getHexValue() {
            return hexValue;
        }
    }
}
