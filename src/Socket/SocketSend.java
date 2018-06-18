package Socket;

import com.google.gson.Gson;
import machines.HabitatModel;
import data.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Handler;

public class SocketSend {
    private final DataOutputStream outStream;
    Socket socket;
    Handler handler;
    private Gson gson = new Gson();

    public SocketSend(Socket socket) throws IOException {
        this.socket = socket;
        outStream = new DataOutputStream(socket.getOutputStream());
    }

    public void sendData(PetsRequest request) {
        try {
            String req = gson.toJson(request);
            outStream.write(req.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() throws IOException {
        socket.getInputStream().close();
        socket.getOutputStream().close();
        socket.close();
    }

    public void swap(String swapId) throws IOException {
        ArrayList<NewPet> newPets = new ArrayList<>();
        for(int i = 0; i<PetArrayList.getInstance().arrayPetList.size(); i++) {
            Pet pet = PetArrayList.getInstance().arrayPetList.get(i);
            String type = pet instanceof Cat ? "cat" : "dog";
            if (pet instanceof Cat) {
                newPets.add(new NewPet(pet.getX(), pet.getY(), pet.getIdentity(), type));
                PetTreeSet.addCatTreeSet(pet.getIdentity());
                PetHashMap.hashmapCat.put(pet.getIdentity(), PetHashMap.getTimeofBorn(pet.getIdentity()));
                PetArrayList.removeFromarrayPetList(pet);
            }
        }
        PetsRequest request = new PetsRequest("swap req", PetArrayList.getInstance().id, swapId, newPets, PetTreeSet.numberCat, PetHashMap.hashmapCat);
        request.setTime(HabitatModel.time);
        String req = gson.toJson(request);
        System.out.println("swap req " + req);
        outStream.write(req.getBytes());
    }
}