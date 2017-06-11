package com.example.amira.movies;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

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

public class RecipeFragment extends Fragment {
String data;
    GridView grid;
    ArrayList<Recipes> Recipelist;
    ArrayList<Recipes> Recipelist2;
    private GridAdapter gridAdapter;
    final String RESULTS = "Results";
    final String POSTER_PATH = "PhotoUrl";
    final String Category = "Category";
    final String Subacategoy = "Subcategory";
    final String ORIGINAL_TITLE = "Title";
    final String VOTE_AVG = "StarRating";
    final String id = "RecipeID";
    String title,cat,rate,image;
    ArrayList<Recipes>searchresult;
    int ids;

    public RecipeFragment() {
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        Boolean isInternetPresent = false;
        //InternetConnection cd;
        setHasOptionsMenu(true);

        Recipelist = new ArrayList<>();
        Recipelist2 = new ArrayList<>();
        searchresult=new ArrayList<>();
        Log.d("recipe", "inside reipe fragment");

    }







    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        grid = (GridView) rootView.findViewById(R.id.gridView1);


        TaskManag taskManag = new TaskManag();
        taskManag.execute();

        //grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = getBundle(position);
                ((Call) (getActivity())).get(bundle);


                if (MainActivity.mTwoPane == false) {
                    Intent intent = new Intent(getActivity(), Details.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });

