package example.example.com.bakingapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import example.example.com.bakingapp.Model.Recipe;
import example.example.com.bakingapp.R;

/**
 * Created by Mohamed Ahmed on 10/7/2018.
 */

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder> {
    private List<Recipe> mRecipeList;

    private Context mContext;


    public void setmRecipeList(List<Recipe> mRecipeList) {
        this.mRecipeList = mRecipeList;
    }

    private final RecipeAdapterClickHandeler mClickHandeler;

    public RecipesAdapter(RecipeAdapterClickHandeler mClickHandeler, Context context) {
        this.mClickHandeler = mClickHandeler;
        mContext = context;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutItemId = R.layout.main_activity_item;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(layoutItemId, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        holder.bind(mRecipeList.get(position));
    }

    @Override
    public int getItemCount() {
        return mRecipeList.size();
    }

    public interface RecipeAdapterClickHandeler {
        void onClick(Recipe selectedRecipe);
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mTitle;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.main_item_title);
            itemView.setOnClickListener(this);
        }

        public void bind(Recipe recipe) {
            mTitle.setText(recipe.getName());
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Recipe selectedFilm = mRecipeList.get(adapterPosition);
            mClickHandeler.onClick(selectedFilm);
        }
    }
}
