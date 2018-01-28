import java.io.*;
import java.util.HashMap;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class ReadFile {

    private static BufferedReader inFile;
    private static final String[] NUCS = {"Adenine","Thymine", "Cytosine", "Guanine"};

    public static HashMap<String, String> readFile() throws FileNotFoundException{
    	JFileChooser chooser = new JFileChooser();
    	int returnVal = chooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
        	return readFile(chooser.getSelectedFile().getAbsolutePath());
        }
        throw new FileNotFoundException();
    }
    
    public static HashMap<String, String> readFile(String path) {
    	String file = "";
    	HashMap<String,String> map = new HashMap<String,String>();
        try {
            String line;
            String head ="";
            inFile = new BufferedReader(new FileReader(path));  
            if((line = inFile.readLine()) != null){
            	checkFastaness(path.substring(path.length()-6,path.length()), line.charAt(0));
            	head = line;
            }
            while ((line = inFile.readLine()) != null) {
            	if(line.charAt(0) != '>'){
            		file += line;
            	}
            	else{
            		checkNucleotidiness(file);
            		map.put(head, file);
            		head = line;
            		file = "";
            	}
            }
            map.put(head, file);
            
            inFile.close();        
            return map;
        } catch (FileNotFoundException fnfe) {
        	JOptionPane.showMessageDialog(null, "Bestand niet gevonden");
        } catch (IOException ioe) {
        	JOptionPane.showMessageDialog(null, "Kan niet lezen in bestand");
        } catch (NonFastaIsh nfi){
        	JOptionPane.showMessageDialog(null, nfi.getMessage());
        } catch (NotNucleoTidy nnt){
        	JOptionPane.showMessageDialog(null, nnt.getMessage());
        } catch (Exception e) {
            System.out.println("Onbekende fout: geef op");
        }
        return null;

    }
    
    private static void checkFastaness(String extension, char firstChar) throws NonFastaIsh{
    	if(!extension.equals(".fasta")){
    		throw new NonFastaIsh("Extesion is wrong. Not .fasta.");
    	}
    	else if(firstChar != '>'){
    		throw new NonFastaIsh("Fasta file does not start with '>'. This is blasphemy and I eat blasphemers at night. Sweet dreams..");
    	}
    }
    
    private static void checkNucleotidiness(String seq) throws NotNucleoTidy{
    	for(int i = 0; i < seq.length(); i++ ){
    		if(seq.charAt(i) != '\n'){
    			isNuc(seq.charAt(i));
    		}
    	}
    }
    
    private static void isNuc(char n) throws NotNucleoTidy{
    	for(int i = 0; i < NUCS.length; i++){
    		if(Character.toUpperCase(n) == NUCS[i].charAt(0)){
    			return;
    		}
    	}
       	throw new NotNucleoTidy(n + " is not a nucleotide.");
    }
}

class NotNucleoTidy extends Exception {

    /**
    * Overschrijven van de constructor van Exception
    */
    public NotNucleoTidy() {
        /** 
        * call van de constructor van de super class: Exception
        */
        super();
        
    }

    public NotNucleoTidy(String err) {
        
        super(err);
        
    }
    
}

class NonFastaIsh extends Exception {

    /**
    * Overschrijven van de constructor van Exception
    */
    public NonFastaIsh() {
        /** 
        * call van de constructor van de super class: Exception
        */
        super();
        
    }

    public NonFastaIsh(String err) {
        
        super(err);
        
    }
    
}