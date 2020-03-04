package com.example.news7;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.news7.Adapter.RecyclerAdapter;
import com.example.news7.Model.NewsModel;
import com.example.news7.volley.MySingleton;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class BuisnessActivity extends AppCompatActivity {
 RequestQueue mqueue;
 RecyclerView recyclerView;
 ArrayList<NewsModel> newslist=new ArrayList<>();
 private RecyclerAdapter recyclerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerAdapter=new RecyclerAdapter(this,newslist);
        recyclerView.setAdapter(recyclerAdapter);

        mqueue= MySingleton.getInstance(this).getRequestQueue();
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Loading..");
        pd.show();
        String url="https://news.abplive.com/business/feed";
       // String url="https://www.abplive.in/bussiness/feed";
        StringRequest mrequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                     pd.dismiss();
                //The parse(String html) method parses the input HTML into a new Document.
                //This document object can be used to traverse and get details of the html dom.
                Document document= Jsoup.parse(response);

                //this will tell from which element we have to select the data upto its closing tag
                Elements itemelement=document.select("item");
                for (int i=0;i<itemelement.size();i++)
                {
                    Element item=itemelement.get(i);
                    /*String title=item.child(0).text();
                    String desc=item.child(3).text();
                    String imglink=item.child(4).attr("url").toString();
                    String pubdate=item.child(5).text();*/

                    String title=item.child(0).text().replace("<![CDATA[","").replace("]]>","");
                    String desc=item.child(7).text().replace("&lt;strong&gt;","").replace("&lt;/strong&gt;","");
                    String imglink=item.child(9).attr("url").toString();
                    String pubdate=item.child(3).text();
                    NewsModel model=new NewsModel(title,desc,imglink,pubdate);
                    newslist.add(model);

                }
                recyclerAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MySingleton.getInstance(this).addToRequestQueue(mrequest);
    }
}
