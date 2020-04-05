package dev.palgogo;

import dev.palgogo.chapter3.Chapter3;

import java.nio.file.Paths;

public class Main {
    static {
        System.load(Paths.get(".").toAbsolutePath().normalize().toString() + "\\libs\\opencv_java420.dll");
    }

    public static void main(String[] args) {
        new Chapter3().imgLoadingAndWriting();
    }

    //TODO load lybrary in the run proccess search how
}