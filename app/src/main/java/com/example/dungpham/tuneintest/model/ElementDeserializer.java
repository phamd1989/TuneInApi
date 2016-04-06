package com.example.dungpham.tuneintest.model;

import android.util.Log;

import com.example.dungpham.tuneintest.service.ReactiveApiService;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by dungpham on 4/5/16.
 */
public class ElementDeserializer implements JsonDeserializer<BaseElement> {
    @Override
    public BaseElement deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            return new Gson().fromJson(json, BaseElement.class);
        } catch (Exception e) {
            Log.e(ReactiveApiService.TAG, e.toString());
            return null;
        }
    }
}
