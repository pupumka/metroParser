import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataFormating {
    public static void main(String[] args) {
        String filePath = "C:\\Users\\mironov.matvey\\Documents\\GitHub\\metroParser\\src\\import\\1\\Общая информация.txt";
        ArrayList<String> list = readCommonInfo(filePath);
        list = deleteBrackets(list);
        for (String s : list){
            System.out.println(s);
        }
    }

    public void printStructure(ArrayList<String> list){
        for (int i=0;i<list.size();i++){
            String s = list.get(i);
            List<String> splitList = Arrays.asList(s.split(","));
            for (int j = 0; j<splitList.size();j++){
                String key = splitList.get(j).split("=")[0];
                String value =
                System.out.println(i+"@"+"Общая информация"+"@");
            }
        }
    }

    public static ArrayList<String> deleteBrackets(ArrayList<String> oldList){
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i<oldList.size();i++){
            String s1 = oldList.get(i);
            String s2 = s1.substring(1,s1.length()-1);
            list.add(s2);
        }
        return list;
    }

    public static ArrayList<String> readCommonInfo(String path) {
        BufferedReader br;
        ArrayList<String> list = new ArrayList<String>();
        try {
            br = new BufferedReader(new FileReader(new File(path)));

            String line = br.readLine();
            while (line != null) {
                //System.out.println(line);
                list.add(line);
                // считываем остальные строки в цикле
                line = br.readLine();
            }
        }
        catch (Exception e){
            System.out.println("Exception in func editCommonInfo has been catched!!!");
            e.printStackTrace();
        }
        return list;
    }
}
