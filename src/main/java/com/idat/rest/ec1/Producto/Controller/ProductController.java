package com.idat.rest.ec1.Producto.Controller;

import com.idat.rest.ec1.Producto.Model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("api/products")
public class ProductController {

    private final List<Product> products = new ArrayList<>();
    private final AtomicLong contador = new AtomicLong();

    public ProductController() {
        initData();
    }

    private void initData(){
        Product lentes = new Product(
                contador.incrementAndGet(),
                "Lentes UV",
                "Lentes con tecnología UV" ,
                249.99,
                20);
        Product teclado = new Product(
                contador.incrementAndGet(),
                "Teclado Arrai",
                "Teclado mécanico 1-200" ,
                129.99,
                13);
        Product mouse = new Product(
                contador.incrementAndGet(),
                "Teclado Bl500",
                "Teclado de oficina" ,
                49.99,
                48);
        products.add(lentes);
        products.add(teclado);
        products.add(mouse);
    }

    @GetMapping()
    public ResponseEntity<List<Product>> getProducts() {
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        Product product = products.stream().filter(p -> p.getId().equals(id)).findAny().orElse(null);
        if(product != null){
            return new ResponseEntity<>(product, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product newProduct = new Product(
                contador.incrementAndGet(),
                product.getNombre(),
                product.getDescripcion(),
                product.getPrecio(),
                product.getCantidad());
        products.add(newProduct);
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        Product productUpdate = null;
        for(Product p : products){
            if(p.getId() == id){
                p.setNombre(product.getNombre());
                p.setDescripcion(product.getDescripcion());
                p.setPrecio(product.getPrecio());
                p.setCantidad(product.getCantidad());
                productUpdate = p;
                break;
            }
        }
        return new ResponseEntity<>(productUpdate, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable Long id) {
        Product product = products.stream().filter(p -> p.getId().equals(id)).findAny().orElse(null);
        if (product != null) {
            products.remove(product);
            return new ResponseEntity<Product>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
