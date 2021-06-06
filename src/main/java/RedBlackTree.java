import interfaces.INode;
import interfaces.IRedBlackTree;

public class RedBlackTree<T extends Comparable<T>, V> implements IRedBlackTree<T, V> {

    private INode<T, V> root;
    private final INode<T, V> nil = new Node<>(INode.BLACK);

    private int size = 0;

    public INode<T, V> getNil() {
        return nil;
    }
    @Override
    public INode<T, V> getRoot() {
        return root;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public void clear() {
        root = null;
    }

    @Override
    public V search(T key) {
        return searchHelper(key, this.root).getValue();
    }

    private INode<T, V> searchHelper(T key, INode<T, V> node) {
        if (node == this.nil)
            return this.nil;
        if (key.compareTo(node.getKey()) == 0)
            return node;
        else if (key.compareTo(node.getKey()) < 0)
            return searchHelper(key, node.getLeftChild());
        else
            return searchHelper(key, node.getRightChild());
    }

    @Override
    public boolean contains(T key) {
        return searchHelper(key, this.root) != this.nil;
    }

    @Override
    public void insert(T key, V value) {
        if (key == null) return;
        size++;
        if (root == null) { /*first node*/
            root = createNode(null, INode.BLACK);
            root.setKey(key);
            root.setValue(value);
            root.setLeftChild(this.nil);
            root.setRightChild(this.nil);
            return;
        }

        INode<T, V> currentNode, nextNode = root;
        int res;

        do {
            currentNode = nextNode;
            res = key.compareTo(currentNode.getKey());

            if (res > 0) nextNode = currentNode.getRightChild();
            else nextNode = currentNode.getLeftChild();

        } while (!nextNode.equals(this.nil));

        INode<T, V> node = createNode(currentNode, INode.RED);
        node.setKey(key);
        node.setValue(value);
        if (res > 0) currentNode.setRightChild(node);
        else currentNode.setLeftChild(node);

        insertionFixup(node);
    }

    @Override
    public boolean delete(T key) {
        INode<T, V> nodeToDelete = searchHelper(key, this.root);
        if (nodeToDelete == this.nil)
            return false;
        else {
            deleteNode(nodeToDelete);
            size--;
            return true;
        }
    }

    public void deleteNode(INode<T, V> node) {
        INode<T, V> interruptedNode = node;
        INode<T, V> transplantedNode;
        boolean originalColor = interruptedNode.getColor();
        if (node.getLeftChild() == this.nil) {
            // One child replace the deleted node with right child
            transplantedNode = node.getRightChild();
            transplant(node, node.getRightChild());
        } else if (node.getRightChild() == this.nil) {
            // One child replace the deleted node with left child
            transplantedNode = node.getLeftChild();
            transplant(node, node.getLeftChild());
        } else {
            // Two children replace the deleted node with its successor
            interruptedNode = getMinimum(node.getRightChild());
            originalColor = interruptedNode.getColor();
            transplantedNode = interruptedNode.getRightChild();
            // In case it was nil
            transplantedNode.setParent(interruptedNode);
            if (interruptedNode.getParent() != node) {
                // Replace the successor with its right child
                transplant(interruptedNode, interruptedNode.getRightChild());
                interruptedNode.setRightChild(node.getRightChild());
                interruptedNode.getRightChild().setParent(interruptedNode);
            }
            // replace the deleted node with the successor
            transplant(node, interruptedNode);
            interruptedNode.setLeftChild(node.getLeftChild());
            interruptedNode.getLeftChild().setParent(interruptedNode);
            interruptedNode.setColor(node.getColor());
        }
        // if the moved node or deleted node was black then we need a fixup
        if (!originalColor)
            deleteFixup(transplantedNode);
    }

    public INode<T, V> getMinimum(INode<T, V> node) {
        return (node.getLeftChild() != this.nil) ? getMinimum(node.getLeftChild()) : node;
    }

    private void insertionFixup(INode<T, V> node) {
        if (node.equals(root)) {
            node.setColor(INode.BLACK);
            return;
        }

        INode<T, V> parent = node.getParent();
        if (isBlack(parent)) return; /*also handles the case where my parent is the root*/
        INode<T, V> grandParent = parent.getParent();
        if (grandParent==null)  return;
        INode<T, V> uncle = getUncle(node);

        /*case uncle is red*/
        if (uncle != this.nil && isRed(uncle)) {
            uncle.setColor(INode.BLACK);
            parent.setColor(INode.BLACK);
            grandParent.setColor(INode.RED);
            insertionFixup(grandParent);
        }
        /*case uncle is black*/
        else if (node.getParent().equals(grandParent.getLeftChild())) {
            /*make sure new node is left child*/
            if (isRightChild(node)) {
                leftRotate(node.getParent());
                parent = node;
            }
            parent.setColor(INode.BLACK);
            grandParent.setColor(INode.RED);
            rightRotate(parent.getParent());
        }
        else {
            if (isLeftChild(node)) {
                rightRotate(node.getParent());
                parent = node;
            }
            parent.setColor(INode.BLACK);
            grandParent.setColor(INode.RED);
            leftRotate(parent.getParent());
        }
    }

    private INode<T, V> createNode(INode<T, V> parent, boolean color) {
        INode<T, V> node = new Node<>(color);
        node.setParent(parent);
        node.setLeftChild(nil);
        node.setRightChild(nil);
        return node;
    }

    protected INode<T, V> getUncle(INode<T, V> node) {
        INode<T, V> parent = node.getParent(), grandParent = parent.getParent();
        INode<T, V> leftChild = grandParent.getLeftChild();

        if (parent.equals(leftChild)) return grandParent.getRightChild();
        return leftChild;
    }

    protected boolean isRightChild(INode<T, V> node) {
        INode<T, V> parent = node.getParent();
        INode<T, V> rightChild = parent.getRightChild();
        return node.equals(rightChild);
    }

    protected boolean isLeftChild(INode<T, V> node) {
        INode<T, V> parent = node.getParent();
        INode<T, V> leftChild = parent.getLeftChild();
        return node.equals(leftChild);
    }

    protected boolean isRed(INode<T, V> node) {
        return node.getColor();
    }

    protected boolean isBlack(INode<T, V> node) {
        return !node.getColor();
    }

    /*in order to avoid confusion, rotate methods take their parent as the parameter*/
    private void leftRotate(INode<T, V> parent) {
        INode<T, V> child = parent.getRightChild();

        INode<T, V> source = parent.getParent();
        INode<T, V> commonChild = child.getLeftChild();

        parent.setRightChild(commonChild);
        child.setLeftChild(parent);

        rotateHandle(parent, child, source, commonChild);

    }

    /*in order to avoid confusion, rotate methods take their parent as the parameter*/
    private void rightRotate(INode<T, V> parent) {
        INode<T, V> child = parent.getLeftChild();

        INode<T, V> source = parent.getParent();
        INode<T, V> commonChild = child.getRightChild();

        parent.setLeftChild(commonChild);
        child.setRightChild(parent);

        rotateHandle(parent, child, source, commonChild);
    }

    private void rotateHandle(INode<T, V> parent, INode<T, V> child, INode<T, V> source, INode<T, V> commonChild) {
        if (!commonChild.equals(this.nil))
            commonChild.setParent(parent);

        child.setParent(source);
        parent.setParent(child);

        if (source != null) {
            if (source.getRightChild().equals(parent)) {
                source.setRightChild(child);
            } else {
                source.setLeftChild(child);
            }
        } else {
            this.root = child;
        }
    }

    private void transplant(INode<T, V> originalNode, INode<T, V> transplantedNode) {
        if (originalNode.getParent() == null)
            this.root = transplantedNode;
        else if (originalNode == originalNode.getParent().getLeftChild())
            originalNode.getParent().setLeftChild(transplantedNode);
        else
            originalNode.getParent().setRightChild(transplantedNode);
        transplantedNode.setParent(originalNode.getParent());
    }

    private void deleteFixup(INode<T, V> transplantedNode) {
        while (transplantedNode != this.root && !transplantedNode.getColor()) {
            if (transplantedNode == transplantedNode.getParent().getLeftChild()) {
                INode<T, V> sibling = transplantedNode.getParent().getRightChild();
                // Case 1 sibling is red
                if (sibling.getColor()) {
                    sibling.setColor(INode.BLACK);
                    transplantedNode.getParent().setColor(INode.RED);
                    leftRotate(transplantedNode.getParent());
                    sibling = transplantedNode.getParent().getRightChild();
                }
                // Case 2 sibling is black with two black children
                if (!sibling.getLeftChild().getColor() && !sibling.getRightChild().getColor()) {
                    sibling.setColor(INode.RED);
                    // elevate the fix up to the parent
                    transplantedNode = transplantedNode.getParent();
                }
                else {
                    // Case 3 sibling with one red child near to the transplanted node
                    if (!sibling.getRightChild().getColor()) {
                        sibling.getLeftChild().setColor(INode.BLACK);
                        sibling.setColor(INode.RED);
                        rightRotate(sibling);
                        sibling = transplantedNode.getParent().getRightChild();
                    }
                    // Case 4 sibling with one red child far from the transplanted node
                    sibling.setColor(transplantedNode.getParent().getColor());
                    transplantedNode.getParent().setColor(INode.BLACK);
                    sibling.getRightChild().setColor(INode.BLACK);
                    leftRotate(transplantedNode.getParent());
                    // To terminate while loop
                    transplantedNode = root;
                }
            } else {
                INode<T, V> sibling = transplantedNode.getParent().getLeftChild();
                // Case 1
                if (sibling.getColor()) {
                    sibling.setColor(INode.BLACK);
                    transplantedNode.getParent().setColor(INode.RED);
                    rightRotate(transplantedNode.getParent());
                    sibling = transplantedNode.getParent().getLeftChild();
                }
                // Case 2
                if (!sibling.getLeftChild().getColor() && !sibling.getRightChild().getColor()) {
                    sibling.setColor(INode.RED);
                    transplantedNode = transplantedNode.getParent();
                }
                else {
                    // Case 3
                    if (!sibling.getLeftChild().getColor()) {
                        sibling.getRightChild().setColor(INode.BLACK);
                        sibling.setColor(INode.RED);
                        leftRotate(sibling);
                        sibling = transplantedNode.getParent().getLeftChild();
                    }
                    // Case 4
                    sibling.setColor(transplantedNode.getParent().getColor());
                    transplantedNode.getParent().setColor(INode.BLACK);
                    sibling.getLeftChild().setColor(INode.BLACK);
                    rightRotate(transplantedNode.getParent());
                    // To terminate while loop
                    transplantedNode = root;
                }
            }
        }
        transplantedNode.setColor(INode.BLACK);
    }

    public String inOrderTraverse() {
        return inOrderTraverseHelper(this.root, "");
    }

    public String inOrderTraverseHelper(INode<T, V> node, String accumulator) {
        if (node == this.nil)
            return accumulator;
        accumulator = inOrderTraverseHelper(node.getLeftChild(), accumulator);
        accumulator += node.getKey() + " " + (node.getColor()?"Red": "Black") + " ";
        accumulator = inOrderTraverseHelper(node.getRightChild(), accumulator);
        return accumulator;
    }

    public int getSize() {
        return size;
    }
}
