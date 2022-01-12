import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class StateMachine {
    public ArrayList<State> states;
    public ArrayList<String> alphabet;
    public ArrayList<Transition> transitionFunction;
    public State initialState;
    public ArrayList<State> acceptingStates;
    public State deadState;

    public StateMachine(ArrayList<State> states, ArrayList<String> alphabet,
                        ArrayList<Transition> transitionFunction, State initialState,
                        ArrayList<State> acceptingStates) {
        this.states = states;
        this.alphabet = alphabet;
        this.transitionFunction = transitionFunction;
        this.initialState = initialState;
        this.acceptingStates = acceptingStates;
        this.deadState = new State("Dead State");
    }

    public ArrayList<State> getStates() {
        return states;
    }

    public ArrayList<String> getAlphabet() {
        return alphabet;
    }

    public ArrayList<Transition> getTransitionFunction() {
        return transitionFunction;
    }

    public State getInitialState() {
        return initialState;
    }

    public ArrayList<State> getAcceptingStates() {
        return acceptingStates;
    }

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
            if(current==deadState)
                return false;
            current = findNext(current,c);
        }
        return acceptingStates.contains(current);
    }

    /**
     * Function to run the finite state machine.
     * @param current The current state.
     * @param input The next input (of length 1).
     * @return The next state.
     */
    private State findNext(State current, String input){
        for (Transition t: transitionFunction){
            if (t.getCurrent().equals(current) && t.getInput().equals(input))
                return t.next;
        }
        return deadState;
    }

    /**
     * Reads a state machine in a pre specified format.
     */
    public static StateMachine readStateMachine(Scanner sc){
        sc.nextLine();
        ArrayList<State> states = readStates(sc);
        ArrayList<String> alphabet = readAlphabet(sc);
        ArrayList<Transition> transitionFunction = readTransitionFunction(sc,states);
        State initialState = readInitialState(sc,states);
        ArrayList<State> acceptingStates = readAcceptingStates(sc, states);
        return new StateMachine(states,alphabet,transitionFunction,initialState,acceptingStates);
    }

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

    public static ArrayList<String> readAlphabet(Scanner sc){
        sc.useDelimiter("Transition Function\n");
        String alphabetString = sc.next();
        String[] alphabetArray = alphabetString.split("\n");
        ArrayList<String> alphabet = new ArrayList<>();
        for (String s: alphabetArray){
            alphabet.add(s.split("")[0]);
        }
        sc.nextLine();
        return alphabet;
    }

    public static ArrayList<Transition> readTransitionFunction(Scanner sc, ArrayList<State> states){
        sc.useDelimiter("Initial State");
        String transitionsString = sc.next();
        String[] transitionsArray = transitionsString.split("\n");
        ArrayList<Transition> transitionFunction = new ArrayList<>();
        for (String transitionString: transitionsArray){
            transitionFunction.add(readTransition(transitionString,states));
        }
        sc.nextLine();
        return  transitionFunction;
    }

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

    public static ArrayList<State> readAcceptingStates(Scanner sc, ArrayList<State> states){
        String statesString = sc.next();
        String[] statesArray = statesString.split("\n");
        ArrayList<State> accStates = new ArrayList<>();

        for (String sName: statesArray){
            for (State state: states){
                if (sName.equals(state.getName()))
                    accStates.add(state);
            }
        }
        return accStates;
    }

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

    public State(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return Objects.equals(name, state.name);
    }

    @Override
    public String toString() {
        return getName();
    }
}

class Transition {
    public State current;
    public State next;
    public String input;

    public Transition(State current, State next, String input) {
        this.current = current;
        this.next = next;
        this.input = input;
    }

    public State getCurrent() {
        return current;
    }

    public State getNext() {
        return next;
    }

    public String getInput() {
        return input;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transition that = (Transition) o;
        return this.current.equals(that.current) &&
                this.next.equals(that.next) && this.input.equals(that.input);
    }

    @Override
    public String toString() {
        return  current +
                "___" + input +
                "___" + next;
    }

}
