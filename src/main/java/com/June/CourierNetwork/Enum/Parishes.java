package com.June.CourierNetwork.Enum;

public enum Parishes {
    STCATHERINE("St. Catherine"),
    STJAMES("St. James"),
    STMARY("St. Mary"),
    STTHOMAS("St. Thomas"),
    STANDREW("St. Andrew"),
    STANN("St. Ann"),
    WESTMORELAND("Westmoreland"),
    STELIZABETH("St. Elizabeth"),
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

    public String getParishName() {
        return parishName;
    }

}
