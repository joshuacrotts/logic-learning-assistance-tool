package com.llat.models.localstorage.uidescription;

import com.llat.models.gson.GsonIO;
import com.llat.models.localstorage.uidescription.mainview.*;
import com.llat.models.localstorage.uidescription.menubar.*;
import com.llat.models.localstorage.uidescription.settingsview.*;
import com.llat.models.symbols.*;
import com.llat.translate.TranslatorAdaptor;

import java.util.List;

public class TranslateUIDO {
    private static final String LANG_FROM = "en";
    private final String langTo;
    TranslatorAdaptor ta = new TranslatorAdaptor();

    public TranslateUIDO(String langTo) {
        this.langTo = langTo;
    }

    public static void main(String[] args) {
        TranslateUIDO main = new TranslateUIDO("ar");
        String langTo = "ar";
        GsonIO g = new GsonIO("UID/UIObject_en.json", UIObject.class);
        UIObject obj = (UIObject) g.getData();
        obj = main.translateUIDO(obj);
        g.update(obj, "UIObject" + "_" + langTo + ".json");
    }

    public UIObject translateUIDO(UIObject obj) {
        TranslateUIDO main = new TranslateUIDO(this.langTo);

        // -- Menu Bar --
        MenuBar menuBar = obj.getMenuBar();
        // File menu
        File fileMenu = menuBar.getFile();
        fileMenu.setLabel(this.ta.translate(LANG_FROM, this.langTo, menuBar.getFile().getLabel()));
        fileMenu.setNewProject((NewProject) main.menu(fileMenu.getNewProject()));
        fileMenu.setOpenProject((OpenProject) main.menu(fileMenu.getOpenProject()));
//        fileMenu.setExport((Export) main.menu(fileMenu.getExport()));
        fileMenu.setLogin((Login) main.menu(fileMenu.getLogin()));
        fileMenu.setRegister((Register) main.menu(fileMenu.getRegister()));
        fileMenu.setSettings((Settings) main.menu(fileMenu.getSettings()));
        fileMenu.setExit((Exit) main.menu(fileMenu.getExit()));
        menuBar.setFile(fileMenu);
        // Help menu
        Help helpMenu = menuBar.getHelp();
        helpMenu.setLabel(this.ta.translate(LANG_FROM, this.langTo, helpMenu.getLabel()));
        helpMenu.setAbout((About) main.menu(helpMenu.getAbout()));
        menuBar.setHelp(helpMenu);
        obj.setMenuBar(menuBar);
        // Main View
        MainView mainView = obj.getMainView();
        // Logic Symbols
        LogicSymbols logicSymbols = mainView.getLogicSymbols();
        // Propositional Symbols
        Propositional propositional = logicSymbols.getPropositional();
        propositional.setImplication((Implication) main.symbol(propositional.getImplication(), this.langTo));
        propositional.setBiconditional((Biconditional) main.symbol(propositional.getBiconditional(), this.langTo));
        propositional.setNegation((Negation) main.symbol(propositional.getNegation(), this.langTo));
        propositional.setConjunction((Conjunction) main.symbol(propositional.getConjunction(), this.langTo));
        propositional.setDisjunction((Disjunction) main.symbol(propositional.getDisjunction(), this.langTo));
        propositional.setExclusiveDisjunction((ExclusiveDisjunction) main.symbol(propositional.getExclusiveDisjunction(), this.langTo));
        propositional.setTurnstile((Turnstile) main.symbol(propositional.getTurnstile(), this.langTo));
        propositional.setDoubleTurnstile((DoubleTurnstile) main.symbol(propositional.getDoubleTurnstile(), this.langTo));
        logicSymbols.setPropositional(propositional);
        // Predicate Symbols
        Predicate predicate = logicSymbols.getPredicate();
        predicate.setUniversal((Universal) main.symbol(predicate.getUniversal(), this.langTo));
        predicate.setExistential((Existential) main.symbol(predicate.getExistential(), this.langTo));
        logicSymbols.setPredicate(predicate);
        // Main View
        MainViewLabels mainViewLabels = mainView.getMainViewLabels();
        mainViewLabels.setPropositionalLabel(this.ta.translate(LANG_FROM, this.langTo, mainViewLabels.getPropositionalLabel()));
        mainViewLabels.setPredicateLabel(this.ta.translate(LANG_FROM, this.langTo, mainViewLabels.getPredicateLabel()));
        mainViewLabels.setTruthTableLabel(this.ta.translate(LANG_FROM, this.langTo, mainViewLabels.getTruthTableLabel()));
        mainViewLabels.setTruthTreeLabel(this.ta.translate(LANG_FROM, this.langTo, mainViewLabels.getTruthTreeLabel()));
        mainViewLabels.setSymbolNameLabel(this.ta.translate(LANG_FROM, this.langTo, mainViewLabels.getSymbolNameLabel()));
        mainViewLabels.setFormalNameLabel(this.ta.translate(LANG_FROM, this.langTo, mainViewLabels.getSymbolNameLabel()));
        mainViewLabels.setAlternativeSymbolsLabel(this.ta.translate(LANG_FROM, this.langTo, mainViewLabels.getAlternativeSymbolsLabel()));
        mainViewLabels.setExamplesLabel(this.ta.translate(LANG_FROM, this.langTo, mainViewLabels.getExamplesLabel()));
        mainViewLabels.setAxiomTabLabel(this.ta.translate(LANG_FROM, this.langTo, mainViewLabels.getAxiomTabLabel()));
        mainViewLabels.setHistoryTabLabel(this.ta.translate(LANG_FROM, this.langTo, mainViewLabels.getHistoryTabLabel()));
        mainViewLabels.setSolveButton(this.ta.translate(LANG_FROM, this.langTo, mainViewLabels.getSolveButton()));
        // General Menu
        GeneralMenu generalMenu = mainViewLabels.getGeneralMenu();
        generalMenu.setLabel(this.ta.translate(LANG_FROM, this.langTo, generalMenu.getLabel()));
        for (int i = 0; i < generalMenu.getContent().size(); i++) {
            generalMenu.getContent().get(i).setLabel(this.ta.translate(LANG_FROM, this.langTo, generalMenu.getContent().get(i).getLabel()));
        }

        mainViewLabels.setGeneralMenu(generalMenu);
        // Proposition Menu
        PropositionalMenu propositionalMenu = mainViewLabels.getPropositionalMenu();
        propositionalMenu.setLabel(this.ta.translate(LANG_FROM, this.langTo, propositionalMenu.getLabel()));
        for (int i = 0; i < propositionalMenu.getContent().size(); i++) {
            propositionalMenu.getContent().get(i).setLabel(this.ta.translate(LANG_FROM, this.langTo, propositionalMenu.getContent().get(i).getLabel()));
        }

        mainViewLabels.setPropositionalMenu(propositionalMenu);
        // Predicate Menu
        PredicateMenu predicateMenu = mainViewLabels.getPredicateMenu();
        predicateMenu.setLabel(this.ta.translate(LANG_FROM, this.langTo, predicateMenu.getLabel()));
        for (int i = 0; i < predicateMenu.getContent().size(); i++) {
            predicateMenu.getContent().get(i).setLabel(this.ta.translate(LANG_FROM, this.langTo, predicateMenu.getContent().get(i).getLabel()));
        }

        mainViewLabels.setPredicateMenu(predicateMenu);

        mainView.setMainViewLabels(mainViewLabels);

        obj.setMainView(mainView);

        // Settings View
        SettingsView settingsView = obj.getSettingsView();
        settingsView.setCancel(this.ta.translate(LANG_FROM, this.langTo, settingsView.getCancel()));
        settingsView.setSave(this.ta.translate(LANG_FROM, this.langTo, settingsView.getSave()));
        obj.setSettingsView(settingsView);
        // Categories
        Categories categories = settingsView.getCategories();
        // Appearance
        Appearance appearance = categories.getAppearance();
        appearance.setLabel(this.ta.translate(LANG_FROM, this.langTo, appearance.getLabel()));
        // Theme
        Theme theme = appearance.getTheme();
        theme.setLabel(this.ta.translate(LANG_FROM, this.langTo, theme.getLabel()));
        Applied applied = theme.getApplied();
        applied.setName(this.ta.translate(LANG_FROM, this.langTo, applied.getName()));
        theme.setApplied(applied);
        for (int i = 0; i < theme.getAllThemes().size(); i++) {
            theme.getAllThemes().get(i).setName(this.ta.translate(LANG_FROM, this.langTo, theme.getAllThemes().get(i).getName()));
        }
        appearance.setTheme(theme);
        categories.setAppearance(appearance);
        // Language
        Language language = categories.getLanguage();
        language.setLabel(this.ta.translate(LANG_FROM, this.langTo, language.getLabel()));
        categories.setLanguage(language);
        // Advance
        Advanced advanced = categories.advanced;
        advanced.setLabel(this.ta.translate(LANG_FROM, this.langTo, advanced.getLabel()));
        advanced.getTimeOut().setLabel(this.ta.translate(LANG_FROM, this.langTo, advanced.getTimeOut().getLabel()));
        categories.setAdvanced(advanced);

        settingsView.setCategories(categories);
        obj.setSettingsView(settingsView);
        // Login
        LoginViewObject loginViewObject = obj.getLoginView();
        loginViewObject.setLoginButton(this.ta.translate(LANG_FROM, this.langTo, loginViewObject.getLoginButton()));
        loginViewObject.setReturnButton(this.ta.translate(LANG_FROM, this.langTo, loginViewObject.getReturnButton()));
        loginViewObject.setPasswordLabel(this.ta.translate(LANG_FROM, this.langTo, loginViewObject.getPasswordLabel()));
        loginViewObject.setPasswordPromptText(this.ta.translate(LANG_FROM, this.langTo, loginViewObject.getPasswordPromptText()));
        loginViewObject.setUserNameLabel(this.ta.translate(LANG_FROM, this.langTo, loginViewObject.getUserNameLabel()));
        loginViewObject.setUserNamePromptText(this.ta.translate(LANG_FROM, this.langTo, loginViewObject.getUserNamePromptText()));

        obj.setLoginView(loginViewObject);

        // Register
        RegisterView registerView = obj.getRegisterView();
        registerView.setRegisterButton(this.ta.translate(LANG_FROM, this.langTo, registerView.getRegisterButton()));
        registerView.setReturnButton(this.ta.translate(LANG_FROM, this.langTo, registerView.getReturnButton()));
        registerView.setPasswordLabel(this.ta.translate(LANG_FROM, this.langTo, registerView.getPasswordLabel()));
        registerView.setPasswordPromptText(this.ta.translate(LANG_FROM, this.langTo, registerView.getPasswordPromptText()));
        registerView.setUserNameLabel(this.ta.translate(LANG_FROM, this.langTo, registerView.getUserNameLabel()));
        registerView.setUserNamePromptText(this.ta.translate(LANG_FROM, this.langTo, registerView.getUserNamePromptText()));
        registerView.setFirstName(this.ta.translate(LANG_FROM, this.langTo, registerView.getFirstName()));
        registerView.setFirstNamePromptText(this.ta.translate(LANG_FROM, this.langTo, registerView.getFirstNamePromptText()));
        registerView.setLastName(this.ta.translate(LANG_FROM, this.langTo, registerView.getLastNamePromptText()));

        obj.setRegisterView(registerView);


        return obj;
    }


