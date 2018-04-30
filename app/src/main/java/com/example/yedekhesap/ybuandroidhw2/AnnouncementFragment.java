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

public class AnnouncementFragment extends Fragment {
    private static final String TAG = "AnnouncementFragment";
    public ListView announces;
    public ArrayList<String> announcement;
    public ArrayList<String> links;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.announcements_fragment,container,false);
        announces =(ListView) view.findViewById(R.id.announceList);
        new AnnouncementYBU().execute();
        return view;
    }

    private class AnnouncementYBU extends AsyncTask <Void,Void,Void>{

        String url = "http://www.ybu.edu.tr/muhendislik/bilgisayar";



        @Override
        protected void onPreExecute(){
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... voids) {

            try{
                announcement = new ArrayList<String>();
                links = new ArrayList<String>();
                Iterator<Element> iterator = Jsoup.connect(url).get().select("div.caContent").first().select("div.cncItem").iterator();

                while(iterator.hasNext()){
                    Element div =iterator.next();
                    announcement.add(div.text());
                    System.out.println("Value 1: " + div.select("a").attr("href"));
                    links.add(div.select("a").attr("href"));



                }

            } catch (IOException e) {
                e.printStackTrace();
            }



            return null;
        }

        protected void onPostExecute(Void aVoid){

            ArrayAdapter adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,announcement);
            announces.setAdapter(adapter);
            announces.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {


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
