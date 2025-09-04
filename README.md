# jsonlogic-evaluation

## JSONLogic vs Drools

| **Feature**               | **JSONLogic**                      | **Drools**                                               |
|----------------------------|-----------------------------------|----------------------------------------------------------|
| **Rule execution**         | One evaluation → one result       | Rule can fire multiple times                             |
| **Side effects**           | None                              |  Can update facts                                        |
| **Chaining**               | No                                | Yes, forward-chaining triggers more rules                |
| **Determinism**            | Always same                       | Depends on agenda, salience, and fact updates            |
| **Multi-result per input** | Only one                          | Possible (through multiple rules or rule chaining)       |


### Drools works well for growing complexity because of

- Conflict resolution / prioritization - As more rules overlap, Drools’ agenda, salience, and conflict resolution automatically decide which rules should run first. You don’t need to manually order them.

- Forward chaining - New rules can depend on the results of old rules. If your rulebase grows to handle new conditions, you don’t have to restructure the old logic — the engine naturally reevaluates facts and fires applicable rules.

- Scalability - Unlike JSONLogic (which is linear and manual), Drools can handle hundreds or thousands of rules without your code turning into a giant nested if-else.