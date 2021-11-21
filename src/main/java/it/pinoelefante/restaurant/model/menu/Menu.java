package it.pinoelefante.restaurant.model.menu;

import it.pinoelefante.restaurant.model.Audit;
import it.pinoelefante.restaurant.model.Identifiable;
import it.pinoelefante.restaurant.repository.AuditListener;
import it.pinoelefante.restaurant.repository.Auditable;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Entity
@Table(schema = "menu", name = "menu")
@EntityListeners(AuditListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE menu.menu SET enabled = false where id = ?")
public class Menu implements Identifiable<Integer>, Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private boolean enabled;
    @Embedded
    private Audit audit;

    @OneToMany(mappedBy = "menu", fetch = FetchType.LAZY)
    private List<MenuComposition> menuCategories;

    public List<MenuComposition> getCategories() {
        if (isNull(menuCategories) || menuCategories.isEmpty()) {
            return Collections.emptyList();
        }
        return menuCategories.stream()
                .sorted(Comparator.comparing(MenuComposition::getOrder).thenComparing(x -> x.getAudit().getCreatedAt()))
                .collect(Collectors.toList());
    }
}
