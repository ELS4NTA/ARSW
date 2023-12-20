package edu.eci.arsw.myrestaurant.beans.impl.colombia;

import edu.eci.arsw.myrestaurant.model.RestaurantProduct;
import edu.eci.arsw.myrestaurant.beans.TaxesCalculator;
import edu.eci.arsw.myrestaurant.model.ProductType;
import org.springframework.stereotype.Service;


public class TaxesCalculator2016ColTributaryReform implements TaxesCalculator {

	@Override
	public float getProductTaxes(RestaurantProduct p) {
		if (p.getType()==ProductType.DRINK){
			return 0.16f;
		}
		else{
			return 0.19f;
		}
	}

}
