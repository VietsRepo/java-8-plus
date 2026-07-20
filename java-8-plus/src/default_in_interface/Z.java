package default_in_interface;

public class Z implements X, Y {

	@Override
	public void describe() {
		X.super.describe();
	}
	
}
