package xyz.cupscoffee;

import net.querz.mca.MCAFile;
import net.querz.mca.MCAUtil;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        File file = new File("D:\\Projects\\CoffeeFiles\\r.-8.2.mca");
        System.out.println(file.exists());
        MCAFile mcaFile = MCAUtil.read(file);

        System.out.println(mcaFile);
    }
}