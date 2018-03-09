package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class JsonUtils {

    private static final String NAME = "name";
    private static final String MAIN_NAME = "mainName";
    private static final String ALSO_KNOWN_AS = "alsoKnownAs";
    private static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String DESCRIPTION = "description";
    private static final String IMAGE = "image";
    private static final String INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {

        String mainName = "";
        ArrayList<String> alsoKnownAs = new ArrayList<>();
        String placeOfOrigin = "";
        String description = "";
        String imageURL = "";
        ArrayList<String> ingredients = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            // Create a JSONObject from the JSON response string
            JSONObject clickedSandwich = new JSONObject(json);

            if (clickedSandwich.has(NAME)) {
                // For a given sandwich, extract the JSONObject associated with the
                // key called "name", which contains name information about the sandwich
                JSONObject nameInfo = clickedSandwich.getJSONObject(NAME);

                // Extract the value for the key called "mainName"
                mainName = nameInfo.optString(MAIN_NAME);

                // Extract the JSONArray associated with the key called "alsoKnownAs",
                // which represents a list of alternative known names for the sandwich.
                JSONArray alsoKnownAsArray = nameInfo.getJSONArray(ALSO_KNOWN_AS);
                for (int j = 0; j < alsoKnownAsArray.length(); j++) {
                    alsoKnownAs.add(alsoKnownAsArray.optString(j));
                }
            }

            if (clickedSandwich.has(PLACE_OF_ORIGIN)) {
                // Extract the value for the key called "mainName"
                placeOfOrigin = clickedSandwich.optString(PLACE_OF_ORIGIN);
            }

            if (clickedSandwich.has(DESCRIPTION)) {
                // Extract the value for the key called "mainName"
                description = clickedSandwich.optString(DESCRIPTION);
            }

            if (clickedSandwich.has(IMAGE)) {
                // Extract the value for the key called "mainName"
                imageURL = clickedSandwich.optString(IMAGE);
            }

            if (clickedSandwich.has(INGREDIENTS)) {
                // Extract the JSONArray associated with the key called "ingredients",
                // which represents a list of ingredients of the sandwich.
                JSONArray ingredientsArray = clickedSandwich.getJSONArray(INGREDIENTS);
                for (int j = 0; j < ingredientsArray.length(); j++) {
                    ingredients.add(ingredientsArray.optString(j));
                }
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the sandwich JSON results", e);
        }

        // Return the sandwich with its related info :
        return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, imageURL, ingredients);
    }
}