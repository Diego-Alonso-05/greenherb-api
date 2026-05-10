# Traceability Matrix

| ID | Requirement | Endpoint | Test Type | Test Name | Result |
|----|-------------|-----------|------------|------------|--------|
| AUTH-01 | User login with valid credentials | POST /auth/login | Integration | loginWithValidCredentialsShouldReturnToken | PASS |
| AUTH-02 | User login with invalid credentials | POST /auth/login | Integration | loginWithInvalidCredentialsShouldFail | PASS |
| PLAN-01 | Create valid plan | POST /plans | Integration | createValidPlanShouldReturnOk | PASS |
| PLAN-02 | Reject invalid temperature range | POST /plans | Integration | createPlanWithInvalidTemperatureRangeShouldReturnBadRequest | PASS |
| PLAN-03 | Retrieve plans | GET /plans | Integration | getPlansShouldReturnOk | PASS |
| PLAN-04 | Create valid plan logic | Service Unit Test | createValidPlanShouldSavePlan | PASS |
| PLAN-05 | Reject invalid humidity range | Service Unit Test | createPlanWithInvalidHumidityRangeShouldThrowException | PASS |
| MEAS-01 | Create normal measurement | POST /measurements | Integration | createMeasurementInsideLimitsShouldNotCreateAlert | PASS |
| MEAS-02 | Generate CRITICAL alert | POST /measurements | Integration | createMeasurementWithCriticalConditionsShouldCreateCriticalAlert | PASS |
| MEAS-03 | Generate WARNING alert | POST /measurements | Integration | createMeasurementWithWarningConditionsShouldCreateWarningAlert | PASS |
| MEAS-04 | Reject measurement without active plan | Service Unit Test | noActivePlanShouldThrowException | PASS |
| ALERT-01 | Retrieve alerts | GET /alerts | Integration | getAlertsShouldReturnOk | PASS |
| ALERT-02 | Resolve alert | PATCH /alerts/{id}/resolve | Integration | resolveAlertShouldChangeStatusToResolved | PASS |
| ALERT-03 | Ignore alert with justification | PATCH /alerts/{id}/ignore | Integration | ignoreAlertWithJustificationShouldChangeStatusToIgnored | PASS |
| ALERT-04 | Reject ignore without justification | PATCH /alerts/{id}/ignore | Integration | ignoreAlertWithoutJustificationShouldReturnBadRequest | PASS |