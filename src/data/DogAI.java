package data;

public class DogAI extends BaseAI {
    public DogAI() {
        super("DogThread");
    }

    @Override
    void nextStep() {
        synchronized (PetArrayList.getInstance().arrayPetList){
            for (Pet pet:PetArrayList.getInstance().arrayPetList){
                if (pet instanceof Dog){
                    pet.move();
                }
            }
        }
    }
}
