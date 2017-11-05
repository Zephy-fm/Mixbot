package model;

import java.util.HashMap;

public class ChatStateManager {
	public final static String[] MONITORED_STATES =
		{"foodSpam"};
	
	private HashMap<String, Boolean> states;
	
	public ChatStateManager() {
		this.states = new HashMap<String, Boolean>();
		for (String current : MONITORED_STATES) {
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
		for (String current : ChatStateManager.MONITORED_STATES) {
			returnMessage += current + ":" + this.states.get(current).toString() + " ";
		}
		returnMessage += "]";
		return returnMessage;
	}

}
