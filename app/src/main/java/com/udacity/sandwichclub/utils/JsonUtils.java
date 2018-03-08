package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class JsonUtils {

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

            if (clickedSandwich.has("name")) {
                // For a given sandwich, extract the JSONObject associated with the
                // key called "name", which contains name information about the sandwich
                JSONObject nameInfo = clickedSandwich.getJSONObject("name");

                // Extract the value for the key called "mainName"
                mainName = nameInfo.optString("mainName");

                // Extract the JSONArray associated with the key called "alsoKnownAs",
                // which represents a list of alternative known names for the sandwich.
                JSONArray alsoKnownAsArray = nameInfo.getJSONArray("alsoKnownAs");
                for (int j = 0; j < alsoKnownAsArray.length(); j++) {
                    alsoKnownAs.add(alsoKnownAsArray.optString(j));
                }
            }

            if (clickedSandwich.has("placeOfOrigin")) {
                // Extract the value for the key called "mainName"
                placeOfOrigin = clickedSandwich.optString("placeOfOrigin");
            }

            if (clickedSandwich.has("description")) {
                // Extract the value for the key called "mainName"
                description = clickedSandwich.optString("description");
            }

            if (clickedSandwich.has("image")) {
                // Extract the value for the key called "mainName"
                imageURL = clickedSandwich.optString("image");
            }

            if (clickedSandwich.has("ingredients")) {
                // Extract the JSONArray associated with the key called "ingredients",
                // which represents a list of ingredients of the sandwich.
                JSONArray ingredientsArray = clickedSandwich.getJSONArray("ingredients");
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