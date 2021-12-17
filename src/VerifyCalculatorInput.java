import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class VerifyCalculatorInput {

    static StateMachine verifier;

    public static void main(String[] args){
        verifier = generateMachine();
        System.out.println("Project by Mudit Lodha");
        System.out.println("This is a finite state machine which verifies whether the user input expression is a valid expression for a calculator");
        System.out.println("Enter the input to be tested");
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        input = input.replaceAll("\\s","");
        boolean valid = verifier.checker(input);
        if(valid)
            System.out.println(input+" is a valid expression for a calculator input.");
        else
            System.out.println(input+" is not a valid expression for a calculator input.");

    }

    public static StateMachine generateMachine(){
        ArrayList<State> states = generateStates();
        Set<Transition> transitionFunction = generateTransitions(states);
        Set<String> alphabet = generateAlphabet();
        State initialState = states.get(0);
        Set<State> acceptingStates = new HashSet<>();
        acceptingStates.add(states.get(4));
        return new StateMachine(states, alphabet, transitionFunction,
                initialState,acceptingStates);
    }

    public static ArrayList<State> generateStates(){
        ArrayList<State> states = new ArrayList<>();
        states.add(new State("A"));
        states.add(new State("B"));
        states.add(new State("C"));
        states.add(new State("D"));
        states.add(new State("E"));
        return states;
    }

    public static Set<Transition> generateTransitions(ArrayList<State> states){
        Set<Transition> transitions = new HashSet<>();
        //define all transitions for integers
        for (int i =0; i<10; i++){
            //from A to B
            Transition t1 = new Transition(states.get(0),states.get(1),String.valueOf(i));
            //from B to B
            Transition t2 = new Transition(states.get(1),states.get(1),String.valueOf(i));
            //from C to B
            Transition t3 = new Transition(states.get(2),states.get(1),String.valueOf(i));
            //from D to B
            Transition t4 = new Transition(states.get(3),states.get(1),String.valueOf(i));
            transitions.add(t1);
            transitions.add(t2);
            transitions.add(t3);
            transitions.add(t4);
        }

        //define transitions for arithmetic operators
        //from B to C
        transitions.add(new Transition(states.get(1),states.get(2),"+"));
        transitions.add(new Transition(states.get(1),states.get(2),"-"));
        transitions.add(new Transition(states.get(1),states.get(2),"*"));
        transitions.add(new Transition(states.get(1),states.get(2),"/"));

        //define transitions from A to D and C to D with -
        transitions.add(new Transition(states.get(0), states.get(3), "-"));
        transitions.add(new Transition(states.get(2), states.get(3), "-"));

        //define transition from B to D with =
        transitions.add(new Transition(states.get(1),states.get(4),"="));
        return transitions;
    }

    public static Set<String> generateAlphabet() {

        Set<String> Strings = new HashSet<String>();

        //add all integers
        for (int i=0; i<10; i++){
            Strings.add(String.valueOf(i));
        }

        //add all arithmetic operators and =
        Strings.add("+");
        Strings.add("-");
        Strings.add("/");
        Strings.add("*");
        Strings.add("=");
        return Strings;
    }

    private String removeSpaces(String s1) {
        return s1.replaceAll("\\s","");
    }
}
