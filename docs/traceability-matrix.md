# Traceability Matrix

| ID | Requirement | Endpoint | Test Type | Test Name | Result |
|----|-------------|-----------|------------|------------|--------|
| AUTH-01 | User login with valid credentials | POST /auth/login | Integration | loginWithValidCredentialsShouldReturnToken | PASS |
| AUTH-02 | User login with invalid credentials | POST /auth/login | Integration | loginWithInvalidCredentialsShouldFail | PASS |
| AUTH-03 | Valid login returns JWT token | AuthService.login | Unit | login_withValidCredentials_returnsToken | PASS |
| AUTH-04 | Invalid username should throw exception | AuthService.login | Unit | login_withInvalidUsername_throwsException | PASS |
| AUTH-05 | Wrong password should throw exception | AuthService.login | Unit | login_withWrongPassword_throwsException | PASS |

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

| SYS-01 | JWT authentication flow | POST /auth/login | System / Postman | POST Login | PASS |
| SYS-02 | Retrieve plans with JWT token | GET /plans | System / Postman | GET Get Plans | PASS |
| SYS-03 | Create valid plan with JWT token | POST /plans | System / Postman | POST Create Plan | PASS |
| SYS-04 | Create critical measurement and generate alert | POST /measurements | System / Postman | POST Create Critical Measurement | PASS |
| SYS-05 | Retrieve generated alerts | GET /alerts | System / Postman | GET Get Alerts | PASS |
| SYS-06 | Resolve alert successfully | PATCH /alerts/{id}/resolve | System / Postman | PATCH Resolve Alert | PASS |
| SYS-07 | Reject unauthorized access without JWT token | Protected endpoints | System / Postman | Unauthorized Request Should Return 401/403 | PASS |

---

# Reverse Traceability Matrix (Requirement -> Tests)

| Requirement | Related Tests |
|-------------|----------------|
| User authentication | AUTH-01, AUTH-02, AUTH-03, AUTH-04, AUTH-05, SYS-01 |
| JWT authorization and protected endpoints | SYS-02, SYS-03, SYS-07 |
| Plan creation and validation | PLAN-01, PLAN-02, PLAN-04, PLAN-05, SYS-03 |
| Plan retrieval | PLAN-03, SYS-02 |
| Measurement registration | MEAS-01, MEAS-04 |
| Alert generation logic | MEAS-02, MEAS-03, SYS-04 |
| Alert retrieval | ALERT-01, SYS-05 |
| Alert resolution | ALERT-02, SYS-06 |
| Alert ignore validation | ALERT-03, ALERT-04 |