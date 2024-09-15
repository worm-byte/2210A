/*
 * Rosaline Scully
 * August 3, 2024
 * Student ID 250966670
 * 
 * This is the interface for running the program which is a dictionary of different files.
 * It will either show a definition, play a song or sound, show a picture or GIF, or show a website.
 * You are also able to add and delete from the dictionary.
 * You can find the largest value and print it's attributes. Same with the smallest value.
 * You can also view a list of all nodes with a given prefix.
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Interface {

	//instance variable is binary search dictionary
    private static BSTDictionary dictionary = new BSTDictionary();

    public static void main(String[] args) {

        String inputFile = args[0];
        
        // load records from the input file
        inputFileReader(inputFile);
        
        // create a StringReader object to read user commands
        StringReader keyboard = new StringReader();
        String line;
        
        // main processing loop
        while (true) {
            line = keyboard.read("Enter next command: ").trim();
            if (line == null) {
                System.out.println("Invalid command.");
                continue;
            }
            
            String[] entry;
        	String command;
            String prompt;
            
            //split keyboard entry into command and prompt if there is a space in it
            if(line.contains(" ")) {
            	entry = line.split(" ", 2);
            	command = entry[0].toLowerCase();
                prompt = entry[1].toLowerCase();
            }else {
            	command = line; //if it's just one word do this
            	prompt = "";
            }

            //switch between the different commands depending on user input
            switch (command) {
                case "define":
                    define(prompt);
                    break;
                case "translate":
                    translate(prompt);
                    break;
                case "sound":
                    sound(prompt);
                    break;
                case "play":
                    play(prompt);
                    break;
                case "say":
                    say(prompt);
                    break;
                case "show":
                    show(prompt);
                    break;
                case "animate":
                    animate(prompt);
                    break;
                case "browse":
                    browse(prompt);
                    break;
                case "delete":
                    delete(prompt);
                    break;
                case "add":
                    add(prompt);
                    break;
                case "list":
                    list(prompt);
                    break;
                case "first":
                    first();
                    break;
                case "last":
                    last();
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("Invalid command.");
                    break;
            }
        }
    }
    

    //reads input from a file and puts it in the binary search dictionary
    private static void inputFileReader(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String label = line.trim();
                line = reader.readLine();
                if (line == null) break; // if there's no corresponding type and data, break
                Record record = typeDeterminator(label,line); 
                if (record != null) {
                    dictionary.put(record); // add the record to the ordered dictionary
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        } catch(DictionaryException e) {
        	System.out.println(e.getMessage());
        }
    }

    //determine type and data from input line and return a record to put in dictionary
    private static Record typeDeterminator(String label, String line) {
    	int type;
    	String data = line.trim();
    	
    	if(data.startsWith("-")) {
    		type = 3;
    		data = data.substring(1).trim();
    	}else if(data.startsWith("+")){
    		type = 4;
    		data = data.substring(1).trim();
    	}else if(data.startsWith("*")) {
    		type = 5;
    		data = data.substring(1).trim();
    	}else if(data.startsWith("/")) {
    		type = 2;
    		data = data.substring(1).trim();
    	}else if(data.endsWith("gif")) {
    		type = 7;
    	}else if(data.endsWith("jpg")) {
    		type = 6;
    	}else if(data.endsWith("html")) {
    		type = 8;
    	}else {
    		type = 1;
    	}
    	
    	return new Record(new Key(label,type),data);
    }

    //print out the definition of a given word if it's in the dictionary
    private static void define(String key) {
        Key k = new Key(key,1); //create a new key and see if it's in the dictionary
        Record r = dictionary.get(k);
        
        if(r != null) {
        	System.out.println(r.getDataItem());
        }else {
        	System.out.println("The word " + key + " is not in the dictionary ");
        }
    }

    //print out the translation of a given word in French if it's in the dictionary
    private static void translate(String key) {
        Key k = new Key(key,2);
        Record r = dictionary.get(k);
        
        if(r != null) {
        	System.out.println(r.getDataItem());
        }else {
        	System.out.println("There is no definition for the word " + key);
        }
    }

    //play a sound file on computer if it's in the dictionary
    private static void sound(String key) {
        Key k = new Key(key,3); //create a new key and see if it's in the dictionary
        Record r = dictionary.get(k);
        
        if(r != null) {
        	try { //call soundplayer to play the sound
        	    SoundPlayer player = new SoundPlayer();
        	    player.play(r.getDataItem());
        	}
        	catch (MultimediaException e) {
        	    System.out.println(e.getMessage());
        	}
        }else {
        	System.out.println("There is no sound file for " + key);
        }
    }

    //play a music file on computer if it's in the dictionary
    private static void play(String key) {
        Key k = new Key(key,4); //create a new key and see if it's in the dictionary
        Record r = dictionary.get(k);
        
        if(r != null) {
        	try { //call soundplayer to play the sound
        	    SoundPlayer player = new SoundPlayer();
        	    player.play(r.getDataItem());
        	}
        	catch (MultimediaException e) {
        	    System.out.println(e.getMessage());
        	}
        }else {
        	System.out.println("There is no music file for " + key);
        }
    }

    //play a voice file on computer if it's in the dictionary
    private static void say(String key) {
    	Key k = new Key(key,5); //create a new key and see if it's in the dictionary
        Record r = dictionary.get(k);
        
        if(r != null) {
        	try { //call soundplayer to play the sound
        	    SoundPlayer player = new SoundPlayer();
        	    player.play(r.getDataItem());
        	}
        	catch (MultimediaException e) {
        	    System.out.println(e.getMessage());
        	}
        }else {
        	System.out.println("There is no voice file for " + key);
        }
    }

    //show an image on screen if it's in the dictionary
    private static void show(String key) {
        Key k = new Key(key,6); //create a new key and see if it's in the dictionary
        Record r = dictionary.get(k);
        
        if(r != null) {
        	try { //call pictureviewer to display the image
        		PictureViewer viewer = new PictureViewer();
        	    viewer.show(r.getDataItem());
        	}
        	catch (MultimediaException e) {
        	    System.out.println(e.getMessage());
        	}
        }else {
        	System.out.println("There is no image file for " + key);
        }
    }

    //play a gif image on screen if it's in the dictionary
    private static void animate(String key) {
    	Key k = new Key(key,7); //create a new key and see if it's in the dictionary
        Record r = dictionary.get(k);
        
        if(r != null) {
        	try { //call pictureviewer to display the gif
        		PictureViewer viewer = new PictureViewer();
        	    viewer.show(r.getDataItem());
        	}
        	catch (MultimediaException e) {
        	    System.out.println(e.getMessage());
        	}
        }else {
        	System.out.println("There is no animated image file for " + key);
        }
    }

    //display a website if it's in the dictionary
    private static void browse(String key) {
        Key k = new Key(key,8); //create a new key and see if it's in the dictionary
        Record r = dictionary.get(k);
        
        if(r != null) { //showhtml to display the website
        	ShowHTML browser = new ShowHTML();
    	    browser.show(r.getDataItem());
        }else {
        	System.out.println("There is no webpage called " + key);
        }
    }

    //delete a key from the dictionary if it exists
    private static void delete(String keyToRemove) {
    	String[] toRemove = keyToRemove.split(" ", 2); //split the string into the label and type
    	
    	Key k = new Key(toRemove[0],Integer.parseInt(toRemove[1])); //create a new key given label and type
    	
		try {
			dictionary.remove(k);
		}catch(DictionaryException e) {
			System.out.println(e.getMessage());
		}
    }

    //add a key to the dictionary if it's not already in the dictionary
    private static void add(String keyToAdd) {
        String[] toAdd = keyToAdd.split(" ",3); //split the string into the label, type, and data
        
        Record r = new Record(new Key(toAdd[0],Integer.parseInt(toAdd[1])),toAdd[2]); //create a new record and key given label type and data
        
        try {
        	dictionary.put(r);
        }catch(DictionaryException e) {
        	System.out.println(e.getMessage());
        }
    }

    //prints out a list of all keys in dictionary that have the given prefix if it exists
    private static void list(String prefix) {
        Key k = new Key(prefix, 0);  //create a new key given the prefix
        Record r = dictionary.successor(k); //find the successor of key
        
        if (r == null || !r.getKey().getLabel().startsWith(prefix)) { //if no successor found first try exit early
            System.out.println("No label attributes in the ordered dictionary start with prefix " + prefix);
            return; 
        } else { //if found print the key label
            System.out.print(r.getKey().getLabel());
        }

        while (true) { 
            k = new Key(r.getKey().getLabel(), r.getKey().getType()); //create a new key to find the successor of r
            r = dictionary.successor(k); //r now becomes the next successor
            if (r != null && r.getKey().getLabel().startsWith(prefix)) { //check if the next successor still starts with prefix
                System.out.print(", " + r.getKey().getLabel());
            } else {
                break; // exit if no more records found with prefix
            }
        }
    }


    //prints out label, type, and data of the smallest item in dictionary
    private static void first() {
        Record first = dictionary.smallest();
        
        System.out.println(first.getKey().getLabel() + "," + first.getKey().getType() + "," + first.getDataItem());
    }

    //prints out label, type, and data of largest item in dictionary
    private static void last() {
        Record last = dictionary.largest();
        
        System.out.println(last.getKey().getLabel() + "," + last.getKey().getType() + "," + last.getDataItem());
    }
    
}





