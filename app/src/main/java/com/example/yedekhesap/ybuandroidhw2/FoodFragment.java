package com.example.yedekhesap.ybuandroidhw2;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Engin on 29.04.2018.
 */

public class FoodFragment extends Fragment {
    private static final String TAG = "FoodFragment";
    TextView foodList;
    URL url;
    ProgressDialog progressDialog;
    ArrayList<String> arraylist;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.food_fragment,container,false);
        foodList = (TextView) view.findViewById(R.id.foodList);
        new FoodSks().execute();
        return view;
    }
private class FoodSks extends AsyncTask <Void,Void,Void>{

    String sks;
    @Override
    protected void onPreExecute(){

        super.onPreExecute();

    }
    @Override
    protected Void doInBackground(Void... voids) {

        arraylist = new ArrayList<String>();

        try{
            url = new URL("http://ybu.edu.tr/sks");
            Document document = Jsoup.parse(url, 4000);
            Element table = document.select("table").first();
            Iterator<Element> iterator = table.select("td").iterator();

            iterator.next();
            while(iterator.hasNext()){
                arraylist.add(iterator.next().text());
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }
    @Override
    protected void onPostExecute(Void aVoid){

        String list = "";
        for(int i=0;i<arraylist.size();i++)
            list += "\n"+arraylist.get(i).toString();
        foodList.setText(list);

    }
}


}
