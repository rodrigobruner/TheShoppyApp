package app.bruner.theshoppyapp;

import androidx.annotation.NonNull;
/*
    To separate the provincial data and perhaps reuse it in the future,
    I decided to create a class that stores the provinces and returns a list of them in a static method.
 */
public class Provinces {

    public static String[] getProvinces() {
        //Returns an array of strings containing the provinces
        return new String[] {
                "Alberta",
                "British Columbia",
                "Manitoba",
                "New Brunswick",
                "Newfoundland and Labrador",
                "Nova Scotia",
                "Ontario",
                "Prince Edward Island",
                "Quebec",
                "Saskatchewan"
        };
    }
}
