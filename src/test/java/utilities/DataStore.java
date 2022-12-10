package utilities;

import java.util.HashMap;
import java.util.Map;

public class DataStore {

    private static ThreadLocal<Map<String, Object>> dataStore = new ThreadLocal<>();
    private static final Map<String, Object> sharedDataStore = new HashMap<>();

    public static Object getData(String key){
        initializeDataStore();
        if(dataStore.get().containsKey(key)){
            return dataStore.get().get(key);
        }
        else{
            return null;
        }
    }

    public static void putData(String key, Object value){
        initializeDataStore();
        dataStore.get().put(key, value);
    }

    private static void initializeDataStore(){
        if(dataStore.get() == null){
            Map<String, Object> dataMap = new HashMap<>();
            dataStore.set(dataMap);
        }
    }

    public static void shareData(String key, Object value){
        sharedDataStore.put(key, value);
    }

    public static Object getSharedData(String key){
        if(sharedDataStore.containsKey(key)){
            return sharedDataStore.get(key);
        }
        else{
            return null;
        }
    }
}
