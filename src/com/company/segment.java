package com.company;

public class segment<E> {

    private E[] data;
    private E[] tree; // 线段树本体capacity 是data 的四倍
    private Merger<E> merger;


    // 为什么可以查询一定区间里面的内容 是因为我的这棵线段数已经保存了相应的索引区间数值

    // 构建一颗线段树
    public segment(E[] arr, Merger<E> merger) {

        this.merger = merger;

        data = (E[]) new Object[arr.length];
        for (int i = 0; i < arr.length; i++) {
            data[i] = arr[i];
        }
        tree = (E[]) new Object[arr.length * 4];
        // 下面调用一个函数用来进行线段树的构建
        buildTheSegment(0 , 0 , arr.length - 1);
    }

    // 查询函数的编写
    public E  search(int left , int right)
    {
        if( left < 0 || left >= data.length || right < 0 || right >= data.length)
        {
            throw new IllegalArgumentException("index is out of range");
        }
        return findTheResult(0,0,data.length - 1 ,left,right);
    }

    private E findTheResult(int findBeginIndex , int l ,int r ,int theL , int theR)
    {
        // 查询的函数也是递归的操作
        if(l == theL && r == theR)
        {
            return tree[findBeginIndex];
        }

        int mid = l + (r - l) / 2;
        int theTempLeft = getLeft(findBeginIndex);
        int theTempRight = getRight(findBeginIndex);
        if(theL >= mid +1 )
        {
            // 这里还是需要进行返回数值的  否则中间的递归断点了
           return findTheResult(theTempRight,mid+1 , r, theL,theR);
        }
        else if (theR <= mid)
        {

            //这里在某种情况下面是死循环  theR <= mid
           return  findTheResult(theTempLeft,l,mid,theL,theR);
        }
        // 这种情况就需要进行拆分了
        E resultLeft = findTheResult(theTempLeft,l,mid,theL,mid);
        E resultRight = findTheResult(theTempRight,mid+1,r,mid+1,theR);
//        return (E) Merger.merger(resultLeft , resultRight);
        // if i setting the merger is a new merger

        return merger.merger(resultLeft,resultRight); // had defined
    }

    // 线段树的构建
    public void buildTheSegment(int startIndex , int l , int r)
    {
        // 首先就是递归的终结条件
        if( l == r )
        {
            tree[startIndex] = data[l];  // or data[r] 都是一样的效果
            return;
        }

        int theLeftIndex = getLeft(startIndex);
        int theRightIndex = getRight(startIndex);
        int mid = l + ( r - l ) / 2;

        buildTheSegment(theLeftIndex,l,mid);
        buildTheSegment(theRightIndex,mid +1 ,r);

        // 最后的一个条件就是对于所以结点 需要保存什么数据
        // 取决于用户的需求-- for example

        // 匿名函数 不是随便的使用的  因为这里底层并没有调用函数 所以不能这么用
        tree[startIndex] = merger.merger(tree[theLeftIndex] , tree[theRightIndex]);
    }

    // get the data
    public E getData(int index)
    {
        if(index < 0 || index >= data.length )
        {
            throw new IllegalArgumentException("index is out of range");
        }
        return data[index];
    }

    // get the child of left and right
    public int getLeft(int index)
    {
        return index * 2 + 1;
    }
    public int getRight(int index){
        return index * 2 + 2;
    }

    // get the size
    public int getTheSize(){
        return data.length;
    }



    @Override
    public String toString()
    {
        StringBuilder str = new StringBuilder();
        str.append("[");
        for(int i = 0 ; i < tree.length; i++)
        {
            if(tree[i] != null)
            {
                str.append(tree[i]);
            }
            else{
                str.append("null");
            }
            if( i < tree.length - 1 ){
                str.append(" -> ");
            }
        }
        str.append("]");
        return str.toString();
    }

}
//public class SegmentTree<E> {
//
//    private E[] tree;
//    private E[] data;
//    private Merger<E> merger;
//
//    public SegmentTree(E[] arr, Merger<E> merger){
//
//        this.merger = merger;
//
//        data = (E[])new Object[arr.length];
//        for(int i = 0 ; i < arr.length ; i ++)
//            data[i] = arr[i];
//
//        tree = (E[])new Object[4 * arr.length];
//        buildSegmentTree(0, 0, arr.length - 1);
//    }
//
//    // 在treeIndex的位置创建表示区间[l...r]的线段树
//    private void buildSegmentTree(int treeIndex, int l, int r){
//
//        if(l == r){
//            tree[treeIndex] = data[l];
//            return;
//        }
//
//        int leftTreeIndex = leftChild(treeIndex);
//        int rightTreeIndex = rightChild(treeIndex);
//
//        // int mid = (l + r) / 2;
//        int mid = l + (r - l) / 2;
//        buildSegmentTree(leftTreeIndex, l, mid);
//        buildSegmentTree(rightTreeIndex, mid + 1, r);
//
//        tree[treeIndex] = merger.merge(tree[leftTreeIndex], tree[rightTreeIndex]);
//    }
//
//    public int getSize(){
//        return data.length;
//    }
//
//    public E get(int index){
//        if(index < 0 || index >= data.length)
//            throw new IllegalArgumentException("Index is illegal.");
//        return data[index];
//    }
//
//    // 返回完全二叉树的数组表示中，一个索引所表示的元素的左孩子节点的索引
//    private int leftChild(int index){
//        return 2*index + 1;
//    }
//
//    // 返回完全二叉树的数组表示中，一个索引所表示的元素的右孩子节点的索引
//    private int rightChild(int index){
//        return 2*index + 2;
//    }
//
//    @Override
//    public String toString(){
//        StringBuilder res = new StringBuilder();
//        res.append('[');
//        for(int i = 0 ; i < tree.length ; i ++){
//            if(tree[i] != null)
//                res.append(tree[i]);
//            else
//                res.append("null");
//
//            if(i != tree.length - 1)
//                res.append(", ");
//        }
//        res.append(']');
//        return res.toString();
//    }
//}
