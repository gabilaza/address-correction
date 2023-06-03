# Address correction - PA Project 2023

- Write an algorithm that corrects the fields country, state, city of a postal address. The Spring boot project will
  expose a REST API that will receive an address as input and return its corrected form. It is not allowed to use and
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

- [ ] Supports one language
- [ ] Supports 1 country
- [ ] Normalization of the address (i.e. lowercase, remove .,;“!?-)
- [ ] Tests for latency: average ms to correct an address.
- [ ] Tests for precisions (%): take 100 correct addresses, make mistakes on them, call the API then compare the
  original
  correct address with the response from the API.
- [ ] Project can be executed locally

### 30 points

- [ ] Supports addresses written in multiple languages
- [ ] Supports 10+ countries
- [ ] Postal code to be used as confidence booster (optional)
- [ ] Project is deployed in cloud