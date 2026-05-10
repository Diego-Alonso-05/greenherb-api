# MC/DC Coverage

Modified Condition/Decision Coverage (MC/DC) is a testing technique used to verify that every condition in a decision independently affects the final outcome.

The GREENHERB API uses several logical conditions to:
- generate CRITICAL alerts,
- generate WARNING alerts,
- validate plans,
- validate ignored alerts.

The following tables demonstrate the MC/DC analysis performed.

The system creates a CRITICAL alert when:

if (highTemperature && lowHumidity)

Both conditions must be true.

| Test Case | High Temperature | Low Humidity | Result |
|------------|------------------|---------------|--------|
| TC1 | false | false | No alert |
| TC2 | true | false | No alert |
| TC3 | false | true | No alert |
| TC4 | true | true | CRITICAL alert |

This demonstrates that each condition independently affects the final decision.

The system creates a WARNING alert when:

if (highHumidity || lowTemperature)

At least one condition must be true.

| Test Case | High Humidity | Low Temperature | Result |
|------------|----------------|----------------|--------|
| TC1 | false | false | No warning |
| TC2 | true | false | WARNING |
| TC3 | false | true | WARNING |
| TC4 | true | true | WARNING |

This demonstrates that both conditions independently affect the decision outcome.

The system validates plan ranges using:

if (minTemp >= maxTemp)

| Test Case | Condition Result | Expected Behaviour |
|------------|-----------------|-------------------|
| TC1 | false | Plan accepted |
| TC2 | true | Plan rejected |

This ensures invalid temperature ranges are rejected.

The system validates ignored alerts using:

if (justification == null || justification.isBlank())

| Test Case | Null | Blank | Result |
|------------|------|-------|--------|
| TC1 | false | false | Accepted |
| TC2 | true | false | Rejected |
| TC3 | false | true | Rejected |

This ensures alerts cannot be ignored without proper justification.

The MC/DC analysis demonstrates that:
- every logical condition was independently evaluated,
- all relevant combinations were tested,
- the alert system and validation logic behave correctly under different scenarios.