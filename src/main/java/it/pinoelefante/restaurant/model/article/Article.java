package it.pinoelefante.restaurant.model.article;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.pinoelefante.restaurant.model.Audit;
import it.pinoelefante.restaurant.model.Identifiable;
import it.pinoelefante.restaurant.model.reference_data.SaleType;
import it.pinoelefante.restaurant.repository.AuditListener;
import it.pinoelefante.restaurant.repository.Auditable;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.util.Objects.isNull;

@Entity
@Table(schema = "article", name = "anagraphic")
@EntityListeners(AuditListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE article.anagraphic SET enabled = false WHERE id = ?")
@Builder
public class Article implements Identifiable<Integer>, Auditable, EntityWithPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private boolean enabled;
    @Embedded
    private Audit audit;

    @ManyToOne
    @JoinColumn(name = "sale_type")
    @NotNull
    private SaleType saleType;

    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY)
    private List<ArticleAttribute> attributes;

    @OneToMany(mappedBy = "article")
    private List<ArticleIngredient> ingredients;

    @OneToMany(mappedBy = "article")
    private List<ArticlePrice> prices;

    public PriceEntity getNextPrice() {
        if (isNull(prices) || prices.isEmpty()) {
            return null;
        }
        return prices.stream().filter(x -> x.getStartDate().isAfter(LocalDate.now()))
                .min(Comparator.comparing(ArticlePrice::getStartDate))
                .orElse(null);
    }

    public PriceEntity getPreviousPrice() {
        PriceEntity current = getCurrentPrice();
        if (isNull(current)) {
            return null;
        }
        return prices.stream().filter(x -> x.getStartDate().isBefore(current.getStartDate()))
                .max(Comparator.comparing(ArticlePrice::getStartDate)).orElse(null);
    }

    public PriceEntity getCurrentPrice() {
        if (isNull(prices) || prices.isEmpty()) {
            return null;
        }
        return prices.stream()
                .filter(x -> x.getStartDate().compareTo(LocalDate.now()) <= 0)
                .max(Comparator.comparing(ArticlePrice::getStartDate))
                .orElse(null);
    }

    @JsonIgnore
    public List<ArticleAttribute> getAttributes() {
        return isNull(attributes) ? Collections.emptyList() : attributes;
    }

    public synchronized void addAttribute(ArticleAttribute attr) {
        if (isNull(attributes)) {
            attributes = new ArrayList<>();
        }
        attributes.add(attr);
    }
}
