package data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

public class PetArrayList {
    private static PetArrayList instance;
    public ArrayList<String> users;
    public String id;

    public ArrayList <Pet> arrayPetList;

    private PetArrayList(){
        arrayPetList = new ArrayList<>();
        users = new ArrayList<>();
    }

    public static PetArrayList getInstance(){ //Создан ли список?
        if (null == instance){
            instance = new PetArrayList();
        }
        return instance;
    }
}