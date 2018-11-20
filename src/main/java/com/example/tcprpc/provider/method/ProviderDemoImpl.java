package com.example.tcprpc.provider.method;

public class ProviderDemoImpl implements ProviderDemo {
    @Override
    public String printMsg(String msg) {
        System.out.println("------"+msg+"-----------");
        return "你好"+msg;
    }
}
