public class Main {
  public static void main(String[] args) {
    Bazaar b = new Bazaar("a", "b", 10, 10);
    Item i = b.listItem("a", 11, "b");
    i.buyItem(b);
    //i.buyItem(b);
    b.close();
  }

}
