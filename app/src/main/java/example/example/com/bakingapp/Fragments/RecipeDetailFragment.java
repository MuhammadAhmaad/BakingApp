package example.example.com.bakingapp.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.Serializable;

import example.example.com.bakingapp.Activities.RecipeDetailActivity;
import example.example.com.bakingapp.Adapters.StepsAdapter;
import example.example.com.bakingapp.Model.Globals;
import example.example.com.bakingapp.Model.Ingredient;
import example.example.com.bakingapp.Model.Recipe;
import example.example.com.bakingapp.Model.Step;
import example.example.com.bakingapp.R;

public class RecipeDetailFragment extends Fragment implements StepsAdapter.RecipeAdapterClickHandeler {

    private Recipe mRecipe;
    private TextView ingredientsTv;
    private RecyclerView mRecyclerView;
    private Context mContext;
    private StepsAdapter mAdapter;

    public void setmRecipe(Recipe mRecipe, Context context) {
        this.mRecipe = mRecipe;
        mContext = context;
    }

    public RecipeDetailFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        ingredientsTv = view.findViewById(R.id.recipe_ingredients);
        mRecyclerView = view.findViewById(R.id.steps_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        if (savedInstanceState != null) {
            mRecipe = (Recipe) savedInstanceState.get(Globals.RECIPE);
        }
        mAdapter = new StepsAdapter(this, mContext);
        mAdapter.setmRecipeList(mRecipe.getSteps());
        mRecyclerView.setAdapter(mAdapter);
        for (Ingredient ingredient : mRecipe.getIngredients()) {
            String ingreientContent = ingredient.getQuantity() + " " + ingredient.getMeasure() + " " + ingredient.getIngredient();
            ingredientsTv.append(ingreientContent + "\n");
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(Globals.RECIPE, mRecipe);
    }

    @Override
    public void onClick(Step selectedStep) {
        if (mContext == null)
            mContext = getContext();
        RecipeDetailActivity recipeDetailActivity = (RecipeDetailActivity) mContext;
        recipeDetailActivity.replaceFragment(selectedStep);
    }
}
