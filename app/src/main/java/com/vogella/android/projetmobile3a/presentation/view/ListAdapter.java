package com.vogella.android.projetmobile3a.presentation.view;


import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import com.vogella.android.projetmobile3a.R;
import com.vogella.android.projetmobile3a.presentation.model.Country;


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private final List<Country> values;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Country country);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView txtHeader;
        TextView txtFooter1;
        TextView txtFooter2;
        View layout;

        ViewHolder(View v) {
            super(v);
            layout = v;
            txtHeader = v.findViewById(R.id.firstLine);
            txtFooter1 = v.findViewById(R.id.totalCases);
            txtFooter2 = v.findViewById(R.id.totalRecovered);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    ListAdapter(List<Country> myDataset, OnItemClickListener listener) {
        this.values = myDataset;
        this.listener = listener;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.row_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Country currentCountry = values.get(position);
        holder.txtHeader.setText(currentCountry.getName());
        holder.txtFooter1.setText("Active : " + currentCountry.getTotalConfirmed());
        holder.txtFooter2.setText("Recovered : " + currentCountry.getTotalRecovered());

        holder.itemView.setOnClickListener(v -> listener.onItemClick(currentCountry));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }


}


