package com.company;

import com.sun.deploy.util.ArrayUtil;
import com.sun.xml.internal.ws.util.StringUtils;

import java.io.File;
import java.security.PublicKey;
import java.util.*;

/**
 * Created by sagibaz on 4/1/2015.
 */
public class BruteAttack {

    private byte[] m_iv;
    private byte[] m_originalIV;
    private byte[] m_data;

    private List<Character> possibleValues;
    private List<Character> possibles;
    private List<Character> assigns;

    private int m_totalCount=0;

    private double bestTry;
    public List<Character> bestKey;

    private HashSet<String> allWords;

    public BruteAttack(byte[] iv,byte[] data) {

        m_originalIV=iv;
        m_iv = Arrays.copyOf(m_originalIV,10);
        m_data = data;
        bestTry=1;
        possibles = new ArrayList<Character>();
        assigns = new ArrayList<Character>();
        possibleValues = new ArrayList<Character>();

        allWords = new HashSet<String>();

        String[] words = FileHelper.GetTextData("allWords.txt").split("\n");
        for (String word: words) {
            allWords.add(word);
        }


        for (int i = 0; i < 8; i++) {
            possibles.add((char) (i + 97));
            possibleValues.add((char) (i + 97));
        }

    }

    public void BreakKey() {
        if (possibles.isEmpty()) {
            if (++m_totalCount%100==0)
                System.out.print(m_totalCount + "\n");
            CheckAssign();
        }
        for (Character c: possibleValues) {
            if (possibles.contains(c)) {
                possibles.remove(c);
                assigns.add(c);
                BreakKey();
                possibles.add(c);
                assigns.remove(c);
            }
        }
    }

    public void CheckAssign() {

        int totalWords = 0;
        int falseWords = 0;

        m_iv = Arrays.copyOf(m_originalIV, 10);

        Encryptor encryptor = new Encryptor(assigns, m_iv);

        byte[] dataDecrypted = encryptor.DecryptAmount(m_data,10000);

            String str = new String(dataDecrypted);
            String[] tokens = str.split("[\\W]+");
            for (String s : tokens) {
                if ((s.length() == 3 || s.length() == 2) && !s.matches(".*\\d+.*")) {
                    totalWords++;
                    if (!allWords.contains(s))
                        falseWords++;
                }
            }

        double ratio = falseWords / (double) totalWords;

            if (ratio < bestTry) {
                bestTry = ratio;
                bestKey = new ArrayList<Character>(assigns);
            }
        }

    public String GetBestKey() {
        String ans = "";

        Map<Character, Character> treeMap = new TreeMap<Character, Character>();

        for (int i = 0; i < 8; i++) {
            treeMap.put(bestKey.get(i),((char) (i + 97)));
            //ans += bestKey.get(i) + " " + ((char) (i + 97))  + "\n";
        }
        for (Map.Entry<Character, Character> entry : treeMap.entrySet()) {
            ans += entry.getKey()+" "+entry.getValue()+"\n";
        }
        return ans;
    }
}



