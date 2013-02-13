package gwt.client.personality;


import gwt.client.game.display.LogDisplay;
import gwt.client.main.VConstants;
import gwt.client.main.base.OObject;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Stack;


public class StackObject implements Serializable{
	public Stack<OObject> stack = new Stack<OObject>();
	public Stack<StackObject> children = new Stack<StackObject>();
	public StackObject() {
	
	}
	public StackObject(List<OObject> pending) {
		if(pending.isEmpty()){
			throw new IllegalArgumentException();
		}
		stack.addAll(pending);
	}
	public OObject peek(){
		if(children.size() == 0){
			return stack.peek();
		}
		return children.peek().stack.peek();
	}
	public int size() {
		int a = 0;
		for(StackObject c : children){
			a+=c.size();
		}
		a+= stack.size();
		return a;
	}
	public void pop() {
		if(children.size() == 0){
			stack.pop();
			return;
		}
		children.peek().stack.pop();
		if(children.peek().size() == 0){
			children.pop();
		}
		
		
	}
	public void pushList(List<OObject> pending) {
		children.push(new StackObject(pending));
	}
	public void push(OObject action) {
		OObject clone = action.clone();
		if(clone == null){
			throw new IllegalArgumentException(""+action);
		}
		clone.setParent(action.getParent());
		
		stack.push(clone);
	}
	public boolean isEmpty() {

		return size()==0;
	}
	public void popChild() {
		children.pop();
	}
	@Override
	public String toString() {
		return "StackObject [stack=" + stack + "]\n"+ children;
	}
	public void clear() {
		stack.clear();
		children.clear();
		
		
	}
	
}
