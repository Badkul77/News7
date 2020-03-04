package com.example.news7.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.news7.Model.NewsModel;
import com.example.news7.R;
import com.example.news7.volley.MySingleton;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyviewHolder> {
   private Context ctx;
   private ArrayList<NewsModel> nlist;
private MySingleton mySingleton;
private ImageLoader imageLoader;
    public RecyclerAdapter(Context ctx, ArrayList<NewsModel> nlist) {
        this.ctx = ctx;
        this.nlist = nlist;
        mySingleton=MySingleton.getInstance(ctx);
        imageLoader=mySingleton.getImageLoader();
    }

    @NonNull
    @Override
    public RecyclerAdapter.MyviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v= LayoutInflater.from(ctx.getApplicationContext()).inflate(R.layout.news_item,viewGroup,false);
        return new MyviewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerAdapter.MyviewHolder myviewHolder, int i) {
     NewsModel newsModel=nlist.get(i);
     myviewHolder.title.setText(newsModel.getTitle());
        myviewHolder.desc.setText(newsModel.getDescription());
        myviewHolder.pubdate.setText(newsModel.getPubdate());

        String imgurl=newsModel.getImgurl();
        if(imgurl!=null)
        {
            imageLoader.get(imgurl, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {

                    myviewHolder.img.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }



    }

    @Override
    public int getItemCount() {
        return nlist.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView title;
        private TextView desc;
        private TextView pubdate;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.imgview);
            title=itemView.findViewById(R.id.titletxt);
            desc=itemView.findViewById(R.id.detailtxt);
            pubdate=itemView.findViewById(R.id.datetxt);
        }
    }
}
