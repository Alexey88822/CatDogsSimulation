package data;

public class CatAI extends BaseAI {
    public CatAI() {
        super("CatThread");
    }

    @Override
    void nextStep() {
        synchronized (PetArrayList.getInstance().arrayPetList){
            for (Pet pet:PetArrayList.getInstance().arrayPetList){
                if (pet instanceof Cat){
                    pet.move();
                }
            }
        }
    }
}
