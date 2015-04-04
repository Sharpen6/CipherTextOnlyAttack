package com.company;

import java.util.*;

/**
 * Created by User on 03-04-2015.
 */
public class WordsCounter {

    private Map<Character,Integer> m_wordsCounter;
    public double longWordsRatio = 0;

    public WordsCounter() {
        m_wordsCounter = new HashMap<Character,Integer>();
        for (int i=0; i<8; i++) {
            char value = (char) (i + 97);
            Character Value = new Character(value);
            if (!m_wordsCounter.containsKey(Value))
                m_wordsCounter.put(Value,0);
        }
    }
    public void AddChar(char c) {
        if (m_wordsCounter.containsKey(c))
            m_wordsCounter.put(c,m_wordsCounter.get(c)+1);
    }


    public void Add(String word) {
        for (Character c: word.toCharArray()) {
            if (m_wordsCounter.containsKey(c))
                m_wordsCounter.put(c,m_wordsCounter.get(c)+1);
        }
        String[] words =  word.split("[\\W]+");
        int countLong=0;

        for (String s: words) {
            if (s.length()>5)
                countLong++;
        }
        longWordsRatio = countLong/(double)words.length;
    }

    public Character GetMostRepeatingChar() {
        int maxCount = m_wordsCounter.entrySet().iterator().next().getValue();
        Character maxValue = m_wordsCounter.entrySet().iterator().next().getKey();
        for (char value: m_wordsCounter.keySet()) {
            if (m_wordsCounter.get(value)>maxCount)
            {
                maxCount=m_wordsCounter.get(value);
                maxValue=value;
            }
        }
        return maxValue;
    }

    /*public List<Character> GetCountingOrder() {
        List<Character> ans = new ArrayList<Character>();
        for (int i=0; i<8; i++) {
            Character c=GetMostRepeatingChar();
            ans.add(c);
            m_wordsCounter.remove(c);
        }
        return ans;
    }*/

}
