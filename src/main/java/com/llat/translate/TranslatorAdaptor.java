package com.llat.translate;

import java.util.List;

public class TranslatorAdaptor implements TranslatorInterface {

    /**
     *
     */
    private final TranslatorInterface T = new GoogleTranslatorAPI();

    @Override
    public String translate(String langFrom, String langTo, String text) {
        return this.T.translate(langFrom, langTo, text);
    }

    @Override
    public List<String> translateList(String langFrom, String langTo, List<String> textList) {
        return this.T.translateList(langFrom, langTo, textList);
    }
}
