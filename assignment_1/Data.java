/*
 * Rosaline Scully
 * Student ID: 250966670
 * July 22, 2024
 * 
 * This class stores a configuration and it's score in a data structure
 */
public class Data {
    //the configuration of the board saved as a string
    private final String configuration;
    //the score for the board configuration
    private final int scoreConfig;

    //initializes a data object with a configuration and score
    public Data(String config, int score){
        this.configuration = config;
        this.scoreConfig = score;
    }
    //returns configurations of this data object
    public String getConfiguration(){
        return configuration;
    }
    //returns score of this data object
    public int getScore(){
        return scoreConfig;
    }
}


