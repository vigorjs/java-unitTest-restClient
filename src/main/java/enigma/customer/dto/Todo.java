package enigma.customer.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Todo {
    private Integer id;
    private Integer userId;
    private String title;
    private Boolean completed;
}
