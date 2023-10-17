package ke.co.phyno.learning.easy.rules.type;

import ke.co.phyno.learning.easy.rules.rules.age.MaximumAgeRule;
import ke.co.phyno.learning.easy.rules.rules.age.MinimumAgeRule;
import lombok.Getter;
import org.springframework.lang.NonNull;

@Getter
public enum RuleType {
    MINIMUM_AGE("", new MinimumAgeRule()),
    MAXIMUM_AGE("", new MaximumAgeRule()),
    ;

    private final String description;
    private final Object object;

    <T> RuleType(@NonNull String description, @NonNull T object) {
        this.description = description;
        this.object = object;
    }
}