    public MenuBarContent menu(MenuBarContent obj) {
        obj.setLabel(this.ta.translate(LANG_FROM, this.langTo, obj.getLabel()));

        for (int i = 0; i < obj.getContent().size(); i++) {
            obj.getContent().get(i).setLabel(this.ta.translate(LANG_FROM, this.langTo, obj.getContent().get(i).getLabel()));
        }
        return obj;
    }

    public Symbol symbol(Symbol s, String langTo) {
        TranslatorAdaptor ta = new TranslatorAdaptor();
//        com.llat.models.localstorage.Symbol.StringSymbol symbol = s.getSymbol();
        String description = s.getDescription();
        s.setDescription(ta.translate("en", langTo, description));

        Symbol.Axioms axioms = s.getAxioms();
        String explanation = axioms.getExplanation();
        axioms.setExplanation(ta.translate("en", langTo, explanation));
        List<String> examples = axioms.getExample();
        for (int i = 0; i < examples.size(); i++) {
            examples.set(i, ta.translate("en", langTo, axioms.getExample().get(i)));
        }
        axioms.setExample(examples);
        s.setAxioms(axioms);

        String tooltip = s.getTooltip();
        s.setTooltip(ta.translate("en", langTo, tooltip));

        String readAs = s.getReadAs();
        s.setReadAs(ta.translate("en", langTo, readAs));

        return s;


    }


}
