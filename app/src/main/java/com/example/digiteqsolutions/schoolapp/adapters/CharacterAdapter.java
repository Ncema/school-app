package com.example.digiteqsolutions.schoolapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.digiteqsolutions.schoolapp.R;
import com.example.digiteqsolutions.schoolapp.model.CharactersResponseModel;

import java.util.List;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder> {

    private List<CharactersResponseModel> charactersResponseModels;
    private Context context;
    private OnItemClickListener listener;


    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    public CharacterAdapter(Context context, List<CharactersResponseModel> charactersResponseModels) {
        this.charactersResponseModels = charactersResponseModels;
        this.context = context;
    }

    public CharacterAdapter( List<CharactersResponseModel> charactersResponseModels) {
        this.charactersResponseModels = charactersResponseModels;
    }


    public class CharacterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView textviewName, textViewRole, textViewV,textViewMinisteryofmagic,textViewOrderofthephoniex,textViewDumbledorearmy,textViewDeathEater,textViewBloodstatus,textViewSpecies;


        public CharacterViewHolder(View view) {
            super(view);
            textviewName = view.findViewById(R.id.item_name);
            textViewRole = view.findViewById(R.id.item_role);
            textViewV = view.findViewById(R.id.item_v);
            textViewMinisteryofmagic = view.findViewById(R.id.item_ministeryofmagic);
            textViewOrderofthephoniex = view.findViewById(R.id.item_orderofthephoniex);
            textViewDumbledorearmy = view.findViewById(R.id.item_dumbledorearmy);
            textViewBloodstatus = view.findViewById(R.id.item_bloodstatus);
            textViewSpecies = view.findViewById(R.id.item_species);
            textViewDeathEater = view.findViewById(R.id.item_deathEater);

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


        @Override
        public void onClick(View v) {


        }
    }




    @NonNull
    @Override
    public CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_item_character, parent, false);
        return new CharacterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final CharacterViewHolder holder, int position) {
        CharactersResponseModel charactersResponseModel = charactersResponseModels.get(position);

        if (charactersResponseModel != null) {
            holder.textviewName.setText("Name:"+charactersResponseModel.name);
            holder.textViewRole.setText("Role :" +charactersResponseModel.role);
            holder.textViewV.setText("Version :"+ String.valueOf(charactersResponseModel.__v));
            holder.textViewMinisteryofmagic.setText("Ministery :"+String.valueOf(charactersResponseModel.ministryOfMagic));
            holder.textViewOrderofthephoniex.setText("Phoniex :"+ String.valueOf(charactersResponseModel.orderOfThePhoenix));
            holder.textViewDumbledorearmy.setText("Doubledoreary :"+ String.valueOf(charactersResponseModel.dumbledoresArmy));
            holder.textViewBloodstatus.setText("Blood status :"+ charactersResponseModel.bloodStatus);
            holder.textViewSpecies.setText("Species :"+ charactersResponseModel.species);
            holder.textViewDeathEater.setText("DeathEater :"+ String.valueOf(charactersResponseModel.deathEater));
        }


    }

    @Override
    public int getItemCount() {
        return charactersResponseModels.size();
    }
}
