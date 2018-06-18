package data;

import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;

public class PetTreeSet {
    public static TreeSet<Integer> numberSet = new TreeSet<>();
    public static TreeSet<Integer> numberCat = new TreeSet<>();
    public static TreeSet<Integer> numberDog = new TreeSet<>();

    public static void addtoTreeSet(Integer a){
        numberSet.add(a);
    }

    public static void addCatTreeSet(int a) { numberCat.add(a); }
    public static void addDogTreeSet(int a) { numberDog.add(a); }

    public static void removeOfTreeSet(int number){
        numberSet.remove(number);
    }

  /*  public static int getFirstValueTreeSet(){
        int value = numberSet.first();
        return value;
    }

    public static int getLastValueTreeSet(){
        int value = numberSet.last();
        return value;
    }

    public static int getNextValueTreeSet(int number){
        if(numberSet.contains(numberSet.higher(number))) {
            int value = numberSet.higher(number);
            return value;
        }
        else return number;
    } */

    public static int getSizeTreeSet(){
        int value = numberSet.size();
        return value;
    }
}
