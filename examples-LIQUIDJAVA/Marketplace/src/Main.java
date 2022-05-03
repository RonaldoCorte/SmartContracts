import repair.regen.specification.Refinement;



public class Main {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Marketplace m = new Marketplace(1, 10 , 2, 15, "item", 5);		
		System.out.println(m.makeOffer(3, 6));
		m.accept(2);

		@Refinement("x ==0")
		int x = 0;
		
	}

}
