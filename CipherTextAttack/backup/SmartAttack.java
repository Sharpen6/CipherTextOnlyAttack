package com.company;

import java.util.*;

/**
 * Created by sagibaz on 4/1/2015.
 */
public class SmartAttack {
    private byte[] m_iv;
    private byte[] m_originalIV;
    private byte[] m_xorVector;
    private byte[] m_data;

    private List<Character> possibleValues;
    private List<Character> possibles;
    private List<Character> assigns;

    public Map<String, Double> m_tries;
    private int m_totalCount = 0;

    private String possibleBytes;

    private double bestTry;
    public List<Character> bestKey;


    public SmartAttack(byte[] iv, byte[] data) {

        m_data = data;
        /* create vector for the decryption */
        m_xorVector = Arrays.copyOf(iv, data.length + 10);
        for (int i = 0; i < data.length; i++)
            m_xorVector[i + 10] = m_data[i];

        m_originalIV = iv;
        m_iv = Arrays.copyOf(m_originalIV, 10);

        bestTry = 1;
        possibles = new ArrayList<Character>();
        assigns = new ArrayList<Character>();
        possibleValues = new ArrayList<Character>();

        possibleBytes = FileHelper.GetTextData("allLetters.txt");
        possibleBytes += "\r";


        m_tries = new HashMap<String, Double>();

        for (int i = 0; i < 8; i++) {
            possibles.add((char) (i + 97));
            possibleValues.add((char) (i + 97));
        }

    }

    public void GetLetterA() {

        //assigns.add('a');assigns.add('b');assigns.add('c');assigns.add('d');
        //assigns.add('e');assigns.add('f');assigns.add('g');assigns.add('h');

        Encryptor encryptor = new Encryptor(assigns, m_iv);

        byte[] text = encryptor.Decrypt(m_data);

        WordsCounter wc = new WordsCounter();

        for (int i = 1; i < m_data.length - 2; i++) {
            //if (( (int)m_data[i - 1] ^ (int) m_xorVector[i - 1]) == 32
                //    && ( (int)m_data[i] ^ (int) m_xorVector[i]) != 32
                //    && ( (int)m_data[i + 1] ^ (int) m_xorVector[i + 1]) == 32) {
                System.out.print((char)( (int)m_data[i] ^ (int) m_xorVector[i]));
                //wc.AddChar((char)( (int)m_data[i] ^ (int) m_xorVector[i]));
           //}
        }
        Character c = wc.GetMostRepeatingChar();

    }
}
        /*

        String[] words = str.split("[\\W]+");

        for (String word: words) {
            if (word.length()==1) {
                int xValue = word.charAt(0);
                int yValue = m_xorVector[];
                int xorValue = xValue^yValue;
                byte c = (byte)xorValue;
            }

        }

    }

    public void BreakKey() {
        if (possibles.isEmpty()) {
            for (int i = 0; i < 8; i++) {
                System.out.print(((char) (i + 97)) + " " + assigns.get(i) + "\n");
            }
            System.out.print(++m_totalCount + "\n");
            if (m_totalCount==430)
                System.out.print("here!");
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
        double ratio=0;

        m_iv = Arrays.copyOf(m_originalIV,10);
        Encryptor encryptor = new Encryptor(assigns, m_iv);

        //Byte[] dataDecryptedBytes = (Byte[]) encryptor.AppendDecrypt(m_data, 100);
        //byte[] dataDecrypted = new byte[dataDecryptedBytes.length];
        byte[] dataDecrypted = encryptor.Decrypt(m_data);
        //for (int i = 0; i < dataDecryptedBytes.length; i++) {
        //    dataDecrypted[i] = dataDecryptedBytes[i].byteValue();

        char[] charArr = (new String(dataDecrypted)).toCharArray();
        for (int i=0;i<dataDecrypted.length;i++) {
            char c = charArr[i];
            if (possibleBytes.indexOf(c)==-1)
                return;
        }

        String str = new String(dataDecrypted);

        StringTokenizer stz = new StringTokenizer(str,"[ ,\n\r]+");
        while (stz.hasMoreTokens()) {
            totalWords++;
            String s = stz.nextToken();
            if (s.length() > 7) {
                falseWords++;
            }
        }

        ratio = falseWords / (double) totalWords;

        if (ratio<bestTry) {
            bestTry = ratio;
            bestKey = new ArrayList<Character>(assigns);
        }

        //WordsCounter wc = new WordsCounter();
        //wc.Add(str);
        //Character c = wc.GetMostRepeatingChar();

    }
    public String GetBestKey() {
        String ans = "";
        for (int i = 0; i < 8; i++) {
            ans += ((char) (i + 97)) + " " + bestKey.get(i) + "\n";
        }
        return ans;
    }
}



*/