package data;

import java.awt.*;
import java.io.Serializable;
import java.util.UUID;

public abstract class Pet implements IBehaviour, Serializable {

    private int posX;
    private int posY;
    private int identificator;
    private UUID id = UUID.randomUUID();
    // Экземпляр класса Graphics хранит параметры, необходимые для отрисовки
    public abstract void paint(Graphics g);

    public Pet(){

    }

    Pet(int X, int Y, int identificator){ //При создании объекта рандомно генерируются его координаты
        this.posX=X;
        this.posY=Y;
        this.identificator=identificator;
    }


    public UUID getId(){
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getIdentity(){
        return identificator;
    }

    public void setIdentity(int identificator) { this.identificator=identificator; }

    @Override
    public int getX() {
        return posX;
    }

    @Override
    public int getY() {
        return posY;
    }

    @Override
    public void setX(int X) {
        this.posX = X;
    }

    @Override
    public void setY(int Y) {
        this.posY = Y;
    }
}
