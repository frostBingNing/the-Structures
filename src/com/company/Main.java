package com.company;

import java.util.ArrayList;
import java.util.Random;
import com.company.heap;
import java.util.TreeMap;
import javax.swing.text.Segment;

/**
 * 对于用户可以调用的接口 采用 public
 * 否则一律采用 private 信息隐藏 --- 保证数据的正确性
 */
public class Main {

    public static void main(String[] args) {

        // 构建一颗树
        AVLtree<Integer> tree = new AVLtree<>();
        for(int i =0 ; i< 10 ; i++)
        {
            tree.add(i+1);
        }

        System.out.println(tree.isBST());
        System.out.println(tree.isAVL());
        tree.deleteRandomNode(7);
        System.out.println(tree.isBST());
        System.out.println(tree.isAVL());

        // 进行一种中序遍历
        tree.showInformation();
    }

}

