package com.oracle.utils;
import java.util.List;
import java.util.Map;

import com.oracle.main.AutoCorrect;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class AutoCorrectUtilsTest   {

    public static final String FILE_LOCAION = "words.txt";

    @Test
    public void testInitializeDictionaryWithNullFileName(){

        List<String> dictionaryList = AutoCorrectUtils.initializeDictionary(null);

        assert(dictionaryList==null);

    }

    @Test
    public void testInitializeDictionaryWithEmptyileName(){

        List<String> dictionaryList = AutoCorrectUtils.initializeDictionary("");

        assert(dictionaryList==null);

    }

    @Test
    public void testEditMapInitializer(){

        Map<Integer,List<String>> editMap= AutoCorrectUtils.editMapInitializer("Sovan");

        assertEquals(editMap.keySet().size(),"Sovan".length()+1);

        editMap= AutoCorrectUtils.editMapInitializer("");

        assertEquals(editMap.keySet().size(),1);

    }


    @Test
    public void testProcessDistanceDictionary(){

        List<String> testWordList = AutoCorrectUtils.initializeDictionary(FILE_LOCAION);

        Map<Integer,List<String>> editMap = AutoCorrectUtils.processDistanceDictionary("aaa",testWordList);

        assertEquals(editMap.keySet().size(),"aaa".length()+1);

        editMap = AutoCorrectUtils.processDistanceDictionary("",testWordList);

        assertEquals(editMap.keySet().size(),"".length()+1);

    }

    @Test
    public void testGetWordSuggestion() throws Exception{

        List<String> testWordList = AutoCorrectUtils.initializeDictionary(FILE_LOCAION);

         String x  = AutoCorrectUtils.getWordSuggestion("Sovan",()->testWordList);
         assertEquals(x,"Sevan");

         assertEquals("gimel",AutoCorrectUtils.getWordSuggestion("Timel",()->testWordList));

         assertEquals("Addison",AutoCorrectUtils.getWordSuggestion("additon",()->testWordList));

         assertEquals("denominator",AutoCorrectUtils.getWordSuggestion("denomiiiiiiiiiiiiiiiiinator",()->testWordList));

    }


}
