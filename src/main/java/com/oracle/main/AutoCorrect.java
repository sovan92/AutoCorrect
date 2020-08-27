package com.oracle.main;

import com.oracle.exceptions.DictionaryInitializeException;
import com.oracle.utils.AutoCorrectUtils;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AutoCorrect {


    public static void main(String[] args) {

        String FILE_LOCAION = args[0];

        List<String> wordList = AutoCorrectUtils.initializeDictionary(FILE_LOCAION);


        System.out.println("*** Auto Correct Functionality *** ");

        System.out.println("*** Press 0 to terminate ***");

        // Creating a Infinitely running thread.
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            String input = "";
            while (!input.equalsIgnoreCase("0")) {
                input = AutoCorrectUtils.getInput();
                if (input.equalsIgnoreCase("0")) {
                    break;
                }
                try {
                    System.out.println("::Suggested Word:: " + AutoCorrectUtils.getWordSuggestion(input, () -> wordList));
                } catch (DictionaryInitializeException e) {
                    e.printStackTrace();
                    input = "0";
                }

            }
        });

    }
}
