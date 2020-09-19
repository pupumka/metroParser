import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        deltaLinks("C:\\Users\\mironov.matvey\\Documents\\GitHub\\");
    }


    public static void deltaLinks(String path){
        BufferedReader br;
        //ArrayList<String> list = new ArrayList<String>();
        try { // последняя выгрузка БОЛЬШОЙ файл , 28555 строк
            br = new BufferedReader(new FileReader(new File(path+ "metroParser\\src\\Links2.txt")));
            ArrayList<String> list1 = new ArrayList<String>();
            String line = br.readLine();
            while (line != null) {
                //System.out.println(line);
                list1.add(line);
                // считываем остальные строки в цикле
                line = br.readLine();
            }

            //файл с ссылок которого парсилась вся инфа. 22422 строк
            br = new BufferedReader(new FileReader(new File(path+ "metroParser\\src\\Links_Main.txt")));
            ArrayList<String> list2 = new ArrayList<String>();
            line = br.readLine();
            while (line != null) {
                //System.out.println(line);
                list2.add(line);
                // считываем остальные строки в цикле
                line = br.readLine();
            }

            for (int i = 0 ; i < list1.size(); i++){
                if (!list2.contains(list1.get(i))){
                    System.out.println(list1.get(i));
                }
            }

        }
        catch (Exception e){
            System.out.println("Exception in func deltaLinks has been catched!!!");
            e.printStackTrace();
        }


    }

}
