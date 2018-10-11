package example.example.com.bakingapp.Retrofit;

import java.util.List;

import example.example.com.bakingapp.Model.Recipe;
import retrofit2.Call;
import retrofit2.http.GET;


/**
 * Created by Mohamed Ahmed on 10/7/2018.
 */

public interface GetDataService {

    @GET("/topher/2017/May/59121517_baking/baking.json")
    Call<List<Recipe>> getAllRecipes();
}
