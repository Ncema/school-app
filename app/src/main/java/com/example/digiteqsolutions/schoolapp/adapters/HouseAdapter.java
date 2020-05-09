package com.example.digiteqsolutions.schoolapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.digiteqsolutions.schoolapp.R;
import com.example.digiteqsolutions.schoolapp.model.HousesResponseModel;

import java.util.List;

public class HouseAdapter extends RecyclerView.Adapter<HouseAdapter.HouseViewHolder>{

    private List<HousesResponseModel> housesResponseModels;
    private Context context;
    private OnItemClickListener listener;


    public HouseAdapter(Context context, List<HousesResponseModel> housesResponseModels) {
        this.housesResponseModels = housesResponseModels;
        this.context = context;
    }

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public HouseAdapter( List<HousesResponseModel> housesResponseModels) {
        this.housesResponseModels = housesResponseModels;
    }


    public class HouseViewHolder extends RecyclerView.ViewHolder  {

        public TextView textviewName, textViewMascout, textViewHeadOfHouse,textViewHouseGhost,textViewFounder,textViewV,textViewSchool,textViewMembers;


        public HouseViewHolder(View view) {
            super(view);
            textviewName = view.findViewById(R.id.item_name);
            textViewMascout = view.findViewById(R.id.item_mascout);
            textViewHeadOfHouse = view.findViewById(R.id.item_headOfHouse);
            textViewHouseGhost = view.findViewById(R.id.item_headOfHouse);
            textViewFounder = view.findViewById(R.id.item_founder);
            textViewV = view.findViewById(R.id.item_v);
            textViewSchool = view.findViewById(R.id.item_school);
            textViewMembers = view.findViewById(R.id.item_members);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(itemView, position);



                        }
                    }
                }
            });



        }


    }

    @NonNull
    @Override
    public HouseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_item_house, parent, false);
        return new HouseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HouseViewHolder holder, int position) {
        HousesResponseModel housesResponseModel = housesResponseModels.get(position);

        if (housesResponseModels != null) {
            holder.textviewName.setText(housesResponseModel.name);
            holder.textViewMascout.setText(housesResponseModel.mascot);
            holder.textViewHeadOfHouse.setText(housesResponseModel.headOfHouse);
            holder.textViewHouseGhost.setText(housesResponseModel.houseGhost);
            holder.textViewFounder.setText(housesResponseModel.founder);
            holder.textViewV.setText(String.valueOf(housesResponseModel.__v));
            holder.textViewSchool.setText(housesResponseModel.school);

        }
    }

    @Override
    public int getItemCount() {
        return housesResponseModels.size();
    }
}
