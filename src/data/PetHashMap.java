package data;

import java.net.Inet4Address;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PetHashMap {
     public static Map<Integer, Long> hashmap = new HashMap<Integer, Long>();
     public static Map<Integer, Long> hashmapCat = new HashMap<Integer, Long>();
     public static Map<Integer, Long> hashmapDog = new HashMap<Integer, Long>();
     public static int getSizeHashMap(){
          int value = hashmap.size();
          return value;
     }

     public static long getTimeofBorn(int identificator){
          return hashmap.get(identificator);
     }
     public static long getTimeofBornCat(int identificator) { return hashmapCat.get(identificator); }
     public static long getTimeofBornDog(int identificator) { return hashmapDog.get(identificator); }
}
