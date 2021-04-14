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
    private String langTo;
    TranslatorAdaptor ta = new TranslatorAdaptor();

    public static void main(String[] args) {
        TranslateUIDO main = new TranslateUIDO("ar");
        String langTo = "ar";
//        Gson gson = new Gson();
//        JsonReader reader = new JsonReader(new FileReader("C:\\Users\\PC\\IntelliJIDEAProjects\\Logic-Learning-Assistance-Tool\\src\\main\\resources\\UIDescription_en.json"));
        GsonIO g = new GsonIO("UID/UIObject_en.json", UIObject.class);
        UIObject obj = (UIObject) g.getData();
        obj = main.translateUIDO(obj);
        g.update(obj, "UIObject" + "_" + langTo + ".json");
    }

    public TranslateUIDO(String langTo) {
        this.langTo = langTo;
    }

    public UIObject translateUIDO(UIObject obj) {
        TranslateUIDO main = new TranslateUIDO(this.langTo);

        // -- Menu Bar --
        MenuBar menuBar = obj.getMenuBar();
        // File menu
        File fileMenu = menuBar.getFile();
        fileMenu.setLabel(ta.translate(LANG_FROM, langTo, menuBar.getFile().getLabel()));
        fileMenu.setNewProject((NewProject) main.menu(fileMenu.getNewProject()));
        fileMenu.setOpenProject((OpenProject) main.menu(fileMenu.getOpenProject()));
        fileMenu.setExport((Export) main.menu(fileMenu.getExport()));
        fileMenu.setLogin((Login) main.menu(fileMenu.getLogin()));
        fileMenu.setRegister((Register) main.menu(fileMenu.getRegister()));
        fileMenu.setSettings((Settings) main.menu(fileMenu.getSettings()));
        fileMenu.setExit((Exit) main.menu(fileMenu.getExit()));
        menuBar.setFile(fileMenu);
        // Help menu
        Help helpMenu = menuBar.getHelp();
        helpMenu.setLabel(ta.translate(LANG_FROM, langTo, helpMenu.getLabel()));
        helpMenu.setAbout((About) main.menu(helpMenu.getAbout()));
        menuBar.setHelp(helpMenu);
        obj.setMenuBar(menuBar);
        // Main View
        MainView mainView = obj.getMainView();
        // Logic Symbols
        LogicSymbols logicSymbols = mainView.getLogicSymbols();
        // Propositional Symbols
        Propositional propositional = logicSymbols.getPropositional();
        propositional.setImplication((Implication) main.symbol(propositional.getImplication(), langTo));
        propositional.setBiconditional((Biconditional) main.symbol(propositional.getBiconditional(), langTo));
        propositional.setNegation((Negation) main.symbol(propositional.getNegation(), langTo));
        propositional.setConjunction((Conjunction) main.symbol(propositional.getConjunction(), langTo));
        propositional.setDisjunction((Disjunction) main.symbol(propositional.getDisjunction(), langTo));
        propositional.setExclusiveDisjunction((ExclusiveDisjunction) main.symbol(propositional.getExclusiveDisjunction(), langTo));
        propositional.setTurnstile((Turnstile) main.symbol(propositional.getTurnstile(), langTo));
        propositional.setDoubleTurnstile((DoubleTurnstile) main.symbol(propositional.getDoubleTurnstile(), langTo));
        logicSymbols.setPropositional(propositional);
        // Predicate Symbols
        Predicate predicate = logicSymbols.getPredicate();
        predicate.setUniversal((Universal) main.symbol(predicate.getUniversal(), langTo));
        predicate.setExistential((Existential) main.symbol(predicate.getExistential(), langTo));
        logicSymbols.setPredicate(predicate);
        // Main View
        MainViewLabels mainViewLabels = mainView.getMainViewLabels();
        mainViewLabels.setPropositionalLabel(ta.translate(LANG_FROM, langTo, mainViewLabels.getPropositionalLabel()));
        mainViewLabels.setPredicateLabel(ta.translate(LANG_FROM, langTo, mainViewLabels.getPredicateLabel()));
        mainViewLabels.setTruthTabelLabel(ta.translate(LANG_FROM, langTo, mainViewLabels.getTruthTabelLabel()));
        mainViewLabels.setTruthTreeLabel(ta.translate(LANG_FROM, langTo, mainViewLabels.getTruthTreeLabel()));
        mainViewLabels.setSymbolNameLabel(ta.translate(LANG_FROM, langTo, mainViewLabels.getSymbolNameLabel()));
        mainViewLabels.setFormalNameLabel(ta.translate(LANG_FROM, langTo, mainViewLabels.getSymbolNameLabel()));
        mainViewLabels.setAlternativeSymbolsLabel(ta.translate(LANG_FROM, langTo, mainViewLabels.getAlternativeSymbolsLabel()));
        mainViewLabels.setExamplesLabel(ta.translate(LANG_FROM, langTo, mainViewLabels.getExamplesLabel()));
        mainViewLabels.setAxiomTabLabel(ta.translate(LANG_FROM, langTo, mainViewLabels.getAxiomTabLabel()));
        mainViewLabels.setHistoryTabLabel(ta.translate(LANG_FROM, langTo, mainViewLabels.getHistoryTabLabel()));
        mainViewLabels.setSolveButton(ta.translate(LANG_FROM, langTo, mainViewLabels.getSolveButton()));
        // General Menu
        GeneralMenu generalMenu = mainViewLabels.getGeneralMenu();
        generalMenu.setLabel(ta.translate(LANG_FROM, langTo, generalMenu.getLabel()));
        for (int i = 0; i < generalMenu.getContent().size(); i++) {
            generalMenu.getContent().get(i).setLabel(ta.translate(LANG_FROM, langTo, generalMenu.getContent().get(i).getLabel()));
        }

        mainViewLabels.setGeneralMenu(generalMenu);
        // Proposition Menu
        PropositionalMenu propositionalMenu = mainViewLabels.getPropositionalMenu();
        propositionalMenu.setLabel(ta.translate(LANG_FROM, langTo, propositionalMenu.getLabel()));
        for (int i = 0; i < propositionalMenu.getContent().size(); i++) {
            propositionalMenu.getContent().get(i).setLabel(ta.translate(LANG_FROM, langTo, propositionalMenu.getContent().get(i).getLabel()));
        }

        mainViewLabels.setPropositionalMenu(propositionalMenu);
        // Predicate Menu
        PredicateMenu predicateMenu = mainViewLabels.getPredicateMenu();
        predicateMenu.setLabel(ta.translate(LANG_FROM, langTo, predicateMenu.getLabel()));
        for (int i = 0; i < predicateMenu.getContent().size(); i++) {
            predicateMenu.getContent().get(i).setLabel(ta.translate(LANG_FROM, langTo, predicateMenu.getContent().get(i).getLabel()));
        }

        mainViewLabels.setPredicateMenu(predicateMenu);

        mainView.setMainViewLabels(mainViewLabels);

        obj.setMainView(mainView);

        // Settings View
        SettingsView settingsView = obj.getSettingsView();
        settingsView.setCancel(ta.translate(LANG_FROM, langTo, settingsView.getCancel()));
        settingsView.setSave(ta.translate(LANG_FROM, langTo, settingsView.getSave()));
        obj.setSettingsView(settingsView);
        // Categories
        Categories categories = settingsView.getCategories();
        // Appearance
        Appearance appearance = categories.getAppearance();
        appearance.setLabel(ta.translate(LANG_FROM, langTo, appearance.getLabel()));
        // Theme
        Theme theme = appearance.getTheme();
        theme.setLabel(ta.translate(LANG_FROM, langTo, theme.getLabel()));
        Applied applied = theme.getApplied();
        applied.setName(ta.translate(LANG_FROM, langTo, applied.getName()));
        theme.setApplied(applied);
        for (int i = 0; i < theme.getAllThemes().size(); i++) {
            theme.getAllThemes().get(i).setName(ta.translate(LANG_FROM, langTo, theme.getAllThemes().get(i).getName()));
        }
        appearance.setTheme(theme);
        categories.setAppearance(appearance);
        // Language
        Language language = categories.getLanguage();
        language.setLabel(ta.translate(LANG_FROM, langTo, language.getLabel()));
        categories.setLanguage(language);
        // Advance
        Advanced advanced = categories.advanced;
        advanced.setLabel(ta.translate(LANG_FROM, langTo, advanced.getLabel()));
        advanced.getTimeOut().setLabel(ta.translate(LANG_FROM, langTo, advanced.getTimeOut().getLabel()));
        categories.setAdvanced(advanced);

        settingsView.setCategories(categories);
        obj.setSettingsView(settingsView);
        // Login
        LoginViewObject loginViewObject = obj.getLoginView();
        loginViewObject.setLoginButton(ta.translate(LANG_FROM, langTo, loginViewObject.getLoginButton()));
        loginViewObject.setReturnButton(ta.translate(LANG_FROM, langTo, loginViewObject.getReturnButton()));
        loginViewObject.setPasswordLabel(ta.translate(LANG_FROM, langTo, loginViewObject.getPasswordLabel()));
        loginViewObject.setPasswordPromptText(ta.translate(LANG_FROM, langTo, loginViewObject.getPasswordPromptText()));
        loginViewObject.setUserNameLabel(ta.translate(LANG_FROM, langTo, loginViewObject.getUserNameLabel()));
        loginViewObject.setUserNamePromptText(ta.translate(LANG_FROM, langTo, loginViewObject.getUserNamePromptText()));

        obj.setLoginView(loginViewObject);

        // Register
        RegisterView registerView = obj.getRegisterView();
        registerView.setRegisterButton(ta.translate(LANG_FROM, langTo, registerView.getRegisterButton()));
        registerView.setReturnButton(ta.translate(LANG_FROM, langTo, registerView.getReturnButton()));
        registerView.setPasswordLabel(ta.translate(LANG_FROM, langTo, registerView.getPasswordLabel()));
        registerView.setPasswordPromptText(ta.translate(LANG_FROM, langTo, registerView.getPasswordPromptText()));
        registerView.setUserNameLabel(ta.translate(LANG_FROM, langTo, registerView.getUserNameLabel()));
        registerView.setUserNamePromptText(ta.translate(LANG_FROM, langTo, registerView.getUserNamePromptText()));
        registerView.setFirstName(ta.translate(LANG_FROM, langTo, registerView.getFirstName()));
        registerView.setFirstNamePromptText(ta.translate(LANG_FROM, langTo, registerView.getFirstNamePromptText()));
        registerView.setLastName(ta.translate(LANG_FROM, langTo, registerView.getLastNamePromptText()));

        obj.setRegisterView(registerView);


        return obj;
    }


    public MenuBarContent menu(MenuBarContent obj) {
        obj.setLabel(ta.translate(LANG_FROM, langTo, obj.getLabel()));

        for (int i = 0; i < obj.getContent().size(); i++) {
            obj.getContent().get(i).setLabel(ta.translate(LANG_FROM, langTo, obj.getContent().get(i).getLabel()));
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
