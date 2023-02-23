package tqs.lab1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TqsStackIntegerTest {

    private final int n = 3;
    private final TqsStack<Integer> stack = new TqsStack<>();
    private final TqsStack<Integer> boundedStack = new TqsStack<>(3);

    @Test
    @DisplayName("A stack is empty on construction.")
    void test_a() {
        assertTrue(stack.isEmpty());
    }

    @Test
    @DisplayName("A stack has size 0 on construction.")
    void test_b() {
        assertEquals(0, stack.size());
    }

    @Test
    @DisplayName("After n pushes to an empty stack, n > 0, the stack is not empty and its size is n.")
    void test_c() {
        for (int i = 1; i <= n; i++) {
            stack.push(i);
        }
        assertFalse(stack.isEmpty());
        assertEquals(n, stack.size());
    }

    @Test
    @DisplayName("If one pushes x then pops, the value popped is x.")
    void test_d() {
        stack.push(1);
        assertEquals(1, stack.pop());
    }

    @Test
    @DisplayName("If one pushes x then peeks, the value returned is x, but the size stays the same.")
    void test_e() {
        stack.push(1);
        int sz = stack.size();
        assertEquals(1, stack.peek());
        assertEquals(sz, stack.size());
    }

    @Test
    @DisplayName("If the size is n, then after n pops, the stack is empty and has a size 0.")
    void test_f() {
        for (int i = 1; i <= n; i++) {
            stack.push(i);
        }
        assertEquals(n, stack.size());
        for (int i = 1; i <= n; i++) {
            stack.pop();
        }
        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());
    }

    @Test
    @DisplayName("Popping from an empty stack does throw a NoSuchElementException.")
    void test_g() {
        assertThrows(java.util.NoSuchElementException.class, stack::pop);
    }

    @Test
    @DisplayName("Peeking into an empty stack does throw a NoSuchElementException.")
    void test_h() {
        assertThrows(java.util.NoSuchElementException.class, stack::peek);
    }

    @Test
    @DisplayName("For bounded stacks only: pushing onto a full stack does throw an IllegalStateException.")
    void test_i() {
        for (int i = 1; i <= n; i++) {
            boundedStack.push(i);
        }
        assertThrows(java.lang.IllegalStateException.class, () -> boundedStack.push(n + 1));
    }
}