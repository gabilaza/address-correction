package com.chill.normalize;

import com.chill.service.TreeAddressService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@Component
public class Translation {
    private final HashMap<String, String> translationMap = new HashMap<>();

    private final TreeAddressService treeAddressService;

    public HashMap<String, String> getTranslationMap() {
        return translationMap;
    }

    public void initTranslate(List<List<String>> words) {
        for (List<String> wordList : words) {
            String valueKey = null;
            for (String word : wordList) {
                if (treeAddressService.existsVertexByLocation(word)) {
                    valueKey = word;
                    break;
                }
            }

            if (valueKey != null) {
                for (String word : wordList) {
                    translationMap.put(word, valueKey);
                }
            }
        }
    }



}
