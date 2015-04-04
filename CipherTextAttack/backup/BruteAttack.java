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

    public Map<String,Double> m_tries;
    private int m_totalCount=0;

    private String possibleBytes;

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

        possibleBytes = FileHelper.GetTextData("allLetters.txt");
        possibleBytes+="\r";
        allWords = new HashSet<String>();

        String[] words = FileHelper.GetTextData("allWords.txt").split("\n");
        for (String word: words) {
            allWords.add(word);
        }

        m_tries = new HashMap<String,Double>();

        for (int i = 0; i < 8; i++) {
            possibles.add((char) (i + 97));
            possibleValues.add((char) (i + 97));
        }

    }

    public void BreakKey() {
        if (possibles.isEmpty()) {
            //for (int i = 0; i < 8; i++) {
                //System.out.print(((char) (i + 97)) + " " + assigns.get(i) + "\n");
            //}
            if (++m_totalCount%100==0)
                System.out.print(m_totalCount + "\n");
            //if (m_totalCount==36496)
            //    System.out.print("here!");
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

        //String key = "";
        //for (int i = 0; i < 7; i++) {
        //    key += ((char) (i + 97)) + " " + assigns.get(i) + "\n";
        //}
        //key += ((char) (7 + 97)) + " " + assigns.get(7);
    }

    public void CheckAssign() {

        int totalWords = 0;
        int falseWords = 0;
        double ratio = 0;

        m_iv = Arrays.copyOf(m_originalIV, 10);

        Encryptor encryptor = new Encryptor(assigns, m_iv);

        //for (int k=100;k<m_data.length;k=k*2) {
            //Byte[] dataDecryptedBytes = (Byte[]) encryptor.AppendDecrypt(m_data, 1000);

            //byte[] dataDecrypted = new byte[dataDecryptedBytes.length];
            //byte[] dataDecrypted = encryptor.Decrypt(m_data);
        byte[] dataDecrypted = encryptor.DecryptAmount(m_data,10000);
            //for (int i = 0; i < dataDecryptedBytes.length; i++)
            //    dataDecrypted[i] = dataDecryptedBytes[i].byteValue();
        /*
        char[] charArr = (new String(dataDecrypted)).toCharArray();
        for (int i=0;i<dataDecrypted.length;i++) {
            char c = charArr[i];
            if (possibleBytes.indexOf(c)==-1)
                return;
        }
        */
            String str = new String(dataDecrypted);
            String[] tokens = str.split("[\\W]+");
            for (String s : tokens) {
                if ((s.length() == 3 || s.length() == 2) && !s.matches(".*\\d+.*")) {
                    totalWords++;
                    if (!allWords.contains(s))
                        falseWords++;
                }
            }

/*
        StringTokenizer stz = new StringTokenizer(str,"[ ,\n\r]+");
        while (stz.hasMoreTokens()) {
            totalWords++;
            String s = stz.nextToken();
            if (s.length() > 7) {
                falseWords++;
            }
        }
*/
            ratio = falseWords / (double) totalWords;

            if (ratio < bestTry) {
                bestTry = ratio;
                bestKey = new ArrayList<Character>(assigns);
            } //else
         //   {
          //      continue;
         //   }


            //WordsCounter wc = new WordsCounter();
            //wc.Add(str);
            //Character c = wc.GetMostRepeatingChar();
        //}

        }

    public String GetBestKey() {
        String ans = "";
        for (int i = 0; i < 8; i++) {
            ans += ((char) (i + 97)) + " " + bestKey.get(i) + "\n";
        }
        return ans;
    }
}



