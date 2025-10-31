package ku.restaurant.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class RestaurantRequest {
    @NotBlank(message = "Name is mandatory")
    private String name;


    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating cannot exceed 5")
    private double rating;


    @NotBlank(message = "Location is mandatory")
    private String location;

    // Getter and setter for name
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    // Getter and setter for rating
    public double getRating() {
        return rating;
    }
    public void setRating(double rating) {
        this.rating = rating;
    }

    // Getter and setter for location
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    // toString method
    @Override
    public String toString() {
        return "RestaurantRequest{" +
                "name='" + name + '\'' +
                ", rating=" + rating +
                ", location='" + location + '\'' +
                '}';
    }

    // equals method
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RestaurantRequest)) return false;

        RestaurantRequest that = (RestaurantRequest) o;

        if (Double.compare(that.rating, rating) != 0) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return location != null ? location.equals(that.location) : that.location == null;
    }

    // hashCode method
    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name != null ? name.hashCode() : 0;
        temp = Double.doubleToLongBits(rating);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (location != null ? location.hashCode() : 0);
        return result;
    }
}
