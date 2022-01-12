import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class VerifyCalculatorInput {

    static StateMachine verifier;

    public static void main(String[] args){
        verifier = generateMachine();
        System.out.println("Project by Mudit Lodha.");
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
        ArrayList<Transition> transitionFunction = generateTransitions(states);
        ArrayList<String> alphabet = generateAlphabet();
        State initialState = states.get(0);
        ArrayList<State> acceptingStates = new ArrayList<>();
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

    public static ArrayList<Transition> generateTransitions(ArrayList<State> states){

        //from A
        ArrayList<Transition> transitions = new ArrayList<>(numberTransitions(states.get(0), states.get(1)));
        transitions.add(new Transition(states.get(0), states.get(3), "-"));

        //from B
        transitions.addAll(numberTransitions(states.get(1),states.get(1)));
        transitions.add(new Transition(states.get(1),states.get(2),"+"));
        transitions.add(new Transition(states.get(1),states.get(2),"-"));
        transitions.add(new Transition(states.get(1),states.get(2),"*"));
        transitions.add(new Transition(states.get(1),states.get(2),"/"));
        transitions.add(new Transition(states.get(1),states.get(4),"="));

        //from C
        transitions.addAll(numberTransitions(states.get(2),states.get(1)));
        transitions.add(new Transition(states.get(2), states.get(3), "-"));

        //from D
        transitions.addAll(numberTransitions(states.get(3),states.get(1)));

        return transitions;
    }


    public static ArrayList<Transition> numberTransitions(State current, State next){
        ArrayList<Transition> res = new ArrayList<>();
        for (int i =0; i<10; i++)
            res.add(new Transition(current, next, String.valueOf(i)));
        return res;
    }

    public static ArrayList<String> generateAlphabet() {

        ArrayList<String> Strings = new ArrayList<>();

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

    public static void writeFileWithFSM(StateMachine sm) throws IOException {
        FileWriter fileWriter = new FileWriter("StateMachineText.txt");
        fileWriter.write(sm.toString());
        fileWriter.close();
    }

    private String removeSpaces(String s1) {
        return s1.replaceAll("\\s","");
    }
}
