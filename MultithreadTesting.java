import clientserver.*;
import clientserver.message.MessageGeneration;
import clientserver.shop.Shop;
import clientserver.packet.DataCollector;

import org.junit.Assert;
import org.junit.Test;

import javax.xml.crypto.Data;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Random;

public class MultithreadTesting {
    private static Shop magazyn;
    private static Receiver receiving;
    private static Sending sending;

    @Test
    public void test() {
        receiving = new Receiver();
        sending = new Sending();
        int bUserId = 29;
        byte bSrc = (byte) 544;
        long bPkId = 42342343;
        magazyn = new Shop();
        magazyn.add_group_of_product("Drinks");
        magazyn.add_product_to_group("Milk", "Drinks");
        magazyn.add_product("Milk", 33);
        magazyn.set_price("Milk", 50.5);
        magazyn.add_product_to_group("Water", "Drinks");
        magazyn.add_product("Water", 40);
        magazyn.set_price("Water", 10.90);
        magazyn.add_product_to_group("7Up", "Drinks");
        magazyn.add_product("7Up", 20);
        magazyn.set_price("7Up", 25);
        magazyn.add_product_to_group("Soda", "Drinks");
        magazyn.add_product("Soda", 15);
        magazyn.set_price("Soda", 33);
        magazyn.add_product_to_group("Zhyvchuk", "Drinks");
        magazyn.add_product("Zhyvchuk", 33);
        magazyn.set_price("Zhyvchuk", 20.20);

        magazyn.add_group_of_product("Food");
        magazyn.add_product_to_group("Bread", "Food");
        magazyn.add_product("Bread", 100);
        magazyn.set_price("Bread", 18);
        magazyn.add_product_to_group("Salt", "Food");
        magazyn.add_product("Salt", 20);
        magazyn.set_price("Salt", 10.90);
        magazyn.add_product_to_group("Sugar", "Food");
        magazyn.add_product("Sugar", 50);
        magazyn.set_price("Sugar", 20.40);
        magazyn.add_product_to_group("Cheesecake", "Food");
        magazyn.add_product("Cheesecake", 10);
        magazyn.set_price("Cheesecake", 45.50);
        magazyn.add_product_to_group("Hot-Dog", "Food");
        magazyn.add_product("Hot-Dog", 40);
        magazyn.set_price("Hot-Dog", 27);

        Threads t1 = new Threads("Thread 1");
        Threads t2 = new Threads("Thread 2");
        try {
            for (int i = 0; i<5; i++){
            Thread.sleep(10);
            int cType = new Random().nextInt(6);
            MessageGeneration gen = new MessageGeneration();
            String product = new String(gen.generate(magazyn, cType).getMessage());
            magazyn.process_shop(Processor.process(sending.sendPacket(DataCollector.getByteArray(cType, bUserId, bSrc, bPkId, product),
                    bUserId)));
            Receiver.receivePacket();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Assert.assertNotNull(magazyn);
    }
}
