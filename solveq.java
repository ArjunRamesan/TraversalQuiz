package com.example.arjunramesan.traversalquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class solveq extends AppCompatActivity {

    String h = "", a = "";
    cpuActivity u = new cpuActivity();
    TextView answer;
    RadioButton postordr;
    RadioButton preordr,inordr;
    EditText qw;
    Button gb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solveq);
        answer = (TextView) findViewById(R.id.textView10);
        postordr = (RadioButton) findViewById(R.id.radioButton4);
        preordr = (RadioButton) findViewById(R.id.radioButton3);
        inordr = (RadioButton) findViewById(R.id.radioButton);
        qw = (EditText) findViewById(R.id.editText2);
        a = qw.getText().toString();
        gb = (Button) findViewById(R.id.button7);

        Button op = (Button) findViewById(R.id.button8);
        op.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer.setText("");
                func();
            }
        });

        final Intent back = new Intent(this,MainActivity.class);
        gb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(back);
            }
        });

    }


    public void func() {
        Tree t1 = new Tree();
        t1.insert(qw.getText().toString());
        if (preordr.isChecked()) {
            t1.traverse(1);
        } else if (postordr.isChecked()) {
            t1.traverse(2);
        }
        else if(inordr.isChecked())
        {
            t1.traverse(3);
        }


    }

    class Node {                                        //using BST and Stack to solve question//
        public char data;
        public Node leftChild;
        public Node rightChild;

        public Node(char x) {
            data = x;
        }

        public void displayNode() {
            h = String.valueOf(data);
            answer.append(h);
        }
    }


    class Stack1 {
        private Node[] a;
        private int top, m;

        public Stack1(int max) {
            m = max;
            a = new Node[m];
            top = -1;
        }

        public void push(Node key) {
            a[++top] = key;
        }

        public Node pop() {
            return (a[top--]);
        }

        public boolean isEmpty() {
            return (top == -1);
        }
    }

    class Stack2 {
        private char[] a;
        private int top, m;

        public Stack2(int max) {
            m = max;
            a = new char[m];
            top = -1;
        }

        public void push(char key) {
            a[++top] = key;
        }

        public char pop() {
            return (a[top--]);
        }

        public boolean isEmpty() {
            return (top == -1);
        }
    }

    class Conversion {
        private Stack2 s;
        private String input;
        private String output = "";

        public Conversion(String str) {
            input = str;
            s = new Stack2(str.length());
        }

        public String inToPost() {
            for (int i = 0; i < input.length(); i++) {                       //setting operator precedence//
                char ch = input.charAt(i);
                switch (ch) {
                    case '+':
                    case '-':
                        gotOperator(ch, 1);
                        break;
                    case '*':
                    case '/':
                        gotOperator(ch, 2);
                        break;
                    case '(':
                        s.push(ch);
                        break;
                    case ')':
                        gotParenthesis();
                        break;
                    default:
                        output = output + ch;
                }
            }
            while (!s.isEmpty())
                output = output + s.pop();
            return output;
        }

        private void gotOperator(char opThis, int prec1) {
            while (!s.isEmpty()) {
                char opTop = s.pop();
                if (opTop == '(') {
                    s.push(opTop);
                    break;
                } else {
                    int prec2;
                    if (opTop == '+' || opTop == '-')
                        prec2 = 1;
                    else
                        prec2 = 2;
                    if (prec2 < prec1) {
                        s.push(opTop);
                        break;
                    } else
                        output = output + opTop;
                }
            }
            s.push(opThis);
        }

        private void gotParenthesis() {
            while (!s.isEmpty()) {
                char ch = s.pop();
                if (ch == '(')
                    break;
                else
                    output = output + ch;
            }
        }
    }

    class Tree {
        private Node root;

        public Tree() {
            root = null;
        }

        public void insert(String s) {
            Conversion c = new Conversion(s);
            s = c.inToPost();
            Stack1 stk = new Stack1(s.length());
            s = s + "#";
            int i = 0;
            char symbol = s.charAt(i);
            Node newNode;
            while (symbol != '#') {
                if (symbol >= '0' && symbol <= '9' || symbol >= 'A'
                        && symbol <= 'Z' || symbol >= 'a' && symbol <= 'z') {
                    newNode = new Node(symbol);
                    stk.push(newNode);
                } else if (symbol == '+' || symbol == '-' || symbol == '/'
                        || symbol == '*') {
                    Node ptr1 = stk.pop();
                    Node ptr2 = stk.pop();
                    newNode = new Node(symbol);
                    newNode.leftChild = ptr2;
                    newNode.rightChild = ptr1;
                    stk.push(newNode);
                }
                symbol = s.charAt(++i);
            }
            root = stk.pop();
        }

        public void traverse(int type) {
            switch (type) {
                case 1:
                    preOrder(root);
                    break;
                case 2:
                    postOrder(root);
                    break;

                case 3:
                    inOrder(root);
                    break;

            }
        }

        private void preOrder(Node localRoot) {
            if (localRoot != null) {
                localRoot.displayNode();
                preOrder(localRoot.leftChild);
                preOrder(localRoot.rightChild);
            }
        }


        private void inOrder(Node localRoot)
        {
            if (localRoot != null)
            {
                inOrder(localRoot.leftChild);
                localRoot.displayNode();
                inOrder(localRoot.rightChild);
            }
        }

        private void postOrder(Node localRoot) {
            if (localRoot != null) {
                postOrder(localRoot.leftChild);
                postOrder(localRoot.rightChild);
                localRoot.displayNode();
            }
        }
    }

}