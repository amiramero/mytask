package com.example.amira.movies;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by amira on 4/28/2016.
 *
 */
public class Database extends SQLiteOpenHelper {
    static String name = "recipedatabase";
    SQLiteDatabase db;
    Context context;
    public Database(Context context) {
        super(context, name, null, 7);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Recipes (id integer primary key, poster text, title text, category text, vote_average text, description text, recipeid integer, instructions text, subcategory text)");
        db.execSQL("create table favourite (id2 integer primary key, poster text, title text, category text, vote_average text, recipeid2 integer, id22 integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists Recipes");
        db.execSQL("Drop table if exists favourite");
        onCreate(db);
    }

    public void add(String poster, String title, String category, String desc, String rate, int id,String subcategory,String instruction) {
        ContentValues content = new ContentValues();
        content.put("poster", poster);
        content.put("title", title);
        content.put("category", category);
        content.put("vote_average", rate);
        content.put("description", desc);
        content.put("recipeid", id);
        content.put("instructions",instruction);
        content.put("subcategory",subcategory);

        db = getWritableDatabase();
        long i = db.insert("Recipes", null, content);
        if(i == 0)
            Toast.makeText(context, "not inserted", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, " inserted", Toast.LENGTH_SHORT).show();

        db.close();

    }
    public void offline(String poster, String title, String category, String rate, int id) {
        ContentValues content = new ContentValues();
        content.put("poster", poster);
        content.put("title", title);
        content.put("category", category);
        content.put("vote_average", rate);
        content.put("recipeid", id);


        db = getWritableDatabase();
        long i = db.insert("favourite", null, content);
        if(i == 0)
            Toast.makeText(context, "not inserted", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, " inserted", Toast.LENGTH_SHORT).show();

        db.close();

    }

    public Boolean ifexist(String Movietitle) {
        db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Recipes where title=?", new String[]{Movietitle});
        if (cursor.getCount() != 0) {
            db.close();
            return true;
        }
        return false;
    }

    public void delete_recipedata(String Movietitle) {
        db = getWritableDatabase();
        db.delete("Recipes", "title like ?", new String[]{Movietitle});
        db.close();

    }

    public Cursor Fetch_all() {
        db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Recipes", null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

        } else {
            cursor = null;
        }
        db.close();
        return cursor;
    }

}

