package translate;

import java.util.List;

public class TranslatorAdaptor implements TranslatorInterface {
    TranslatorInterface T = new GoogleTranslatorAPI();

    @Override
    public String translate(String langFrom, String langTo, String text) {
        return T.translate(langFrom, langTo, text);
    }

    @Override
    public List<String> translateList(String langFrom, String langTo, List<String> textList) {
        return T.translateList(langFrom, langTo, textList);
    }
}
