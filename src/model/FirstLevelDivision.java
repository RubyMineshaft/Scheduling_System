package model;

public class FirstLevelDivision {

    private int id;
    private int firstLevelDivisionName;
    private int countryID;

    public FirstLevelDivision(int id, int firstLevelDivisionName, int countryID) {
        this.id = id;
        this.firstLevelDivisionName = firstLevelDivisionName;
        this.countryID = countryID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFirstLevelDivisionName() {
        return firstLevelDivisionName;
    }

    public void setFirstLevelDivisionName(int firstLevelDivisionName) {
        this.firstLevelDivisionName = firstLevelDivisionName;
    }

    public int getCountryID() {
        return countryID;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }
}
