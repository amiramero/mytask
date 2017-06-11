package com.example.amira.movies;

/**
 * Created by amira on 4/29/2016.
 *
 */
public class Recipes {

    private String title;

    private String Subcategory;

    private String poster;
private  String category;
    private String voteAverage;


    private int id;
    private String Description;
    private  String Ingredients;
    private String Instructions;

    public String getInstructions() {
        return Instructions;
    }

    public void setInstructions(String instructions) {
        Instructions = instructions;
    }

    public String getCategory() {
        return category;

    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubcategory() {
        return Subcategory;
    }

    public void setSubcategory(String subcategory) {
        Subcategory = subcategory;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }



    public String getIngredients() {
        return Ingredients;
    }

    public void setIngredients(String ingredients) {
        Ingredients = ingredients;
    }




    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }




    public String getPoster() {
        return poster;
    }


    public void setPoster(String poster) {
        this.poster = poster;
    }


    public String getVoteAverage() {
        return voteAverage;
    }


    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }



    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


}
