package gestioneCarrello;
import java.util.ArrayList;
import java.util.List;

public class Carts<T> {
	
		List<T> items;
		
		public Carts() {
			items = new ArrayList<T>();
		}
		
		public void addItem(T item) {
			items.add(item);
		}
		
		public void deleteItem(T item) {
		 items.remove(item);
		}
		
		public List<T> getItems(){
			return items;
		}
		
		public void deleteItems() {
			items.clear();
		}
		
}
