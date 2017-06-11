package com.example.amira.movies;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.MenuItem;

public class MainActivity extends ActionBarActivity implements RecipeFragment.Call {



   public static boolean mTwoPane = false;
public static String data;
    public SearchView searchView;
RecipeFragment.TaskSearch taskSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      //  setHasOptionsMenu(true);
//taskSearch=new RecipeFragment.TaskSearch();
        if (findViewById(R.id.movie_detail_container) != null) {
            mTwoPane = true;

            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()

                        .replace(R.id.movie_detail_container, new DetailFragment())
                        .commit();
            }
        } else {
            mTwoPane = false;

                getSupportFragmentManager().beginTransaction()

                        .replace(R.id.container, new RecipeFragment())
                        .commit();
        }
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//       // getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        return super.onOptionsItemSelected(item);
//    }


   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        final MenuItem myActionMenuItem = menu.findItem( R.id.search_bar);
        SearchManager manager=(SearchManager)getSystemService(Context.SEARCH_SERVICE);
       searchView = (SearchView) myActionMenuItem.getActionView();
        if (null != searchView) {
            searchView.setSearchableInfo(manager
                    .getSearchableInfo(getComponentName()));
            searchView.setIconifiedByDefault(false);
        }

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {
                // this is your adapter that will be filtered
                return true;
            }

            public boolean onQueryTextSubmit(String query) {
                //Here u can get the value "query" which is entered in the search box.
                data=searchView.getQuery().toString();
                return true;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);

        return super.onCreateOptionsMenu(menu);
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void get(Bundle bundle) {
        if (mTwoPane) {
            DetailFragment fragment = new DetailFragment();
            fragment.setArguments(bundle);
            Log.e("TAF", "inside get" + bundle.toString());
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_container, fragment)
                    .commit();
        } else {

            RecipeFragment fragments=new RecipeFragment();
            fragments.setArguments(bundle);
            Log.e("TAF", "inside get" + bundle.toString());
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragments)
                    .commit();
        }
    }
}
