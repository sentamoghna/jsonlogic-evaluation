// No package declaration!

import org.kie.internal.io.ResourceFactory;
import org.kie.api.runtime.KieSession;
import org.kie.api.io.ResourceType;
import org.kie.internal.utils.KieHelper;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.jamsesso.jsonlogic.JsonLogic;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {

        // Drool
        KieHelper kieHelper = new KieHelper();
        kieHelper.addResource(ResourceFactory.newClassPathResource("loan-rules.drl"), ResourceType.DRL);
        KieSession ksession = kieHelper.build().newKieSession();

        Map<String, Object> input = new HashMap<>();
        input.put("income", 60000);

        ksession.insert(input);
        int fired = ksession.fireAllRules();
        System.out.println("Rules fired: " + fired);
        ksession.dispose();

        // JSONLogic
        JsonLogic jsonLogic = new JsonLogic();
        ObjectMapper mapper = new ObjectMapper();
        String ruleJson = new String(Files.readAllBytes(Paths.get("src/main/resources/loan-rules.json")));
        List<Map<String, Object>> rules = mapper.readValue(ruleJson, List.class);

        Map<String, Object> data = new HashMap<>();
        data.put("income", 60000);

        List<String> firedResults = new ArrayList<>();
        for (Map<String, Object> rule : rules) {
            Object logic = rule.get("logic");
            boolean matches = (boolean) jsonLogic.apply(mapper.writeValueAsString(logic), data);
            if (matches) {
                firedResults.add((String) rule.get("result"));
            }
        }
        System.out.println("JSONLogic");
        System.out.println("All matching rules: " + firedResults);
    }
}
