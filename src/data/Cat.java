package data;

import machines.HabitatController;
import machines.HabitatView;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class Cat extends Pet implements Serializable {

    private static Image img;
    boolean flag=true;
    public static int periodCat = 1;

    public int getPeriodCat(){
        return periodCat;
    }

    public Cat(){

    }

    public void setPeriodCat(int periodCat){
        this.periodCat=periodCat;
    }

    static {
        try {
            img = ImageIO.read(new File("resources/image/cat.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Cat(int X, int Y, int identificator){
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
        int speed=5;
        if ((getY()<=25)) flag = false;
        if ((getY()>25)&&(getX()>100)&&(flag==true)){
                setY(getY()-speed);
                HabitatController.Repaint();
        }
        if ((getY()<=25)&&(getX()>25)){
                setX(getX()-speed);
                HabitatController.Repaint();
        }
        if ((getX()<=25)&&(getY()<HabitatView.panelGen.getHeight()-100)){
                setY(getY()+speed);
                HabitatController.Repaint();
        }
        if ((getY()>=HabitatView.panelGen.getHeight()-100)&&(getX()<=25)){
            setX(getX()+speed);
            HabitatController.Repaint();
        }
        if ((getY()>=HabitatView.panelGen.getHeight()-100)&&(getX()>25)&&(getX()<HabitatView.panelGen.getWidth()-100)){
            setX(getX()+speed);
            HabitatController.Repaint();
        }
        if ((getX()>=HabitatView.panelGen.getWidth()-100)&&(getY()>25)){
            setY(getY()-speed);
            HabitatController.Repaint();
        }
    }
}
