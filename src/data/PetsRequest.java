package data;

import data.Pet;
import data.NewPet;

import java.util.*;

public class PetsRequest {
    String status;
    String id;
    String swapId;
    long time;
    ArrayList<String> users = new ArrayList<>();
    public ArrayList<NewPet> arrayPetList;
    public TreeSet<Integer> idTreeSet;
    public static Map<Integer, Long> bornHashMap;

    public PetsRequest(String status, String id, ArrayList<String> users) {
        this.status = status;
        this.id = id;
        this.users = users;
    }

    public String getSwapId() {
        return swapId;
    }

    public static void getTimeofBorn(int a) { bornHashMap.get(a); }

    public void setSwapId(String swapId) {
        this.swapId = swapId;
    }

    public PetsRequest(String status, String id, String swapId, ArrayList<NewPet> arrayPetList, TreeSet<Integer> idTreeSet, Map<Integer, Long> bornHashMap) {
        this.status = status;
        this.id = id;
        this.swapId = swapId;
        this.arrayPetList = arrayPetList;
        this.idTreeSet = idTreeSet;
        this.bornHashMap = bornHashMap;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public PetsRequest(String status, ArrayList<NewPet> arrayPetList, TreeSet<Integer> idTreeSet, HashMap<Integer, Long> bornHashMap) {
        this.status = status;
        this.arrayPetList = arrayPetList;
        this.idTreeSet = idTreeSet;
        this.bornHashMap = bornHashMap;
    }

    public PetsRequest() {
    }

    public PetsRequest(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<String> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<String> users) {
        this.users = users;
    }
}
