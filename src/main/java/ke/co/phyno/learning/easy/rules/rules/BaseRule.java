package ke.co.phyno.learning.easy.rules.rules;

import lombok.Getter;
import lombok.Setter;

@Getter
public class BaseRule {
    @Setter
    private int priority = 0;

    @Setter
    private String data = null;
}
