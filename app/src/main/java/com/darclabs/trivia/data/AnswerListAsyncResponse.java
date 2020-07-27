package com.darclabs.trivia.data;

import com.darclabs.trivia.model.Question;

import java.util.ArrayList;
//This allows the list to be created asynchronously
public interface AnswerListAsyncResponse {

    void processFinished(ArrayList<Question> questionArrayList);


}
