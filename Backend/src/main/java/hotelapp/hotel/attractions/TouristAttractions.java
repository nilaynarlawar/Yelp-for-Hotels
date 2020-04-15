package hotelapp.hotel.attractions;

import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

/**
 * This class is used make the List of TouristAttraction class, for parsing Json.
 */
public class TouristAttractions {
    @SerializedName("results")
    private List<TouristAttraction> touristAttractions;

    /**
     * Parameterize constructor of class TouristAttarctions
     *
     * @param touristAttractions list of all tourist attraction
     */
    public TouristAttractions(List<TouristAttraction> touristAttractions) {
        this.touristAttractions = touristAttractions;
    }

    /**
     * gives the list of all the touristAttractions.
     *
     * @return List of touristAttractions.
     */
    public List<TouristAttraction> getTouristAttractions() {
        return Collections.unmodifiableList(touristAttractions);
    }

    @Override
    public String toString() {
        return "TouristAttractions{" +
                "touristAttractions=" + touristAttractions +
                "}";
    }
}
