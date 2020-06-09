package com.project.starwarsgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String[] character_attributes = {"name","birth_year","eye_color","gender","hair_color","height","mass","skin_color"};
    String[] planets_attributes = {"name","diameter","rotation_period","orbital_period","gravity","population","climate","terrain","surface_water"};
    String[] films_attributes = {"title","episode_id","opening_crawl","director","producer"};
    String[] starships_attributes = {"name","MGLT","passengers","crew","cargo_capacity","consumables","length","model","hyperdrive_rating","max_atmosphering_speed","starship_class","manufacturer"};
    String[] vehicle_attributes = {"name","length","crew","consumables","passengers","model","vehicle_class","max_atmosphering_speed","cargo_capacity","cost_in_credits","manufacturer"};
    String[] species_attributes = {"name","language","classification","designation","average_height","average_lifespan","eye_colors","hair_colors","skin_colors"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openPlanets(View view) {
        Intent planets = new Intent(MainActivity.this,QuestionActivity.class);
        planets.putExtra("Total",60);
        planets.putExtra("Search","planets/");
        planets.putExtra("attributes",planets_attributes);
        startActivity(planets);
    }

    public void openChar(View view) {
        Intent Character = new Intent(MainActivity.this,QuestionActivity.class);
        Character.putExtra("Total",82);
        Character.putExtra("Search","people/");
        Character.putExtra("attributes",character_attributes);
        startActivity(Character);
    }

    public void openSpecies(View view) {
        Intent Species = new Intent(MainActivity.this,QuestionActivity.class);
        Species.putExtra("Total",37);
        Species.putExtra("Search","species/");
        Species.putExtra("attributes",species_attributes);
        startActivity(Species);
    }

    public void openVehicles(View view) {
        Intent Vehicles = new Intent(MainActivity.this,QuestionActivity.class);
        Vehicles.putExtra("Total",39);
        Vehicles.putExtra("Search","vehicles/");
        Vehicles.putExtra("attributes",vehicle_attributes);
        startActivity(Vehicles);
    }

    public void openStarShips(View view) {
        Intent Starships = new Intent(MainActivity.this,QuestionActivity.class);
        Starships.putExtra("Total",36);
        Starships.putExtra("Search","starships/");
        Starships.putExtra("attributes",starships_attributes);
        startActivity(Starships);
    }

    public void openFilms(View view) {
        Intent Films= new Intent(MainActivity.this,QuestionActivity.class);
        Films.putExtra("Total",6);
        Films.putExtra("Search","films/");
        Films.putExtra("attributes",films_attributes);
        startActivity(Films);
    }
}