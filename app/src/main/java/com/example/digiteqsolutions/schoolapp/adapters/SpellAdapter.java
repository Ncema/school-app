package com.example.digiteqsolutions.schoolapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.digiteqsolutions.schoolapp.R;
import com.example.digiteqsolutions.schoolapp.model.SpellResponseModel;


import java.util.List;

public class SpellAdapter extends RecyclerView.Adapter<SpellAdapter.SpellViewHolder> {
    private List<SpellResponseModel> spellResponseModelList;
    private Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public SpellAdapter(Context context, List<SpellResponseModel> spellResponseModelList) {
        this.spellResponseModelList = spellResponseModelList;
        this.context = context;
    }

    public SpellAdapter( List<SpellResponseModel> spellResponseModelList) {
        this.spellResponseModelList = spellResponseModelList;
    }


    public class SpellViewHolder extends RecyclerView.ViewHolder  {

        public TextView textviewEffect, textViewSpell, textViewType;


        public SpellViewHolder(View view) {
            super(view);
            textviewEffect = view.findViewById(R.id.item_effect);
            textViewSpell = view.findViewById(R.id.item_spell);
            textViewType = view.findViewById(R.id.item_type);


        }
    }

    @NonNull
    @Override
    public SpellViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_item_spell, parent, false);
        return new SpellViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SpellViewHolder holder, int position) {
        SpellResponseModel spellResponseModel = spellResponseModelList.get(position);

        if (spellResponseModel != null) {
            holder.textviewEffect.setText("Effect : "+ spellResponseModel.effect);
            holder.textViewSpell.setText("Spell :"+spellResponseModel.spell);
            holder.textViewType.setText("Type :" +spellResponseModel.type);
        }

    }

    @Override
    public int getItemCount() {
        return spellResponseModelList.size();
    }

}

