package com.June.CourierNetwork.Enum;

public enum Parishes {
    ST_CATHERINE("St. Catherine"),
    ST_JAMES("St. James"),
    ST_MARY("St. Mary"),
    ST_THOMAS("St. Thomas"),
    ST_ANDREW("St. Andrew"),
    ST_ANN("St. Ann"),
    WESTMORELAND("Westmoreland"),
    ST_ELIZABETH("St. Elizabeth"),
    PORTLAND("Portland"),
    MANCHESTER("Manchester"),
    KINGSTON("Kingston"),
    HANOVER("Hanover"),
    CLARENDON("Clarendon"),
    TRELAWNY("Trelawny");

    private final String parishName;

    // Constructor
    Parishes(String parishName) {
        this.parishName = parishName;
    }

    // Getter method to retrieve the parish name
    public String getParishName() {
        return parishName;
    }
}
