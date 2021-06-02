package model;

/** Model for FirstLevelDivision objects. */
public class FirstLevelDivision {

    /** The Division ID. */
    private int id;

    /** The Division name. */
    private String name;

    /** The Division's country ID. */
    private int countryID;

    /** Constructor for FirstLevelDivision objects.
     * @param id the division ID
     * @param name the division name
     * @param countryID the country ID
     */
    public FirstLevelDivision(int id, String name, int countryID) {
        this.id = id;
        this.name = name;
        this.countryID = countryID;
    }

    /** Getter for division ID.
     * @return the division ID
     */
    public int getId() {
        return id;
    }

    /** Setter for division ID.
     * @param id the division ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /** Getter for division name.
     * @return the division name
     */
    public String getName() {
        return name;
    }

    /** Setter for division name.
     * @param name the division name
     */
    public void setName(String name) {
        this.name = name;
    }

    /** Getter for country ID.
     * @return the country ID
     */
    public int getCountryID() {
        return countryID;
    }

    /** Overridden toString method for displaying divisions in combo boxes.
     * @return a custom string showing the division name
     */
    @Override
    public String toString(){
        return name;
    }
}
