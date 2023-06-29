import clientserver.*;
import clientserver.shop.Shop;
import clientserver.packet.DataCollector;

import org.junit.Assert;
import org.junit.Test;

public class ShopTesting {
    private static Shop magazyn;
    private static Receiver receiving;
    private static Sending sending;


    @Test
    public void shop_test1() {
        receiving = new Receiver();
        sending = new Sending();
        int cType = 0; //get the amount of products
        int bUserId = 29;
        byte bSrc = (byte) 544;
        long bPkId = 42342343;
        magazyn = new Shop();
        magazyn.add_group_of_product("Drinks");
        magazyn.add_product_to_group("Milk", "Drinks");
        magazyn.add_product("Milk", 33);

        String product = "Milk";

        magazyn.process_shop(Processor.process(sending.sendPacket(DataCollector.getByteArray(cType, bUserId, bSrc, bPkId, product),
                bUserId))); //result of command

        Assert.assertNotNull(magazyn);
    }

    @Test
    public void shop_test2() {
        receiving = new Receiver();
        sending = new Sending();
        int cType = 1; //delete some amount of products
        int bUserId = 30;
        byte bSrc = (byte) 544;
        long bPkId = 42342344;
        magazyn = new Shop();
        magazyn.add_group_of_product("Drinks");
        magazyn.add_product_to_group("Milk", "Drinks");
        magazyn.add_product("Milk", 33);

        String product = "Milk";
        String amount = "22";
        String query = product + " " + amount;

        magazyn.process_shop(Processor.process(sending.sendPacket(DataCollector.getByteArray(cType, bUserId, bSrc, bPkId, query), bUserId)));
        System.out.println(magazyn.get_amount_of_product(product)); //result of command

        Assert.assertNotNull(magazyn);
    }

    @Test
    public void shop_test3() {
        receiving = new Receiver();
        sending = new Sending();
        int cType = 2; //add some amount of products
        int bUserId = 30;
        byte bSrc = (byte) 544;
        long bPkId = 42342344;
        magazyn = new Shop();
        magazyn.add_group_of_product("Drinks");
        magazyn.add_product_to_group("Milk", "Drinks");
        magazyn.add_product("Milk", 33);

        String product = "Milk";
        String amount = "22";
        String query = product + " " + amount;

        magazyn.process_shop(Processor.process(sending.sendPacket(DataCollector.getByteArray(cType, bUserId, bSrc, bPkId, query), bUserId)));
        System.out.println(magazyn.get_amount_of_product(product)); //result of command

        Assert.assertNotNull(magazyn);
    }

    @Test
    public void shop_test4() {
        receiving = new Receiver();
        sending = new Sending();
        int cType = 3; //add new group of products
        int bUserId=28;
        byte bSrc = (byte)544;
        long bPkId = 42342342;
        magazyn = new Shop();
        String group_name = "Drinks";

        magazyn.process_shop(Processor.process(sending.sendPacket(DataCollector.getByteArray(cType, bUserId, bSrc, bPkId, group_name), bUserId)));
        System.out.println(magazyn.getGroups_of_product()); //result of command

        Assert.assertNotNull(magazyn);
    }

    @Test
    public void shop_test5() {
        receiving = new Receiver();
        sending = new Sending();
        int cType = 4; //add new product
        int bUserId=28;
        byte bSrc = (byte)544;
        long bPkId = 42342342;
        magazyn = new Shop();
        String group_name = "Drinks";
        magazyn.add_group_of_product(group_name);
        String product = "Juice";
        String query = product + " " + group_name;

        magazyn.process_shop(Processor.process(sending.sendPacket(DataCollector.getByteArray(cType, bUserId, bSrc, bPkId, query), bUserId)));
        magazyn.currentProducts(); //result of command

        Assert.assertNotNull(magazyn);
    }

    @Test
    public void shop_test6() {
        receiving = new Receiver();
        sending = new Sending();
        int cType = 5; //change the price of any product
        int bUserId = 30;
        byte bSrc = (byte) 544;
        long bPkId = 42342344;
        magazyn = new Shop();
        magazyn.add_group_of_product("Drinks");
        magazyn.add_product_to_group("Milk", "Drinks");
        magazyn.add_product("Milk", 33);

        String product = "Milk";
        String price = "50.40";
        String query = product + " " + price;

        magazyn.process_shop(Processor.process(sending.sendPacket(DataCollector.getByteArray(cType, bUserId, bSrc, bPkId, query), bUserId)));
        System.out.println("Price of " + product +" is " + magazyn.getProducts().get(product).getPrice_of_product());
         //result of command

        Assert.assertNotNull(magazyn);
    }
}
