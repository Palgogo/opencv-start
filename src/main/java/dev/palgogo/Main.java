package dev.palgogo;

import dev.palgogo.chapter3.Chapter3;
import org.opencv.core.Core;

public class Main {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        new Chapter3().imgLoadingAndWriting();
    }

}

//TODO add openCV library to gradle properties
//TODO add dependencies for running from gradle