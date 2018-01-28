import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFileChooser;


public class GRPH {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			HashMap<String,String> map = ReadFile.readFile();
			HashMap<String,ArrayList<String>> graph = new HashMap<String,ArrayList<String>>(map.size()^2);
			for(String tail : map.keySet()){
				for(String head : map.keySet()){
					if(!map.get(tail).equalsIgnoreCase(map.get(head))){
						for(int i = 0; i < map.get(tail).length();i++){
							for(int j = 1; j <= map.get(head).length();j++){
								if(map.get(tail).substring(map.get(tail).length()-1-i, map.get(tail).length()).equalsIgnoreCase(map.get(head).substring(0,j))){
									i = map.get(tail).length();
									j = map.get(head).length();
									if(graph.keySet().contains(tail)){
										graph.get(tail).add(head);
									}
									else{
										ArrayList<String> list = new ArrayList<String>();
										list.add(head);
										graph.put(tail, list);
										
									}
								}
							}
						}
					}
				}
			}
			System.out.println(graph);
		}
		catch(FileNotFoundException exc){
			System.out.println("I will not forgive you for this.");
		}
	}

}
