package com.metacoders.nextfly;

public class Model_for_Article_row {


    public  static  final  int IMAGE_TYPE= 1 ;
    String name, id;
    private int type ;


    public  Model_for_Article_row (String name, String id){
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }



}
