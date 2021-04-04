package com.llat.models.localstorage.uidescription;

import com.llat.models.gson.GsonIO;
import com.llat.models.symbols.*;
import com.llat.translate.TranslatorAdaptor;

public class TranslateUIDO {

    public static void main(String[] args) {
        TranslateUIDO main = new TranslateUIDO();
        String langTo = "fr";
//        Gson gson = new Gson();
//        JsonReader reader = new JsonReader(new FileReader("C:\\Users\\PC\\IntelliJIDEAProjects\\Logic-Learning-Assistance-Tool\\src\\main\\resources\\UIDescription_en.json"));
        GsonIO g = new GsonIO("UID/UIDescription_en.json", UIDescriptionObject.class);
        UIDescriptionObject obj = (UIDescriptionObject) g.getData();
        obj = main.translateUIDO(obj, langTo);
        g.update(obj, "UIDescription" + "_" + langTo + ".json");
    }

    public UIDescriptionObject translateUIDO(UIDescriptionObject obj, String langTo) {
        TranslateUIDO main = new TranslateUIDO();
        obj.setImplication((Implication) main.symbol(obj.getImplication(), langTo));

        obj.setBiconditional((Biconditional) main.symbol(obj.getBiconditional(), langTo));

        obj.setConjunction((Conjunction) main.symbol(obj.getConjunction(), langTo));

        obj.setDisjunction((Disjunction) main.symbol(obj.getDisjunction(), langTo));

        obj.setExclusiveDisjunction((ExclusiveDisjunction) main.symbol(obj.getExclusiveDisjunction(), langTo));

        obj.setTurnstile((Turnstile) main.symbol(obj.getTurnstile(), langTo));

        obj.setDoubleTurnstile((DoubleTurnstile) main.symbol(obj.getDoubleTurnstile(), langTo));

        obj.setUniversal((Universal) main.symbol(obj.getUniversal(), langTo));

        obj.setExistential((Existential) main.symbol(obj.getExistential(), langTo));

        return obj;
    }

    public Symbol symbol(Symbol s, String langTo) {
        TranslatorAdaptor ta = new TranslatorAdaptor();
//        Symbol.StringSymbol symbol = s.getSymbol();
        String description = s.getDescription();
        s.setDescription(ta.translate("en", langTo, description));

        Symbol.Axioms axioms = s.getAxioms();
        String explanation = axioms.getExplanation();
        axioms.setExplanation(ta.translate("en", langTo, explanation));
        s.setAxioms(axioms);

        String tooltip = s.getTooltip();
        s.setTooltip(ta.translate("en", langTo, tooltip));

        String readAs = s.getReadAs();
        s.setReadAs(ta.translate("en", langTo, readAs));

        return s;


    }


}
