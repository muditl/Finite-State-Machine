import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;


public class StateMachine {
    public ArrayList<State> states;
    public Set<String> alphabet;
    public Set<Transition> transitionFunction;
    public State initialState;
    public Set<State> acceptingStates;
    public State deadState;

    /**
     * Constructor for state machine object.
     * @param states ArrayList of states in the given machine.
     * @param alphabet Set of String that form the alphabet of machine.
     * @param transitionFunction Set of transitions that form the transition function.
     * @param initialState Initial state of the machine.
     * @param acceptingStates Set of accepting states.
     */
    public StateMachine(ArrayList<State> states, Set<String> alphabet,
                        Set<Transition> transitionFunction, State initialState,
                        Set<State> acceptingStates) {
        this.states = states;
        this.alphabet = alphabet;
        this.transitionFunction = transitionFunction;
        this.initialState = initialState;
        this.acceptingStates = acceptingStates;
        this.deadState = new State("Dead State");
    }

    /**
     * Getter for states.
     * @return ArrayList of all states.
     */
    public ArrayList<State> getStates() {
        return states;
    }

    /**
     * Getter for alphabets.
     * @return Set of all alphabets.
     */
    public Set<String> getAlphabet() {
        return alphabet;
    }

    /**
     * Getter for transition function
     * @return Set of all transitions in the transition function.
     */
    public Set<Transition> getTransitionFunction() {
        return transitionFunction;
    }

    /**
     * Getter for initial state.
     * @return The Initial state.
     */
    public State getInitialState() {
        return initialState;
    }

    /**
     * Getter for accepting states.
     * @return Set of all accepting states.
     */
    public Set<State> getAcceptingStates() {
        return acceptingStates;
    }

    /**
     * Getter for dead state.
     * @return Dead state.
     */
    public State getDeadState() {
        return deadState;
    }

    /**
     * Method to check if a given string is part of the language of the machine.
     * @param input The input string.
     * @return Boolean indicating whether or not the input is in the language.
     */
    public boolean checker(String input){
        String[] in = input.split("");
        State current = getInitialState();
        for (String c:in){

            //if the machine already reached dead state
            if(current==deadState)
                return false;

            current = findNext(current,c);
        }
        return acceptingStates.contains(current);
    }

    /**
     * Function to run the finite state machine.
     * @param current The current state.
     * @param in The next input.
     * @return The next state.
     */
    private State findNext(State current, String in){
        for (Transition t: transitionFunction){
            if (t.getCurrent().equals(current) && t.getInput().equals(in))
                return t.next;
        }
        return deadState;
    }

    /**
     * Reads a state machine in a pre specified format.
     * @param sc Scanner.
     * @return State Machine object.
     */
    public static StateMachine readStateMachine(Scanner sc){
        sc.nextLine();
        ArrayList<State> states = readStates(sc);
        Set<String> alphabet = readAlphabet(sc);
        Set<Transition> transitionFunction = readTransitionFunction(sc,states);
        State initialState = readInitialState(sc,states);
        Set<State> acceptingStates = readAcceptingStates(sc, states);
        return new StateMachine(states,alphabet,transitionFunction,initialState,acceptingStates);
    }

    /**
     * Reads states.
     * @param sc Scanner.
     * @return ArrayList of states.
     */
    public static ArrayList<State> readStates(Scanner sc){
        sc.useDelimiter("Alphabet\n");
        String statesString = sc.next();
        String[] statesArray = statesString.split("\n");
        ArrayList<State> states = new ArrayList<>();
        for (String name: statesArray){
            State state = new State(name);
            states.add(state);
        }
        sc.nextLine();
        return states;
    }

    /**
     * Reads alphabet.
     * @param sc Scanner.
     * @return Set of Strings that form the alphabet.
     */
    public static Set<String> readAlphabet(Scanner sc){
        sc.useDelimiter("Transition Function\n");
        String alphabetString = sc.next();
        String[] alphabetArray = alphabetString.split("\n");
        Set<String> alphabet = new HashSet<>();
        for (String s: alphabetArray){
            alphabet.add(s.split("")[0]);
        }
        sc.nextLine();
        return alphabet;
    }

