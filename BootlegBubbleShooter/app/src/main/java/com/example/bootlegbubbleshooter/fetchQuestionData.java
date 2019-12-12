package com.example.bootlegbubbleshooter;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

class fetchQuestionData extends AsyncTask<Void,Void,Void>{
    String data ="";
    String dataParsed = "";
    String singleParsed ="";
    String correctAnswerString = "";
    char correctAnswer;

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL("https://api.myjson.com/bins/c0yoo");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while(line != null){
                line = bufferedReader.readLine();
                data = data + line;
            }


            JSONArray qfact = new JSONArray(data);
            //Random rand = new Random();
            //int randInt = rand.nextInt(qfact.length());
            //String number = Integer.toString(randInt+1);
            for(int i =0 ;i < qfact.length(); i++){
                //if(i==randInt)
                //{
                    JSONObject JO = (JSONObject) qfact.get(i);
                    singleParsed = JO.get("Q"+Integer.toString(i+1)) + "\n";
                    correctAnswerString = JO.get("Answer") + "\n";
                    correctAnswer = correctAnswerString.charAt(0);
                    dataParsed = dataParsed + singleParsed + "\n";
                //}
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public char getCorrectAnswer()
    {
        return correctAnswer;
    }


    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Levels.q_data.setText(dataParsed);
    }
}
