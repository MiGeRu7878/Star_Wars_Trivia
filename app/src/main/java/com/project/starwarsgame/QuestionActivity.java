package com.project.starwarsgame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuestionActivity extends AppCompatActivity {

    TextView questionTV;
    Random rand = new Random();
    Button button1,button2,button3;
    String[] attributes;
    int attribute_item;
    int item;
    int total;
    String search;
    int score = 0;
    String answer;
    ProgressBar progressBar;
    String Url = "https://swapi.dev/api/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        questionTV = findViewById(R.id.questionTV);
        progressBar = findViewById(R.id.progressBar);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button1.getText();

        progressBar.setMax(60000);
        progressBar.setProgress(60000);
        new CountDownTimer(60000,1000){

            @Override
            public void onTick(long l) {
                int myInt = ((int) l);
                progressBar.setProgress(myInt);
            }

            @Override
            public void onFinish() {
                button1.setClickable(false);
                button2.setClickable(false);
                button3.setClickable(false);

                AlertDialog.Builder alert = new AlertDialog.Builder(QuestionActivity.this);
                alert.setTitle("Game Over");
                alert.setMessage("Your final score is "+score);
                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent gameOver = new Intent(QuestionActivity.this,MainActivity.class);
                        startActivity(gameOver);
                    }
                });
                alert.create().show();

            }
        }.start();

        Bundle bundle = getIntent().getExtras();
        search = (String)bundle.get("Search");
        Url += search;
        attributes = bundle.getStringArray("attributes");
        total = (int) bundle.get("Total");
        getRandomItem();
    }
    public void getRandomItem()
    {
        System.out.println(Arrays.toString(attributes));
        attribute_item = rand.nextInt(attributes.length -1);
        attribute_item += 1;

        int random_item = rand.nextInt(total+1);

        int page;
        if (random_item == 0 ){
            page = 1;
        } else {
            page = (random_item + 9)/10;
        }
        String str_page = "?page="+page;
        Url += str_page;
        if(search.equals("films/")) {
            item = random_item -1;
        } else {
            item = Math.abs(random_item-((page-1)*10)-1);
        }
        fetchData();
    }
    public void fetchData()
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject star_wars_object = new JSONObject(response);

                    JSONArray people = star_wars_object.getJSONArray("results");
                    JSONObject person = people.getJSONObject(item);
                    JSONObject randomish = people.getJSONObject(Math.abs(item-1));
                    
                    String name = person.getString(attributes[0]);
                    questionTV.setText(String.format("What is %s's %s?",name, attributes[attribute_item]));
                    answer = person.getString(attributes[attribute_item]);
                    String randOption = randomish.getString(attributes[attribute_item]);
                    String option3;

                    if (answer.equals("n/a")){
                        option3 = "unknown";
                    } else {
                        option3 = "n/a";
                    }

                    if(randOption.equals(answer)){
                        randOption = randOption.substring(0,randOption.length()-1);
                    }

                    String[] answerOptions = new String[] {answer,randOption,option3};
                    List<String> strList = Arrays.asList(answerOptions);
                    Collections.shuffle(strList);
                    answerOptions = strList.toArray(new String[strList.size()]);
                    System.out.println(Arrays.toString(answerOptions));

                    button1.setText(answerOptions[0]);
                    button2.setText(answerOptions[1]);
                    button3.setText(answerOptions[2]);


                    Url = "https://swapi.dev/api/" + search;

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                questionTV.setText("error");

            }
        });
        queue.add(stringRequest);
    }
    public void button_clicked(View view) {
        Button b = (Button)view;
        String buttonText = b.getText().toString();
        checkAnswer(buttonText);
        getRandomItem();
    }
    public void checkAnswer(String option){
        if(option.equals(answer)){
            score += 1;
        } else {
            score -= 1;
        }
    }
}