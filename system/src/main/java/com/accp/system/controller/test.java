package com.accp.system.controller;

import java.util.Scanner;

public class test{
    public static String show(String gs){
        String [] args=gs.split("");

        for (int i=0; i<args.length;i++) {
            int b=0;
//            System.out.println(args[i]);
      if(i<(args.length-1) && args[i+1].equals(args[i])){
            if(i+2<(args.length) && args[i+2].equals(args[i])){
                System.out.println("重复语句是:"+i+args[i]);
                ++b;
            }




      }

        }
        return "";
    }
    public static void main(String [] args){
//        test.show("helloo");
        Scanner input=new Scanner(System.in);
        System.out.print("请输入语句:");
        test.show(input.next());

    }
}