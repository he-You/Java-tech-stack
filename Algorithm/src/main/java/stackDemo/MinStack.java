package stackDemo;

import java.util.Stack;

/**
 * 实现一个栈，它有出栈入栈取最小元素 3 个方法，且保证这 3 个方法的时间复杂度都是 O(1)
 *
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2020/10/18 23:33
 */
public class MinStack {
    private Stack<Integer> mainStack = new Stack<>();
    private Stack<Integer> minStack = new Stack<>();

    public void push(int element){
        mainStack.push(element);
        // 如果辅助栈为空，或者新元素小于或等于辅助栈栈顶元素，则将新元素压入辅助栈
        if (minStack.empty() || element<= minStack.peek()) {
            minStack.push(element);
        }
    }

    public Integer pop(){
        // 如果出栈元素和辅助栈栈顶元素值相等，辅助栈出栈
        if (mainStack.peek().equals(minStack.peek())){
            minStack.pop();
        }
        return mainStack.pop();
    }

    public int getMin() throws Exception {
        if (mainStack.empty()){
            throw new Exception("stack is empty");
        }
        return minStack.peek();
    }
}
