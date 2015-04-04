package com.company;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by User on 30-03-2015.
 */
public class KeysSystem {
    Map<Character, Character> mapTo;
    Map<Character, Character> mapFrom;

    public KeysSystem(List<Character> key) {
        mapTo = FillMapTo(key);
        mapFrom = FillMapBy(key);
    }

    private Map<Character, Character> FillMapTo(List<Character> input) {
        Map<Character, Character> ans = new HashMap<Character, Character>();

        for (int i = 0; i < input.size(); i++) {
            ans.put((char) (i + 97), input.get(i));
        }
        return ans;
    }

    private Map<Character, Character> FillMapBy(List<Character> input) {
        Map<Character, Character> ans = new HashMap<Character, Character>();

        for (int i = 0; i < input.size(); i++) {
            ans.put((char) (i + 97), input.get(i));
        }
        return ans;
    }

    public char isDefinedTo(char x) {
        if (mapTo.containsKey(x))
            return mapTo.get(x);
        return x;
    }

    public char isDefinedBy(char x) {
        if (mapFrom.containsKey(x))
            return mapFrom.get(x);
        return x;
    }
}