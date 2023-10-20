package ke.co.phyno.learning.easy.rules.data.rules;

import lombok.*;

import java.io.Serializable;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RuleAgeData implements Serializable {
    @Builder.Default
    private int age = 0;

}
