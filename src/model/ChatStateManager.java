package model;

import java.util.HashMap;

public class ChatStateManager {
	private HashMap<String, Boolean> states;
	private final static String[] monitoredStates =
		{"foodSpam"};
	
	public ChatStateManager() {
		this.states = new HashMap<String, Boolean>();
		for (String current : monitoredStates) {
			this.states.put(current, false);
		}
	}
	
	public boolean isActive(String state) {
		return this.states.get(state);
	}
	
	public void activate(String state) {
		if (state == null) {
			throw new IllegalArgumentException("state cannot be null");
		}
		if (!this.states.containsKey(state)) {
			throw new NullPointerException(state + " is not a recorded state.");
		}
		this.states.put(state, true);
	}
	
	public void deactivate(String state) {
		if (state == null) {
			throw new IllegalArgumentException("state cannot be null");
		}
		if (!this.states.containsKey(state)) {
			throw new NullPointerException(state + " is not a recorded state");
		}
		this.states.put(state, false);
	}
	
	public String toString() {
		String returnMessage = "ChatStateManager: States being monitored [ ";
		for (String current : ChatStateManager.monitoredStates) {
			returnMessage += current + " ";
		}
		returnMessage += "]";
		return returnMessage;
	}

}
