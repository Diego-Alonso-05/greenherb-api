# Defect Report

During testing and validation of the GREENHERB API, several defects and unexpected behaviours were identified.

The following defects were documented according to:
- severity,
- priority,
- reproduction steps,
- expected behaviour,
- actual behaviour.

DEFECT-01

Title:
Multiple active plans generate query conflict

Severity:
High

Priority:
High

Description:
The application fails when more than one active plan exists in the database.

Steps to Reproduce:
1. Create one active plan
2. Create another active plan
3. Submit a measurement

Expected Result:
The system should:
- reject duplicate active plans
  OR
- automatically select only one active plan

Actual Result:
The system throws the error:

Query did not return a unique result

--------------------------------------------------

DEFECT-02

Title:
Measurement creation without active plan

Severity:
Medium

Priority:
Medium

Description:
Measurements cannot be processed when no active plan exists.

Steps to Reproduce:
1. Remove all active plans
2. Submit a measurement

Expected Result:
The system should display a clearer validation message.

Actual Result:
The API returns:

No active plan found

--------------------------------------------------

DEFECT-03

Title:
Ignoring alert without justification

Severity:
Low

Priority:
Low

Description:
The API rejects requests when the justification field is empty.

Steps to Reproduce:
1. Create an alert
2. Call ignore endpoint with empty justification

Expected Result:
Validation message explaining the problem.

Actual Result:
HTTP 400 Bad Request returned.

--------------------------------------------------

The identified defects helped improve the robustness of the application and validate edge-case behaviours during the QA process.

The testing process successfully detected:
- validation issues,
- data consistency problems,
- alert management edge cases.