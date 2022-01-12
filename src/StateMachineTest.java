import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;

public class StateMachineTest {
    ArrayList<State> states;
    ArrayList<Transition> transitionFunction;
    ArrayList<String> alphabet;
    State initialState;
    ArrayList<State> acceptingStates;
    StateMachine stateMachine;

    @BeforeEach
    void setUp(){
        states = generateStates();
        transitionFunction = generateTransitions();
        alphabet = generateAlphabet();
        initialState = states.get(0);
        acceptingStates = new ArrayList<>();
        acceptingStates.add(states.get(4));
        stateMachine =  new StateMachine(states, alphabet, transitionFunction,
                initialState,acceptingStates);
    }

    @Test
    void removeSpacesTest() {
        String s1  = "da a 3 3 3   addad     adad \n adadad\t\t2\tddd";
        String s2 =removeSpaces(s1);
        assertEquals("daa333addadadadadadad2ddd", s2);
    }

    @Test
    void emptyTest(){
        String in = "";
        in = removeSpaces(in);
        assertFalse(stateMachine.checker(in));
    }

    @Test
    void testInteger(){
        String in = "42";
        in = removeSpaces(in);
        assertFalse(stateMachine.checker(in));
    }

    @Test
    void testOperation(){
        String in = "+";
        in = removeSpaces(in);
        assertFalse(stateMachine.checker(in));
    }

    @Test
    void testEquals(){
        String in = "=";
        in = removeSpaces(in);
        assertFalse(stateMachine.checker(in));
    }

    @Test
    void testMinusSign(){
        String in = "-";
        in = removeSpaces(in);
        assertFalse(stateMachine.checker(in));
    }

    @Test
    void testOperationAtStart(){
        String in = "*24";
        in = removeSpaces(in);
        assertFalse(stateMachine.checker(in));
    }

    @Test
    void testIntegerEquals(){
        String in = "53=";
        in = removeSpaces(in);
        assertTrue(stateMachine.checker(in));
    }

    @Test
    void testAddition(){
        String in = "5252 + 2542 = ";
        in = removeSpaces(in);
        assertTrue(stateMachine.checker(in));
    }

    @Test
    void testValue(){
        String in = "552=";
        in = removeSpaces(in);
        assertTrue(stateMachine.checker(in));

    }

    @Test
    void testNegativeValue(){
        String in = "-552=";
        in = removeSpaces(in);
        assertTrue(stateMachine.checker(in));
    }

    @Test
    void testDivision(){
        String in = "252/52=";
        in = removeSpaces(in);
        assertTrue(stateMachine.checker(in));
    }

    @Test
    void testNegativeDivision(){
        String in = "252/-52=";
        in = removeSpaces(in);
        assertTrue(stateMachine.checker(in));
    }

    @Test
    void testReadStateMachine() {
        Scanner sc = new Scanner(FSMString());
        StateMachine actual = StateMachine.readStateMachine(sc);
        assertEquals(FSMString(), actual.toString()); //dont know how else to check
    }


    @Test
    void testReadStates(){
        String toRead = "A\nB\nC\nD\nE\nAlphabet\nsome alpha";
        Scanner sc = new Scanner(toRead);
        ArrayList<State> states = StateMachine.readStates(sc);
        ArrayList<State> expected = generateStates();
        assertEquals(expected,states);
    }

    @Test
    void testReadAlphabet(){
        String toRead = "0\n" +
                "1\n" +
                "2\n" +
                "3\n" +
                "4\n" +
                "5\n" +
                "6\n" +
                "7\n" +
                "8\n" +
                "9\n" +
                "+\n" +
                "-\n" +
                "*\n" +
                "/\n" +
                "=\nTransition Function\n some trans";
        Scanner sc = new Scanner(toRead);
        ArrayList<String> expected = generateAlphabet();
        ArrayList<String> actual = StateMachine.readAlphabet(sc);
        assertEquals(expected,actual);
    }

    @Test
    void testReadTF(){
        String toRead = "B___1___B\n" +
                "A___0___B\n" +
                "A___7___B\n" +
                "A___5___B\n" +
                "D___8___B\n" +
                "B___*___C\n" +
                "B___3___B\n" +
                "B___7___B\n" +
                "C___7___B\n" +
                "B___/___C\n" +
                "C___2___B\n" +
                "D___5___B\n" +
                "C___4___B\n" +
                "C___0___B\n" +
                "C___-___D\n" +
                "A___2___B\n" +
                "B___8___B\n" +
                "D___9___B\n" +
                "C___1___B\n" +
                "B___4___B\n" +
                "D___6___B\n" +
                "D___2___B\n" +
                "A___8___B\n" +
                "D___3___B\n" +
                "D___4___B\n" +
                "A___6___B\n" +
                "C___6___B\n" +
                "B___0___B\n" +
                "D___0___B\n" +
                "B___5___B\n" +
                "C___9___B\n" +
                "A___9___B\n" +
                "B___9___B\n" +
                "D___7___B\n" +
                "C___3___B\n" +
                "B___+___C\n" +
                "C___5___B\n" +
                "D___1___B\n" +
                "B___-___C\n" +
                "A___4___B\n" +
                "A___1___B\n" +
                "A___-___D\n" +
                "A___3___B\n" +
                "B___6___B\n" +
                "C___8___B\n" +
                "B___=___E\n" +
                "B___2___B\n"+
                "Initial State";
        Scanner sc = new Scanner(toRead);
        ArrayList<Transition> expected = generateTransitions();
        ArrayList<Transition> actual = StateMachine.readTransitionFunction(sc,generateStates());
        assertEquals(expected.size(),actual.size()); //dont know how to compare each element in a set.
    }

