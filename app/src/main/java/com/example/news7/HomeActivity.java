package com.example.news7;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.androidquery.AQuery;
import com.example.news7.volley.MySingleton;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Locale;

import technolifestyle.com.imageslider.FlipperLayout;
import technolifestyle.com.imageslider.FlipperView;

public class HomeActivity extends AppCompatActivity {
    FlipperLayout  flipperLayout;
    RequestQueue mqueue;
    Button btnhome,btnworld,btnsport,btntelv,btnbusiness,btngad,btnlife,btnhealth;
    AQuery aQuery;
    ImageButton btnspeak;
    TextToSpeech textToSpeech;
    String tospeak="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);


        initcomponent();


        clicklistener();


        flipperLayout=findViewById(R.id.flipper);
        mqueue= MySingleton.getInstance(this).getRequestQueue();
     // getSupportActionBar().hide();
       // String url="https://hindi.oneindia.com/rss/hindi-news-fb.xml";
        String url="https://news.abplive.com/news/india/feed";
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Loading..");
        pd.show();
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
                  /*  String title=item.child(0).text();
                    String desc=item.child(3).text();
                    String imglink=item.child(4).attr("url").toString();
                    String pubdate=item.child(5).text();*/

                    final String title=item.child(0).text().replace("<![CDATA[","").replace("]]>","");
                    final String desc=item.child(7).text().replace("&lt;strong&gt;","").replace("&lt;/strong&gt;","");
                    final String imglink=item.child(9).attr("url").toString();
                    final String pubdate=item.child(3).text();
                    tospeak=tospeak.concat(title)+".               ";
                   if(i<6)
                   {
                       FlipperView view=new FlipperView(getBaseContext());
                       view.setImageUrl(imglink)
                               .setDescription(title+"                             ");
                       view.setImageScaleType(ImageView.ScaleType.FIT_XY);
                       view.setOnFlipperClickListener(new FlipperView.OnFlipperClickListener() {
                           @Override
                           public void onFlipperClick(FlipperView flipperView) {

                               AlertDialog.Builder ab = new AlertDialog.Builder(HomeActivity.this);
                               View itemView=LayoutInflater.from(HomeActivity.this).inflate(R.layout.alert_item,null);
                                final ImageView img12;
                                TextView title7,descrip,date;
                               img12=itemView.findViewById(R.id.imgview1);
                               title7=itemView.findViewById(R.id.titletxt);
                               descrip=itemView.findViewById(R.id.detailtxt);
                               date=itemView.findViewById(R.id.datetxt);
                               title7.setText(title);
                               descrip.setText(desc);
                               date.setText(pubdate);
                               img12.setImageResource(R.drawable.profile);
                               aQuery=new AQuery(HomeActivity.this);
                               aQuery.id(img12).image(imglink);
                               ab.setView(itemView);
                               ab.setTitle("News 7");
                               ab.show();

                           }
                       });
                       flipperLayout.addFlipperView(view);

                   }
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MySingleton.getInstance(this).addToRequestQueue(mrequest);
           textToSpeech=new TextToSpeech(this, new TextToSpeech.OnInitListener() {
               @Override
               public void onInit(int status) {
                  if (status==TextToSpeech.SUCCESS)
                  {
                      int result=textToSpeech.setLanguage(Locale.ENGLISH);
                      if (result== TextToSpeech.LANG_MISSING_DATA ||
                                   result ==textToSpeech.LANG_NOT_SUPPORTED)
                      {
                          Toast.makeText(HomeActivity.this, "This Language is not Supported", Toast.LENGTH_SHORT).show();
                      }
                      else
                      {
                          btnspeak.setEnabled(true);
                          textToSpeech.setPitch(0.8f);
                          textToSpeech.setSpeechRate(0.8f);
                         // speak(tospeak);
                      }
                  }
               }
           });

        btnspeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (!textToSpeech.isSpeaking()) {
                   Toast.makeText(HomeActivity.this, "Speaking ", Toast.LENGTH_SHORT).show();
                   speak(tospeak);
               }
               else {
                   Toast.makeText(HomeActivity.this, "Stop", Toast.LENGTH_SHORT).show();
                   textToSpeech.stop();
                  // textToSpeech.shutdown();
               }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("About us");
        menu.add("Contact us");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String title = item.getTitle().toString();
        switch (title)
        {
            case "About us":
                Toast.makeText(this, "About us", Toast.LENGTH_SHORT).show();
                break;
            case "Contact us":
                Toast.makeText(this, "Contact us", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void speak(String text)
    {
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.LOLLIPOP)
        {
            textToSpeech.speak(text,TextToSpeech.QUEUE_FLUSH,null,null);
        }
        else
        {
            textToSpeech.speak(text,TextToSpeech.QUEUE_FLUSH,null);
        }

    }
    void initcomponent()
    {
        btnhome=findViewById(R.id.btnprofile);
        btnworld=findViewById(R.id.btnworld);
        btnsport=findViewById(R.id.btnsports);
        btnbusiness=findViewById(R.id.btnbusiness);
        btngad=findViewById(R.id.btngadgets);
        btntelv=findViewById(R.id.btntelevision);
        btnlife=findViewById(R.id.btnlifestyle);
        btnhealth=findViewById(R.id.btnhealths);
        btnspeak=findViewById(R.id.speak);
    }
    void clicklistener()
    {
        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(HomeActivity.this, IndianActivity.class);
                startActivity(in);
            }
        });
        btnworld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(HomeActivity.this,WorldActivity.class);
                startActivity(in);
            }
        });
        btnsport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(HomeActivity.this,SportActivity.class);
                startActivity(in);
            }
        });
        btnbusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(HomeActivity.this,BuisnessActivity.class);
                startActivity(in);
            }
        });
        btntelv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(HomeActivity.this,TelevisionActivity.class);
                startActivity(in);
            }
        });
        btngad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(HomeActivity.this,GadgetsActivity.class);
                startActivity(in);
            }
        });
        btnlife.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(HomeActivity.this,LifeStyleActivity.class);
                startActivity(in);
            }
        });
        btnhealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(HomeActivity.this,HealthActivity.class);
                startActivity(in);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (textToSpeech !=null)
        {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }

   /* @Override
    protected void onResume() {
        super.onResume();
        if (textToSpeech !=null)
        {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }*/
}
