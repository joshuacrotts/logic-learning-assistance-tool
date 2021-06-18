package com.llat.models.interpreters;


import com.llat.algorithms.*;
import com.llat.algorithms.predicate.PredicateTruthTreeGenerator;
import com.llat.algorithms.propositional.PDFTruthTablePrinter;
import com.llat.algorithms.propositional.PropositionalTruthTreeGenerator;
import com.llat.algorithms.propositional.TexTablePrinter;
import com.llat.input.events.SolvedFormulaEvent;
import com.llat.input.events.UnsolvedFormulaEvent;
import com.llat.models.LogicSetup;
import com.llat.models.events.*;
import com.llat.models.treenode.WffTree;
import com.llat.tools.Event;
import com.llat.tools.EventBus;
import com.llat.tools.Listener;
import com.llat.views.events.*;

public class LogicSetupInterpreter implements Listener {

    /**
     *
     */
    private final LogicSetup logicSetup;
    private WffTree outputTree;

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
            RandomGeneratedFormulaEvent randomGeneratedFormulaEvent = new RandomGeneratedFormulaEvent();
            if (!(logicReturn instanceof LogicSetup.LogicVoid)) {
                switch (this.logicSetup.convertStringToAlgorithmType(((ApplyAlgorithmEvent) _event).getAlgorithmType())) {
                    case RANDOM_PREDICATE_FORMULA:
                    case RANDOM_PROPOSITIONAL_FORMULA:
                        this.logicSetup.setWffTree(null);
                        this.outputTree = null;
                        randomGeneratedFormulaEvent = new RandomGeneratedFormulaEvent(((LogicSetup.LogicFormula) (logicReturn)).getFormula());
                        EventBus.throwEvent(new SetAlgorithmInputEvent(this.logicSetup.getAvailableAlgorithms()));
                        break;

                    case CLOSED_TREE_DETERMINER:
                    case LOGICALLY_CONTINGENT_DETERMINER:
                    case OPEN_TREE_DETERMINER:
                    case GROUND_SENTENCE_DETERMINER:
                    case CLOSED_SENTENCE_DETERMINER:
                    case OPEN_SENTENCE_DETERMINER:
                        this.outputTree = ((LogicSetup.LogicTruthAndParseTree) logicReturn).getWffTree();
                        updateViewTruthEvent = new UpdateViewTruthEvent(((LogicSetup.LogicTruthAndParseTree) logicReturn).getTruthValue());
                        updateViewParseTreeEvent = new UpdateViewParseTreeEvent(this.outputTree);
                        break;

                    case TRUTH_TABLE_GENERATOR:
                        this.outputTree = ((LogicSetup.LogicTruthAndParseTree) logicReturn).getWffTree();
                        updateViewTruthEvent = new UpdateViewTruthEvent(((LogicSetup.LogicTruthAndParseTree) logicReturn).getTruthValue());
                        updateViewTruthTableEvent = new UpdateViewTruthTableEvent(this.outputTree);
                        break;

                    case MAIN_OPERATOR_DETECTOR:
                    case BOUND_VARIABLE_DETECTOR:
                    case FREE_VARIABLE_DETECTOR:
                        this.outputTree = ((LogicSetup.LogicTree) logicReturn).getWffTree();
                        updateViewParseTreeEvent = new UpdateViewParseTreeEvent(this.outputTree);
                        break;

                    case PREDICATE_TRUTH_TREE_GENERATOR:
                    case PROPOSITIONAL_TRUTH_TREE_GENERATOR:
                        this.outputTree = ((LogicSetup.LogicParseAndTruthTree) logicReturn).getWffTree();
                        updateViewParseTreeEvent = new UpdateViewParseTreeEvent(this.outputTree);
                        updateViewTruthTreeEvent = new UpdateViewTruthTreeEvent(((LogicSetup.LogicParseAndTruthTree) logicReturn).getTruthTree());
                        break;

                    case LOGICALLY_CONSISTENT_DETERMINER:
                    case LOGICALLY_CONTRARY_DETERMINER:
                    case LOGICAL_TAUTOLOGY_DETERMINER:
                    case LOGICALLY_IMPLIED_DETERMINER:
                    case LOGICAL_FALSEHOOD_DETERMINER:
                    case LOGICALLY_CONTRADICTORY_DETERMINER:
                    case LOGICALLY_EQUIVALENT_DETERMINER:
                    case ARGUMENT_TRUTH_TREE_VALIDATOR:
                    case SEMANTIC_ENTAILMENT_DETERMINER:
                        this.outputTree = ((LogicSetup.LogicTruthParseAndTruthTree) logicReturn).getWffTree();
                        this.outputTree.setFlags(((LogicSetup.LogicTruthParseAndTruthTree) logicReturn).getWffTree().getFlags());
                        updateViewTruthEvent = new UpdateViewTruthEvent(((LogicSetup.LogicTruthParseAndTruthTree) logicReturn).getTruthValue());
                        updateViewParseTreeEvent = new UpdateViewParseTreeEvent(this.outputTree);
                        updateViewTruthTreeEvent = new UpdateViewTruthTreeEvent(((LogicSetup.LogicTruthParseAndTruthTree) logicReturn).getTruthTree());
                        break;
                }
            }

