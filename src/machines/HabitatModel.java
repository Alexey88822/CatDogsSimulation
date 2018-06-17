package machines;

import data.*;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.Random;

public class HabitatModel { // обработка событий клавиатуры

    private double pCat; //Вероятность появления Cat
    private double pDog; //Вероятность появления Dog
    public static long time = 0;
    private int timeCat; //Период появления Cat
    private int timeDog; //Период появления Dog
    private int timeCatLife;
    private int timeDogLife;
    static int amountOfG = 0;
    static int amountOfL = 0;
    static PetTreeSet tree;
    static CatAI catAI = new CatAI();
    static DogAI dogAI = new DogAI();

    public HabitatModel(double pCat, double pDog, int timeCat, int timeDog, int timeCatLife, int timeDogLife) {
        this.pCat = pCat;
        this.pDog = pDog;
        this.timeCat = timeCat;
        this.timeDog = timeDog;
        this.timeCatLife = timeCatLife;
        this.timeDogLife = timeDogLife;
        dogAI.start();
        catAI.start();
    }

    void update(long t) {
        synchronized (PetArrayList.getInstance().arrayPetList) {
            for (int j = 0; j != 2; j++) {
                for (int i = 0; i < PetArrayList.getInstance().arrayPetList.size(); i++) {
                    Pet hh = PetArrayList.getInstance().arrayPetList.get(i);
                    int currentvalue = hh.getIdentity();
                    long timeborn = PetHashMap.getTimeofBorn(currentvalue);
                    if (PetArrayList.getInstance().arrayPetList.get(i) instanceof Cat) {
                        if (t - timeborn >= Long.parseLong(HabitatView.timeCatLife.getText())) {
                            // amountOfG--;
                            PetHashMap.hashmap.remove(currentvalue);
                            PetTreeSet.removeOfTreeSet(currentvalue);
                            PetArrayList.getInstance().arrayPetList.remove(i);
                            break;
                        }
                    }
                    if (PetArrayList.getInstance().arrayPetList.get(i) instanceof Dog) {
                        if (t - timeborn >= Long.parseLong(HabitatView.timeDogLife.getText())) {
                            // amountOfL--;
                            PetHashMap.hashmap.remove(currentvalue);
                            PetTreeSet.removeOfTreeSet(currentvalue);
                            PetArrayList.getInstance().arrayPetList.remove(i);
                            break;
                        }
                    }
                }
            }
            pCat = Double.parseDouble(HabitatView.CatP.getText());
            timeCatLife = Integer.parseInt((HabitatView.timeCatLife.getText()));
            HabitatController.updateSliderCat();
            if (t % timeCat == 0) { //Каждые timeCat секунд
                if (pCat > (double) Math.random()) { // Если прошло по вероятности
                    amountOfG++;
                    boolean flag = true;
                    int random;
                    int first = 0;
                    int second = 1000;
                    do {
                        int p = 0;
                        random = first + (int) (Math.random() * second);
                        for (int i = 0; i < PetArrayList.getInstance().arrayPetList.size(); i++, p++) {
                            Pet pet = PetArrayList.getInstance().arrayPetList.get(i);
                            if (pet.getIdentity() != random) continue;
                            else {
                                break;
                            }
                        }
                        if (p == PetArrayList.getInstance().arrayPetList.size()) flag = false;
                    } while (flag);
                    Pet rb = new Cat(100 + (int) ((Math.random() * 740)), 100 + (int) ((Math.random() * 400)), random);
                    PetArrayList.getInstance().arrayPetList.add(rb);
                    PetTreeSet.addtoTreeSet(random);
                    PetHashMap.hashmap.put(random, t);
                }
            }
            pDog = Double.parseDouble(HabitatView.DogP.getText());
            timeDogLife = Integer.parseInt((HabitatView.timeDogLife.getText()));
            HabitatController.updateSliderDog();
            if (t % timeDog == 0) { //Каждые timeDog секунд
                if (pDog > (double) Math.random()) { //Если прошло по вероятности
                    amountOfL++;
                    boolean flag = true;
                    int random;
                    int first = 1001;
                    int second = 1000;
                    do {
                        int p = 0;
                        random = first + (int) (Math.random() * second);
                        for (int i = 0; i < PetArrayList.getInstance().arrayPetList.size(); i++, p++) {
                            Pet pet = PetArrayList.getInstance().arrayPetList.get(i);
                            if (pet.getIdentity() != random) continue;
                            else {
                                break;
                            }
                        }
                        if (p == PetArrayList.getInstance().arrayPetList.size()) flag = false;
                    } while (flag);
                    Pet rb = new Dog(150 + (int) ((Math.random() * 740)), 10 + (int) ((Math.random() * 400)), random);
                    PetArrayList.getInstance().arrayPetList.add(rb);
                    PetTreeSet.numberSet.add(random);
                    PetHashMap.hashmap.put(random, t);
                }
            }
        }
    }

    static void pauseCatAI (){
        if (!catAI.paused){
            catAI.paused = true;
        }
    }
    static void beginCatAI(){
        if (catAI.paused){
            synchronized (catAI.obj){
                catAI.paused = false;
                catAI.obj.notify();
            }
        }
    }
    static void pauseDogAI (){
        if (!dogAI.paused){
            dogAI.paused = true;
        }
    }
    static void beginDogAI(){
        if (dogAI.paused){
            synchronized (dogAI.obj){
                dogAI.paused = false;
                dogAI.obj.notify();
            }
        }
    }

    public double getpCat() {
        return pCat;
    }

    public void setpCat(double pCat) {
        this.pCat = pCat;
    }

    public double getpDog() {
        return pDog;
    }

    public void setpDog(double pDog) {
        this.pDog = pDog;
    }

    public int getTimeCat() {
        return timeCat;
    }

    public int getTimeDogLife(){
        return timeDogLife;
    }

    public int getTimeCatLife(){
        return timeCatLife;
    }

    public void setTimeCatLife(int timeCatLife){
        this.timeCatLife = timeCatLife;
    }

    public void setTimeDogLife(int timeDogLife){
        this.timeDogLife = timeDogLife;
    }

    public void setTimeCat(int timeCat) {
        this.timeCat = timeCat;
    }

    public int getTimeDog() {
        return timeDog;
    }

    public void setTimeDog(int timeDog) {
        this.timeDog = timeDog;
    }
}