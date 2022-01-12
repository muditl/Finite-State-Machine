# Finite-State-Machine
A FiniteStateMachine class, created in Java, complete with documentation, testing, and an example running class.

For the demonstration, run the VerifyCalculatorInput class. 

To run tests, run the TestStateMachine class. 

Both aforementioned classes use a finite state machine defined below: 

Q = {A, B, C, D, E}
Σ = {0,1,2,3,4,5,6,7,8,9, +, -, *, /, =}
δ = 

| **Current State** | **0,1,2,3,4,5,6,7,8,9** | **+,\*,/** | **-**      | **=**      |
| ----------------- | ----------------------- | ---------- | ---------- | ---------- |
| **A**             | B                       | Dead State | D          | Dead State |
| **B**             | B                       | C          | C          | E          |
| **C**             | B                       | Dead State | B          | Dead State |
| **D**             | B                       | Dead State | Dead State | Dead State |
| **E**             | Dead State              | Dead State | Dead State | Dead State |

q0= A
F = E

The diagram:



!["Diagram of the state machine defined."](https://github.com/muditl/Finite-State-Machine/blob/3f33c4c20a4f94fc5cc8c83f08af29086bdfb7d4/media/FSM.jpg)

Some examples:

- “13 + 145 * 12 / 13 =” gives True
- “24 = 23 + 123” gives False
- “13 - - 152” gives False
- “924 + * 152 =” gives False
- “63 - - 52 =” gives True
- “25 = ” gives True
