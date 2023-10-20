package ke.co.phyno.learning.easy.rules.rules.age;

import ke.co.phyno.learning.easy.rules.data.customer.CustomerInfoData;
import ke.co.phyno.learning.easy.rules.rules.BaseRule;
import ke.co.phyno.learning.easy.rules.type.RuleType;
import ke.co.phyno.learning.easy.rules.utils.SharedUtils;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.jeasy.rules.api.*;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

@Log
@SpringBootTest
public class AgeRulesTests {
    @Autowired
    private SharedUtils sharedUtils;

    private final CustomerInfoData customerInfo = CustomerInfoData.builder()
            .name("Phelix Ochieng")
            .dateOfBirth(Date.valueOf(LocalDate.of(1980, 1, 4)))
            .build();

    private RulesEngine rulesEngine() {
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

            @SneakyThrows
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

    private Facts facts() {
        Facts facts = new Facts();
        facts.put("REQUEST_ID", UUID.randomUUID().toString());
        facts.put("FINACLE_CUSTOMER_INFO", this.customerInfo);
        return facts;
    }

    @Test
    @DisplayName(value = "Test minimum age rule")
    public void testMinimumAgeRule() {
        Rules rules = new Rules();
        rules.register(RuleType.MINIMUM_AGE.getRule());

        RulesEngine rulesEngine = this.rulesEngine();
        Facts facts = this.facts();
        rulesEngine.fire(rules, facts);
        log.log(Level.INFO, String.format("Minimum age rule fired [ %s ]", facts));
    }

    @Test
    @DisplayName(value = "Test maximum age rule")
    public void testMaximumAgeRule() {
        Rules rules = new Rules();
        rules.register(RuleType.MAXIMUM_AGE.getRule());

        RulesEngine rulesEngine = this.rulesEngine();
        Facts facts = this.facts();
        rulesEngine.fire(rules, facts);
        log.log(Level.INFO, String.format("Maximum age rule fired [ %s ]", facts));
    }

    @Test
    @DisplayName(value = "Test all age rules")
    public void testAllAgeRules() {
        List<BaseRule> rules = new ArrayList<>();

        BaseRule min = RuleType.MINIMUM_AGE.getRule();
        min.setPriority(1);
        rules.add(min);

        BaseRule max = RuleType.MAXIMUM_AGE.getRule();
        max.setPriority(2);
        rules.add(max);

        RulesEngine rulesEngine = this.rulesEngine();
        Facts facts = this.facts();

        rulesEngine.fire(new Rules(rules.toArray()), facts);
        log.log(Level.INFO, String.format("All rules fired [ %s ]", this.sharedUtils.toJson(facts.asMap(), true)));
    }
}
