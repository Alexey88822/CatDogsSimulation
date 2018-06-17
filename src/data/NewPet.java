package data;

import java.awt.*; // графический интерфейс
import java.util.UUID;

public class NewPet {

    private int posX;
    private int posY;
    private int id;
    String type;

    public int getId() {
        return id;
    }

    // Экземпляр класса Graphics хранит параметры, необходимые для отрисовки

    public NewPet(int posX, int posY, int id) {
        this.posX = posX;
        this.posY = posY;
        this.id = id;
    }

    public NewPet(int posX, int posY, int id, String type) {
        this.posX = posX;
        this.posY = posY;
        this.id = id;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public NewPet() {
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void setId(int id) {
        this.id = id;
    }
}