        return rootView;
    }

    @NonNull
    private Bundle getBundle(int position) {
        Bundle bundle = new Bundle();

        bundle.putInt("id", Recipelist.get(position).getId());//id
        bundle.putInt("postion",position);
        Log.e("GRID", bundle.toString());
        return bundle;
    }
    SearchView searchView;
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        ;
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.search_bar);
        searchView = new SearchView(((MainActivity) getActivity()).getSupportActionBar().getThemedContext());
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(item, searchView);



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.action_fav)
        {
            Database d = new Database(getActivity());
            Cursor data = d.Fetch_all();
            if (data != null) {

                Recipelist.clear();
                Recipes MyRecipe;
                do {
                    MyRecipe = new Recipes();
                    MyRecipe.setPoster(data.getString(data.getColumnIndex("poster")));
                    MyRecipe.setTitle(data.getString(data.getColumnIndex("title")));
                    MyRecipe.setCategory(data.getString(data.getColumnIndex("category")));
                    MyRecipe.setVoteAverage(data.getString(data.getColumnIndex("vote_average")));
                    MyRecipe.setId(data.getInt(data.getColumnIndex("recipeid")));
                    Recipelist.add(MyRecipe);
                    grid.setAdapter(new GridAdapter(getActivity(), Recipelist));
                    Toast.makeText(getActivity(), "new adapter", Toast.LENGTH_SHORT).show();
                } while (data.moveToNext());

            } else {
                Toast.makeText(getActivity(), "null data", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        if(id==R.id.offline) {
            Database database = new Database(getActivity());
            database.offline(image, title, cat, rate, ids);
            return true;
        }
        if(id==R.id.search_bar)
        {
          /*  data=searchView.getQuery().toString();
            TaskSearch taskSearch=new TaskSearch();
            taskSearch.execute();*/
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {


                    return true;
                }

                @Override
                public boolean onQueryTextChange(String s) {

                data=searchView.getQuery().toString();
                TaskSearch taskSearch=new TaskSearch();
                taskSearch.execute();

                for(Recipes x:Recipelist2)
                {


                      String name=x.getTitle();
                    if(name.contains(s))
                    {

                        searchresult.add(x);
                    }
                }


                    gridAdapter.filter(searchresult);
               /* gridAdapter.notifyDataSetChanged();*/
                    return true;
                }
            });

        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    public class TaskManag extends AsyncTask<String, Void, ArrayList<Recipes>> {//a3ed tany async task
// awou

        @Override
        protected ArrayList<Recipes> doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
// ham3l 3 asnk task one for movie //done  ll reviews and le trial

            try {


                String api_key = "da84c4afea059e8ae06f74c450ea8793";

                Constants.myurl =
                        "http://api2.bigoven.com/recipes?api_key=axV15293h59oU9Z853fw48CmI1H1Js" ;
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
                Constants.fetchrecipe = buffer.toString();
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
                Log.e("TAG", Constants.fetchrecipe);
                return getrecipe(Constants.fetchrecipe);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        public ArrayList<Recipes> getrecipe(String mo)
                throws JSONException {
//            Constants.postersList.clear();

            JSONObject obj = new JSONObject(mo);
            JSONArray jsonArray = obj.getJSONArray(RESULTS);

            Recipes myrecipe;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject movieData = jsonArray.getJSONObject(i);
                String moviePosterPath = movieData.getString(POSTER_PATH);

                myrecipe = new Recipes();
                myrecipe.setId(movieData.getInt(id));
                ids=movieData.getInt(id);
                myrecipe.setTitle(movieData.getString(ORIGINAL_TITLE));
                title=movieData.getString(ORIGINAL_TITLE);
                myrecipe.setPoster(moviePosterPath);
                image=moviePosterPath;
                myrecipe.setCategory(movieData.getString(Category));
                cat=movieData.getString(Category);
               myrecipe.setSubcategory(movieData.getString(Subacategoy));
                myrecipe.setVoteAverage(movieData.getString(VOTE_AVG));
                rate=movieData.getString(VOTE_AVG);

                Recipelist.add(myrecipe);

            }
            return Recipelist;
        }

        @Override
        protected void onPostExecute(ArrayList<Recipes> posterPaths) {
            if (posterPaths != null) {

                gridAdapter = new GridAdapter(getActivity(), posterPaths);
                grid.setAdapter(gridAdapter);
                if (MainActivity.mTwoPane) {
                    ((Call) getActivity()).get(getBundle(0));//3shan ageb awoul movie
                }

            }
        }
    }


    public interface Call {
        void get(Bundle bundle);
    }

    public interface Type {
        int get_Type();
    }
    public class TaskSearch extends AsyncTask<String, Void, ArrayList<Recipes>> {

        @Override
        protected ArrayList<Recipes> doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            try {



                String urlsearch=
                        "http://api2.bigoven.com/recipes?pg=1&rpp=25&title_kw="+data+"&api_key=axV15293h59oU9Z853fw48CmI1H1Js";
                Log.d("url22", "d" + urlsearch);
                URL url = new URL(urlsearch);
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
                Log.e("me", Constants.fetchrecipe2);
                return getrecipe2(Constants.fetchrecipe2);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        public ArrayList<Recipes> getrecipe2(String mo)
                throws JSONException {
//            Constants.postersList.clear();

            JSONObject obj = new JSONObject(mo);
            JSONArray jsonArray = obj.getJSONArray(RESULTS);

            Recipes myrecipe2;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject movieData = jsonArray.getJSONObject(i);
                String moviePosterPath = movieData.getString(POSTER_PATH);

                myrecipe2 = new Recipes();
                myrecipe2.setId(movieData.getInt(id));
                myrecipe2.setTitle(movieData.getString(ORIGINAL_TITLE));
                myrecipe2.setPoster(moviePosterPath);
                myrecipe2.setCategory(movieData.getString(Category));
                myrecipe2.setSubcategory(movieData.getString(Subacategoy));
                myrecipe2.setVoteAverage(movieData.getString(VOTE_AVG));

                Recipelist2.add(myrecipe2);

            }
            return Recipelist2;
        }

        @Override
        protected void onPostExecute(ArrayList<Recipes> posterPaths) {
            if (posterPaths != null) {

                gridAdapter = new GridAdapter(getActivity(), posterPaths);
                grid.setAdapter(gridAdapter);
                //gridAdapter.filter(searchresult);


            }
        }
    }










}

