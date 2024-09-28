/*
 * 
 * This class creates a hash dictionary and uses a hash function.
 * It allows you to add and remove items from the hash dictionary.
 */

import java.util.Iterator;
import java.util.LinkedList;

public class HashDictionary implements DictionaryADT{
    //provides the number of elements in hash dictionary
    private int count;
    //the hashtable of linked lists
    private LinkedList<Data>[] hashTable;
    //the size of the hash table
    private int sizeTable;

    //initializes the hash table to a given size and updates the sizeTable variable
    public HashDictionary(int size){
        hashTable = new LinkedList[size];
        this.sizeTable = size;

    }

    //adds a data item to the hash table. will throw an exception if there
    //is already the same configuration in the hash table
    public int put (Data record) throws DictionaryException{
        int h = hashFunction(record.getConfiguration()); //create a hash first for the configuration
        if(hashTable[h] == null){ //create a new linked list and add to hash table if spot is empty
            hashTable[h] = new LinkedList<>();
            hashTable[h].addFirst(record);
            count++;
            return 0;
        }
        else{ //if spot is not empty, first check if configuration already exists in the list
            if(listChecker(h,record)) throw new DictionaryException(); //throw exception if config found in list

            hashTable[h].add(record); //add record to the linked list in index of hashtable
            count++;
            return 1;
        }

    }

    //removes an element from the hash table given the configuration
    //throws an exception if it cannot be found
    public void remove (String config) throws DictionaryException{
        int h = hashFunction(config); //first calculate the hash of the configuration
        if(hashTable[h] != null) {
            Iterator<Data> iterator = hashTable[h].iterator(); //iterator will go through list in this slot
            while(iterator.hasNext()){
                Data configuration = iterator.next();
                if(configuration.getConfiguration().equals(config)){ //check if what's in the list already is equals config
                    iterator.remove();//remove it if it matches
                    count--;
                    return;
                 }
            }
        }
        throw new DictionaryException(); //throw an exception if config cannot be found in hash table
    }

    //returns the score of a configuration
    //returns -1 if it's not in the hash table
    public int get (String config){
        int h = hashFunction(config); //calculate the hash of the configuration
        if(hashTable[h] != null) {
            Iterator<Data> iterator = hashTable[h].iterator(); //create an iterator for the list in this slot fo hash table
            while(iterator.hasNext()){
                Data configuration = iterator.next();
                if(configuration.getConfiguration().equals(config)){ //check if current element in iterator matches config
                    return configuration.getScore(); //return the score of this element if it matches
                }
            }
        }
        return -1; //return -1 if the configuration cannot be found
    }

    //return the number of elements stored in the hash table
    public int numRecords(){
        return count;
    }

    //this is my polynomial hash function
    private int hashFunction(String str){
        int hash = 0;

        for(int i = 0; i < str.length(); i++){
            int ascii = str.charAt(i);
            hash += (31 * hash + ascii)%sizeTable;
        }

        return (hash%sizeTable);
    }

    //this method checks if a configuration already exists in a list
    //returns true if it does exist, false if it does not
    private boolean listChecker(int hash, Data record) {

        Iterator<Data> iterator = hashTable[hash].iterator();

        while(iterator.hasNext()){
            Data configuration = iterator.next();
            if(configuration.getConfiguration().equals(record.getConfiguration())){
                return true;
            }
        }

        return false;

    }

}


