package com.example.arjunramesan.traversalquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class cpuActivity extends AppCompatActivity {

    TextView tv1;
    TextView tv2;
    EditText ans;
    TextView tv3;
    TextView scr;
    private Stack theStack;
    private String output = "";
    String h="";
    String u="";
    String answer = "";
    private ArrayList<String> expressions;
    Random r=new Random();String ansstring ="";
    int score = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpu);
        tv1 = (TextView) findViewById(R.id.textView);
        tv2 = (TextView) findViewById(R.id.textView2);
        tv3 = (TextView) findViewById(R.id.textView5);
        ans = (EditText) findViewById(R.id.editText);
        scr = (TextView) findViewById(R.id.textView6);

        gettingText();
        func();

        Button subm = (Button) findViewById(R.id.button);
        subm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ansstring = ans.getText().toString();
                if(ansstring.equalsIgnoreCase(tv2.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(),"correct",Toast.LENGTH_SHORT).show();
                    score++;
                    scr.setText("Score :"+score);
                    ans.setText("");

                    func();

                }
                else
                {
                    Toast.makeText(getApplicationContext(),"wrong",Toast.LENGTH_SHORT).show();
                    score--;
                    scr.setText("Score :"+score);

                }
            }
        });

        final Intent givup = new Intent(this, givp.class);
        final Button giveup = (Button) findViewById(R.id.button2);
        giveup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String h = tv2.getText().toString();
                givup.putExtra("Score", score);
                givup.putExtra("Result",h);
                startActivity(givup);
            }
        });


    }

    public void func() {
        int index;
        tv1.setText("");
        tv2.setText("");
        tv3.setText("");
        index=r.nextInt(expressions.size());
        tv1.setText(expressions.get(index));
        String a = tv1.getText().toString();
        Tree t1 = new Tree();
        t1.insert(a);
        int randompre = r.nextInt(2);
        if(randompre==1)
        {
            tv3.setText("Find the PREORDER of the expression :");
            t1.traverse(1);
        }

        else
        {
            tv3.setText("Find the POSTORDER of the expression :");
            t1.traverse(2);
        }



    }

    class Node
    {
        public char data;
        public Node leftChild;
        public Node rightChild;

        public Node(char x)
        {
            data = x;
        }

        public void displayNode()
        {
            h = String.valueOf(data);
            tv2.append(h);
        }
    }


    class Stack1
    {
        private Node[] a;
        private int    top, m;

        public Stack1(int max)
        {
            m = max;
            a = new Node[m];
            top = -1;
        }

        public void push(Node key)
        {
            a[++top] = key;
        }

        public Node pop()
        {
            return (a[top--]);
        }

        public boolean isEmpty()
        {
            return (top == -1);
        }
    }

    class Stack2
    {
        private char[] a;
        private int    top, m;

        public Stack2(int max)
        {
            m = max;
            a = new char[m];
            top = -1;
        }

        public void push(char key)
        {
            a[++top] = key;
        }

        public char pop()
        {
            return (a[top--]);
        }

        public boolean isEmpty()
        {
            return (top == -1);
        }
    }

    class Conversion
    {
        private Stack2 s;
        private String input;
        private String output = "";

        public Conversion(String str)
        {
            input = str;
            s = new Stack2(str.length());
        }

        public String inToPost()
        {
            for (int i = 0; i < input.length(); i++)
            {
                char ch = input.charAt(i);
                switch (ch)
                {
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

        private void gotOperator(char opThis, int prec1)
        {
            while (!s.isEmpty())
            {
                char opTop = s.pop();
                if (opTop == '(')
                {
                    s.push(opTop);
                    break;
                } else
                {
                    int prec2;
                    if (opTop == '+' || opTop == '-')
                        prec2 = 1;
                    else
                        prec2 = 2;
                    if (prec2 < prec1)
                    {
                        s.push(opTop);
                        break;
                    } else
                        output = output + opTop;
                }
            }
            s.push(opThis);
        }

        private void gotParenthesis()
        {
            while (!s.isEmpty())
            {
                char ch = s.pop();
                if (ch == '(')
                    break;
                else
                    output = output + ch;
            }
        }
    }

    class Tree
    {
        private Node root;

        public Tree()
        {
            root = null;
        }

        public void insert(String s)
        {
            Conversion c = new Conversion(s);
            s = c.inToPost();
            Stack1 stk = new Stack1(s.length());
            s = s + "#";
            int i = 0;
            char symbol = s.charAt(i);
            Node newNode;
            while (symbol != '#')
            {
                if (symbol >= '0' && symbol <= '9' || symbol >= 'A'
                        && symbol <= 'Z' || symbol >= 'a' && symbol <= 'z')
                {
                    newNode = new Node(symbol);
                    stk.push(newNode);
                } else if (symbol == '+' || symbol == '-' || symbol == '/'
                        || symbol == '*')
                {
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

        public void traverse(int type)
        {
            switch (type)
            {
                case 1:
                    preOrder(root);
                    break;
                case 2:
                    postOrder(root);
                    break;

            }
        }

        private void preOrder(Node localRoot)
        {
            if (localRoot != null)
            {
                localRoot.displayNode();
                preOrder(localRoot.leftChild);
                preOrder(localRoot.rightChild);
            }
        }

        private void postOrder(Node localRoot)
        {
            if (localRoot != null)
            {
                postOrder(localRoot.leftChild);
                postOrder(localRoot.rightChild);
                localRoot.displayNode();
            }
        }
    }




    private void gettingText(){

        expressions=new ArrayList<>();
        BufferedReader exp=null;


        try {
            exp = new BufferedReader(new InputStreamReader(getAssets().open("database.txt")));

            String line = exp.readLine();
            while (line != null) {
                expressions.add(line);
                line = exp.readLine();
            }
            exp.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}

