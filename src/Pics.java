import org.openqa.selenium.WebDriver;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Pics {
    public static ArrayList<String> linklist = new ArrayList<>();
    public static ArrayList<String> piclist = new ArrayList<>();
    public static ArrayList<String> findPics = new ArrayList<>();
    public static HashMap<String,String> map = new HashMap<>();
    public static String path = "E:\\Program Files\\metroParser\\src\\import\\1\\all_import\\";
    public static void main(String[] args) {
        linklist = readLinksFromFile(path+"all_links.txt",linklist );
        piclist = readLinksFromFile(path+"all_pics.txt",piclist );
        findPics = readLinksFromFile(path+"delta_pics.txt",findPics );

        for (int i =0;i<piclist.size();i++){
            for (int j = 0; j<findPics.size();j++){
                if (piclist.get(i).equals(findPics.get(j))){
                    System.out.println(linklist.get(i));
                }
            }
        }

    }

    public static ArrayList<String> readLinksFromFile (String filePath, ArrayList<String> dest){
        BufferedReader br;
        //ArrayList<String> list = new ArrayList<String>();
        try {
            br = new BufferedReader(new FileReader(new File(filePath)));
            String line = br.readLine();
            while (line != null) {
                //System.out.println(line);
                dest.add(line);
                // считываем остальные строки в цикле
                line = br.readLine();
            }
        }
        catch (FileNotFoundException e){
            System.out.println("Exception while reading file at has been catched!!!");
            e.printStackTrace();
        }
        catch (IOException e){
            System.out.println("Exception while reading strings from file at has been catched!!!");
            e.printStackTrace();
        }
        return dest;
    }

}