    @Test
    void testReadInitialState(){
        String toRead = "A\nAccepting States";
        Scanner sc = new Scanner(toRead);
        State expected = generateStates().get(0);
        State actual = StateMachine.readInitialState(sc,generateStates());
        assertEquals(expected, actual);
    }

    @Test
    void testReadAccStates(){
        String toRead= "E";
        Scanner sc = new Scanner(toRead);
        ArrayList<State> expected= new ArrayList<>();
        expected.add(generateStates().get(4));
        ArrayList<State> actual = StateMachine.readAcceptingStates(sc,generateStates());
        assertEquals(expected.size(), actual.size());
    }

    /*
    -----------------------------------------------------
    These are all the methods to set up the testing suite.
    -----------------------------------------------------
    */

    private String removeSpaces(String s1) {
        return s1.replaceAll("\\s","");
    }

    public ArrayList<State> generateStates(){
        ArrayList<State> states = new ArrayList<>();
        states.add(new State("A"));
        states.add(new State("B"));
        states.add(new State("C"));
        states.add(new State("D"));
        states.add(new State("E"));
        return states;
    }

    public ArrayList<Transition> generateTransitions(){


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

    public ArrayList<Transition> numberTransitions(State current, State next){
        ArrayList<Transition> res = new ArrayList<>();
        for (int i =0; i<10; i++)
            res.add(new Transition(current, next, String.valueOf(i)));
        return res;
    }

    public ArrayList<String> generateAlphabet() {

        ArrayList<String> Strings = new ArrayList<>();

        //add all integers
        for (int i=0; i<10; i++){
            Strings.add(String.valueOf(i));
        }

        //add all arithmetic operators and =
        Strings.add("+");
        Strings.add("-");
        Strings.add("*");
        Strings.add("/");
        Strings.add("=");
        return Strings;
    }

    // Generate a string to test the Read method.
    public String FSMString() {

        return "States\n" +
                "A\n" +
                "B\n" +
                "C\n" +
                "D\n" +
                "E\n" +
                "Alphabet\n" +
                "*\n" +
                "+\n" +
                "-\n" +
                "/\n" +
                "0\n" +
                "1\n" +
                "2\n" +
                "3\n" +
                "4\n" +
                "5\n" +
                "6\n" +
                "7\n" +
                "8\n" +
                "9\n" +
                "=\n" +
                "Transition Function\n" +
                "A___0___B\n" +
                "A___1___B\n" +
                "A___2___B\n" +
                "A___3___B\n" +
                "A___4___B\n" +
                "A___5___B\n" +
                "A___6___B\n" +
                "A___7___B\n" +
                "A___8___B\n" +
                "A___9___B\n" +
                "A___-___D\n" +
                "B___0___B\n" +
                "B___1___B\n" +
                "B___2___B\n" +
                "B___3___B\n" +
                "B___4___B\n" +
                "B___5___B\n" +
                "B___6___B\n" +
                "B___7___B\n" +
                "B___8___B\n" +
                "B___9___B\n" +
                "B___+___C\n" +
                "B___-___C\n" +
                "B___*___C\n" +
                "B___/___C\n" +
                "B___=___E\n" +
                "C___0___B\n" +
                "C___1___B\n" +
                "C___2___B\n" +
                "C___3___B\n" +
                "C___4___B\n" +
                "C___5___B\n" +
                "C___6___B\n" +
                "C___7___B\n" +
                "C___8___B\n" +
                "C___9___B\n" +
                "C___-___D\n" +
                "D___0___B\n" +
                "D___1___B\n" +
                "D___2___B\n" +
                "D___3___B\n" +
                "D___4___B\n" +
                "D___5___B\n" +
                "D___6___B\n" +
                "D___7___B\n" +
                "D___8___B\n" +
                "D___9___B\n" +
                "Initial State\n" +
                "A\n" +
                "Accepting States\n" +
                "E\n" +
                "B\n" +
                "A\n";
    }

}