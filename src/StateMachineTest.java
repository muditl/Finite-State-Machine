import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


public class StateMachineTest {
    ArrayList<State> states;
    Set<Transition> transitionFunction;
    Set<String> alphabet;
    State initialState;
    Set<State> acceptingStates;
    StateMachine stateMachine;

    @BeforeEach
    void setUp(){
        states = generateStates();
        transitionFunction = generateTransitions();
        alphabet = generateAlphabet();
        initialState = states.get(0);
        acceptingStates = new HashSet<>();
        acceptingStates.add(states.get(4));
        stateMachine =  new StateMachine(states, alphabet, transitionFunction,
                initialState,acceptingStates);
    }

    @Test
    void removeSpacesTest() {
        String s1  = new String("da a 3 3 3   addad     adad \n adadad\t\t2\tddd");
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
        Scanner sc = new Scanner(s);
        StateMachine sm = StateMachine.readStateMachine(sc);
        //System.out.println(sm.toString()); //dont know how else to check
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
        String toRead = "1\n" +
                "2\n" +
                "3\n" +
                "4\n" +
                "5\n" +
                "6\n" +
                "7\n" +
                "8\n" +
                "9\n" +
                "0\n" +
                "+\n" +
                "-\n" +
                "*\n" +
                "/\n" +
                "=\nTransition Function\n some trans";
        Scanner sc = new Scanner(toRead);
        Set<String> expected = generateAlphabet();
        Set<String> actual = StateMachine.readAlphabet(sc);
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
        Set<Transition> expected = generateTransitions();
        Set<Transition> actual = StateMachine.readTransitionFunction(sc,generateStates());
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
        Set<State> expected= new HashSet<>();
        expected.add(generateStates().get(4));
        Set<State> actual = StateMachine.readAcceptingStates(sc,generateStates());
        assertEquals(expected.size(), actual.size());
    }

    /*
    These are all the methods to set up the testing suite.
    */

    /**
     * Remove spaces from a string.
     * @param s1 string.
     * @return string without spaces.
     */
    private String removeSpaces(String s1) {
        return s1.replaceAll("\\s","");
    }

    /**
     * Generates 5 states.
     * @return An ArrayList of 5 states.
     */
    public ArrayList<State> generateStates(){
        ArrayList<State> states = new ArrayList<>();
        states.add(new State("A"));
        states.add(new State("B"));
        states.add(new State("C"));
        states.add(new State("D"));
        states.add(new State("E"));
        return states;
    }

    /**
     * Generate a transition function.
     * @return Set of transitions for a machine that checks simple arithmetic input.
     */
    public Set<Transition> generateTransitions(){
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

    /**
     * Generates the alphabet.
     * @return A set of strings that is the alphabet for a simple arithmetic calculator.
     */
    public Set<String> generateAlphabet() {

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

    //String to test reading
    String s = "States\n" +
            "A\n" +
            "B\n" +
            "C\n" +
            "D\n" +
            "E\n" +
            "Alphabet\n" +
            "1\n" +
            "2\n" +
            "3\n" +
            "4\n" +
            "5\n" +
            "6\n" +
            "7\n" +
            "8\n" +
            "9\n" +
            "0\n" +
            "+\n" +
            "-\n" +
            "*\n" +
            "/\n" +
            "=\n" +
            "Transition Function\n" +
            "B___1___B\n" +
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
            "B___2___B\n" +
            "Initial State\n" +
            "A\n" +
            "Accepting States\n" +
            "E\n";

}