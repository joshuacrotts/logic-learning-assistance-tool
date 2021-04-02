package com.llat.translate;

import java.util.List;

public interface TranslatorInterface {
    String translate(String langFrom, String langTo, String text);
    List<String> translateList(String langFrom, String langTo, List<String> textList);
}
