package com.example.digiteqsolutions.schoolapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SpellResponseModel {
    @SerializedName("_id")
    public String _id;
    @SerializedName("spell")
    public String spell;
    @SerializedName("type")
    public String type;
    @SerializedName("effect")
    public String effect;

    public SpellResponseModel(String _id, String spell, String type, String effect) {
        this._id = _id;
        this.spell = spell;
        this.type = type;
        this.effect = effect;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getSpell() {
        return spell;
    }

    public void setSpell(String spell) {
        this.spell = spell;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }
}
