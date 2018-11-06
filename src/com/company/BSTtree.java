package com.company;

public class BSTtree<E extends Comparable<E>> {


    private class Node {
        public E e;
        public Node left, right;

        public Node(E e) {
            this.e = e;
            left = null;
            right = null;
        }
    }

    private Node root;
    private int size;

    public BSTtree() {
        root = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    // 向二分搜索树中添加新的元素e
    public void add(E e) {
        root = add(root, e);
    }

    // 向以node为根的二分搜索树中插入元素e，递归算法
    // 返回插入新节点后二分搜索树的根
    private Node add(Node node, E e) {
        if (node == null) {
            size++;
            return new Node(e);
        }

        if (e.compareTo(node.e) < 0)
            node.left = add(node.left, e);
        else if (e.compareTo(node.e) > 0)
            node.right = add(node.right, e);

        return node;
    }

    public Node findNode(Node node, E e) {
        if (node == null)
            return null;

        if (e.compareTo(node.e) < 0) {
            return findNode(node.left, e);
        } else if (e.compareTo(node.e) > 0) {
            return findNode(node.right, e);
        } else {
            return node;
        }
    }

    /**
     * @param
     * @return the value
     * @// FIXME: 2018/10/21
     */

    public E theMin() {
        if (size == 0) {
            throw new IllegalArgumentException("the bst is empty");
        }
        Node min = getTheMin(root);
        return min.e; // 直接返回最小节点的数值
    }

    public Node getTheMin(Node node) {

        if (node.left == null)
            return node;   // 递归条件的终结
        return getTheMin(node.left); //递归继续进行下去的连接
    }

    /**
     * @param * @information find the max value
     */
    public E theMax() {
        if (size == 0) {
            throw new IllegalArgumentException("the bst is empty");
        }
        Node max = getTheMax(root);
        return max.e;
    }

    public Node getTheMax(Node node) {
        if (node.right == null) {
            return node;
        }
        return getTheMax(node.right);
    }


    /**
     * @param : the correct node and value
     */
    public E removeMin() {
        E ret = theMin(); // 返回最小值
        root = removeMin(root);  // return a min node
        return ret;
    }

    private Node removeMin(Node root) {
        if (root.left == null) {
            // 表示当前结点就是最小值所在的结点
            Node temp = root.right;
            root.right = null; // 设置为空
            size--;
            return temp;
        }
        // 这里逻辑出现了一定的问题 没有维护后期的树
        root.left = removeMin(root.left);
        return root; // 没有到达递归结束的最后条件继续执行相应的操作

    }

    public E removeMax() {
        E ret = theMax();
        root = removeMin(root);
        return ret;
    }

    private Node removeMax(Node root) {

        if (root.right == null) {
            //表示当前结点就是要删除结点
            Node temp = root.left;
            root.left = null;
            size--;
            return temp;
        }
        // 一样没有维护当前的树
        root.right =  removeMax(root.right);
        return root;
    }

    /**
     * @param : delete random node
     *
     */
    public void deleteRandomNode(E e){
        // 删除二分搜索树里面值对应的元素
        //返回删除结点之后的根结点
        root = deleteRandomNode(root , e);
    }

    private Node deleteRandomNode(Node root,E e) {
        // 还是像上面一样进行递归删除操作
        if (root == null)
            return null; // 等价于没有找到数值

        if (e.compareTo(root.e) < 0) {
            root.left = deleteRandomNode(root.left, e);
            return root;
        } else if (e.compareTo(root.e) > 0) {
            root.right = deleteRandomNode(root.right, e);
            return root;
        } else {
            // e == node.e
            // 比较复杂 complex
            if (root.left == null) {
                // 直接替换掉当前接结点的右节点
                Node temp = root.right;
                root.right = null;
                size--;
                return temp;
            } else if (root.right == null) {
                // 和上面的正好相反
                Node temp = root.left;
                root.left = null;
                size--;
                return temp;
            } else {
                // root.left and root.right not equale null
                Node rightMix = getTheMin(root.right);
                rightMix = removeMin(root.right);
                rightMix.left = root.left;
                root.left = root.right = null;
                return rightMix;
            }

        }
    }
}


