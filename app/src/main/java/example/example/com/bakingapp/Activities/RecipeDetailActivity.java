package example.example.com.bakingapp.Activities;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import example.example.com.bakingapp.Adapters.StepsAdapter;
import example.example.com.bakingapp.Fragments.RecipeDetailFragment;
import example.example.com.bakingapp.Fragments.StepDetailFragment;
import example.example.com.bakingapp.Model.Globals;
import example.example.com.bakingapp.Model.Recipe;
import example.example.com.bakingapp.Model.Step;
import example.example.com.bakingapp.R;

public class RecipeDetailActivity extends AppCompatActivity {
    private Recipe mRecipe;
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        mRecipe = (Recipe) getIntent().getSerializableExtra(Globals.RECIPE);
        getSupportActionBar().setTitle(mRecipe.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
        recipeDetailFragment.setmRecipe(mRecipe, this);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.recipe_detail_fragment, recipeDetailFragment)
                .commit();
        if (findViewById(R.id.step_detail_fragment) != null) {
            mTwoPane = true;
            StepDetailFragment stepDetailFragment = new StepDetailFragment();
            stepDetailFragment.setmStep(mRecipe.getSteps().get(0), mRecipe.getSteps());
            stepDetailFragment.setmContext(this);
            stepDetailFragment.setmTwoPane(mTwoPane);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.step_detail_fragment, stepDetailFragment)
                    .commit();
        } else {
            mTwoPane = false;
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        handleBackPressed();
        return true;
    }

    public void replaceFragment(Step selectedStep) {
        if (mTwoPane) {
            StepDetailFragment stepDetailFragment = new StepDetailFragment();
            stepDetailFragment.setmStep(selectedStep, mRecipe.getSteps());
            stepDetailFragment.setmContext(this);
            stepDetailFragment.setmTwoPane(mTwoPane);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.step_detail_fragment, stepDetailFragment)
                    .commit();
        } else {
            StepDetailFragment stepDetailFragment = new StepDetailFragment();
            stepDetailFragment.setmStep(selectedStep, mRecipe.getSteps());
            stepDetailFragment.setmContext(this);
            stepDetailFragment.setmTwoPane(mTwoPane);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.recipe_detail_fragment, stepDetailFragment)
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        handleBackPressed();
    }

    private void handleBackPressed() {
        if (mTwoPane || findViewById(R.id.step_detail_instructions) == null)
            finish();
        else {
            RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
            recipeDetailFragment.setmRecipe(mRecipe, this);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.recipe_detail_fragment, recipeDetailFragment)
                    .commit();
        }
    }
}
