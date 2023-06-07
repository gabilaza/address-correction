# Address correction - PA Project 2023

- Write an algorithm that corrects the fields country, state, city of a postal address. The Spring boot project will
  expose a REST API that will receive an address as input and return its corrected form. It is not allowed to use any
  external APIs or libraries that process Addresses.


- Address input fields: country, state, city, postal code

## Example

### Input

```json
{
  "country": "RO",
  "state": "New York",
  "city": "Iasi",
  "postalCode": "700000"
}
```

### Output

```json
{
  "country": "Romania",
  "state": "Iasi",
  "city": "Iasi",
  "postalCode": "700028"
}
```

## Grading

### 15 points

- [x] Supports one language
- [x] Supports 1 country
- [x] Normalization of the address (i.e. lowercase, remove .,;“!?-)
- [x] Tests for latency: average ms to correct an address.
- [x] Tests for precisions (%): take 100 correct addresses, make mistakes on them, call the API, then compare the
  original
  correct address with the response from the API.
- [x] Project can be executed locally

### 30 points

- [x] Supports addresses written in multiple languages
- [x] Supports 10+ countries
- [x] Postal code to be used as confidence booster (optional)
- [x] Project is deployed in the cloud
