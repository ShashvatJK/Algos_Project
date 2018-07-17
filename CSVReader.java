/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algos.project;
	



import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CSVReader {
    public static void main(String[] args) {
    }  
    
    public String[][] getDataFromCSV(String csvFileLocation){
        String[][] data= new String[5574][2];
        try (BufferedReader br = new BufferedReader(new FileReader(csvFileLocation))) {
            String line = br.readLine();            
            for(int i=0;(line = br.readLine()) != null;i++){
                switch(line.charAt(0)){
                    case 'h': 
                        data[i][0]="ham";
                        data[i][1]=line.substring(4,line.length()-3);
                        break;
                    case 's': 
                        data[i][0]="spam";
                        data[i][1]=line.substring(5,line.length()-3);
                        break;
                }
                //System.out.println("Data "+i+" [Key= "+data[i][0]+" , String="+data[i][1]+"]");
            }

        } catch (IOException e) {
            System.out.println(e);
        }
        return data;
    }
}
