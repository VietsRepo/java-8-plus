package greeting.app;

import greeting.api.Greeter;

public class GreetingSystem implements Greeter {

	@Override
	public String greet(String name) {
		return "Welcome to Java Module System, " + name;
	}

}
