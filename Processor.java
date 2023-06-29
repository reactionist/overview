package clientserver;

import clientserver.message.ObjectDomain;
import clientserver.packet.Packet;
import clientserver.message.Message;
import clientserver.shop.Shop;
import clientserver.textcode.Decriptor;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Processor {
    private static Decriptor decript;
    public static ObjectDomain process(Packet packet){
        byte[] receivedPacket = packet.getPack_in_bytes();;
        byte[] decoded_message = new byte[receivedPacket.length-16-8-2];
        System.arraycopy(receivedPacket, 24, decoded_message, 0, decoded_message.length);
        decoded_message = Decriptor.decrypt(decoded_message);
        byte[] decodedPacket = new byte[16+8+decoded_message.length+2];
        System.arraycopy(receivedPacket, 0, decodedPacket, 0, 16 + 8);
        for (int i=0;i<decoded_message.length;i++){
            decodedPacket[16+8+i] = decoded_message[i];
        }
        decodedPacket[decodedPacket.length-2] = receivedPacket[receivedPacket.length-2];
        decodedPacket[decodedPacket.length-1] = receivedPacket[receivedPacket.length-1];
        receivedPacket = decodedPacket;
        int command = packet.getbMsg().getcType();
        ObjectDomain od = null;
        if (command == 0 || command == 3){
            byte[] product_byte = new byte[decoded_message.length];
            System.arraycopy(decoded_message, 0, product_byte, 0, decoded_message.length);
            String product = new String(product_byte, StandardCharsets.UTF_8);
            od = new ObjectDomain(product, command);
        }
        if (command == 1 || command == 2){
            String query = new String(decoded_message);
            char[] query_check = query.toCharArray();
            int i = 0;
            while (query_check[i] != ' '){
                i++;
            }
            char[] prod = new char[i];
            char[] num = new char[query_check.length - i - 1];
            System.arraycopy(query_check, 0, prod, 0, i);
            System.arraycopy(query_check,i+1,num,0,query_check.length - i - 1);
            String product = new String(prod);
            String num_str = new String(num);
            int num_int = Integer.parseInt(num_str);
            od = new ObjectDomain(product, num_int, command);
        }
        if (command == 5){
            String query = new String(decoded_message);
            char[] query_check = query.toCharArray();
            int i = 0;
            while (query_check[i] != ' '){
                i++;
            }
            char[] prod = new char[i];
            char[] num = new char[query_check.length - i - 1];
            System.arraycopy(query_check, 0, prod, 0, i);
            System.arraycopy(query_check,i+1,num,0,query_check.length - i - 1);
            String product = new String(prod);
            String num_str = new String(num);
            double num_d = Double.parseDouble(num_str);
            od = new ObjectDomain(product, num_d, command);
        }
        if (command == 4){
            String products = new String(decoded_message);
            String[] products_array = products.split(" ");
            String product = products_array[0];
            String group = products_array[1];
            od = new ObjectDomain(product, group, command);
        }
        return od;
        }
    }
