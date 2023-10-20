package ke.co.phyno.learning.easy.rules.type;

import ke.co.phyno.learning.easy.rules.rules.BaseRule;
import ke.co.phyno.learning.easy.rules.rules.age.MaximumAgeBaseRule;
import ke.co.phyno.learning.easy.rules.rules.age.MinimumAgeBaseRule;
import ke.co.phyno.learning.easy.rules.utils.rules.RuleTypeUtils;
import lombok.Getter;
import org.springframework.lang.NonNull;

@Getter
public enum RuleType {
    MINIMUM_AGE("", RuleTypeUtils.getBean(MinimumAgeBaseRule.class)),
    MAXIMUM_AGE("", RuleTypeUtils.getBean(MaximumAgeBaseRule.class)),
    ;

    private final String description;
    private final BaseRule rule;

    RuleType(@NonNull String description, @NonNull BaseRule rule) {
        this.description = description;
        this.rule = rule;
    }
}
