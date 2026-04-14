package se.iths.kattis.webshopgrupp5.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {

    @NotBlank(message = "Produktnamn får inte vara tomt")
    private String name;
    @NotNull(message = "Pris måste anges")
    @Positive(message = "Priset måste vara större än 0kr")
    private Double price;
    @NotBlank(message = "Kategori måste anges")
    private String category;
    @NotBlank(message = "BildUrl får inte vara tom")
    private String pictureUrl;
}
