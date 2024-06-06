package co.com.excel.data;

import java.io.IOException;
import java.util.ArrayList;

public class testSample {

    public static void main(String[] args) throws IOException {
        dataDriven data = new dataDriven();
        ArrayList datos = data.getData("Purchase");
        System.out.println(datos.get(0));
        System.out.println(datos.get(1));
        System.out.println(datos.get(2));

    }



}
