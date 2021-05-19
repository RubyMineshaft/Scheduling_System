package model;

public class FirstLevelDivision {

    private int id;
    private int firstLevelDivisionName;

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

    public FirstLevelDivision(int id, int firstLevelDivisionName) {
        this.id = id;
        this.firstLevelDivisionName = firstLevelDivisionName;
    }
}
