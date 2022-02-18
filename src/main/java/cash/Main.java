package cash;

import cash.entity.Receipt;
import cash.entity.ReceiptProducts;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ReceiptProducts rp = new ReceiptProducts(1, 2, 100);
        ReceiptProducts rp1 = new ReceiptProducts(10, 20, 200);
        ReceiptProducts rp3 = new ReceiptProducts(100, 200, 300);
        ReceiptProducts rp4 = new ReceiptProducts(1000, 2000, 400);


        Receipt receipt = new Receipt();
        ArrayList<ReceiptProducts> oldList = new ArrayList<>();
        System.out.println(oldList + "oldList");
        oldList.add(rp);
        oldList.add(rp1);
        oldList.add(rp3);
        System.out.println((oldList + "newList"));
        receipt.setReceiptProducts(oldList);
        receipt.getReceiptProducts().add(rp4);
        ArrayList<ReceiptProducts> arrayList = receipt.getReceiptProducts();
        arrayList.remove(rp3);

        for (ReceiptProducts i : arrayList) {
            System.out.println(i);

        }


    }


}
