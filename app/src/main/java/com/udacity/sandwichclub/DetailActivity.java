package com.udacity.sandwichclub;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.databinding.ActivityDetailBinding;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    // Store the binding
    private ActivityDetailBinding binding;

    // Declare an instance of Sandwich
    private Sandwich sandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the content view (replacing `setContentView`)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = 0;
        if (intent != null) {
            position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        }
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);

        setTitle(sandwich.getMainName());

        Picasso.with(this)
                .load(sandwich.getImage())
                .into(binding.imageIv);

        populateUI();
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

        if(sandwich.getAlsoKnownAs().size() != 0) {
            for (int i = 0; i < sandwich.getAlsoKnownAs().size(); i++) {
                if (sandwich.getAlsoKnownAs().size() == 1) {
                    binding.alsoKnownTv.append(sandwich.getAlsoKnownAs().get(i));
                }
                else { binding.alsoKnownTv.append("\u2022 " + sandwich.getAlsoKnownAs().get(i)); }

                if (i < sandwich.getAlsoKnownAs().size() - 1) {
                    binding.alsoKnownTv.append("\n\n");
                }
            }
        }
        else {
            binding.alsoKnownLabel.setVisibility(View.GONE);
            binding.alsoKnownTv.setVisibility(View.GONE);
        }

        if(!sandwich.getPlaceOfOrigin().isEmpty()) {
            binding.originTv.setText(sandwich.getPlaceOfOrigin());
        }
        else {
            binding.originLabel.setVisibility(View.GONE);
            binding.originTv.setVisibility(View.GONE);
        }

        if(!sandwich.getDescription().isEmpty()) {
            binding.descriptionTv.setText(sandwich.getDescription());
        }
        else {
            binding.descriptionLabel.setVisibility(View.GONE);
            binding.descriptionTv.setVisibility(View.GONE);
        }

        if(sandwich.getIngredients().size() != 0) {
            for (int i = 0; i < sandwich.getIngredients().size(); i++) {
                binding.ingredientsTv.append("\u2022 " + sandwich.getIngredients().get(i));
                if (i < sandwich.getIngredients().size() - 1) {
                    binding.ingredientsTv.append("\n\n");
                }
            }
        }
        else {
            binding.ingredientsLabel.setVisibility(View.GONE);
            binding.ingredientsTv.setVisibility(View.GONE);
        }
    }
}