            EventBus.throwEvent(randomGeneratedFormulaEvent);
            EventBus.throwEvent(updateViewTruthEvent);
            EventBus.throwEvent(updateViewParseTreeEvent);
            EventBus.throwEvent(updateViewTruthTreeEvent);
            EventBus.throwEvent(updateViewTruthTableEvent);
        } else if (_event instanceof ExportPDFParseTreeEvent) {
            PDFPrinter pdfParseTreePrinter = new PDFParseTreePrinter(this.outputTree, ((ExportPDFParseTreeEvent) _event).getFilePath());
            pdfParseTreePrinter.outputToFile();
        } else if (_event instanceof ExportPDFTruthTableEvent) {
            PDFPrinter pdfTruthTablePrinter = new PDFTruthTablePrinter(this.outputTree, ((ExportPDFTruthTableEvent) _event).getFilePath());
            pdfTruthTablePrinter.outputToFile();
        } else if (_event instanceof ExportPDFTruthTreeEvent) {
            BaseTruthTreeGenerator truthTreeGenerator;
            truthTreeGenerator = (this.outputTree.isPropositionalWff()) ? new PropositionalTruthTreeGenerator(this.outputTree) : new PredicateTruthTreeGenerator(this.outputTree);
            PDFTruthTreePrinter pdfTruthTreePrinter = new PDFTruthTreePrinter(truthTreeGenerator.getTruthTree(), ((ExportPDFTruthTreeEvent) _event).getFilePath());
            pdfTruthTreePrinter.outputToFile();
        } else if (_event instanceof ExportLaTeXParseTreeEvent) {
            TexParseTreePrinter texParseTreePrinter = new TexParseTreePrinter(this.outputTree, ((ExportLaTeXParseTreeEvent) _event).getFilePath());
            texParseTreePrinter.outputToFile();
        } else if (_event instanceof ExportLaTeXTruthTableEvent) {
            TexTablePrinter texTablePrinter = new TexTablePrinter(this.outputTree, ((ExportLaTeXTruthTableEvent) _event).getFilePath());
            texTablePrinter.outputToFile();
        } else if (_event instanceof ExportLaTeXTruthTreeEvent) {
            BaseTruthTreeGenerator truthTreeGenerator;
            truthTreeGenerator = (this.outputTree.isPropositionalWff()) ? new PropositionalTruthTreeGenerator(this.outputTree) : new PredicateTruthTreeGenerator(this.outputTree);
            TexTruthTreePrinter texTruthTreePrinter = new TexTruthTreePrinter(truthTreeGenerator.getTruthTree(), ((ExportLaTeXTruthTreeEvent) _event).getFilePath());
            texTruthTreePrinter.outputToFile();
        } else if (_event instanceof AlgorithmSelectionViewInitializedEvent) {
            EventBus.throwEvent(new SetAlgorithmInputEvent(this.logicSetup.getAvailableAlgorithms()));
        } else if (_event instanceof UnsolvedFormulaEvent) {
            this.logicSetup.setWffTree(null);
            EventBus.throwEvent(new SetAlgorithmInputEvent(this.logicSetup.getAvailableAlgorithms()));
        }
    }
}
