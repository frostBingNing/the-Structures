package com.company;


import java.util.ArrayList;

/**
 *   param : create a heap with array
 *   0 1 2 3 4 5 6 7 8 ....
 *   - a b c d e f g h ....
 *
 *   we can see that
 *   parent i / 2
 *   childs left i*2   right i*2 + 1
 *
 */
public class heap<E extends Comparable<E>>{

    private Array<E> data;


    //在这里直接实现数组的heapify 变为堆的形式
    public heap(E[] arr)
    {
        // heap 底层的实现逻辑还是数组 所以这里传递过去就可以
        data = new Array<>(arr);
        int theStartIndex = getParent(arr.length-1);
        // 这里可以省略几步 因为直接从当前的index 往下面的索引递归就可以遍历完全部的结点
        for( theStartIndex= theStartIndex; theStartIndex >= 0; theStartIndex--)
        {
            siftDown(theStartIndex);
        }
    }

    public  heap(int capacity){
        data = new Array<>(capacity); // 创建指定数组的数组
    }

    public  heap(){
        data = new Array<>();         // 暂时创建一个空的数组
    }

    //返回当前堆里面的元素个数
    public int getSize(){
        return data.getSize();
    }

    // 判断当当前堆是否为空
    public boolean isEmpty(){
        return data.isEmpty();
    }

    // 获取当前结点的父结点索引
    public int getParent(int index){

        //需要对index 进行一定的判断
        if(index == 0 )
        {
            throw new IllegalArgumentException("the heap don't have the index");
        }
        return (index - 1) / 2;
    }

    // 获取当前结点的左右孩子结点的索引

    public int getLeft(int index){
        return index * 2 + 1;
    }

    public int getRight(int index){
        return index * 2 + 2;
    }


    // siftUp and siftDown
    public E findTheMax(){
        if(data.getSize() == 0)
        {throw new IllegalArgumentException("don't have the index 0");
        }
        return data.get(0);
    }

    public E getTheMax(){
        E ret = findTheMax(); // 首先获得当前的数值
        data.swap(0,getSize()- 1);
        data.removeLast();
        siftDown(0);
        return ret;
    }

    // siftDown
    private void siftDown(int index){
        while(getLeft(index) < getSize())
        {
            int temp = getLeft(index);
            if(temp+1 < getSize() && data.get(temp +1).compareTo(data.get(temp)) > 0)
            {
                temp = getRight(index);
            }
//            data.swap(temp,index);  method is wrong
            if(data.get(index).compareTo(data.get(temp)) >= 0)
            {
                break;
            }
            data.swap(index,temp);
            index = temp;
        }
    }

    // add a new value
    public void add(E e){
        data.addLast(e);
        siftUp(data.getSize()-1); //维护一下这个列表
    }

    // as the same ,we can easily create the siftUp
    private void siftUp(int index)
    {
        while(index > 0 && data.get(getParent(index)).compareTo(data.get(index)) < 0){
            data.swap(index,getParent(index));
            index = getParent(index);
        }
    }
}
