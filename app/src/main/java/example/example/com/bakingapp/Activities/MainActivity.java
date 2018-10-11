package example.example.com.bakingapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import example.example.com.bakingapp.Adapters.RecipesAdapter;
import example.example.com.bakingapp.IngredientsWidgetProvider;
import example.example.com.bakingapp.Model.Globals;
import example.example.com.bakingapp.Model.Ingredient;
import example.example.com.bakingapp.Model.Recipe;
import example.example.com.bakingapp.R;
import example.example.com.bakingapp.Retrofit.GetDataService;
import example.example.com.bakingapp.Retrofit.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecipesAdapter.RecipeAdapterClickHandeler {

    @BindView(R.id.no_internet_label)
    TextView noInternetLabel;
    @BindView(R.id.main_activity_rec_view)
    RecyclerView mRecyclerView;
    RecipesAdapter mAdapter;
    @BindView(R.id.main_progress_bar)
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle("Baking Time");
        mRecyclerView.setHasFixedSize(true);
        int posterWidth = 750;
        GridLayoutManager gridLayoutManager =
                new GridLayoutManager(this, calculateBestSpanCount(posterWidth));
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mAdapter = new RecipesAdapter(this, this);
        if (isConnected()) {
            GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<List<Recipe>> call = service.getAllRecipes();
            call.enqueue(new Callback<List<Recipe>>() {

                @Override
                public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                    mProgressBar.setVisibility(View.INVISIBLE);
                    showResult(response.body());
                }

                @Override
                public void onFailure(Call<List<Recipe>> call, Throwable t) {

                }
            });
        } else {
            showNoInternetMessage();
        }
    }

    private void showResult(List<Recipe> recipeList) {
        mRecyclerView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
        noInternetLabel.setVisibility(View.INVISIBLE);
        mAdapter.setmRecipeList(recipeList);
        mRecyclerView.setAdapter(mAdapter);

        SharedPreferences mPrefs = getSharedPreferences("MyWidgetPref",MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Random ran = new Random();
        int randomInListRange = ran.nextInt(recipeList.size());
        Log.e("randomm",randomInListRange+"");
        Recipe mRecipe =recipeList.get(randomInListRange);
        String ingredints = "Ingredients of " + mRecipe.getName() + "\n";
        for (Ingredient i : mRecipe.getIngredients()) {
            ingredints += i.getQuantity() + " " + i.getMeasure() + " " + i.getIngredient() + "\n";
        }
        prefsEditor.putString("rec",ingredints);
        prefsEditor.commit();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void showNoInternetMessage() {
        mProgressBar.setVisibility(View.INVISIBLE);
        noInternetLabel.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
    }

    public boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    private int calculateBestSpanCount(int posterWidth) {
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float screenWidth = outMetrics.widthPixels;
        return Math.round(screenWidth / posterWidth);
    }

    @Override
    public void onClick(Recipe selectedRecipe) {
        Intent detailsIntent = new Intent(this, RecipeDetailActivity.class);
        detailsIntent.putExtra(Globals.RECIPE, selectedRecipe);
        startActivity(detailsIntent);
    }
}
