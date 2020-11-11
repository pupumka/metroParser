import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class DataFormating {
    public static Map<Integer,String> uniqueCategories = new HashMap<>();
    public static ArrayList<Integer> uniqueParentId = new ArrayList<>();

    public static void main(String[] args) {

        //String filePath = "C:\\Users\\mironov.matvey\\Documents\\GitHub\\metroParser\\src\\import\\1\\Категории.txt";

        String filePath = "E:\\Program Files\\metroParser\\src\\import\\1\\Категории.txt";
        //String filePath = "C:\\Users\\mironov.matvey\\Documents\\GitHub\\metroParser\\src\\import\\1\\Общая информация.txt";

        ArrayList<String> list = readCommonInfo(filePath);
        list = deleteBrackets(list);
        categories1level(list);

        filePath = "E:\\Program Files\\metroParser\\src\\import\\1\\Значения категорий.txt";
        list = readCommonInfo(filePath);
        list = deleteBrackets(list);
        categories2(list);

        //printStructureFinal(list);
        //printSostav(list);

        //printStructure(list);

        //for (String s : list){
        //    System.out.println(s);
        //}
    }

    public static void categories2(ArrayList<String> list){
        for (int i =0; i<list.size();i++){
            //System.out.println(list.get(i));
            int listSize = list.get(i).split(";").length;
            String categoryName = list.get(i).split(";")[listSize-1];
            int counter = 0;
            for (Map.Entry<Integer,String> entry : uniqueCategories.entrySet()){
                if (entry.getValue().equals(categoryName)){
                    System.out.println(uniqueParentId.get(counter)+","+entry.getKey());
                }
                counter++;
            }
        }
    }

    public static void categories1level(ArrayList<String> list){
        //Map<Integer,String> uniqueCategories = new HashMap<>();
        //ArrayList<Integer> uniqueCategoryId = new ArrayList<>();
        //ArrayList<Integer> uniqueParentId = new ArrayList<>();

        int categoryId = 100;
        int parentId = 0;
        for (int i =0; i< list.size();i++){
            int categoryNum = list.get(i).split(";").length;
            for (int j = 0;j<categoryNum;j++) {
                if (j!=0){
                    for (Map.Entry<Integer,String> entry : uniqueCategories.entrySet()){
                        if (list.get(i).split(";")[j-1].equals(entry.getValue())){
                            parentId = entry.getKey();
                        }
                    }
                    //parentId = uniqueCategories.
                }
                if (j == 0) {
                    parentId=0;
                }
                String categoryName = list.get(i).split(";")[j];

                if (!uniqueCategories.containsValue(categoryName)) {
                    uniqueCategories.put(categoryId, categoryName);
                    uniqueParentId.add(parentId);
                    categoryId++;
                }

                //System.out.println(categoryId+"@"+parentId+"@"+categoryName);

                //categoryId++;
            }
        }

        int z = 0;
        //for (Map.Entry<Integer,String> entry : uniqueCategories.entrySet()){
        //    System.out.println(entry.getKey()+"@"+uniqueParentId.get(z)+"@"+entry.getValue());
        //    z++;
       // }
        //for (int k = 0;k<uniqueCategories.size();k++){
        //    System.out.println(uniqueCategories.get(k)+"@"+uniqueParentId.get(k)+"@"+uniqueCategories.get(k));
        //} //818 последняя строка по счету


    }

    public static void printSostav(ArrayList<String> list){
        for (int i=0;i<list.size();i++){
            if (!list.get(i).equals("null") && !list.get(i).equals("")) {
                System.out.println(105 + i + "@" + "Состав" + "@" + list.get(i));
            }
        }
    }

    public static void printStructureFinal(ArrayList<String> list){
        for (int i = 0; i<list.size();i++){
            ArrayList<String> keyValuePairs = new ArrayList<>(Arrays.asList(list.get(i).split("@")));
            if (keyValuePairs.size()>1){
                for (int j=0;j<keyValuePairs.size();j++){
                    String key = keyValuePairs.get(j).split("=")[0];
                    String value = keyValuePairs.get(j).split("=")[1];
                    if (key.startsWith(" ")){
                        key = key.substring(1);
                    }
                    System.out.println(105+i+"@"+key+"@"+value);
                }
            }
            //if (keyValuePairs.size()==1){
            //    System.out.println(list.get(i));
            //}
        }
    }

    public static void printAggregatedKeys(ArrayList<String> list){
        for (int i = 0; i<list.size();i++){
            ArrayList<String> keyValuePairs = new ArrayList<>(Arrays.asList(list.get(i).split("@")));
            if (keyValuePairs.size()>0){
                for (int j=0;j<keyValuePairs.size();j++){
                    String key = keyValuePairs.get(j).split("=")[0];
                    if (key.startsWith(" ")){
                        key = key.substring(1);
                    }
                    System.out.println(key);
                }
            }
            if (keyValuePairs.size()==0){
                System.out.println(list.get(i));
            }
        }
    }

    public static void printStructure(ArrayList<String> list){
        for (int i=0;i<list.size();i++){
            String s = list.get(i);
            System.out.println(s);
            LinkedList<String> splitList = new LinkedList<>(Arrays.asList(s.split(", ")));
            //for (int z = 0; z<30;z++){
                splitList = strangeShit(splitList,i);
            //}

            //for (String str : splitList){
                //System.out.println(str);
            //}

        }
    }

    public static LinkedList<String> strangeShit(LinkedList<String> splitList, int i){
        for (int j = 0; j<splitList.size();j++){
            int removeCount = 0;
            if (!splitList.get(j).contains("=") && (j-1)!=-1 && !splitList.get(j).equals("NULL") ){
                String next = splitList.get(j);
                String previous = splitList.get(j-1);
                splitList.set(j-1,previous+", "+next);
                removeCount=1+removeCount;
                splitList.remove(j);
            }
            if (splitList.get(j-removeCount).equals("Вес") && (j-1)!=-1 && j!=splitList.size()){
                String next = splitList.get(j);
                String previous = splitList.get(j-1);
                splitList.set(j-1,previous+", "+next);
                splitList.remove(j-removeCount);
            }

            String keyValueMassiv[] = splitList.get(j-removeCount).split("=");
            String key = "";
            String value = "";
            if (keyValueMassiv.length > 1) {
                key = keyValueMassiv[0];

                if (key.charAt(0) == ' ') {
                    key = key.substring(1);
                }
                value = keyValueMassiv[1];
            }
            System.out.println(i+"@"+"Общая информация"+"@"+key+"@"+value);
        }
        return splitList;
    }

    public static void printStructure2(ArrayList<String> list) {
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            System.out.println(s);
            List<String> splitList = Arrays.asList(s.split(", "));
            for (int j = 0; j < splitList.size(); j++) {
                String keyValueMassiv[] = splitList.get(j).split("=");
                String key = "";
                String value = "";
                if (keyValueMassiv.length > 1) {
                    key = keyValueMassiv[0];

                    if (key.charAt(0) == ' ') {
                        key = key.substring(1);
                    }
                    value = keyValueMassiv[1];
                }
                //System.out.println(i+"@"+"Общая информация"+"@"+key+"@"+value);
            }
        }
    }

    public static ArrayList<String> deleteBrackets(ArrayList<String> oldList){
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i<oldList.size();i++){

            String s = oldList.get(i).replace("[","");
            s = s.replace("]","");
            if (i==0){
                s = s.substring(1);
            }
            list.add(s);
            //String s1 = oldList.get(i);
            //String s2 = s1.substring(1,s1.length()-1);
            //list.add(s2);
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
