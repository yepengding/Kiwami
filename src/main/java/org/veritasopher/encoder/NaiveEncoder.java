package org.veritasopher.encoder;

import org.veritasopher.element.AtomicProposition;
import org.veritasopher.element.State;
import org.veritasopher.element.Transition;
import org.veritasopher.structure.KripkeStructure;
import org.veritasopher.util.FileUtil;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Naive Encoder
 *
 * @author Yepeng Ding
 */
public class NaiveEncoder {

    /**
     * Structure to be encoded
     */
    private final KripkeStructure kripkeStructure;

    /**
     * Atomic proposition encoding map.
     * Map: atomic proposition -> atomic proposition index
     */
    private final Map<AtomicProposition, Integer> apMap;

    /**
     * State encoding map.
     * Map: state -> encoded state
     */
    private final Map<State, EncodedState> stateMap;

    /**
     * Encode the Kripke structure in the constructor
     */
    public NaiveEncoder(KripkeStructure kripkeStructure) {
        this.apMap = new HashMap<>();
        this.stateMap = new HashMap<>();
        this.kripkeStructure = kripkeStructure;

        kripkeStructure.checkLegacy();
        encodeAtomicProposition();
        encodeState();
    }

    /**
     * Generate an SMT-LIB program with bound k for bounded model checking
     *
     * @param k bound
     * @return SMT-LIB program for bounded model checking
     */
    public String generateSMTWithBound(int k) {
        String template = FileUtil.readFromFileInResource("templates/SMTTemplate");

        String transitionConstraints = generateTransitionConstraints();

        String initStateAssertion = generateInitStateAssertion();

        String transitionAssertion = generateTransitionAssertions(k);

        return transitionAssertion;
    }

    /**
     * Generate a transition constraints
     *
     * @return transition constraints
     */
    private String generateTransitionConstraints() {
        Set<Transition> transitions = this.kripkeStructure.getTransitions();
        if (transitions.size() > 1) {
            return """
                    (or
                    %s
                    )
                    """.formatted(
                    transitions.stream()
                            .map(t -> "(and %s %s)".formatted(
                                            this.stateMap.get(t.src()).constraint("x"),
                                            this.stateMap.get(t.dst()).constraint("y")
                                    )
                            ).collect(Collectors.joining(System.lineSeparator()))
            );
        } else if (transitions.size() == 1) {
            Transition t = transitions.iterator().next();
            return "(and %s %s)".formatted(
                    this.stateMap.get(t.src()).constraint("x"),
                    this.stateMap.get(t.dst()).constraint("y")
            );
        } else {
            return "";
        }
    }

    /**
     * Generate initial state assertion
     *
     * @return initial state assertion
     */
    private String generateInitStateAssertion() {
        if (this.kripkeStructure.getInitStates().size() == 1) {
            return """
                    (assert (and %s))
                    """.formatted(
                    this.stateMap.get(this.kripkeStructure.getInitStates().iterator().next())
                            .constraint("0"));
        }

        return """
                (assert (or
                %s
                ))
                """.formatted(
                this.kripkeStructure.getInitStates().stream()
                        .map(s -> "(and %s)".formatted(this.stateMap.get(s).constraint("0")))
                        .collect(Collectors.joining(System.lineSeparator())));
    }


    /**
     * Generate transition assertions
     *
     * @param k bound
     * @return transition assertions
     */
    private String generateTransitionAssertions(int k) {
        return IntStream.range(0, k)
                .mapToObj(i -> "(assert (t %d %d))".formatted(i, i + 1))
                .collect(Collectors.joining(System.lineSeparator()));
    }

    /**
     * Encode atomic proposition: atomic proposition -> index
     */
    private void encodeAtomicProposition() {
        int index = 0;
        for (AtomicProposition ap :
                this.kripkeStructure.getAtomicPropositionSet()) {
            this.apMap.put(ap, index++);
        }
    }

    /**
     * Encode state map: state -> encoded state (boolean array)
     */
    private void encodeState() {
        for (State state :
                this.kripkeStructure.getStates()) {
            boolean[] code = new boolean[this.apMap.size()];
            this.kripkeStructure.getAtomicPropositionSetOfState(state).stream()
                    .map(this.apMap::get)
                    .forEach(i -> code[i] = true);
            String unindexedConstraint = IntStream.range(0, code.length)
                    .mapToObj(i -> (code[i] ? "(s %s %d)" : "(not (s %s %d))").formatted("%s", i))
                    .collect(Collectors.joining(" "));
            this.stateMap.put(state, new EncodedState(code, unindexedConstraint));
        }
    }

    /**
     * Get an encoded state by a given state
     *
     * @param state state
     * @return either an encoded state or null
     */
    public Optional<EncodedState> getEncodedState(State state) {
        return Optional.ofNullable(this.stateMap.get(state));
    }

    /**
     * Print state map.
     */
    public void printStateMap() {
        this.stateMap.forEach(((state, encodedState) -> {
            System.out.println(state.toString());
            System.out.println(Arrays.toString(encodedState.code));
            System.out.println();
        }));
    }

    /**
     * Encoded State
     *
     * @param code                state code
     * @param unindexedConstraint unindexed state constraint in SMT form
     */
    public record EncodedState(
            boolean[] code,
            String unindexedConstraint
    ) {

        public String constraint(String index) {
            String[] indices = new String[this.code.length];
            Arrays.fill(indices, index);
            return this.unindexedConstraint().formatted((Object[]) indices);
        }
    }

}
