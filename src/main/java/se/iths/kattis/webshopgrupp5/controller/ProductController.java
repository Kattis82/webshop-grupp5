package se.iths.kattis.webshopgrupp5.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.iths.kattis.webshopgrupp5.model.Product;
import se.iths.kattis.webshopgrupp5.service.AppUserService;
import se.iths.kattis.webshopgrupp5.service.CartService;
import se.iths.kattis.webshopgrupp5.service.ProductService;

import java.security.Principal;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final CartService cartService;
    private final AppUserService appUserService;

    public ProductController(ProductService productService, CartService cartService, AppUserService appUserService) {
        this.productService = productService;
        this.cartService = cartService;
        this.appUserService = appUserService;
    }

    //listar alla produkter eller filtrera per kategori
    @GetMapping
    public String listProducts(@RequestParam(required = false) String category, Model model, Principal principal) {
        boolean isAdmin = false;

        if (principal != null) {
            isAdmin = appUserService.findByUsername(principal.getName())
                    .map(user -> "ADMIN".equals(user.getRole()))
                    .orElse(false);
        }
        model.addAttribute("isAdmin", isAdmin);

        if (category != null) {
            model.addAttribute("products", productService.findByCategory(category));
            model.addAttribute("category", category);
            return "products/category";
        }

        model.addAttribute("products", productService.getAllProducts());
        return "products";
    }

    //visa produktinfo
    @GetMapping("/name/{name}")
    public String productDetails(@PathVariable String name, Model model) {
        model.addAttribute("product", productService.findByProductname(name));
        return "products/detail";
    }

    //sök efter produkt
    @GetMapping("/search")
    public String searchProducts(@RequestParam String query, Model model) {
        model.addAttribute("products", productService.search(query));
        model.addAttribute("query", query);
        return "products";
    }

    //lägg till i kundvagn
    @GetMapping("/add/{id}")
    public String addToCart(@PathVariable Long id) {
        Product product = productService.findById(id);
        cartService.addProduct(product);
        return "redirect:/products/cart";
    }

    //länk till kundvagn
    @GetMapping("/cart")
    public String userCart(Model model) {
        model.addAttribute("cart", cartService.getCartItems());
        return "cart";
    }
}
