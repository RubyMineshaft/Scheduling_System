package model;

public class Country {

    private int id;
    private String name;

    public int getID() {
        return id;
    }

    public void setID(int countryID) {
        this.id = countryID;
    }

    public String getName() {
        return name;
    }

    public void setName(String countryName) {
        this.name = countryName;
    }

    public Country(int countryID, String countryName) {
        this.id = countryID;
        this.name = countryName;
    }

    @Override
    public String toString(){
        return name;
    }
}
