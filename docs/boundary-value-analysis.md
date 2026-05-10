# Boundary Value Analysis

## Temperature Limits

| Test Case | Value | Expected Result |
|-----------|------|----------------|
| min-1 | 17 | WARNING |
| min | 18 | Accepted |
| nominal | 23 | Accepted |
| max | 28 | Accepted |
| max+1 | 29 | CRITICAL |

---

## Humidity Limits

| Test Case | Value | Expected Result |
|-----------|------|----------------|
| min-1 | 39 | CRITICAL |
| min | 40 | Accepted |
| nominal | 60 | Accepted |
| max | 80 | Accepted |
| max+1 | 81 | WARNING |

---

## Plan Temperature Range

| Test Case | minTemp | maxTemp | Expected |
|-----------|---------|---------|----------|
| Invalid | 30 | 20 | Rejected |
| Boundary valid | 18 | 28 | Accepted |

---

## Plan Humidity Range

| Test Case | minHumidity | maxHumidity | Expected |
|-----------|-------------|-------------|----------|
| Invalid | 90 | 80 | Rejected |
| Boundary valid | 40 | 80 | Accepted |