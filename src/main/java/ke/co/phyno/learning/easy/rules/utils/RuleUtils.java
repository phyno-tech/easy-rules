package ke.co.phyno.learning.easy.rules.utils;

import ke.co.phyno.learning.easy.rules.rules.BaseRule;
import ke.co.phyno.learning.easy.rules.rules.age.MaximumAgeRule;
import ke.co.phyno.learning.easy.rules.rules.age.MinimumAgeRule;
import ke.co.phyno.learning.easy.rules.type.RuleType;
import lombok.SneakyThrows;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class RuleUtils implements ApplicationContextAware {
    private static ApplicationContext context;

    public static <T> T getBean(Class<T> bean) {
        return context.getAutowireCapableBeanFactory().getBean(bean);
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext context) throws BeansException {
        RuleUtils.context = context;
    }

    @SneakyThrows
    public BaseRule rule(@NonNull RuleType type) {
        return switch (type) {
            case MINIMUM_AGE -> new MinimumAgeRule();
            case MAXIMUM_AGE -> new MaximumAgeRule();
            default -> throw new Exception(String.format("Rule not implemented %s", type.name()));
        };
    }
}
