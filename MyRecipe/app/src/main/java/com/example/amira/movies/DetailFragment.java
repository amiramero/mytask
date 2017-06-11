package com.example.amira.movies;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class DetailFragment extends android.support.v4.app.Fragment {////////
    ListView list;
    String image = "";
    Context mContext;

    Button fav,inst,remove,btn_ingredient;
    int x;
String name;
    String categry;
    String descriptions;
    String Ingredients;
    String Instruction;
    String subcategorys;
    String starrate;
    Database data;

    Bundle bundle;
    View rootView;
    String title;
    final String POSTER_PATH = "PhotoUrl";
    final String Category = "Category";
    final String Subacategoy = "Subcategory";
    final String ORIGINAL_TITLE = "Title";
    final String VOTE_AVG = "StarRating";
    final String Description = "Description";
    final String INSTRUCTIONS="Instructions";
    final String Ingredient="Ingredients";
    final String id = "RecipeID";
        ArrayList<String>names;
     TextView overviewTV;
     TextView description;
     TextView rateTV;
     TextView titleTV;
     TextView subcategory;
     ImageView poster;

    ArrayList<Recipes> ingredientlist;
    public DetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();

        data = new Database(mContext);
        ingredientlist =new ArrayList<>();
        names =new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        Log.d("detailsFragment", "inside dtails fragment");
         rootView = inflater.inflate(R.layout.fragment_details, container, false);
        //list = (ListView) rootView.findViewById(R.id.lv_trailers_reviews);
        LayoutInflater inflae = getActivity().getLayoutInflater();

       // View v = inflae.inflate(R.layout.listheader, list, false);
       // list.addHeaderView(v);
        data d=new data();
        d.execute();
        overviewTV   = (TextView) rootView.findViewById(R.id.tv_category);
        description  = (TextView) rootView.findViewById(R.id.tv_desc);
        rateTV       = (TextView) rootView.findViewById(R.id.tv_rate);
        titleTV      = (TextView) rootView.findViewById(R.id.tv_title);
        subcategory  = (TextView) rootView.findViewById(R.id.tv_subcategory);
         poster       = (ImageView) rootView.findViewById(R.id.img_poster);
        fav=(Button)rootView.findViewById(R.id.favourite);
        inst=(Button)rootView.findViewById(R.id.instruction);
        remove=(Button)rootView.findViewById(R.id.remove);
        btn_ingredient=(Button)rootView.findViewById(R.id.ingerdint);
        bundle = getArguments();


        if (bundle != null)
        {

            x = bundle.getInt("id");
            //pos =bundle.getInt("postion");

        }
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (data.ifexist(title)) {
                    Toast.makeText(getActivity(), "Already exist", Toast.LENGTH_SHORT).show();
                } else
                    data.add(image,title,categry,descriptions,starrate,x,subcategorys,Instruction);
           Log.e("data",data.toString());
            }


        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.delete_recipedata(title);
                Toast.makeText(getActivity(),"removed from favourite ", Toast.LENGTH_LONG).show();
            }
        });
        inst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getActivity(),instructions.class);
                i.putExtra("inst",Instruction);
                startActivity(i);
            }
        });
        btn_ingredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                names.add(name);
                Intent i=new Intent(getActivity(),Ingredientss.class);
                i.putStringArrayListExtra("name",names);
                startActivity(i);
            }
        });

        return rootView;
    }


    public class data extends AsyncTask<String, Void, ArrayList<Recipes>> {//a3ed tany async task
// awou

        @Override
        protected ArrayList<Recipes> doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;


            try {

                Constants.myurl ="http://api2.bigoven.com/recipe/"+bundle.getInt("id")+"?api_key=axV15293h59oU9Z853fw48CmI1H1Js";

                Log.d("url", "movie url" + Constants.myurl);
                URL url = new URL(Constants.myurl);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();


                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {

                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                Constants.fetchrecipe2 = buffer.toString();
            } catch (IOException e) {

                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        // Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }

            }


            try {
                Log.d("dr", Constants.fetchrecipe2);
                return getrecipe(Constants.fetchrecipe2);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        public ArrayList<Recipes> getrecipe(String mo)
                throws JSONException {
//            Constants.postersList.clear();

            JSONObject obj = new JSONObject(mo);

            Recipes myrecipe;

                String moviePosterPath = obj.getString(POSTER_PATH);

                myrecipe = new Recipes();
                Log.d("ty",obj.getString(ORIGINAL_TITLE));
               title=obj.getString(ORIGINAL_TITLE);
                categry=obj.getString(Category);
                image=moviePosterPath;
                subcategorys =obj.getString(Subacategoy);
            Instruction=obj.getString(INSTRUCTIONS);
            Log.e("inst",Instruction);
            descriptions=obj.getString(Description);
            starrate=obj.getString(VOTE_AVG);
            Log.e("yu",starrate);
            Ingredients=obj.getString(Ingredient);
            JSONArray jsonArray = obj.getJSONArray(Ingredient);


            for (int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject data = jsonArray.getJSONObject(i);
                myrecipe.setIngredients(data.getString("Name"));
    name=data.getString("Name");
            }
            Log.e("ing",name);


            ingredientlist.add(myrecipe);



            return ingredientlist;
        }

        @Override
        protected void onPostExecute(ArrayList<Recipes> posterPaths) {
            if (posterPaths != null)
            {

                overviewTV.setText( "  Category Name : "+categry);
                if(descriptions.equals(""))
                {
                    description.setText("  There is no Description for this food");
                }
                else
                    {
                    description.setText("  Description  : " +descriptions);
                     }
                titleTV.setText( "  Title : "+title);

                rateTV.setText( "  Rate: "+starrate);

                subcategory.setText("  Subcategory : "+subcategorys);

                Picasso.with(getActivity()).load( image+"?h=640&w=640").into(poster);

            }
        }
    }


}