package com.example.amira.movies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by amira on 4/6/2016.
 */
public class GridAdapter extends BaseAdapter {



    private Context mContext;
    ArrayList<Recipes> RecipeList;

    public GridAdapter(Context c, ArrayList<Recipes> paths) {
        mContext = c;
        this.RecipeList = paths;
    }

    public int getCount() {
        return RecipeList.size();
    }

    public Object getItem(int position) {
        return RecipeList.get(position);
    }


    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {

            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.griditem, parent, false);
            holder.title=(TextView)convertView.findViewById(R.id.title);
            holder.rate=(TextView)convertView.findViewById(R.id.rate);
            holder.category=(TextView)convertView.findViewById(R.id.category);
            holder.poster = (ImageView) convertView.findViewById(R.id.imageView2);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
 holder.title.setText("Title: "+ RecipeList.get(position).getTitle());
        holder.rate.setText("Rate: "+ RecipeList.get(position).getVoteAverage());
        holder.category.setText("Category: "+ RecipeList.get(position).getCategory());
        Picasso.with(mContext).load(
                RecipeList.get(position).getPoster()+"?h=640&w=640")
                .into(holder.poster);

        return convertView;
    }
    public void filter(ArrayList<Recipes>newlist)
    {RecipeList=new ArrayList<>();
        RecipeList.addAll(newlist);
        notifyDataSetChanged();

    }

    private class ViewHolder {
        ImageView poster;
        TextView title,category,rate;
    }


}
