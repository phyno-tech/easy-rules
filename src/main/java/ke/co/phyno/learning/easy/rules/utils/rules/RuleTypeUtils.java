package ke.co.phyno.learning.easy.rules.utils.rules;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class RuleTypeUtils implements ApplicationContextAware {
    private static ApplicationContext context;

    public static <T> T getBean(Class<T> bean) {
        return context.getBean(bean);
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext context) throws BeansException {
        RuleTypeUtils.context = context;
    }
}
