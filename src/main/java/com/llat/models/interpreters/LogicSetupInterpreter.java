package com.llat.models.interpreters;


import com.llat.input.events.SolvedFormulaEvent;
import com.llat.models.LogicSetup;
import com.llat.models.events.*;
import com.llat.tools.Event;
import com.llat.tools.EventBus;
import com.llat.tools.Listener;
import com.llat.views.events.ApplyAlgorithmEvent;
import com.llat.views.events.ExportLatexParseTreeEvent;
import com.llat.views.events.ExportLatexTruthTableEvent;
import com.llat.views.events.ExportLatexTruthTreeEvent;

public class LogicSetupInterpreter implements Listener {

    /**
     *
     */
    private final LogicSetup logicSetup;

    public LogicSetupInterpreter(LogicSetup _logicSetup) {
        this.logicSetup = _logicSetup;
        EventBus.addListener(this);
    }

    @Override
    public void catchEvent(Event _event) {
        if (_event instanceof SolvedFormulaEvent) {
            this.logicSetup.setWffTree(((SolvedFormulaEvent) _event).getWffTree());
            EventBus.throwEvent(new SetAlgorithmInputEvent(this.logicSetup.getAvailableAlgorithms()));
        } else if (_event instanceof ApplyAlgorithmEvent) {
            LogicSetup.LogicReturn logicReturn = this.logicSetup.detectAlgorithm(this.logicSetup.convertStringToAlgorithmType(((ApplyAlgorithmEvent) _event).getAlgorithmType()));
            UpdateViewTruthEvent updateViewTruthEvent = new UpdateViewTruthEvent();
            UpdateViewParseTreeEvent updateViewParseTreeEvent = new UpdateViewParseTreeEvent();
            UpdateViewTruthTreeEvent updateViewTruthTreeEvent = new UpdateViewTruthTreeEvent();
            UpdateViewTruthTableEvent updateViewTruthTableEvent = new UpdateViewTruthTableEvent();

            switch (this.logicSetup.convertStringToAlgorithmType(((ApplyAlgorithmEvent) _event).getAlgorithmType())) {
                case CLOSED_TREE_DETERMINER:
                case LOGICAL_FALSEHOOD_DETERMINER:
                case LOGICALLY_CONTINGENT_DETERMINER:
                case LOGICAL_TAUTOLOGY_DETERMINER:
                case OPEN_TREE_DETERMINER:
                case LOGICALLY_CONTRADICTORY_DETERMINER:
                case LOGICALLY_CONSISTENT_DETERMINER:
                case LOGICALLY_CONTRARY_DETERMINER:
                case LOGICALLY_EQUIVALENT_DETERMINER:
                case LOGICALLY_IMPLIED_DETERMINER:
                case GROUND_SENTENCE_DETERMINER:
                case CLOSED_SENTENCE_DETERMINER:
                case OPEN_SENTENCE_DETERMINER:
                    updateViewTruthEvent = new UpdateViewTruthEvent(((LogicSetup.LogicTruthAndParseTree) logicReturn).getTruthValue());
                    updateViewParseTreeEvent = new UpdateViewParseTreeEvent(((LogicSetup.LogicTruthAndParseTree) logicReturn).getWffTree());
                    break;

                case TRUTH_TABLE_GENERATOR:
                    updateViewTruthEvent = new UpdateViewTruthEvent(((LogicSetup.LogicTruthAndParseTree) logicReturn).getTruthValue());
                    updateViewTruthTableEvent = new UpdateViewTruthTableEvent(((LogicSetup.LogicTruthAndParseTree) logicReturn).getWffTree());
                    break;

                case MAIN_OPERATOR_DETECTOR:
                case BOUND_VARIABLE_DETECTOR:
                case FREE_VARIABLE_DETECTOR:
                    updateViewParseTreeEvent = new UpdateViewParseTreeEvent(((LogicSetup.LogicTree) logicReturn).getWffTree());
                    break;

                case ARGUMENT_TRUTH_TREE_VALIDATOR:
                case PREDICATE_TRUTH_TREE_GENERATOR:
                case PROPOSITIONAL_TRUTH_TREE_GENERATOR:
                    updateViewParseTreeEvent = new UpdateViewParseTreeEvent(((LogicSetup.LogicParseAndTruthTree) logicReturn).getWffTree());
                    updateViewTruthTreeEvent = new UpdateViewTruthTreeEvent(((LogicSetup.LogicParseAndTruthTree) logicReturn).getTruthTree());
                    break;

                case RANDOM_FORMULA_GENERATION:
                    break;
            }

            EventBus.throwEvent(updateViewTruthEvent);
            EventBus.throwEvent(updateViewParseTreeEvent);
            EventBus.throwEvent(updateViewTruthTreeEvent);
            EventBus.throwEvent(updateViewTruthTableEvent);
        }
        else if (_event instanceof ExportLatexParseTreeEvent) {
            System.out.println("Exporting latex parse tree event.");
            System.out.println(((ExportLatexParseTreeEvent) _event).getFilePath());
        }
        else if (_event instanceof ExportLatexTruthTableEvent) {
            System.out.println("Exporting latex truth table event.");
            System.out.println(((ExportLatexTruthTableEvent) _event).getFilePath());
        }
        else if (_event instanceof ExportLatexTruthTreeEvent) {
            System.out.println("Exporting latex truth tree event.");
            System.out.println(((ExportLatexTruthTreeEvent) _event).getFilePath());
        }
    }

}
