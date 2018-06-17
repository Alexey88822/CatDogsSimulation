package data;

import machines.HabitatController;
import machines.HabitatView;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class Dog extends Pet implements Serializable{

    private static Image img;
    public static int periodDog = 1;

    public int getPeriodDog(){
        return periodDog;
    }

    public void setPeriodDog(int periodDog){
        this.periodDog=periodDog;
    }

    static {
        try {
            img = ImageIO.read(new File("resources/image/dog.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Dog(){

    }

    public Dog(int X, int Y, int identificator){
        super(X, Y, identificator);
    }
    // Экземпляр класса Graphics хранит параметры, необходимые для отрисовки
    public void paint(Graphics g){
        if(img != null){
            g.drawImage(img,this.getX(),this.getY(),img.getWidth(null),img.getHeight(null),null);
        }
    }

    @Override
    public void move() {
        Pet MinimumCatLife = null;
        int speed = 1;
        long minimum = 1000;
        for(int i=0;i<PetArrayList.getInstance().arrayPetList.size();i++){
            Pet pet = PetArrayList.getInstance().arrayPetList.get(i);
            if (pet instanceof Cat) {
                int id=pet.getIdentity();
                long TimeLife = PetHashMap.getTimeofBorn(id);
                if (TimeLife<minimum) minimum=TimeLife;
            }
        }
        for(int i=0;i<PetArrayList.getInstance().arrayPetList.size();i++){
            Pet pet = PetArrayList.getInstance().arrayPetList.get(i);
            int id = pet.getIdentity();
            long TimeLife = PetHashMap.getTimeofBorn(id);
            if (TimeLife==minimum) MinimumCatLife = pet;
        }
        if ((getX()!=MinimumCatLife.getX()&&(getY()!=MinimumCatLife.getY()))){
            if(getX()<MinimumCatLife.getX()){
                setX(getX()+speed);
                HabitatController.Repaint();
            }
            if(getX()>MinimumCatLife.getX()){
                setX(getX()-speed);
                HabitatController.Repaint();
            }
            if(getY()<MinimumCatLife.getY()){
                setY(getY()+speed);
                HabitatController.Repaint();
            }
            if(getY()>MinimumCatLife.getY()){
                setY(getY()-speed);
                HabitatController.Repaint();
            }
        }

    }
}
