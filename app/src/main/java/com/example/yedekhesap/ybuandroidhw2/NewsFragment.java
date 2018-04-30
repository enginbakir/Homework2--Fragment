package com.example.yedekhesap.ybuandroidhw2;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;


/**
 * Created by Engin on 29.04.2018.
 */

public class NewsFragment extends Fragment {
    private static final String TAG = "NewsFragment";
    public ListView newsList;
    public ArrayList<String> news ;
    public ArrayList<String> links ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_fragment,container,false);
        newsList =(ListView) view.findViewById(R.id.newsList);
        new NewsYBU().execute();
        return view;
    }

    private class NewsYBU extends AsyncTask <Void,Void,Void>{

        String url = "http://www.ybu.edu.tr/muhendislik/bilgisayar";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... voids) {


            try{
                news = new ArrayList<String>();
                links = new ArrayList<String>();
               Iterator<Element> iterator =  Jsoup.connect(url).get().select("div.cnContent").first().select("div.cncItem").iterator();

            while(iterator.hasNext()){
                Element element = iterator.next();
                news.add(element.text());
                links.add(element.select("a").attr("href"));
            }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
        @Override
        protected void onPostExecute(Void result) {

            ArrayAdapter adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,news);
            newsList.setAdapter(adapter);
            newsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,long id) {

                    String[] urls = new String[10000];
                    for(int i=0;i<links.size();i++){
                        urls[i]="http://www.ybu.edu.tr/muhendislik/bilgisayar/"+links.get(i).toString();
                    }

                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urls[position]));
                    startActivity(intent);

                }
            });

        }
    }
}
