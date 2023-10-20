package ke.co.phyno.learning.easy.rules.config;

import ke.co.phyno.learning.easy.rules.utils.SharedUtils;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.jeasy.rules.api.*;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.logging.Level;

@Log
@Configuration
public class RuleConfigurer {
    @Autowired
    private SharedUtils sharedUtils;

    @Bean
    public RulesEngine ageRulesEngine() {
        DefaultRulesEngine rulesEngine = new DefaultRulesEngine(new RulesEngineParameters(false, true, true, RulesEngineParameters.DEFAULT_RULE_PRIORITY_THRESHOLD));
        rulesEngine.registerRuleListener(new RuleListener() {
            @Override
            public boolean beforeEvaluate(Rule rule, Facts facts) {
                log.log(Level.FINE, String.format("Evaluate rule - %s", rule.getName()));
                if (facts.get("JOHARI_ERROR") == null) {
                    return RuleListener.super.beforeEvaluate(rule, facts);
                }
                return false;
            }

            @SneakyThrows(value = Exception.class)
            @Override
            public void afterEvaluate(Rule rule, Facts facts, boolean success) {
                log.log(Level.FINE, String.format("Evaluate rule done [ rule=%s, success=%s, facts=%s ]", rule.getName(), success, sharedUtils.toJson(facts.asMap(), true)));
                if (success && facts.get("JOHARI_ERROR") != null) {
                    throw new Exception(String.format("Rule not properly defined - %s", rule.getName()));
                }
                RuleListener.super.afterEvaluate(rule, facts, success);
            }
        });
        return rulesEngine;
    }
}
