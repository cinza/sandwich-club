package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        // Declare variables to work with
        Sandwich sandwichSelected = null;
        JSONObject jsonSandwich = null;
        JSONObject baseJSON = null;
        String name;
        String placeOfOrigin;
        String description;
        String imgURL;
        JSONArray ingredients = null;
        JSONArray knowAs = null;
        List<String> ingredientsList = new  ArrayList<String>();
        List<String> knowAsList = new ArrayList<String>();

        //if is empty return null
        if(json.isEmpty ()){
            return null;
        }

        // start try, catch
        try{
            // declare instance of json object
            jsonSandwich = new JSONObject (json);

            //declare basejson object from name element
            baseJSON = jsonSandwich.getJSONObject("name");

            // get value mainName from baseJson
            name = baseJSON.getString("mainName");
            // get value from place origin
            placeOfOrigin = jsonSandwich.getString("placeOfOrigin");
            // get value from description
            description = jsonSandwich.getString("description");
            // get value from image
            imgURL = jsonSandwich.getString ("image");
            // get value from ingredients
            ingredients = jsonSandwich.getJSONArray("ingredients");
            // get value from known as
            knowAs = baseJSON.getJSONArray("alsoKnownAs");

            // fill list with items from ingredients
            for (int i=0; i<ingredients.length(); i++) {
                ingredientsList.add( ingredients.getString(i) );
            }
            // fill list with items from knownAs
            for(int x=0; x<knowAs.length (); x++){
                knowAsList.add(knowAs.getString (x));
            }
            // create instance of sandwich object
            sandwichSelected = new Sandwich (name, knowAsList, placeOfOrigin, description, imgURL, ingredientsList);



        }catch(JSONException error){
            Log.e("JSON","ERROR parsing Sandwich JSON ", error);
        }



        return sandwichSelected;
    }
}
