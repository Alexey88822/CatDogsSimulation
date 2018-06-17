package data;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public abstract class Fon implements IBehaviour{
    private static Image img;

    static {
        try {
            img = ImageIO.read(new File("resources/image/fon.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Экземпляр класса Graphics хранит параметры, необходимые для отрисовки
    public static void paintfon(Graphics g){
        if(img != null){
            g.drawImage(img,0,0,1280,1000,null);
        }
    }
}
