package com.example.avito_task;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Random;

public class ComplexRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public List<Object> items;
    private int cnt;

    //for marking for Recycler view what type to inflate on current List position
    private final int USER = 0;
    private Random mRandom= new Random();

    public ComplexRecyclerViewAdapter(List<Object> items) {
        this.items = items;
        cnt=items.size();
    }

    public void AddCardToViewAdapter(){
//        items.add(1);
        int newIndex = mRandom.nextInt(items.size()-1);
        System.out.println(newIndex);
        cnt++;
        items.set(newIndex,cnt);
//        notifyItemInserted(items.size() - 1);
        notifyItemChanged(newIndex);
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    //tell the RecyclerView about the type of view to inflate based on the position.
    @Override
    public int getItemViewType(int position) {
        Object o = items.get(position);
        if (items.get(position) instanceof Integer)
            return USER;
        return -1;
    }

    //tell the RecyclerView.Adapter about which RecyclerView.ViewHolder
    // object to create based on the viewType returned
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        switch (viewType) {
            case USER:
                View v1 = inflater.inflate(R.layout.layout_viewholder_card, viewGroup, false);
                viewHolder = new ViewHolderCard(v1);
//                setOnClickDeleteListener(viewHolder);
                break;

            default:
                break;
        }
        return viewHolder;
    }

    //configure the ViewHolder
    // with actual data that needs to be displayed
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case USER:
                ViewHolderCard vh1 = (ViewHolderCard) viewHolder;
                configureViewHolderCard(vh1, position);
                break;
            default:
                break;
        }
    }

    private void configureViewHolderCard(ViewHolderCard viewHolderCard, int position) {
//        viewHolderCard.getLabel().setText(String.valueOf(viewHolderCard.getAdapterPosition()));
        viewHolderCard.getLabel().setText(String.valueOf(items.get(position)));
        viewHolderCard.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPos = viewHolderCard.getAdapterPosition();
                if (adapterPos != RecyclerView.NO_POSITION) {
                    items.remove(adapterPos);
                    notifyItemRemoved(viewHolderCard.getAdapterPosition());
                    notifyItemRangeChanged(viewHolderCard.getAdapterPosition(),items.size());
                }
            }
        });
    }


}
