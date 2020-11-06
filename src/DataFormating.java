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
        printStructure(list);
        //for (String s : list){
        //    System.out.println(s);
        //}
    }

    public static void printStructure(ArrayList<String> list){
        for (int i=0;i<list.size();i++){
            String s = list.get(i);
            System.out.println(s);
            List<String> splitList = Arrays.asList(s.split(", "));
            for (int j = 0; j<splitList.size();j++){
                String keyValueMassiv[] = splitList.get(j).split("=");
                String key = "";
                String value = "";
                if (keyValueMassiv.length>1) {
                    key = keyValueMassiv[0];

                    if (key.charAt(0)==' '){
                        key = key.substring(1);
                    }
                    value = keyValueMassiv[1];
                }
                System.out.println(i+"@"+"Общая информация"+"@"+key+"@"+value);
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
