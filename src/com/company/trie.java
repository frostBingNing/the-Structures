package com.company;

import java.util.TreeMap;

public class trie {

    private class Node{

        public boolean isWord; // 截止到现在为止是否是单词
        public TreeMap<Character,Node> next;


        // Node 函数的构造
        public Node(){
            this(false);
        }

        public Node(boolean bool)
        {
            this.isWord = bool;
            this.next = new TreeMap<>();  // 暂时指向下一结点的是空
        }
    }

    // trie params
    private Node root;
    private int size ;


    public trie(){
        root = new Node();
        // important if define Node root = new Node()  is error  引用的外部变量  所以没有定义  抛出控制错误
        size = 0;
    }


    public int getSize(){
        return size;
    }

    public void add(String str){


        Node cur = root;
//        Character buff = null;
        for(int i = 0 ; i < str.length() ; i++)
        {
            char buff = str.charAt(i); // 依次提取里面的字符
            if(cur.next.get(buff) == null)
            {
                cur.next.put(buff,new Node());
//                cur = cur.next.get(buff);

//                cur = cur.next.get(buff);
            }
            cur = cur.next.get(buff);// 用户输入的所有字符均已进入

        }


        // 下面这个如果放错位置 就变成了统计里面的字符串个数
        if(!cur.isWord)
        {
            cur.isWord = true;
            size ++;
        }
    }
    // 向trie 里面添加元素
    public boolean search (String buff)
    {
       Node str = root ;

       for(int i = 0 ; i< buff.length() ; i++)
       {
           char c = buff.charAt(i);
           if(str.next.get(c) == null)
           {
               return false;
           }
           str = str.next.get(c);  // 指向下一个结点
       }

       // 遍历一遍之后  直接输出当前结点的isWord 是否为真
        return str.isWord;
    }

    // 判断trie 里面是否含有以 后面 prefix 开头的单词
    public boolean isPrefix(String buff)
    {
            Node str = root;
            for(int i =0 ; i <buff.length() ; i++)
            {
                char c = buff.charAt(i);
                if(str.next.get(c) == null){
                    return false;
                }
                str = str.next.get(c);
            }
            return true;
    }


    // 添加一个函数 用来查询带有. 的单词
    public boolean findWith(String buff)
    {




        return fitThe(root,buff,0);
        // here is has a str.next.KeySet()


    }


    public boolean deleteThe(String buff)
    {
        // 还是模仿添加元素的样子
        Node cur = root;
        for(int i = 0 ; i < buff.length() ; i++)
        {
            char c = buff.charAt(i);
            if(cur.next.get(c) == null){
                return false;
            }
            else{
                cur = cur.next.get(c);
            }
        }

        cur.isWord = false;
        return true;
    }





    private boolean fitThe(Node root, String buff , int index)
    {

        if( index  == buff.length())
            return root.isWord;



        char c = buff.charAt(index);

            // 添加终结条件


        if( c != '.')
        {
            if(root.next.get(c) == null)
                return false;
            else{
                root = root.next.get(c);
                return fitThe(root,buff,index+ 1)  ;
            }
        }
        else{

            // == .
            for(char nextChar : root.next.keySet())
            {
                // 只要一直是. 那么就继续匹配下去
                if(fitThe(root.next.get(nextChar),buff,index + 1))
                {
                    return true;
                }
            }
            return false;
        }
    }

}
