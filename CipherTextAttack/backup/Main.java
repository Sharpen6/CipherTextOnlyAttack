package com.company;

public class Main {

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Invalid Input");
            return;
        }
        if (args[0].equals("Decryption"))
            FindKey(args[1], args[2]);

    }
    private static void FindKey(String cipherTextPath, String ivPath) {
        byte[] cipherText = FileHelper.GetBinaryData(cipherTextPath);
        byte[] iv = FileHelper.GetBinaryData(ivPath);
        BruteAttack brAttack = new BruteAttack(iv,cipherText);
        brAttack.BreakKey();
        String key = brAttack.GetBestKey();

        FileHelper.WriteToFileAsText(key,cipherTextPath+"_key.txt");
    }
}
