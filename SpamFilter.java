/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algos.project;

/**
 *
 * @author hp
 */
import java.util.*;
public class SpamFilter {
    public static void main(String args[]){
        double ham=0,spam=0;
        CSVReader csvr=new CSVReader();
        String data[][]=csvr.getDataFromCSV("C:\\Users\\hp\\Desktop\\csv\\spam.csv");
        for(int i=0;i<data.length;i++){
            if(data[i][0].equals("ham")){
                ham++;
            }else{
                spam++;
            }
        }
        double total=ham+spam;
        double ham_prob=ham/total,spam_prob=spam/total;
        Scanner scan= new Scanner(System.in);
        System.out.println("Enter SMS string:");
        String input_data=scan.nextLine();;
        List<String> word_list=new ArrayList<String>();
        for(int i=0;i<data.length;i++){
            String temp="";
            for(int j=0;j<data[i][1].length();j++){
                if(data[i][1].charAt(j)==' ' 
                        || data[i][1].charAt(j)==','
                        || data[i][1].charAt(j)=='.'
                        || data[i][1].charAt(j)=='"'
                        || data[i][1].charAt(j)=='-'
                        || data[i][1].charAt(j)=='!'
                        || data[i][1].charAt(j)=='?'
                        || data[i][1].charAt(j)==';'
                        || data[i][1].charAt(j)==')'
                        || data[i][1].charAt(j)=='('
                        || data[i][1].charAt(j)==':'
                        || data[i][1].charAt(j)=='*'){
                    if(temp.equalsIgnoreCase("")==false){
                        if(word_list.contains(temp.toLowerCase())==false){
                            word_list.add(temp.toLowerCase());//list of unique words from the dataset
                        }
                    }
                    temp="";
                    continue;
                }
                temp=temp+data[i][1].charAt(j);
            }
        }
        //44 46 34
        
        List<CustomObject> word_probs=new ArrayList<CustomObject>();
        for(int i=0;i<word_list.size();i++){
            double ham_i=0,spam_i=0;
            for(int j=0;j<data.length;j++){
                if(data[j][1].contains(word_list.get(i))){
                    if(data[j][0].equals("ham")){                        
                        ham_i++;
                    }else{
                        spam_i++;   
                    }                    
                }
            }
            word_probs.add(new CustomObject(word_list.get(i),ham_i/ham,spam_i/spam));
           // System.out.println(word_list.get(i)+" hamprob "+ham_i/ham+" spamprob "+spam_i/spam);
        }
        
        List<String> input_word_list=new ArrayList<String>();
          
        String temp_input="";
        //int input_word_ham_prob,input_word_spam_prob;
        for(int j=0;j<input_data.length();j++){                
            if((input_data.charAt(j)==' ' 
                || input_data.charAt(j)==','
                || input_data.charAt(j)=='.'
                || input_data.charAt(j)=='"'
                || input_data.charAt(j)=='-'
                || input_data.charAt(j)=='!'
                || input_data.charAt(j)=='?'
                || input_data.charAt(j)==';'
                || input_data.charAt(j)==')'
                || input_data.charAt(j)=='('
                || input_data.charAt(j)==':')
                && temp_input.equalsIgnoreCase("")==false){
                if(input_word_list.contains(temp_input.toLowerCase())==false){
                    input_word_list.add(temp_input.toLowerCase());
                }
                temp_input="";
                continue;
            }
            temp_input=temp_input+input_data.charAt(j);
        }
        double input_string_ham_prob=1.0,input_string_spam_prob=1.0;
        for(int i=0;i<word_list.size();i++){
            if(input_word_list.contains(word_list.get(i))==true){
                input_string_ham_prob *= word_probs.get(i).getHamProbability();
                input_string_spam_prob *= word_probs.get(i).getSpamProbability();
            }else{
                input_string_ham_prob *= (1-word_probs.get(i).getHamProbability());
                input_string_spam_prob *= (1-word_probs.get(i).getSpamProbability());
            }
        }
        
        System.out.println("Input ham "+input_string_ham_prob);
        System.out.println("Input spam "+input_string_spam_prob);
        System.out.println("Total ham "+ham_prob);
        System.out.println("Total spam "+spam_prob);
        
        double final_ham_prob = (input_string_ham_prob*ham_prob)/(input_string_ham_prob*ham_prob + input_string_spam_prob*spam_prob);
        double final_spam_prob = (input_string_spam_prob*spam_prob)/(input_string_ham_prob*ham_prob + input_string_spam_prob*spam_prob);
        if(final_ham_prob >= final_spam_prob){
           System.out.println("ham " +final_ham_prob);
        }else{
           System.out.println("spam " +final_spam_prob); 
        }
    }
  
}
//what you have done is wrong slightly
//u need to have an arraylist of all the words unique in training set 
//calculate their ham_prob and spam_prob
//then check if that ArrayList.contains(the words from input text)
//now multiply their probability for two conditions given ham or spam
//also multiply with the probability of other words not present in input
//after finally finding ham or spam
//add the input string to the dataset for the machine to learn and improve.
class CustomObject{
    String value_word;
    double value_ham_prob,value_spam_prob;
    public CustomObject(String word,double ham_prob,double spam_prob){
        value_word=word;
        value_ham_prob=ham_prob;
        value_spam_prob=spam_prob;  
    }
    public double getHamProbability(){
        return value_ham_prob;
    }
    public double getSpamProbability(){
        return value_spam_prob;
    }
    public String getWord(){
        return value_word;
    }
    public void setHamProbability(double ham_prob){
        value_ham_prob=ham_prob;
    }
    public void setSpamProbability(double spam_prob){
        value_spam_prob=spam_prob;
    }
    public void setWord(String word){
        value_word=word;
    }
}
