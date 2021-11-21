package it.pinoelefante.restaurant.controller.dto.article.out;

import it.pinoelefante.restaurant.model.reference_data.SaleType;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SaleTypeDtoOut implements Serializable {
    private Integer id;
    private String description;

    public SaleTypeDtoOut(SaleType saleType) {
        setId(saleType.getId());
        var descr = StringUtils.firstNonBlank(saleType.getTranslation(), saleType.getName());
        setDescription(descr);
    }
}
