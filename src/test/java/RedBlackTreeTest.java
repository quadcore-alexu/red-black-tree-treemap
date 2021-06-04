import static org.junit.jupiter.api.Assertions.*;

class RedBlackTreeTest {

    @org.junit.jupiter.api.Test
    void delete() {
        RedBlackTree<Integer, String> rbTree = new RedBlackTree<>();
        rbTree.insert(30, null);
        rbTree.insert(15, null);
        rbTree.insert(24, null);
        rbTree.insert(13, null);
        rbTree.insert(55, null);
        rbTree.insert(12, null);
        rbTree.insert(23, null);
        rbTree.insert(9, null);
        rbTree.insert(10, null);
        rbTree.insert(11, null);
        rbTree.insert(8, null);
        rbTree.insert(7, null);
        rbTree.insert(3, null);
        rbTree.insert(16, null);
        rbTree.insert(18, null);
        rbTree.insert(22, null);
        rbTree.insert(25, null);
        rbTree.insert(32, null);
        rbTree.insert(31, null);
        rbTree.insert(66, null);
        rbTree.delete(30);
        rbTree.delete(15);
        rbTree.insert(77, null);
        rbTree.delete(66);
        rbTree.delete(7);
        rbTree.delete(8);
        rbTree.delete(16);
        String expectedInOrderTraversal =   "3 Red 9 Black 10 Black 11 Red 12 Black 13 Black " +
                                            "18 Black 22 Black 23 Black 24 Red 25 Black 31 Black " +
                                            "32 Black 55 Red 77 Black ";
        assertEquals(expectedInOrderTraversal, rbTree.inOrderTraverse());
    }
}