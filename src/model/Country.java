package model;

/** Model for Country objects. */
public class Country {

    /** The Country ID. */
    private int id;

    /** The name of the country. */
    private String name;

    /** Getter for country ID.
     * @return the country ID
     */
    public int getID() {
        return id;
    }

    /** Setter for country ID.
     * @param countryID the country ID
     */
    public void setID(int countryID) {
        this.id = countryID;
    }

    /** Getter for country name.
     * @return the country name
     */
    public String getName() {
        return name;
    }

    /** Setter for country name.
     * @param countryName the country name
     */
    public void setName(String countryName) {
        this.name = countryName;
    }

    /** Constructor for Country objects.
     * @param countryID the country ID
     * @param countryName the country name
     */
    public Country(int countryID, String countryName) {
        this.id = countryID;
        this.name = countryName;
    }

    /** Overridden toString method for displaying countries in combo boxes.
     * @return custom String describing the country
     */
    @Override
    public String toString(){
        return name;
    }
}
