package se.iths.kattis.webshopgrupp5.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import se.iths.kattis.webshopgrupp5.model.ProductDTO;
import se.iths.kattis.webshopgrupp5.service.ProductService;

@Controller
@RequestMapping("/admin/products")
public class AdminProductController {

    private final ProductService productService;

    public AdminProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("productDTO", new ProductDTO());
        return "admin/createProduct";
    }

    @PostMapping("/create")
    public String createProduct(@Valid @ModelAttribute("productDTO") ProductDTO productDTO,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/createProduct";
        }

        productService.create(
                productDTO.getName(),
                productDTO.getPrice(),
                productDTO.getCategory(),
                productDTO.getPictureUrl()
        );
        return "redirect:/products";
    }

    @GetMapping("/delete")
    public String deleteProduct(@RequestParam String name) {
        productService.deleteProductByName(name);
        return "redirect:/products";
    }
}
