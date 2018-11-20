package com.example.tcprpc.provider;

import com.example.tcprpc.provider.method.ProviderDemo;
import com.example.tcprpc.provider.method.ProviderDemoImpl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class ProviderServer {

    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //用map容器服务的注册中心
        HashMap<String, Object> serviceMap = new HashMap<>();
        serviceMap.put(ProviderDemo.class.getName(), new ProviderDemoImpl());

        ServerSocket serverSocket = new ServerSocket(8899);
        while (true) {
            Socket socket = serverSocket.accept();
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            String interfaceName = inputStream.readUTF();
            String methodName = inputStream.readUTF();

            Class<?>[] parameterTypes = (Class<?>[]) inputStream.readObject();

            Object[] rpcArgs = (Object[]) inputStream.readObject();

            Class<?> providerInterface = Class.forName(interfaceName);
            Object provider = serviceMap.get(interfaceName);
            Method method = providerInterface.getMethod(methodName, parameterTypes);

            Object result = method.invoke(provider, rpcArgs);

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(result);
        }

    }
}
