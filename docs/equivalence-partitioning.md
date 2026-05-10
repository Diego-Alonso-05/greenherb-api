# Equivalence Partitioning

## Temperature Validation

| Class | Input Example | Expected Result |
|------|----------------|----------------|
| Valid temperature | 23 | Accepted |
| Below minimum | 15 | WARNING alert |
| Above maximum | 30 | CRITICAL alert |
| Null value | null | Invalid request |

---

## Humidity Validation

| Class | Input Example | Expected Result |
|------|----------------|----------------|
| Valid humidity | 60 | Accepted |
| Below minimum | 30 | CRITICAL alert |
| Above maximum | 90 | WARNING alert |
| Null value | null | Invalid request |

---

## Plan Validation

| Class | Input Example | Expected Result |
|------|----------------|----------------|
| Valid range | minTemp=18, maxTemp=28 | Accepted |
| Invalid temp range | minTemp=30, maxTemp=20 | Rejected |
| Invalid humidity range | minHumidity=90, maxHumidity=80 | Rejected |

---

## Alert Ignore Validation

| Class | Input Example | Expected Result |
|------|----------------|----------------|
| Valid justification | "Sensor error" | Accepted |
| Empty justification | "" | Rejected |
| Null justification | null | Rejected |