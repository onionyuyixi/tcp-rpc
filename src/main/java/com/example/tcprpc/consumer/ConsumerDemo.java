package com.example.tcprpc.consumer;

import com.example.tcprpc.provider.method.ProviderDemo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;

public class ConsumerDemo {

    public static void main(String[] args) throws NoSuchMethodException, IOException, ClassNotFoundException {
        String providerInterface = ProviderDemo.class.getName();
        Method method = ProviderDemo.class.getMethod("printMsg", String.class);
        Object[] rpcArgs = {"hello RPC"};
        Socket consumer = new Socket("127.0.0.1", 8899);
        ObjectOutputStream outputStream = new ObjectOutputStream(consumer.getOutputStream());
        outputStream.writeUTF(providerInterface);
        outputStream.writeUTF(method.getName());
        outputStream.writeObject(method.getParameterTypes());
        outputStream.writeObject(rpcArgs);

        //从生产者读取返回结果
        ObjectInputStream input = new ObjectInputStream(consumer.getInputStream());
        Object result = input.readObject();
        System.out.println(result.toString());
        


    }
}
