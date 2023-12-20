package edu.eci.arsw.myrestaurant.model;

public class RestaurantProduct  {

	private int price;

	private String name;
        
        private ProductType type;


        public RestaurantProduct(){
            
        }
        
	public RestaurantProduct(String nombre, int precio, ProductType type) {
		super();
		this.price = precio;
		this.name = nombre;
                this.type=type;
	}
        public ProductType getType() {
            return type;
        }
        public void setType(ProductType type) {
            this.type = type;
        }

    
        public void setPrice(int precio) {
            this.price = precio;
        }

    
        public void setName(String nombre) {
            this.name = nombre;
        }
        
        
    
	public String getName() {
		return name;
	}
	

	public int getPrice() {
		return price;
	}
	
}
