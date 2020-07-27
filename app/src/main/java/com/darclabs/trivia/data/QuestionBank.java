package com.darclabs.trivia.data;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.darclabs.trivia.controller.AppController;
import com.darclabs.trivia.model.Question;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import static com.darclabs.trivia.controller.AppController.TAG;

public class QuestionBank {
    ArrayList<Question> questionArrayList = new ArrayList<>();
    private String url = "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";

    //Returns a question ArrayList
    public List<Question> getQuestions(final AnswerListAsyncResponse callback){
        //JsonArrayRequest to fetch the data for the questions.
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                (JSONArray) null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++){
                            //Try catch for error handling.
                            try {
                                Question question = new Question();
                                //This will grab the first item in each array (the questions).
                                question.setAnswer(response.getJSONArray(i).get(0).toString());
                                //This will grab the second item, the answers
                                question.setAnswerTrue(response.getJSONArray(i).getBoolean(1));
                                //Add question objects to list
                                questionArrayList.add(question);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        //If callback isn't empty, send the list to main activity
                        if(null != callback) callback.processFinished(questionArrayList);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        //Create instance of AppController to add a Json array request to the request queue
        AppController.getInstance().addToRequestQueue(jsonArrayRequest);
        //Returns the list of question objects
        return questionArrayList;
    }

}
