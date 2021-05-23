package com.myapp.spring.web.api;



import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myapp.spring.exception.NotEnoughProductsInStockException;
import com.myapp.spring.model.Product;
import com.myapp.spring.repository.ProductRepository;
import com.myapp.spring.service.ShoppingCartService;

@RestController
public class ShoppingCartAPI {

	@Autowired
    private final ShoppingCartService shoppingCartService;

	@Autowired
    private final ProductRepository productRepository;

    @Autowired
    public ShoppingCartAPI(ShoppingCartService shoppingCartService , ProductRepository productRepository) {
        this.productRepository = productRepository;
        this.shoppingCartService= shoppingCartService;
    }

    @GetMapping("/shoppingCart")
    public ResponseEntity<Map<Product,Integer>> shoppingCart() {
    	
    	return new ResponseEntity<Map<Product,Integer>>(shoppingCartService.getProductsInCart(),HttpStatus.OK); 
    }

    @PostMapping("/shoppingCart/addProduct/{productId}/{quantity}")
    public ResponseEntity<Map<Product,Integer>> addProductToCart(@PathVariable("productId") Long productId,@PathVariable("quantity") Integer quantity) {
        Product pro=productRepository.findById(productId).orElse(new Product());
       if(pro!=null)
    	  return new ResponseEntity<Map<Product,Integer>>(shoppingCartService.addProduct(pro,quantity),HttpStatus.OK);
       return shoppingCart();
    }

    @PostMapping("/shoppingCart/removeProduct/{productId}/{quantity}")
    public ResponseEntity<Map<Product,Integer>>removeProductFromCart(@PathVariable("productId") Long productId,@PathVariable("quantity") Integer quantity) {
    	 Product pro=productRepository.findById(productId).orElse(new Product());
         if(pro!=null)
        	 return new ResponseEntity<Map<Product,Integer>>(shoppingCartService.removeProduct(pro,quantity),HttpStatus.OK);
         return shoppingCart();
    }

    @GetMapping("/shoppingCart/checkout")
    public ResponseEntity<Map<Product,Integer>>  checkout() {
        try {
             shoppingCartService.checkout();
        } catch (NotEnoughProductsInStockException e) {
            	e.printStackTrace();
        }
        return shoppingCart();
    }
   
}