    /**
     * Reads the transition function.
     * @param sc Scanner
     * @param states States of the machine.
     * @return A set containing the transition function.
     */
    public static Set<Transition> readTransitionFunction(Scanner sc, ArrayList<State> states){
        sc.useDelimiter("Initial State");
        String transitionsString = sc.next();
        String[] transitionsArray = transitionsString.split("\n");
        Set<Transition> transitionFunction = new HashSet<>();
        for (String transitionString: transitionsArray){
            transitionFunction.add(readTransition(transitionString,states));
        }
        sc.nextLine();
        return  transitionFunction;
    }

    /**
     * Reads a single transition.
     * @param transitionString String of the transition function.
     * @param states Set of states in the machine.
     * @return The Transition object.
     */
    public static Transition readTransition(String transitionString, ArrayList<State> states){
        String[] split = transitionString.split("___");
        String currentStateString = split[0];
        String inputString = split[1];
        String nextStateString = split[2];
        State currentState = null;
        State nextState = null;
        for (State state: states) {
            if (state.getName().equals(currentStateString))
                currentState = state;
            if (state.getName().equals(nextStateString))
                nextState = state;
        }
        if (inputString.length()>1)
            throw new IllegalArgumentException("Input is not a single String");
        if(currentState==null && nextState==null)
            throw new NullPointerException("State(s) not found");

        String input = inputString.split("")[0];
        return new Transition(currentState, nextState, input);
    }

    /**
     * Reads the initial state.
     * @param sc Scanner
     * @param states States in the machine.
     * @return The initial State.
     */
    public static State readInitialState(Scanner sc, ArrayList<State> states){
        sc.useDelimiter("\nAccepting States");
        String stateString = sc.next();
        State initState = null;
        for (State state: states){
            if(state.getName().equals(stateString))
                initState = state;
        }
        if (initState==null)
            throw new NullPointerException("State not found");
        sc.nextLine();
        return initState;

    }

    /**
     * Reads the accepting states in the machine.
     * @param sc Scanner.
     * @param states States in the machine.
     * @return Set of all accepting states.
     */
    public static Set<State> readAcceptingStates(Scanner sc, ArrayList<State> states){
        String statesString = sc.next();
        String[] statesArray = statesString.split("\n");
        Set<State> accStates = new HashSet<>();
        for (State state: states){
            for (String sName: statesArray){
                if (sName.equals(state.getName()))
                    accStates.add(state);
            }
        }
        return accStates;
    }

    /**
     * Converts State Machine Object into human readable string.
     * @return The String.
     */
    @Override
    public String toString(){
        StringBuilder res = new StringBuilder("States\n");
        for (State s: states)
            res.append(s.toString()).append("\n");
        res.append("Alphabet\n");
        for(String s: alphabet)
            res.append(s).append("\n");
        res.append("Transition Function\n");
        for (Transition t: transitionFunction)
            res.append(t.toString()).append("\n");
        res.append("Initial State\n");
        res.append(initialState.toString()).append("\n");
        res.append("Accepting States\n");
        for (State s: acceptingStates)
            res.append(s).append("\n");
        return res.toString();
    }

}

class State {
    public String name;

    /**
     * Constructor for State object.
     * @param name Name of the state.
     */
    public State(String name){
        this.name = name;
    }

    /**
     * Getter for name.
     * @return name.
     */
    public String getName() {
        return name;
    }

    /**
     * Equals method for state.
     * @param o Object to compare to.
     * @return Boolean whether equals or not.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return Objects.equals(name, state.name);
    }

    /**
     * Converts State object into a human readable string.
     * @return The state string.
     */
    @Override
    public String toString() {
        return getName();
    }
}

class Transition {
    public State current;
    public State next;
    public String input;

    /**
     * Constructor for Transition object.
     * @param current Current state.
     * @param next Next State.
     * @param input String input.
     */
    public Transition(State current, State next, String input) {
        this.current = current;
        this.next = next;
        this.input = input;
    }

    /**
     * Getter for current state.
     * @return Current state.
     */
    public State getCurrent() {
        return current;
    }

    /**
     * Getter for next state.
     * @return The next state.
     */
    public State getNext() {
        return next;
    }

    /**
     * Getter for input.
     * @return A Char input.
     */
    public String getInput() {
        return input;
    }

    /**
     * Method to check if 2 transitions are the same.
     * @param o Transition to check with.
     * @return Boolean whether they are the same.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transition that = (Transition) o;
        return this.current.equals(that.current) &&
                this.next.equals(that.next) && this.input.equals(that.input);
    }

    /**
     * Converts a transition object into human readable string.
     * @return The transition string.
     */
    @Override
    public String toString() {
        return  current +
                "___" + input +
                "___" + next;
    }
}
