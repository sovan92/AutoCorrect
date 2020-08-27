package com.oracle.utils;

import com.oracle.exceptions.DictionaryInitializeException;

import java.io.*;
import java.nio.file.DirectoryIteratorException;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import com.oracle.model.WordListModel;


public class AutoCorrectUtils {


    // Initilize my dictionary of words
    public static List<String> initializeDictionary(String fileName) {

        List<String> inputList = null;

        if (fileName != null && !fileName.trim().equalsIgnoreCase("")) {
            try {

                File file = new File(fileName);
                if (!file.exists()) {
                    throw new FileNotFoundException(String.format("File with path %s is not found.", file.getAbsolutePath()));
                }
                BufferedReader br = new BufferedReader(new FileReader(file));
                inputList = br.lines().skip(1).map((word) -> word.trim()).collect(Collectors.toList());
                br.close();
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
        return inputList;
    }

    // Initialize my edit Distance map
    public static Map<Integer, List<String>> editMapInitializer(String word) {

        if (word == null || word.trim().length() == 0) {
            word = "";
        }

        Map<Integer, List<String>> stringMap = new HashMap<Integer, List<String>>();
        for (int i = 0; i <= word.length(); i++) {
            stringMap.put(i, new ArrayList<String>());
        }
        return stringMap;
    }

    //Get my Input through Scanner.
    public static String getInput() {

        Scanner sc = new Scanner(System.in);
        System.out.println("::Enter the Word::");
        String input = sc.next();
        return input;
    }


    // Process Edit Distance for all the words in a dictionary .
    // This can be altered to include a Trie data structure.
    public static Map<Integer, List<String>> processDistanceDictionary(String inputWord, List<String> wordLis) {

        Map<Integer, List<String>> editMap = AutoCorrectUtils.editMapInitializer(inputWord);

        int inputLength = inputWord.length();

        for (String word : wordLis) {

            int editDistance = EditDistance.calculateEditDistance(word, inputWord);

            if (editDistance < inputLength) {

                List<String> editMapList = editMap.get(editDistance);
                editMapList.add(word);
            }
        }

        return editMap;
    }

    // Get the Suggested word after going through the list edit distance Map
    public static String getWordSuggestion(String word, WordListModel model) throws DictionaryInitializeException {

        List<String> wordList = model.getWordList();

        if (wordList == null) {
            throw new DictionaryInitializeException();
        }


        int inputWordLength = word.length();
        Map<Integer, List<String>> editMap = processDistanceDictionary(word, wordList);
        for (int i = 0; i < inputWordLength; i++) {

            if (editMap.get(i).size() != 0) {
                return editMap.get(i).get(0);
            }
        }
        return "";
    }

}

