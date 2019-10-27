package nl.amis.js2java;

import java.util.Map;
import java.util.Random;

import java.lang.reflect.Method;


public class Joker {

	String[] jokes = {"What's the best thing about Switzerland? I don't know, but the flag is a big plus.", "I’d like to start with the chimney jokes – I’ve got a stack of them. The first one is on the house.",
	      			"As a scarecrow, people say I’m outstanding in my field. But hay - it’s in my jeans.",
	    			"The best time to add insult to injury is when you’re signing somebody’s cast.",
	    			"Years ago I used to supply Filofaxes for the mafia. I was involved in very organised crime.",
	    			"I had a dream last night that I was cutting carrots with the Grim Reaper – dicing with death.",
	    			"I told my girlfriend she drew her eyebrows too high. She seemed surprised.",
	    			"I went to buy some camouflage trousers the other day, but I couldn’t find any.",
	    			"I waited and stayed up all night and tried to figure out where the sun was. Then it dawned on me.",
	    			"I saw this bloke chatting-up a cheetah. I thought: 'He’s trying to pull a fast one.'",
	    			"I have kleptomania. But when it gets bad, I take something for it.",
	    			"Time flies like an arrow, fruit flies like a banana.",
	    			"This bloke said to me: 'I’m going to attack you with the neck of a guitar.' I said: 'Is that a fret?'",
	    			"Somebody actually complimented me on my driving today. They left a little note, it said ‘Parking Fine.’"};
	
	public String getJoke() {
		String joke = jokes[new Random().nextInt(jokes.length)];
		return joke;
	}
	
	public String[] getJokes(int numberOfJokes) {
		String[] jokes = new String[numberOfJokes];
		for (int i=0;i<numberOfJokes;i++) {
			jokes[i]=getJoke();
		}
		return jokes;
	}
	
	public void transferJokes(Map transferContext) {
		int numberOfJokes = (int) transferContext.get("desiredNumberOfJokes");
		transferContext.put("jokes", getJokes(numberOfJokes));								
	}
	
	public static void main(String[] args) {
		System.out.println("Cracking a Joke: "+ new Joker().getJoke());
	}
}
