package model;

public class FirstLevelDivision {

    private int id;
    private int name;
    private int countryID;

    public FirstLevelDivision(int id, int name, int countryID) {
        this.id = id;
        this.name = name;
        this.countryID = countryID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public int getCountryID() {
        return countryID;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }
}
