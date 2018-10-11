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
import example.example.com.bakingapp.Model.Step;
import example.example.com.bakingapp.R;

/**
 * Created by Mohamed Ahmed on 10/9/2018.
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepViewHolder> {
    private List<Step> mStepsList;

    private Context mContext;


    public void setmRecipeList(List<Step> mStepList) {
        this.mStepsList = mStepList;
    }

    private final RecipeAdapterClickHandeler mClickHandeler;

    public StepsAdapter(RecipeAdapterClickHandeler mClickHandeler, Context context) {
        this.mClickHandeler = mClickHandeler;
        mContext = context;
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutItemId = R.layout.step_view_item;
        if(mContext==null)
            mContext=parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(layoutItemId, parent, false);
        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        holder.bind(mStepsList.get(position));
    }

    @Override
    public int getItemCount() {
        return mStepsList.size();
    }

    public interface RecipeAdapterClickHandeler {
        void onClick(Step selectedStep);
    }

    public class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mTitle;

        public StepViewHolder(View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.step_item_title);
            itemView.setOnClickListener(this);
        }

        public void bind(Step step) {
            mTitle.setText("Step " + step.getId()+" : "+step.getShortDescription());
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Step selectedStep = mStepsList.get(adapterPosition);
            mClickHandeler.onClick(selectedStep);
        }
    }
}


