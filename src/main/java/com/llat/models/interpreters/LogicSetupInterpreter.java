package com.llat.models.interpreters;


import com.llat.input.events.SolvedFormulaEvent;
import com.llat.models.LogicSetup;
import com.llat.models.events.*;
import com.llat.tools.Event;
import com.llat.tools.EventBus;
import com.llat.tools.Listener;
import com.llat.views.events.ApplyAlgorithmEvent;

public class LogicSetupInterpreter implements Listener {

    /**
     *
     */
    private LogicSetup logicSetup;

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
                    updateViewTruthEvent = new UpdateViewTruthEvent(((LogicSetup.LogicTruthAndTree) logicReturn).getTruthValue());
                    updateViewParseTreeEvent = new UpdateViewParseTreeEvent(((LogicSetup.LogicTruthAndTree) logicReturn).getWffTree());
                    updateViewTruthTreeEvent = new UpdateViewTruthTreeEvent(((LogicSetup.LogicTruthAndTree) logicReturn).getWffTree());
                    break;

                case TRUTH_TABLE_GENERATOR:
                    updateViewTruthEvent = new UpdateViewTruthEvent(((LogicSetup.LogicTruthAndTree) logicReturn).getTruthValue());
                    updateViewTruthTableEvent = new UpdateViewTruthTableEvent(((LogicSetup.LogicTruthAndTree) logicReturn).getWffTree());
                    break;

                case MAIN_OPERATOR_DETECTOR:
                case BOUND_VARIABLE_DETECTOR:
                case FREE_VARIABLE_DETECTOR:
                    updateViewParseTreeEvent = new UpdateViewParseTreeEvent(((LogicSetup.LogicTree) logicReturn).getWffTree());
                    break;

                case ARGUMENT_TRUTH_TREE_VALIDATOR:
                    updateViewParseTreeEvent = new UpdateViewParseTreeEvent(((LogicSetup.LogicTree) logicReturn).getWffTree());
                    updateViewTruthTreeEvent = new UpdateViewTruthTreeEvent(((LogicSetup.LogicTree) logicReturn).getWffTree());
                    break;

                case PROPOSITIONAL_TRUTH_TREE_GENERATOR:
                    updateViewParseTreeEvent = new UpdateViewParseTreeEvent(((LogicSetup.LogicTruthTree) logicReturn).getTruthTree().getWff());
                    updateViewTruthTreeEvent = new UpdateViewTruthTreeEvent(((LogicSetup.LogicTruthTree) logicReturn).getTruthTree().getWff());
                    break;

                case RANDOM_FORMULA_GENERATION:
                    break;

                case GROUND_SENTENCE_DETERMINER:
                case CLOSED_SENTENCE_DETERMINER:
                case OPEN_SENTENCE_DETERMINER:
                    updateViewTruthEvent = new UpdateViewTruthEvent(((LogicSetup.LogicTruthAndTree) logicReturn).getTruthValue());
                    updateViewParseTreeEvent = new UpdateViewParseTreeEvent(((LogicSetup.LogicTruthAndTree) logicReturn).getWffTree());
                    break;

                // Build truth tree.
                case PREDICATE_TRUTH_TREE_GENERATOR:
                    updateViewParseTreeEvent = new UpdateViewParseTreeEvent(((LogicSetup.LogicTruthTree) logicReturn).getTruthTree().getWff());
                    break;
            }

            EventBus.throwEvent(updateViewTruthEvent);
            EventBus.throwEvent(updateViewParseTreeEvent);
            EventBus.throwEvent(updateViewTruthTreeEvent);
            EventBus.throwEvent(updateViewTruthTableEvent);
        }
    }

}
