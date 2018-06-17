package Socket;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import data.*;
import machines.HabitatModel;
import machines.HabitatView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.Socket;
import java.util.ArrayList;

public class SocketRecieve extends Thread {
    private final HabitatView view;
    private final HabitatModel model;
    String host = "localhost";
    int port = 8000;
    Socket socket;
    DataOutputStream outStream;
    DataInputStream inStream;
    Gson gson = new Gson();

    public SocketRecieve(Socket socket, HabitatView view, HabitatModel model) throws IOException {
        this.socket = socket;
        this.view = view;
        inStream = new DataInputStream(socket.getInputStream());
        outStream = new DataOutputStream(socket.getOutputStream());
        this.model = model;
    }


    public void run() {
        while (true) {
            try {
                byte[] bytes = new byte[1024];
                inStream.read(bytes);
                String string = new String(bytes);
                System.out.println("Result is " + string);
                JsonReader reader = new JsonReader(new StringReader(string));
                reader.setLenient(true);
                PetsRequest petsRequest;
                petsRequest = gson.fromJson(reader, PetsRequest.class);
                ArrayList<NewPet> newPets = new ArrayList<>();
                ArrayList<Pet> pets = new ArrayList<>();
                switch (petsRequest.getStatus()) {
                    case "update":
                        System.out.println("update list");
                        PetArrayList.getInstance().users = petsRequest.getUsers();
                        view.updateUsersList();
                        break;
                    case "first connect":
                        System.out.println("first start");
                        PetArrayList.getInstance().id = petsRequest.getId();
                        break;
                    case "swap":
                        System.out.println("swap echo");
                        break;
                    case "swap req":
                        System.out.println("swap req from " + petsRequest.getId());
                        newPets.clear();
                        for(int i=0; i<PetArrayList.getInstance().arrayPetList.size(); i++) {
                            Pet pet = PetArrayList.getInstance().arrayPetList.get(i);
                            String type = pet instanceof Cat ? "cat" : "dog";
                            newPets.add(new NewPet(pet.getX(), pet.getY(), pet.getIdentity(), type));
                        }
                        PetsRequest res = new PetsRequest("swap res", PetArrayList.getInstance().id, petsRequest.getId(), newPets, PetTreeSet.numberSet, PetHashMap.hashmap);
                        res.setTime(HabitatModel.time);
                        String req = gson.toJson(res);
                        outStream.write(req.getBytes());
                    case "swap res":
                        System.out.println("swap res from " + petsRequest.getId());
                        HabitatModel.time = petsRequest.getTime();
                        PetTreeSet.numberSet = petsRequest.idTreeSet;
                        PetHashMap.hashmap = petsRequest.bornHashMap;
                        for(int i=0; i<petsRequest.arrayPetList.size(); i++) {
                            NewPet newPet = petsRequest.arrayPetList.get(i);
                            Pet pet = newPet.getType().equals("cat") ? new Cat() : new Dog();
                            pet.setX(newPet.getPosX());
                            pet.setY(newPet.getPosY());
                            pet.setIdentity(newPet.getId());
                            pets.add(pet);
                        }
                        PetArrayList.getInstance().arrayPetList = pets;
                        break;
                    default:
                        System.out.println("def ");
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
