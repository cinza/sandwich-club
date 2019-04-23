package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {

//            closeOnError();
        }else{

        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);

        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }


        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];

        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        Log.i("SANDWICH SELECTED", String.valueOf (sandwich));
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);

        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwichSelected) {
        // Declare variables need it
        TextView originTxtView = findViewById (R.id.origin_tv);
        TextView knowAsTxtView = findViewById (R.id.also_known_tv);
        TextView ingredientsTxtView = findViewById (R.id.ingredients_tv);
        TextView descriptionTxtView = findViewById (R.id.description_tv);
        String originStr = sandwichSelected.getPlaceOfOrigin();
        String descriptionStr = sandwichSelected.getDescription ();
        List<String> alsoKnowListStr = sandwichSelected.getAlsoKnownAs ();
        StringBuilder knowAsItems = new StringBuilder ();
        List<String> ingredientsListStr = sandwichSelected.getIngredients ();
        StringBuilder ingredientsItems = new StringBuilder ();



        // Check if origin is different to "",  if no it will set the unknow text from setUnknow

        if(originStr != " "){
            originTxtView.setText (originStr);
        }else{
            setUnknown(originTxtView);
        }
        // check if description is empty
        if(descriptionStr != ""){
            descriptionTxtView.setText (descriptionStr);
        }
        // Fill knowAsItems stream builder with list of alsoKnownListStr
        for(String item : alsoKnowListStr){
            knowAsItems.append(item).append(", ");
        }

        //Check if knowAsItems is empty, if so set text unknown
        if(knowAsItems.length() > 1){
           knowAsItems.setLength (knowAsItems.length () - 2);
           knowAsTxtView.setText (knowAsItems);
        }else{
            setUnknown (knowAsTxtView);
        }

        // Fill ingredientsItems stream builder with list of ingredientsListStr
        for(String item : ingredientsListStr){
            ingredientsItems.append(item).append(", ");
        }

        //Check if ingredientsItems is empty, if so set text unknown
        if(ingredientsItems.length() > 1){
            ingredientsItems.setLength (ingredientsItems.length () - 2);
            ingredientsTxtView.setText (ingredientsItems);
        }else{
            setUnknown (ingredientsTxtView);
        }


    }
    // method to set teview to unknown value
    void setUnknown(TextView txtToChange){
        txtToChange.setText ("unknown");
    }
}
