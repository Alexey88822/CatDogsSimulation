package data;

import java.net.Inet4Address;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PetHashMap {
     public static Map<Integer, Long> hashmap = new HashMap<Integer, Long>();

     public static int getSizeHashMap(){
          int value = hashmap.size();
          return value;
     }

     public static long getTimeofBorn(int identificator){
          return hashmap.get(identificator);
     }
}
