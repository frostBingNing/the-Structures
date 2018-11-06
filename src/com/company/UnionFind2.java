package com.company;

public class UnionFind2 implements base {

    //  首先创建实现数组
    private int[] test;
    // 添加一个当前结点所有个数的数组
    private int[] nums;


    // 初始化函数
    UnionFind2(int size){
        test = new int[size];
        nums = new int[size];

        for(int i = 0 ; i<test.length ; i++)
        {
            test[i] = i ;
            nums[i] = 1;
        }

    }


    // 实现接口里面的函数
    @Override
    public int getSize(){
        return test.length;
    }

    private int find(int index){

        if(index < 0 || index > test.length)
        {
            throw new IllegalArgumentException("index is out of range");
        }


        while(index  != test[index]){
            index = test[index];
        }
        return index;
    }

    @Override
    public boolean isConnected(int index1 , int index2){

        if(index1 < 0 || index1 > test.length || index2 < 0 || index2 > test.length)
        {
            throw new IllegalArgumentException("index is out of range");
        }


        return find(index1) ==  find(index2);
    }

    @Override
    public void unionElements(int index1 , int index2){
        if(index1 < 0 || index1 > test.length || index2 < 0 || index2 > test.length)
        {
            throw new IllegalArgumentException("index is out of range");
        }

        // 这种的元素连接采用的是根结点的链接
        int parent1 = find(index1);
        int parent2 = find(index2);

        if(parent1 == parent2)
            return;

        // 前面的元素指向后面的元素
        // 添加nums 之后 主要修改的就是下面的逻辑

        if(nums[parent1] < nums[parent2]) {
            test[parent1] = parent2;
            nums[parent2] += nums[parent1];
        }
        else { //  这里的等于号  可以归并为任意一种情况 没有特殊的区别
            test[parent2] = parent1;
            nums[parent1] += nums[parent2];
        }
    }

    // 按照元素打印里面的详细情况
    public StringBuilder showInformation(){

        StringBuilder str = new StringBuilder();
        for(int param: test)
        {
            str.append(param);
            str.append("-->");
        }
        return str;
    }

}
